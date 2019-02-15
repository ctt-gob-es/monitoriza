/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the  terms
 * of the European Public License (EUPL), either version 1.1 or (at your option)
 * any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * more details.
 * You should have received a copy of the EUPL1.1 license
 * along with this program; if not, you may find it at
 * http:joinup.ec.europa.eu/software/page/eupl/licence-eupl
 ******************************************************************************/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.i18n.Language.java.
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
 * @version 1.1, 15/02/2019.
 */
package es.gob.monitoriza.i18n;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import es.gob.monitoriza.i18n.utils.UtilsTomcat;

/** 
 * <p>Class responsible for managing the access to language resources.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.1, 15/02/2019.
 */
public final class Language {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(Language.class);
			
	/**
	 * Constructor method for the class Language.java. 
	 */
	private Language() {
		super();
	}
	
	/**
	 * Attribute that represents the locale specified in the configuration.
	 */
	private static Locale currentLocale;
	
	/**
	 * Attribute that represents the url class loader for the messages files.
	 */
	private static URLClassLoader urlClassLoaderMessages = null;
	
	/**
	 * Constant attribute that represents the name of messages directory inside configuration directory.
	 */
	private static final String MESSAGES_DIRECTORY = "messages";

	/**
	 * Constant attribute that represents the string to identify the the bundle name for the file with the application language.
	 */
	private static final String BUNDLENAME_LANGUAGE = "Language";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with the status app servlet logs.
	 */
	private static final String BUNDLENAME_STATUS = "status.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with the alarm module.
	 */
	private static final String BUNDLENAME_ALARM = "alarm.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with core logs.
	 */
	private static final String BUNDLENAME_CORE = "core.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with persistence logs.
	 */
	private static final String BUNDLENAME_PERSISTENCE = "persistence.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with web admin logs.
	 */
	private static final String BUNDLENAME_WEBADMIN = "webAdmin.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with core logs.
	 */
	private static final String BUNDLENAME_COMMONUTILS = "commonsUtils.monitoriza";
	
	/**
	 * Constant attribute that represents the string to identify the bundle name to the file related with core logs.
	 */
	private static final String BUNDLENAME_RESTGENERAL = "rest.monitoriza";

	/**
	 * Constant attribute that represents the key for the configured locale for the platform.
	 */
	private static final String LANGUAGE = "LANGUAGE";
	
	/**
	 * Attribute that represents the properties for the locale for the servlet status bundle messages.
	 */
	private static ResourceBundle resStatusAppBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the alarm bundle messages.
	 */
	private static ResourceBundle resAlarmBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the core bundle messages.
	 */
	private static ResourceBundle resCoreBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the core bundle messages.
	 */
	private static ResourceBundle resPersistenceBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the core bundle messages.
	 */
	private static ResourceBundle resWebAdminBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the core bundle messages.
	 */
	private static ResourceBundle resCommonsUtilsBundle = null;
	
	/**
	 * Attribute that represents the properties for the locale for the core bundle messages.
	 */
	private static ResourceBundle resRestGeneralBundle = null;
	

	static {
		//currentLocale = new Locale(ResourceBundle.getBundle(BUNDLENAME_LANGUAGE).getString(LANGUAGE));

		//monitoriza = ResourceBundle.getBundle(CONTENT_LANGUAGE_PATH, currentLocale);
		
		// Preparamos el URLClassLoader que hará referencia
		// al directorio de los mensajes de logs dentro de la configuración.
		try {
			final File configDirFile = new File(UtilsTomcat.createAbsolutePath(UtilsTomcat.getTomcatConfigDir(), MESSAGES_DIRECTORY));
			urlClassLoaderMessages = AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {

				public URLClassLoader run() {
					try {
						return new URLClassLoader(new URL[ ] { configDirFile.toURI().toURL() });
					} catch (MalformedURLException e) {
						throw new RuntimeException(e);
					}
				}
			});
			reloadMessagesConfiguration();
		} catch (RuntimeException e) {
			LOGGER.error(e);
		}
				
	}
	
	/**
	 * Method that loads the configured locale and reload the text messages.
	 */
	public static void reloadMessagesConfiguration() {

		boolean takeDefaultLocale = false;
		String propLocale = null;

		// Cargamos el recurso que determina el locale.
		ResourceBundle resLocale = ResourceBundle.getBundle(BUNDLENAME_LANGUAGE, Locale.getDefault(), urlClassLoaderMessages);
		if (resLocale == null) {
			takeDefaultLocale = true;
		} else {
			propLocale = resLocale.getString(LANGUAGE);
		}

		// Tratamos de inicializar el Locale.
		if (propLocale == null) {

			takeDefaultLocale = true;

		} else {

			propLocale = propLocale.trim();
			String[ ] localeSplit = propLocale.split("_");
			if (localeSplit == null || localeSplit.length != 2) {

				takeDefaultLocale = true;

			} else {

				currentLocale = new Locale(localeSplit[0], localeSplit[1]);

			}

		}

		// Si hay que tomar el locale por defecto...
		if (takeDefaultLocale) {

			LOGGER.error("No property was obtained correctly determining the Locale for log messages. Will take the default locale.");
			currentLocale = Locale.getDefault();

		}

		// Se informa en el log del Locale selecccionado.
		LOGGER.info("Take the next locale for messages logs: " + currentLocale.toString());
		
		// Se cargan los mensajes del módulo del servlet de estados.
		resStatusAppBundle = ResourceBundle.getBundle(BUNDLENAME_STATUS, currentLocale, urlClassLoaderMessages);
		
		// Se cargan los mensajes del módulo de alarmas.
		resAlarmBundle = ResourceBundle.getBundle(BUNDLENAME_ALARM, currentLocale, urlClassLoaderMessages);

		// Se cargan los mensajes del módulo del core.
		resCoreBundle = ResourceBundle.getBundle(BUNDLENAME_CORE, currentLocale, urlClassLoaderMessages);
				
		// Se cargan los mensajes del módulo de persistencia.
		resPersistenceBundle = ResourceBundle.getBundle(BUNDLENAME_PERSISTENCE, currentLocale, urlClassLoaderMessages);
		
		// Se cargan los mensajes del módulo de administración web.
		resWebAdminBundle = ResourceBundle.getBundle(BUNDLENAME_WEBADMIN, currentLocale, urlClassLoaderMessages);
		
		// Se cargan los mensajes del módulo utilidades.
		resCommonsUtilsBundle = ResourceBundle.getBundle(BUNDLENAME_COMMONUTILS, currentLocale, urlClassLoaderMessages);
		
		// Se cargan los mensajes del módulo rest.
		resRestGeneralBundle = ResourceBundle.getBundle(BUNDLENAME_RESTGENERAL, currentLocale, urlClassLoaderMessages);

	}

	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resStatusAppBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResMonitoriza(String key) {
		return resStatusAppBundle.getString(key);
	}
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResAlarmMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resAlarmBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResAlarmMonitoriza(String key) {
		return resAlarmBundle.getString(key);
	}
		
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResWebMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resWebAdminBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResWebMonitoriza(String key) {
		return resWebAdminBundle.getString(key);
	}
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResCoreMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resCoreBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResCoreMonitoriza(String key) {
		return resCoreBundle.getString(key);
	}
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResPersistenceMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resPersistenceBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResPersistenceMonitoriza(String key) {
		return resPersistenceBundle.getString(key);
	}
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResCommonsUtilsMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resCommonsUtilsBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResCommonsUtilsMonitoriza(String key) {
		return resCommonsUtilsBundle.getString(key);
	}
		
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResRestGeneralMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(resRestGeneralBundle.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResRestGeneralMonitoriza(String key) {
		return resRestGeneralBundle.getString(key);
	}
	
}
