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
 * <b>File:</b><p>es.gob.monitoriza.service.IKeystoreService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 15/02/2019.
 */
package es.gob.monitoriza.service;

import java.security.KeyStore;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 15/02/2019.
 */
public interface IKeystoreService {
	
	/**
	 * Method that obtains a keystore by its identifier.
	 * @param keystoreId The keystore identifier.
	 * @return {@link KeystoreMonitoriza}
	 */
	KeystoreMonitoriza getKeystoreById(Long keystoreId);
	
	/**
	 * Method that obtains an user by its login.
	 * @param name The keystore name.
	 * @return {@link KeystoreMonitoriza}
	 */
	KeystoreMonitoriza getKeystoreByName(String name);
		
	/**
	 * Method that gets all the users from the persistence.
	 * @return a {@link Iterable<Keystore>} with the information of all users.
	 */
	Iterable<KeystoreMonitoriza> getAllKeystore();
	
	/**
	 * Deletes a certificate from persistence.
	 * @param idSystemCertificate System Certificate identifier
	 * @param idKeystore Keystore identifier
	 * @throws CryptographyException Error accessing the keystore
	 * @throws DataIntegrityViolationException Error violating constraint in persistence
	 */
	void delete(Long idSystemCertificate, Long idKeystore) throws CryptographyException, DataIntegrityViolationException;
	
	/**
	 * Saves the information of a keystore in persistence.
	 * @param keystore Keystore to save
	 * @return Keystore saved
	 */
	KeystoreMonitoriza saveKeystore(KeystoreMonitoriza keystore);
	
	/**
	 * Method that retrieves the keystore for valid service key pairs from database.
	 * @return Keystore cointaining the key pairs for valid service.
	 */
	KeyStore loadValidServiceKeystore();
	
	/**
	 * Method that retrieves the keystore for SSL certificates from database.
	 * @return Keystore containing the certificates for SSL.
	 */
	KeyStore loadSslTruststore();
	
	/**
	 * Method that retrieves the keystore for RFC3161 authentication key pairs from database.
	 * @return Keystore containing the key pairs for RFC3161 authentication.
	 */
	KeyStore loadRfc3161Keystore();
	
	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param alias String that represents the SSL certificate alias to be stored
	 * @param sysCert byte[] that represents the certificate 
	 * @throws Exception If the method fails
	 * @return List<SystemCertificate>
	 */
	List<SystemCertificate> saveSsl(String alias, byte[] sysCert) throws Exception;
			
}
