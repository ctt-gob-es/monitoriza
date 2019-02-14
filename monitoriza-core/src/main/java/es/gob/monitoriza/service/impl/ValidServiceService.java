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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ValidServiceService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.cron.ValidCertificatesJob;
import es.gob.monitoriza.persistence.configuration.dto.ValidServiceDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.persistence.configuration.model.repository.AuthenticationTypeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ValidServiceRepository;
import es.gob.monitoriza.service.IValidServiceService;

/** 
 * <p>Class that implements the communication with the operations of the persistence layer for ValidService.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
@Service("validServiceService")
public class ValidServiceService implements IValidServiceService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private ValidServiceRepository repository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private AuthenticationTypeRepository repositoryAuthType;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private SystemCertificateRepository repositorySysCert;	
		
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ValidCertificatesJob validCertificatesJob;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IValidServiceService#getAllValidServices()
	 */
	@Override
	public List<ValidService> getAllValidServices() {
		return repository.findAll();
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IValidServiceService#getValidServiceById(java.lang.Long)
	 */
	@Override
	public ValidService getValidServiceById(final Long validServiceId) {
		return repository.findByIdValidService(validServiceId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IValidServiceService#saveValidService(es.gob.monitoriza.persistence.configuration.model.entity.ValidService)
	 */
	@Override
	@Transactional
	public ValidService saveValidService(final ValidServiceDTO validServiceDto) {
		
		ValidService validService = null;
		boolean runJob = Boolean.FALSE;
		
		if (validServiceDto.getIdValidService() != null) {
			validService = repository.findByIdValidService(validServiceDto.getIdValidService());
		} else {
			validService = new ValidService();
		}
		validService.setApplication(validServiceDto.getApplication());
		validService.setAuthenticationType(repositoryAuthType.findByIdAuthenticationType(validServiceDto.getAuthenticationType()));
		validService.setHost(validServiceDto.getHost());
		validService.setIsSecure(validServiceDto.getIsSecure());
		
		if (!StringUtils.equals(validService.getCronExpression(), validServiceDto.getCronExpression()) && validServiceDto.getIsEnableValidationJob()) {
			runJob = Boolean.TRUE;
		}
		validService.setIsEnableValidationJob(validServiceDto.getIsEnableValidationJob());
		validService.setCronExpression(validServiceDto.getCronExpression());
		validService.setPass(validServiceDto.getPass());
		validService.setPort(validServiceDto.getPort());
		validService.setUser(validServiceDto.getUser());
		validService.setValidServiceCertificate(repositorySysCert.findByIdSystemCertificate(validServiceDto.getValidServiceCertificate()));
						
		validService = repository.save(validService);
		
		// Si todo va bien, se ejecuta el job
		if (runJob) {
			validCertificatesJob.start();
		}
		
		return validService;

	}
	
}
