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
 * <b>File:</b><p>es.gob.monitoriza.alarm.AlarmTaskManager.java.</p>
 * <b>Description:</b><p> Class that defines the methods in charge of send alarms or store it according to the defined block time.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 30/01/2019.
 */
package es.gob.monitoriza.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that defines the methods in charge of send alarms or store it according to the defined block time.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 30/01/2019.
 */
public class AlarmTaskManager {

	/**
	 * Attribute that represents the list of blocked alarms (identified by their service name and their service status).
	 */
	private static List<String> blockedAlarms = new ArrayList<String>();

	/**
	 * Attribute that contains the list of non-sent alarms ordered by service name. 
	 */
	private static Map<String, List<AlarmMail>> alarmStack = new HashMap<String, List<AlarmMail>>();

	/**
	 * Method that send the alarm through email.
	 * 
	 * @param alarm Alarm to send.
	 */
	public static void sendAlarm(AlarmMail alarm) {
		if (alarm != null) {
			String serviceName = alarm.getServiceName();
			String serviceStatus = alarm.getServiceStatus();
			String alarmIdentifier = serviceName + UtilsStringChar.SYMBOL_UNDERSCORE_STRING + serviceStatus;
			String[] serviceIdentifier = new String[]{serviceName, serviceStatus};
			// Si el tipo de servicio no esta bloqueado...
			if (!blockedAlarms.contains(alarmIdentifier)) {
				// Comprobamos que no haya ninguna alarma pendiente de ser
				// enviada para el servicio seleccionado. En caso de haberla, la
				// recuperamos.
				List<AlarmMail> alarmsToSend = new ArrayList<AlarmMail>();
				if (alarmStack.containsKey(alarmIdentifier)) {
					// Obtenemos la lista de alarmas almacenadas para el servicio concreto.
					alarmsToSend = alarmStack.get(alarmIdentifier);
					// Vaciamos la pila referente al servicio.
					removeAlarmStack(alarmIdentifier);
				}
				alarmsToSend.add(alarm);
				// Recuperamos el tiempo de bloqueo del tipo de alarma
				// seleccionado.
				long blockAlarmTime = alarm.getBlockedTime();
				// Enviamos la alarma.
				AlarmTaskScheduler.sendAlarm(alarmsToSend);
				// Bloqueamos la alarma para que no se vuelva a enviar otra del
				// mismo tipo hasta pasado el tiempo de bloqueo.
				blockedAlarms.add(alarmIdentifier);
				AlarmTaskScheduler.startBlockAlarm(alarmIdentifier, blockAlarmTime);

			} else {
				// Si la alarma está bloqueada, la añadimos a la lista de
				// pendientes de envio.
				if(alarmStack.containsKey(alarmIdentifier)){
					List<AlarmMail> alarms = alarmStack.get(alarmIdentifier);
					alarms.add(alarm);
					alarmStack.put(alarmIdentifier, alarms);
				} else {
					List<AlarmMail> alarms = new ArrayList<AlarmMail>();
					alarms.add(alarm);
					alarmStack.put(alarmIdentifier, alarms);
				}
			}
		}
	}
	
	/**
	 * Method that gets the alarms list of a given service from the stack. 
	 * 
	 * @param alarmIdentifier Name of the service to search.
	 * @return a list with the alarm associated to the service.
	 */
	public static List<AlarmMail> getAlarmStack(String alarmIdentifier){
		return alarmStack.get(alarmIdentifier);
	}
	
	/**
	 * Method that remove from the stack of blocked alarm a given alarm.
	 * 
	 * @param alarmIdentifier Name of the service associate to the alarm.
	 */
	public static void removeBlockedAlarm(String alarmIdentifier){
		blockedAlarms.remove(alarmIdentifier);
	}

	/**
	 * Method that remove the set of alarms associated to a service.
	 * 
	 * @param alarmIdentifier identifier of the service to match.
	 */
	public static void removeAlarmStack(String alarmIdentifier) {
		alarmStack.remove(alarmIdentifier);
	}

	/**
	 * Method that remove the set of alarms associated to a service.
	 * 
	 * @param serviceIdentifier Name and status of the service to match given as a array.
	 */
//	public static void removeAlarmStack(String alarmIdentifier){
//		//String id = serviceIdentifier[0] + UtilsStringChar.SYMBOL_UNDERSCORE_STRING + serviceIdentifier[1];
//		removeAlarmStack(alarmIdentifier);
//	}
}
