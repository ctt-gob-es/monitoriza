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
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.TimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 28/102018.
 */
public interface ITimerMonitorizaService {
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param timerId The timer identifier.
	 * @return {@link PlatformAfirma}
	 */
	TimerMonitoriza getTimerMonitorizaById(Long timerId);
			
	/**
	 * Method that stores a timer in the persistence.
	 * @param timerDto a {@link TimerDTO} with the information of the timer.
	 * @return {@link ServiceMonitoriza} The service. 
	 */
	TimerMonitoriza saveTimerMonitoriza(TimerDTO timerDto);
			
	/**
	 * Method that deletes a timer in the persistence.
	 * @param timerId {@link Long} that represents the user identifier to delete.
	 */
	void deleteTimerMonitoriza(Long timerId);
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @return a {@link Iterable<TimerMonitoriza>} with the information of all services.
	 */
	Iterable<TimerMonitoriza> getAllTimerMonitoriza();
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @param idTimers List of timer identifiers
	 * @return a {@link Iterable<TimerMonitoriza>} with the information of all services.
	 */
	Iterable<TimerMonitoriza> getAllTimerMonitorizaById(Iterable<Long> idTimers);
		
	/**
	 * Method that obtains all timers.
	 * @param input DataTablesInput configuration object
	 * @return DataTablesOutput<TimerMonitoriza>
	 */
	DataTablesOutput<TimerMonitoriza> findAll(DataTablesInput input);
	
	/**
	 * Method that obtains all timers that has at least one service that uses RFC3161 authentication.
	 * @return List<TimerMonitoriza>
	 */
	List<TimerMonitoriza> findTimersAnyServiceUsingRFC3161Auth();

}
