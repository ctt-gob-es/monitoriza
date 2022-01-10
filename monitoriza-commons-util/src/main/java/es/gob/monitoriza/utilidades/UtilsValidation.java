/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://administracionelectronica.gob.es
*
* Copyright 2005-2019 Gobierno de España
* Este fichero se distribuye bajo las licencias EUPL versión 1.1 según las
* condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este 
* fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
*/

/** 
 * <b>File:</b><p>es.gob.eventmanager.utils.UtilsValidation.java.</p>
 * <b>Description:</b><p>Class with utilities for validate objects.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.utilidades;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

/** 
 * <p>Class with utilities for validate objects.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 10/01/2022.
 */
public final class UtilsValidation {

	/**
	 * Constructor method for the class UtilsValidation.java.
	 */
	private UtilsValidation() {

	}
	/**
	 * Checks if a list of values is valid (not null).
	 * @param values collection of values to validate.
	 * @return true if any parameter is null and false if all parameters are valid (not null).
	 */
	public static boolean checkNullValues(Object... values) {
		for (Object object: values) {
			if (object == null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Asserts whether a array is valid (not null and not empty).
	 * @param data array to validate.
	 * @return true if array is valid and false otherwise.
	 */
	public static boolean isValid(byte[ ] data) {

		if (data != null && data.length > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Asserts whether a Long object is valid (not null and is nonzero).
	 * @param data data to validate.
	 * @return true if object is valid and false otherwise.
	 */
	public static boolean isValid(Long data) {

		if (data != null && data != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Asserts whether a Integer object is valid (not null and is nonzero).
	 * @param data data to validate.
	 * @return true if object is valid and false otherwise.
	 */
	public static boolean isValid(Integer data) {

		if (data != null && data != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Asserts whether a map is valid (not null and not empty).
	 * @param data map to validate.
	 * @return true if map is valid and false otherwise.
	 */
	public static boolean isValid(Map<?, ?> data) {

		if (data == null || data.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Asserts whether a collection is valid (not null and not empty).
	 * @param data collection to validate.
	 * @return true if collection is valid and false otherwise.
	 */
	public static boolean isValid(Collection<?> data) {

		if (data == null || data.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a collection is valid (not null and/or not empty).
	 * @param values collection of values to validate.
	 * @return true if all values are valid and false otherwise.
	 */
	public static boolean isValid(Object... values) {
		boolean isValid = true;
		for (int i = 0; i < values.length && isValid; i++) {
			Object object = values[i];
			if (object == null) {
				return false;
			} else {
				if (object instanceof String) {
					isValid = !UtilsStringChar.isNullOrEmptyTrim(object.toString());
				} else if (object instanceof Map<?, ?>) {
					isValid = isValid((Map<?, ?>) object);
				} else if (object instanceof Collection<?>) {
					isValid = isValid((Collection<?>) object);
				} else if (object.getClass().isArray()) {
					isValid = Array.getLength(object) > 0;
				}
			}
		}
		return isValid;
	}

	/**
	 * Checks if a string is a number.
	 * @param number Parameter that represent string to check.
	 * @return true if the string is a number and false otherwise.
	 */
	public static boolean isNumeric(String number) {
		if (isValid(number)) {
			return number.matches("-?\\d+(\\.\\d+)?");
		} else {
			return false;
		}
	}

	/**
	 * Checks if a string is a number of type integer. 
	 * @param number Parameter that represent string to check.
	 * @return true if the string is a number of type integer and false otherwise.
	 */
	public static boolean isInteger(String number) {
		try {
			if (isValid(number)) {
				Integer.parseInt(number);
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Method that checks if a parameter is a symmetric key.
	 * @param symmetricKey Parameter that represents the symmetric key.
	 * @throws NumberFormatException If the value isn't a valid symmetric key.
	 */
	public static void validateSymmetricKey(String symmetricKey) throws NumberFormatException {
		StringTokenizer tripleDes = new StringTokenizer(symmetricKey, ",");
		byte[ ] tripleDESKey = new byte[tripleDes.countTokens()];

		int j = 0;
		while (tripleDes.hasMoreTokens()) {
			String ss = tripleDes.nextToken();
			tripleDESKey[j] = (byte) Integer.decode(ss).intValue();
			j++;
		}
	}

	/**
	 * Method that checks if a parameter is an OID.
	 * @param value Parameter that represents the OID.
	 * @throws GSSException If the string is incorrectly formatted.
	 */
	public static void validateOID(String value) throws GSSException {
		new Oid(value);
	}

}
