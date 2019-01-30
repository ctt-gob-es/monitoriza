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
 * <b>File:</b><p>org.monitoriza.alarm.AlarmManager.java.</p>
 * <b>Description:</b><p> Class that manages the alarms system of Monitoriz@.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 30/01/2019.
 */
package es.gob.monitoriza.alarm;

import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.i18n.IAlarmLogMessages;

/** 
 * <p>Class that manages the alarms system of Monitoriz@.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 30/01/2019.
 */
public class AlarmManager {


	/**
	 * Method that received a service name and a service status and it throws a new alarm.
	 * 
	 * @param serviceName Name of the service associated to the alarm.
	 * @param serviceStatus Status of the service associated to the alarm.
	 * @throws AlarmException if the input parameters are nor correct or something in the process fails.
	 */
	public static void throwNewAlarm(final AlarmMail alarm) throws AlarmException {
				
		// Comprobamos que los parametros introducidos sean correctos.
		if (alarm.getServiceName() == null || alarm.getServiceStatus() == null) {
			throw new AlarmException(IAlarmLogMessages.ERRORALAMR001);
		}
						
		// Enviamos la alarma al gestor de alarmas para que decida si hay que
		// enviarla o añadirla al resumen.
		AlarmTaskManager.sendAlarm(alarm);

	}
	
}
