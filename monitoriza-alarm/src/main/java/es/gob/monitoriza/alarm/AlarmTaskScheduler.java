/* 
/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>org.monitoriza.alarm.AlarmTaskScheduler.java.</p>
 * <b>Description:</b><p> Class that represents the scheduler for the tasks object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package org.monitoriza.alarm;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import es.gob.monitoriza.alarm.mail.MailService;
import es.gob.monitoriza.alarm.types.Alarm;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.util.MailUtils;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that represents the scheduler for the tasks object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public class AlarmTaskScheduler {

	/**
	 * Method that generates the email info necessary to send the alarm and then send it.
	 * 
	 * @param alarmsToSent List of alarm to sent.
	 */
	public static void sendAlarm(List<Alarm> alarmsToSent) {
		List<String> destinations = new ArrayList<String>();
		// Si la lista de alarmas no esta vacía...
		if (alarmsToSent != null && alarmsToSent.size() > 0) {
			// Obtenemos el nombre del servicio
			String serviceName = alarmsToSent.get(0).getServiceName();
			// Obtenemos el identificador de la alarma (nombre del servicio y el estado.
			String[ ] serviceIdentifier = new String[ ] { alarmsToSent.get(0).getServiceName(), alarmsToSent.get(0).getServiceStatus() };
			String issuer = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_ISSUER);
			String host = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_HOST);
			String port = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_PORT);
			String authentication = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_AUTHENTICATION);
			String user = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_USER);
			String password = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_PASSWORD);
			String subject;
			String body;
			if (alarmsToSent.size() < 2) {
				String subjectMailKey = GeneralConstants.SERVICE + GeneralConstants.DOT + serviceName.toLowerCase() + GeneralConstants.DOT + GeneralConstants.ALARM + GeneralConstants.DOT + GeneralConstants.SUBJECT;
				subject = StaticMonitorizaProperties.getProperty(subjectMailKey);
				String bodyMailKey = GeneralConstants.SERVICE + GeneralConstants.DOT + serviceName.toLowerCase() + GeneralConstants.DOT + GeneralConstants.ALARM + GeneralConstants.DOT + GeneralConstants.BODY;
				body = StaticMonitorizaProperties.getProperty(bodyMailKey);
			} else {
				subject = Language.getFormatResMonitoriza(LogMessages.SUMMARY_ALARM_SUBJECT_MAIL, new Object[ ] { serviceName });
				body = generateSummaryBody(alarmsToSent);
			}
			destinations = MailUtils.getListAddresseesForAlarm(serviceIdentifier);
			MailService ms = new MailService(destinations, issuer, host, Integer.valueOf(port), Boolean.valueOf(authentication), user, password, subject, body);
			System.out.println("Mensaje enviado: \nSubject: "+ subject+"\nBody: "+ body +"\nNúmero de alarmas: " + alarmsToSent.size());
			ms.send();
		}

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
				// Si trascurrido el tiempo de bloqueo, no hay ninguna alarma
				// por enviar, quitamos el bloqueo.
				if (alarms == null || alarms.size() <= 0) {
					AlarmTaskManager.removeBlockedAlarm(serviceIdentifier);
				} else {
					// Si existen alarmas almacenadas para su envío, las
					// enviamos.
					sendAlarm(alarms);
					// Vaciamos la pila de alarmas
					AlarmTaskManager.removeAlarmStack(serviceIdentifier);
				}
				scheduler.shutdown();
				System.out.println("pool shutdowned");
			}
		};
		scheduler.schedule(sender, blockAlarmTime, TimeUnit.MILLISECONDS);
	}

	public static void main(String[ ] args) {
		System.out.println(StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_IP));
	}

	/**
	 * Method that generates the body for alarms summary mails.
	 * 
	 * @param alarmsToSent list of alarms that are part of the mail to send.
	 * @return a string which represents the body of the mail.
	 */
	private static String generateSummaryBody(List<Alarm> alarmsToSent) {
		StringBuilder body = new StringBuilder();
		body.append(Language.getFormatResMonitoriza(LogMessages.BODY_MAIL_SUMMARY, new Object[]{alarmsToSent.size(), alarmsToSent.get(0).getServiceName(), alarmsToSent.get(0).getServiceStatus()}));
		for(Alarm alarm : alarmsToSent){
			body.append(GeneralConstants.LINE_FEED + alarm.getDateOfCreation().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		}
		return body.toString();
	}

}
