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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
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

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	private static String samlRequest;

	private static String nodeServiceUrl = "";

	private static boolean forceAuthCheck = true;
	private static String nameIDPolicy = SamlNameIdFormat.UNSPECIFIED.getNameIdFormat();

	private static String providerName = "";
	private static String spApplication = "";

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
	public static Long sendRequest(final File file, final ConfigServiceDTO service, final KeyStore ssl) throws IOException, SamlEngineConfigurationException {
		
//		ProtocolEngineNoMetadataI protocolEngine = null;
		
		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory
				.getSpProtocolEngine(Constants.SP_CONF);
//		
//		ProtocolEngineConfigurationFactoryNoMetadata protocolEngineConfigurationFactory = 
//        		new ProtocolEngineConfigurationFactoryNoMetadata("MonitorizaSamlEngine.xml", null,
//        				"C:\\servidores\\Monitoriza\\peticionesClave\\config\\");
//		
//		protocolEngine = protocolEngineConfigurationFactory.getConfiguration("MonitorizaNoMetadata");
		
//		ProtocolEngineFactory protocolEngineFactory = new ProtocolEngineFactory(
//                new ProtocolEngineConfigurationFactory("MonitorizaSamlEngine.xml", null, null));
		
		Properties prop = new Properties();
		prop.loadFromXML(new FileInputStream(file));
		providerName = prop.getProperty("provider.name");
		spApplication = prop.getProperty("monitoriza.aplication");
		returnUrl = prop.getProperty("monitoriza.return"); 

		forceAuthCheck = false;
		nodeServiceUrl = (service.getBaseUrl());

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

		samlRequest = EidasStringUtil.encodeToBase64(binaryRequestMessage.getMessageBytes());

		Long tiempoTotal = null;
		byte[] requestByte = samlRequest.getBytes();

		try {

			// Establecemos el timeout de la conexión y de la lectura
			URL endpoint = new URL(new URL(service.getBaseUrl()), service.getOcspContext(), new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();

					if (connection instanceof HttpsURLConnection) {

						String msgError = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS004);

						try {

							TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
							SSLContext ctx = SSLContext.getInstance("SSL");
							tmf.init(ssl);
							ctx.init(null, tmf.getTrustManagers(), null);
							SSLSocketFactory factory = ctx.getSocketFactory();
							((HttpsURLConnection) connection).setSSLSocketFactory(factory);

						} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {

							LOGGER.error(msgError, e);

						}

						((HttpsURLConnection) connection).setHostnameVerifier(new NameVerifier());

					}

					return (connection);
				}

			});

			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS008, new Object[] { endpoint }));

			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setConnectTimeout(service.getTimeout().intValue());
			con.setReadTimeout(service.getTimeout().intValue());
			con.setRequestProperty("Content-Type", "application/http-request");
			con.setRequestProperty("Accept", "application/http-response");
			con.setRequestProperty("charset", "utf-8");
			con.setRequestProperty("Content-Length", Integer.toString(requestByte.length));
			con.setUseCaches(false);

			// Se escribe la petición HTTP en la conexión
			OutputStream os = con.getOutputStream();
			os.write(requestByte);
			os.flush();
			os.close();

			LocalTime beforeCall = LocalTime.now();
			// Conexión...
			con.connect();
			// Comprobamos que la conexión se estableció correctamente
			if (con.getResponseCode() / 100 != 2) {
				// Si hay algún problema de conexión, considero la petición como
				// perdida...
				LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS005));
			} else {
				// Lectura...
				con.getContent();
				LocalTime afterCall = LocalTime.now();
				tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY)
						- beforeCall.getLong(ChronoField.MILLI_OF_DAY);
			}

		} catch (IOException e) {

			LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS005), e);
		}

		return tiempoTotal;
	}

	/**
	 * <p>
	 * Private class that allows to verify the host of the HTTPS service.
	 * </p>
	 * <b>Project:</b>
	 * <p>
	 * Application for monitoring services of @firma suite systems.
	 * </p>
	 * 
	 * @version 1.0, 02/05/2018.
	 */
	private static class NameVerifier implements HostnameVerifier {

		/**
		 * {@inheritDoc}
		 * 
		 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
		 *      javax.net.ssl.SSLSession)
		 */
		public boolean verify(final String hostname, final SSLSession session) {
			return true;
		}
	}
	
	public static void main(String[] args) throws SamlEngineConfigurationException {
		try {
			File f = new File(
					"C:/Users/samuel.zuluaga/Desktop/workspaceMonitorizaManu/monitoriza/config/monitoriza.xml");
			ConfigServiceDTO s = new ConfigServiceDTO("");
			s.setBaseUrl("https://localhost:8443/EidasNode/ServiceProvider");
			KeyStore k = loadSslTruststore();

			sendRequest(f, s, k);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static KeyStore loadSslTruststore() {

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new FileInputStream("C:/Trabajo/truststoreWS.jks");) {
			// Accedemos al almacén de confianza SSL
			// msgError =
			// Language.getResMonitoriza(LogMessages.ERROR_ACCESS_CERTIFICATE_SSL);
			cer = KeyStore.getInstance("JKS");

			cer.load(readStream, "12345".toCharArray());

		} catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
		 

}
