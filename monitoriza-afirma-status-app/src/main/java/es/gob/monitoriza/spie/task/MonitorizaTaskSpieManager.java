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
 * <b>File:</b><p>es.gob.monitoriza.spie.task.MonitorizaTaskSpieManager.java.</p>
 * <b>Description:</b><p>Class that update the configuration of the scheduled SPIE timer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>03/05/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 03/05/2019.
 */
 package es.gob.monitoriza.spie.task;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.service.impl.PlatformService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spie.thread.RequestLauncherSpie;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.timers.TimersHolder;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that update the configuration of the scheduled SPIE timer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 03/05/2019.
 */
@Service("monitorizaTaskSpieManager")
public class MonitorizaTaskSpieManager {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	public void updateSpieTimerFromWebAdmin(final Long platform, final Long frequency) {
		
		StringBuilder idTimerSpie = new StringBuilder();
		idTimerSpie.append(GeneralConstants.SPIE).append(platform);
		Timer timer = TimersHolder.getInstance().getCurrentTimersSpieHolder().get(idTimerSpie.toString());

		if (timer != null) {
			timer.cancel();
		}

		timer = new Timer();
		ExecuteTimer batchTimer = new ExecuteTimer(platform);
		timer.schedule(batchTimer, 0, frequency);

		// El timer programado se añade a la memoria para poder gestionarlo
		TimersHolder.getInstance().getCurrentTimersSpieHolder().put(idTimerSpie.toString(), timer);
		
		
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
