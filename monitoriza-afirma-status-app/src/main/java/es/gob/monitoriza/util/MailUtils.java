/* 
/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.util.MailUtils.java.</p>
 * <b>Description:</b><p> Utilities class that contains necessary method for every class related with mails.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>23/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2018.
 */
package es.gob.monitoriza.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.ServiceStatus;

import java.util.Set;

/** 
 * <p>Utilities class that contains necessary method for every class related with mails.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 25/01/2018.
 */
public final class MailUtils {

	/**
	 * Constant that represents the regular expression for the mail addressees properties.
	 */
	private static final String regExpMailsAddressProp = "(mail.attribute.address)\\d*";

	/**
	 * Constructor method for the class MailUtils.java. 
	 */
	private MailUtils() {
	}

	/**
	 * Method that given a string and a separator, split the string and remove the spaces.
	 * @param string Element to split.
	 * @param separator String that will be used as splitter.
	 * @return a list with the elements split.
	 */
	public static List<String> fromStringToListstring(String string, String separator) {
		List<String> res = new ArrayList<String>();
		// Separamos los elementos.
		String[ ] rawElems = string.split(separator);
		for (String elem: rawElems) {
			// Eliminamos los posibles espacios.
			res.add(elem.trim());
		}
		return res;
	}

	/**
	 * Method that obtains the list of email addressees from the static property file.
	 * @return a list with the mails addressees.
	 */
	public static List<String> getListAddressees() {
		List<String> res = new ArrayList<String>();
		// Recorremos todas las propiedades
		Set<Entry<Object, Object>> properties = StaticMonitorizaProperties.getAllProperties();
		for (Entry<Object, Object> property: properties) {
			// Si coincide la key de la propiedad con la expresión regular
			// definida, lo añadimos a la lista.
			if (property.getKey().toString().matches(regExpMailsAddressProp)) {
				res.add(property.getValue().toString());
			}
		}
		return res;
	}

	/**
	 * Method that get the list of email addressees for a given service.
	 * 
	 * @param serviceIdentifier Name of the service associated to the alarm.
	 * @return a list with the mails addressees.
	 */
	public static List<String> getListAddresseesForAlarm(String[ ] serviceIdentifier) {
		List<String> res = new ArrayList<String>();
		if (serviceIdentifier != null && serviceIdentifier.length == 2) {
			String serviceName = serviceIdentifier[0];
			String serviceStatus = serviceIdentifier[1];
			String addrKey = null;
			if (serviceStatus.equals(ServiceStatus.DEGRADADO)) {
				addrKey = GeneralConstants.SERVICE + GeneralConstants.DOT + serviceName.toLowerCase() + GeneralConstants.DOT + GeneralConstants.DEGENERATED + GeneralConstants.DOT + GeneralConstants.MAIL_ADDRESS;
			} else if (serviceStatus.equals(ServiceStatus.CAIDO)) {
				addrKey = GeneralConstants.SERVICE + GeneralConstants.DOT + serviceName.toLowerCase() + GeneralConstants.DOT + GeneralConstants.DOWNED + GeneralConstants.DOT + GeneralConstants.MAIL_ADDRESS;
			} else {
				return null;
			}
			String addrValues = StaticMonitorizaProperties.getProperty(addrKey);
			String[ ] values = addrValues.split(GeneralConstants.SLASH);
			for (String value: values) {
				res.add(StaticMonitorizaProperties.getProperty(value));
			}
			return res;
		}
		return null;
	}
}
