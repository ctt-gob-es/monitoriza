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
 * <b>File:</b><p>es.gob.monitoriza.webservice.DSSCertificate.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 ago. 2018.
 */
package es.gob.monitoriza.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/** 
 * <p>Class DSSCertificate.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 2 ago. 2018.
 */
@WebService(targetNamespace = "urn:oasis:names:tc:dss:1.0:core:schema", name = "DSSCertificate")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DSSCertificate {

	/**
	 * Method that verify a certificate.
	 * @param dssXML request
	 * @return response
	 */
	@WebMethod
	@WebResult(name = "verifyReturn", targetNamespace = "urn:oasis:names:tc:dss:1.0:core:schema", partName = "verifyReturn")
	public String verify(@WebParam(partName = "dssXML", name = "dssXML") String dssXML);
}
