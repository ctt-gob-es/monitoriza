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
import java.util.List;

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
import es.gob.monitoriza.invoker.http.conf.messages.AttributeType;
import es.gob.monitoriza.invoker.http.conf.messages.ParamType;
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
public class HttpInvoker extends AbstractHttpInvoker {

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
	 * @throws InvokerException TODO
	 */
	public static Long sendRequest(final File file, final ConfigServiceDTO service, final KeyStore ssl) throws InvokerException {
		CloseableHttpClient httpClient;
		Long tiempoTotal = null;
		LocalTime beforeCall = null;
		ClaveAgentConfType requestConf;
		String samlRequest;
		
		try {
			requestConf = Utilities.transformJabx(file);
			samlRequest = AbstractHttpInvoker.generateSamlRequest(requestConf, service);
			
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

}