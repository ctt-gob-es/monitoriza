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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ServiceScheduledService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ServiceScheduled.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/09/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.SpieScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.SpieScheduledRepository;
import es.gob.monitoriza.service.ISpieScheduledService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for SpieScheduled.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 12/09/2018.
 */
@Service
public class SpieScheduledService implements ISpieScheduledService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private SpieScheduledRepository repository; 
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#getSpieScheduledById(java.lang.Long)
	 */
	@Override
	public SpieScheduled getSpieScheduledById(Long timerId) {
		
		return repository.findByIdSpieScheduled(timerId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#saveSpieScheduled(es.gob.monitoriza.persistence.configuration.model.entity.SpieScheduled)
	 */
	@Override
	public SpieScheduled saveSpieScheduled(SpieScheduled timer) {
		
		return repository.save(timer);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#deleteSpieScheduled(java.lang.Long)
	 */
	@Override
	public void deleteSpieScheduled(Long spieScheduledId) {
		repository.deleteById(spieScheduledId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#getAllSpieScheduled()
	 */
	@Override
	public Iterable<SpieScheduled> getAllSpieScheduled() {
		
		return repository.findAll();
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#getAllSpieScheduledById(java.lang.Iterable)
	 */
	@Override
	public Iterable<SpieScheduled> getAllSpieScheduledById(Iterable<Long> idTimers) {
		return repository.findAllById(idTimers);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#emptyTimersScheduled()
	 */
	@Override
	public void emptyTimersScheduled() {
		repository.deleteAll();		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISpieScheduledService#getSpieScheduledByPlatformType(java.lang.Long)
	 */
	@Override
	public SpieScheduled getSpieScheduledByPlatformType(Long idPlatformType) {
		
		return repository.findByPlatformTypeIdPlatformType(idPlatformType);
	}

	

}
