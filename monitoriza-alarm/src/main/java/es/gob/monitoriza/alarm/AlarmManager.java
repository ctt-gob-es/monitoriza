/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

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
import es.gob.monitoriza.persistence.configuration.dto.DTOService;
import es.gob.monitoriza.utilidades.GeneralUtils;
import es.gob.monitoriza.utilidades.StaticServicesManager;

/** 
 * <p>Class that manages the alarms system of Monitoriz@.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public class AlarmManager {

	static {
		STATUS = GeneralUtils.getValuesOfConstants(ServiceStatusConstants.class, null);
		SERVICES = StaticServicesManager.getServices();
	}
	private static final List<String> STATUS;
	private static final List<DTOService> SERVICES;

	/**
	 * Method that received a service name and a service status and it throws a new alarm.
	 * 
	 * @param serviceName Name of the service associated to the alarm.
	 * @param serviceStatus Status of the service associated to the alarm.
	 * @throws AlarmException if the input parameters are nor correct or something in the process fails.
	 */
	public static void throwNewAlarm(String serviceName, String serviceStatus) throws AlarmException {
		// Comprobamos que los parametros introducidos sean correctos.
		if (serviceName == null || serviceStatus == null) {
			throw new AlarmException(LogMessages.ERROR_SERVICE_STATUS_OR_SERVICE_NAME_NULL);
		}
		if (!STATUS.contains(serviceStatus)) {
			throw new AlarmException(LogMessages.ERROR_SERVICE_STATUS_NOT_MATCH);
		}
		if (!SERVICES.contains(new DTOService(serviceName))) {
			throw new AlarmException(LogMessages.ERROR_SERVICE_NAME_NOT_MATCH);
		}
		// Creamos una nueva alarma.
		Alarm alarm = new Alarm(serviceName, serviceStatus);
		// Enviamos la alarma al gestor de alarmas para que decida si hay que
		// enviarla o añadirla al resumen.
		AlarmTaskManager.sendAlarm(alarm);

	}
}