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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.UserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 6 mar. 2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.UserMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.UserDatatableRepository;
import es.gob.monitoriza.service.IUserMonitorizaService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 6 mar. 2018.
 */
@Service
public class UserMonitorizaService implements IUserMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private UserMonitorizaRepository repository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private UserDatatableRepository dtRepository; 

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getUserMonitorizaById(java.lang.Long)
	 */
	@Override
	public UserMonitoriza getUserMonitorizaById(Long userId) {
		return repository.findByIdUserMonitoriza(userId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#addUserMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza)
	 */
	@Override
	public UserMonitoriza addUserMonitoriza(UserMonitoriza user) {
		return repository.save(user);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#updateUserMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza)
	 */
	@Override
	public void updateUserMonitoriza(UserMonitoriza user) {
		repository.save(user);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#deleteUserMonitoriza(java.lang.Long)
	 */
	@Override
	public void deleteUserMonitoriza(Long userId) {
		repository.deleteById(userId);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getAllUserMonitoriza()
	 */
	@Override
	public Iterable<UserMonitoriza> getAllUserMonitoriza() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getUserMonitorizaByLogin(java.lang.String)
	 */
	@Override
	public UserMonitoriza getUserMonitorizaByLogin(String login) {
		return repository.findByLogin(login);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#getAllSorted(org.springframework.data.domain.Sort)
	 */
	@Override
	public Iterable<UserMonitoriza> getAllSorted(Sort sort) {
		
		return repository.findAll(sort);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IUserMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<UserMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}
		
}
