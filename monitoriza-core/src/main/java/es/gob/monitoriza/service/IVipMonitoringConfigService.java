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
 * <b>File:</b><p>es.gob.monitoriza.service.IVipMonitoringConfigService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/01/2019.
 */
package es.gob.monitoriza.service;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfigTimerDTO;

/** 
 * <p>Interface that provides configuration methods for the VIP monitoring process.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30/01/2019.
 */
public interface IVipMonitoringConfigService {

	
	/**
	 * Method that gets all timers from database.
	 * @return List of TimerDTO
	 */
	List<ConfigTimerDTO> getAllTimers();
	
	/**
	 * Method that gets timers by ids from database.
	 * @param idTimers List that contains timer identifiers.
	 * @return List of TimerDTO
	 */
	List<ConfigTimerDTO> getAllTimersById(List<Long> idTimers);
	

	/**
	 * Method that gets the services  from persistence (database or static properties file).
	 * @param timerDTO The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	List<ConfigServiceDTO> getServicesByTimer(ConfigTimerDTO timerDTO);	
	
}
