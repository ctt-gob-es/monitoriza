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
 * @version 1.4, 26/09/2023.
 */
package es.gob.monitoriza.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import es.gob.monitoriza.utilidades.loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.service.IConfSpieService;
import es.gob.monitoriza.service.ITimerScheduledService;
import es.gob.monitoriza.service.impl.ConfSpieService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spie.task.MonitorizaTaskSpieManager;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;

/** 
 * <p>Class that executes a scheduled task to check if there are changes on running scheduled timers.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 26/09/2023.
 */
@Component
public class TimerScheduledCheckerTask {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_STATUS_LOG);
		
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private ITimerScheduledService scheduledService;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private MonitorizaTaskManager taskManager;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private MonitorizaTaskSpieManager taskSpieManager;

	/**
	 * 
	 */
	public void checkIfScheduledTimersHaveChanged() {
		
		LOGGER.info("Se comprueba si existen timers pendientes de actualizar");
		
		final List<Long> timersToUpdate = new ArrayList<Long>();
		
		// Se obtiene la lista de timers VIP programados con su estado (actualizado o no)
		List<TimerScheduled> listScheduled = StreamSupport.stream(scheduledService.getAllTimerScheduled().spliterator(), false)
				.collect(Collectors.toList());
		
		for (TimerScheduled scheduled : listScheduled) {
			
			// Si el timer programado no está actualizado, se añade a la lista de pendientes de actualizar.
			if (!scheduled.isUpdated()) {
				
				timersToUpdate.add(scheduled.getTimer().getIdTimer());			
				
			}
			
		}
		
		taskManager.updateTimersFromWebAdmin(timersToUpdate);
		
		// Se comprueba si es necesario actualizar los timers SPIE
		// Se obtiene el servicio de configuración SPIE.
		IConfSpieService confSpieService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.CONF_SPIE_SERVICE, ConfSpieService.class);
		
		ConfSpie confSpie = confSpieService.getAllConfSpie();
		
		if (confSpie != null)
		{
			if (confSpie.getUpdateAfirma()) {
				taskSpieManager.updateSpieTimerFromWebAdmin(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA, confSpie.getFrequencyAfirma());
				confSpie.setUpdateAfirma(Boolean.FALSE);
			}
			
			if (confSpie.getUpdateTsa()) {
				taskSpieManager.updateSpieTimerFromWebAdmin(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA, confSpie.getFrequencyAfirma());
				confSpie.setUpdateTsa(Boolean.FALSE);
			}
			
			confSpieService.save(confSpie);
		}
		
	}

}
