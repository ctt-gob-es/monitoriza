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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.SystemCertificateDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.CPlatformTypeSpecification;
import es.gob.monitoriza.persistence.configuration.model.specification.KeystoreSpecification;
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
	public SystemCertificate getSystemCertificateById(Long certId) {
		
		return repository.findByIdSystemCertificate(certId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getSystemCertificateByAlias(java.lang.String)
	 */
	@Override
	public SystemCertificate getSystemCertificateByAlias(String alias) {
		
		return repository.findByAlias(alias);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#saveSystemCertificate(es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public SystemCertificate saveSystemCertificate(SystemCertificate cert) {
		
		return repository.save(cert);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#deleteSystemCertificate(java.lang.Long)
	 */
	@Override
	public void deleteSystemCertificate(Long certId) {

		repository.deleteById(certId);

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
	public DataTablesOutput<SystemCertificate> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#findAllSsl(org.springframework.data.jpa.datatables.mapping.DataTablesInput, es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate)
	 */
	@Override
	public DataTablesOutput<SystemCertificate> findAllSsl(DataTablesInput input) {
				
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
	public DataTablesOutput<SystemCertificate> findAllAuth(DataTablesInput input) {
				
		Keystore keystoreAuth = new Keystore();
		keystoreAuth.setIdKeystore(Keystore.ID_AUTHCLIENT_RFC3161);
		KeystoreSpecification byKeystoreAuth = new KeystoreSpecification(keystoreAuth);
		
		return dtRepository.findAll(input, byKeystoreAuth);
		
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

}
