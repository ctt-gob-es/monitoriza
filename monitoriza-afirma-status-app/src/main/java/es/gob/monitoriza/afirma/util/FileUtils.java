/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.utils.FileUtils.java.</p>
 * <b>Description:</b><p> Utilities class for Files.</p>
 * <b>Project:</b><p>Autotester.</p>
 * <b>Date:</b><p>21/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/1/2018.
 */
package es.gob.monitoriza.afirma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import es.gob.monitoriza.afirma.constant.GeneralConstants;
import es.gob.monitoriza.afirma.constant.LogMessagesConstants;
import es.gob.monitoriza.afirma.i18.Language;

/** 
 * <p>Utilities class for Files.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 11/01/2018.
 */
public final class FileUtils {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constructor method for the class FileUtils.java.
	 */
	private FileUtils() {
	}	

	/**
	 * method that read a file line by line fiven a file object.
	 * 
	 * @param file File to read.
	 * @return the content of the file as string object.
	 */
	public static String readFile(File file) {
		FileReader fileReader = null;
		BufferedReader bf = null;
		FileWriter result = null;
		StringBuilder res = new StringBuilder();
		try {
			// Creamos un fileReader para leer el log con las peticiones y
			// respuestas.
			fileReader = new FileReader(file.getAbsolutePath());
			// Creamos el buffer de escritura
			bf = new BufferedReader(fileReader);
			// Iniciamos la variable que irá leyendo linea por linea el fichero.
			String line = bf.readLine();
			if (line != null) {
				res.append(line);
				line = bf.readLine();
				while (line != null) {
					line = bf.readLine();
					res.append(line);
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessagesConstants.ERROR_NO_FILE_EXISTS), e);
		} catch (IOException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessagesConstants.ERROR_READING_FILE), e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessagesConstants.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessagesConstants.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
		}
		return res.toString();
	}

	

}
