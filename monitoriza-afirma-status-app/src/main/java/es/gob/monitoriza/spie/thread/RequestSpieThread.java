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
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.spie.thread;

import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.alarm.AlarmManager;
import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IAlarmMailText;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.Alarm;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SpieType;
import es.gob.monitoriza.persistence.configuration.model.utils.IAlarmIdConstants;
import es.gob.monitoriza.service.impl.AlarmService;
import es.gob.monitoriza.service.impl.DailySpieMonitoringService;
import es.gob.monitoriza.service.impl.SpieMonitoringConfigService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spie.html.IHtmlSpieResolver;
import es.gob.monitoriza.spie.html.impl.HtmlAvgResponseTimeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlEmergencyModeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlHsmConnResolver;
import es.gob.monitoriza.spie.html.impl.HtmlPlatformConnResolver;
import es.gob.monitoriza.spie.invoker.SpieInvoker;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.UtilsGrayLog;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that get the results of the SPIE services configured.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 30/01/2019.
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
	 * Attribute that represents . 
	 */
	private final transient Map<Long, RowStatusSpieDTO> spieHolder;
	
	/**
	 * Attribute that represents the singleton instance of the SPIE services manager. 
	 */
	private final transient SpieMonitoringConfigService spieMonitoringConfigService = ApplicationContextProvider.getApplicationContext().getBean("spieMonitoringConfigService", SpieMonitoringConfigService.class);
	
	/**
	 * Attribute that represents the singleton instance of the SPIE alarm service. 
	 */
	private final transient AlarmService alarmSpieService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.ALARM_SPIE_SERVICE, AlarmService.class);

	/**
	 * Constructor method for the class RequestSpieThread.java.
	 * @param spieHolderParam Reference to the Map that holds the current status for the processed SPIEs. 
	 * @param nodeParam the value for {@link #nodeUrl} to set 
	 * @param sslParam the value for {@link #ssl} to set
	 * 
	 */
	public RequestSpieThread(final Map<Long, RowStatusSpieDTO> spieHolderParam, final NodeMonitoriza nodeParam, final KeyStore sslParam ) {
		super();
		this.spieHolder = spieHolderParam;
		this.node = nodeParam;
		ssl = sslParam;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
				
		// ¿Se va a mantener map de runnig services para evitar repetir?

		// Obtención de la dirección a invocar
		final StringBuffer spieBaseAddress = new StringBuffer();
		spieBaseAddress.append(node.getIsSecure() ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP).append(UtilsStringChar.SYMBOL_COLON_STRING).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(node.getHost()).append(UtilsStringChar.SYMBOL_COLON_STRING).append(node.getPort());
		
		if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA)) {
			checkSpieAfirma(spieBaseAddress.toString());
		} else if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA)){
			checkSpieTsa(spieBaseAddress.toString());
		}
				
	}
	
	/**
	 * Method that checks which SPIEs are available for this @Firma node and executes the invoker.
	 * @param spieBaseAddress Node address: protocol://ip:port
	 */
	private void checkSpieAfirma(final String spieBaseAddress) {
		
		final ConfSpieDTO confSpie = spieMonitoringConfigService.getSpieConfiguration();
		SpieType spieType = null;
		RowStatusSpieDTO status = null;
		String htmlInvokerResult = null;
		IHtmlSpieResolver resolver = null;
		String msgAlarm = null;
			
		try {
			
		
    		if (node.getCheckHsm() != null && node.getCheckHsm()) {
    			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
    			resolver = new HtmlHsmConnResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    			// Se envía alarma si procede
    			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM002_AFIRMA_NO_HSM_CONNECTION).getDescription(), new Object[]{node.getName()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM002_AFIRMA_NO_HSM_CONNECTION, msgAlarm);
    		}
    		
    		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
    			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
    			resolver = new HtmlEmergencyModeResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    			// Se envía alarma si procede
    			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM005_AFIRMA_EMERGENCY_MODE).getDescription(), new Object[]{node.getName()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM005_AFIRMA_EMERGENCY_MODE, msgAlarm);
    		}
    		
    		if (node.getCheckTsa() != null && node.getCheckTsa()) {
    			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_TSA);
    			resolver = new HtmlPlatformConnResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    			// Se envía alarma si procede
    			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM001_AFIRMA_NO_TSA_CONNECTION).getDescription(), new Object[]{node.getName()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM001_AFIRMA_NO_TSA_CONNECTION, msgAlarm);
    		}
    		
    		htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
    		
    		if (node.getCheckServices() != null && node.getCheckServices()) {
    			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
    			final String htmlAvgResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
    			HtmlAvgResponseTimeResolver avgResolver = new HtmlAvgResponseTimeResolver(spieType.getSemaphoreErrorLevel());
    			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), avgResolver.solveHtmlResult(htmlAvgResult, confSpie), avgResolver.getDetailResults(), LocalDateTime.now());
    			// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    			// Se envía alarma si procede
    			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM004_AFIRMA_TRANS_ABOVE_MAX).getDescription(), new Object[]{node.getName(), confSpie.getPercentAccept()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM004_AFIRMA_TRANS_ABOVE_MAX, msgAlarm);
    		}
    		
		} catch (InvokerException ie) {
			
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS015, new Object[ ] { node.getName() }), ie);
			setAndPersistAllStatusError(spieBaseAddress);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM009_ERROR_NODE_CONNECTION).getDescription(), new Object[]{node.getName(), spieBaseAddress});
			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), SemaphoreEnum.RED.getId(), null, LocalDateTime.now());
			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM009_ERROR_NODE_CONNECTION, msgAlarm);		
						
		}
		
	}
	
	/**
	 * Method that checks which SPIEs are available for this TS@ node and executes the invoker.
	 * @param spieBaseAddress Node address: protocol://ip:port
	 */
	private void checkSpieTsa(final String spieBaseAddress) {

		final ConfSpieDTO confSpie = spieMonitoringConfigService.getSpieConfiguration();
		SpieType spieType = null;
		RowStatusSpieDTO status = null;
		String htmlInvokerResult = null;
		IHtmlSpieResolver resolver = null;
		String msgAlarm = null;
		
		try {

			if (node.getCheckHsm() != null && node.getCheckHsm()) {
				spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_HSM_TSA);
				resolver = new HtmlHsmConnResolver(spieType.getSemaphoreErrorLevel());
				htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
				status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
				// Se actualiza el mapa de resultados
				spieHolder.put(spieType.getIdSpieType(), status);
				// Se persiste el resultado
				saveDailySpieMonitoring(spieType.getTokenName(), status);
				// Se envía alarma si procede
				msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM006_TSA_NO_HSM_CONNECTION).getDescription(), new Object[]{node.getName()});   
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM006_TSA_NO_HSM_CONNECTION, msgAlarm);
			}

			if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
				spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_TSA);
				resolver = new HtmlEmergencyModeResolver(spieType.getSemaphoreErrorLevel());
				htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
				status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
				// Se actualiza el mapa de resultados
				spieHolder.put(spieType.getIdSpieType(), status);
				// Se persiste el resultado
				saveDailySpieMonitoring(spieType.getTokenName(), status);
				// Se envía alarma si procede
				msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM008_TSA_EMERGENCY_MODE).getDescription(), new Object[]{node.getName()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM008_TSA_EMERGENCY_MODE, msgAlarm);
			}

			if (node.getCheckAfirma() != null && node.getCheckAfirma()) {
				spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_AFIRMA);
				resolver = new HtmlPlatformConnResolver(spieType.getSemaphoreErrorLevel());
				htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
				status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
				// Se actualiza el mapa de resultados
				spieHolder.put(spieType.getIdSpieType(), status);
				// Se persiste el resultado
				saveDailySpieMonitoring(spieType.getTokenName(), status);
				// Se envía alarma si procede
				msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM007TSA_NO_AFIRMA_CONNECTION).getDescription(), new Object[]{node.getName()});
    			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM007TSA_NO_AFIRMA_CONNECTION, msgAlarm);
			}

		} catch (InvokerException ie) {
			
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS015, new Object[ ] { node.getName() }), ie);
			setAndPersistAllStatusError(spieBaseAddress);
			msgAlarm = Language.getFormatResAlarmMonitoriza(alarmSpieService.getAlarmById(IAlarmIdConstants.ALM009_ERROR_NODE_CONNECTION).getDescription(), new Object[]{node.getName(), spieBaseAddress});
			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), SemaphoreEnum.RED.getId(), null, LocalDateTime.now());
			checkStatusAndSendAlarmIfNecessary(status, IAlarmIdConstants.ALM009_ERROR_NODE_CONNECTION, msgAlarm);			
			
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
    			case 0:
    				name = SemaphoreEnum.GREEN.getName();
    				break;
    			case 1:
    				name = SemaphoreEnum.AMBER.getName();
    				break;
    			case 2:
    				name = SemaphoreEnum.RED.getName();
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
	 * Persist null status for all SPIE services when there is no response from the SPIE node.
	 * @param spieBaseAddress Base address of the SPIE node.
	 */
	private void setAndPersistAllStatusError(final String spieBaseAddress) {
		
		SpieType spieType = null;
		RowStatusSpieDTO status = null;
				
		if (node.getCheckHsm() != null && node.getCheckHsm()) {
			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
		
		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
		
		if (node.getCheckTsa() != null && node.getCheckTsa()) {
			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_CONN_TSA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
						
		if (node.getCheckServices() != null && node.getCheckServices()) {
			spieType = spieMonitoringConfigService.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
		
	}
	
	/**
	 * Sends a SPIE alarm if its necessary.
	 * @param spieStatus RowStatusSpieDTO with the status result information of the SPIE.
	 * @param idAlarm Alarm identifier.
	 * @param msgAlarm Message of the alarm to be sent in the body of the e-mail.
	 */
	private void checkStatusAndSendAlarmIfNecessary(final RowStatusSpieDTO spieStatus, final String idAlarm, final String msgAlarm)  {
				
		StringBuilder subjectAlarm = new StringBuilder();
		String estado = getSemaphoreNameById(spieStatus.getStatusValue());
						
		Alarm alarm = alarmSpieService.getAlarmById(idAlarm);
				
		// El estado es erróneo, se lanza alarma.
		if (!spieStatus.getStatusValue().equals(SemaphoreEnum.GREEN.getId()) && alarm != null && alarm.getActive()) {
			
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
	
}
