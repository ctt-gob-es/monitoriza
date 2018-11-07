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
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.ConfAlarmDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.Alarm;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlarmRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.MailMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlarmDataTablesRepository;
import es.gob.monitoriza.service.IAlarmService;
import es.gob.monitoriza.service.IMailMonitorizaService;

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
 * @version 1.2, 28/10/2018.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AlarmService implements IAlarmService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmRepository repositoryAlarm;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private MailMonitorizaRepository repositoryMail;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private IMailMonitorizaService serviceMail;

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmDataTablesRepository dtRepository;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IAlarmService#getAllAlarm()
	 */
	@Override
	public List<Alarm> getAllAlarm() {
		List<Alarm> result = new ArrayList<Alarm>();
		result = repositoryAlarm.findAll();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IAlarmService#getAlarmById(java.lang.Long)
	 */
	@Override
	public Alarm getAlarmById(String idAlarm) {
		return repositoryAlarm.findByIdAlarm(idAlarm);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IAlarmService#saveAlarm(es.gob.valet.persistence.configuration.model.entity.Alarm)
	 */
	@Override
	@Transactional
	public Alarm saveAlarm(Alarm alarm) {
		return repositoryAlarm.save(alarm);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IAlarmService#deleteAlarm(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteAlarm(Long idAlarm) {
		repositoryAlarm.deleteById(idAlarm);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlarmService#getAllAlarm(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<Alarm> getAllAlarm(DataTablesInput input) {
		return dtRepository.findAll(input);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlarmService#saveAlarm(es.gob.monitoriza.persistence.configuration.dto.ConfAlarmDTO)
	 */
	@Override
	@Transactional
	public Alarm saveAlarm(ConfAlarmDTO alarmDto) {
		
		Alarm alarm = null;
		alarm = repositoryAlarm.findByIdAlarm(alarmDto.getIdAlarm());
		alarm.setIdAlarm(alarmDto.getIdAlarm());
		alarm.setTimeBlock(alarmDto.getTimeBlock());
		alarm.setActive(alarmDto.getActive());
		Set<MailMonitoriza> mails = serviceMail.splitMails(alarmDto.getMailsConcat());
		alarm.setMails(mails);

		Alarm alarmNew = repositoryAlarm.save(alarm);

		for (MailMonitoriza m : mails) {
			repositoryMail.save(m);
		}
		
		return alarmNew;
	}

}
