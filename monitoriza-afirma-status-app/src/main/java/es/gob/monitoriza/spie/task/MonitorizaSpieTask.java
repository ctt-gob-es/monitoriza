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
 * <b>File:</b><p>es.gob.monitoriza.spie.task.MonitorizaSpieTask.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>25/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 11/05/2022.
 */
package es.gob.monitoriza.spie.task;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.service.impl.PlatformService;
import es.gob.monitoriza.service.impl.SpieMonitoringConfigService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spie.thread.RequestLauncherSpie;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.timers.TimersHolder;
import es.gob.monitoriza.utilidades.UtilsStringChar;


/** 
 * <p>Class that initializes the timers for processing the batch of requests for each SPIE service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 11/05/2022.
 */
public class MonitorizaSpieTask extends HttpServlet {

	/**
	 * Attribute that represents the serial number. 
	 */
	private static final long serialVersionUID = -7514164022124720290L;
		
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		
		super.init(config);
					
		scheduleSpieFromWebAdmin();
	}

	/**
	 * Method that gets the SPIE configuration from the web admin database and schedules the service request batch.
	 */
	private void scheduleSpieFromWebAdmin() {
					
		final ConfSpieDTO confSpie =  ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SPIE_MONITORING_CONFIG_SERVICE, SpieMonitoringConfigService.class).getSpieConfiguration();
		
		if (confSpie != null) {
			scheduleSpieAfirma(confSpie.getFrequencyAFirma());
			scheduleSpieTsa(confSpie.getFrequencyTsa());
		} else {
			LOGGER.info(Language.getResMonitoriza(IStatusLogMessages.STATUS018));
		}
		
	}

	/**
	 * Method that schedules a timer task for requesting SPIE from @Firma.
	 * @param afirmaFrequency Period on the launcher
	 */
	private void scheduleSpieAfirma(final Long afirmaFrequency) {
		Timer timer = new Timer();
		ExecuteTimer batchTimer = new ExecuteTimer(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		
		try {
			timer.schedule(batchTimer, 0, afirmaFrequency);
			
			StringBuilder timerAfirma = new StringBuilder();
			timerAfirma.append(GeneralConstants.SPIE).append(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
			
			// El timer programado se añade a la memoria para poder gestionarlo  
			TimersHolder.getInstance().getCurrentTimersSpieHolder().put(timerAfirma.toString(), timer);
			
		} catch (IllegalArgumentException e) {
			LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS027));
		}
		
		
	}
	
	/**
	 * Method that schedules a timer task for requesting SPIE from @Firma.
	 * @param afirmaFrequency Period on the launcher
	 */
	private void scheduleSpieTsa(final Long afirmaFrequency) {
		Timer timer = new Timer();
		ExecuteTimer batchTimer = new ExecuteTimer(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		
		try {
			timer.schedule(batchTimer, 0, afirmaFrequency);
			
			StringBuilder timerTsa = new StringBuilder();
			timerTsa.append(GeneralConstants.SPIE).append(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
			
			// El timer programado se añade a la memoria para poder gestionarlo  
			TimersHolder.getInstance().getCurrentTimersSpieHolder().put(timerTsa.toString(), timer);
		} catch (IllegalArgumentException e) {
			LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS028));
		}
	}
	
	/**
	 * <p>Timer class that process the batch of requests for testing the services.</p>
	 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
	 * @version 1.0, 25/10/2018.
	 */
	private class ExecuteTimer extends TimerTask {
		
		/**
		 * Attribute that represents the type of the platform. 
		 */
		private transient Long platformType;
	
		/**
		 * Constructor method for the class MonitorizaSpieTask.java.
		 * @param platformTypeParam Name of the timer
		 */
		ExecuteTimer(final Long platformTypeParam) {
			this.platformType = platformTypeParam;
		}

		/**
		 * {@inheritDoc}
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			
			CPlatformType type = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.PLATFORM_SERVICE, PlatformService.class).getPlatformTypeById(platformType);
			StringBuilder idTimerTask = new StringBuilder();
			idTimerTask.append(GeneralConstants.SPIE).append(UtilsStringChar.SYMBOL_HYPHEN_STRING).append(type.getName());

			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS001, new Object[ ] { idTimerTask }));
			
			RequestLauncherSpie rlt = new RequestLauncherSpie();
			
			rlt.startInvoker(platformType);
					

		}

	}

}
