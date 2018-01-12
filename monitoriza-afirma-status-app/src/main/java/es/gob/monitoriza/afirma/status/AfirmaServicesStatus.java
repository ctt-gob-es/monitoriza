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
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.afirma.status.AfirmaServicesStatus.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that gets the average response times for @firma services.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 21/12/2017.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 21 dic. 2017.
 */
package es.gob.monitoriza.afirma.status;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import es.gob.monitoriza.afirma.constant.AfirmaServicesNames;
import es.gob.monitoriza.afirma.constant.AfirmaWsdlServicesName;
import es.gob.monitoriza.afirma.constant.GeneralConstants;
import es.gob.monitoriza.afirma.constant.LogMessagesConstants;
import es.gob.monitoriza.afirma.exception.InvokerException;
import es.gob.monitoriza.afirma.i18.Language;
import es.gob.monitoriza.afirma.util.FileUtils;
import es.gob.monitoriza.afirma.util.RequestUtils;
import es.gob.monitoriza.afirma.util.StaticMonitorizaProperties;


/** 
 * <p>Class that gets the average response times for @firma services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 21 dic. 2017.
 */
public class AfirmaServicesStatus extends HttpServlet {

	/**
	 * Attribute that represents the generated version id. 
	 */
	private static final long serialVersionUID = -6583932223401086851L;
		
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents . 
	 */
	public static final String MONITORIZA01 = "MONITORIZA01";
	
	/**
	 * Attribute that represents the path where the pairs are stored.
	 */
	private static String requestDirectory = StaticMonitorizaProperties.getProperty(GeneralConstants.ROOT_PATH_DIRECTORY);
		
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "");
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error(Language.getFormatResMonitoriza("", new Object[ ] { request.getRemoteAddr() }), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.info(Language.getFormatResMonitoriza(LogMessagesConstants.INIT_SERVICE, new Object[ ] { }));
		LOGGER.info(Language.getFormatResMonitoriza(LogMessagesConstants.PATH_DIRECTORY_REQUESTS, new Object[ ] { requestDirectory }));
		
		try {
			startInvoker(requestDirectory);
		} catch (InvokerException e) {
			LOGGER.error(e.getMessage());
		}        
	
	}
	
	/**
	 * Method that realizes the invocation of service by service name.
	 * 
	 * @param rootPath initial path where is store every petition.
	 * @param responsesDir Path where the responses will be stored.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	private static void startInvoker(String rootPath) throws InvokerException {
		File file = new File(requestDirectory);
		// Comprobamos que la ruta proporcioanda es correcta.
		if (!file.isDirectory()) {
			throw new InvokerException(Language.getFormatResMonitoriza(LogMessagesConstants.ERROR_BAD_DIRECTORIES_STRUCTURE, new Object[ ] { file.getAbsolutePath() }));
		}
		// Recorremos los subdirectorios que separan las peticiones por
		// servicio.
		for (File f: file.listFiles()) {
			// Si el subdirectorio no es una carpeta, lanzamos un error ya que
			// la ruta proporcionada no es correcta.
			if (!f.isDirectory()) {
				throw new InvokerException(Language.getFormatResMonitoriza(LogMessagesConstants.ERROR_BAD_DIRECTORIES_STRUCTURE, new Object[ ] { file.getAbsolutePath() }));
			}
			sendSetRequests(f);
		}
	}
	
	/**
	 * Method that search every request in the given directory, send it and store the response.
	 * 
	 * @param file Directory where search requests.
	 * @param responsesDir Directory where store the responses.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	private static void sendSetRequests(File file) throws InvokerException {
		LOGGER.info(Language.getFormatResMonitoriza(LogMessagesConstants.STARTING_TO_SEND_STORED_REQUESTS, new Object[ ] { file.toPath().toString()}));
		// Comprobamos que la estructura de directorios sea correcta.
		if (!file.isDirectory()) {
			throw new InvokerException(Language.getFormatResMonitoriza(LogMessagesConstants.ERROR_BAD_DIRECTORIES_STRUCTURE, new Object[ ] { file.getAbsolutePath() }));
		}
		// Obtenemos el servicio al que vamos a invocar.
		String serviceName;
		try {
			serviceName = AfirmaServicesNames.class.getField(file.getName()).get(null).toString();
		} catch (IllegalArgumentException | SecurityException
				| NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalArgumentException(Language.getFormatResMonitoriza(LogMessagesConstants.ERROR_INVALID_SERVICE_NAME, new Object[ ] { requestDirectory }));
		}
		// Recorremos los subdirectorios de la ruta indicada.
		for (File f: file.listFiles()) {
			// Comprobamos que la estructura de directorios sea correcta.
			if (!f.isDirectory()) {
				throw new InvokerException(Language.getFormatResMonitoriza(LogMessagesConstants.ERROR_BAD_DIRECTORIES_STRUCTURE, new Object[ ] { file.getAbsolutePath() }));
			}
			// Vamos recorriendo los ficheros, y si es una petición, la enviamos
			// a @Firma y almacenamos la respuesta
			for (File request: f.listFiles()) {
				
					String id = RequestUtils.getTransacctionIdGivenFileName(request.getName());
					LOGGER.info(Language.getFormatResMonitoriza(LogMessagesConstants.SENDING_REQUEST, new Object[]{id}));
					String response = sendRequest(request, serviceName);
			
			}
		}

	}
	
	/**
	 * Method that sends a request and get the response message.
	 * 
	 * @param petition request file which contents the SOAP message.
	 * @param serviceName name of the service to invoke. It's should match with the real name of the service since it will be inserted in the URL of invocation.
	 * @return the response received from @Firma as a String.
	 */
	private static String sendRequest(File petition, String serviceName) {
		// Obtenemos el contenido del fichero
		String soapMsg = FileUtils.readFile(petition);
		String response = null;

		try {
			// Obtenemos las propiedades de comunicación con @Firma
			String secureMode = Boolean.valueOf(StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
			String ip = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_IP);
			String port = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_PORT);
			String servicePath = StaticMonitorizaProperties.getProperty(GeneralConstants.AFIRMA_CONNECTION_SERVICE_PATH);
			String wsdlServiceName = AfirmaWsdlServicesName.class.getField(serviceName).get(null).toString();
			String url = secureMode + GeneralConstants.COLON + GeneralConstants.DOUBLE_PATH_SEPARATOR + ip + GeneralConstants.COLON + port + servicePath + wsdlServiceName;

			// Creamos el Mime SoapAction necesario para enviar la petición
			MimeHeaders mhs = new MimeHeaders();
			// Generamos el SOAPAction para el servicio concreto.
			
			mhs.addHeader("SOAPAction", wsdlServiceName);
			// Pasamos a InputStream la petición SOAP
			InputStream is = new ByteArrayInputStream(soapMsg.getBytes());
			// Creamos el mensaje SOAP
			SOAPMessage message = MessageFactory.newInstance().createMessage(mhs, is);
			// Creamos la URL de punto de acceso a donde se va a atacar.
			java.net.URL endpoint = new URL(url);
			// Creamos el objeto de conexión y realizamos la llamada
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = connection.call(message, endpoint);
			// Guardamos la respuesta recibida.
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			response = new String(out.toByteArray());
		} catch (IOException | SOAPException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessagesConstants.ERROR_SENDING_SOAP_PETITION), e);
			response = null;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			LOGGER.error(e.getMessage());
		} 
		return response;
	}

}
