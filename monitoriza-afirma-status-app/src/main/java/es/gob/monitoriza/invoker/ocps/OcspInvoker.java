/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.invoker.ocps.OcspInvoker.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>25 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 25 ene. 2018.
 */
package es.gob.monitoriza.invoker.ocps;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.DTOService;
import es.gob.monitoriza.utilidades.FileUtils;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that performs the request of a OCSP service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 25 ene. 2018.
 */
public class OcspInvoker {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param requestFile request file which contents the SOAP message.
	 * @param serviceName name of the service to invoke. It's should match with the real name of the service since it will be inserted in the URL of invocation.
	 * @return the time...
	 */
	public static Long sendRequest(final File requestFile, final DTOService service) {
		
		Long tiempoTotal = null;
		byte[ ] requestByte = FileUtils.fileToByteArray(requestFile);

		String secureMode = Boolean.valueOf(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
		String ip = StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_HOST);
		String port = StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_PORT);
		String ocspPath = StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_OCSP_PATH);
		String base = secureMode + GeneralConstants.COLON + GeneralConstants.DOUBLE_PATH_SEPARATOR + ip + GeneralConstants.COLON + port;

		try {

			URL endpoint = new URL(new URL(base), ocspPath);

			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setConnectTimeout(service.getTimeout().intValue());
			con.setReadTimeout(service.getTimeout().intValue());
			con.setRequestProperty("Content-Type", "application/ocsp-request");
			con.setRequestProperty("Accept", "application/ocsp-response");
			con.setRequestProperty("charset", "utf-8");
			con.setRequestProperty("Content-Length", Integer.toString(requestByte.length));
			con.setUseCaches(false);

			LocalTime beforeCall = LocalTime.now();
			// Conexión...
			con.connect();
			// Comprobamos que la conexión se estableció correctamente
			if (con.getResponseCode() / 100 != 2) {
				// Si hay algún problema de conexión, considero la petición como perdida...
				LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_OCSP_PETITION));
			}
			else {
    			// Lectura...
    			con.getContent();
    			LocalTime afterCall = LocalTime.now();
    			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
			}

		} catch (java.net.SocketTimeoutException e) {
			
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_OCSP_PETITION), e);

		} catch (IOException e) {

			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_OCSP_PETITION), e);

		}

		return tiempoTotal;
	}

}