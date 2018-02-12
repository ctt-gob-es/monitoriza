/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.configuration.impl.StaticServicesManage.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>19 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 19 ene. 2018.
 */
package es.gob.monitoriza.configuration.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.DTOService;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ services from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 19/01/2018.
 */
public class StaticServicesManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the key suffix for the service timer property. 
	 */
	private static final String SERVICE_TIMER_PROPERTY = "timer";

	/**
	 * Attribute that represents the key suffix for the service timeout property. 
	 */
	private static final String SERVICE_TIMEOUT_PROPERTY = "timeout";
	
	/**
	 * Attribute that represents the key suffix for the service wsdl property. 
	 */
	private static final String SERVICE_WSDL_PROPERTY = "wsdl";

	/**
	 * Attribute that represents the key suffix for the service wsdl property. 
	 */
	private static final String SERVICE_DEGRADED_THRESHOLD_PROPERTY = "degradedthreshold";
	
	/**
	 * Attribute that represents the key suffix for the service wsdl property. 
	 */
	private static final String SERVICE_LOST_THRESHOLD_PROPERTY = "lostthreshold";

	/**
	 * Method that gets the services configuration from persistence (database or static properties file)
	 * @return
	 */
	public static List<DTOService> getServices() {
		
		boolean addService;

		List<DTOService> services = new ArrayList<DTOService>();

		Set<Entry<Object, Object>> allProperties = StaticMonitorizaProperties.getAllProperties();

		Iterator<Entry<Object, Object>> itProp = allProperties.iterator();

		DTOService service = null;
		
		String basePath = StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);
		
		while (itProp.hasNext()) {

			Entry<Object, Object> pair = (Entry<Object, Object>) itProp.next();
			String key = (String) pair.getKey();
			addService = Boolean.TRUE;

			if (key.startsWith("service")) {

				String[ ] keyArray = key.split("\\.");

				try {

					String serviceId = keyArray[1];

					if (services.contains(new DTOService(serviceId))) {

						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setTimerId((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_TIMEOUT_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setTimeout(Long.parseLong((String)pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_WSDL_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setWsdl((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_DEGRADED_THRESHOLD_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setDegradedThreshold(Long.parseLong((String) pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_LOST_THRESHOLD_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setLostThreshold((String) pair.getValue());
						} 
												
					} else {

						service = new DTOService(serviceId);
						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							service.setTimerId((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_TIMEOUT_PROPERTY)) {
							service.setTimeout(Long.parseLong((String)pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_WSDL_PROPERTY)) {
							service.setWsdl((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_DEGRADED_THRESHOLD_PROPERTY)) {
							service.setDegradedThreshold(Long.parseLong((String) pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_LOST_THRESHOLD_PROPERTY)) {
							service.setLostThreshold((String) pair.getValue());
						} else {
							addService = Boolean.FALSE;
						}
						
						if (addService) {
							service.setDirectoryPath(basePath.concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(serviceId));
							
							if (serviceId.contains(GeneralConstants.RFC3161_SERVICE) || serviceId.contains(GeneralConstants.TIMESTAMP_SERVICE)) {
								service.setAfirmaService(Boolean.FALSE);
							} else {
								service.setAfirmaService(Boolean.TRUE);
							}
							
							services.add(service);
						}
					}
					
					

				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.error(Language.getFormatResMonitoriza(LogMessages.ERROR_PROPERTY_SERVICE, new Object[ ] { key }));
				}

			}

		}

		return services;
	}

	/**
	 * Method that gets the services  from persistence (database or static properties file)
	 * @param timerId The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	public static List<DTOService> getServicesByTimer(final String timerId) {

		final List<DTOService> servicesTimer = new ArrayList<DTOService>();

		Iterator<DTOService> itServices = getServices().iterator();

		while (itServices.hasNext()) {

			DTOService service = (DTOService) itServices.next();

			if (service.getTimerId().equals(timerId)) {

				servicesTimer.add(service);

			}

		}

		return servicesTimer;
	}

}
