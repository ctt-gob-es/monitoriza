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
 * @version 1.0, 22/10/2018.
 */
package es.gob.monitoriza.service;

import es.gob.monitoriza.persistence.configuration.model.entity.SpieScheduled;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/10/2018.
 */
public interface ISpieScheduledService {
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param spieScheduledId Identifier for the scheduled SPIE.
	 * @return {@link SpieScheduled}
	 */
	SpieScheduled getSpieScheduledById(Long spieScheduledId);
	
	/**
	 * Method that obtains the configuration for timer by its identifier.
	 * @param idPlatformType Identifier of the platform type for this scheduled SPIE.
	 * @return {@link SpieScheduled}
	 */
	SpieScheduled getSpieScheduledByPlatformType(Long idPlatformType);
			
	/**
	 * Method that stores a timer in the persistence.
	 * @param spieScheduled a {@link SpieScheduled} with the information of the scheduled SPIE.
	 * @return {@link ServiceScheduled} The service. 
	 */
	SpieScheduled saveSpieScheduled(SpieScheduled spieScheduled);
	
	/**
	 * Method that deletes a scheduled SPIE in the persistence.
	 * @param scheduledSpieId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteSpieScheduled(Long scheduledSpieId);
		
	/**
	 * Method that gets all the SPIE scheduled from the persistence.
	 * @return a {@link Iterable<SpieScheduled>} with the information of all services.
	 */
	Iterable<SpieScheduled> getAllSpieScheduled();
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @param idScheduledSpies {@link Iterable<Long>} of identifiers.
	 * @return a {@link Iterable<SpieScheduled>} with the information of all services.
	 */
	Iterable<SpieScheduled> getAllSpieScheduledById(Iterable<Long> idScheduledSpies);		
	
	/**
	 * Method that deletes all entities of SpieScheduled from persistence.
	 */
	void emptyTimersScheduled();


}
