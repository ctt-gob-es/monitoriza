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
 * @version 1.0, 19 ene. 2018.
 */
package es.gob.monitoriza.configuration.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.service.IServiceMonitorizaService;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ services from database persistence.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 12/06/2018.
 */
@Service
public class AdminServicesManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	@Autowired
	IServiceMonitorizaService serviceService;
	
	

	/**
	 * Method that gets the services  from persistence (database or static properties file)
	 * @param timerId The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	public List<ServiceDTO> getServicesByTimer(final Long idTimer) {

		final List<ServiceDTO> servicesTimer = new ArrayList<ServiceDTO>();
//		ServiceDTO serviceDTO = null;
//		final TimerMonitoriza timer = new TimerMonitoriza();
//		timer.setIdTimer(idTimer);
//		List<ServiceMonitoriza> servicesByTimer = StreamSupport.stream(serviceService.getAllByTimer(timer).spliterator(), false)
//				.collect(Collectors.toList());
//
//		for (ServiceMonitoriza service : servicesByTimer) {
//			serviceDTO = new ServiceDTO(serviceId)
//		}

		return servicesTimer;
	}
	
	
}
