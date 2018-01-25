/* 
/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>org.monitoriza.alarm.AlarmTaskManager.java.</p>
 * <b>Description:</b><p> Class that defines the methods in charge of send alarms or store it according to the defined block time.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package org.monitoriza.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.gob.monitoriza.alarm.types.Alarm;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that defines the methods in charge of send alarms or store it according to the defined block time.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public class AlarmTaskManager {

	/**
	 * Attribute that represents the list of blocked alarms (identified by their service name and their service status).
	 */
	private static List<String> blockedAlarms = new ArrayList<String>();

	/**
	 * Attribute that contains the list of non-sent alarms ordered by service name. 
	 */
	private static Map<String, List<Alarm>> alarmStack = new HashMap<String, List<Alarm>>();

	/**
	 * Method that send the alarm through email.
	 * 
	 * @param alarm Alarm to send.
	 */
	public static void sendAlarm(Alarm alarm) {
		if (alarm != null) {
			String serviceName = alarm.getServiceName();
			String serviceStatus = alarm.getServiceStatus();
			String AlarmIdentifier = serviceName+ GeneralConstants.SEPARATOR + serviceStatus;
			String[] serviceIdentifier = new String[]{serviceName, serviceStatus};
			// Si el tipo de servicio no esta bloqueado...
			if (!blockedAlarms.contains(AlarmIdentifier)) {
				// Comprobamos que no haya ninguna alarma pendiente de ser
				// enviada para el servicio seleccionado. En caso de haberla, la
				// recuperamos.
				List<Alarm> alarmsToSent = new ArrayList<Alarm>();
				if (alarmStack.containsKey(AlarmIdentifier)) {
					// Obtenemos la lista de alarmas almacenadas para el servicio concreto.
					alarmsToSent = alarmStack.get(AlarmIdentifier);
					// Vaciamos la pila referente al servicio.
					removeAlarmStack(AlarmIdentifier);
				}
				alarmsToSent.add(alarm);
				// Recuperamos el tiempo de bloqueo del tipo de alarma
				// seleccionado.
				long blockAlarmTime = getBlockTimeForService(serviceName);
				// Enviamos la alarma.
				AlarmTaskScheduler.sendAlarm(alarmsToSent);
				// Bloqueamos la alarma para que no se vuelva a enviar otra del
				// mismo tipo hasta pasado el tiempo de bloqueo.
				blockedAlarms.add(AlarmIdentifier);
				AlarmTaskScheduler.startBlockAlarm(serviceIdentifier, blockAlarmTime);

			} else {
				// Si la alarma está bloqueada, la añadimos a la lista de
				// pendientes de envio.
				if(alarmStack.containsKey(AlarmIdentifier)){
					List<Alarm> alarms = alarmStack.get(AlarmIdentifier);
					alarms.add(alarm);
					alarmStack.put(AlarmIdentifier, alarms);
				} else {
					List<Alarm> alarms = new ArrayList<Alarm>();
					alarms.add(alarm);
					alarmStack.put(AlarmIdentifier, alarms);
				}
			}
		}
	}

	/**
	 * Method that gets the block alarm time defined for the service in the static properties.
	 * 
	 * @param serviceName name of the service to search.
	 * @return the time to keep blocked the alarm.
	 */
	private static long getBlockTimeForService(String serviceName) {
		String propertyKey = GeneralConstants.SERVICE + GeneralConstants.DOT + serviceName.toLowerCase() + GeneralConstants.DOT + GeneralConstants.BLOCK_TIME_KEY;
		String blockAlarmTimeId = StaticMonitorizaProperties.getProperty(propertyKey);
		String time = StaticMonitorizaProperties.getProperty(blockAlarmTimeId);
		return Long.valueOf(time);
	}
	
	/**
	 * Method that gets the alarms list of a given service from the stack. 
	 * 
	 * @param serviceIdentifier Name of the service to search.
	 * @return a list with the alarm assiciated to the service.
	 */
	public static List<Alarm> getAlarmStack(String[] serviceIdentifier){
		return alarmStack.get(serviceIdentifier[0] + GeneralConstants.SEPARATOR + serviceIdentifier[1]);
	}
	
	/**
	 * Method that remove from the stack of blocked alarm a given alarm.
	 * 
	 * @param serviceIdentifier Name of the service associate to the alarm.
	 */
	public static void removeBlockedAlarm(String[] serviceIdentifier){
		blockedAlarms.remove(serviceIdentifier[0] + GeneralConstants.SEPARATOR + serviceIdentifier[1]);
	}

	/**
	 * Method that remove the set of alarms associated to a service.
	 * 
	 * @param serviceIdentifier identifier of the service to match.
	 */
	private static void removeAlarmStack(String serviceIdentifier) {
		alarmStack.remove(serviceIdentifier);
	}

	/**
	 * Method that remove the set of alarms associated to a service.
	 * 
	 * @param serviceIdentifier Name and status of the service to match given as a array.
	 */
	public static void removeAlarmStack(String[] serviceIdentifier){
		String id = serviceIdentifier[0] + GeneralConstants.SEPARATOR + serviceIdentifier[1];
		removeAlarmStack(id);
	}
}
