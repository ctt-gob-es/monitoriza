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
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.repository.KeystoreRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.service.IKeystoreService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for Keystore.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 30/01/2019.
 */
@Service("keystoreService")
public class KeystoreService implements IKeystoreService {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
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
	public KeystoreMonitoriza getKeystoreById(Long keystoreId) {
		return repository.findByIdKeystore(keystoreId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#getKeystoreByName(java.lang.String)
	 */
	@Override
	public KeystoreMonitoriza getKeystoreByName(String name) {
		return repository.findByName(name);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#getAllKeystore()
	 */
	@Override
	public Iterable<KeystoreMonitoriza> getAllKeystore() {
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
		
		KeystoreMonitoriza ko = keyStoreFacade.deleteCertificate(cert.getAlias());
		
		ko.setVersion(ko.getVersion() + 1L);
				
		repository.save(ko);
		repository.flush();
		
		syscertRepository.delete(cert);
		syscertRepository.flush();
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#saveKeystore(es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza)
	 */
	@Override
	public KeystoreMonitoriza saveKeystore(KeystoreMonitoriza keystore) {
		
		return repository.save(keystore);
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#loadSslTruststore()
	 */
	public KeyStore loadSslTruststore() {
		
		final KeystoreMonitoriza ks = getKeystoreByName(GeneralConstants.SSL_TRUST_STORE_NAME);
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE005);
			cer = KeyStore.getInstance(ks.getKeystoreType());
			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#loadRfc3161Keystore()
	 */
	public KeyStore loadRfc3161Keystore() {
		
		final KeystoreMonitoriza ks = getKeystoreByName(GeneralConstants.RFC3161_KEYSTORE_NAME);
		
		final IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén RFC3161
			msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE006);
			cer = KeyStore.getInstance(ks.getKeystoreType());

			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#loadValidServiceKeystore()
	 */
	public KeyStore loadValidServiceKeystore() {

		final KeystoreMonitoriza ks = getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);

		final IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			msgError = Language.getResCoreMonitoriza(IStatusLogMessages.STATUS012);
			cer = KeyStore.getInstance(ks.getKeystoreType());
			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
}
