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
 * <b>File:</b><p>es.gob.afirma.utilidades.UtilsStringChar.java.</p>
 * <b>Description:</b><p>Class that provides functionality for managing strings and characters.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>28/01/2009.</p>
 * @author Gobierno de España.
 * @version 1.3, 07/09/2016.
 */
package es.gob.monitoriza.utilidades;

import org.apache.commons.codec.binary.Hex;

/**
 * <p>Class that provides functionality for managing strings and characters.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.3, 07/09/2016.
 */
public final class UtilsStringChar {

	/**
	 * Constructor method for the class UtilsStringChar.java.
	 */
	private UtilsStringChar() {
		super();
	}

	/**
	 * Constant attribute that represents the string to identify the empty string.
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * Constant attribute that represents the char to identify the blank space character.
	 */
	public static final char SPECIAL_BLANK_SPACE = ' ';

	/**
	 * Constant attribute that represents the string to identify the blank space character.
	 */
	public static final String SPECIAL_BLANK_SPACE_STRING = String.valueOf(SPECIAL_BLANK_SPACE);

	/**
	 * Constant attribute that represents the char to identify the line break character.
	 */
	public static final char SPECIAL_LINE_BREAK = '\n';

	/**
	 * Constant attribute that represents the string to identify the line break character.
	 */
	public static final String SPECIAL_LINE_BREAK_STRING = String.valueOf(SPECIAL_LINE_BREAK);

	/**
	 * Constant attribute that represents the char to identify the line start character.
	 */
	public static final char SPECIAL_LINE_START = '\r';

	/**
	 * Constant attribute that represents the string to identify the line start character.
	 */
	public static final String SPECIAL_LINE_START_STRING = String.valueOf(SPECIAL_LINE_START);

	/**
	 * Constant attribute that represents the char to identify the tabulation character.
	 */
	public static final char SPECIAL_TABULATION = '\t';

	/**
	 * Constant attribute that represents the string to identify the tabulation character.
	 */
	public static final String SPECIAL_TABULATION_STRING = String.valueOf(SPECIAL_TABULATION);

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>#</code>.
	 */
	public static final char SYMBOL_PAD = '#';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>:</code>.
	 */
	public static final char SYMBOL_COLON = ':';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>;</code>.
	 */
	public static final char SYMBOL_SEMICOLON = ';';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>=</code>.
	 */
	public static final char SYMBOL_EQUAL = '=';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>-</code>.
	 */
	public static final char SYMBOL_MINUS = '-';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>,</code>.
	 */
	public static final char SYMBOL_COMMA = ',';

	/**
	 * Constant attribute that represents the string to identify the symbol: <code>,</code>.
	 */
	public static final String SYMBOL_COMMA_STRING = String.valueOf(SYMBOL_COMMA);

	/**
	 * Constant attribute that represents the array with all the characters defined as constants in this class.
	 */
	public static final char[ ] CHARACTERS_SET = new char[ ] {
															  // UtilsStringChar.SPECIAL_BLANK_SPACE,
	UtilsStringChar.SPECIAL_LINE_BREAK, UtilsStringChar.SPECIAL_LINE_START, UtilsStringChar.SPECIAL_TABULATION, UtilsStringChar.SYMBOL_PAD, UtilsStringChar.SYMBOL_COLON, UtilsStringChar.SYMBOL_SEMICOLON, UtilsStringChar.SYMBOL_EQUAL };

	/**
	 * Constant attribute that represents the token '0'.
	 */
	private static final char TOKEN_ZERO = '0';

	/**
	 * Method that checks whether a string is null or empty.
	 * @param str Parameter that represents the string to process.
	 * @return a boolean with <code>true</code> value whether the string is null or empty, or <code>false</code> value in another case.
	 */
	public static boolean isNullOrEmpty(String str) {

		if (str == null || str.isEmpty()) {

			return true;

		}
		return false;
	}

	/**
	 * Method that checks whether a string is null or empty after to apply the method {@link String#trim()} on.
	 * @param str Parameter that represents the string to process.
	 * @return a boolean with <code>true</code> value whether the string is null or empty, or <code>false</code> value in another case.
	 */
	public static boolean isNullOrEmptyTrim(String str) {

		if (str == null || str.trim().isEmpty()) {

			return true;

		}

		return false;

	}

	/**
	 * Method that checks whether a string contains some of the characters contained in {@link #CHARACTERS_SET} (true) or not (false).
	 * @param str Parameter that represents the string to process.
	 * @return a boolean that indicates whether a string contains some of the characters contained in {@link #CHARACTERS_SET}
	 * (true) or not (false).
	 */
	public static boolean containsSomeOfTheCharacterSet(String str) {

		return containsSomeOfTheCharacterSetInTheSet(str, UtilsStringChar.CHARACTERS_SET);

	}

	/**
	 * Method that checks whether a string contains some of the characters contained in certain characters array (true) or not (false).
	 * @param str Parameter that represents the string to process.
	 * @param characterSet Parameter that represents the characters array to process.
	 * @return a boolean that indicates whether a string contains some of the characters contained in certain characters array
	 * (true) or not (false).
	 */
	public static boolean containsSomeOfTheCharacterSetInTheSet(String str, char[ ] characterSet) {

		// Si la cadena de entrada es nula o vacía, devolvemos false.
		if (UtilsStringChar.isNullOrEmpty(str)) {
			return false;
		}

		// Si el conjunto de caracteres es nulo o vacío, devolvemos true.
		if (characterSet == null || characterSet.length == 0) {
			return true;
		}

		// Recorremos todos los caracteres y comprobamos si alguno está
		// contenido
		// en la cadena que se recibe como parámetro.
		for (int index = 0; index < characterSet.length; index++) {

			char actualChar = characterSet[index];
			if (str.indexOf(actualChar) >= 0) {

				return true;

			}

		}

		// Si llegamos aquí, es que no contiene ninguno de los caracteres.
		return false;

	}

	/**
	 * Method that converts a byte array to a hexadecimal string.
	 * @param data Parameter that represents the byte array to convert.
	 * @param separate Parameter that indicates if to separate each two bytes with {@link #SYMBOL_MINUS} (true) or not (false).
	 * @return an string with hexadecimal format.
	 */
	public static String convertByteArrayToHex(byte[ ] data, boolean separate) {
		char[ ] c = Hex.encodeHex(data);
		if (c == null || c.length == 0) {
			return null;
		}
		StringBuilder resultSB = new StringBuilder();
		byte numChars = 0;
		if (c.length % 2 != 0) {
			resultSB.append(TOKEN_ZERO);
			numChars++;
		}
		for (int i = 0; i < c.length; i++) {
			resultSB.append(c[i]);
			if (separate) {
				numChars++;
				if (numChars % 2 == 0 && i < c.length - 1) {
					resultSB.append(UtilsStringChar.SYMBOL_MINUS);
				}
			}
		}
		String result = resultSB.toString();
		UtilsResource.cleanStringBuilder(resultSB);
		return result;
	}

	/**
	 * Removes all the blanks from the input string.
	 * It is considered how blanks: {@link UtilsStringChar#SPECIAL_BLANK_SPACE_STRING}, {@link UtilsStringChar#SPECIAL_LINE_BREAK_STRING},
	 * {@link UtilsStringChar#SPECIAL_LINE_START_STRING} and {@link UtilsStringChar#SPECIAL_TABULATION_STRING}.
	 * @param string String from what removes the blanks.
	 * @return a empty string if the input parameter is <code>null</code>, otherwise, the same input
	 * string after removes the blanks.
	 */
	public static String removeBlanksFromString(String string) {

		String result = EMPTY_STRING;

		// Si la cadena a tratar no es nula...
		if (string != null) {

			// Eliminamos los espacios por delante y detrás.
			result = string.trim();
			// Eliminamos los saltos de línea.
			result = result.replaceAll(SPECIAL_LINE_BREAK_STRING, EMPTY_STRING);
			// Eliminamos los indicadores de nueva línea.
			result = result.replaceAll(SPECIAL_LINE_START_STRING, EMPTY_STRING);
			// Eliminamos las tabulaciones.
			result = result.replaceAll(SPECIAL_TABULATION_STRING, EMPTY_STRING);
			// Eliminamos los espacios en blanco.
			result = result.replaceAll(SPECIAL_BLANK_SPACE_STRING, EMPTY_STRING);

		}

		return result;

	}

}
