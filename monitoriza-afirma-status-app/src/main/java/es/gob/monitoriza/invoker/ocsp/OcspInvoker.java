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
 * <b>File:</b><p>es.gob.monitoriza.invoker.ocps.OcspInvoker.java.</p>
 * <b>Description:</b><p>Class that performs the request of a OCSP service.</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>25/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 11/05/2022.
 */
package es.gob.monitoriza.invoker.ocsp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.utilidades.FileUtils;

/** 
 * <p>Class that performs the request of a OCSP service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.7, 11/05/2022.
 */
public final class OcspInvoker {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Constructor method for the class OcspInvoker.java. 
	 */
	private OcspInvoker() {
		
	}

	/**
	 * Method that sends a request and get the response message.
	 * @param idTimerTask Identifier of the scheduled service.
	 * @param requestFile request file which contents the SOAP message.
	 * @param service DTOService that contains the configuration data for the service.
	 * @return Long that represents the time in milliseconds that has taken to complete the request.
	 * If there is some configuration or communication problem, this value will be null.
	 */
	public static Long sendRequest(final String idTimerTask, final File requestFile, final ConfigServiceDTO service, final KeyStore ssl) throws InvokerException {
		
		Long tiempoTotal = null;
		byte[ ] requestByte = FileUtils.fileToByteArray(requestFile);
				
		try {
		
			// Establecemos el timeout de la conexión y de la lectura
			URL endpoint = new URL(new URL(service.getBaseUrl()), service.getOcspContext(), new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();

					if (connection instanceof HttpsURLConnection) {

						String msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS004, new Object[]{idTimerTask});

						try {

							TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
							SSLContext ctx = SSLContext.getInstance("TLS");
							tmf.init(ssl);
							ctx.init(null, tmf.getTrustManagers(), null);
							SSLSocketFactory factory = ctx.getSocketFactory();
							((HttpsURLConnection) connection).setSSLSocketFactory(factory);

						} catch (NoSuchAlgorithmException | KeyStoreException
								| KeyManagementException e) {

							LOGGER.error(msgError, e);

						}

						((HttpsURLConnection) connection).setHostnameVerifier(new NameVerifier());

					}
										
					return (connection);
				}

			});
			
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS008, new Object[ ] { idTimerTask, requestFile, endpoint}));
			
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
			
			// Se escribe la petición OCSP en la conexión
			OutputStream os = con.getOutputStream();
			os.write(requestByte);
			os.flush();
			os.close();

			LocalTime beforeCall = LocalTime.now();
			// Conexión...
			con.connect();
			// Comprobamos que la conexión se estableció correctamente
			if (con.getResponseCode() / NumberConstants.NUM100 != 2) {
				// Si hay algún problema de conexión, considero la petición como perdida...
				LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS005, new Object[]{idTimerTask, requestFile}));
			}
			else {
    			// Lectura...
				con.getContent();
    			LocalTime afterCall = LocalTime.now();
    			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
			}
		
		} catch (IOException e) {

			String msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS005, new Object[]{idTimerTask});
			LOGGER.error(msgError, e);
			throw new InvokerException(msgError,e.getCause());
		}
		

		return tiempoTotal;
	}
	
	/**
	 * <p>Private class that allows to verify the host of the HTTPS service.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 02/05/2018.
	 */
	private static class NameVerifier implements HostnameVerifier {

		/**
		 * {@inheritDoc}
		 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
		 */
		public boolean verify(final String hostname, final SSLSession session) {
			return true;
		}
	}

}
