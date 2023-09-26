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
 * <b>Date:</b><p>29/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.8, 26/09/2023.
 */
package es.gob.monitoriza.invoker.rfc3161;

import java.io.File;
import java.io.IOException;
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

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.utilidades.FileUtils;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;
import es.gob.monitoriza.utilidades.UtilsResource;

/** 
 * <p>Class that manages and performs the request of a service via RFC3161.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.8, 26/09/2023.
 */
public final class Rfc3161Invoker {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_STATUS_LOG);
	
	/**
	 * Constant that represents the 'SSL' protocol. 
	 */
	private static final String SSL_PROTOCOL = "SSL";
	
	/**
	 * Constant that represents the 'TLS' protocol. 
	 */
	private static final String TLS_PROTOCOL = "TLS";
			
	/**
	 * Constant that represents the type for the client authentication keystore. 
	 */
	private static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";
	
	/**
	 * Constant that represents the password for the client authentication keystore. 
	 */
	private static final String CLIENT_AUTH_KEYSTORE_PASSWORD = "12345";
	
	/**
	 * Constructor method for the class Rfc3161Invoker.java. 
	 */
	private Rfc3161Invoker(){
		
	}
	
	/**
	 * Method that invokes the TS@ RFC 3161 - HTTPS service to obtain an ASN.1 timestamp.
	 * @param idTimerTask Identifier of the scheduled timer.
	 * @param requestFile File that contains the RFC3161 request.
	 * @param service DTOService that contains the configuration data for the service.
	 * @param ssl Keystore that contains ssl certificates.
	 * @param authRfc3161 Keystore that contains authentication certificate.
	 * @return Long that represents the time in milliseconds that has taken to complete the request.
	 * If there is some configuration or communication problem, this value will be null.
	 * @throws InvokerException If the method fails.
	 */
	public static Long sendRequest(final String idTimerTask, final File requestFile, final ConfigServiceDTO service, final KeyStore ssl, final KeyStore authRfc3161) throws InvokerException {
		LOGGER.debug(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS009, new Object[]{idTimerTask}));
		
		Long tiempoTotal = null;
		String msgError = null;
		OutputStream out = null;
		
		try {
			// Accedemos al almacén de confianza SSL
						
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(GeneralConstants.TRUST_MANAGER_FACTORY_SUN_X509);
			
			tmf.init(ssl);

			SSLContext ctx = SSLContext.getInstance(TLS_PROTOCOL);

			// Obtenemos el indicador para saber si es necesaria la
			// autenticación del cliente
			if (service.getUseRfc3161Auth()) {
				LOGGER.debug(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS010, new Object[]{idTimerTask}));

				// Obtenemos el alias del certificado a usar para la
				// autenticación cliente
				//String certificateAlias = StaticMonitorizaProperties.getProperty(StaticConstants.RFC3161_HTTPS_CERTIFICATE_ALIAS);
				// Comprobamos que el alias del certificado a usar para la
				// autenticación cliente no es nulo
				checkValueNotNull(service.getRfc3161Cert(), Language.getFormatResMonitoriza(IStatusLogMessages.STATUS011, new Object[]{idTimerTask}));

				// Accedemos al almacén con el certificado para la autenticación cliente
				msgError = Language.getFormatResMonitoriza(IStatusLogMessages.STATUS012, new Object[]{idTimerTask});
												
				// Comprobamos que el certificado existe en el almacén
				if (!authRfc3161.containsAlias(service.getRfc3161Cert())) {
					msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS006, new Object[ ] { idTimerTask, service.getRfc3161Cert() });
				} else {
					PrivateKey pk = null;
					// Obtenemos la clave privada asociada al alias del
					// certificado
					msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS007, new Object[ ] { idTimerTask, service.getRfc3161Cert() });
					if (authRfc3161.isKeyEntry(service.getRfc3161Cert())) {
						pk = (PrivateKey) authRfc3161.getKey(service.getRfc3161Cert(), service.getRfc3161Password().toCharArray());
					}
					// Obtenemos la cadena de certificación para el alias del
					// certificado
					msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS008, new Object[ ] { idTimerTask,  service.getRfc3161Cert() });
					Certificate[ ] certificateChain = authRfc3161.getCertificateChain(service.getRfc3161Cert());
					// Creamos un almacén de claves vacío para meter el
					// certificado
					// a usar para la autenticación cliente
					msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS009, new Object[]{idTimerTask});
					String keystoreType = KEYSTORE_TYPE_PKCS12;
					String keystorePass = StaticMonitorizaConfig.getProperty(StaticMonitorizaConfig.SYSTEM_KEYSTORE_PASSWORD);
					KeyStore ks = KeyStore.getInstance(keystoreType);
					ks.load(null, keystorePass.toCharArray());
					ks.setKeyEntry(service.getRfc3161Cert(), pk, keystorePass.toCharArray(), certificateChain);
					KeyManagerFactory kmf = KeyManagerFactory.getInstance(GeneralConstants.TRUST_MANAGER_FACTORY_SUN_X509);
					kmf.init(ks, keystorePass.toCharArray());
					
					ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				}

			} else {
				LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS013, new Object[]{idTimerTask}));
				msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS009, new Object[]{idTimerTask});
				ctx.init(null, tmf.getTrustManagers(), null);
			}

			// Generamos la petición de sello de tiempo
			byte request[] = FileUtils.fileToByteArray(requestFile);

			// Obtenemos la URL de conexión con el servicio RFC 3161
			URL url = getRFC3161TSAURLFromWebAdmin(idTimerTask, service);
			
			HttpsURLConnection tsaConnection = (HttpsURLConnection) url.openConnection();
			tsaConnection.setHostnameVerifier(new NameVerifier());

			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS008, new Object[ ] { idTimerTask, requestFile, url.toString()}));			
			
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
			throw new InvokerException(msgError,e.getCause());
		} finally {
			UtilsResource.safeCloseOutputStream(out);
		}
		
		return tiempoTotal;
	}
		
	/**
	 * Method that gets the URL of the configured RFC3161 service in TS@.
	 * @param idTimerTask Timer identifier
	 * @param service Configured RFC3161 service
	 * @return URL of the RFC3161 service in TS@
	 * @throws InvokerException 
	 */
	private static URL getRFC3161TSAURLFromWebAdmin(final String idTimerTask, final ConfigServiceDTO service) throws InvokerException {
					
		StringBuffer tsaURL = new StringBuffer();
		tsaURL.append(service.getBaseUrl()).append(service.getRfc3161Context());
		
		try {
			return new URL(tsaURL.toString());
		} catch (MalformedURLException e) {
			throw new InvokerException(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS010, new Object[ ] {idTimerTask, tsaURL }), e);
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
