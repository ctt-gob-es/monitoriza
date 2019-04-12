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
 * @version 1.5, 26/03/2019.
 */
package es.gob.monitoriza.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

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
import es.gob.monitoriza.persistence.configuration.model.repository.StatusCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.utilidades.StatusCertificateEnum;
import es.gob.monitoriza.utilidades.UtilsCertificate;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for Keystore.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 26/03/2019.
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
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
	private StatusCertificateRepository statusCertRepository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
	private TimerScheduledRepository scheduledRepository;

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

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IKeystoreService#saveSsl()
	 */
	@Override
	@Transactional
	public List<SystemCertificate> saveSsl(final String alias, final byte[] certBytes) throws Exception {
		
		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(repository.findByIdKeystore(KeystoreMonitoriza.ID_TRUSTSTORE_SSL));

		X509Certificate cert = UtilsCertificate.getCertificate(certBytes);

		String issuer = UtilsCertificate.getCertificateIssuerId(cert);
		String subject = UtilsCertificate.getCertificateId(cert);
		BigInteger serialNumber = UtilsCertificate.getCertificateSerialNumber(cert);

		KeystoreMonitoriza ko = null;

		// Alta de certificado

		// Valida el certificado y lo añade al almacén truststore
		// ssl del sistema

		ko = keyStoreFacade.storeCertificate(alias, cert, null);
		// Modificamos el keystore correspondiente, añadiendo el
		// certificado
		if (syscertRepository.findByKeystoreAndIssuerAndSerialNumber(ko, issuer, serialNumber) != null) {
			LOGGER.error(Language.getFormatResWebMonitoriza(ICoreLogMessages.ERRORCORE016, new Object[] {alias} ));
			throw new Exception(GeneralConstants.CERTIFICATE_STORED);
		}

		repository.save(ko);

		SystemCertificate sysCert = new SystemCertificate();

		sysCert.setAlias(alias);
		sysCert.setIssuer(issuer);
		sysCert.setSubject(subject);
		sysCert.setKeystore(ko);
		sysCert.setKey(true);
		sysCert.setSerialNumber(serialNumber);
		sysCert.setStatusCertificate(statusCertRepository.findByIdStatusCertificate(StatusCertificateEnum.VALID.getId()));

		// Añade el certificado a la persistencia
		syscertRepository.save(sysCert);
		listSystemCertificate.add(sysCert);

		// Importación correcta
		LOGGER.info(Language.getFormatResCoreMonitoriza(ICoreLogMessages.CORE011, new Object[ ] { alias }));
		
		// Al haber cambios en el almacén Truststore SSL, se procede a marcar todos los timers programados como elegibles para ser reprogramados
		scheduledRepository.setAllNotUpdated();
		
		return listSystemCertificate;
		
	}
	
}
