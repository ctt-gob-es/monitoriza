/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2017 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.utilidades.UtilsASN1.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>11/05/2012.</p>
 * @author Gobierno de España.
 * @version 1.4, 01/09/2017.
 */
package es.gob.monitoriza.utilidades;

import javax.security.auth.x500.X500Principal;

/**
 * <p>Class that contains all utilities methods used in ASN1 Objects.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.4, 01/09/2017.
 */
public final class ASN1Utilities {

	/**
	 * Constant that defines an email address ASN1 oid.
	 */
	public static final String EMAIL_ADDRESS_OID = "1.2.840.113549.1.9.1";

	/**
	 * Constant that defines an email address ASN1 name.
	 */
	public static final String EMAIL_ADDRESS_NAME = "emailAddress";

	/**
	 * Constant that defines an email address ASN1 short name.
	 */
	public static final String EMAIL_ADDRESS_SHORT_NAME = "EMAIL";

	/**
	 * Constant that defines a postal code ASN1 oid.
	 */
	public static final String POSTAL_CODE_OID = "2.5.4.17";

	/**
	 * Constant that defines a postal code ASN1 name.
	 */
	public static final String POSTAL_CODE_NAME = "postalCode";

	/**
	 * Constant that defines a postal code ASN1 short name.
	 */
	public static final String POSTAL_CODE_SHORT_NAME = "postalCode";

	/**
	 * Constant that defines a serial number ASN1 oid.
	 */
	public static final String SERIAL_NUMBER_OID = "2.5.4.5";

	/**
	 * Constant that defines a serial number ASN1 name.
	 */
	public static final String SERIAL_NUMBER_NAME = "serialNumber";

	/**
	 * Constant that defines a serial number ASN1 short name.
	 */
	public static final String SERIAL_NUMBER_SHORT_NAME = "SERIALNUMBER";

	/**
	 * Constant that defines a given name ASN1 oid.
	 */
	public static final String GIVEN_NAME_OID = "2.5.4.42";

	/**
	 * Constant that defines a given name ASN1 name.
	 */
	public static final String GIVEN_NAME_NAME = "givenName";

	/**
	 * Constant that defines a given name ASN1 short name.
	 */
	public static final String GIVEN_NAME_SHORT_NAME = "givenName";

	/**
	 * Constant that defines a surname ASN1 oid.
	 */
	public static final String SURNAME_OID = "2.5.4.4";

	/**
	 * Constant that defines a surname ASN1 name.
	 */
	public static final String SURNAME_NAME = "surname";

	/**
	 * Constant that defines a surname ASN1 short name.
	 */
	public static final String SURNAME_SHORT_NAME = "SN";

	/**
	 * Constructor method for the class ASN1Utilities.java.
	 */
	private ASN1Utilities() {
		super();
	}

	/**
	 * Method that obtains the content of a {@link X500Principal} object on string format.
	 * @param name Parameter that represents the object to be processed.
	 * @return the content of the {@link X500Principal} object on string format.
	 * @throws CommonUtilsException If the method fails.
	 */
	public static String toString(X500Principal name) throws Exception {
		
		return name.getName(X500Principal.RFC2253);
		
	}
	

	/**
	 * Method that removes the unicode characters from a string.
	 * @param stringToProcess Parameter that represents the processed string.
	 * @return the string without unicode characters.
	 */
	public static String removeUNICODECharacters(String stringToProcess) {
		String result = "";
		if (stringToProcess != null) {
			result = stringToProcess.replaceAll("[^\\p{Print}]", "");
		}
		return result;
	}

}
