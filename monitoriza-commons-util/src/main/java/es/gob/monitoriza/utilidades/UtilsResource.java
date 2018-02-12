/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.util.UtilsResource.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>1 feb. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 feb. 2018.
 */
package es.gob.monitoriza.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Class that provides functionality to control resources.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 1 feb. 2018.
 */
public final class UtilsResource {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Method that handles the closing of a {@link OutputStream} resource.
	 * @param os Parameter that represents a {@link OutputStream} resource.
	 */
	public static void safeCloseOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				LOGGER.error(Language.getFormatResMonitoriza("", new Object[ ] { os.getClass().getName(), e.getMessage() }));
			}
		}
	}
	
	/**
	 * Method that handles the closing of a {@link InputStream} resource.
	 * @param is Parameter that represents a {@link InputStream} resource.
	 */
	public static void safeCloseInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				LOGGER.error(Language.getFormatResMonitoriza("", new Object[ ] { is.getClass().getName(), e.getMessage() }));
			}
		}
	}

}
