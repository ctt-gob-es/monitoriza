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
 * <b>File:</b><p>es.gob.monitoriza.webservice.ClientManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 ago. 2018.
 */
package es.gob.monitoriza.webservice;

import java.net.MalformedURLException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.configuration.manager.AdminServicesManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.enums.AuthenticationTypeEnum;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.handler.ClientHandler;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.trust.WSAuthSSLX509TrustManager;

/** 
 * <p>Class ClientManager.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 1 ago. 2018.
 */
@Service
public class ClientManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the localhost string.
	 */
	private static final String LOCALHOST = "localhost";
	
	/**
	 * Attribute that represents the name space uri.
	 */
	private static final String NAME_SPACE_URI = "http://soapinterop.org/";
	
	/**
	 * Attribute that represents the loval part.
	 */
	private static final String LOCAL_PART = "verify";

	/**
	 * Attribute that represents the service object for accessing the aminServicesManager.
	 */
	@Autowired
	private AdminServicesManager adminServicesManager;

	static {
		// for localhost testing only
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				if (hostname.equals(LOCALHOST)) {
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * Method that creates the service DSSCertificate.
	 * @param url URI
	 * @return validation result
	 * @throws MalformedURLException exception
	 */
	public String getDSSCertificateServiceClientResult(String url, ValidService validService, Object[] peticion) throws Exception {
		
		LOGGER.info("getDSSCertificateServiceClient init");
		
		String response = null;
		
		if (validService.getIsSecure() != null && validService.getIsSecure()) {

			KeyStore cerSSL = adminServicesManager.loadSslTruststore();

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(GeneralConstants.TRUST_MANAGER_FACTORY_SUN_X509);

			tmf.init(cerSSL);

			HttpsURLConnection.setDefaultSSLSocketFactory(getSSLSocketFactory(cerSSL));
		}
		
		ClientHandler requestHandler = null;
		
		
		if (validService.getAuthenticationType().getIdAuthenticationType().equals(AuthenticationTypeEnum.USERPASS.getId())) {
			requestHandler = new ClientHandler(AuthenticationTypeEnum.USERPASS.getName());
			requestHandler.setUserAlias(validService.getUser());
			requestHandler.setPassword(validService.getPass());
		} else if (validService.getAuthenticationType().getIdAuthenticationType().equals(AuthenticationTypeEnum.CERTIFICATE.getId())) {
			SystemCertificate certValidService = validService.getValidServiceCertificate();
			Keystore keystoreValidService = certValidService.getKeystore();
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreValidService);
			KeyStore ksAuthValidServ = adminServicesManager.loadValidServiceKeystore();
			X509Certificate certificateSOAP = (X509Certificate) ksAuthValidServ.getCertificate(certValidService.getAlias());
			PrivateKey privateKeySOAP = (PrivateKey) ksAuthValidServ.getKey(certValidService.getAlias(), keyStoreFacade.getKeystoreDecodedPasswordString(keystoreValidService.getPassword()).toCharArray());
			requestHandler = new ClientHandler(AuthenticationTypeEnum.CERTIFICATE.getName());
			requestHandler.setCertificate(certificateSOAP);
			requestHandler.setKey(privateKeySOAP);
		}
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(NAME_SPACE_URI, LOCAL_PART));
			call.setTimeout(10000);
			if (requestHandler != null) {
				call.setClientHandlers(requestHandler,null);
			}
			response = (String) call.invoke(peticion);
			System.out.println(response);
		} catch (Exception e) {
			LOGGER.equals(e.getMessage());
		}
		
		LOGGER.info("getDSSCertificateServiceClient end");
		
		return response;
	}

	/**
	 * Method that initializes the trust managers.
	 * @param keystore Parameter that represents the KeyStore.
	 * @return one trust manager for each type of trust material.
	 * @throws KeyStoreException If the method fails.
	 * @throws NoSuchAlgorithmException If the method fails.
	 */
	private static TrustManager[ ] createTrustManagers(KeyStore keystore) throws KeyStoreException, NoSuchAlgorithmException {
		TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmfactory.init(keystore);
		TrustManager[ ] trustmanagers = tmfactory.getTrustManagers();
		for (int i = 0; i < trustmanagers.length; i++) {
			if (trustmanagers[i] instanceof X509TrustManager) {
				trustmanagers[i] = new WSAuthSSLX509TrustManager((X509TrustManager) trustmanagers[i]);
			}
		}
		return trustmanagers;
	}

	/**
	 * Method that generates the object used to manage the SSL communication with TS@.
	 * @return the object used to manage the SSL communication with TS@.
	 * @throws TSAServicesException If the method fails.
	 */
	private SSLSocketFactory getSSLSocketFactory(final KeyStore ksSSL) {
		SSLSocketFactory sslSocketFactory = null;
		try {
			// Comprobamos que el almacén de confianza TSA - SSL se haya cargado
			checkValueNotNull(ksSSL, "Mensaje");
		} catch (Exception e) {
			LOGGER.error("Error");
		}
		try {
			// Obtenemos el array de trust managers contenidos en el almacén de
			// confianza TSA - SSL
			SSLContext sslcontext = SSLContext.getInstance("SSL");
			TrustManager[ ] trustmanagers = createTrustManagers(ksSSL);
			sslcontext.init(null, trustmanagers, null);
			sslSocketFactory = sslcontext.getSocketFactory();
		} catch (Exception e) {
			LOGGER.error("Error");
		}
		return sslSocketFactory;
	}

	/**
	 * Method that checks if a value isn't null.
	 * @param value Parameter that represents the value to check.
	 * @param msg Parameter that represents the error message for the exception generated when the value is null.
	 * @throws InvokerException If the value is null.
	 */
	private void checkValueNotNull(final Object value, final String msg) throws InvokerException {
		if (value == null) {
			throw new InvokerException(msg);
		}
	}

	/**
	 * Get adminServicesManager.
	 * @return adminServicesManager
	 */
	public AdminServicesManager getAdminServicesManager() {
		return adminServicesManager;
	}

	/**
	 * Set adminServicesManager.
	 * @param adminServicesManager set adminServicesManager
	 */
	public void setAdminServicesManager(AdminServicesManager adminServicesManager) {
		this.adminServicesManager = adminServicesManager;
	}

}
