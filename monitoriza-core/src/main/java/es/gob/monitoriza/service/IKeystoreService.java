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
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.service;

import org.springframework.dao.DataIntegrityViolationException;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 25/01/2019.
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
		
}
