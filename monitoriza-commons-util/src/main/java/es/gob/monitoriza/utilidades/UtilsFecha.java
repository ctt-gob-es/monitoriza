/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2015 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.utilidades.UtilsFecha.java.</p>
 * <b>Description:</b><p>Class that provides methods for managing dates.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>25/10/2005.</p>
 * @author Gobierno de España.
 * @version 1.2, 13/08/2019.
 */
package es.gob.monitoriza.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p>Class that provides methods for managing dates.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * @version 1.2, 13/18/2019.
 */
public class UtilsFecha {

	/**
	 * Constant attribute that represents the date format <code>yyyy-MM-dd EEE HH:mm:ss ZZZZ</code>.
	 */
	public static final String FORMATO_COMPLETO = "yyyy-MM-dd EEE HH:mm:ss ZZZZ";

	/**
	 * Constant attribute that represents the date format <code>yyyy-MM-dd</code>.
	 */
	public static final String FORMATO_FECHA = "yyyy-MM-dd";

	/**
	 * Constant attribute that represents the date format <code>HH:mm:ss</code>.
	 */
	public static final String FORMATO_HORA = "HH:mm:ss";

	/**
	 * Constant attribute that represents the date format <code>yyyy-MM-dd HH:mm:ss</code>.
	 */
	public static final String FORMATO_FECHA_HORA = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Constant attribute that represents the date format <code>dd/MM/yyyy</code>.
	 */
	public static final String FORMATO_FECHA_CORTO = "dd/MM/yyyy";

	/**
	 * Constant attribute that represents the date format <code>yyyyMMddHHmmss</code>.
	 */
	public static final String FORMATO_FECHA_JUNTA = "yyyyMMddHHmmss";

	/**
	 * Constant attribute that represents the date format <code>dd_MM_yyyy</code>.
	 */
	public static final String FORMATO_FECHA_BAJA = "dd_MM_yyyy";

	/**
	 * Constant attribute that represents the date format <code>"dd/MM/yyyy HH:mm:ss"</code>.
	 */
	public static final String FORMATO_FECHA_ESTANDAR = "dd/MM/yyyy HH:mm:ss";

	/**
	 * Constant attribute that represents the date format <code>"dd/MM/yyyy/HH/mm/ss"</code>.
	 */
	public static final String FORMATO_FECHA_BARRAS = "dd/MM/yyyy/HH/mm/ss";

	/**
	 * Constant attribute that represents the date format <code>"yyyyMMddHHmmss.SZ"</code>.
	 */
	public static final String FORMATO_FECHA_JUNTA_ADICIONAL = "yyyyMMddHHmmss.SZ";

	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd'T'HHmmssZ"</code>.
	 */
	public static final String FORMATO_FECHA_HORA_ADICIONAL = "yyyy-MM-dd'T'HHmmssZ";

	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd HH:mm:ss,SSS"</code>.
	 */
	public static final String FORMATO_FECHA_HORA_COMPLETA = "yyyy-MM-dd HH:mm:ss,SSS";

	/**
	 * Constant attribute that represents the date format <code>"dd-MM-yyyy"</code>.
	 */
	public static final String FORMATO_FECHA_INVERTIDO = "dd-MM-yyyy";

	/**
	 * Constant attribute that represents the date format <code>"dd/MM/yyyy HH:mm:ss.SSS"</code>.
	 */
	public static final String FORMATO_FECHA_ESTANDAR_ADICIONAL = "dd/MM/yyyy HH:mm:ss.SSS";

	/**
	 * Constant attribute that represents the date format <code>"dd-MMM-yyyy HH:mm"</code>.
	 */
	public static final String FORMATO_FECHA_HORA_MINUTOS = "dd-MMM-yyyy HH:mm";

	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd HH:mm:ss ZZZZ"</code>.
	 */
	public static final String FORMATO_SEMICOMPLETO = "yyyy-MM-dd HH:mm:ss ZZZZ";

	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd'T'HH:mm:ss.SSS"</code>.
	 */
	public static final String FORMATO_FECHA_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	/**
	 * Constant attribute that represents the date format <code>"yyyy/MM/dd EEE hh:mm:ss zzzz"</code>.
	 */
	public static final String FORMATO_COMPLETO_ADICIONAL = "yyyy/MM/dd EEE hh:mm:ss zzzz";

	/**
	 * Constant attribute that represents the date format <code>"dd-MM-yy_HH-mm-ss"</code>.
	 */
	public static final String FORMATO_FECHA_HORA_SEGUNDOS = "dd-MM-yy_HH-mm-ss";

	/**
	 * Constant attribute that represents the date format <code>"MM/DD/YYYY HH24:MI:SS"</code>.
	 */
	public static final String FORMATO_FECHA_ORACLE = "MM/dd/yyyy HH:mm:ss";
	
	/**
	 * Constant attribute that represents the date format <code>"YYYY/MM/DD HH24:MI:SS"</code>.
	 */
	public static final String FORMATO_FECHA_MYSQL_HSQLDB = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd HH:mm:ss.SSS zzz"</code>.
	 */
	public static final String FORMATO_FECHA_XML_CONFIGURACION = "yyyy-MM-dd HH:mm:ss.SSS zzz";
	
	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd'T'HH:mm:ss"</code>.
	 */
	public static final String FORMATO_FECHA_CRL_ISSUE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
	
	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'"</code>.
	 */
	public static final String FORMATO_FECHA_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'";
	
	/**
	 * Constant attribute that represents the date format <code>"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"</code>.
	 */
	public static final String FORMAT_DATE_TIME_JSON = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	/**
	 * Attribute that represents the value of the date.
	 */
	private java.util.Date fecha;

	/**
	 * Attribute that represents the time zone offset.
	 */
	private TimeZone zona = null;

	/**
	 * Constructor method for the class UtilsFecha.java.
	 */
	public UtilsFecha() {
		Calendar cal = Calendar.getInstance();
		zona = cal.getTimeZone();
		fecha = cal.getTime();
	}

	/**
	 * Constructor method for the class UtilsFecha.java.
	 * @param fechaStr Parameter that represents the value of the date.
	 * @param formato Parameter that represents the format used for the date.
	 * @throws ParseException If the method fails.
	 */
	public UtilsFecha(String fechaStr, String formato) throws ParseException {
		Locale l = new Locale("ES", "es");

		SimpleDateFormat formador = new SimpleDateFormat(formato, l);
		zona = Calendar.getInstance().getTimeZone();
		formador.setTimeZone(zona);
		fecha = formador.parse(fechaStr);

	}
	
	/**
	 * Method that obtains a date from a string using a determined pattern.
	 * @param initialDate Parameter that represents the string to transform in date.
	 * @param pattern Parameter that represents the pattern used to generate the date.
	 * @return a date from a string using a determined pattern.
	 * @throws ParseException If the method fails.
	 */
	public static Date transformDate(String initialDate, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date dateParsed = null;
		dateParsed = format.parse(initialDate);
		return dateParsed;
	}

	/**
	 * Method that obtains a string with the value of the date for certain format.
	 * @param formato Parameter that represents the format to apply for the date.
	 * @return a string with the value of the date for certain format.
	 */
	public final String toString(String formato) {
		Locale l = new Locale("ES", "es");
		SimpleDateFormat formador = new SimpleDateFormat(formato, l);
		if (zona != null) {
			formador.setTimeZone(zona);
		}
		return formador.format(fecha);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public final String toString() {
		return toString(FORMATO_FECHA_HORA);
	}
	
	/**
	 * Method that obtains a string with the value of the date for certain format.
	 * @param dateFormat Parameter that represents the format to apply for the date.
	 * @param dateParam Date to be transform to String
	 * @return a string with the value of the date for certain format.
	 */
	public static final String toString(String dateFormat, Date dateParam) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, UtilsCountryLanguage.getFirstLocaleOfCountryCode(UtilsCountryLanguage.ES_COUNTRY_CODE));
		return simpleDateFormat.format(dateParam);
	}

	/**
	 * Gets the value of the attribute {@link #fecha}.
	 * @return the value of the attribute {@link #fecha}.
	 */
	public final Date getFecha() {
		return fecha;
	}

	/**
	 * Sets the value of the attribute {@link #fecha}.
	 * @param pFecha The value for the attribute {@link #fecha}.
	 */
	public final void setFecha(Date pFecha) {
		fecha = pFecha;
	}
}