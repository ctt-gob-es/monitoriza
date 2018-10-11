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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.GeneralUtils.java.</p>
 * <b>Description:</b><p> Class that contains general utils methods.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 10/10/2018.
 */
package es.gob.monitoriza.utilidades;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.ICommonsUtilLogMessages;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Class that contains general utils methods.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 10/10/2018.
 */
public class GeneralUtils {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the format of the date time strings. 
	 */
	public static final String LOCAL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
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
			LOGGER.error(Language.getResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS006));
			return null;
		}
		return values;
	}
	
	/**
	 * 
	 * @param serviceId
	 * @return
	 */
	public static String getSystemName(final String serviceId) {
		
		String nombreSistema;
		
		if (serviceId.contains(GeneralConstants.RFC3161_SERVICE) || serviceId.contains(GeneralConstants.TIMESTAMP_SERVICE)) {
			nombreSistema = GeneralConstants.PLATFORM_TSA;
		} else {
			nombreSistema = GeneralConstants.PLATFORM_AFIRMA;
		}
		
		return nombreSistema;
	}
	
	/**
	 * Gets the String representation of the given local date time.
	 * @param localDateTime
	 * @return String that represents the local date time
	 */
	public static String getFormattedDateTime(final LocalDateTime localDateTime) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT);

        return localDateTime.format(formatter);
	}

}
