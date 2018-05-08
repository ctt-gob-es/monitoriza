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
 * @version 1.0, 15/01/2018.
 */
package es.gob.monitoriza.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.staticconfig.StaticServicesManager;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.status.thread.RequestLauncher;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that initializes the timers for processing the batch of requests for each service.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 15/01/2018.
 */
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
	public void init() throws ServletException {

		int timerIndex = 1;
		String timerId = GeneralConstants.MONITORIZA_TIMER + timerIndex;
		String timerFreqProperty = StaticMonitorizaProperties.getProperty(timerId + GeneralConstants.DOT + GeneralConstants.FREQUENCY);
		Timer timer = null;
		ExecuteTimer batchTimer = null;

		// Se programan los timers dados de alta en el archivo de propiedades
		// estático: timer1, timer2,...
		while (timerFreqProperty != null) {

			timer = new Timer();
			batchTimer = new ExecuteTimer(timerId, StaticServicesManager.getServicesByTimer(timerId));
			timerIndex++;
			timerId = GeneralConstants.MONITORIZA_TIMER + timerIndex;
			timer.schedule(batchTimer, 0, Long.parseLong(timerFreqProperty));
			timerFreqProperty = StaticMonitorizaProperties.getProperty(timerId + GeneralConstants.DOT + GeneralConstants.FREQUENCY);

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
		 * Constructor method for the class MonitorizaServletTask.java.
		 * @param timerId String that represents the identifier of the timer being executed
		 * @param serviciosDelTimer List<DTOService> that contains the services associated to the timer
		 */
		public ExecuteTimer(final String timerId, final List<ServiceDTO> serviciosDelTimer) {
			this.timerId = timerId;
			this.serviciosDelTimer = serviciosDelTimer;
		}

		/**
		 * {@inheritDoc}
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {

			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.INIT_SERVICE, new Object[ ] { timerId }));

			//RequestProcessor.getInstance().startInvoker(StatusHolder.getInstance().getCurrenttatusHolder(), serviciosDelTimer);
			
			RequestLauncher rlt = new RequestLauncher();
			
			rlt.startInvoker(StatusHolder.getInstance().getCurrenttatusHolder(), serviciosDelTimer);

		}

	}

}
