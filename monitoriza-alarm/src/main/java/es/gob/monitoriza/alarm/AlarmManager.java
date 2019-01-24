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
 * @version 1.4, 18/01/2019.
 */
package es.gob.monitoriza.alarm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.gob.monitoriza.alarm.types.Alarm;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.i18n.IAlarmLogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.service.impl.AlarmMonitorizaService;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.GeneralUtils;

/** 
 * <p>Class that manages the alarms system of Monitoriz@.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 18/01/2019.
 */
public class AlarmManager {

	static {
		STATUS = GeneralUtils.getValuesOfConstants(ServiceStatusConstants.class, null);
	}
	private static final List<String> STATUS;

	/**
	 * Method that received a service name and a service status and it throws a new alarm.
	 * 
	 * @param serviceName Name of the service associated to the alarm.
	 * @param serviceStatus Status of the service associated to the alarm.
	 * @throws AlarmException if the input parameters are nor correct or something in the process fails.
	 */
	public static void throwNewAlarm(final ConfigServiceDTO service, final String serviceStatus, final Long tiempoMedio) throws AlarmException {
		
		// Se obtiene el servicio de alarmas para cargar la configuración de bloqueo y direcciones.
		AlarmMonitorizaService alarmMonitorizaService = ApplicationContextProvider.getApplicationContext().getBean("alarmMonitorizaService", AlarmMonitorizaService.class);
		AlarmMonitoriza alarmMonitoriza = alarmMonitorizaService.getAlarmMonitorizaById(service.getIdAlarm());
				
		// Comprobamos que los parametros introducidos sean correctos.
		if (service == null || serviceStatus == null) {
			throw new AlarmException(IAlarmLogMessages.ERRORALAMR001);
		}
		if (!STATUS.contains(serviceStatus)) {
			throw new AlarmException(IAlarmLogMessages.ERRORALAMR002);
		}

		// Creamos una nueva alarma.
		Alarm alarm = new Alarm(service.getServiceName(), serviceStatus, tiempoMedio, service.getPlatform());
		
		if (serviceStatus.equals(ServiceStatusConstants.DEGRADADO)) {
			alarm.setAddresses(getAddressesFromAlarm(alarmMonitoriza.getEmailsDegraded()));
		} else if (serviceStatus.equals(ServiceStatusConstants.CAIDO)) {
			alarm.setAddresses(getAddressesFromAlarm(alarmMonitoriza.getEmailsDown()));
		} else {
			throw new AlarmException(IAlarmLogMessages.ERRORALAMR002);
		}
		
		alarm.setBlockedTime(alarmMonitoriza.getBlockedTime());
		alarm.setServiceUrl(getServiceConfigUrl(service));
		alarm.setUmbralDegradado(service.getDegradedThreshold());
		
		// Enviamos la alarma al gestor de alarmas para que decida si hay que
		// enviarla o añadirla al resumen.
		AlarmTaskManager.sendAlarm(alarm);

	}
	
	/**
	 * Method that retrieves the addresses from a MailMonitoriza object.
	 * @param mailSet Set of mail configuration objects of Monitoriz@
	 * @return List<String> that represents the mail addresses 
	 */
	private static List<String> getAddressesFromAlarm(final Set<MailMonitoriza> mailSet) {
		
		final List<String> mailList = new ArrayList<String>();
		
		Iterator<MailMonitoriza> mailIterator = mailSet.iterator();
		
		while (mailIterator.hasNext()) {
			
			mailList.add(mailIterator.next().getEmailAddress());
		}
		
		return mailList;
	}
	
	/**
	 * Method that returns the URL of the service.
	 * @param service Configuration information of the service.
	 * @return Complete URL of the service.
	 */
	private static String getServiceConfigUrl(final ConfigServiceDTO service) {
		
		String serviceUrl = null;
		
		switch (service.getServiceType().toLowerCase()) {
			
			case GeneralConstants.SOAP_SERVICE:
				serviceUrl = service.getSoapUrl().concat(service.getWsdl());
				break;
			case GeneralConstants.OCSP_SERVICE:
				serviceUrl = service.getBaseUrl().concat(service.getOcspContext());
				break;
			case GeneralConstants.RFC3161_SERVICE:
				serviceUrl = service.getBaseUrl().concat(service.getRfc3161Context());
				break;
			case GeneralConstants.HTTP_SERVICE:
				serviceUrl = service.getSoapUrl().concat(service.getWsdl());
				break;
		}
		
		return serviceUrl;
	}
}
