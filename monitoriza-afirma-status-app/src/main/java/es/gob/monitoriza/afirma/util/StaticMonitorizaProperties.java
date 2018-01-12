// Copyright (C) 2018, Gobierno de España
// This program is licensed and may be used, modified and redistributed under the terms
// of the European Public License (EUPL), either version 1.1 or (at your
// option) any later version as soon as they are approved by the European Commission.
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// or implied. See the License for the specific language governing permissions and
// more details.
// You should have received a copy of the EUPL1.1 license
// along with this program; if not, you may find it at
// http://joinup.ec.europa.eu/software/page/eupl/licence-eupl

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.afirma.util.StaticMonitorizaProperties.java.
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
package es.gob.monitoriza.afirma.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;


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
	 * Attribute that represents the class logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(StaticMonitorizaProperties.class);
		
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
}
