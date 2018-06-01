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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.ServiceRestController.java.</p>
 * <b>Description:</b><p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 abr. 2018.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.form.AlarmForm;
import es.gob.monitoriza.form.MailForm;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IMailMonitorizaService;

/**
 * <p>
 * Class that manages the REST requests related to the Services administration
 * and JSON communication.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 20 abr. 2018.
 */
@RestController
public class AlarmRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IMailMonitorizaService mailService;

	@Autowired
	private IAlarmMonitorizaService alarmService;

	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of services to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/mailsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<MailMonitoriza> mails(@Valid DataTablesInput input) {
		return (DataTablesOutput<MailMonitoriza>) mailService.findAll(input);
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alarmsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<AlarmMonitoriza> alarms(@Valid DataTablesInput input) {
		return (DataTablesOutput<AlarmMonitoriza>) alarmService.findAll(input);
	}
	
	@RequestMapping(path = "/emails", method = RequestMethod.GET)
	public List<Long> emails(@RequestParam("id") Long idAlarm, @RequestParam("type") int type) {
		List<Long> result = new ArrayList<Long>();
		Set<MailMonitoriza> mails = new HashSet<MailMonitoriza>();
		AlarmMonitoriza alarmMonitoriza = alarmService.getAlarmMonitorizaById(idAlarm); 
		switch(type) {
			case 0:
				mails = alarmMonitoriza.getEmailsDegraded();
				break;
			case 1:
				mails = alarmMonitoriza.getEmailsDown();
				break;
			default:
				break;
		}
		
		for (MailMonitoriza mm: mails) {
			result.add(mm.getIdMail());
		}

		return result;
	}
	
	/**
	 * Method that maps the save timer web request to the controller and saves
	 * it in the persistence.
	 * 
	 * @param timerForm
	 *            Object that represents the backing timer form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<TimerMonitoriza>}
	 */
	@RequestMapping(path = "/savemail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<MailMonitoriza> saveMail(
			@Validated(OrderedValidation.class) @RequestBody MailForm mailForm, BindingResult bindingResult) {
		DataTablesOutput<MailMonitoriza> dtOutput = new DataTablesOutput<>();
		MailMonitoriza mailMonitoriza = null;
		List<MailMonitoriza> listNewMail = new ArrayList<MailMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewMail = StreamSupport.stream(mailService.getAllMailMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (mailForm.getIdMail() != null) {
					mailMonitoriza = mailService.getMailMonitorizaById(mailForm.getIdMail());
				} else {
					mailMonitoriza = new MailMonitoriza();
				}
	
				mailMonitoriza.setEmailAddress(mailForm.getEmailAddress());
				MailMonitoriza mail = mailService.saveMailMonitoriza(mailMonitoriza);
	
				listNewMail.add(mail);
			} catch (Exception e) {
				listNewMail = StreamSupport.stream(mailService.getAllMailMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		dtOutput.setData(listNewMail);

		return dtOutput;
	}

	@RequestMapping(path = "/savealarm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<AlarmMonitoriza> saveAlarm(
			@Validated(OrderedValidation.class) @RequestBody AlarmForm alarmForm, BindingResult bindingResult) {
		DataTablesOutput<AlarmMonitoriza> dtOutput = new DataTablesOutput<>();
		AlarmMonitoriza alarmMonitoriza = null;
		List<AlarmMonitoriza> listNewAlarm = new ArrayList<AlarmMonitoriza>();
		
		if (bindingResult.hasErrors()) {
			listNewAlarm = StreamSupport.stream(alarmService.getAllAlarmMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (alarmForm.getIdAlarm() != null) {
					alarmMonitoriza = alarmService.getAlarmMonitorizaById(alarmForm.getIdAlarm());
				} else {
					alarmMonitoriza = new AlarmMonitoriza();
				}
	
				alarmMonitoriza.setName(alarmForm.getName());
				alarmMonitoriza.setBlockedTime(alarmForm.getBlockedTime());
				Set<MailMonitoriza> mailsDegraded = mailService.splitMails(alarmForm.getDegradedConcat());
				alarmMonitoriza.setEmailsDegraded(mailsDegraded);
				Set<MailMonitoriza> mailsDown = mailService.splitMails(alarmForm.getDownConcat());
				alarmMonitoriza.setEmailsDown(mailsDown);
				AlarmMonitoriza alarm = alarmService.saveAlarmMonitoriza(alarmMonitoriza);
	
				for(MailMonitoriza mm: mailsDegraded) {
					mailService.saveMailMonitoriza(mm);
				}
				for(MailMonitoriza mm2: mailsDown) {
					mailService.saveMailMonitoriza(mm2);
				}
				
				listNewAlarm.add(alarm);
			} catch (Exception e) {
				listNewAlarm = StreamSupport.stream(alarmService.getAllAlarmMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		dtOutput.setData(listNewAlarm);

		return dtOutput;
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletemail", method = RequestMethod.POST)
	public String deleteMail(@RequestParam("id") Long mailId, @RequestParam("index") String index) {
		try {
			MailMonitoriza mailMonitoriza =  mailService.getMailMonitorizaById(mailId);
			if( mailMonitoriza.getAlarmDegraded().isEmpty() && mailMonitoriza.getAlarmDown().isEmpty() ) {
				mailService.deleteMailMonitoriza(mailId);
			}else {
				index = "-1";
			}
		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletealarm", method = RequestMethod.POST)
	public String deleteAlarm(@RequestParam("id") Long alarmId, @RequestParam("index") String index) {
		try {
			alarmService.deleteAlarmMonitoriza(alarmId);
		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

}