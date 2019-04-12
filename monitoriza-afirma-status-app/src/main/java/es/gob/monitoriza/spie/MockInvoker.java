/* 
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
 * <b>File:</b><p>es.gob.monitoriza.spie.MockInvoker.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>18/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 18/10/2018.
 */
package es.gob.monitoriza.spie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.spie.invoker.SpieInvoker;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 18/10/2018.
 */
public class MockInvoker {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		//invokeAndProcess();
		ConfigServiceDTO service = new ConfigServiceDTO(1L, "DSSAfirmaVerify", "timer1", 10000L, "DSSAfirmaVerify", 1000L, "10", "", "@firma", "SOAP", 1L);
		//service.setSoapUrl("https://pre-afirma.redsara.es/afirmaws/services/");
		service.setSoapUrl("http://localhost:8088/afirmaws/services/");
		service.setWsdl("DSSAfirmaVerify");
		File request = new File("C:\\Proyecto\\Monitoriza\\peticionesPrueba\\dssafirmaverify\\grupoPrincipal\\DSSAfirmaVerify_CAdES_FNMT_request_SOAP_local.xml");
		try {
			SoapMockInvoker.sendRequest(request, service, loadSslTruststore());
		} catch (InvokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static void invokeAndProcess() throws InvokerException {
		
		String resultSpie = getRequestFromSpieInker();
		String htmlEmergencyStatus = getRequestFromStatusServlet();
		Document doc = Jsoup.parse(htmlEmergencyStatus);
		Element table = doc.select("table").get(0); //select the first table.
		Elements rows = table.select("tr");

		Element row = rows.get(NumberConstants.NUM1);
		Elements cols = row.select("td");

		String emergencyStatus = cols.get(NumberConstants.NUM1).text();
		
		if ("ON".equals(emergencyStatus)) {
			System.out.println("Semáforo amarillo!!");
		} else {
			System.out.println("Semáforo verde!!!");
		}
		
	}
	
	/**
	 * Method that calls the status servlet.
	 * @return String that represents the status information in JSON format
	 */
	private static String getRequestFromStatusServlet() {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpgetSpieEmergencyMode = new HttpGet("http://localhost:8088/spie/checkStatusInf?opCodes=2");
		String result = null;
				
		//Execute and get the response.
		HttpResponse responseEmergencyModeStatus = null;
		HttpEntity entity = null;
		
		try {
			responseEmergencyModeStatus = httpclient.execute(httpgetSpieEmergencyMode);
			entity = responseEmergencyModeStatus.getEntity();
			
			if (entity != null) {
			    try (InputStream instream = entity.getContent();) {
			    	result = new BufferedReader(new InputStreamReader(instream))
			    			  .lines().collect(Collectors.joining());
			    } 
			}
						
		} catch (IOException e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB016, new Object[]{ httpgetSpieEmergencyMode.getURI() }));
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	private static String getRequestFromSpieInker() throws InvokerException {
		
		return SpieInvoker.sendRequest("http://localhost:8088", "/spie/checkStatusInf?opCodes=2", null);
	}
	
	/**
	 * 
	 * @return
	 */
	private static KeyStore loadSslTruststore() {

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new FileInputStream("C:/servidores/Monitoriza/apache-tomcat-8.5.24-1.0/conf/certificates/truststoreWS.jks");) {
			// Accedemos al almacén de confianza SSL
			
			cer = KeyStore.getInstance("JKS");

			cer.load(readStream, "12345".toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException ex) {
			System.out.println(ex.toString());
		}

		return cer;
	}

}
