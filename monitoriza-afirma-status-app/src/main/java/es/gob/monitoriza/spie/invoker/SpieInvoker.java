/* 
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
 * <b>File:</b><p>es.gob.monitoriza.spie.invoker.SpieInvoker.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>27/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.spie.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Class that performs the request of a SPIE service via HTTP.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 25/01/2019.
 */
public final class SpieInvoker {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Constructor method for the class OcspInvoker.java. 
	 */
	private SpieInvoker() {
		
	}

	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param nodeUrl String that represents the url address of the node
	 * @param spieUrl String that represents the url context address of the SPIE service
	 * @param ssl KeyStore object that represents the system truststore
	 * @return Long that represents the time in milliseconds that has taken to complete the request.
	 * If there is some configuration or communication problem, this value will be null.
	 */
	public static String sendRequest(final String nodeUrl, final String spieUrl, final KeyStore ssl) throws InvokerException {
					
		String result = null;
		
		try {
		
			// Establecemos el timeout de la conexión y de la lectura
			URL endpoint = new URL(new URL(nodeUrl), spieUrl, new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();

					if (connection instanceof HttpsURLConnection) {

						String msgError = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS004);

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
			
			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("GET");
			con.setConnectTimeout(NumberConstants.NUM10000);
			con.setReadTimeout(NumberConstants.NUM10000);
			con.setRequestProperty("Accept", "text/html");
			con.setUseCaches(false);
						
			// Conexión...
			con.connect();
			// Comprobamos que la conexión se estableció correctamente
			if (con.getResponseCode() / NumberConstants.NUM100 != 2) {
				// Si hay algún problema de conexión, considero la petición como perdida...
				String msg = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS016, new Object[]{con.getResponseCode()});
				throw new InvokerException(msg);
			}
			else {
				// Lectura...

				try (BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));) {

				  result = bf.lines().collect(Collectors.joining());

				}

			}
		
		} catch (IOException e) {

			String msg = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS016, new Object[]{e.getMessage()});
			throw new InvokerException(msg, e);
		}
		

		return result;
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
