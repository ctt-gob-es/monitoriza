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
 * @version 1.1, 12/09/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlarmMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlarmMonitorizaDatatableRepository;
import es.gob.monitoriza.service.IAlarmMonitorizaService;

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
public class AlarmMonitorizaService implements IAlarmMonitorizaService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmMonitorizaRepository repository;

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmMonitorizaDatatableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getMailMonitorizaById(java.lang.Long)
	 */
	@Override
	public AlarmMonitoriza getAlarmMonitorizaById(Long alarmId) {

		return repository.findByIdAlarm(alarmId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#saveMailMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza)
	 */
	@Override
	public AlarmMonitoriza saveAlarmMonitoriza(AlarmMonitoriza alarm) {

		return repository.save(alarm);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#deleteMailMonitoriza(java.lang.Long)
	 */
	@Override
	public void deleteAlarmMonitoriza(Long alarmId) {
		repository.deleteById(alarmId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getAllMailMonitoriza()
	 */
	@Override
	public Iterable<AlarmMonitoriza> getAllAlarmMonitoriza() {

		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<AlarmMonitoriza> findAll(DataTablesInput input) {

		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlarmMonitorizaService#getAllAlarmMonitorizaByMail(es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza)
	 */
	@Override
	public Iterable<AlarmMonitoriza> getAllAlarmMonitorizaByMail(MailMonitoriza mail) {
		
		List<AlarmMonitoriza> degraded = StreamSupport.stream(repository.findByEmailsDegradedIdMail(mail.getIdMail()).spliterator(), false).collect(Collectors.toList());
		List<AlarmMonitoriza> down = StreamSupport.stream(repository.findByEmailsDownIdMail(mail.getIdMail()).spliterator(), false).collect(Collectors.toList());
		
		degraded.addAll(down);
				
		return degraded.stream().distinct().collect(Collectors.toList());
	}
	
}
