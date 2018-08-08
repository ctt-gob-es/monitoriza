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
 * <b>File:</b><p>es.gob.monitoriza.webservice.DSSCertificateService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 ago. 2018.
 */
package es.gob.monitoriza.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/** 
 * <p>Class DSSCertificate.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 2 ago. 2018.
 */
@WebServiceClient(name = "DSSCertificateService", wsdlLocation = "[PROTOCOL]://[SERVER]:[PORT]/afirmaws/services/DSSAfirmaVerifyCertificate?wsdl", targetNamespace = "urn:oasis:names:tc:dss:1.0:core:schema")
public class DSSCertificateService extends Service {

	/**
	 * Attribute that represents the wsdl location. 
	 */
	public final static URL WSDL_LOCATION;

	/**
	 * Attribute that represents the service name. 
	 */
	public final static QName SERVICE = new QName("urn:oasis:names:tc:dss:1.0:core:schema", "DSSCertificateService");

	/**
	 * Attribute that represents the afirma verify certificate name. 
	 */
	public final static QName DSSAfirmaVerifyCertificate = new QName("urn:oasis:names:tc:dss:1.0:core:schema", "DSSAfirmaVerifyCertificate");

	static {
		URL url = null;
		try {
			url = new URL("[PROTOCOL]://[SERVER]:[PORT]/afirmaws/services/DSSAfirmaVerifyCertificate?wsdl");
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(DSSCertificateService.class.getName()).log(java.util.logging.Level.INFO, "Can not initialize the default wsdl from {0}", "[PROTOCOL]://[SERVER]:[PORT]/afirmaws/services/DSSAfirmaVerifyCertificate?wsdl");
		}
		WSDL_LOCATION = url;
	}

	/**
	 * Constructor method for the class DSSCertificateService.java.
	 * @param wsdlLocation URL
	 */
	public DSSCertificateService(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	/**
	 * Constructor method for the class DSSCertificateService.java.
	 * @param wsdlLocation URL
	 * @param serviceName service name
	 */
	public DSSCertificateService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	/**
	 * Constructor method for the class DSSCertificateService.java. 
	 */
	public DSSCertificateService() {
		super(WSDL_LOCATION, SERVICE);
	}

	/**
	 * Constructor method for the class DSSCertificateService.java.
	 * @param features web service features
	 */
	public DSSCertificateService(WebServiceFeature... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	/**
	 * Constructor method for the class DSSCertificateService.java.
	 * @param wsdlLocation URL wsdl location
	 * @param features web service features
	 */
	public DSSCertificateService(URL wsdlLocation, WebServiceFeature... features) {
		super(wsdlLocation, SERVICE, features);
	}

	/**
	 * Constructor method for the class DSSCertificateService.java.
	 * @param wsdlLocation URL wsdl location
	 * @param serviceName service name
	 * @param features web service features
	 */
	public DSSCertificateService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	/**
	 * Method that creates a service DSSCertificate.
	 * @return service DSSCertificate
	 */
	@WebEndpoint(name = "DSSAfirmaVerifyCertificate")
	public DSSCertificate getDSSAfirmaVerifyCertificate() {
		return super.getPort(DSSAfirmaVerifyCertificate, DSSCertificate.class);
	}

	/**
	 * Method that creates a service DSSCertificate.
	 * @param features web service features
	 * @return ervice DSSCertificate
	 */
	@WebEndpoint(name = "DSSAfirmaVerifyCertificate")
	public DSSCertificate getDSSAfirmaVerifyCertificate(WebServiceFeature... features) {
		return super.getPort(DSSAfirmaVerifyCertificate, DSSCertificate.class, features);
	}

}
