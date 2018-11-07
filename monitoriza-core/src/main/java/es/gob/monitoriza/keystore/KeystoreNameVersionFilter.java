/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2016 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.ws.keystores.KeystoreNameVersionFilter.java.</p>
 * <b>Description:</b><p>Class that defines a filter to search in a directory the files
 * that match with the name of the specified keystore, without the version number.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>28/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.1, 15/09/2016.
 */
package es.gob.monitoriza.keystore;

import java.io.File;
import java.io.FilenameFilter;

import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that defines a filter to search in a directory the files
 * that match with the name of the specified keystore, without the version number.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.1, 15/09/2016.
 */
public class KeystoreNameVersionFilter implements FilenameFilter {

	/**
	 * Constant attribute that represents the string with the dot character
	 * in a regular expression.
	 */
	public static final String REGEX_DOT = "\\.";

	/**
	 * Constant attribute that represents a string with the character '-'.
	 */
	public static final String HYPHEN = "-";

	/**
	 * Attribute that represents the keystore name to identify.
	 */
	private String keystoreName = null;

	/**
	 * Attribute that represents the prefix with the timestamp added to the keystore name.
	 */
	private String prefix = null;

	/**
	 * Constructor method for the class KeystoreNameVersionFilter.java.
	 */
	private KeystoreNameVersionFilter() {
		super();
	}

	/**
	 * Constructor method for the class KeystoreNameVersionFilter.java.
	 * @param prefixTimestamp Prefix with the timestamp added to the keystore name. If it is
	 * unknown, then must be <code>null</code> and the prefix will not be considered.
	 * @param keystoreNameParam Name of the keystore to search. If this
	 * parameter is null or empty, be considered a empty string.
	 */
	public KeystoreNameVersionFilter(String prefixTimestamp, String keystoreNameParam) {
		this();
		prefix = prefixTimestamp;
		if (UtilsStringChar.isNullOrEmptyTrim(keystoreNameParam)) {
			keystoreName = UtilsStringChar.EMPTY_STRING;
		} else {
			keystoreName = keystoreNameParam;
		}

	}

	/**
	 * {@inheritDoc}
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public final boolean accept(File dir, String name) {

		boolean result = false;

		// Comprobamos que el nombre recibido no sea nulo o vacío.
		if (!UtilsStringChar.isNullOrEmptyTrim(name)) {

			// El nombre debe poder descomponerse en dos partes separadas
			// por un punto.
			String[ ] parts = name.split(REGEX_DOT);
			if (parts != null && parts.length == 2) {

				// Si ambas partes no son nulas...
				if (parts[0] != null && parts[1] != null) {

					// La primera parte debe dividirse en otras dos por un
					// guión.
					String[ ] subParts = parts[0].split(HYPHEN);
					if (parts != null && parts.length == 2) {

						// Si ambas subpartes no son nulas...
						if (subParts[0] != null && subParts[1] != null) {

							// La primera debe ser igual al prefijo (si este no
							// es nulo),
							// y la segunda al nombre del keystore a buscar.
							result = keystoreName.equals(subParts[1]);
							if (result && prefix != null) {
								result = prefix.equals(subParts[0]);
							}

						}

					}

					// Si el resultado de momento es correcto, comprobamos que
					// la última
					// parte sea un número.
					if (result) {

						try {
							Long.valueOf(parts[1]);
						} catch (NumberFormatException e) {
							result = false;
						}

					}

					// Si el nombre es correcto, hay que comprobar si se trata
					// de un
					// fichero y no de un directorio.
					if (result) {

						File file = new File(dir, name);
						result = file.isFile();

					}

				}

			}

		}

		return result;

	}

}
