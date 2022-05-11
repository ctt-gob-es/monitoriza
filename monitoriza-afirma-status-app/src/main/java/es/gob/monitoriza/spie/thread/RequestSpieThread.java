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
 * <b>File:</b><p>es.gob.monitoriza.spie.thread.RequestSpieThread.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.10, 11/05/2022.
 */
package es.gob.monitoriza.spie.thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.alarm.AlarmManager;
import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IAlarmLogMessages;
import es.gob.monitoriza.i18n.IAlarmMailText;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.Alarm;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SpieType;
import es.gob.monitoriza.persistence.configuration.model.utils.IAlarmIdConstants;
import es.gob.monitoriza.service.IAlarmService;
import es.gob.monitoriza.service.IMaintenanceServiceService;
import es.gob.monitoriza.service.ISpieMonitoringConfigService;
import es.gob.monitoriza.service.IStatusService;
import es.gob.monitoriza.service.impl.AlarmService;
import es.gob.monitoriza.service.impl.DailySpieMonitoringService;
import es.gob.monitoriza.service.impl.MaintenanceServiceService;
import es.gob.monitoriza.service.impl.SpieMonitoringConfigService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spie.html.AbstractHtmlSpieResolver;
import es.gob.monitoriza.spie.invoker.SpieInvoker;
import es.gob.monitoriza.spie.status.StatusSpieHolder;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.UtilsGrayLog;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that get the results of the SPIE services configured.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.10, 11/05/2022.
 */
public class RequestSpieThread implements Runnable {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the system truststore. 
	 */
	private static KeyStore ssl;
	
	/**
	 * Attribute that represents the node. 
	 */
	private final transient NodeMonitoriza node;
	
	/**
	 * Attribute that represents the base address for invoking SPIEs in the node.
	 */
	private final transient String spieBaseAddress;
	
	/**
	 * Constructor method for the class RequestSpieThread.java.
	 * @param nodeParam the value for {@link #nodeUrl} to set 
	 * @param sslParam the value for {@link #ssl} to set
	 * 
	 */
	public RequestSpieThread(final NodeMonitoriza nodeParam, final KeyStore sslParam ) {
		super();
		this.node = nodeParam;
		ssl = sslParam;
		final StringBuffer address = new StringBuffer();
		address.append(node.getIsSecure() ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP).append(UtilsStringChar.SYMBOL_COLON_STRING).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(node.getHost()).append(UtilsStringChar.SYMBOL_COLON_STRING).append(node.getPort());
		spieBaseAddress = address.toString();
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	
		if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA)) {
			checkSpieAfirma();
		} else if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA)){
			checkSpieTsa();
		}
				
	}
	
	/**
	 * Method that checks which SPIEs are available for this @Firma node and obtains the status result on each one.
	 */
	private void checkSpieAfirma() {

		// Se obtiene la configuración general de monitorización
		final ConfSpieDTO confSpie = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SPIE_MONITORING_CONFIG_SERVICE, SpieMonitoringConfigService.class).getSpieConfiguration();
		SpieType spieType = null;
		String msgAlarm = null;

		// Se obtiene el servicio de alarmas.
		IAlarmService alarmService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.ALARM_SPIE_SERVICE, AlarmService.class);
		// Se obtiene el servicio de monitorización SPIE.
		ISpieMonitoringConfigService spieService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SPIE_MONITORING_CONFIG_SERVICE, SpieMonitoringConfigService.class);
		Alarm alarm = null;

		// Se comprueban los SPIEs configurados para el nodo.
		// Preparamos el posible mensaje de alarma parametrizado por cada caso.
		// Se invoca el SPIE y se parsea el HTML de respuesta, obtienendo el estado.
		if (node.getCheckHsm() != null && node.getCheckHsm()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM002_AFIRMA_NO_HSM_CONNECTION);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM005_AFIRMA_EMERGENCY_MODE);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

		if (node.getCheckTsa() != null && node.getCheckTsa()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM001_AFIRMA_NO_TSA_CONNECTION);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_CONN_TSA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}
		
		if (node.getCheckValidMethod() != null && node.getCheckValidMethod()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM004_AFIRMA_TRANS_ABOVE_MAX);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName(), confSpie.getPercentAccept() });
			spieType = spieService.getSpieTypeById(SpieType.ID_VAL_METHODS);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

		if (node.getCheckServices() != null && node.getCheckServices()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM004_AFIRMA_TRANS_ABOVE_MAX);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName(), confSpie.getPercentAccept() });
			spieType = spieService.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

	}
	
	/**
	 * Method that checks which SPIEs are available for this TS@ node and obtains the status result on each one.
	 */
	private void checkSpieTsa() {

		SpieType spieType = null;
		String msgAlarm = null;

		// Se obtiene el servicio de alarmas.
		IAlarmService alarmService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.ALARM_SPIE_SERVICE, AlarmService.class);
		// Se obtiene el servicio de monitorización SPIE.
		ISpieMonitoringConfigService spieService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SPIE_MONITORING_CONFIG_SERVICE, SpieMonitoringConfigService.class);
		Alarm alarm = null;

		// Se comprueban los SPIEs configurados para el nodo.
		// Preparamos el posiblem mensaje de alarma parametrizado por cada caso.
		// Se invoca el SPIE y se parsea el HTML de respuesta, obtienendo el estado.
		if (node.getCheckHsm() != null && node.getCheckHsm()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM006_TSA_NO_HSM_CONNECTION);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_CONN_HSM_TSA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM008_TSA_EMERGENCY_MODE);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_TSA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

		if (node.getCheckAfirma() != null && node.getCheckAfirma()) {
			alarm = alarmService.getAlarmById(IAlarmIdConstants.ALM007TSA_NO_AFIRMA_CONNECTION);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarm.getDescription(), new Object[ ] { node.getName() });
			spieType = spieService.getSpieTypeById(SpieType.ID_CONN_AFIRMA);
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS017, new Object[] { node.getName(), spieType.getTokenName()}));
			resolveSpie(spieType, alarm, msgAlarm);

		}

	}
	
	/**
	 * Saves the SPIE status sample in persistence.
	 * @param service SPIE service type
	 * @param status Object that represents the status information of the SPIE
	 */
	private void saveDailySpieMonitoring(String service, RowStatusSpieDTO status) {
		
		DailySpieMonitorig daily = new DailySpieMonitorig();
		
		daily.setPlatform(status.getSystem());
		daily.setSamplingTime(status.getStatusUptodate());
		daily.setService(service);
		daily.setStatus(getSemaphoreNameById(status.getStatusValue()));
		daily.setNode(status.getNodeName());
		
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.DAILY_SPIE_MONITORING_SERVICE, DailySpieMonitoringService.class).saveDailySpieMonitoring(daily);
		} catch (DatabaseException e) {
			String msg = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS019);
			LOGGER.error(msg, e);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_STATUS_SPIE_SAVE, msg);
		}
		
	}
	
	/**
	 * Gets the semaphore name value by its identifier.
	 * @param id Semaphore identifier
	 * @return Semaphore name value
	 */
	private String getSemaphoreNameById(Integer id) {
		
		String name = null;
		
		if (id != null) {
    		switch (id) {
    			case NumberConstants.NUM0:
    				name = SemaphoreEnum.GREEN.getName();
    				break;
    			case NumberConstants.NUM2:
    				name = SemaphoreEnum.AMBER.getName();
    				break;
    			case NumberConstants.NUM3:
    				name = SemaphoreEnum.RED.getName();
    				break;
    			case NumberConstants.NUM4:
    				name = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS017, new Object[]{node.getName()});
    				break;	
    			default:
    				name = SemaphoreEnum.RED.getName();
    				break;
    		} 
				
		} else {
			name = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS017, new Object[]{node.getName()});
		}

		return name;
	}
	
	
	/**
	 * Sends a SPIE alarm if its necessary.
	 * @param spieStatus RowStatusSpieDTO with the status result information of the SPIE.
	 * @param alarm {@link Alarm} SPIE alarm to send if there is an error
	 * @param msgAlarm Parametrized message for the alarm
	 */
	private void checkStatusAndSendAlarmIfNecessary(final RowStatusSpieDTO spieStatus, final Alarm alarm, final String msgAlarm)  {
				
		StringBuilder subjectAlarm = new StringBuilder();
		String estado = getSemaphoreNameById(spieStatus.getStatusValue());
		Boolean redAndSendAlarm = !spieStatus.getStatusValue().equals(SemaphoreEnum.GREEN.getId()) && alarm != null && alarm.getActive() && alarm.getTimeBlock() != null;
		
		// El estado es erróneo, se lanza alarma.
		if (spieStatus.getStatusValue() == null || redAndSendAlarm) {
			
			subjectAlarm.append(Language.getFormatResAlarmMonitoriza(IAlarmMailText.SUBJECT_ALARM_SPIE, new Object[]{spieStatus.getSystem(), spieStatus.getNodeName(), spieStatus.getSpieService(), estado}));
						
			AlarmMail alarmMail = new AlarmMail(spieStatus.getSpieService(), estado, getListDestinationAddresses(alarm.getMails()), alarm.getTimeBlock(), spieStatus.getNodeName(), spieStatus.getNodeAddress(), subjectAlarm.toString(), msgAlarm);
								 
			try {
				AlarmManager.throwNewAlarm(alarmMail);
			} catch (AlarmException e) {
				LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS003, new Object[ ] { spieStatus.getSpieService(), estado }), e);
			}
						
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ALARM_SPIE_ERROR, msgAlarm);						
		}
		
	}
	
	/**
	 * Gets the list of destination addresses associated to the input alarm.
	 * @param addresses Set<MailMonitoriza> with the configured addresses for this alarm.
	 * @return the list of destination addresses associated to the input alarm. <code>null</code> if
	 * there is no addresses assigned to this alarm.
	 */
	public List<String> getListDestinationAddresses(Set<MailMonitoriza> addresses) {

		List<String> result = null;

		List<MailMonitoriza> mailList = new ArrayList<>(addresses);
		if (mailList != null && !mailList.isEmpty()) {

			result = new ArrayList<String>(mailList.size());
			for (MailMonitoriza mail: mailList) {
				result.add(mail.getEmailAddress());
			}

		}

		return result;

	}
	
	/**
	 * Gets the status of the SPIE.
	 * @param spieType Type of the SPIE
	 * @param alarm {@link Alarm} SPIE alarm to send if there is an error
	 * @param msgAlarm Parametrized message for the alarm
	 * @return {@link RowStatusSpieDTO} with the status of the SPIE
	 */
	private RowStatusSpieDTO resolveSpie(final SpieType spieType, final Alarm alarm, String msgAlarm) {
				
		// Se obtiene la clase de parseo HTML del tipo de SPIE por reflexión,
		// según la cadena de nombre de clase almacenada en persistencia.
		AbstractHtmlSpieResolver resolver = null;
		try {
			Class<?> cl = Class.forName(spieType.getResolverClass());
			Constructor<?> cons = cl.getConstructor(Integer.class);
			resolver = (AbstractHtmlSpieResolver) cons.newInstance(spieType.getSemaphoreErrorLevel());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS024, new Object[ ] { spieType.getTokenName() }), e);
		}
				
		String htmlInvokerResult = null;
		RowStatusSpieDTO status = null;
				
		String idStatus = IStatusService.getUniqueIdStatusSpie(node.getIdNode(), spieType.getIdSpieType());
		
		try {
							
			// Se invoca el SPIE.
			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
			
			// Si se ha obtenido clase de parseo, se parsea el resultado, obteniendo el estado.
			if (resolver != null) {
				status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SPIE_MONITORING_CONFIG_SERVICE, SpieMonitoringConfigService.class).getSpieConfiguration()), resolver.getAvgDetailResults(), resolver.getValMethodDetailResults(), LocalDateTime.now());
			}
			
			// Se actualiza el mapa de resultados usando el identificador obtenido.
			StatusSpieHolder.getInstance().getCurrentStatusHolder().put(idStatus, status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
			// Se persiste la información de mantenimiento del servicio SPIE
			manageMaintenanceService(status);
			// Se envía alarma si procede
			checkStatusAndSendAlarmIfNecessary(status, alarm, msgAlarm);
			
		} catch (InvokerException ie) {
			
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS023, new Object[ ] { node.getName() }), ie);
			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), SemaphoreEnum.OTHER.getId(), resolver.getAvgDetailResults(), resolver.getValMethodDetailResults(), LocalDateTime.now());
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
			// Se actualiza el mapa de resultados usando el identificador obtenido.
			StatusSpieHolder.getInstance().getCurrentStatusHolder().put(idStatus, status);
			// Se persiste la información de mantenimiento del servicio SPIE
			manageMaintenanceService(status);
			// Se envía la alarma correspondiente a nodo sin conexion
			checkStatusAndSendAlarmIfNecessary(status, alarm, Language.getFormatResAlarmMonitoriza(IAlarmLogMessages.ERRORALAMR006, new Object[]{node.getName(), spieBaseAddress}));		
		}
				
		return status;
	}
	
	/**
	 * Method that manage the maintenance status of this service.
	 * 
	 * @param status {@link RowStatusSpieDTO} with the obtained SPIE status information
	 */
	private void manageMaintenanceService(RowStatusSpieDTO status) {

		// Comprobar si es necesario añadir el servicio a la tabla de
		// mantenimiento
		IMaintenanceServiceService maintenanceService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.MAINTENANCE_SERVICE, MaintenanceServiceService.class);

		MaintenanceService maintenance = maintenanceService.getMaintenanceServiceByService(IStatusService.getUniqueNameStatusSpie(status.getNodeName(), status.getSpieService()));
		// Si el servicio no se ha añadio aún a la tabla de mantenimiento, se
		// persiste
		if (maintenance == null) {

			maintenance = new MaintenanceService();
			maintenance.setIsInMaintenance(Boolean.FALSE);
			// El nombre identificativo para el servicio SPIE estará compuesto:
			// NOMBRE_NODO_TIPO_SPIE
			maintenance.setService(IStatusService.getUniqueNameStatusSpie(status.getNodeName(), status.getSpieService()));
			maintenance.setStatusOrigin(status.getStatusValue());
			maintenanceService.saveMaintenanceService(maintenance);

		} else {

			// Si el estado del servicio ha cambiado desde la última vez,
			// y estaba marcado, se elimina la marca
			if (maintenance.getIsInMaintenance() && !Objects.equals(status.getStatusValue(), maintenance.getStatusOrigin())) {

					maintenance.setIsInMaintenance(Boolean.FALSE);
					maintenanceService.saveMaintenanceService(maintenance);

				}

			}
		}

	
}
