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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;

/** 
 * <p>Interface that provides CRUD functionality for the SystemCertificate entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1 28/10/2018.
 */
public interface SystemCertificateRepository extends JpaRepository<SystemCertificate, Long>, JpaSpecificationExecutor<SystemCertificate> {
	
	/**
	  * Method that obtains a system certificate by its primary key. 
	 * @param id String that represents the primary key of the @firma platform in the persistence.
	 * @return Object that represents a system certificate from the persistence. 
	 */
	SystemCertificate findByIdSystemCertificate(Long id);
	
	/**
	  * Method that obtains a system certificate by its alias. 
	 * @param alias String that represents the certificate alias.
	 * @return Object that represents a system certificate from the persistence. 
	 */
	SystemCertificate findByAlias(String alias);
	
	/**
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore certificate.
	 * @param issuer String that represents the issuer certificate.
	 * @param serialNumber BigInteger that represents the serial number certificate.
	 * @return Object that represents a system certificate from the persistence. 
	 */
	SystemCertificate findByKeystoreAndIssuerAndSerialNumber(Keystore keystore, String issuer, BigInteger serialNumber);
	
	/**
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore certificate.
	 * @param issuer String that represents the issuer certificate.
	 * @param serialNumber BigInteger that represents the serial number certificate.
	 * @param userMonitoriza UserMonitoriza that represents the user certificate.
	 * @return Object that represents a system certificate from the persistence.
	 */
	SystemCertificate findByKeystoreAndIssuerAndSerialNumberAndUserMonitoriza(Keystore keystore, String issuer, BigInteger serialNumber, UserMonitoriza userMonitoriza);
	
	/**
	 * Method that delete all certificates of a user.
	 * @param userMonitoriza UserMonitoriza entity
	 */
	void deleteByUserMonitoriza(UserMonitoriza userMonitoriza);

}
