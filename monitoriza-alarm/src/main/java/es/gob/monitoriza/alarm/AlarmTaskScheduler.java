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
 * <b>File:</b><p>es.gob.monitoriza.alarm.AlarmTaskScheduler.java.</p>
 * <b>Description:</b><p> Class that represents the scheduler for the tasks object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 30/01/2019.
 */
package es.gob.monitoriza.alarm;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import es.gob.monitoriza.alarm.mail.MailService;
import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.CipherException;
import es.gob.monitoriza.i18n.IAlarmLogMessages;
import es.gob.monitoriza.i18n.IAlarmMailText;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.service.impl.ConfServerMailService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.AESCipher;
import es.gob.monitoriza.utilidades.UtilsGrayLog;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that represents the scheduler for the tasks object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.7, 30/01/2019.
 */
public class AlarmTaskScheduler {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Method that generates the email info necessary to send the alarm and then send it.
	 * 
	 * @param alarmsToSend List of alarm to sent.
	 */
	public static void sendAlarm(List<AlarmMail> alarmsToSend) {
				
		ConfServerMail serverMail = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.CONF_SERVER_MAIL_SERVICE, ConfServerMailService.class).getAllConfServerMail();
		    		
		if (serverMail != null && isMailServerValid(serverMail)) {
    		    		
    		// Si la lista de alarmas no esta vacía...
    		if (alarmsToSend != null && alarmsToSend.size() > 0) {
    			// Obtenemos el nombre del servicio
    			String issuer = serverMail.getIssuerMail();
    			String host = serverMail.getHostMail();
    			Long port = serverMail.getPortMail();
    			Boolean authentication = serverMail.getAuthenticationMail();
    			Boolean tls = serverMail.getTslMail();
    			String user = serverMail.getUserMail();
    			String password = null;
    			
    			if (serverMail.getPasswordMail() != null) {
    				
    				try {
    					password = new String(AESCipher.getInstance().decryptMessage(serverMail.getPasswordMail()));
    				} catch (CipherException e) {
    					LOGGER.error(Language.getResAlarmMonitoriza(IAlarmLogMessages.ERRORALAMR004), e);
    				}
    			}
    			
    			String subject = UtilsStringChar.EMPTY_STRING;
    			String body = UtilsStringChar.EMPTY_STRING;
    			
    			if (alarmsToSend.size() == NumberConstants.NUM1) {
    				subject = Language.getResAlarmMonitoriza(IAlarmMailText.SUBJECT_MAIL_MONITORIZA).concat(UtilsStringChar.SPECIAL_BLANK_SPACE_STRING).concat(alarmsToSend.get(0).getSubject());
    				body = alarmsToSend.get(0).getBody();
    			} else if (alarmsToSend.size() > NumberConstants.NUM1){
    				subject = Language.getResAlarmMonitoriza(IAlarmMailText.SUBJECT_MAIL_MONITORIZA).concat(UtilsStringChar.SPECIAL_BLANK_SPACE_STRING).concat(Language.getResAlarmMonitoriza(IAlarmMailText.SUMMARY_ALARM_SUBJECT_MAIL)).concat(UtilsStringChar.SPECIAL_BLANK_SPACE_STRING).concat(alarmsToSend.get(0).getSubject());
    				body = generateSummaryBody(alarmsToSend);
    			}

      			MailService ms = new MailService(alarmsToSend.get(0).getAddresses(), issuer, host, port.intValue(), authentication, tls, user, password, subject.toString(), body.toString());
    			
    			LOGGER.info("Mensaje enviado: \nSubject: "+ subject+"\nBody: "+ body +"\nNúmero de alarmas: " + alarmsToSend.size());
    						
    			ms.send();
    		} 
		} else {
			String msg = Language.getResAlarmMonitoriza(IAlarmLogMessages.ERRORALAMR005);
			LOGGER.error(msg);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_MAIL_SERVER_CONFIG, msg);
		}

	}
		
	/**
	 * Method that manages the block process of an alarm.
	 * 
	 * @param serviceIdentifier Name and status of the service to block.
	 * @param blockAlarmTime Time to keep blocked the alarm.
	 */
	public static void startBlockAlarm(String alarmIdentifier, long blockAlarmTime) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable sender = new Runnable() {

			public void run() {
				List<AlarmMail> alarms = AlarmTaskManager.getAlarmStack(alarmIdentifier);
				
				// Al haber transcurrido el tiempo de bloqueo, desbloqueamos este tipo de alarma
				AlarmTaskManager.removeBlockedAlarm(alarmIdentifier);
				// Si existen alarmas almacenadas para su envío, las
				// enviamos.
				LOGGER.info(Language.getFormatResAlarmMonitoriza(IAlarmMailText.SUMMARY_ALARM_SENT, new Object [] {alarmIdentifier}));
				sendAlarm(alarms);
				// Vaciamos la pila de alarmas
				AlarmTaskManager.removeAlarmStack(alarmIdentifier);
				
				scheduler.shutdown();
								
				LOGGER.info(Language.getFormatResAlarmMonitoriza(IAlarmLogMessages.ALARM001, new Object [] {alarmIdentifier}));
			}
		};
				
		scheduler.schedule(sender, blockAlarmTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * Method that generates the body for alarms summary mails.
	 * 
	 * @param alarmsToSent list of alarms that are part of the mail to send.
	 * @return a string which represents the body of the mail.
	 */
	private static String generateSummaryBody(List<AlarmMail> alarmsToSent) {
		StringBuilder body = new StringBuilder();
		body.append(Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY, new Object[]{alarmsToSent.size()}));
		for(AlarmMail alarm : alarmsToSent){
			body.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(alarm.getDateOfCreation().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(alarm.getBody());
		}
		return body.toString();
	}
	
	/**
	 * Method that generate each row (per alarm) for summary alarms.
	 * @param alarm Alarm object with the alarm information.
	 * @return String that represents the text of the summary body row.
	 */
//	private static String generateBodyRowVip(final Alarm alarm) {
//				
//		final String tiempoMedioServicio =  alarm.getUmbralDegradado() != null? alarm.getUmbralDegradado().toString(): null;
//		
//		String body = null;
//				
//		switch (alarm.getServiceStatus()) {
//			case ServiceStatusConstants.DEGRADADO:
//				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY_ROW_DEGRADED, new Object[ ] { alarm.getAvgTime(), tiempoMedioServicio });
//				break;
//			case ServiceStatusConstants.CAIDO:
//				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY_ROW_LOST, new Object[ ] { });
//				break;
//			default:
//				break;
//		}	
//		
//		return body;
//	}
	
	
	private static Boolean isMailServerValid(final ConfServerMail serverMail) {
		
		return (serverMail.getHostMail() != null &&
				serverMail.getPortMail() != null &&
				(serverMail.getAuthenticationMail() && serverMail.getUserMail() != null) || !serverMail.getAuthenticationMail());
				
			
		
	}

}
