/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.exceptions.InvokerException.java.</p>
 * <b>Description:</b><p> Class that defines the exception object of the Invoker class.</p>
 * <b>Project:</b><p>Autotester</p>
 * <b>Date:</b><p>9/1/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 9/1/2018.
 */
package es.gob.monitoriza.afirma.exception;


/** 
 * <p>Class that defines the exception object of the Invoker class.</p>
 * * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 12/01/2018.
 */
public class InvokerException extends Exception {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 998733761989614093L;

	/**
	 * Constructor method for the class InvokerException.java. 
	 */
	public InvokerException() {
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message 
	 */
	public InvokerException(String message) {
		super(message);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param cause 
	 */
	public InvokerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 */
	public InvokerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 * @param enableSuppression indicates if it should enable the suppression.
	 * @param writableStackTrace  indicates if it should write the stack trace.
	 */
	public InvokerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
