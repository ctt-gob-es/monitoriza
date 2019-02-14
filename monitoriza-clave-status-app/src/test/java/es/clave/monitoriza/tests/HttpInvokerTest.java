package es.clave.monitoriza.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;
import es.gob.monitoriza.invoker.http.conf.util.Utilities;
import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
import es.gob.monitoriza.utilidades.UtilsStringChar;
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

public class HttpInvokerTest {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	static {
		String path = HttpInvokerTest.class.getClassLoader().getResource("logging.properties").getFile();
		System.setProperty("java.util.logging.config.file", path);
	}

	public static void main(String[] args) {
		try {
			URL xmlFileUrl = HttpInvokerTest.class.getResource("/monitoriza.xml");
			File xmlFile = new File(xmlFileUrl.toURI());
			sendRequest(xmlFile);
		} catch (SamlEngineConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static Long sendRequest(final File file) throws IOException, SamlEngineConfigurationException {
		try {
			ClaveAgentConfType requestConf = Utilities.transformJabx(file);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

		String samlRequest;

		String nodeServiceUrl = UtilsStringChar.EMPTY_STRING;

		boolean forceAuthCheck = true;
		String nameIDPolicy = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();

		String providerName = UtilsStringChar.EMPTY_STRING;
		String spApplication = UtilsStringChar.EMPTY_STRING;

		String returnUrl = UtilsStringChar.EMPTY_STRING;
		String eidasloa = LevelOfAssurance.LOW.stringValue();

		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);

		Properties prop = new Properties();
		prop.loadFromXML(new FileInputStream(file));
		providerName = prop.getProperty("provider.name");
		spApplication = prop.getProperty("monitoriza.aplication");
		returnUrl = prop.getProperty("monitoriza.return");

		forceAuthCheck = false;
		nodeServiceUrl = ("localhost:8888/EidasNode/ServiceProvider");

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

		Long tiempoTotal = null;
		samlRequest = EidasStringUtil.encodeToBase64(binaryRequestMessage.getMessageBytes());
		// tiempoTotal = sendRequestHttpUrlConn(samlRequest,
		// SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8));
		tiempoTotal = sendRequestHttpClient(samlRequest, SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8));

		return tiempoTotal;
	}

	private static Long sendRequestHttpUrlConn(String samlRequest, String relayState) {
		Long res = 0L;
		URL endpoint;
		try {
			endpoint = new URL("http://localhost:8888/EidasNode/ServiceProvider");

			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setConnectTimeout(28000);
			con.setReadTimeout(28000);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// con.setRequestProperty("Accept", "application/http-response");
			con.setRequestProperty("charset", "utf-8");
			//
			con.setUseCaches(false);

			Map<String, String> params = new HashMap<String, String>();
			params.put("SAMLRequest", samlRequest);
			params.put("RelayState", relayState);
			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}

			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			con.setRequestProperty("Content-Length", Integer.toString(postDataBytes.length));

			// Se escribe la petición HTTP en la conexión
			con.getOutputStream().write(postDataBytes);
			con.getOutputStream().flush();
			con.getOutputStream().close();

			LocalTime beforeCall = LocalTime.now();
			// Conexión...
			con.connect();
			// Comprobamos que la conexión se estableció correctamente
			if (con.getResponseCode() / 100 != 2) {
				// Si hay algún problema de conexión, considero la petición como
				// perdida...
				LOGGER.error("No fue posible enviar la petición");
			} else {
				// Lectura...
				con.getContent();
				LocalTime afterCall = LocalTime.now();
				res = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	private static Long sendRequestHttpClient(String samlRequest, String relayState) {
		LocalTime beforeCall = null;
		Long res = 0L;

		try {

			//Configuración del Proxy
			//HttpHost proxy = new HttpHost("localhost", 8888);
			//client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			
//			String username = "user";
//			String password = "pass";
//			Credentials credentials = new UsernamePasswordCredentials(username, password);
//			AuthScope authScope = new AuthScope("proxy ip", 8888);
//			
//			client.getConnectionManager().		
			
			//Autenticación mutua
			
			// load the keystore containing the client certificate - keystore type is probably jks or pkcs12 
			final KeyStore keystore = KeyStore.getInstance("pkcs12"); 
			//InputStream keystoreInput = new FileInputStream(new File("C:\\Trabajo\\Proyectos\\CLAVE\\config\\KeyStore.jks"));
			InputStream keystoreInput = new FileInputStream(new File("C:\\Users\\samuel.zuluaga\\Desktop\\PF_ACTIVO_EIDAS.p12"));
			// TODO get the keystore as an InputStream from somewhere 
			keystore.load(keystoreInput, "G5cp,fYC9gje".toCharArray()); 
			KeyManagerFactory tmf = KeyManagerFactory.getInstance("SunX509");
			tmf.init(keystore, "G5cp,fYC9gje".toCharArray());
//			if(Autenticacion mutua) {
//				InputStream inKs = new FileInputStream(direccion);
//				KeyStore ks = KeyStore.getInstance("pkcs12");
//				String pass = "password";
//				ks.load(inKs, pass.toCharArray());
//				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//				kmf.init(ks, pass.toCharArray());
//				
//				SSLContext sslContext = SSLContext.getInstance("SSL");
//				sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//			}else {
//				SSLContext sslContext = SSLContext.getInstance("SSL");
//				sslContext.init(null, tmf.getTrustManagers(), null);


//			SSLContext sslContext = new SSLContextBuilder().loadKeyMaterial(keystore, "G5cp,fYC9gje".toCharArray()).build();
			
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(tmf.getKeyManagers(), null, null);
			
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
			          new String[]{"TLSv1.2", "TLSv1.1"},
			          null,
			          SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			
			CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();			
			
			//CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://localhost:4443/IdP2/AuthenticateCitizen");
			//httpPost.addHeader("Country", "ES");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("SAMLRequest", samlRequest));
			params.add(new BasicNameValuePair("RelayState", relayState));
			//params.add(new BasicNameValuePair("Country", "ES"));

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			beforeCall = LocalTime.now();
			CloseableHttpResponse response = client.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			beforeCall = LocalTime.now();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}/* catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LocalTime afterCall = LocalTime.now();
			res = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
		}

		return res;
	}

}
