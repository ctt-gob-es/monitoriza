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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.KeystoreService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 09/11/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.repository.KeystoreRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.service.IKeystoreService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for Keystore.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 09/11/2018.
 */
@Service
public class KeystoreService implements IKeystoreService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private KeystoreRepository repository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private SystemCertificateRepository syscertRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#getKeystoreById(java.lang.Long)
	 */
	@Override
	public Keystore getKeystoreById(Long keystoreId) {
		return repository.findByIdKeystore(keystoreId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#getKeystoreByName(java.lang.String)
	 */
	@Override
	public Keystore getKeystoreByName(String name) {
		return repository.findByName(name);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#getAllKeystore()
	 */
	@Override
	public Iterable<Keystore> getAllKeystore() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @throws CryptographyException 
	 * @see es.gob.monitoriza.service.IKeystoreService#saveKeystore()
	 */
	@Override
	@Transactional
	public void delete(Long systemCertificateId, Long idKeystore) throws CryptographyException, DataIntegrityViolationException {
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(repository.findByIdKeystore(idKeystore));
		SystemCertificate cert = syscertRepository.findByIdSystemCertificate(systemCertificateId);
		
		Keystore ko = keyStoreFacade.deleteCertificate(cert.getAlias());
		
		ko.setVersion(ko.getVersion() + 1L);
				
		repository.save(ko);
		repository.flush();
		
		syscertRepository.delete(cert);
		syscertRepository.flush();
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#saveKeystore(es.gob.monitoriza.persistence.configuration.model.entity.Keystore)
	 */
	@Override
	public Keystore saveKeystore(Keystore keystore) {
		
		return repository.save(keystore);
	}
	
}
