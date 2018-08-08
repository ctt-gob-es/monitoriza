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
 * <b>File:</b><p>es.gob.monitoriza.webservice.ClientManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 ago. 2018.
 */
package es.gob.monitoriza.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class ClientManager.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 1 ago. 2018.
 */
public class ClientManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Method that creates the service DSSCertificate.
	 * @param url URI
	 * @return service DSSCertificate
	 * @throws MalformedURLException exception
	 */
	public DSSCertificate getDSSCertificateServiceClient(String url) throws MalformedURLException {
		LOGGER.info("getDSSCertificateServiceClient init");
		URL certificateServiceURL = new URL(url);
		DSSCertificateService certificateService = new DSSCertificateService(certificateServiceURL);
		DSSCertificate client = certificateService.getDSSAfirmaVerifyCertificate();
		LOGGER.info("getDSSCertificateServiceClient end");
		return client;
	}
}
