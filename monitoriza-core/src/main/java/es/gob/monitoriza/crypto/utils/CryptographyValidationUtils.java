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
 * <b>File:</b><p>es.gob.afirma.cryptography.utils.CryptographyValidationUtils.java.</p>
 * <b>Description:</b><p>Class with utilities for the validation of objects for cryptography module.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>15/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.0, 15/06/2012.
 */
package es.gob.monitoriza.crypto.utils;

import java.util.List;

import es.gob.monitoriza.crypto.exception.CryptographyException;

/**
 * <p>Class with utilities for the validation of objects for cryptography module.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 15/06/2012.
 */
public final class CryptographyValidationUtils {
		
	/**
	 * Constructor method for the class CryptographyValidationUtils.java.
	 */
	private CryptographyValidationUtils() {

	}

	/**
	 * Method that checks if an object is null or not.
	 * @param o Parameter that represents the object to check.
	 * @param msg Parameter that represents the log message if the object is null.
	 * @throws CryptographyException If the object is null.
	 */
	public static void checkIsNotNull(Object o, String msg) throws CryptographyException {
		if (o == null) {
			throw new CryptographyException(msg);
		}
	}
	
	/**
	 * Method that checks if a list is null or empty, or not.
	 * @param list Parameter that represents the list to check.
	 * @param msg Parameter that represents the log message if the list is null or empty.
	 * @throws CryptographyException If the list is null or empty.
	 */
	public static void checkIsNotNullAndNotEmpty(List<?> list, String msg) throws CryptographyException {
		if (list == null || list.size() == 0) {
			throw new CryptographyException(msg);
		}
	}
		
}
