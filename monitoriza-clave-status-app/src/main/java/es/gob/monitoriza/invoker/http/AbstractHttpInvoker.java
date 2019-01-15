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
 * <b>File:</b><p>es.gob.monitoriza.invoker.http.HttpInvoker.java.</p>
 * <b>Description:</b><p>Class that performs the request of a HTTP service.</p>
  * <b>Project:</b><p>Application for monitoring services of Cl@ve suite systems</p>
 * <b>Date:</b><p>25 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 18/10/2018.
 */
package es.gob.monitoriza.invoker.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.ssl.HostnameVerifier;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.invoker.http.conf.messages.AttributeType;
import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;
import es.gob.monitoriza.invoker.http.conf.util.SelectByAlias;
import es.gob.monitoriza.invoker.http.conf.util.Utilities;
import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
import eu.eidas.auth.commons.attribute.ImmutableAttributeMap;
import eu.eidas.auth.commons.attribute.PersonType;
import eu.eidas.auth.commons.attribute.impl.StringAttributeValueMarshaller;
import eu.eidas.auth.commons.protocol.IRequestMessageNoMetadata;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssurance;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssuranceComparison;
import eu.eidas.auth.commons.protocol.eidas.impl.EidasAuthenticationRequestNoMetadata;
import eu.eidas.auth.commons.protocol.impl.EidasSamlBinding;
import eu.eidas.auth.commons.protocol.impl.SamlNameIdFormat;
import eu.eidas.auth.engine.ProtocolEngineNoMetadataI;
import eu.eidas.auth.engine.xml.opensaml.SAMLEngineUtils;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

/**
 * <p>
 * Class that performs the request of a HTTP service.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of Cl@ve suite systems.
 * </p>
 * 
 * @version 1.2, 18/10/2018.
 */
public abstract class AbstractHttpInvoker {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	/**
	 * Attribute that represents the configuration of the SAML request.
	 */
	protected final static ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);
	/**
	 * Attribute that represent the force authn of the SAML request.
	 */
	protected static final Boolean FORCE_AUTH_CHECK_DEF_VALUE = Boolean.TRUE;
	/**
	 * Attribute that represent the id policy value of the SAML request.
	 */
	protected static final String NAME_ID_POLICY_DEF_VALUE = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();
	/**
	 * Attribute that represent the level of assurance of the SAML request.
	 */
	protected static final String EIDAS_LOA_DEF_VALUE = LevelOfAssurance.LOW.stringValue();
	/**
	 * Constant attribute that represents the property key
	 * invoker.http.connect.timeout.
	 */
	public static final String INVOKER_HTTP_CONNECT_TIMEOUT = "invoker.http.connect.timeout";

	/**
	 * Constant attribute that represents the property key
	 * invoker.http.connection.request.timeout.
	 */
	public static final String INVOKER_HTTP_CONNECTION_REQUEST_TIMEOUT = "invoker.http.connection.request.timeout";

	/**
	 * Constant attribute that represents the property key
	 * invoker.http.socket.timeout.
	 */
	public static final String INVOKER_HTTP_SOCKET_TIMEOUT = "invoker.http.socket.timeout";

	/**
	 * Group of attributes that the SAML request could contain.
	 */
	protected static AttributeDefinition familyName;
	protected static AttributeDefinition firstName;
	protected static AttributeDefinition dateOfBirth;
	protected static AttributeDefinition personIdentifier;
	protected static AttributeDefinition legalName;
	protected static AttributeDefinition legalPersonIdentifier;

	static {
		familyName = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName").friendlyName("FamilyName").personType(PersonType.NATURAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentFamilyNameType", "eidas-natural").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
		firstName = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName").friendlyName("FirstName").personType(PersonType.NATURAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentGivenNameType", "eidas-natural").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
		dateOfBirth = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/DateOfBirt").friendlyName("DateOfBirth").personType(PersonType.NATURAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/naturalperson", "DateOfBirthType", "eidas-natural").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
		personIdentifier = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier").friendlyName("PersonIdentifier").personType(PersonType.NATURAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType", "eidas-natural").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
		legalName = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalName").friendlyName("LegalName").personType(PersonType.LEGAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalNameType", "eidas-legal").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
		legalPersonIdentifier = new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalPersonIdentifier").friendlyName("LegalPersonIdentifier").personType(PersonType.LEGAL_PERSON).required(true).transliterationMandatory(true).uniqueIdentifier(false).xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalPersonIdentifierType", "eidas-legal").attributeValueMarshaller(new StringAttributeValueMarshaller()).build();
	}

	/**
	 * Method that generate the SAML request
	 * 
	 * @param requestConf
	 *            request configuration for the SAML request
	 * @param service
	 *            service configuration from Monitoriza
	 * @return String return the SAML request generate
	 * @throws EIDASSAMLEngineException
	 */
	protected static String generateSamlRequest(final ClaveAgentConfType requestConf, final ConfigServiceDTO service) throws EIDASSAMLEngineException {
		String res = null;
		String spType;

		ImmutableAttributeMap reqAttrMapBuilder = ReqAtributos(requestConf);

		String providerName = requestConf.getRequest().getSamlRequest().getProviderName();
		LOGGER.debug("Provider name: " + providerName);
		String spApplication = requestConf.getRequest().getSamlRequest().getSPApplication();
		LOGGER.debug("SP application: " + spApplication);
		if (requestConf.getRequest().getSamlRequest().getSPType() == null) {
			spType = "public";
		} else {
			spType = requestConf.getRequest().getSamlRequest().getSPType().toString();
		}
		LOGGER.debug("Sp type: " + spType);
		String returnUrl = requestConf.getRequest().getSamlRequest().getAssertionConsumerServiceURL();
		LOGGER.debug("URL de retorno: " + returnUrl);

		String destination = (service.getSoapUrl() + service.getWsdl());
		LOGGER.debug("URL de destino: " + destination);

		// build the request
		EidasAuthenticationRequestNoMetadata.Builder reqBuilder = new EidasAuthenticationRequestNoMetadata.Builder();
		reqBuilder.destination(destination);
		reqBuilder.providerName(providerName);
		reqBuilder.spType(spType);
		reqBuilder.requestedAttributes(reqAttrMapBuilder);
		reqBuilder.levelOfAssurance(EIDAS_LOA_DEF_VALUE);

		reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.fromString("minimum").stringValue());

		reqBuilder.nameIdFormat(NAME_ID_POLICY_DEF_VALUE);

		reqBuilder.binding(EidasSamlBinding.EMPTY.getName());
		reqBuilder.assertionConsumerServiceURL(returnUrl);
		reqBuilder.forceAuth(FORCE_AUTH_CHECK_DEF_VALUE);

		reqBuilder.spApplication(spApplication);

		IRequestMessageNoMetadata binaryRequestMessage = null;
		EidasAuthenticationRequestNoMetadata authRequest = null;

		reqBuilder.id(SAMLEngineUtils.generateNCName());
		authRequest = reqBuilder.build();
		binaryRequestMessage = protocolEngine.generateRequestMessage(authRequest, true);

		res = EidasStringUtil.encodeToBase64(binaryRequestMessage.getMessageBytes());

		return res;
	}

	/**
	 * Method that create the HttpClient for the connection
	 * 
	 * @param requestConf
	 *            configuration for the creation of the connection
	 * @param service
	 *            configuration of Monitoriza for the creation of the connection
	 * @param ssl
	 *            Configuration of Keystore
	 * @return CloseableHttpClient 
	 * 			  return the CloseableHttpClient required for the connection
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws InvokerException 
	 */
	protected static CloseableHttpClient createHttpClient(final ClaveAgentConfType requestConf, final ConfigServiceDTO service, final KeyStore ssl) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, InvokerException {
		CloseableHttpClient res;
		CredentialsProvider credsProvider = null;
		SSLConnectionSocketFactory sslConnectionSocketFactory = null;
		RequestConfig reqConfig = null;

		LOGGER.debug("Inicio de la creación del cliente HTTP");
		reqConfig = getRequestConfig(requestConf);

		if (requestConf.getConnection() != null) {
			// Proxy Credentials
			credsProvider = createProxyCredentials(requestConf, reqConfig);
			// Configuración canal seguro
			sslConnectionSocketFactory = createSSLConnectionSocketFactory(service, requestConf, ssl);
		}

		// Creación petición HTTP
		res = createRequestHttp(service, sslConnectionSocketFactory, reqConfig, credsProvider);

		return res;
	}

	/**
	 * Method setting configuration for the creation of the connection
	 * 
	 * @param requestConf
	 * 				Configuration for the creation of the connection
	 * @return RequestConfig
	 * @throws InvokerException
	 */
	protected static RequestConfig getRequestConfig(final ClaveAgentConfType requestConf) throws InvokerException {
		RequestConfig res;
		RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(Integer.valueOf(StaticMonitorizaProperties.getProperty(INVOKER_HTTP_CONNECT_TIMEOUT))).setConnectionRequestTimeout(Integer.valueOf(StaticMonitorizaProperties.getProperty(INVOKER_HTTP_CONNECTION_REQUEST_TIMEOUT))).setSocketTimeout(Integer.valueOf(StaticMonitorizaProperties.getProperty(INVOKER_HTTP_SOCKET_TIMEOUT)));

		if (requestConf.getConnection() != null) {
			if (requestConf.getConnection().getProxy() != null) {
				LOGGER.debug("Se han introducido datos de conexión con proxy.");
				if (Integer.parseInt(requestConf.getConnection().getProxy().getPort()) < 1 || Integer.parseInt(requestConf.getConnection().getProxy().getPort()) > 65535) {
					throw new InvokerException("El puerto introducido es erróneo.");
				} else {
					LOGGER.debug("Configurando la conexión por medio de proxy.");
					HttpHost proxy = new HttpHost(requestConf.getConnection().getProxy().getHost(), Integer.parseInt(requestConf.getConnection().getProxy().getPort()));
					builder.setProxy(proxy);
				}
			}
		}
		
		res = builder.build();

		return res;
	}

	/**
	 * Method create request http client
	 * 
	 * @param service 
	 * 				Configuration of Monitoriza for the creation of the connection
	 * @param ssl
	 * 				Configuration of Keystore
	 * @param requestConf
	 * 				Configuration for the creation of the connection
	 * @return CloseableHttpClient
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static SSLConnectionSocketFactory createSSLConnectionSocketFactory(final ConfigServiceDTO service, final ClaveAgentConfType requestConf, KeyStore ssl) throws InvokerException {
		KeyManagerFactory kmf;
		KeyStore keystore = null;
		KeyManager[ ] keyManagers = null;
		PrivateKeyStrategy pks = null;
		SSLContext sslContext;
		SSLContextBuilder builder = null;
		SSLConnectionSocketFactory res = null;
		TrustManagerFactory tmf = null;
		String[ ] s = service.getBaseUrl().split(":");
		HostnameVerifier hostnameVerifier = null;;

		try {

			// Es conexión segura
			if (s[0].equalsIgnoreCase("https")) {
				// Almacen de confianza
				tmf = TrustManagerFactory.getInstance("SunX509");
				tmf.init(ssl);
				// Protocolo seuro
				sslContext = SSLContext.getInstance("SSL");

				// Autenticación Mutua
				if (requestConf.getConnection().getAuthenticationMutual() != null) {

					LOGGER.debug("Configurando el keystore para la autenticación mutua.");

					if (requestConf.getConnection().getAuthenticationMutual().getPath() == null && requestConf.getConnection().getAuthenticationMutual().getBase64() == null) {
						throw new InvokerException("La autenticación mutua necesita la dirección o la base64 del almacenamiento de las claves.");
					} else {
						if (requestConf.getConnection().getAuthenticationMutual().getPath() != null) {
							keystore = Utilities.loadKeystorePath(requestConf.getConnection().getAuthenticationMutual().getPath(), requestConf.getConnection().getAuthenticationMutual().getPasswordKeyStore(), requestConf.getConnection().getAuthenticationMutual().getTypeKeyStore());

						} else {
							keystore = Utilities.loadKeystoreBase64(requestConf.getConnection().getAuthenticationMutual().getBase64(), requestConf.getConnection().getAuthenticationMutual().getPasswordKeyStore(), requestConf.getConnection().getAuthenticationMutual().getTypeKeyStore());
						}

						kmf = KeyManagerFactory.getInstance("SunX509");
						kmf.init(keystore, requestConf.getConnection().getAuthenticationMutual().getPasswordKeyStore().toCharArray());

						// En el caso de estar configurado el alias a utilizar
						// del keystore
						if (requestConf.getConnection().getAuthenticationMutual().getAlias() != null && requestConf.getConnection().getAuthenticationMutual().getPasswordAlias() != null) {
							LOGGER.debug("Utilizando el alias " + requestConf.getConnection().getAuthenticationMutual().getAlias() + " para la autenticación mutua.");
							pks = new SelectByAlias(requestConf.getConnection().getAuthenticationMutual().getAlias());
							builder = SSLContexts.custom().loadKeyMaterial(keystore, requestConf.getConnection().getAuthenticationMutual().getPasswordAlias().toCharArray(), pks);
							sslContext = builder.build();
						} else {
							keyManagers = kmf.getKeyManagers();
						}
					}
				}

				sslContext.init(keyManagers, tmf.getTrustManagers(), null);

				res = new SSLConnectionSocketFactory(sslContext, new String[ ] { "TLSv1.2", "TLSv1.1" }, null, hostnameVerifier.ALLOW_ALL);
			}
		} catch (InvokerException e) {
			throw new InvokerException(e.getMessage());
		} catch (Exception e) {
			throw new InvokerException("Error al crear la conexión SSL de la petición: " + e.getMessage(), e);
		}
		return res;
	}

	/**
	 * Method create request http client
	 * 
	 * @param service 
	 * 				Configuration of Monitoriza for the creation of the connection
	 * @param sslsf
	 * 				Configuration SSL connection
	 * @param config 
	 * 				Request config 
	 * @return CloseableHttpClient
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static CloseableHttpClient createRequestHttp(ConfigServiceDTO service, SSLConnectionSocketFactory sslsf, RequestConfig config, CredentialsProvider credsProvider) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		CloseableHttpClient res = null;
		HttpClientBuilder httpClientBuilder;

		httpClientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config);

		if (sslsf != null) {
			httpClientBuilder.setSSLSocketFactory(sslsf);
		}

		// Credenciales del proxy
		if (credsProvider != null) {
			httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
		}

		res = httpClientBuilder.build();

		return res;
	}

	/**
	 * Method create Proxy credentials
	 * 
	 * @param requestConf
	 * 				Params of request XML file
	 * @param requestConfig
	 * 				Request config
	 * @return CredentialsProvider
	 * @throws InvokerException
	 */
	private static CredentialsProvider createProxyCredentials(ClaveAgentConfType requestConf, RequestConfig requestConfig) throws InvokerException {
		CredentialsProvider res = null;

		if (requestConfig.getProxy() != null) {
			if (requestConf.getConnection().getProxy().getUser() != null && requestConf.getConnection().getProxy().getPassword() == null) {
				LOGGER.warn("Ha introducido conexión con autenticacion por medio del proxy, sin la contraseña correspondiente.");
			} else if (requestConf.getConnection().getProxy().getUser() != null && requestConf.getConnection().getProxy().getPassword() != null) {
				res = new BasicCredentialsProvider();
				res.setCredentials(new AuthScope(requestConfig.getProxy()), new UsernamePasswordCredentials(requestConf.getConnection().getProxy().getUser(), requestConf.getConnection().getProxy().getPassword()));

			}
		}

		return res;

	}

	/**
	 * Method that adds the required attributes
	 * 
	 * @param requestConf
	 *            parameter that provides the attributes
	 * @return ImmutableAttributeMap.Builder return the map with the required
	 *         attributes
	 */
	protected static ImmutableAttributeMap ReqAtributos(ClaveAgentConfType requestConf) {
		ImmutableAttributeMap res;
		List<AttributeDefinition<?>> reqAttrList = new ArrayList<AttributeDefinition<?>>();
		if (requestConf.getRequest().getSamlRequest().getAttributes() == null)
			res = new ImmutableAttributeMap.Builder().putAll(reqAttrList).build();
		else {
			for (AttributeType attr: requestConf.getRequest().getSamlRequest().getAttributes().getAttribute()) {
				if (attr == AttributeType.FAMILY_NAME) {
					reqAttrList.add(familyName);
				} else if (attr == AttributeType.FIRST_NAME) {
					reqAttrList.add(firstName);
				} else if (attr == AttributeType.DATE_OF_BIRTH) {
					reqAttrList.add(dateOfBirth);
				} else if (attr == AttributeType.PERSON_IDENTIFIER) {
					reqAttrList.add(personIdentifier);
				} else if (attr == AttributeType.LEGAL_NAME) {
					reqAttrList.add(legalName);
				} else if (attr == AttributeType.LEGAL_PERSON_IDENTIFIER) {
					reqAttrList.add(legalPersonIdentifier);
				}
			}
		}
		res = new ImmutableAttributeMap.Builder().putAll(reqAttrList).build();
		return res;
	}
}