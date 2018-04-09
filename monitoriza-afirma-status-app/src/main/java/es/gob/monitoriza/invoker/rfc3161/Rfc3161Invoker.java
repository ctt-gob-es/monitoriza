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
 * <b>File:</b><p>es.gob.monitoriza.invoker.rfc3161.Rfc3161Invoker.java.</p>
 * <b>Description:</b><p>Class that manages and performs the request of a service via RFC3161.</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>29 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 29 ene. 2018.
 */
package es.gob.monitoriza.invoker.rfc3161;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import es.gob.monitoriza.configuration.ConnectionManager;
import es.gob.monitoriza.configuration.impl.StaticConnectionManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.utilidades.FileUtils;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;
import es.gob.monitoriza.utilidades.UtilsResource;

/** 
 * <p>Class that manages and performs the request of a service via RFC3161.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 29 ene. 2018.
 */
public class Rfc3161Invoker {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Method that invokes the TS@ RFC 3161 - HTTPS service to obtain an ASN.1 timestamp.
	 * @param requestFile File that contains the RFC3161 request.
	 * @param service DTOService that contains the configuration data for the service.
	 * @return Long that represents the time in milliseconds that has taken to complete the request.
	 * If there is some configuration or communication problem, this value will be null.
	 * @throws InvokerException If the method fails.
	 */
	public static Long sendRequest(final File requestFile, final ServiceDTO service) throws InvokerException {
		LOGGER.debug(Language.getResMonitoriza(LogMessages.INIT_RFC3161));
		
		Long tiempoTotal = null;
		String msgError = null;
		OutputStream out = null;
		
		try {
			// Accedemos al almacén de confianza TSA - SSL
			msgError = Language.getResMonitoriza(LogMessages.ERROR_ACCESS_CERTIFICATE_SSL);
						
			KeyStore cer = KeyStore.getInstance(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_SSL_TRUSTSTORE_TYPE));
			InputStream readStream = new FileInputStream(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_SSL_TRUSTTORE_PATH));
			cer.load(readStream, StaticMonitorizaProperties.getProperty(StaticConstants.TSA_SSL_TRUSTTORE_PASSWORD).toCharArray());
			readStream.close();	
						
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(cer);

			SSLContext ctx = SSLContext.getInstance("SSL");

			// Obtenemos el indicador para saber si es necesaria la
			// autenticación del cliente
			Boolean authClient = Boolean.parseBoolean(StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_USE_CLIENT_AUTHENTICATION));
			if (authClient) {
				LOGGER.debug(Language.getResMonitoriza(LogMessages.AUTH_CLIENT_RFC3161_ON));

				// Obtenemos el alias del certificado a usar para la
				// autenticación cliente
				String certificateAlias = StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_CERTIFICATE_ALIAS);
				// Comprobamos que el alias del certificado a usar para la
				// autenticación cliente no es nulo
				checkValueNotNull(certificateAlias, Language.getResMonitoriza(LogMessages.ERROR_UNDEFINED_CERTIFICATE_AUTH_CLIENT_RFC3161));

				// Accedemos al almacén con el certificado para la autenticación cliente
				msgError = Language.getResMonitoriza(LogMessages.ERROR_KEYSTORE_ACCESS_AUTH_CLIENT_RFC3161);
				
				KeyStore cerAuth = KeyStore.getInstance(StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_CERTIFICATE_TYPE));
				InputStream is = new FileInputStream(StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_AUTH_KEYSTORE_PATH));
				cerAuth.load(is, StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_CERTIFICATE_PASSWORD).toCharArray());
												
				// Comprobamos que el certificado existe en el almacén
				if (!cerAuth.containsAlias(certificateAlias)) {
					msgError = Language.getFormatResMonitoriza(LogMessages.ERROR_NOCERT_IN_KEYSTORE_AUTH_CLIENT_RFC3161, new Object[ ] { certificateAlias });
				} else {
					PrivateKey pk = null;
					// Obtenemos la clave privada asociada al alias del
					// certificado
					msgError = Language.getFormatResMonitoriza(LogMessages.ERROR_PRIVATE_KEY_AUTH_CLIENT_RFC3161, new Object[ ] { certificateAlias });
					if (cerAuth.isKeyEntry(certificateAlias)) {
						pk = (PrivateKey) cerAuth.getKey(certificateAlias, StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_CERTIFICATE_PASSWORD).toCharArray());
					}
					// Obtenemos la cadena de certificación para el alias del
					// certificado
					msgError = Language.getFormatResMonitoriza(LogMessages.ERROR_CERTIFICATE_CHAIN_AUTH_CLIENT_RFC3161, new Object[ ] { certificateAlias });
					Certificate[ ] certificateChain = cerAuth.getCertificateChain(certificateAlias);
					// Creamos un almacén de claves vacío para meter el
					// certificado
					// a usar para la autenticación cliente
					msgError = Language.getResMonitoriza(LogMessages.ERROR_CONTEXT_RFC3161);
					String keystoreType = "PKCS12";
					String keystorePass = "12345";
					KeyStore ks = KeyStore.getInstance(keystoreType);
					ks.load(null, keystorePass.toCharArray());
					ks.setKeyEntry(certificateAlias, pk, keystorePass.toCharArray(), certificateChain);
					KeyManagerFactory kmf = KeyManagerFactory.getInstance(GeneralConstants.TRUST_MANAGER_FACTORY_SUN_X509);
					kmf.init(ks, keystorePass.toCharArray());
					
					ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				}

			} else {
				LOGGER.debug(Language.getResMonitoriza(LogMessages.AUTH_CLIENT_RFC3161_OFF));
				msgError = Language.getResMonitoriza(LogMessages.ERROR_CONTEXT_RFC3161);
				ctx.init(null, tmf.getTrustManagers(), null);
			}

			// Generamos la petición de sello de tiempo
			byte request[] = FileUtils.fileToByteArray(requestFile);

			// Obtenemos la URL de conexión con el servicio RFC 3161
			URL url = getRFC3161TSAURL();
			//LOGGER.info(Language.getResMonitoriza("logTMH019"));

			//msgError = Language.getResMonitoriza("logTMH018");
			HttpsURLConnection tsaConnection = (HttpsURLConnection) url.openConnection();
			tsaConnection.setHostnameVerifier(new NameVerifier());

			SSLSocketFactory factory = ctx.getSocketFactory();
			tsaConnection.setSSLSocketFactory(factory);
			tsaConnection.setDoInput(true);
			tsaConnection.setDoOutput(true);
			tsaConnection.setUseCaches(false);
			tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
			tsaConnection.setAllowUserInteraction(false);
			tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");

			LocalTime beforeCall = LocalTime.now();
			out = tsaConnection.getOutputStream();
						
			out.write(request);
			out.close();
			LocalTime afterCall = LocalTime.now();
			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
					
			
		} catch (IOException | NoSuchAlgorithmException  | KeyStoreException | UnrecoverableKeyException | KeyManagementException | CertificateException e) {
			LOGGER.error(msgError, e);
		} finally {
			UtilsResource.safeCloseOutputStream(out);
		}
		
		return tiempoTotal;
	}
		
	/**
	 * Method that obtains the URL for connecting to TS@ RFC 3161 - HTTPS service from the configuration properties file.
	 * @return the URL for connecting to TS@ RFC 3161 - HTTPS service.
	 * @throws InvokerException If the method fails.
	 */
	private static URL getRFC3161TSAURL() throws InvokerException {
		
		ConnectionManager tsaConnection = new StaticConnectionManager();
		
		String host = tsaConnection.getTsaConnection().getHost();
		String port = tsaConnection.getTsaConnection().getRfc3161Port();
		String context = tsaConnection.getTsaConnection().getRfc3161Context();
				
		String tsaURL = "https://" + host + ":" + port + context;
		
		try {
			return new URL(tsaURL);
		} catch (MalformedURLException e) {
			throw new InvokerException(Language.getFormatResMonitoriza(LogMessages.ERROR_MALFORMED_URL_RFC3161, new Object[ ] { tsaURL }), e);
		}
	}
	
	/**
	 * Method that checks if a value isn't null.
	 * @param value Parameter that represents the value to check.
	 * @param msg Parameter that represents the error message for the exception generated when the value is null.
	 * @throws InvokerException If the value is null.
	 */
	private static void checkValueNotNull(final Object value, final String msg) throws InvokerException {
		if (value == null) {
			throw new InvokerException(msg);
		}
	}
	
	/**
	 * <p>Private class that allows to verify the host of the TS@ RFC 3161 - HTTPS service.</p>
	 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
	 * @version 1.0, 29 ene. 2018.
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
