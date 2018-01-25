/* 
// Copyright (C) 2018, Gobierno de España
// This program is licensed and may be used, modified and redistributed under the terms
// of the European Public License (EUPL), either version 1.1 or (at your
// option) any later version as soon as they are approved by the European Commission.
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// or implied. See the License for the specific language governing permissions and
// more details.
// You should have received a copy of the EUPL1.1 license
// along with this program; if not, you may find it at
// http://joinup.ec.europa.eu/software/page/eupl/licence-eupl

/** 
 * <b>File:</b><p>es.gob.monitoriza.invoker.soap.HttpSoapInvoker.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>22 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 22 ene. 2018.
 */
package es.gob.monitoriza.invoker.soap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.dto.DTOService;
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.util.FileUtils;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22 ene. 2018.
 */
public class HttpSoapInvoker {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param requestFile request file which contents the SOAP message.
	 * @param serviceName name of the service to invoke. It's should match with the real name of the service since it will be inserted in the URL of invocation.
	 * @return the time...
	 */
	public static Long sendRequest(final File requestFile, final DTOService service, final Map<String,String> statusHolder) {
		// Obtenemos el contenido del fichero
		String soapMsg = FileUtils.readFile(requestFile);
		Long tiempoTotal = null;

		try (InputStream is = new ByteArrayInputStream(soapMsg.getBytes());) {
			// Obtenemos las propiedades de comunicación con @Firma
			String secureMode = Boolean.valueOf(StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
			String ip = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_IP);
			String port = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_PORT);
			String servicePath = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_SERVICE_PATH);
			String wsdlServiceName = service.getWsdl();
			String base = secureMode + GeneralConstants.COLON + GeneralConstants.DOUBLE_PATH_SEPARATOR + ip + GeneralConstants.COLON + port;

			// Creamos el Mime SoapAction necesario para enviar la petición
			MimeHeaders mhs = new MimeHeaders();
			// Generamos el SOAPAction para el servicio concreto.
			
			mhs.addHeader("SOAPAction", wsdlServiceName);
						
			// Creamos el mensaje SOAP
			SOAPMessage message = MessageFactory.newInstance().createMessage(mhs, is);
			// Creamos la URL de punto de acceso a donde se va a atacar.
			//URL endpoint = new URL(url);
			// Creamos el objeto de conexión y realizamos la llamada
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();
			
			// Establecemos el timeout de la conexión y de la lectura
			
			URL endpoint = new URL(new URL(base), servicePath + wsdlServiceName, new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL target = new URL(url.toString());
					URLConnection connection = target.openConnection();
					// Connection settings
					connection.setConnectTimeout(Integer.parseInt(service.getTimeout()));
					connection.setReadTimeout(Integer.parseInt(service.getTimeout()));
					return (connection);
				}
			});
												
			LocalTime beforeCall = LocalTime.now();
			
			connection.call(message, endpoint);
			
			LocalTime afterCall = LocalTime.now();
			
			tiempoTotal = afterCall.getLong(ChronoField.MILLI_OF_DAY) - beforeCall.getLong(ChronoField.MILLI_OF_DAY);
												
		} catch (IOException | SOAPException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_SOAP_PETITION), e);

		} catch (IllegalArgumentException | SecurityException e) {
			LOGGER.error(e.getMessage());
		} 
		
		return tiempoTotal;
	}

}
