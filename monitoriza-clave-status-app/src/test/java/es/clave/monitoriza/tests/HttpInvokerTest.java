package es.clave.monitoriza.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
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
	      String path = HttpInvokerTest.class.getClassLoader()
	                              .getResource("logging.properties")
	                              .getFile();
	      System.setProperty("java.util.logging.config.file", path);
	  }
	
	public static void main(String[] args) {	
		try {
			sendRequest(new File("C:\\Users\\samuel.zuluaga\\Desktop\\workspaceMonitorizaManu\\monitoriza\\config\\monitoriza.xml"));
		} catch (SamlEngineConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static Long sendRequest(final File file) throws IOException, SamlEngineConfigurationException {
	
	String samlRequest;

	String nodeServiceUrl = "";

	boolean forceAuthCheck = true;
	String nameIDPolicy = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();

	String providerName = "";
	String spApplication = "";

	String returnUrl = "";
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
						.xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType", "eidas-natural")
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
		//tiempoTotal = sendRequestHttpUrlConn(samlRequest, SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8)); 
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
			//con.setRequestProperty("Accept", "application/http-response");
			con.setRequestProperty("charset", "utf-8");
			//
			con.setUseCaches(false);

			Map<String,String> params = new HashMap<String,String>();
			params.put("SAMLRequest", samlRequest);
			params.put("RelayState", relayState);
		            StringBuilder postData = new StringBuilder();
			for (Map.Entry<String,String> param : params.entrySet()) {
				if (postData.length() != 0) postData.append('&');
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
				res = afterCall.getLong(ChronoField.MILLI_OF_DAY)
						- beforeCall.getLong(ChronoField.MILLI_OF_DAY);
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
		
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://localhost:8888/EidasNode/ServiceProvider");
	 
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("SAMLRequest", samlRequest));
	    params.add(new BasicNameValuePair("RelayState", relayState));	    
	    try {	    
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			beforeCall = LocalTime.now();
			CloseableHttpResponse response = client.execute(httpPost);
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			beforeCall = LocalTime.now();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LocalTime afterCall = LocalTime.now();
			res = afterCall.getLong(ChronoField.MILLI_OF_DAY)
					- beforeCall.getLong(ChronoField.MILLI_OF_DAY);
		}
	    
	    return res;
	}
	
}
