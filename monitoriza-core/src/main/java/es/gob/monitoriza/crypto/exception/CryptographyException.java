/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2009-,2011 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.cryptography.exception.CryptographyException.java.</p>
 * <b>Description:</b><p>Class that manages the errors related with the management of keystores in the system.</p>
 * <b>Date:</b><p>11/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.0, 11/06/2012.
 */
package es.gob.monitoriza.crypto.exception;

/**
 * <p>Class that manages the errors related with the management of keystores in the system.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.0, 11/06/2012.
 */
public class CryptographyException extends Exception {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -2510321036425367692L;

	/**
	 * Constructor method for the class CryptographyException.java.
	 */
	public CryptographyException() {
		super();
	}

	/**
	 * Constructor method for the class CryptographyException.java.
	 * @param message Error message.
	 */
	public CryptographyException(String message) {
		super(message);
	}

	/**
	 * Constructor method for the class CryptographyException.java.
	 * @param cause Error cause.
	 */
	public CryptographyException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor method for the class CryptographyException.java.
	 * @param message Error message.
	 * @param cause Error cause.
	 */
	public CryptographyException(String message, Throwable cause) {
		super(message, cause);
	}

}
