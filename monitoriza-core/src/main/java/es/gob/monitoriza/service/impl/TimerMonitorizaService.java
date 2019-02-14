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
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.TimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.TimerMonitorizaDatatableRepository;
import es.gob.monitoriza.service.ITimerMonitorizaService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 30/01/2019.
 */
@Service("timerMonitorizaService")
public class TimerMonitorizaService implements ITimerMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerMonitorizaRepository repositoryTimer; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerScheduledRepository repositoryScheduled; 
	
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
		
		return repositoryTimer.findByIdTimer(timerId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#saveTimerMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza)
	 */
	@Override
	@Transactional
	public TimerMonitoriza saveTimerMonitoriza(TimerDTO timerDto) {
		
		TimerMonitoriza timerMonitoriza = null;		
		
		if (timerDto.getIdTimer() != null) {
			timerMonitoriza = repositoryTimer.findByIdTimer(timerDto.getIdTimer());
		} else {
			timerMonitoriza = new TimerMonitoriza();
		}

		timerMonitoriza.setFrequency(timerDto.getFrequency());
		timerMonitoriza.setName(timerDto.getName());
		TimerMonitoriza timer = repositoryTimer.save(timerMonitoriza);
						
		// Sólo se actualiza la tarea programada del timer, si éste exsistía ya.
		// Un nuevo timer sin servicios no debe ser contemplado.
		if (timerDto.getIdTimer() != null) {
			
			TimerScheduled scheduled = repositoryScheduled.findByTimerIdTimer(timerDto.getIdTimer());
			// Actualizar en bd (tabla TIMER_SCHEDULED) el timer poniendo IS_UPDATED a false
			if (scheduled != null) {
				scheduled.setUpdated(false);
				repositoryScheduled.save(scheduled);
			}
		}
				
		return timer;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#deleteTimerMonitoriza(java.lang.Long)
	 */
	@Override
	@Transactional(noRollbackFor = EmptyResultDataAccessException.class)
	public void deleteTimerMonitoriza(Long timerId) {
						
		if (timerId != null) {
			try {
				
				TimerMonitoriza timerMonitoriza = repositoryTimer.findByIdTimer(timerId);
				repositoryTimer.deleteById(timerId);
						
				TimerScheduled scheduled = repositoryScheduled.findByTimerIdTimer(timerMonitoriza.getIdTimer());
						
				// Se actualiza el estado del timer programado
				if (scheduled != null) {
					scheduled.setUpdated(false);
				
					repositoryScheduled.save(scheduled);
				}	
						
			} catch (Exception e) {
				throw e;
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#getAllTimerMonitoriza()
	 */
	@Override
	public Iterable<TimerMonitoriza> getAllTimerMonitoriza() {
		
		return repositoryTimer.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<TimerMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#getAllTimerMonitorizaById(java.lang.Iterable)
	 */
	@Override
	public Iterable<TimerMonitoriza> getAllTimerMonitorizaById(Iterable<Long> idTimers) {
		return repositoryTimer.findAllById(idTimers);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerMonitorizaService#findTimersAnyServiceUsingRFC3161Auth()
	 */
	@Override
	public List<TimerMonitoriza> findTimersAnyServiceUsingRFC3161Auth() {
		
		return repositoryTimer.findTimersAnyServiceUsingRFC3161Auth();
	}

}
