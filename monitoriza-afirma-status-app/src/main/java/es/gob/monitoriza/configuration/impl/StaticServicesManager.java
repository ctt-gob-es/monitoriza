/* 
// Copyright (C) 2018, Gobierno de España
// This program is licensed and may be used, modified and redistributed under the terms
// of the European Public License (EUPL), either version 1.1 or (at your
// option) any later version as soon as they are approved by the European Commission.
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// or implied. See the License for the specific language governing permissions and
// more details.
// You should have received a copy of the EUPL1.1 license
// along with this program; if not, you may find it at
// http://joinup.ec.europa.eu/software/page/eupl/licence-eupl

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

import org.apache.log4j.Logger;

import java.util.Set;

import es.gob.monitoriza.configuration.ServicesManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.dto.DTOService;
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ services from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 19/01/2018.
 */
public class StaticServicesManager implements ServicesManager {

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
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ServicesManager#getServices()
	 */
	@Override
	public List<DTOService> getServices() {

		List<DTOService> services = new ArrayList<DTOService>();

		Set<Entry<Object, Object>> allProperties = StaticMonitorizaProperties.getAllProperties();

		Iterator<Entry<Object, Object>> itProp = allProperties.iterator();

		DTOService service = null;

		while (itProp.hasNext()) {

			Entry<Object, Object> pair = (Entry<Object, Object>) itProp.next();
			String key = (String) pair.getKey();

			if (key.startsWith("service")) {

				String[ ] keyArray = key.split("\\.");

				try {

					String serviceId = keyArray[1];

					if (services.contains(new DTOService(serviceId))) {

						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setTimerId((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_TIMEOUT_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setTimeout((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_WSDL_PROPERTY)) {
							services.get(services.indexOf(new DTOService(serviceId))).setWsdl((String) pair.getValue());
						}
					} else {

						service = new DTOService(serviceId);
						if (keyArray[2].equals(SERVICE_TIMER_PROPERTY)) {
							service.setTimerId((String) pair.getValue());

						} else if (keyArray[2].equals(SERVICE_TIMEOUT_PROPERTY)) {
							service.setTimeout((String) pair.getValue());
						} else if (keyArray[2].equals(SERVICE_WSDL_PROPERTY)) {
							service.setWsdl((String) pair.getValue());
						}

						services.add(service);
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					LOGGER.error(Language.getFormatResMonitoriza(LogMessages.ERROR_PROPERTY_SERVICE, new Object[ ] { key }));
				}

			}

		}

		return services;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ServicesManager#getServicesByTimer(java.lang.String)
	 */
	@Override
	public List<DTOService> getServicesByTimer(final String timerId) {

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
