/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2009-2012 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.persistence.configuration.model.bo.exception.CipherException.java.</p>
 * <b>Description:</b><p>Class that represents an exception related to the AES Cipher.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>18/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 18/01/2019.
 */
package es.gob.monitoriza.exception;

/**
 * <p>Class that represents an exception related to the AES Cipher.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 18/01/2019.
 */
public class CipherException extends Exception {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 7494476533690074863L;

	/**
	 * Constructor method for the class CipherException.java.
	 */
	public CipherException() {
		super();

	}

	/**
	 * Constructor method for the class CipherException.java.
	 * @param message Error message.
	 */
	public CipherException(String message) {
		super(message);

	}

	/**
	 * Constructor method for the class CipherException.java.
	 * @param cause Error cause.
	 */
	public CipherException(Throwable cause) {
		super(cause);

	}

	/**
	 * Constructor method for the class CipherException.java.
	 * @param message Error message.
	 * @param cause Error cause.
	 */
	public CipherException(String message, Throwable cause) {
		super(message, cause);

	}

}
