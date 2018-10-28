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
 * <b>File:</b><p>es.gob.monitoriza.service.IServiceScheduledService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.service;

import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
public interface ITimerScheduledService {
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param timerId The platform identifier.
	 * @return {@link TimerScheduled}
	 */
	TimerScheduled getTimerScheduledById(Long timerId);
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param timerIdTimer The platform identifier.
	 * @return {@link TimerScheduled}
	 */
	TimerScheduled getTimerScheduledByIdTimer(Long timerIdTimer);
			
	/**
	 * Method that stores a timer in the persistence.
	 * @param timer a {@link ServiceScheduled} with the information of the service.
	 * @return {@link ServiceScheduled} The service. 
	 */
	TimerScheduled saveTimerScheduled(TimerScheduled timer);
			
	/**
	 * Method that deletes a timer in the persistence.
	 * @param timerId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteTimerScheduled(Long timerId);
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @return a {@link Iterable<TimerScheduled>} with the information of all services.
	 */
	Iterable<TimerScheduled> getAllTimerScheduled();
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @param idTimers {@link Iterable<Long>} of identifiers
	 * @return a {@link Iterable<TimerScheduled>} with the information of all services.
	 */
	Iterable<TimerScheduled> getAllTimerScheduledById(Iterable<Long> idTimers);		
	
	/**
	 * Method that deletes all entities of TimerScheduled from persistence.
	 */
	void emptyTimersScheduled();
	
	/**
	 * Sets all scheduled eligible for re-charging its configuration.
	 */
	void setAllScheduledTimersNotUpdated();


}
