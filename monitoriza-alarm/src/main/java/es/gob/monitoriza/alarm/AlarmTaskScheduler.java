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
 * @version 1.4, 14/01/2019.
 */
package es.gob.monitoriza.alarm;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import es.gob.monitoriza.alarm.mail.MailService;
import es.gob.monitoriza.alarm.types.Alarm;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.IAlarmLogMessages;
import es.gob.monitoriza.i18n.IAlarmMailText;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.service.impl.ConfServerMailService;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.GeneralUtils;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that represents the scheduler for the tasks object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 14/01/2019.
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
	public static void sendAlarm(List<Alarm> alarmsToSend) {
		
		ConfServerMailService serverMailService = ApplicationContextProvider.getApplicationContext().getBean("serverMailService", ConfServerMailService.class);
		
		ConfServerMail serverMail = serverMailService.getAllConfServerMail();
		
		List<String> destinations = new ArrayList<String>();
		// Si la lista de alarmas no esta vacía...
		if (alarmsToSend != null && alarmsToSend.size() > 0) {
			// Obtenemos el nombre del servicio
			String serviceName = alarmsToSend.get(0).getServiceName();
			// Obtenemos el identificador de la alarma (nombre del servicio y el estado.
			//String[ ] serviceIdentifier = new String[ ] { alarmsToSend.get(0).getServiceName(), alarmsToSend.get(0).getServiceStatus() };
			String issuer = serverMail.getIssuerMail();
			String host = serverMail.getHostMail();
			Long port = serverMail.getPortMail();
			Boolean authentication = serverMail.getAuthenticationMail();
			Boolean tls = serverMail.getTslMail();
			String user = serverMail.getUserMail();
			String password = serverMail.getPasswordMail();
			String subject;
			String body;
			if (alarmsToSend.size() < 2) {
				subject = generateSubject(alarmsToSend.get(0));
				body = generateBody(alarmsToSend.get(0));
			} else {
				subject = Language.getResAlarmMonitoriza(IAlarmMailText.SUBJECT_MAIL_MONITORIZA).concat(" ").concat(Language.getFormatResAlarmMonitoriza(IAlarmMailText.SUMMARY_ALARM_SUBJECT_MAIL, new Object[ ] { serviceName }));
				body = generateSummaryBody(alarmsToSend);
			}
			//destinations = MailUtils.getListAddresseesForAlarm(serviceIdentifier);
			destinations = alarmsToSend.get(0).getAddresses();
			MailService ms = new MailService(destinations, issuer, host, port.intValue(), authentication, tls, user, password, subject.toString(), body.toString());
			
			LOGGER.info("Mensaje enviado: \nSubject: "+ subject+"\nBody: "+ body +"\nNúmero de alarmas: " + alarmsToSend.size());
						
			ms.send();
		}

	}

	/**
	 * Method that generate the subject for single alarm mails.
	 * @param alarm Alarm object with the alarm information.
	 * @return String that represents the text of the alarm mail subject.
	 */
	private static String generateSubject(final Alarm alarm) {
		
		StringBuffer subject = new StringBuffer();
		
		subject.append(Language.getResAlarmMonitoriza(IAlarmMailText.SUBJECT_MAIL_MONITORIZA)).append(" ").append(GeneralUtils.getSystemName(alarm.getServiceName().toLowerCase())).append(GeneralConstants.EN_DASH_WITH_SPACES).append(alarm.getServiceName()).append(GeneralConstants.BLANK).append(alarm.getServiceStatus());
		
		return subject.toString();
	}
	
	/**
	 * Method that generate the body for single alarm mails.
	 * @param alarm Alarm object with the alarm information.
	 * @return String that represents the text of the alarm mail body.
	 */
	private static String generateBody(final Alarm alarm) {
				
		final String systemService = GeneralUtils.getSystemName(alarm.getServiceName().toLowerCase()).concat(GeneralConstants.EN_DASH_WITH_SPACES).concat(alarm.getServiceWsdl());
		final String tiempoMedioServicio =  alarm.getUmbralDegradado() != null? alarm.getUmbralDegradado().toString(): null;
		
		String body = null;				
				
		switch (alarm.getServiceStatus()) {
			case ServiceStatusConstants.DEGRADADO:
				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_ALARM_DEGRADED, new Object[ ] { systemService, alarm.getAvgTime(), tiempoMedioServicio });
				break;
			case ServiceStatusConstants.CAIDO:
				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_ALARM_LOST, new Object[ ] { systemService });
				break;
			default:
				break;
		}	
		
		return body;
	}
	
	/**
	 * Method that generate each row (per alarm) for summary alarms.
	 * @param alarm Alarm object with the alarm information.
	 * @return String that represents the text of the summary body row.
	 */
	private static String generateBodyRow(final Alarm alarm) {
				
		final String tiempoMedioServicio =  alarm.getUmbralDegradado() != null? alarm.getUmbralDegradado().toString(): null;
		
		String body = null;
				
		switch (alarm.getServiceStatus()) {
			case ServiceStatusConstants.DEGRADADO:
				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY_ROW_DEGRADED, new Object[ ] { alarm.getAvgTime(), tiempoMedioServicio });
				break;
			case ServiceStatusConstants.CAIDO:
				body = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY_ROW_LOST, new Object[ ] { });
				break;
			default:
				break;
		}	
		
		return body;
	}

	/**
	 * Method that manages the block process of an alarm.
	 * 
	 * @param serviceIdentifier Name and status of the service to block.
	 * @param blockAlarmTime Time to keep blocked the alarm.
	 */
	public static void startBlockAlarm(String[ ] serviceIdentifier, long blockAlarmTime) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable sender = new Runnable() {

			public void run() {
				List<Alarm> alarms = AlarmTaskManager.getAlarmStack(serviceIdentifier);
				
				// Al haber transcurrido el tiempo de bloqueo, desbloqueamos este tipo de alarma
				AlarmTaskManager.removeBlockedAlarm(serviceIdentifier);
				// Si existen alarmas almacenadas para su envío, las
				// enviamos.
				LOGGER.info(Language.getFormatResAlarmMonitoriza(IAlarmMailText.SUMMARY_ALARM_SENT, new Object [] {serviceIdentifier}));
				sendAlarm(alarms);
				// Vaciamos la pila de alarmas
				AlarmTaskManager.removeAlarmStack(serviceIdentifier);
				
				scheduler.shutdown();
								
				LOGGER.info(Language.getFormatResAlarmMonitoriza(IAlarmLogMessages.ALARM001, new Object [] {serviceIdentifier}));
			}
		};
				
		scheduler.schedule(sender, blockAlarmTime, TimeUnit.MILLISECONDS);
	}

	public static void main(String[ ] args) {
		System.out.println(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_HOST));
		
	}

	/**
	 * Method that generates the body for alarms summary mails.
	 * 
	 * @param alarmsToSent list of alarms that are part of the mail to send.
	 * @return a string which represents the body of the mail.
	 */
	private static String generateSummaryBody(List<Alarm> alarmsToSent) {
		StringBuilder body = new StringBuilder();
		final String systemService = GeneralUtils.getSystemName(alarmsToSent.get(0).getServiceName().toLowerCase()).concat(GeneralConstants.EN_DASH_WITH_SPACES).concat(alarmsToSent.get(0).getServiceWsdl());
		body.append(Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_SUMMARY, new Object[]{alarmsToSent.size(), systemService, alarmsToSent.get(0).getServiceStatus().toUpperCase()}));
		for(Alarm alarm : alarmsToSent){
			body.append(GeneralConstants.LINE_FEED + alarm.getDateOfCreation().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + GeneralConstants.LINE_FEED + generateBodyRow(alarm));
		}
		return body.toString();
	}

}
