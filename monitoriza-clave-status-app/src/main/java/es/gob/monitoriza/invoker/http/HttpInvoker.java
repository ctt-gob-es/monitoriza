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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
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

	private static String samlRequest;

	private static String nodeServiceUrl = "";

	private static boolean forceAuthCheck = true;
	private static String nameIDPolicy = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();

	private static String providerName = "";
	private static String spApplication = "";
	private static String spType = "";

	private static String returnUrl = "";
	private static String eidasloa = LevelOfAssurance.LOW.stringValue();

	/**
	 * Constructor method for the class HttpInvoker.java.
	 */
	public HttpInvoker() {

	}

	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param requestFile
	 *            request file which contents the HTTP message.
	 * @param service
	 *            DTOService that contains the configuration data for the
	 *            service.
	 * @return Long that represents the time in milliseconds that has taken to
	 *         complete the request. If there is some configuration or
	 *         communication problem, this value will be null.
	 * @throws IOException
	 * @throws SamlEngineConfigurationException
	 */
	public static Long sendRequest(final File file, final ConfigServiceDTO service, final KeyStore ssl)
			throws IOException, SamlEngineConfigurationException {

		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);

		Properties prop = new Properties();
		prop.loadFromXML(new FileInputStream(file));
		providerName = prop.getProperty("samlRequest.providerName");
		spApplication = prop.getProperty("samlRequest.SPApplication");
		spType = prop.getProperty("samlRequest.SPType");
		returnUrl = prop.getProperty("samlRequest.assertionConsumerServiceURL");

		forceAuthCheck = false;
		nodeServiceUrl = (service.getBaseUrl() + service.getSoapUrl() + service.getWsdl());

		ImmutableAttributeMap.Builder reqAttrMapBuilder = new ImmutableAttributeMap.Builder();

		reqAttrMapBuilder.putPrimaryValues(
				new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/RelayState")
						.friendlyName("RelayState").personType(PersonType.NATURAL_PERSON).required(false)
						.uniqueIdentifier(true)
						.xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType",
								"eidas-natural")
						.attributeValueMarshaller(new StringAttributeValueMarshaller()).build(),
				SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8));

		// build the request
		EidasAuthenticationRequestNoMetadata.Builder reqBuilder = new EidasAuthenticationRequestNoMetadata.Builder();
		reqBuilder.destination(nodeServiceUrl);
		reqBuilder.providerName(providerName);
		reqBuilder.spType(spType);
		reqBuilder.requestedAttributes(reqAttrMapBuilder.build());
		if (LevelOfAssurance.getLevel(eidasloa) == null) {
			reqBuilder.levelOfAssurance(LevelOfAssurance.LOW.stringValue());
		} else {
			reqBuilder.levelOfAssurance(eidasloa);
		}

		reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.fromString("minimum").stringValue());

		if (nameIDPolicy != null) {
			reqBuilder.nameIdFormat(nameIDPolicy);
		} else {
			reqBuilder.nameIdFormat(SamlNameIdFormat.UNSPECIFIED.getNameIdFormat());
		}

		reqBuilder.binding(EidasSamlBinding.EMPTY.getName());
		reqBuilder.assertionConsumerServiceURL(returnUrl);
		reqBuilder.forceAuth(forceAuthCheck);

		reqBuilder.spApplication(spApplication);

		IRequestMessageNoMetadata binaryRequestMessage = null;
		EidasAuthenticationRequestNoMetadata authRequest = null;
		try {
			reqBuilder.id(SAMLEngineUtils.generateNCName());
			authRequest = reqBuilder.build();
			binaryRequestMessage = protocolEngine.generateRequestMessage(authRequest, true);
		} catch (EIDASSAMLEngineException e) {

		}

		samlRequest = EidasStringUtil.encodeToBase64(binaryRequestMessage.getMessageBytes());

		Long tiempoTotal = null;
		LocalTime beforeCall = null;
		String[] s = service.getBaseUrl().split(":");

		try {
			CloseableHttpClient client;
			if(s[0].equals("http")) {			
				client = HttpClients.createDefault();
				
			} else {
				// load the keystore containing the client certificate - keystore type is probably jks or pkcs12 
				final KeyStore keystore = KeyStore.getInstance(prop.getProperty("authenticationMutua.typeKeystore")); 
				InputStream keystoreInput = new FileInputStream(new File(prop.getProperty("authenticationMutua.pathKeystore")));
				// get the keystore as an InputStream from somewhere 
				keystore.load(keystoreInput, prop.getProperty("authenticationMutua.passwordKeystore").toCharArray()); 
				
				SSLContext sslContext = new SSLContextBuilder().loadKeyMaterial(keystore, prop.getProperty("authenticationMutua.passwordKeystore").toCharArray()).build();
				
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				          new String[]{"TLSv1.2", "TLSv1.1"},
				          null,
				          SSLConnectionSocketFactory.getDefaultHostnameVerifier());
				
				client = HttpClients.custom().setSSLSocketFactory(sslsf).build(); 
			}
			HttpPost httpPost = new HttpPost(nodeServiceUrl);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("SAMLRequest", samlRequest));
			params.add(new BasicNameValuePair("RelayState", SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8)));

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			beforeCall = LocalTime.now();
			//CloseableHttpResponse response = client.execute(httpPost);
			client.execute(httpPost);
		} catch (

		UnsupportedEncodingException e) {
			e.printStackTrace();
			beforeCall = LocalTime.now();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} finally {
			LocalTime afterCall = LocalTime.now();
			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
		}

		return tiempoTotal;
	}

}
