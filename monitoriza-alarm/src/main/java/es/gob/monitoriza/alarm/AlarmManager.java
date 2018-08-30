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
 * @version 1.0, 24/01/2018.
 */
package es.gob.monitoriza.alarm;

import java.util.List;

import es.gob.monitoriza.alarm.types.Alarm;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.utilidades.GeneralUtils;

/** 
 * <p>Class that manages the alarms system of Monitoriz@.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 30/08/2018.
 */
public class AlarmManager {

	static {
		STATUS = GeneralUtils.getValuesOfConstants(ServiceStatusConstants.class, null);
		//SERVICES = StaticServicesManager.getServices();
	}
	private static final List<String> STATUS;
	//private static final List<ServiceDTO> SERVICES;

	/**
	 * Method that received a service name and a service status and it throws a new alarm.
	 * 
	 * @param serviceName Name of the service associated to the alarm.
	 * @param serviceStatus Status of the service associated to the alarm.
	 * @throws AlarmException if the input parameters are nor correct or something in the process fails.
	 */
	public static void throwNewAlarm(final ServiceDTO service, final String serviceStatus, final Long tiempoMedio) throws AlarmException {
		// Comprobamos que los parametros introducidos sean correctos.
		if (service == null || serviceStatus == null) {
			throw new AlarmException(LogMessages.ERROR_SERVICE_STATUS_OR_SERVICE_NAME_NULL);
		}
		if (!STATUS.contains(serviceStatus)) {
			throw new AlarmException(LogMessages.ERROR_SERVICE_STATUS_NOT_MATCH);
		}
//		if (!SERVICES.contains(service)) {
//			throw new AlarmException(LogMessages.ERROR_SERVICE_NAME_NOT_MATCH);
//		}
		// Creamos una nueva alarma.
		Alarm alarm = new Alarm(service.getServiceName(), serviceStatus, tiempoMedio);
		
		if (serviceStatus.equals(ServiceStatusConstants.DEGRADADO)) {
			alarm.setAddresses(service.getListMailDegraded());
		} else if (serviceStatus.equals(ServiceStatusConstants.CAIDO)) {
			alarm.setAddresses(service.getListMailDown());
		} else {
			throw new AlarmException(LogMessages.ERROR_SERVICE_STATUS_NOT_MATCH);
		}
		
		alarm.setBlockedTime(service.getBlockTimeAlarm());
		alarm.setServiceWsdl(service.getWsdl());
		alarm.setUmbralDegradado(service.getDegradedThreshold());
		
		// Enviamos la alarma al gestor de alarmas para que decida si hay que
		// enviarla o añadirla al resumen.
		AlarmTaskManager.sendAlarm(alarm);

	}
}
