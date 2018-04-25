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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ServiceMonitorizaService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 abr. 2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.TimerMonitorizaDatatableRepository;
import es.gob.monitoriza.service.ITimerMonitorizaService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 abr. 2018.
 */
@Service
public class TimerMonitorizaService implements ITimerMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerMonitorizaRepository repository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerMonitorizaDatatableRepository dtRepository; 

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#getTimerMonitorizaById(java.lang.Long)
	 */
	@Override
	public TimerMonitoriza getTimerMonitorizaById(Long timerId) {
		
		return repository.findByIdTimer(timerId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#saveTimerMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza)
	 */
	@Override
	public TimerMonitoriza saveTimerMonitoriza(TimerMonitoriza timer) {
		
		return repository.save(timer);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#deleteTimerMonitoriza(java.lang.Long)
	 */
	@Override
	public void deleteTimerMonitoriza(Long timerId) {
		repository.deleteById(timerId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#getAllTimerMonitoriza()
	 */
	@Override
	public Iterable<TimerMonitoriza> getAllTimerMonitoriza() {
		
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<TimerMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

}
