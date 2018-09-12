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
 * @version 1.1, 12/09/2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 12/09/2018.
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
	 * Method that gets all the services from the persistence filtered by timer.
	 * @param timer Criteria filter
	 * @return a {@link Iterable<ServiceMonitoriza>} with the information of all services.
	 */
	Iterable<ServiceMonitoriza> getAllByTimer(TimerMonitoriza timer);	
		
	/**
	 * Method that gets all the services for the data table.
	 * @param input DataTablesInput
	 * @return DataTablesOutput<ServiceMonitoriza>
	 */
	DataTablesOutput<ServiceMonitoriza> findAll(DataTablesInput input);
	
	/**
	 * Method that gets all the services from the persistence filtered by platform.
	 * @param timer Criteria filter
	 * @return a {@link Iterable<ServiceMonitoriza>} with the information of all services.
	 */
	Iterable<ServiceMonitoriza> getAllByPlatform(PlatformMonitoriza platform);	
	
	/**
	 * Method that gets all the services from the persistence filtered by alarm.
	 * @param timer Criteria filter
	 * @return a {@link Iterable<ServiceMonitoriza>} with the information of all services.
	 */
	Iterable<ServiceMonitoriza> getAllByAlarm(AlarmMonitoriza platform);	

}
