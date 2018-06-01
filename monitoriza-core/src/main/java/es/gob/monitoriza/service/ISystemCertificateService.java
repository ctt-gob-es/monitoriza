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
 * <b>File:</b><p>es.gob.monitoriza.service.ISystemCertificateService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16 may. 2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16 may. 2018.
 */
public interface ISystemCertificateService {

	/**
	 * Method that obtains a certificate by its identifier.
	 * @param certId The certificate identifier.
	 * @return {@link SystemCertificate}
	 */
	SystemCertificate getSystemCertificateById(Long certId);
	
	/**
	 * Method that obtains a certificate by its alias.
	 * @param alias The certificate alias.
	 * @return {@link SystemCertificate}
	 */
	SystemCertificate getSystemCertificateByAlias(String alias);
	
	/**
	 * Method that stores a user in the persistence.
	 * @param user a {@link SystemCertificate} with the information of the user.
	 * @return {@link SystemCertificate} The user. 
	 */
	SystemCertificate saveSystemCertificate(SystemCertificate cert);
			
	/**
	 * Method that deletes a user in the persistence.
	 * @param certId {@link Integer} that represents the certificate identifier to delete.
	 */
	void deleteSystemCertificate(Long certId);
	
	/**
	 * Method that gets all the users from the persistence.
	 * @return a {@link Iterable<SystemCertificate>} with the information of all certificates.
	 */
	Iterable<SystemCertificate> getAllSystemCertificate();
		
	/**
	 * 
	 * @param input
	 * @return
	 */
	DataTablesOutput<SystemCertificate> findAll(DataTablesInput input);
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	DataTablesOutput<SystemCertificate> findAllSsl(DataTablesInput input);
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	DataTablesOutput<SystemCertificate> findAllAuth(DataTablesInput input);
}
