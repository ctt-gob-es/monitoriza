/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.util.GeneralUtils.java.</p>
 * <b>Description:</b><p> Class that contains general utils methods.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package es.gob.monitoriza.utilidades;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class that contains general utils methods.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24/01/2018.
 */
public class GeneralUtils {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	
	/**
	 * Method that gets the values of the defined constants of a given class.
	 * @param classz Class where searching the values.
	 * @param fieldsAvoided List of names constants that will be ignored in the process.
	 * @return A string list with the values required. 
	 */
	public static List<String> getValuesOfConstants(Class<?> classz, List<String> fieldsAvoided) {
		List<String> values = new ArrayList<String>();
		try {
			Field[ ] fields = classz.getFields();
			for (Field field: fields) {
				if (fieldsAvoided == null || !fieldsAvoided.contains(field.getName())) {
					values.add(field.get(null).toString());
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		return values;
	}

}
