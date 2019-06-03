/*
/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the  terms
 * of the European Public License (EUPL), either version 1.1 or (at your option)
 * any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * more details.
 * You should have received a copy of the EUPL1.1 license
 * along with this program; if not, you may find it at
 * http:joinup.ec.europa.eu/software/page/eupl/licence-eupl
 ******************************************************************************/

/**
 * <b>File:</b><p>es.gob.valet.rest.TslRestService.java.</p>
 * <b>Description:</b><p>Class that represents the TSL restful service.</p>
 * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * <b>Date:</b><p>07/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 01/02/2019.
 */
package es.gob.monitoriza.rest.services;

import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.INotificationOriginIds;
import es.gob.monitoriza.constant.INotificationPriority;
import es.gob.monitoriza.constant.INotificationTypeIds;
import es.gob.monitoriza.exception.MonitorizaRestException;
import es.gob.monitoriza.i18n.IRestGeneralLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.SplDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SplMonitoriza;
import es.gob.monitoriza.rest.elements.LogRestRegisterResponse;
import es.gob.monitoriza.service.ISystemNotificationService;
import es.gob.monitoriza.service.impl.SplService;
import es.gob.monitoriza.service.impl.SystemNotificationService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that represents the log service registration restful service.</p>
 * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * @version 1.0, 01/02/2019.
 */
@Path("/log")
public class RegisterLogRestService implements ILogRestService {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(RegisterLogRestService.class);

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.rest.services.INodeRestService#registerNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Restful needs not final access methods.
	@Override
	@POST
	@Path("/registerLog")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public LogRestRegisterResponse registerLog(@FormParam(PARAM_LOG_NAME) final String logName,
			@FormParam(PARAM_LOG_DESCRIPTION) final String logDescription, @FormParam(PARAM_LOG_TYPE) final String logType,
			@FormParam(PARAM_LOG_URL) final String logUrl, @FormParam(PARAM_LOG_KEY) final String logKey) throws MonitorizaRestException {
		// CHECKSTYLE:ON

		// Indicamos la recepcion del servicio junto con los parametros de
		// entrada.
		LOGGER.info(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG034,
		                                                       new Object[ ] { logName, logDescription, logType, logUrl, logKey }));

		// Se crea el objecto que representa el resultado de la operación.
		final LogRestRegisterResponse result = new LogRestRegisterResponse();

		// Comprobamos los parámetros obligatorios de entrada.
		final String resultCheckParams = checkParamsRegisterLog(logName, logDescription, logType, logUrl, logKey);
		if (resultCheckParams != null) {
			LOGGER.error(resultCheckParams);
			result.setStatus(LogRestServiceStatus.STATUS_ERROR_INPUT_PARAMETERS);
			result.setDescription(resultCheckParams);
			result.setResponseTime(LocalDateTime.now());
			return result;
		}

		// Se busca el nodo a registrar.
		final SplMonitoriza node = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.LOG_NODE_MONITORIZA_SERVICE, SplService.class).getSplByUrl(logUrl);

		// El nodo existe, no se hace nada
		if (node != null) {
			result.setDescription(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG011, new Object[ ] { logUrl }));
			result.setStatus(LogRestServiceStatus.STATUS_NODE_WAS_REGISTERED);
			result.setResponseTime(LocalDateTime.now());
			return result;
		}

		// El nodo no existe. Hay que crearlo.
		String msg = null;
		try {
			// Se registra el nodo
			final SplDTO splDto = new SplDTO();
			splDto.setName(logName);
			splDto.setDescription(logDescription);
			splDto.setType(logType);
			splDto.setUrl(logUrl);
			splDto.setKey(logKey);
			ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.LOG_NODE_MONITORIZA_SERVICE, SplService.class).saveSpl(splDto);
			msg = Language.getFormatResRestGeneralMonitoriza(
                IRestGeneralLogMessages.REST_LOG010,
                new Object[ ] { logUrl });
			result.setDescription(msg);
			result.setStatus(LogRestServiceStatus.STATUS_NODE_REGISTER_CREATED);
			result.setResponseTime(LocalDateTime.now());
		}
		catch (final Exception e) {
			throw new MonitorizaRestException(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG009, new Object[ ] { INodeRestService.SERVICENAME_REGISTER_NODE }), e);
		}

		// Se registra la notificación asociada al registro del nodo.
		final ISystemNotificationService sysNotificationService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SYSTEM_NOTIFICATION_SERVICE, SystemNotificationService.class);
		sysNotificationService.registerSystemNotification(INotificationTypeIds.ID_LOG_NOTIFICATION_TYPE, INotificationOriginIds.ID_REST_SERVICE_LOG_ORIGIN, INotificationPriority.ID_NOTIFICATION_PRIORITY_NORMAL, msg);


		return result;
	}

	/**
	 * Check the params from the imput request.
	 * @param logName Log server name.
	 * @param logDescription Log server description.
	 * @param logType Log server type.
	 * @param logUrl Log server access URL.
	 * @param logKey Log server authentication key.
	 * @return
	 */
	private static String checkParamsRegisterLog(final String logName, final String logDescription, final String logType, final String logUrl, final String logKey) {

		final StringBuffer result = new StringBuffer();
		result.append(
            Language.getFormatResRestGeneralMonitoriza(
                IRestGeneralLogMessages.REST_LOG003,
		        new Object[ ] { ILogRestService.SERVICENAME_REGISTER_LOG }));

		boolean checkError = false;

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(logName)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(ILogRestService.PARAM_LOG_NAME);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(logDescription)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(ILogRestService.PARAM_LOG_DESCRIPTION);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(logType)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(ILogRestService.PARAM_LOG_TYPE);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(logUrl)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(ILogRestService.PARAM_LOG_URL);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(logKey)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(ILogRestService.PARAM_LOG_KEY);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		return  checkError ? result.toString() : null;
	}
}