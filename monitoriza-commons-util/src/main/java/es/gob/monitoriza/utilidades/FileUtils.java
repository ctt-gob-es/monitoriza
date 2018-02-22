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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.FileUtils.java.</p>
 * <b>Description:</b><p> Utilities class for Files.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>21/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/1/2018.
 */
package es.gob.monitoriza.utilidades;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;

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
					res.append(line);
					line = bf.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_NO_FILE_EXISTS), e);
		} catch (IOException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_READING_FILE), e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
		}
		return res.toString();
	}

	public static byte[ ] fileToByteArray(File file) {

		byte[ ] raw;
		byte[ ] result;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			raw = Files.readAllBytes(file.toPath());
			
			for (byte b: raw) {
				if (b != 10 && b != 13) {
					baos.write(b);
				}
			}
			
		} catch (IOException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_READING_FILE), e);
		}

		result = baos.toByteArray();

		return result;
	}

}
