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
 * <b>File:</b><p>es.gob.monitoriza.MonitorizaServletTask.java.</p>
 * <b>Description:</b>
 * <p>Class that initializes the timers for processing the batch of requests for each service.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.2, 12/09/2018.
 */
package es.gob.monitoriza.task;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import es.gob.monitoriza.configuration.manager.AdminServicesManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.TimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.status.thread.RequestLauncher;
import es.gob.monitoriza.timers.TimersHolder;

/** 
 * <p>Class that initializes the timers for processing the batch of requests for each service.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.2, 12/09/2018.
 */
@Configurable
public class MonitorizaServletTask extends HttpServlet {

	/**
	 * Attribute that represents the serial number. 
	 */
	private static final long serialVersionUID = -4512378490682333055L;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init(final ServletConfig config) throws ServletException {
		
		super.init(config);
		
		ApplicationContext ac = (ApplicationContext) getServletConfig().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		//scheduleTimersFromStaticConfig();
		
		scheduleTimersFromWebAdmin(ac);
	}
		
	/**
	 * Method that gets the configuration from the web admin database and schedules the service request batch.
	 */
	private void scheduleTimersFromWebAdmin(final ApplicationContext applicationContext) {
		
		// Se programan los timers dados de alta en la administración web
		Timer timer = null;
		AdminServicesManager adminServiceManager = (AdminServicesManager) applicationContext.getBean("adminServicesManager");
		
		// Se vacía la tabla de timers programados
		adminServiceManager.emptyTimersScheduled();

		List<TimerDTO> timers = adminServiceManager.getAllTimers();
		ExecuteTimer batchTimer = null;

		// Se carga una sola vez el almacén de certificados para conexión
		// segura.
		KeyStore sslKeystore = adminServiceManager.loadSslTruststore();

		// Se carga una sola vez el almacén de certificados para autenticación
		// RFC3161.
		KeyStore rfc3161Keystore = adminServiceManager.loadRfc3161Keystore();

		for (TimerDTO timerDTO: timers) {

			List<ServiceDTO> serviciosTimer = adminServiceManager.getServicesByTimer(timerDTO);
			
			// Sólo programo el timer si tiene algún servicio asociado
			if (serviciosTimer != null && !serviciosTimer.isEmpty()) {
				timer = new Timer();
				batchTimer = new ExecuteTimer(timerDTO.getName(), serviciosTimer, sslKeystore, rfc3161Keystore);
				timer.schedule(batchTimer, 0, timerDTO.getFrequency());
				
				// El timer programado se añade a la memoria para poder gestionarlo  
				TimersHolder.getInstance().getCurrentTimersHolder().put(timerDTO.getIdTimer(), timer);
								
				// El timer programado se guarda en persistencia para poder consultar si ha sido modificado
				TimerScheduled scheduled = new TimerScheduled();
				TimerMonitoriza timerMonitoriza = new TimerMonitoriza();
				timerMonitoriza.setIdTimer(timerDTO.getIdTimer());
				scheduled.setTimer(timerMonitoriza);
				scheduled.setUpdated(Boolean.TRUE);
				adminServiceManager.saveTimerScheduled(scheduled);
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
		private transient List<ServiceDTO> serviciosDelTimer = new ArrayList<ServiceDTO>();

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
		 * @param timerId String that represents the identifier of the timer being executed
		 * @param serviciosDelTimer List<DTOService> that contains the services associated to the timer
		 */
		public ExecuteTimer(final String timerId, final List<ServiceDTO> serviciosDelTimer, final KeyStore sslTrustStore, final KeyStore rfc3161Keystore) {
			this.timerId = timerId;
			this.serviciosDelTimer = serviciosDelTimer;
			this.sslTrustStore = sslTrustStore;
			this.rfc3161Keystore = rfc3161Keystore;
		}

		/**
		 * {@inheritDoc}
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {

			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.INIT_SERVICE, new Object[ ] { timerId }));
			
			RequestLauncher rlt = new RequestLauncher();
			
			rlt.startInvoker(StatusHolder.getInstance().getCurrenttatusHolder(), serviciosDelTimer, sslTrustStore, rfc3161Keystore);

		}

	}

}
