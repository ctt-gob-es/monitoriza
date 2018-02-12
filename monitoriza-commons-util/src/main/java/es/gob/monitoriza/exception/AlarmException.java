/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.exception.AlarmException.java.</p>
 * <b>Description:</b><p> Class that defines the exception object of the alarm module.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package es.gob.monitoriza.exception;


/** 
 * <p>Class that defines the exception object of the alarm module.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public class AlarmException extends Exception {

	/**
	 * Attribute that represents the serial version of the class. 
	 */
	private static final long serialVersionUID = -811278416663235358L;

	/**
	 * Constructor method for the class AlarmException.java. 
	 */
	public AlarmException() {
		super();
	}

	/**
	 * Constructor method for the class AlarmException.java.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace 
	 */
	public AlarmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor method for the class AlarmException.java.
	 * @param message
	 * @param cause 
	 */
	public AlarmException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor method for the class AlarmException.java.
	 * @param message 
	 */
	public AlarmException(String message) {
		super(message);
	}

	/**
	 * Constructor method for the class AlarmException.java.
	 * @param cause 
	 */
	public AlarmException(Throwable cause) {
		super(cause);
	}

}
