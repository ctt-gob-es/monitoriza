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
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 abr. 2018.
 */
public interface ITimerMonitorizaService {
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param userId The platform identifier.
	 * @return {@link PlatformAfirma}
	 */
	TimerMonitoriza getTimerMonitorizaById(Long timerId);
			
	/**
	 * Method that stores a timer in the persistence.
	 * @param user a {@link ServiceMonitoriza} with the information of the service.
	 * @return {@link ServiceMonitoriza} The service. 
	 */
	TimerMonitoriza saveTimerMonitoriza(TimerMonitoriza timer);
			
	/**
	 * Method that deletes a timer in the persistence.
	 * @param serviceId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteTimerMonitoriza(Long timerId);
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @return a {@link Iterable<TimerMonitoriza>} with the information of all services.
	 */
	Iterable<TimerMonitoriza> getAllTimerMonitoriza();
		
	/**
	 * 
	 * @param input
	 * @return
	 */
	DataTablesOutput<TimerMonitoriza> findAll(DataTablesInput input);

}