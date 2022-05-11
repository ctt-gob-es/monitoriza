/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2018 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.utilidades.loggers.Log4jErrorReporter.java.</p>
 * <b>Description:</b><p>Log4j implementation for the error logger to use in Gray Log.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * <b>Date:</b><p>05/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/05/2022.
 */
package es.gob.monitoriza.utilidades.loggers;

import es.gob.monitoriza.utilidades.loggers.Logger;

import biz.paluch.logging.gelf.intern.ErrorReporter;

/**
 * <p>Log4j implementation for the error logger to use in Gray Log.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * @version 1.1, 11/05/2022.
 */
public final class Log4jErrorReporter implements ErrorReporter {

	/**
	 * Attribute that represents the logger for this class.
	 */
	private static final Logger LOGGER = Logger.getLogger(Log4jErrorReporter.class);

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
