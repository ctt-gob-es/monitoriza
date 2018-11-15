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

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBException;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.invoker.http.conf.messages._1_0.ParamType;
import es.gob.monitoriza.invoker.http.conf.messages._1_0.RegisterClaveRequestType;
import es.gob.monitoriza.invoker.http.conf.util.Utilities;
import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
import eu.eidas.auth.commons.attribute.ImmutableAttributeMap;
import eu.eidas.auth.commons.attribute.PersonType;
import eu.eidas.auth.commons.protocol.IRequestMessageNoMetadata;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssurance;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssuranceComparison;
import eu.eidas.auth.commons.protocol.eidas.impl.EidasAuthenticationRequestNoMetadata;
import eu.eidas.auth.commons.protocol.impl.EidasSamlBinding;
import eu.eidas.auth.commons.protocol.impl.SamlNameIdFormat;
import eu.eidas.auth.engine.ProtocolEngineNoMetadataI;
import eu.eidas.auth.engine.configuration.SamlEngineConfigurationException;
import eu.eidas.auth.engine.xml.opensaml.SAMLEngineUtils;
import eu.eidas.auth.engine.xml.opensaml.SecureRandomXmlIdGenerator;
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
public class HttpInvoker {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	private static final Boolean FORCE_AUTH_CHECK_DEF_VALUE = Boolean.TRUE;
	private static final String NAME_ID_POLICY_DEF_VALUE = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();
	private static final String EIDAS_LOA_DEF_VALUE = LevelOfAssurance.LOW.stringValue();

	
	private static final ImmutableAttributeMap.Builder reqAttrMapBuilder;
	
	static {		
		reqAttrMapBuilder = new ImmutableAttributeMap.Builder();
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName")
    			.friendlyName("FamilyName")
    			.personType(PersonType.NATURAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentFamilyNameType", "eidas-natural")
    			.build());
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName")
    			.friendlyName("FirstName")
    			.personType(PersonType.NATURAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentGivenNameType", "eidas-natural")
    			.build());
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/DateOfBirt")
    			.friendlyName("DateOfBirth")
    			.personType(PersonType.NATURAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "DateOfBirthType", "eidas-natural")
    			.build());
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier")
    			.friendlyName("PersonIdentifier")
    			.personType(PersonType.NATURAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType", "eidas-natural")
    			.build());
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalName")
    			.friendlyName("LegalName")
    			.personType(PersonType.LEGAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalNameType", "eidas-legal")
    			.build());
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalPersonIdentifier")
    			.friendlyName("LegalPersonIdentifier")
    			.personType(PersonType.LEGAL_PERSON)
    			.required(true)
    			.uniqueIdentifier(true)
    			.xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalPersonIdentifierType", "eidas-legal")
    			.build());
	}


	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param file
	 *            request file which contents the HTTP configuration.
	 * @param service
	 *            DTOService that contains the configuration data for the
	 *            service.
	 * @return Long that represents the time in milliseconds that has taken to
	 *         complete the request. If there is some configuration or
	 *         communication problem, this value will be null.
	 * @throws IOException
	 * @throws SamlEngineConfigurationException
	 * @throws JAXBException 
	 */
	public static Long sendRequest(final File file, final ConfigServiceDTO service, final KeyStore ssl) throws InvokerException {
		CloseableHttpClient httpClient;
		Long tiempoTotal = null;
		LocalTime beforeCall = null;
		RegisterClaveRequestType requestConf;
		String samlRequest;
		
		try {
			requestConf = Utilities.transformJabx(file);
			samlRequest = generateSamlRequest(requestConf, service);
			
			LOGGER.debug("Petición SAML generada: " + samlRequest);
			
			HttpPost httpPost = new HttpPost(service.getSoapUrl() + service.getWsdl());
			
			for(ParamType paramH: requestConf.getRequest().getHttpRequest().getHeaders().getParam()) {
				if(paramH != null) {
					httpPost.addHeader(paramH.getId(), paramH.getValue());
				}
			}
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for(ParamType param: requestConf.getRequest().getHttpRequest().getParams().getParam()) {
				if(param != null) {
					params.add(new BasicNameValuePair(param.getId(), param.getValue()));
				}	
			}
			
			params.add(new BasicNameValuePair("SAMLRequest", samlRequest));
			params.add(new BasicNameValuePair("RelayState", SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8)));

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			beforeCall = LocalTime.now();
			
			httpClient =  createHttpClient(requestConf, service);
			httpClient.execute(httpPost);
		} catch(JAXBException e) {
			throw new InvokerException("Error al cargar el archivo xml de configuración" + e.getMessage(),e);
		} catch (EIDASSAMLEngineException e) {
			throw new InvokerException("Error al generar la petición SAML"+ e.getMessage(),e);
		} catch (IOException e) {
			throw new InvokerException("Error al crear la conexión con el servicio "+ e.getMessage(),e);
		} catch (KeyStoreException e) {
			throw new InvokerException("Error al cargar el keystore proporcionado"+ e.getMessage(),e);
		} catch (NoSuchAlgorithmException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient"+ e.getMessage(),e);
		} catch (CertificateException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient"+ e.getMessage(),e);
		} catch (KeyManagementException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient"+ e.getMessage(),e);
		} catch (UnrecoverableKeyException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient"+ e.getMessage(),e);
		} finally {
			LocalTime afterCall = LocalTime.now();
			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
		}
		
		return tiempoTotal;
	}

	private static String generateSamlRequest(final RegisterClaveRequestType requestConf, final ConfigServiceDTO service) throws EIDASSAMLEngineException {
		String res = null;
		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);
		
		
		
		String providerName = requestConf.getRequest().getSamlRequest().getProviderName();
		LOGGER.debug("Provider name: " + providerName);
		String spApplication = requestConf.getRequest().getSamlRequest().getSPApplication();
		LOGGER.debug("SP application: " + spApplication);
		String spType = requestConf.getRequest().getSamlRequest().getSPType().toString();
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
		reqBuilder.requestedAttributes(reqAttrMapBuilder.build());
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
	
	private static CloseableHttpClient createHttpClient(final RegisterClaveRequestType requestConf, final ConfigServiceDTO service) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
		CloseableHttpClient res;
		
		String[] s = service.getBaseUrl().split(":");
		
		if(s[0].equals("http")) {			
			res = HttpClients.createDefault();
		} else {
			KeyStore keystore = Utilities.LoadKeystore(requestConf.getConnection().getAuthenticationMutual().getPath().toString(), 
					requestConf.getConnection().getAuthenticationMutual().getPasswordKeyStore().toString(),
					requestConf.getConnection().getAuthenticationMutual().getTypeKeyStore().toString());
			KeyManagerFactory tmf = KeyManagerFactory.getInstance("SunX509");
			tmf.init(keystore, requestConf.getConnection().getAuthenticationMutual().getPasswordKeyStore().toCharArray());
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(tmf.getKeyManagers(), null, null);
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.2", "TLSv1.1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			
			res = HttpClients.custom().setSSLSocketFactory(sslsf).build(); 
		}
		
		return res;
	}
}