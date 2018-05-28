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
public final class LanguageWeb {
			
	/**
	 * Constructor method for the class Language.java. 
	 */
	private LanguageWeb() {
	}

		
	/**
	 * Attribute that represents the list of messages.
	 */
	private static ResourceBundle webmonitoriza;

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
	private static final String CONTENT_WEB_LANGUAGE_PATH = "messages.webmonitoriza";

	static {
		currentLocale = new Locale(ResourceBundle.getBundle(FILE_PROP_NAME).getString(LANGUAGE_ATT));
		
		webmonitoriza = ResourceBundle.getBundle(CONTENT_WEB_LANGUAGE_PATH, currentLocale);
	}
	
	
	/**
	 * Gets the message with the key and values indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @param values Values for insert in the message.
	 * @return String with the message well-formed.
	 */
	public static String getFormatResWebMonitoriza(String key, Object[ ] values) {
		return new MessageFormat(webmonitoriza.getString(key), currentLocale).format(values);
	}

	/**
	 * Gets the message with the key indicated as input parameters.
	 * @param key Key for obtain the message.
	 * @return String with the message.
	 */
	public static String getResWebMonitoriza(String key) {
		return webmonitoriza.getString(key);
	}

}
