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
 * <b>File:</b><p>es.gob.monitoriza.task.TimerScheduledCheckerTask.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/09/2018.
 */
package es.gob.monitoriza.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.service.ITimerScheduledService;

/** 
 * <p>Class that executes a scheduled task to check if there are changes on running scheduled timers.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 12/09/2018.
 */
@Component
public class TimerScheduledCheckerTask {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents . 
	 */
	private static final long CHECKRATE = 60000;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	ITimerScheduledService scheduledService;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	MonitorizaTaskManager taskManager;
		
	@Scheduled(fixedRate = CHECKRATE)
	public void checkIfScheduledTimersHaveChanged() {
		
		LOGGER.info("Se comprueba si existen timers pendientes de actualizar");
		
		final List<Long> timersToUpdate = new ArrayList<Long>();
		
		// Se obtiene la lista de timers programados con su estado (actualizado o no)
		List<TimerScheduled> listScheduled = StreamSupport.stream(scheduledService.getAllTimerScheduled().spliterator(), false)
				.collect(Collectors.toList());
		
		for (TimerScheduled scheduled : listScheduled) {
			
			// Si el timer programado no está actualizado, se añade a la lista de pendientes de actualizar.
			if (!scheduled.isUpdated()) {
				
				timersToUpdate.add(scheduled.getTimer().getIdTimer());			
				
			}
			
		}
		
		taskManager.updateTimersFromWebAdmin(timersToUpdate);
		
	}

}
