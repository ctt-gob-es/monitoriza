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

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.invoker.http.conf.messages.AttributeType;
import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;
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
	
	protected static final Boolean FORCE_AUTH_CHECK_DEF_VALUE = Boolean.TRUE;
	protected static final String NAME_ID_POLICY_DEF_VALUE = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();
	protected static final String EIDAS_LOA_DEF_VALUE = LevelOfAssurance.LOW.stringValue();

	/**
	 * Method that generate the SAML request
	 * @param requestConf
	 * 					request configuration for the SAML request
	 * @param service
	 * 				service configuration from Monitoriza
	 * @return String
	 * 				return the SAML request generate
	 * @throws EIDASSAMLEngineException
	 */
	protected static String generateSamlRequest(final ClaveAgentConfType requestConf, final ConfigServiceDTO service) throws EIDASSAMLEngineException {
		String res = null;
		String spType;
		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);
		
		ImmutableAttributeMap.Builder reqAttrMapBuilder = ReqAtributos(requestConf);
		
		String providerName = requestConf.getRequest().getSamlRequest().getProviderName();
		LOGGER.debug("Provider name: " + providerName);
		String spApplication = requestConf.getRequest().getSamlRequest().getSPApplication();
		LOGGER.debug("SP application: " + spApplication);
		if(requestConf.getRequest().getSamlRequest().getSPType().toString().equals("")) {
			spType ="public";
		}else {
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
	
	/**
	 * Method that create the HttpClient for the connection
	 * @param requestConf
	 * 					configuration for the creation of the connection
	 * @param service
	 * 				configuration of Monitoriza for the creation of the connection
	 * @return CloseableHttpClient
	 * 							return the CloseableHttpClient required for the connection
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	protected static CloseableHttpClient createHttpClient(final ClaveAgentConfType requestConf, final ConfigServiceDTO service) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
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
	
	/**
	 * Method that adds the required attributes 
	 * @param requestConf
	 * 					parameter that provides the attributes
	 * @return ImmutableAttributeMap.Builder
	 * 										return the map with the required attributes
	 */
	protected static ImmutableAttributeMap.Builder ReqAtributos(ClaveAgentConfType requestConf) {
		ImmutableAttributeMap.Builder reqAttrMapBuilder = new ImmutableAttributeMap.Builder();
		for(AttributeType attr:  requestConf.getRequest().getSamlRequest().getAttributes().getAttribute()) {
			if(attr == AttributeType.FAMILY_NAME) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName")
		    			.friendlyName("FamilyName")
		    			.personType(PersonType.NATURAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentFamilyNameType", "eidas-natural")
		    			.build());
			} else if(attr == AttributeType.FIRST_NAME) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName")
		    			.friendlyName("FirstName")
		    			.personType(PersonType.NATURAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "CurrentGivenNameType", "eidas-natural")
		    			.build());
			} else if(attr == AttributeType.DATE_OF_BIRTH) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/DateOfBirt")
		    			.friendlyName("DateOfBirth")
		    			.personType(PersonType.NATURAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "DateOfBirthType", "eidas-natural")
		    			.build());
			} else if(attr == AttributeType.PERSON_IDENTIFIER) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier")
		    			.friendlyName("PersonIdentifier")
		    			.personType(PersonType.NATURAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType", "eidas-natural")
		    			.build());
			} else if(attr == AttributeType.LEGAL_NAME) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalName")
		    			.friendlyName("LegalName")
		    			.personType(PersonType.LEGAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalNameType", "eidas-legal")
		    			.build());
			}else if(attr == AttributeType.LEGAL_PERSON_IDENTIFIER) {
				reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://eidas.europa.eu/attributes/legalperson/LegalPersonIdentifier")
		    			.friendlyName("LegalPersonIdentifier")
		    			.personType(PersonType.LEGAL_PERSON)
		    			.required(true)
		    			.uniqueIdentifier(true)
		    			.xmlType("http://eidas.europa.eu/attributes/legalperson", "LegalPersonIdentifierType", "eidas-legal")
		    			.build());
			}
		}
		return reqAttrMapBuilder;
	}
}