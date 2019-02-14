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
 * <b>Date:</b><p>18 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 18/12/2018.
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

import javax.xml.bind.JAXBException;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;
import es.gob.monitoriza.invoker.http.conf.messages.ParamType;
import es.gob.monitoriza.invoker.http.conf.util.Utilities;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
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
 * @version 1.3, 18/12/2018.
 */
public class HttpInvoker extends AbstractHttpInvoker {

	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param file
	 *            request file which contents the HTTP configuration.
	 * @param service
	 *            DTOService that contains the configuration data for the service.
	 * @return Long that represents the time in milliseconds that has taken to
	 *         complete the request. If there is some configuration or communication
	 *         problem, this value will be null.
	 * @throws InvokerException
	 */
	public static Long sendRequest(final File file, final ConfigServiceDTO service, final KeyStore ssl)
			throws InvokerException {
		CloseableHttpClient httpClient;
		Long tiempoTotal = null;
		LocalTime beforeCall = null;
		ClaveAgentConfType requestConf;
		String samlRequest;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		HttpPost httpPost;

		try {
			requestConf = Utilities.transformJabx(file);
			samlRequest = AbstractHttpInvoker.generateSamlRequest(requestConf, service);
			LOGGER.debug("Petición SAML generada: " + samlRequest);

			httpPost = new HttpPost(service.getSoapUrl() + service.getWsdl());
			LOGGER.debug("URL generada: " + httpPost);

			// Comprobamos si tenemos petición HTTP
			if (requestConf.getRequest().getHttpRequest() != null) {
				// Comprobacion parametros de la cabecera de la peticion HTTP
				if (requestConf.getRequest().getHttpRequest().getHeaders() != null) {
					for (ParamType paramH : requestConf.getRequest().getHttpRequest().getHeaders().getParam()) {
						if (paramH != null) {
							httpPost.addHeader(paramH.getId(), paramH.getValue());
						}
					}
				}

				// Comprobacion parametros de la peticion HTTP
				if (requestConf.getRequest().getHttpRequest().getParams() != null) {
					for (ParamType param : requestConf.getRequest().getHttpRequest().getParams().getParam()) {
						if (param != null) {
							params.add(new BasicNameValuePair(param.getId(), param.getValue()));
						}
					}
				}
			}

			params.add(new BasicNameValuePair("SAMLRequest", samlRequest));
			params.add(new BasicNameValuePair("RelayState", SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8)));

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			beforeCall = LocalTime.now();

			httpClient = createHttpClient(requestConf, service, ssl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			
			// Comprobamos que la conexión se estableció correctamente
			String result = requestConf.getRequest().getHttpRequest().getResult();
			String[] resultSplit = result.split(",");
			Boolean resultCodeResponse = false;
			
			for(int i=0; i<resultSplit.length; i++) {
				if (httpResponse.getStatusLine().getStatusCode() == Integer.parseInt(resultSplit[i])) {
					resultCodeResponse = true;
				}
			}
			
			if (!resultCodeResponse) {
				// Si hay algún problema de conexión, considero la petición como perdida...
				LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS005));
				tiempoTotal = null;
			} else {
				LocalTime afterCall = LocalTime.now();
				tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
			}

		} catch (JAXBException e) {
			throw new InvokerException("Error al cargar el archivo xml de configuración" + e.getMessage(), e);
		} catch (EIDASSAMLEngineException e) {
			throw new InvokerException("Error al generar la petición SAML" + e.getMessage(), e);
		} catch (IOException e) {
			throw new InvokerException("Error al crear la conexión con el servicio " + e.getMessage(), e);
		} catch (KeyStoreException e) {
			throw new InvokerException("Error al cargar el keystore proporcionado" + e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient" + e.getMessage(), e);
		} catch (CertificateException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient" + e.getMessage(), e);
		} catch (KeyManagementException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient" + e.getMessage(), e);
		} catch (UnrecoverableKeyException e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient" + e.getMessage(), e);
		} catch (Exception e) {
			throw new InvokerException("Error al generar la implementación de HTTPClient" + e.getMessage(), e);
		}

		return tiempoTotal;
	}

}