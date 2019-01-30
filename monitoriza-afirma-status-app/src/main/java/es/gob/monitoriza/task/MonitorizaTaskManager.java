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
 * <b>File:</b><p>es.gob.monitoriza.configuration.manager.MonitorizaTaskManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 30/01/2019.
 */
package es.gob.monitoriza.task;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfigTimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.service.ITimerScheduledService;
import es.gob.monitoriza.service.impl.KeystoreService;
import es.gob.monitoriza.service.impl.VipMonitoringConfigService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.status.thread.RequestLauncher;
import es.gob.monitoriza.timers.TimersHolder;

/** 
 * <p>Class that update the configuration of the scheduled services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 30/01/2019.
 */
@Service("monitorizaTaskManager")
class MonitorizaTaskManager {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private VipMonitoringConfigService serviceManager;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private ITimerScheduledService scheduledService;
		
	/**
	 * Method that updates the executing timers.
	 * @param listIdTimersUpdated List with the timers whose elements has changed.
	 */
	void updateTimersFromWebAdmin(final List<Long> listIdTimersUpdated) {
		
		// Se obtienen los timers cuya configuración (incluyendo elementos relacionados) haya cambiando
		List<ConfigTimerDTO> timers = serviceManager.getAllTimersById(listIdTimersUpdated);
		
		if (timers != null && !timers.isEmpty()) {
		
    		ExecuteTimer batchTimer = null;	
    		
    		// Se carga una sola vez el almacén de certificados para conexión segura.
    		KeyStore sslKeystore = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).loadSslTruststore();
    		
    		// Se carga una sola vez el almacén de certificados para conexión segura.
    		KeyStore rfc3161Keystore = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).loadRfc3161Keystore();
    						
    		for (ConfigTimerDTO timerDTO : timers) {
    			
    			Timer timer = TimersHolder.getInstance().getCurrentTimersHolder().get(timerDTO.getIdTimer());
    			
    			// El timer existía y ha habido algún cambio en en la configuración que le afecta.
    			if (timer != null) {
    				
    				// Se cancela el timer para volver a programarlo con los valores actualizados
    				LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS014, new Object[ ] { timerDTO.getName()}));
    				timer.cancel();
    			} 
    			
    			List<ConfigServiceDTO> serviciosTimer = serviceManager.getServicesByTimer(timerDTO);
    			    			
    			// Actualiza/añade el timer sólo si tiene asociado algún servicio
    			if (serviciosTimer != null && !serviciosTimer.isEmpty()) {
    				timer = new Timer();
    				batchTimer = new ExecuteTimer(timerDTO.getName(), serviciosTimer, sslKeystore, rfc3161Keystore);
        			timer.schedule(batchTimer, 0, timerDTO.getFrequency());
    				// Se actualiza el Map de timers con el nuevo timer en ejecución
    				TimersHolder.getInstance().getCurrentTimersHolder().put(timerDTO.getIdTimer(), timer);
    				
    				final TimerScheduled scheduled = scheduledService.getTimerScheduledByIdTimer(timerDTO.getIdTimer());
    				scheduled.setUpdated(true);
    				
    				scheduledService.saveTimerScheduled(scheduled);
    				
    			} else {
    				// El timer ya no tiene ningún servicio asociado, luego se elimina de entre los programados.
    				TimersHolder.getInstance().getCurrentTimersHolder().remove(timerDTO.getIdTimer());
    				final TimerScheduled scheduled = scheduledService.getTimerScheduledByIdTimer(timerDTO.getIdTimer());
    				scheduledService.deleteTimerScheduled(scheduled.getIdTimerScheduled());
    				
    			}
    		}
		}
	}

	
	/**
	 * <p>Timer class that process the batch of requests for testing the services.</p>
	 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
	 * @version 1.0, 15/01/2018.
	 */
	private class ExecuteTimer extends TimerTask {

		/**
		 * Attribute that represents the list of services for the timer being executed. 
		 */
		private transient List<ConfigServiceDTO> serviciosDelTimer = new ArrayList<ConfigServiceDTO>();

		/**
		 * Attribute that represents the timer being executed. 
		 */
		private transient String timerId;
		
		/**
		 * Attribute that represents the keystore used for SSL. 
		 */
		private transient KeyStore sslTrustStore;
		
		/**
		 * Attribute that represents the keystore used for RFC3161 authentication. 
		 */
		private transient KeyStore rfc3161Keystore;

		/**
		 * Constructor method for the class MonitorizaServletTask.java.
		 * @param timerIdParam String that represents the identifier of the timer being executed
		 * @param serviciosDelTimerParam List<DTOService> that contains the services associated to the timer
		 * @param sslTrustStoreParam KeyStore object containing the system truststore
		 * @param rfc3161KeystoreParam KeyStore object containing the authentication keystore for RFC3161 service
		 */
		ExecuteTimer(final String timerIdParam, final List<ConfigServiceDTO> serviciosDelTimerParam, final KeyStore sslTrustStoreParam, final KeyStore rfc3161KeystoreParam) {
			this.timerId = timerIdParam;
			this.serviciosDelTimer = serviciosDelTimerParam;
			this.sslTrustStore = sslTrustStoreParam;
			this.rfc3161Keystore = rfc3161KeystoreParam;
		}

		/**
		 * {@inheritDoc}
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {

			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS001, new Object[ ] { timerId }));
			
			RequestLauncher rlt = new RequestLauncher();
			
			rlt.startInvoker(StatusHolder.getInstance().getCurrenttatusHolder(), serviciosDelTimer, sslTrustStore, rfc3161Keystore);

		}

	}

}
