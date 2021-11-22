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
 * <b>File:</b><p>es.gob.eventmanager.logger.Log4jErrorReporter.java.</p>
 * <b>Description:</b><p>Log4j implementation for the error logger to use in Gray Log.</p>
 *  * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 22/11/2021.
 */
package es.gob.eventmanager.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.paluch.logging.gelf.intern.ErrorReporter;

/**
 * <p>Log4j implementation for the error logger to use in Gray Log.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 22/11/2021.
 */
public final class Log4jErrorReporter implements ErrorReporter {

	/**
	 * Attribute that represents the logger for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Log4jErrorReporter.class);

	/**
	 * Attribute that represents the singleton unique instance for this class.
	 */
	private static Log4jErrorReporter instance = null;

	/**
	 * Constructor method for the class Log4jErrorReporter.java.
	 */
	private Log4jErrorReporter() {
		super();
	}

	/**
	 * Gets the singleton unique instance of this class.
	 * @return the singleton unique instance of this class.
	 */
	public static Log4jErrorReporter getInstance() {
		if (instance == null) {
			instance = new Log4jErrorReporter();
		}
		return instance;
	}

	/**
	 * {@inheritDoc}
	 * @see biz.paluch.logging.gelf.intern.ErrorReporter#reportError(java.lang.String, java.lang.Exception)
	 */
	@Override
	public void reportError(String message, Exception e) {
		LOGGER.error(message, e);
	}

}
