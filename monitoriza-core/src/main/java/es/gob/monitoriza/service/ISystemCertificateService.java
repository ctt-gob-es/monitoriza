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
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.service;

import java.math.BigInteger;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 25/01/2019.
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
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore.
	 * @param issuer String that represents the certificate issuer.
	 * @param serialNumber BigInteger that represents the certificate serial number
	 * @return Object that represents a system certificate from the persistence. 
	 */
	SystemCertificate getSystemCertificateByKsAndIssAndSn(KeystoreMonitoriza keystore, String issuer, BigInteger serialNumber);
	
	/**
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore certificate.
	 * @param issuer String that represents the issuer certificate.
	 * @param serialNumber BigInteger that represents the serial number certificate.
	 * @param userMonitoriza UserMonitoriza that represents the user certificate.
	 * @return Object that represents a system certificate from the persistence.
	 */
	SystemCertificate getSystemCertificateByKsAndIssAndSnAndUser(KeystoreMonitoriza keystore, String issuer, BigInteger serialNumber, UserMonitoriza userMonitoriza);

	/**
	 * Method that stores a user in the persistence.
	 * @param cert a {@link SystemCertificate} with the information of the user.
	 * @return {@link SystemCertificate} The user.
	 */
	SystemCertificate saveSystemCertificate(SystemCertificate cert);

	/**
	 * Method that deletes a user in the persistence.
	 * @param certId {@link Integer} that represents the certificate identifier to delete.
	 */
	void deleteSystemCertificate(Long certId);
	
	/**
	 * Method that delete all certificates of a user.
	 * @param userMonitoriza
	 */
	void deleteSystemCertificateByUserMonitoriza(UserMonitoriza userMonitoriza);

	/**
	 * Method that gets all the certificates from the persistence.
	 * @return a {@link Iterable<SystemCertificate>} with the information of all certificates.
	 */
	Iterable<SystemCertificate> getAllSystemCertificate();

	/**
	 * Method that gets all the ssl certificates from the persistence.
	 * @return a {@link Iterable<SystemCertificate>} with the information of all certificates.
	 */
	Iterable<SystemCertificate> getAllSsl();

	/**
	 * Method that gets all the RFC3161 authentication certificates from the persistence.
	 * @return a {@link Iterable<SystemCertificate>} with the information of all certificates.
	 */
	Iterable<SystemCertificate> getAllAuth();
	
	/**
	 * Method that gets all the valid service authentication certificates from the persistence.
	 * @return a {@link Iterable<SystemCertificate>} with the information of all certificates.
	 */
	Iterable<SystemCertificate> getAllValidService();

	/**
	 * Method that gets all certificates
	 * @param input Datatable filter parameter
	 * @return DataTablesOutput<SystemCertificate>
	 */
	DataTablesOutput<SystemCertificate> findAll(DataTablesInput input);

	/**
	 * Method that gets all SSL certificates
	 * @param input Datatable filter parameter
	 * @return DataTablesOutput<SystemCertificate>
	 */
	DataTablesOutput<SystemCertificate> findAllSsl(DataTablesInput input);

	/**
	 * Method that gets all RFC3161 authentication certificates
	 * @param input Datatable filter parameter
	 * @return DataTablesOutput<SystemCertificate>
	 */
	DataTablesOutput<SystemCertificate> findAllAuth(DataTablesInput input);
	
	/**
	 * Method that gets all valid service authentication certificates
	 * @param input Datatable filter parameter
	 * @return DataTablesOutput<SystemCertificate>
	 */
	DataTablesOutput<SystemCertificate> findAllValidService(final DataTablesInput input);

	/**
	 * Method that gets all user certificates
	 * @param input Datatable filter parameter
	 * @param id user filter parameter
	 * @return DataTablesOutput<SystemCertificate>
	 */
	DataTablesOutput<SystemCertificate> findCertUserByUser(DataTablesInput input, Long idUserMonitoriza);

	/**
	 * Method that gets all user certificates
	 * @param id user filter parameter
	 * @return Iterable<SystemCertificate>
	 */
	Iterable<SystemCertificate> findCertUserByUser(Long idUserMonitoriza);
}
