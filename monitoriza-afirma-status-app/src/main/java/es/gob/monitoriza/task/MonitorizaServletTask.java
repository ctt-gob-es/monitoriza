/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.MonitorizaServletTask.java.</p>
 * <b>Description:</b><p> Interface that contains general constants.</p>
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

import es.gob.monitoriza.configuration.impl.StaticServicesManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.DTOService;
import es.gob.monitoriza.status.RequestProcessor;
import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that contains .</p>
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
		String timerFreqProperty = StaticMonitorizaProperties.getProperty( timerId + GeneralConstants.DOT + GeneralConstants.FREQUENCY);
		Timer timer = null;
		ExecuteTimer batchTimer = null;

		// Se programan los timers dados de alta en el archivo de propiedades estático: timer1, timer2,...
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
		private transient List<DTOService> serviciosDelTimer = new ArrayList<DTOService>();
		
		/**
		 * Attribute that represents the timer being executed. 
		 */
		private transient String timerId;

		/**
		 * Constructor method for the class MonitorizaServletTask.java.
		 * @param serviciosDelTimer 
		 */
		public ExecuteTimer(final String timerId, final List<DTOService> serviciosDelTimer) {
			this.timerId = timerId;
			this.serviciosDelTimer = serviciosDelTimer;
		}

		/**
		 * {@inheritDoc}
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {

			try {

				LOGGER.info(Language.getFormatResMonitoriza(LogMessages.INIT_SERVICE, new Object[] {timerId}));
				
				RequestProcessor.getInstance().startInvoker(StatusHolder.getInstance().getCurrenttatusHolder(), serviciosDelTimer);

			} catch (InvokerException e) {
				LOGGER.error(e.getMessage());
			}

		}

	}

}
