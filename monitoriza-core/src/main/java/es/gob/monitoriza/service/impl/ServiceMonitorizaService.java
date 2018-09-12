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
 * @version 1.1, 12/09/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.ServiceMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.ServiceMonitorizaDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.TimerSpecification;
import es.gob.monitoriza.service.IServiceMonitorizaService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for ServiceMonitoriza.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.1, 12/09/2018.
 */
@Service
@Transactional
public class ServiceMonitorizaService implements IServiceMonitorizaService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ServiceMonitorizaRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ServiceMonitorizaDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getServiceMonitorizaById(java.lang.Long)
	 */
	@Override
	public ServiceMonitoriza getServiceMonitorizaById(Long serviceId) {

		return repository.findByIdService(serviceId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#saveServiceMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza)
	 */
	@Override
	public ServiceMonitoriza saveServiceMonitoriza(ServiceMonitoriza service) {

		return repository.save(service);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#deleteServiceMonitoriza(java.lang.Long)
	 */
	@Override
	@Transactional(noRollbackFor = EmptyResultDataAccessException.class)
	public void deleteServiceMonitoriza(Long serviceId) {
		if (serviceId != null) {
			try {
				ServiceMonitoriza entity = repository.findByIdService(serviceId);
				repository.delete(entity);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllServiceMonitoriza()
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllServiceMonitoriza() {

		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<ServiceMonitoriza> findAll(DataTablesInput input) {

		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByTimer(es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByTimer(TimerMonitoriza timer) {
				
		TimerSpecification byTimer = new TimerSpecification(timer);
		
		return repository.findAll(byTimer);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByPlatform(es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByPlatform(PlatformMonitoriza platform) {
		
		return repository.findByPlatformIdPlatform(platform.getIdPlatform());
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#getAllByAlarm(es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza)
	 */
	@Override
	public Iterable<ServiceMonitoriza> getAllByAlarm(AlarmMonitoriza alarm) {
		
		return repository.findByAlarmIdAlarm(alarm.getIdAlarm());
	}

}
