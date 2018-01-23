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
 * es.gob.monitoriza.util.StaticMonitorizaProperties.java.
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
 * @version 1.0, 22 dic. 2017.
 */
package es.gob.monitoriza.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;


/** 
 * <p>Class contains static properties of Monitoriz@. This properties are immutable and they can be modified only restarting the server context.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 22 dic. 2017.
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
	 * Constructor method for the class StaticEVisorToPDFWSProperties.java.
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
						LOGGER.error("Error al cargar el archivo de propiedades de openoffice con ruta: " + System.getProperty(STATIC_MONITORIZA_SYSTEM_PROPERTY));
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
}
