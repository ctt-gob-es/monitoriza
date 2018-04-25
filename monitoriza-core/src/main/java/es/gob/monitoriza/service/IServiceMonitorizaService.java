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
 * <b>File:</b><p>es.gob.monitoriza.service.IServiceMonitorizaService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 abr. 2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 abr. 2018.
 */
public interface IServiceMonitorizaService {
	
	/**
	 * Method that obtains the configuration for service by its identifier.
	 * @param userId The platform identifier.
	 * @return {@link PlatformAfirma}
	 */
	ServiceMonitoriza getServiceMonitorizaById(Long serviceId);
			
	/**
	 * Method that stores a service in the persistence.
	 * @param user a {@link ServiceMonitoriza} with the information of the service.
	 * @return {@link ServiceMonitoriza} The service. 
	 */
	ServiceMonitoriza saveServiceMonitoriza(ServiceMonitoriza service);
			
	/**
	 * Method that deletes a service in the persistence.
	 * @param serviceId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteServiceMonitoriza(Long serviceId);
	
	/**
	 * Method that gets all the service from the persistence.
	 * @return a {@link Iterable<ServiceMonitoriza>} with the information of all services.
	 */
	Iterable<ServiceMonitoriza> getAllServiceMonitoriza();
		
	/**
	 * 
	 * @param input
	 * @return
	 */
	DataTablesOutput<ServiceMonitoriza> findAll(DataTablesInput input);

}
