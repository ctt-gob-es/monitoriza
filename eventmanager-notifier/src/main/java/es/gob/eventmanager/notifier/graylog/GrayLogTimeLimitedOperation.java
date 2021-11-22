/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://administracionelectronica.gob.es
 *
 * Copyright 2005-2019 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.eventmanager.notifier.graylog.GrayLogTimeLimitedOperation.java.</p>
 * <b>Description:</b><p>Utilties class for the use of GrayLog.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 22/11/2021.
 */
package es.gob.eventmanager.notifier.graylog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.paluch.logging.gelf.intern.GelfMessage;
import biz.paluch.logging.gelf.intern.GelfSender;
import biz.paluch.logging.gelf.intern.sender.GelfUDPSender;
import es.gob.eventmanager.logger.Log4jErrorReporter;
import es.gob.eventmanager.message.Alert;
import es.gob.eventmanager.notifier.thread.ATimeLimitedOperation;
import es.gob.eventmanager.utils.UtilsStringChar;

/**
 * <p>Utilities class for the use of GrayLog.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 22/11/2021.
 */
public class GrayLogTimeLimitedOperation extends ATimeLimitedOperation {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger("eventmanager-service");

	/**
	 * Constant attribute that represents the token key 'EVENT_CODE' for a Gray Log Message Field.
	 */
	private static final String TOKEN_KEY_EVENT_CODE = "EVENT_CODE";

	/**
	 * Constant attribute that represents the token key 'RESOURCE_INFO' for a Gray Log Message Field.
	 */
	private static final String TOKEN_KEY_RESOURCE_INFO = "RESOURCE_INFO";
	
	/**
	 * Constant attribute that represents the token key 'source' for a Gray Log Message Field.
	 */
	private static final String TOKEN_KEY_SOURCE = "source";

	/**
	 * Constant attribute that represents the token key 'MESSAGE' for a Gray Log Message Field.
	 */
	private static final String TOKEN_KEY_MESSAGE = "message";
	
	/**
	 * Constant attribute that represents the level FATAL for the GrayLogger.
	 */
	public static final int LEVEL_FATAL = 0;

	/**
	 * Constant attribute that represents the level ERROR for the GrayLogger.
	 */
	public static final int LEVEL_ERROR = 1;

	/**
	 * Constant attribute that represents the level WARN for the GrayLogger.
	 */
	public static final int LEVEL_WARN = 2;

	/**
	 * Constant attribute that represents the level INFO for the GrayLogger.
	 */
	public static final int LEVEL_INFO = 3;

	/**
	 * Constant attribute that represents the level DEBUG for the GrayLogger.
	 */
	public static final int LEVEL_DEBUG = 4;
	
	private GrayLogConfigDTO config;
	
	private Alert alert;
	
		
	/**
	 * Constructor method for the class GrayLogNotifier.java.
	 * @param config
	 * @param alert 
	 */
	public GrayLogTimeLimitedOperation(GrayLogConfigDTO config, Alert alert) {
		super();
		this.config = config;
		this.alert = alert;
	}


	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.notifier.thread.ATimeLimitedOperation#doOperationThread()
	 */
	@Override
	protected void doOperationThread() throws Exception {
		writeMessageInGrayLog();
		
	}	
	
	/**
	 * This method only works if the Gray Log has been initialized and is enabled in the configuration.
	 * Writes a event-message and send to the GrayLog Server with the following structure/fields:
	 *     EVENT_CODE=[{eventCode}];RESOURCE_INFO=[{resourceInfo}];MESSAGE=[{message}]
	 * @param level Level to assign to the event message. It could be some of:
	 * 		<ul>
	 * 			<li>{@link GrayLogTimeLimitedOperation#LEVEL_FATAL}</li>
	 * 			<li>{@link GrayLogTimeLimitedOperation#LEVEL_ERROR}</li>
	 * 			<li>{@link GrayLogTimeLimitedOperation#LEVEL_WARN}</li>
	 * 			<li>{@link GrayLogTimeLimitedOperation#LEVEL_INFO}</li>
	 * 		</ul>
	 * If it is specified another value, then this method do nothing.
	 * @param eventCode Event code for the event message. If this is <code>null</code> or empty, or not is defined,
	 * then this method do nothing.
	 * @param resourceInfo Additional info for the event-message. It could be <code>null</code> or empty.
	 * @param message Message to add in the event-message. If this is <code>null</code> or empty, or not is defined,
	 * then this method do nothing.
	 */
	private void writeMessageInGrayLog() {

		GelfSender grayLogMessageSender = null;

		if (config.getHost() != null && config.getPort() > 0) {

			try {
				grayLogMessageSender = new GelfUDPSender(config.getHost(), config.getPort(), Log4jErrorReporter.getInstance());
			} catch (IOException e) {
				grayLogMessageSender = null;
				LOGGER.error("No se pudo inicializar la herramienta de envío de mensajes a GrayLog", e);
			}

		}
			
		// Si ni el código de evento ni el mensaje son cadenas nulas o
		// vacías...
		if (!UtilsStringChar.isNullOrEmptyTrim(alert.getCode()) && !UtilsStringChar.isNullOrEmptyTrim(alert.getMessage())) {

			GelfMessage gm = new GelfMessage();
			gm.setShortMessage(alert.getMessage());
			String fullMessage = getParsedEventMessageForGrayLog(alert.getCode(), alert.getResource(), alert.getMessage());
			gm.setFullMessage(fullMessage);
			gm.setJavaTimestamp(Calendar.getInstance().getTimeInMillis());
			gm.setLevel(String.valueOf(2));
			//gm.addField(TOKEN_KEY_EVENT_CODE, alert.getCode());
			
			// Se calcula el hostname para informar el campo "source"
			InetAddress ip;
			String hostname = null;
			try {
				
				ip = InetAddress.getLocalHost();
	            hostname = ip.getHostName();
				
			} catch (UnknownHostException e) {
				String msg = "No ha podido obtenerse el 'hostname' del servidor actual";
				LOGGER.warn(msg, e);
				hostname = msg;
	        }
			
			gm.addField("cod_err", alert.getCode());
			gm.addField(TOKEN_KEY_SOURCE, alert.getResource());
			gm.addField(TOKEN_KEY_MESSAGE, alert.getMessage());
			gm.addFields(config.getGrayLogDeclaredFields());
			grayLogMessageSender.sendMessage(gm);

		} else {

			LOGGER.warn("El código de evento y/o el mensaje a escribir en GrayLog, no tienen valores correctos: EVENT_CODE=[{}], MESSAGE=[{}]", alert.getCode(), alert.getMessage());

		}

	}

	/**
	 * Builds the event-message from the input parameters.
	 * @param eventCode Event code for the event message.
	 * @param resourceInfo Additional info for the event-message. It could be <code>null</code> or empty.
	 * @param message Message to add in the event-message.
	 * @return Event message builded from the input parameters.
	 */
	private static String getParsedEventMessageForGrayLog(String eventCode, String resourceInfo, String message) {
		
		if (UtilsStringChar.isNullOrEmptyTrim(resourceInfo)) {
			
			return TOKEN_KEY_EVENT_CODE.concat("=[").concat(eventCode).concat("];").concat(TOKEN_KEY_MESSAGE).concat("=[").concat(message).concat("];");
			
		} else {
			
			return TOKEN_KEY_EVENT_CODE.concat("=[").concat(eventCode).concat("];").concat(TOKEN_KEY_RESOURCE_INFO).concat("=[").concat(resourceInfo).concat("];").concat(TOKEN_KEY_MESSAGE).concat("=[").concat(message).concat("];");
		}

	}

	

}
