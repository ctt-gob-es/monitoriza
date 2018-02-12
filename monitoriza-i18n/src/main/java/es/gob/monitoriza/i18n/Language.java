/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.util.Language.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class responsible for managing the access to language resources.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 21/12/2017.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 22 dic. 2017.
 */
package es.gob.monitoriza.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/** 
 * <p>Class responsible for managing the access to language resources.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 22 dic. 2017.
 */
public final class Language {
			
	/**
	 * Constructor method for the class Language.java. 
	 */
	private Language() {
	}

	/**
	 * Attribute that represents the list of messages.
	 */
	private static ResourceBundle msgReports;

	/**
	 * Attribute that represents the locale specified in the configuration.
	 */
	private static Locale currentLocale;

	/**
	 * Attribute that represents the name of property file for language configuration. 
	 */
	private static final String FILE_PROP_NAME = "Language";

	/**
	 * Attribute that represents the property that indicates the language used by the system. 
	 */
	private static final String LANGUAGE_ATT = "LANGUAGE";

	/**
	 * Attribute that represents the location of file that contains the messages of system. 
	 */
	private static final String CONTENT_LANGUAGE_PATH = "messages.monitoriza";

	static {
		currentLocale = new Locale(ResourceBundle.getBundle(FILE_PROP_NAME).getString(LANGUAGE_ATT));

		msgReports = ResourceBundle.getBundle(CONTENT_LANGUAGE_PATH, currentLocale);
	}

	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(msgReports.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResMonitoriza(String key) {
		return msgReports.getString(key);
	}

}
