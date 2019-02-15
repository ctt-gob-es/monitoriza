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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.AlarmRestController.java.</p>
 * <b>Description:</b><p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>18/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 15/02/2019.
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.AlarmDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfAlarmDTO;
import es.gob.monitoriza.persistence.configuration.dto.MailDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.Alarm;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IAlarmService;
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
 * @version 1.5, 15/02/2019.
 */
@RestController
public class AlarmRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IMailMonitorizaService mailService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IAlarmMonitorizaService alarmMonitorizaService;
	
	/**
	 * Attribute that represents the service object for accessing the
	 * AlarmRespository.
	 */
	@Autowired
	private IAlarmService alarmService;

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of services to the view.
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

	/**
	 * Method that maps the datatable alarms requests to the controller and forwards
	 * the JSON with the data.
	 * 
	 * @param input
	 *            DataTablesInput
	 * @return DataTablesOutput<AlarmMonitoriza>
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alarmsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<AlarmMonitoriza> alarms(@Valid DataTablesInput input) {
		return (DataTablesOutput<AlarmMonitoriza>) alarmMonitorizaService.findAll(input);
	}

	/**
	 * Method that get all emails of system.
	 * @param idAlarm Identifier of the alarm
	 * @param type Type of mail addresses
	 * @return List of mail addresses identifiers
	 */
	@RequestMapping(path = "/emails", method = RequestMethod.GET)
	public List<Long> emails(@RequestParam("id") Long idAlarm, @RequestParam("type") int type) {
		List<Long> result = new ArrayList<Long>();
		Set<MailMonitoriza> mails = new HashSet<MailMonitoriza>();
		AlarmMonitoriza alarmMonitoriza = alarmMonitorizaService.getAlarmMonitorizaById(idAlarm);
		switch (type) {
		case 0:
			mails = alarmMonitoriza.getEmailsDegraded();
			break;
		case 1:
			mails = alarmMonitoriza.getEmailsDown();
			break;
		default:
			break;
		}

		for (MailMonitoriza mm : mails) {
			result.add(mm.getIdMail());
		}

		return result;
	}

	/**
	 * Method that maps the save timer web request to the controller and saves it in
	 * the persistence.
	 * 
	 * @param mailForm
	 *            Object that represents the backing mail form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<TimerMonitoriza>}
	 */
	@RequestMapping(path = "/savemail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<MailMonitoriza> saveMail(
			@Validated(OrderedValidation.class) @RequestBody MailDTO mailForm, BindingResult bindingResult) {
		DataTablesOutput<MailMonitoriza> dtOutput = new DataTablesOutput<>();
		List<MailMonitoriza> listNewMail = new ArrayList<MailMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewMail = StreamSupport.stream(mailService.getAllMailMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				
				MailMonitoriza mail = mailService.saveMailMonitoriza(mailForm);

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

	/**
	 * Method that saves an alarm in the persistence.
	 * 
	 * @param alarmForm
	 *            Object that represents the backing alarm form
	 * @param bindingResult
	 *            Validation result binding object.
	 * @return DataTablesOutput<AlarmMonitoriza>
	 */
	@RequestMapping(path = "/savealarm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<AlarmMonitoriza> saveAlarm(
			@Validated(OrderedValidation.class) @RequestBody AlarmDTO alarmForm, BindingResult bindingResult) {
		DataTablesOutput<AlarmMonitoriza> dtOutput = new DataTablesOutput<>();
		List<AlarmMonitoriza> listNewAlarm = new ArrayList<AlarmMonitoriza>();
		
		if (bindingResult.hasErrors()) {
			listNewAlarm = StreamSupport.stream(alarmMonitorizaService.getAllAlarmMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());

		} else {
			try {
				
				AlarmMonitoriza alarm = alarmMonitorizaService.saveAlarmMonitoriza(alarmForm);

				listNewAlarm.add(alarm);
			

			} catch (Exception e) {
				listNewAlarm = StreamSupport.stream(alarmMonitorizaService.getAllAlarmMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		dtOutput.setData(listNewAlarm);

		return dtOutput;
	}

	/**
	 * Method that deletes a mail from persistence.
	 * 
	 * @param mailId
	 *            The identifier for the mail to delete
	 * @param index
	 *            Index of the datatable.
	 * @return The row index for the datatable.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletemail", method = RequestMethod.POST)
	public String deleteMail(@RequestParam("id") Long mailId, @RequestParam("index") String index) {
		
		String rowIndex = index;
		
		try {
			MailMonitoriza mailMonitoriza = mailService.getMailMonitorizaById(mailId);
			if (mailMonitoriza.getAlarmDegraded().isEmpty() && mailMonitoriza.getAlarmDown().isEmpty()) {
				mailService.deleteMailMonitoriza(mailId);
			} else {
				rowIndex = GeneralConstants.ROW_INDEX_ERROR;
			}
		} catch (Exception e) {
			rowIndex = GeneralConstants.ROW_INDEX_ERROR;
		}
		return rowIndex;
	}

	/**
	 * Method that deletes an alarm from the persistence.
	 * 
	 * @param alarmId
	 *            Identifier of the alarm to delete.
	 * @param index
	 *            Row index of the datatable.
	 * @return The row index of the datatable.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletealarm", method = RequestMethod.POST)
	public String deleteAlarm(@RequestParam("id") Long alarmId, @RequestParam("index") String index) {
		
		String rowIndex = index;
				
		try {
			alarmMonitorizaService.deleteAlarmMonitoriza(alarmId);
		} catch (Exception e) {
			rowIndex = GeneralConstants.ROW_INDEX_ERROR;
		}
		return rowIndex;
	}
	

	/**
	 * Method that maps the list alarms web requests to the controller and forwards
	 * the list of alarms to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/confalarmsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<Alarm> confAlarms(@Valid DataTablesInput input) {
		return (DataTablesOutput<Alarm>) alarmService.getAllAlarm(input);
	}

	/**
	 * Method that maps the save user web request to the controller and saves it in
	 * the persistence.
	 *
	 * @param alarmForm
	 *            Object that represents the backing alarm form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<Alarmt>}
	 */
	@RequestMapping(value = "/saveconfalarm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<Alarm> save(
			@Validated(OrderedValidation.class) @RequestBody ConfAlarmDTO alarmForm, BindingResult bindingResult) {
		DataTablesOutput<Alarm> dtOutput = new DataTablesOutput<>();
		Alarm alarm = null;
		List<Alarm> listNewAlarm = new ArrayList<Alarm>();

		if (bindingResult.hasErrors()) {
			listNewAlarm = StreamSupport.stream(alarmService.getAllAlarm().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				alarm = alarmService.saveAlarm(alarmForm);
				
				listNewAlarm.add(alarm);
			} catch (Exception e) {
				listNewAlarm = StreamSupport.stream(alarmService.getAllAlarm().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewAlarm);

		return dtOutput;
	}

	/**
	 * Method that get all emails of system.
	 * 
	 * @param idAlarm
	 *            Parameter that represents a Alarm identifier.
	 * @return {@link List<Long>}
	 */
	@RequestMapping(path = "/confemails", method = RequestMethod.GET)
	public List<Long> confEmails(@RequestParam("id") String idAlarm) {
		List<Long> result = new ArrayList<Long>();
		Set<MailMonitoriza> mails = new HashSet<MailMonitoriza>();

		Alarm alarm = alarmService.getAlarmById(idAlarm);
		mails = alarm.getMails();

		for (MailMonitoriza mm : mails) {
			result.add(mm.getIdMail());
		}

		return result;
	}

}
