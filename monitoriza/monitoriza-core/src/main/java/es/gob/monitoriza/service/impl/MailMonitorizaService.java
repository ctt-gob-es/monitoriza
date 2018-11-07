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
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.MailDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.persistence.configuration.model.repository.MailMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ServiceMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TimerScheduledRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.MailMonitorizaDatatableRepository;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IMailMonitorizaService;
import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
@Service
public class MailMonitorizaService implements IMailMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private MailMonitorizaRepository repository; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private MailMonitorizaDatatableRepository dtRepository;
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private ServiceMonitorizaRepository repositoryServices; 
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private TimerScheduledRepository scheduledServices; 
	
	/**
	 * Attribute that represents the injected interface of alarm services. 
	 */
	@Autowired
	private IAlarmMonitorizaService alarmService;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getMailMonitorizaById(java.lang.Long)
	 */
	@Override
	public MailMonitoriza getMailMonitorizaById(Long mailId) {
		
		return repository.findByIdMail(mailId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#saveMailMonitoriza(es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza)
	 */
	@Override
	@Transactional
	public MailMonitoriza saveMailMonitoriza(MailDTO mailDto) {
		
		MailMonitoriza mailMonitoriza = null;
		boolean haCambiadoEmail = false;
		
		if (mailDto.getIdMail() != null) {
			mailMonitoriza = repository.findByIdMail(mailDto.getIdMail());
			haCambiadoEmail = isMailUpdatedForm(mailDto, mailMonitoriza);
		} else {
			mailMonitoriza = new MailMonitoriza();
		}

		mailMonitoriza.setEmailAddress(mailDto.getEmailAddress());
		MailMonitoriza mail = repository.save(mailMonitoriza);

		

		if (haCambiadoEmail && mailMonitoriza.getIdMail() != null) {
			updateScheduledTimerFromMail(mailMonitoriza);
		}
				
		return repository.save(mail);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#deleteMailMonitoriza(java.lang.Long)
	 */
	@Override
	public void deleteMailMonitoriza(Long mailId) {
		repository.deleteById(mailId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#getAllMailMonitoriza()
	 */
	@Override
	public Iterable<MailMonitoriza> getAllMailMonitoriza() {
		
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IServiceMonitorizaService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<MailMonitoriza> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMailMonitorizaService#splitMails(java.lang.String)
	 */
	@Override
	public Set<MailMonitoriza> splitMails(String concatString) {
		Set<MailMonitoriza> result = new HashSet<MailMonitoriza>();
		String[] aux = concatString.split("&");
		
		for (int i=0; i<aux.length; i++) {
			result.add(getMailMonitorizaById(Long.parseLong(aux[i], NumberConstants.NUM10)));
		}
		
		return result;
	}
	
	/**
	 * Method thar checks if there are changes between the mail form
	 *         and the persisted mail.
	 * @param mailForm
	 *            Object that represents the backing form.
	 * @param mail
	 *            Object that represents the MailMonitoriza entity.
	 * @return true if the mail has been updates, false otherwise. 
	 */
	private boolean isMailUpdatedForm(MailDTO mailForm, MailMonitoriza mail) {

		return !(mailForm.getEmailAddress().equals(mail.getEmailAddress()));

	}
	
	/**
	 * Method that sets the scheduled timers of the services which uses this
	 * platform as updated.
	 * 
	 * @param mail
	 *            The mail whose timers must be updated
	 */
	private void updateScheduledTimerFromMail(final MailMonitoriza mail) {

		List<AlarmMonitoriza> alarmsUsingThisMail = StreamSupport
				.stream(alarmService.getAllAlarmMonitorizaByMail(mail).spliterator(), false)
				.collect(Collectors.toList());

		List<ServiceMonitoriza> servicesUsingThisAlarm = new ArrayList<ServiceMonitoriza>();
		for (AlarmMonitoriza alarm : alarmsUsingThisMail) {
			servicesUsingThisAlarm.addAll(StreamSupport.stream(repositoryServices.findByAlarmIdAlarm(alarm.getIdAlarm()).spliterator(), false)
					.collect(Collectors.toList()));
		}

		TimerScheduled scheduled = null;
		for (ServiceMonitoriza service : servicesUsingThisAlarm) {

			scheduled = scheduledServices.findByTimerIdTimer(service.getTimer().getIdTimer());
			scheduled.setUpdated(false);
			scheduledServices.save(scheduled);

		}
	}

}
