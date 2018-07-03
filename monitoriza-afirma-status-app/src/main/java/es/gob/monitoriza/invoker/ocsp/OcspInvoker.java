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
 * <b>Date:</b><p>25 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 25 ene. 2018.
 */
package es.gob.monitoriza.invoker.ocsp;

import java.io.File;
import java.io.IOException;
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

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
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
	 * @param service DTOService that contains the configuration data for the service.
	 * @return Long that represents the time in milliseconds that has taken to complete the request.
	 * If there is some configuration or communication problem, this value will be null.
	 */
	public static Long sendRequest(final File requestFile, final ServiceDTO service, final KeyStore ssl) {
		
		Long tiempoTotal = null;
		byte[ ] requestByte = FileUtils.fileToByteArray(requestFile);

		String secureMode = Boolean.valueOf(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
		String ip = StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_HOST);
		String port = Boolean.valueOf(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_HTTPS_PORT) : StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_PORT);
		String ocspPath = StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_OCSP_PATH);
		
		String base = "";
		if (port != null && !"".equals(port)) {
			base = secureMode + GeneralConstants.COLON + GeneralConstants.DOUBLE_PATH_SEPARATOR + ip + GeneralConstants.COLON + port;
		} else {
			base = secureMode + GeneralConstants.COLON + GeneralConstants.DOUBLE_PATH_SEPARATOR + ip;
		}

		try {

			
			// Establecemos el timeout de la conexión y de la lectura
			
			URL endpoint = new URL(new URL(base), ocspPath, new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();

					if (connection instanceof HttpsURLConnection) {

						String msgError = Language.getResMonitoriza(LogMessages.ERROR_CONTEXT_RFC3161);

						try {

							TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
							SSLContext ctx = SSLContext.getInstance("SSL");
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
			
			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.LOG_ENDPOINT, new Object[ ] { requestFile, endpoint}));
			
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
		
		} catch (IOException e) {

			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_OCSP_PETITION), e);

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
