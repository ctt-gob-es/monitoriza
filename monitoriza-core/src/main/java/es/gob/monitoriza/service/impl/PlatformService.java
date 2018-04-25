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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.PlatformAfirmaService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for PlatformAfirma.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 10 abr. 2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.CPlatformTypeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.PlatformRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.PlatformDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.PlatformSpecification;
import es.gob.monitoriza.persistence.configuration.model.specification.SearchCriteria;
import es.gob.monitoriza.service.IPlatformService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for PlatformAfirma.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10 abr. 2018.
 */
@Service
public class PlatformService implements IPlatformService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private PlatformRepository repository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private CPlatformTypeRepository typeRepository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private PlatformDatatableRepository dtRepository; 

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getPlatformById(java.lang.Long)
	 */
	@Override
	public PlatformMonitoriza getPlatformById(Long afirmaId) {
		return repository.findByIdPlatform(afirmaId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#savePlatform(es.gob.monitoriza.persistence.configuration.model.entity.PlatformAfirma)
	 */
	@Override
	public PlatformMonitoriza savePlatform(PlatformMonitoriza afirma) {
		return repository.save(afirma);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#deleteUserMonitoriza(java.lang.Long)
	 */
	@Override
	public void deletePlatform(Long afirmaId) {
		repository.deleteById(afirmaId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getAllUserMonitoriza()
	 */
	@Override
	public Iterable<PlatformMonitoriza> getAllPlatform() {
		
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#getPlatformTypeById(java.lang.Long)
	 */
	@Override
	public CPlatformType getPlatformTypeById(Long typeId) {
		
		return typeRepository.findByIdPlatformType(typeId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IPlatformService#findAllBySpec(org.springframework.data.jpa.datatables.mapping.DataTablesInput, es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza)
	 */
	@Override
	public DataTablesOutput<PlatformMonitoriza> findAllAfirma(DataTablesInput input) {
				
		CPlatformType platformType = new CPlatformType();
		platformType.setIdPlatformType(ID_PLATFORM_TYPE_AFIRMA);
		PlatformSpecification spec = 
			      new PlatformSpecification(new SearchCriteria("platformType", "=", platformType));
		
		return dtRepository.findAll(input, spec);
	}

}
