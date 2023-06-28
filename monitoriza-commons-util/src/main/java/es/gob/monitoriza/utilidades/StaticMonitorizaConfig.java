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
 * es.gob.monitoriza.utilidades.StaticMonitorizaProperties.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class contains static properties of Monitoriz@. This properties are immutable and they can be modified only restarting the server context.
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
 * @version 1.3, 11/05/2022.
 */
package es.gob.monitoriza.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.ICommonsUtilLogMessages;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Class contains static properties of Monitoriz@. This properties are immutable and they can be modified only restarting the server context.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.3, 11/05/2022.
 */
public class StaticMonitorizaConfig {

	/**
	 * Attribute that represents set of properties of the platform.
	 */
	private static Properties staticProperties;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
		
	/**
	 * Attribute that represents name of properties file.
	 */
	public static final String STATIC_MONITORIZA_SYSTEM_PROPERTY = "monitoriza.properties.path";
			
	/**
	 * Constant attribute that represents name for property <i>"graylog.enabled"</i>.
	 */
	public static final String GRAYLOG_ENABLED = "graylog.enabled";

	/**
	 * Constant attribute that represents name for property <i>"graylog.destination.host"</i>.
	 */
	public static final String GRAYLOG_DESTINATION_HOST = "graylog.destination.host";

	/**
	 * Constant attribute that represents name for property <i>"graylog.destination.port"</i>.
	 */
	public static final String GRAYLOG_DESTINATION_PORT = "graylog.destination.port";

	/**
	 * Constant attribute that represents name for property <i>"graylog.field."</i>.
	 */
	public static final String GRAYLOG_FIELDS_PREFIX = "graylog.field.";
	
	/**
	 * Constant attribute that represents name for property <i>"system.keystore.password."</i>.
	 */
	public static final String SYSTEM_KEYSTORE_PASSWORD = "system.keystore.password";
	
	/**
	 * Constant attribute that represents name for property <i>"system.keystore.password."</i>.
	 */
	public static final String VALIDATION_AXIS2_SECURE_MODE_PROPERTY = "validation.axis2.secureMode";
	
	/**
     * Constant attribute that identifies the class name of the truststore.
     */
    private static final String TRUSTSTORE_CLASSNAME = "javax.net.ssl.trustStore";
    
    /**
     * Attribute that represents the time that the truststore was last modified.
     */
    private static long truststoreLastUpdate = -1;		
	
	/**
	 * Constructor method for the class StaticMonitorizaProperties.java.
	 */
	private StaticMonitorizaConfig() {
	}

	/**
	 * Gets all properties from original file.
	 * @return all properties
	 */
	private static Properties getProperties() {
		if (staticProperties == null) {
			synchronized (StaticMonitorizaConfig.class) {
				if (staticProperties == null) {
					staticProperties = new Properties();
					try (FileInputStream configStream = new FileInputStream(System.getProperty(STATIC_MONITORIZA_SYSTEM_PROPERTY));) {
						
						staticProperties.load(configStream);
						
					} catch (IOException e) {
						LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS007, new Object[ ] { System.getProperty(STATIC_MONITORIZA_SYSTEM_PROPERTY) }), e);
					} 
				}
				
				// Establecemos el nuevo valor de la propiedad que indica el
				// almacen de confianza usado
				// en conexiones seguras, en el caso de que existan.
				String propertyValue = staticProperties.getProperty(VALIDATION_AXIS2_SECURE_MODE_PROPERTY);
				if (propertyValue != null && propertyValue.trim().equalsIgnoreCase("true")) {
				   
				    setTruststore(staticProperties.getProperty("axis2.truststorepath"), staticProperties.getProperty("axis2.truststorepassword"));
				}
			}
		}		
		
		return staticProperties;
	}

	/**
	 * Returns the value of property given.
	 * @param propertyName name of @Firma property.
	 * @return the value of property given.
	 */
	public static String getProperty(String propertyName) {
		
		return (String) getProperties().get(propertyName);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Set<Entry<Object,Object>> getAllProperties() {
	
		return getProperties().entrySet();
		
	}
	
	
	/**
	 * Obtains a collection of static properties which key name start with the prefix given.
	 * @param prefix word placed in the beginning of the key name of property.
	 * @return a collection of static properties.
	 */
	public static Properties getProperties(final String prefix) {
		Properties result = new Properties();
		if (prefix != null) {
			for (Object key: getProperties().keySet()) {
				if (key != null && key.toString().startsWith(prefix)) {
					result.put(key, getProperties().get(key));
				}
			}
		}
		return result;
	}
	/**
	 * Method that returns the value of the system property tomcat.config.path.
	 * @return Value of the system property tomcat.config.path. Null if not exist.
	 */
	public static String getTomcatServerConfigDir() {
		return System.getProperty(StaticConstants.PROP_TOMCAT_SERVER_CONFIG_DIR);
	}
	
	/**
	 * Auxiliar method to create an absolute path to a file.
	 * @param pathDir Directory absolute path that contains the file.
	 * @param filename Name of the file.
	 * @return Absolute path of the file.
	 */
	public static String createAbsolutePath(String pathDir, String filename) {
		return pathDir + File.separator + filename;
	}
	
	/**
     * Method that updates the system properties used to define the truststore for the secure connections.
     * @param truststorePath Parameter that represents the path to the truststore.
     * @param truststorePass Parameter that represents the password of the truststore.
     */
	private static void setTruststore(String truststorePath, String truststorePass) {
		boolean isChanged;
		File truststoreFile;

		isChanged = false;
		truststoreFile = new File(truststorePath);

		if (truststorePath != null && !truststorePath.trim().equals("") && System.getProperty(TRUSTSTORE_CLASSNAME) != null && !System.getProperty(TRUSTSTORE_CLASSNAME).equals(truststorePath)) {
			LOGGER.debug(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.UTILS_AXIS2_TRUSTSTORE001, new Object[ ] { System.getProperty(TRUSTSTORE_CLASSNAME), truststorePath }));
			isChanged = true;
		} else if (!GeneralUtils.checkNullValues(truststorePass, System.getProperty("javax.net.ssl.trustStorePassword")) && !truststorePass.trim().equals("") && !System.getProperty("javax.net.ssl.trustStorePassword").equals(truststorePass)) {
			LOGGER.debug(Language.getResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.UTILS_AXIS2_TRUSTSTORE002));
			isChanged = true;
		} else if (truststoreLastUpdate != truststoreFile.lastModified()) {
			LOGGER.debug(Language.getResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.UTILS_AXIS2_TRUSTSTORE003));
			isChanged = true;
		}

		if (isChanged) {
			LOGGER.debug(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.UTILS_AXIS2_TRUSTSTORE004, new Object[ ] { truststorePath }));
			System.setProperty(TRUSTSTORE_CLASSNAME, truststorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", truststorePass);
			truststoreLastUpdate = truststoreFile.lastModified();
		}
	}

}
