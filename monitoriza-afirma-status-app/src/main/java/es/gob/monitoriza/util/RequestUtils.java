/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.utils.PetitionUtils.java.</p>
 * <b>Description:</b><p> Utilities class that contents method related with SOAP petitions and XML content.</p>
 * <b>Project:</b><p>Autotester</p>
 * <b>Date:</b><p>10/1/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/1/2018.
 */
package es.gob.monitoriza.util;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p> Utilities class that contents method related with SOAP petitions and XML content.</p>
 * * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 12/01/2018.
 */
public final class RequestUtils {
	
	/**
	 * Constructor method for the class PetitionUtils.java. 
	 */
	private RequestUtils() {
	}


	/**
	 * Method that gets the Id of the message given its name. The file name must follow the format XXXXX_NNNNNNNNN.xml
	 * 
	 * @param name File name.
	 * @return the Id of the message or null if the file name doesn't follow the format.
	 */
	public static String getTransacctionIdGivenFileName(String name) {
		StringBuilder sb = new StringBuilder(name);
		Integer index = sb.indexOf(GeneralConstants.SEPARATOR);
		sb.delete(0, index + 1);
		index = sb.indexOf(GeneralConstants.DOT);
		sb.delete(index, sb.length());
		return sb.toString();
	}
	
}
