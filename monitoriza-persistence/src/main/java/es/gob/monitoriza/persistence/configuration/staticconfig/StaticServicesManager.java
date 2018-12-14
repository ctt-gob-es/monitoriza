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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.StaticServicesManage.java.</p>
 * <b>Description:</b>
 * <p>Class that manages the configuration of the @firma/ts@ services from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>19 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.staticconfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.IPersistenceLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ services from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 28/10/2018.
 */
public final class StaticServicesManager {

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
	 * Constructor method for the class StaticServicesManager.java. 
	 */
	private StaticServicesManager(){
		
	}

	/**
	 * Method that gets the services configuration from persistence (database or static properties file).
	 * @return List<ServiceDTO>
	 */
	public static List<ConfigServiceDTO> getServices() {
		
		boolean addService;

		List<ConfigServiceDTO> services = new ArrayList<ConfigServiceDTO>();

		Set<Entry<Object, Object>> allProperties = StaticMonitorizaProperties.getAllProperties();

		Iterator<Entry<Object, Object>> itProp = allProperties.iterator();

		ConfigServiceDTO service = null;
		
		String basePath = StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);
		
		while (itProp.hasNext()) {

			Entry<Object, Object> pair = (Entry<Object, Object>) itProp.next();
			String key = (String) pair.getKey();
			addService = Boolean.TRUE;

			if (key.startsWith("service")) {

				String[ ] keyArray = key.split("\\.");

				try {

					String serviceId = keyArray[1];

					if (services.contains(new ConfigServiceDTO(serviceId))) {

						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							services.get(services.indexOf(new ConfigServiceDTO(serviceId))).setTimerName((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_TIMEOUT_PROPERTY)) {
							services.get(services.indexOf(new ConfigServiceDTO(serviceId))).setTimeout(Long.parseLong((String)pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_WSDL_PROPERTY)) {
							services.get(services.indexOf(new ConfigServiceDTO(serviceId))).setWsdl((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_DEGRADED_THRESHOLD_PROPERTY)) {
							services.get(services.indexOf(new ConfigServiceDTO(serviceId))).setDegradedThreshold(Long.parseLong((String) pair.getValue()));
						} else if (keyArray[2].equals(SERVICE_LOST_THRESHOLD_PROPERTY)) {
							services.get(services.indexOf(new ConfigServiceDTO(serviceId))).setLostThreshold((String) pair.getValue());
						} 
												
					} else {

						service = new ConfigServiceDTO(serviceId);
						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							service.setTimerName((String) pair.getValue());
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
								service.setPlatform(GeneralConstants.PLATFORM_TSA);
							} else {
								service.setPlatform(GeneralConstants.PLATFORM_AFIRMA);
							}
							
							services.add(service);
						}
					}
					
					

				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.error(Language.getFormatResPersistenceMonitoriza(IPersistenceLogMessages.ERRORPERSISTENCE001, new Object[ ] { key }));
				}

			}

		}

		return services;
	}

	/**
	 * Method that gets the services  from persistence (database or static properties file).
	 * @param timerName The name of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	public static List<ConfigServiceDTO> getServicesByTimer(final String timerName) {

		final List<ConfigServiceDTO> servicesTimer = new ArrayList<ConfigServiceDTO>();

		Iterator<ConfigServiceDTO> itServices = getServices().iterator();

		while (itServices.hasNext()) {

			ConfigServiceDTO service = (ConfigServiceDTO) itServices.next();

			if (service.getTimerName().equals(timerName)) {

				servicesTimer.add(service);

			}

		}

		return servicesTimer;
	}
	
	/**
	 * Method that finds a service by its identifier in List<ServiceDTO>.
	 * @param serviceId Service identifier
	 * @return ServiceDTO object that represents the service found
	 */
	public static ConfigServiceDTO getServiceById(final String serviceId) {
				
		Iterator<ConfigServiceDTO> itServices = getServices().iterator();
		boolean found = Boolean.FALSE;

		ConfigServiceDTO service = null;
		while (itServices.hasNext() && !found) {
			
			service = (ConfigServiceDTO) itServices.next();

			if (service.getServiceId().equals(serviceId)) {

				found = Boolean.TRUE;

			}
		}
		
		return service;
		
	}

}
