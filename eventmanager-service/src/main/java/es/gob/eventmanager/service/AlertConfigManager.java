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
 * <b>File:</b><p>es.gob.eventmanager.service.AlarmConfigManager.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.3, 01/12/2021.
 */
package es.gob.eventmanager.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.eventmanager.configuration.ManagerConfigurationServices;
import es.gob.eventmanager.constant.NumberConstants;
import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.3, 01/12/2021.
 */
public final class AlertConfigManager {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger("eventmanager-service");
	
	/**
	 * Attribute that contains the dates on which an alarm event occurs, this map will be used to count alarms.	 
	 */
	private static Map<Long, TreeSet<Date>> alarmsEvents = new HashMap<Long, TreeSet<Date>>();
	
	/**
	 * Attribute that contains all alarms indicating whether or not they are blocked.
	 */
	private static Map<Long, Boolean> alarmsBlocked = new LinkedHashMap<Long, Boolean>();
	
	/**
	 * Initialization of the class.
	 */
	static {
				
		try {
			
			List<AlertConfigMonitoriza> listaAlertConfig = ManagerConfigurationServices.getInstance().getEventManagerBO().getAllAlertConfig();
			
			for (AlertConfigMonitoriza alertConfig : listaAlertConfig) {
				// se inicializa el mapa con todas los tipos de alerta como no bloqueadas
				alarmsEvents.put(alertConfig.getIdAlertConfigMonitoriza(), new TreeSet<>());
				// se inicializa el mapa de eventos de alarmas para registrar cada momento de emision
				alarmsBlocked.put(alertConfig.getIdAlertConfigMonitoriza(), alertConfig.getBlockTime() == null ? false:true);
			}
		
		} catch (EventManagerException e) {
			
		}
	}
	
	/**
	 * Method that checks if a determinate alert is blocked.
	 * @param idAlertType Alert Type identifier.
	 * @return <code>true</code> if the alarm is blocked, otherwise <code>false</code>.
	 */
	public static Boolean isBlockedAlert(Long idAlertConfig) {
		// se comprueba en el mapa de alarmas si está bloqueada o no
		return alarmsBlocked.get(idAlertConfig);		

	}
		
	/**
	 * Unblock the specified alarm and save the state in the configuration.
	 * Also, adds a message in the summaries that includes this alarm, pointing
	 * about the unblocked alarm.
	 * Additionally stop the scheduler task that notify about the blocked alarm.
	 * @param alarmName Alarm name.
	 * @throws AlarmsException In case of some error saving the configuration.
	 */
	public static void unblockAlert(Long idAlertType, Long idApplication) {
		
		try {
			
			// Cargamos la configuracion de la alerta segun
			// el nombre de la aplicacion y el tipo de alerta
			AlertConfigMonitoriza alertConfigToUnblock = ManagerConfigurationServices.getInstance().getEventManagerBO().getAlertConfigByAlertTypeAndApplication(idAlertType, idApplication);			
			
			alertConfigToUnblock.setBlockTime(null);
			
			ManagerConfigurationServices.getInstance().getEventManagerBO().saveAlertConfig(alertConfigToUnblock);
			
			alarmsBlocked.put(alertConfigToUnblock.getIdAlertConfigMonitoriza(), false);
			alarmsEvents.put(alertConfigToUnblock.getIdAlertConfigMonitoriza(), new TreeSet<Date>());
			
		} catch (EventManagerException e) {
			LOGGER.warn(e.getMessage());
		}
		
//		try {
//			addUnblockedAlarmMessageInSummaries(alarmName, fechaEvento);
//		} catch (AlarmsException e) {
//			LOGGER.warn(e.getMessage());
//		}

	}
		
	/**
	 * Method that checks if the alert blocking time has passed.
	 * @param config {@link AlertConfigMonitoriza} that represents the configuration parameters of this alert.
	 * @param currentDate Current date.
	 * @return true, if the alert has to be unlocked.
	 */
	public static boolean checkIfUnblockAlert(AlertConfigMonitoriza config, Date currentDate) {
		// se obtiene el tiempo de bloqueo configurado
		boolean result = false;
		long timeBlock = 0L;
		// Se pasa a milisegundos para trabajar con currentDate.getTime()
		timeBlock = config.getBlockPeriod() * NumberConstants.NUM1000;
		
		if (timeBlock > 0) {
			// se obtiene la fecha en el que se bloqueó la alarma
			Long blockDate = config.getBlockTime().getTime();
			if (currentDate.getTime() - blockDate > timeBlock) {
				result = true;

			}
		}
		return result;
	}
	
	/**
	 * Method that registers an event of Alert in the BBDD to be able to count
	 * his appearances and thus determine if it must be blocked. Later it verifies
	 * that the number of alarms has not been exceeded by period stipulated in the
	 * configuration, in whose case the condition of the alarm changes to blocked.
	 * @param config {@link AlertConfigMonitoriza} that represents the configuration parameters of this alert.
	 * @param eventDate {@link Date} of the event.
	 * @return <code>true</code> if the alarm has been blocked, otherwise <code>false</code>.
	 * @throws EventManagerException 
	 */
	public static boolean checkBlockadeAndAlertCount(AlertConfigMonitoriza config, Date eventDate) throws EventManagerException {

		boolean result = false;
		boolean checkBlockade = true;

		// se añade la alarma en el mapa de eventos para el conteo
		if (alarmsEvents.get(config.getIdAlertConfigMonitoriza()) == null) {
			alarmsEvents.put(config.getIdAlertConfigMonitoriza(), new TreeSet<Date>());
		} 
		alarmsEvents.get(config.getIdAlertConfigMonitoriza()).add(eventDate);				

		// Si el sistema de bloqueo para la alarma se encuentra activo...
		boolean isActiveBlockSystem = false;
		
		isActiveBlockSystem = config.getAllowBlock();
		
		if (checkBlockade && isActiveBlockSystem) {

			result = checkBlockadeAndAlarmCountAux(config, eventDate);

		}		

		return result;

	}

	/**
	 * Auxiliar method to reduce de cyclomatic complexity.
	 * @param ca Alarms configuration manager.
	 * @param alarmName Name of the alarm.
	 * @param eventDate Event date.
	 * @return <code>true</code> if the alarm has been blocked, otherwise <code>false</code>.
	 * @throws EventManagerException 
	 */
	private static boolean checkBlockadeAndAlarmCountAux(AlertConfigMonitoriza config, Date eventDate) throws EventManagerException {

		boolean result = false;

		Long numAlarmsMax = config.getBlockCondition();
		Long numMinsMax = config.getBlockInterval() / NumberConstants.NUM60;
		
		TreeSet<Date> listEvents = alarmsEvents.get(config.getIdAlertConfigMonitoriza());

		if (listEvents.size() >= numAlarmsMax) {
			// se comprueba si desde el primer evento al último han pasado los
			// minutos máximos configurados
			if (hasExceededMaxMinutes(listEvents, numMinsMax)) {
				config.setBlockTime(new Date());
				
				ManagerConfigurationServices.getInstance().getEventManagerBO().saveAlertConfig(config);
				// se bloquea la alarma
				alarmsBlocked.put(config.getIdAlertConfigMonitoriza(), true);
				// se vacía la lista de fechas de eventos de la alarma, dejando
				// solo el último evento, para controlar el tiempo de bloqueo
				removeAlarmsEventBefore(listEvents, config.getIdAlertConfigMonitoriza(), eventDate);

				result = true;
			} else {
				// se elimina el primer elemento de la lista de fechas
				alarmsEvents.get(config.getIdAlertConfigMonitoriza()).remove(listEvents.first());
			}
		}

		return result;

	}

	/**
	 * Method that checks if the set maximum number of minutes has been exceeded.
	 * @param treeEvents TreeSet of events of an alarm.
	 * @param numMinsMax Maximum number of minutes allowed between events before blocking an alarm.
	 * @return true, if the maximum number of minutes has been exceeded. 
	 */
	private static boolean hasExceededMaxMinutes(TreeSet<Date> treeEvents, Long numMinsMax) {
		boolean result = false;
		Date lastEvent = (Date) treeEvents.last();
		Date firstEvent = (Date) treeEvents.first();
		int minLastEvent = (int) (lastEvent.getTime() / NumberConstants.NUM60000);
		int minFirstEvent = (int) (firstEvent.getTime() / NumberConstants.NUM60000);
		if ((minLastEvent - minFirstEvent) >= numMinsMax) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Method to remove those elements with a date before  the date passed by parameter.
	 * 
	 * @param treeEvents TreeSet with the dates of the events.
	 * @param alarmName Alarm name
	 * @param eventDate Date of the event
	 */
	private static void removeAlarmsEventBefore(TreeSet<Date> treeEvents, Long idAlertConfig, Date eventDate) {
		// se obtiene aquellos elementos menores a la fecha actual
		Set<Date> subSet = treeEvents.headSet(eventDate);

		// se eliminan del mapa, dejando solo constancia del último evento, para
		// controlar el tiempo de bloqueo.
		alarmsEvents.get(idAlertConfig).removeAll(subSet);
	}
	
	/**
	 * Adds the block status of a newly created alert configuration
	 * @param config
	 */
	public static void addNewBlockConfig(AlertConfigMonitoriza config) {
		
		alarmsBlocked.put(config.getIdAlertConfigMonitoriza(), config.getBlockTime() == null ? false:true);
	}


}
