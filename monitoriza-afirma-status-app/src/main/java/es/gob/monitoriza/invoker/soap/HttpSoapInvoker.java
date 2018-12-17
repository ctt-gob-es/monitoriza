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
 * <b>File:</b><p>es.gob.monitoriza.invoker.soap.HttpSoapInvoker.java.</p>
 * <b>Description:</b><p>Class that performs the request of a service via HTTP SOAP.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>22/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 05/12/2018.
 */
package es.gob.monitoriza.invoker.soap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.utilidades.FileUtils;

/** 
 * <p>Class that performs the request of a service via HTTP SOAP.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 05/12/2018.
 */
public class HttpSoapInvoker {
	
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
	public static Long sendRequest(final File requestFile, final ConfigServiceDTO service, final KeyStore ssl) throws InvokerException {
		// Obtenemos el contenido del fichero
		String soapMsg = FileUtils.readFile(requestFile);
		Long tiempoTotal = null;
		
		try (InputStream is = new ByteArrayInputStream(soapMsg.getBytes());) {
					
			String wsdlServiceName = service.getWsdl();
			String base = service.getSoapUrl();
			
			// Creamos el Mime SoapAction necesario para enviar la petición
			MimeHeaders mhs = new MimeHeaders();
			// Generamos el SOAPAction para el servicio concreto.
			
			mhs.addHeader("SOAPAction", wsdlServiceName);
						
			// Creamos el mensaje SOAP
			SOAPMessage message = MessageFactory.newInstance().createMessage(mhs, is);
			// Creamos la URL de punto de acceso a donde se va a atacar.
			//URL endpoint = new URL(url);
			// Creamos el objeto de conexión y realizamos la llamada
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			
			// Establecemos las propiedades de la conexión
			
			URL endpoint = new URL(new URL(base), wsdlServiceName, new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();
					
					if (connection instanceof HttpsURLConnection) {
																
						String msgError = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS011);
						
						try {
							
							TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
							SSLContext ctx = SSLContext.getInstance("SSL");
							tmf.init(ssl);
							ctx.init(null, tmf.getTrustManagers(), null);
							SSLSocketFactory factory = ctx.getSocketFactory();
							((HttpsURLConnection) connection).setSSLSocketFactory(factory);

						} catch (NoSuchAlgorithmException
								| KeyStoreException
								| KeyManagementException e) {
							
							LOGGER.error(msgError, e);

						}
						
						((HttpsURLConnection)connection).setHostnameVerifier(new NameVerifier());
						
					}
					// Connection settings
					connection.setConnectTimeout(service.getTimeout().intValue());
					connection.setReadTimeout(service.getTimeout().intValue());
					
					return (connection);
				}
				
			});
			
			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS008, new Object[ ] { requestFile, endpoint}));
			
			LocalTime beforeCall = LocalTime.now();
			
			soapConnection.call(message, endpoint);
			
			LocalTime afterCall = LocalTime.now();
			
			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
												
		} catch (IOException | SOAPException e) {
			
			String msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS012, new Object[ ] { service.getPlatform()});
			LOGGER.error(msgError, e);
			throw new InvokerException(msgError,e.getCause());

		} catch (IllegalArgumentException | SecurityException e) {
			LOGGER.error(e.getMessage());
			throw new InvokerException(e);
		} 
		
		return tiempoTotal;
	}
		
	/**
	 * <p>Private class that allows to verify the host of the HTTPS service.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 30/04/2018.
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