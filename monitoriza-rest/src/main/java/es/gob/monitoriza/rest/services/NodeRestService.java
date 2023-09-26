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
 * @version 1.5, 26/09/2023.
 */
package es.gob.monitoriza.rest.services;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.INotificationOriginIds;
import es.gob.monitoriza.constant.INotificationPriority;
import es.gob.monitoriza.constant.INotificationTypeIds;
import es.gob.monitoriza.exception.MonitorizaRestException;
import es.gob.monitoriza.i18n.IRestGeneralLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.rest.elements.NodeRestStatusResponse;
import es.gob.monitoriza.rest.elements.json.DateString;
import es.gob.monitoriza.service.ISystemNotificationService;
import es.gob.monitoriza.service.impl.NodeMonitorizaService;
import es.gob.monitoriza.service.impl.PlatformService;
import es.gob.monitoriza.service.impl.SystemNotificationService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that represents the node restful service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 26/09/2023.
 */
@Path("/node")
public class NodeRestService implements INodeRestService {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.rest.services.INodeRestService#registerNode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Restful needs not final access methods.
	@Override
	@POST
	@Path("/registerNode")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public NodeRestStatusResponse registerNode(@FormParam(PARAM_NODE_NAME) final String nodeName, @FormParam(PARAM_NODE_HOST) final String nodeHost, @FormParam(PARAM_NODE_PORT) final String nodePort, @FormParam(PARAM_NODE_TYPE) final String nodeType, @FormParam(PARAM_NODE_SECURE) final Boolean nodeSecure, @FormParam(PARAM_SPIE_SELECTED) final Set<String> spieSelected) throws MonitorizaRestException {
		// CHECKSTYLE:ON

		// Indicamos la recepción del servicio junto con los parámetros de
		// entrada.
		LOGGER.info(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG001, new Object[ ] { nodeName, nodeHost, nodePort, spieSelected }));

		// Se crea el objecto que representa el resultado de la operación.
		NodeRestStatusResponse result = null;

		// Inicialmente consideramos que todo es OK para proceder.
		boolean allIsOk = Boolean.TRUE;

		// Comprobamos los parámetros obligatorios de entrada.
		final String resultCheckParams = checkParamsRegisterNode(nodeName, nodeHost, nodePort, nodeType, nodeSecure, spieSelected);
		if (resultCheckParams != null) {
			allIsOk = Boolean.FALSE;
			LOGGER.error(resultCheckParams);
			result = new NodeRestStatusResponse();
			result.setStatus(INodeRestServiceStatus.STATUS_ERROR_INPUT_PARAMETERS);
			result.setDescription(resultCheckParams);
			result.setDateTime(new DateString(Calendar.getInstance().getTime()));
		}

		// Comprobamos que el tipo de plataforma indicada sea correcto
		if (allIsOk) {
			final String resultCheckPlatform = checkPlatform(nodeType);

			if (resultCheckPlatform != null) {
				allIsOk = Boolean.FALSE;
    			result = new NodeRestStatusResponse();
    			result.setStatus(INodeRestServiceStatus.STATUS_ERROR_NODETYPE_PARAMETER);

    			result.setDescription(resultCheckParams);
    			result.setDateTime(new DateString(Calendar.getInstance().getTime()));

			}
		}

		if (allIsOk) {

			String msg = null;
			Integer status = null;
			result = new NodeRestStatusResponse();
    		// Se busca el nodo a registrar.
    		NodeMonitoriza node = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.NODE_MONITORIZA_SERVICE, NodeMonitorizaService.class).getNodeByName(nodeName);
    		
    		// Si el nodo ya existe, se ignora el registro.
    		if (node != null) {
	
    			msg = Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG006, new Object[ ] { nodeName });
    			status = INodeRestServiceStatus.STATUS_ALREADY_REGISTERED;

   			// El nodo no existe. Hay que crearlo.
    		} else {

    			node = prepareNodeToRegister(nodeName, nodeHost, nodePort, nodeType, nodeSecure, spieSelected);
    			msg = Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG005, new Object[ ] { nodeName });
    			status = INodeRestServiceStatus.STATUS_NODE_REGISTER_CREATED;
    			
    			try {
        			// Se registra el nodo, creándolo o modificándolo
        			ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.NODE_MONITORIZA_SERVICE, NodeMonitorizaService.class).saveNode(node);
        			// Se actualiza el mapa de resultados para su uso por parte del semáforo.
        			//StatusNodeHolder.getInstance().getCurrentStatusHolder().put(nodeName, result);
        			final ISystemNotificationService sysNotificationService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SYSTEM_NOTIFICATION_SERVICE, SystemNotificationService.class);
        			// Se registra la notificación asociada al registro del nodo.
        			sysNotificationService.registerSystemNotification(INotificationTypeIds.ID_NODE_NOTIFICATION_TYPE, INotificationOriginIds.ID_REST_SERVICE_NODE_ORIGIN, INotificationPriority.ID_NOTIFICATION_PRIORITY_NORMAL, msg);
        		}
        		catch (final Exception e) {
        			throw new MonitorizaRestException(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG009, new Object[ ] { INodeRestService.SERVICENAME_REGISTER_NODE }), e);
        		}
    		}
    		
    		result.setDescription(msg);
			result.setStatus(status);
			result.setDateTime(new DateString(Calendar.getInstance().getTime()));
    		
		}

		return result;
	}

	/**
	 * Method that checks required parameters for {@link es.gob.monitoriza.rest.services.NodeRestService#registerNode(String, String, String, String)} method.
	 * @param nodeName Name of the node
	 * @param nodeHost Host of the node.
	 * @param nodePort Port of the node.
	 * @param spieSelected String that represents which SPIE services are being monitored for the node.
	 * @return {@link String} with the parameter that not are correctly defined, otherwise <code>null</code>.
	 */
	private String checkParamsRegisterNode(final String nodeName, final String nodeHost, final String nodePort, final String nodeType, final Boolean nodeSecure, final Set<String> spieSelected) {

		final StringBuffer result = new StringBuffer();
		result.append(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG003, new Object[ ] { INodeRestService.SERVICENAME_REGISTER_NODE }));
		boolean checkError = false;

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(nodeName)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(INodeRestService.PARAM_NODE_NAME);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(nodeHost)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(INodeRestService.PARAM_NODE_HOST);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(nodeType)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(INodeRestService.PARAM_NODE_TYPE);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		// Check received parameters
		if (spieSelected == null || spieSelected.isEmpty()) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(INodeRestService.PARAM_SPIE_SELECTED);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		if (checkError) {
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * Method that checks required parameters for {@link es.gob.monitoriza.rest.services.NodeRestService#unRegisterNode(String)} method.
	 * @param nodeName Name of the node
	 * @return {@link String} with the parameter that not are correctly defined, otherwise <code>null</code>.
	 */
	private String checkParamsUnRegisterNode(final String nodeName) {

		final StringBuffer result = new StringBuffer();
		result.append(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG003, new Object[ ] { INodeRestService.SERVICENAME_UNREGISTER_NODE }));
		boolean checkError = false;

		// Check received parameters
		if (UtilsStringChar.isNullOrEmptyTrim(nodeName)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(INodeRestService.PARAM_NODE_NAME);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		if (checkError) {
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * Method that checks if the value for the parameter {@link es.gob.monitoriza.rest.services.INodeRestService#PARAM_NODE_TYPE} is valid.
	 * @param nodeType Platform type of the node
	 * @return {@link String} with the parameter value that not are correctly defined, otherwise <code>null</code>.
	 */
	private String checkPlatform(final String nodeType) {
		final StringBuffer result = new StringBuffer();
		result.append(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG004, new Object[ ] { INodeRestService.SERVICENAME_REGISTER_NODE }));
		boolean checkError = false;

		// Check platform value
		if (!GeneralConstants.PARAMETER_AFIRMA.equalsIgnoreCase(nodeType) && !GeneralConstants.PARAMETER_TSA.equalsIgnoreCase(nodeType)) {
			checkError = true;
			result.append(UtilsStringChar.EMPTY_STRING);
			result.append(UtilsStringChar.SYMBOL_OPEN_BRACKET_STRING);
			result.append(nodeType);
			result.append(UtilsStringChar.SYMBOL_CLOSE_BRACKET_STRING);
		}

		if (checkError) {
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * Method that constructs the node to register.
	 * @param nodeName String that represents the name of the node.
	 * @param nodeHost String that represents the host of the node address.
	 * @param nodePort String that represents the port of the node address.
	 * @param spieSelected String that represents which SPIE services are being monitored for the node.
	 * @return {@link Object} that represents the node to register.
	 */
	private NodeMonitoriza prepareNodeToRegister(final String nodeName, final String nodeHost, final String nodePort, final String nodeType, final Boolean nodeSecure, final Set<String> spieSelected) {

		final NodeMonitoriza node = new NodeMonitoriza();

		node.setName(nodeName);
		node.setHost(nodeHost);
		node.setPort(nodePort);
		node.setIsSecure(nodeSecure);

		CPlatformType type = null;

		// Se establece el tipo de plataforma asociada al nodo
		if (GeneralConstants.PARAMETER_AFIRMA.equalsIgnoreCase(nodeType)) {
			type = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.PLATFORM_SERVICE, PlatformService.class).getPlatformTypeById(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		} else {
			type = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.PLATFORM_SERVICE, PlatformService.class).getPlatformTypeById(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		}

		node.setNodeType(type);

		// Se inicializan las comprobaciones sobre los SPIE.
		node.setCheckAfirma(Boolean.FALSE);
		node.setCheckEmergencyDB(Boolean.FALSE);
		node.setCheckHsm(Boolean.FALSE);
		node.setCheckServices(Boolean.FALSE);
		node.setCheckTsa(Boolean.FALSE);
		node.setCheckValidMethod(Boolean.FALSE);

		// Preparamos la ejecución por reflexión, de los métodos
		// de comprobación de SPIEs para el nodo a registrar.
		final Iterator<String> itSpie = spieSelected.iterator();
		String check = null;

		// Se recorren los SPIEs para el nodo, pasados como argumento y representados por
		// el nobre del método que establece el valor.
		while (itSpie.hasNext()) {

			// Se obtiene el nombre de la propiedad de comprobación SPIE del nodo "checkXXX".
			check = itSpie.next();

			try {
				// Se invoca el método setter correspondiente "setCheckXXX(true)".
				invokeSetter(node, check, Boolean.TRUE);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
				// No ha sido posible ejecutar el método. Se ignora el argumento.
				LOGGER.error(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG002, new Object[ ] { nodeName, check }));
			}
		}

		return node;
	}


	/**
	 * Invoke by reflection the setter method of a property
	 * @param obj Object with the property whose value its being setting.
	 * @param propertyName Name of the property.
	 * @param variableValue Value to set.
	 * @throws IntrospectionException If the method fails.
	 * @throws IllegalAccessException If the method fails.
	 * @throws IllegalArgumentException If the method fails.
	 * @throws InvocationTargetException If the method fails.
	 */
	private void invokeSetter(final Object obj, final String propertyName, final Object variableValue) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PropertyDescriptor pd;

		pd = new PropertyDescriptor(propertyName, obj.getClass());
		final Method setter = pd.getWriteMethod();
		setter.invoke(obj, variableValue);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.rest.services.INodeRestService#unRegisterNode(java.lang.String)
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Restful needs not final access methods.
//	@Override
//	@POST
//	@Path("/unRegisterNode")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public NodeRestStatusResponse unRegisterNode(@FormParam(PARAM_NODE_NAME) String nodeName) throws MonitorizaRestException {
//		// CHECKSTYLE:ON
//
//		// Indicamos la recepción del servicio junto con los parámetros de
//		// entrada.
//		LOGGER.info(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG007, new Object[ ] { nodeName }));
//
//		// Se crea el objecto que representa el resultado de la operación.
//		NodeRestStatusResponse result = null;
//
//		// Inicialmente consideramos que todo es OK para proceder.
//		boolean allIsOk = Boolean.TRUE;
//
//		// Comprobamos los parámetros obligatorios de entrada.
//		String resultCheckParams = checkParamsUnRegisterNode(nodeName);
//		if (resultCheckParams != null) {
//			allIsOk = Boolean.FALSE;
//			LOGGER.error(resultCheckParams);
//			result = new NodeRestStatusResponse();
//			result.setStatus(INodeRestServiceStatus.STATUS_ERROR_INPUT_PARAMETERS);
//			result.setDescription(resultCheckParams);
//			result.setDateTime(LocalDateTime.now());
//		}
//
//		if (allIsOk) {
//
//			// Se busca el nodo a des-registrar.
//			NodeMonitoriza node = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.NODE_MONITORIZA_SERVICE, NodeMonitorizaService.class).getNodeByName(nodeName);
//
//			if (node == null) {
//				allIsOk = Boolean.FALSE;
//				LOGGER.error(resultCheckParams);
//				result = new NodeRestStatusResponse();
//				result.setStatus(INodeRestServiceStatus.STATUS_ERROR_NODE_NOT_FOUND);
//				result.setDescription(resultCheckParams);
//				result.setDateTime(LocalDateTime.now());
//
//			} else {
//
//				node.setActive(Boolean.FALSE);
//    			String msg = Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG008, new Object[ ] { nodeName });
//    			Integer status = INodeRestServiceStatus.STATUS_NODE_UNREGISTER;
//
//    			try {
//        			// Se registra el nodo, ya sea desde cero o activándolo de nuevo.
//        			ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.NODE_MONITORIZA_SERVICE, NodeMonitorizaService.class).saveNode(node);
//        			result = new NodeRestStatusResponse();
//        			result.setDescription(msg);
//        			result.setStatus(status);
//        			result.setDateTime(LocalDateTime.now());
//
//        			ISystemNotificationService sysNotificationService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SYSTEM_NOTIFICATION_SERVICE, SystemNotificationService.class);
//
//        			// Se registra la notificación asociada al registro del nodo.
//        			sysNotificationService.registerSystemNotification(INotificationTypeIds.ID_NODE_NOTIFICATION_TYPE, msg);
//
//        		}
//        		catch (Exception e) {
//        			throw new MonitorizaRestException(Language.getFormatResRestGeneralMonitoriza(IRestGeneralLogMessages.REST_LOG009, new Object[ ] { INodeRestService.SERVICENAME_UNREGISTER_NODE }), e);
//        		}
//
//			}
//		}
//
//		return result;
//	}


}