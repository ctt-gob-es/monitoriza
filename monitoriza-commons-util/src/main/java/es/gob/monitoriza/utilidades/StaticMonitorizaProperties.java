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
 * @version 1.2, 10/10/2018.
 */
package es.gob.monitoriza.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.ICommonsUtilLogMessages;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Class contains static properties of Monitoriz@. This properties are immutable and they can be modified only restarting the server context.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.2, 10/10/2018.
 */
public class StaticMonitorizaProperties {

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
	 * Constant attribute that represents name for property <code>monitoriza.afirma.dssafirmaverify.wsdl</code>.
	 */
	public static final String MONITORIZA_AFIRMA_DSSAFIRMAVERIFY_WSDL= "monitoriza.afirma.dssafirmaverify.wsdl";
	
	/**
	 * Constructor method for the class StaticMonitorizaProperties.java.
	 */
	private StaticMonitorizaProperties() {
	}

	/**
	 * Gets all properties from original file.
	 * @return all properties
	 */
	private static Properties getProperties() {
		if (staticProperties == null) {
			synchronized (StaticMonitorizaProperties.class) {
				if (staticProperties == null) {
					staticProperties = new Properties();
					try (FileInputStream configStream = new FileInputStream(System.getProperty(STATIC_MONITORIZA_SYSTEM_PROPERTY));) {
						
						staticProperties.load(configStream);
						
					} catch (IOException e) {
						LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS007, new Object[ ] { System.getProperty(STATIC_MONITORIZA_SYSTEM_PROPERTY) }), e);
					} 
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
}
