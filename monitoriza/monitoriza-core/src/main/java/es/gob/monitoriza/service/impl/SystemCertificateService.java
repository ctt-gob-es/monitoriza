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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.SystemCertificateService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16 may. 2018.
 */
package es.gob.monitoriza.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.SystemCertificateDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.KeystoreSpecification;
import es.gob.monitoriza.persistence.configuration.model.specification.UserMonitorizaSpecification;
import es.gob.monitoriza.service.ISystemCertificateService;

/**
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16 may. 2018.
 */
@Service
public class SystemCertificateService implements ISystemCertificateService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private SystemCertificateRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private SystemCertificateDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getSystemCertificateById(java.lang.Long)
	 */
	@Override
	public SystemCertificate getSystemCertificateById(final Long certId) {

		return repository.findByIdSystemCertificate(certId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getSystemCertificateByAlias(java.lang.String)
	 */
	@Override
	public SystemCertificate getSystemCertificateByAlias(final String alias) {

		return repository.findByAlias(alias);
	}
	
	/**
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore certificate.
	 * @param issuer String that represents the issuer certificate.
	 * @param serialNumber BigInteger that represents the serial number certificate.
	 * @return Object that represents a system certificate from the persistence. 
	 */
	@Override
	public SystemCertificate getSystemCertificateByKsAndIssAndSn(Keystore keystore, String issuer, BigInteger serialNumber) {
		
		return repository.findByKeystoreAndIssuerAndSerialNumber(keystore, issuer, serialNumber);
		
	}

	/**
	  * Method that obtains a system certificate by its keystore, its issue and its serialNumber.
	 * @param keystore Keystore that represents the keystore certificate.
	 * @param issuer String that represents the issuer certificate.
	 * @param serialNumber BigInteger that represents the serial number certificate.
	 * @param userMonitoriza UserMonitoriza that represents the user certificate.
	 * @return Object that represents a system certificate from the persistence.
	 */
	@Override
	public SystemCertificate getSystemCertificateByKsAndIssAndSnAndUser(Keystore keystore, String issuer, BigInteger serialNumber, UserMonitoriza userMonitoriza) {
		
		return repository.findByKeystoreAndIssuerAndSerialNumberAndUserMonitoriza(keystore, issuer, serialNumber, userMonitoriza);
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#saveSystemCertificate(es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public SystemCertificate saveSystemCertificate(final SystemCertificate cert) {

		return repository.save(cert);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#deleteSystemCertificate(java.lang.Long)
	 */
	@Override
	public void deleteSystemCertificate(final Long certId) {

		repository.deleteById(certId);

	}
	
	/**
	 * Method that delete all certificates of a user.
	 * @param userMonitoriza
	 */
	public void deleteSystemCertificateByUserMonitoriza(UserMonitoriza userMonitoriza) {
		
		repository.deleteByUserMonitoriza(userMonitoriza);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getAllSystemCertificate()
	 */
	@Override
	public Iterable<SystemCertificate> getAllSystemCertificate() {

		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findAll(final DataTablesInput input) {

		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findAllSsl(org.springframework.data.jpa.datatables.mapping.DataTablesInput, es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findAllSsl(final DataTablesInput input) {

		Keystore keystoreSsl = new Keystore();
		keystoreSsl.setIdKeystore(Keystore.ID_TRUSTSTORE_SSL);
		KeystoreSpecification byKeystoreSsl = new KeystoreSpecification(keystoreSsl);

		return dtRepository.findAll(input, byKeystoreSsl);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findAllAuth(org.springframework.data.jpa.datatables.mapping.DataTablesInput, es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findAllAuth(final DataTablesInput input) {

		Keystore keystoreAuth = new Keystore();
		keystoreAuth.setIdKeystore(Keystore.ID_AUTHCLIENT_RFC3161);
		KeystoreSpecification byKeystoreAuth = new KeystoreSpecification(keystoreAuth);

		return dtRepository.findAll(input, byKeystoreAuth);

	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findAllAuth(org.springframework.data.jpa.datatables.mapping.DataTablesInput, es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findAllValidService(final DataTablesInput input) {

		Keystore keystoreAuth = new Keystore();
		keystoreAuth.setIdKeystore(Keystore.ID_VALID_SERVICE_STORE);
		KeystoreSpecification byKeystoreAuth = new KeystoreSpecification(keystoreAuth);

		return dtRepository.findAll(input, byKeystoreAuth);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findCertUserByUser(org.springframework.data.jpa.datatables.mapping.DataTablesInput, java.lang.Long)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findCertUserByUser(final DataTablesInput input, final Long idUserMonitoriza) {

		UserMonitoriza userMonitoriza = new UserMonitoriza();
		userMonitoriza.setIdUserMonitoriza(idUserMonitoriza);
		UserMonitorizaSpecification userMonSpec = new UserMonitorizaSpecification(userMonitoriza);

		return dtRepository.findAll(input, userMonSpec);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findCertUserByUser(java.lang.Long)
	 */
	@Override
	public Iterable<SystemCertificate> findCertUserByUser(final Long idUserMonitoriza) {

		UserMonitoriza userMonitoriza = new UserMonitoriza();
		userMonitoriza.setIdUserMonitoriza(idUserMonitoriza);
		UserMonitorizaSpecification userMonSpec = new UserMonitorizaSpecification(userMonitoriza);

		return repository.findAll(userMonSpec);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getAllSsl()
	 */
	@Override
	public Iterable<SystemCertificate> getAllSsl() {

		Keystore keystoreSsl = new Keystore();
		keystoreSsl.setIdKeystore(Keystore.ID_TRUSTSTORE_SSL);
		KeystoreSpecification byKeystoreSsl = new KeystoreSpecification(keystoreSsl);

		return repository.findAll(byKeystoreSsl);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getAllAuth()
	 */
	@Override
	public Iterable<SystemCertificate> getAllAuth() {

		Keystore keystoreAuth = new Keystore();
		keystoreAuth.setIdKeystore(Keystore.ID_AUTHCLIENT_RFC3161);
		KeystoreSpecification byKeystoreAuth = new KeystoreSpecification(keystoreAuth);

		return repository.findAll(byKeystoreAuth);
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getAllValidService()
	 */
	@Override
	public Iterable<SystemCertificate> getAllValidService() {

		Keystore keystoreAuth = new Keystore();
		keystoreAuth.setIdKeystore(Keystore.ID_VALID_SERVICE_STORE);
		KeystoreSpecification byKeystoreAuth = new KeystoreSpecification(keystoreAuth);

		return repository.findAll(byKeystoreAuth);
	}

	/**
	 * Get repository.
	 * @return repository
	 */
	public SystemCertificateRepository getRepository() {
		return repository;
	}

	/**
	 * Set repository.
	 * @param repositoryP set repository
	 */
	public void setRepository(final SystemCertificateRepository repositoryP) {
		this.repository = repositoryP;
	}

	/**
	 * Get dtRepository.
	 * @return dtRepository
	 */
	public SystemCertificateDatatableRepository getDtRepository() {
		return dtRepository;
	}

	/**
	 * Set dtRepository.
	 * @param dtRepositoryP set dtRepository
	 */
	public void setDtRepository(final SystemCertificateDatatableRepository dtRepositoryP) {
		this.dtRepository = dtRepositoryP;
	}

}
