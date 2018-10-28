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
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.service.ITimerScheduledService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for TimerScheduled.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1 28/10/2018.
 */
@Service
public class TimerScheduledService implements ITimerScheduledService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerScheduledRepository repository; 
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#getTimerScheduledById(java.lang.Long)
	 */
	@Override
	public TimerScheduled getTimerScheduledById(Long timerId) {
		
		return repository.findByIdTimerScheduled(timerId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#saveTimerScheduled(es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled)
	 */
	@Override
	public TimerScheduled saveTimerScheduled(TimerScheduled timer) {
		
		return repository.save(timer);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#deleteTimerScheduled(java.lang.Long)
	 */
	@Override
	public void deleteTimerScheduled(Long timerId) {
		repository.deleteById(timerId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#getAllTimerScheduled()
	 */
	@Override
	public Iterable<TimerScheduled> getAllTimerScheduled() {
		
		return repository.findAll();
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#getAllTimerScheduledById(java.lang.Iterable)
	 */
	@Override
	public Iterable<TimerScheduled> getAllTimerScheduledById(Iterable<Long> idTimers) {
		return repository.findAllById(idTimers);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#getTimerScheduledByIdTimer(java.lang.Long)
	 */
	@Override
	public TimerScheduled getTimerScheduledByIdTimer(Long idTimer) {
		
		return repository.findByTimerIdTimer(idTimer);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#emptyTimersScheduled()
	 */
	@Override
	public void emptyTimersScheduled() {
		repository.deleteAll();	
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ITimerScheduledService#setAllScheduledTimersNotUpdated()
	 */
	@Override
	public void setAllScheduledTimersNotUpdated() {
		repository.setAllNotUpdated();
		
	}

}
