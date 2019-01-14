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
 * @version 1.3, 14/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.AlarmDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.AlarmMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.MailMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ServiceMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
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
 * @version 1.3, 14/01/2019.
 */
@Service("alarmMonitorizaService")
public class AlarmMonitorizaService implements IAlarmMonitorizaService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmMonitorizaRepository repositoryAlarm;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private MailMonitorizaRepository repositoryMail;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private AlarmMonitorizaDatatableRepository dtRepository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private MailMonitorizaService serviceMail;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerScheduledRepository scheduledRepository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private ServiceMonitorizaRepository serviceRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getMailMonitorizaById(java.lang.Long)
	 */
	@Override
	public AlarmMonitoriza getAlarmMonitorizaById(Long alarmId) {

		return repositoryAlarm.findByIdAlarm(alarmId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#saveMailMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza)
	 */
	@Override
	public AlarmMonitoriza saveAlarmMonitoriza(AlarmDTO alarmDto) {
		
		AlarmMonitoriza alarmMonitoriza = null;
		boolean laAlarmaHaCambiado = false;
		
		if (alarmDto.getIdAlarm() != null) {
			alarmMonitoriza = repositoryAlarm.findByIdAlarm(alarmDto.getIdAlarm());
			laAlarmaHaCambiado = isAlarmUpdatedForm(alarmDto, alarmMonitoriza);
		} else {
			alarmMonitoriza = new AlarmMonitoriza();
		}

		alarmMonitoriza.setName(alarmDto.getName());
		alarmMonitoriza.setBlockedTime(alarmDto.getBlockedTime());
		Set<MailMonitoriza> mailsDegraded = serviceMail.splitMails(alarmDto.getDegradedConcat());
		alarmMonitoriza.setEmailsDegraded(mailsDegraded);
		Set<MailMonitoriza> mailsDown = serviceMail.splitMails(alarmDto.getDownConcat());
		alarmMonitoriza.setEmailsDown(mailsDown);
		AlarmMonitoriza alarm = repositoryAlarm.save(alarmMonitoriza);

		for (MailMonitoriza mm : mailsDegraded) {
			repositoryMail.save(mm);
		}
		for (MailMonitoriza mm2 : mailsDown) {
			repositoryMail.save(mm2);
		}

		// Si la alarma ha cambiado y no es nueva (desasignada), hay que actualizar el
		// timer programado.
		if (laAlarmaHaCambiado && alarmMonitoriza.getIdAlarm() != null) {
			updateScheduledTimerFromAlarm(alarmMonitoriza.getIdAlarm());
		}

		return alarm;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#deleteMailMonitoriza(java.lang.Long)
	 */
	@Override
	public void deleteAlarmMonitoriza(Long alarmId) {
		repositoryAlarm.deleteById(alarmId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getAllMailMonitoriza()
	 */
	@Override
	public Iterable<AlarmMonitoriza> getAllAlarmMonitoriza() {

		return repositoryAlarm.findAll();
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
		
		List<AlarmMonitoriza> degraded = StreamSupport.stream(repositoryAlarm.findByEmailsDegradedIdMail(mail.getIdMail()).spliterator(), false).collect(Collectors.toList());
		List<AlarmMonitoriza> down = StreamSupport.stream(repositoryAlarm.findByEmailsDownIdMail(mail.getIdMail()).spliterator(), false).collect(Collectors.toList());
		
		degraded.addAll(down);
				
		return degraded.stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * Method that checks if there are changes between the alarm
	 *         form and the persisted alarm.
	 * @param alarmForm
	 *            The backing form for the alarm
	 * @param alarm
	 *            The alarm
	 * @return true if there are changes in the saved alarm, false otherwise.
	 */
	private boolean isAlarmUpdatedForm(final AlarmDTO alarmForm, final AlarmMonitoriza alarm) {

		return !(alarmForm.getBlockedTime().equals(alarm.getBlockedTime())
				&& alarmForm.getName().equals(alarm.getName())
				&& serviceMail.splitMails(alarmForm.getDegradedConcat()).equals(alarm.getEmailsDegraded())
				&& serviceMail.splitMails(alarmForm.getDownConcat()).equals(alarm.getEmailsDown()));

	}
	
	/**
	 * Method that sets the scheduled timers of the services which uses this
	 * platform as updated.
	 * 
	 * @param idAlarm Identifier of the alarm.
	 */
	private void updateScheduledTimerFromAlarm(final Long idAlarm) {

		final List<ServiceMonitoriza> servicesUsingThisAlarm = StreamSupport
				.stream(serviceRepository.findByAlarmIdAlarm(idAlarm).spliterator(), false).collect(Collectors.toList());

		TimerScheduled scheduled = null;
		for (ServiceMonitoriza service : servicesUsingThisAlarm) {

			scheduled = scheduledRepository.findByTimerIdTimer(service.getTimer().getIdTimer());
			scheduled.setUpdated(false);
			scheduledRepository.save(scheduled);

		}
	}
	
}
