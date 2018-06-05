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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import es.gob.monitoriza.form.ServiceForm;
import es.gob.monitoriza.form.TimerForm;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;

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
public class ServiceRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IServiceMonitorizaService serviceService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private ITimerMonitorizaService timerService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IPlatformService platformService;

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
	@RequestMapping(path = "/servicesdatatable", method = RequestMethod.GET)
	public DataTablesOutput<ServiceMonitoriza> services(@Valid DataTablesInput input) {
		return (DataTablesOutput<ServiceMonitoriza>) serviceService.findAll(input);
	}

	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of timees to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/timersdatatable", method = RequestMethod.GET)
	public DataTablesOutput<TimerMonitoriza> timers(@Valid DataTablesInput input) {
		return (DataTablesOutput<TimerMonitoriza>) timerService.findAll(input);
	}

	/**
	 * Method that maps the request to get the service types for the selected
	 * platform type. to the view.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path = "/loadservicetype", method = RequestMethod.GET)
	public List<String> loadservicetype(@RequestParam("idPlatform") Long idPlatform) {

		PlatformMonitoriza platform = platformService.getPlatformById(idPlatform);
		List<String> serviceTypes = new ArrayList<String>();

		if (platform != null
				&& platform.getPlatformType().getIdPlatformType().equals(IPlatformService.ID_PLATFORM_TYPE_AFIRMA)) {

			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.OCSP_SERVICE.toUpperCase());

		} else if (platform != null
				&& platform.getPlatformType().getIdPlatformType().equals(IPlatformService.ID_PLATFORM_TYPE_TSA)) {

			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.RFC3161_SERVICE.toUpperCase());
		}

		return serviceTypes;
	}

	/**
	 * Method that maps the request to get the service types for the selected
	 * platform type. to the view.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path = "/loadbaseendpoint", method = RequestMethod.GET)
	public Map<String, String> loadbaseendpoint(@RequestParam("idPlatform") Long idPlatform,
			@RequestParam("serviceType") String serviceType) {

		PlatformMonitoriza platform = platformService.getPlatformById(idPlatform);
		StringBuilder baseEndpoint = new StringBuilder();
		baseEndpoint.append("http://").append(platform.getHost()).append(GeneralConstants.COLON)
				.append(platform.getPort());

		switch (serviceType.toLowerCase()) {
		case GeneralConstants.SOAP_SERVICE:
			baseEndpoint.append(platform.getServiceContext());
			break;
		case GeneralConstants.OCSP_SERVICE:
			baseEndpoint.append(platform.getOcspContext());
			break;
		case GeneralConstants.RFC3161_SERVICE:
			baseEndpoint.append(platform.getRfc3161Context());
			break;
		default:
			break;
		}

		return Collections.singletonMap("response", baseEndpoint.toString());
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
	@RequestMapping(value = "/savetimer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<TimerMonitoriza> saveTimer(
			@Validated(OrderedValidation.class) @RequestBody TimerForm timerForm, BindingResult bindingResult) {
		DataTablesOutput<TimerMonitoriza> dtOutput = new DataTablesOutput<>();
		TimerMonitoriza timerMonitoriza = null;
		List<TimerMonitoriza> listNewTimer = new ArrayList<TimerMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewTimer = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "Timer_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (timerForm.getIdTimer() != null) {
					timerMonitoriza = timerService.getTimerMonitorizaById(timerForm.getIdTimer());
				} else {
					timerMonitoriza = new TimerMonitoriza();
				}

				timerMonitoriza.setFrequency(timerForm.getFrequency());
				timerMonitoriza.setName(timerForm.getName());
				TimerMonitoriza timer = timerService.saveTimerMonitoriza(timerMonitoriza);

				listNewTimer.add(timer);
			} catch (Exception e) {
				listNewTimer = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		dtOutput.setData(listNewTimer);

		return dtOutput;
	}

	@RequestMapping(path = "/saveservice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<ServiceMonitoriza> saveService(
			@Validated(OrderedValidation.class) @RequestBody ServiceForm serviceForm, BindingResult bindingResult) {
		DataTablesOutput<ServiceMonitoriza> dtOutput = new DataTablesOutput<>();
		ServiceMonitoriza serviceMonitoriza = null;
		List<ServiceMonitoriza> listNewService = new ArrayList<ServiceMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewService = StreamSupport.stream(serviceService.getAllServiceMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (serviceForm.getIdService() != null) {
					serviceMonitoriza = serviceService.getServiceMonitorizaById(serviceForm.getIdService());
				} else {
					serviceMonitoriza = new ServiceMonitoriza();
				}

				serviceMonitoriza.setDegradedThreshold(serviceForm.getDegradedThreshold());
				serviceMonitoriza.setLostThreshold(serviceForm.getLostThreshold());
				serviceMonitoriza.setName(serviceForm.getName());
				serviceMonitoriza.setNameWsdl(serviceForm.getNameWsdl());
				serviceMonitoriza.setAlarm(alarmService.getAlarmMonitorizaById(serviceForm.getAlarm()));
				serviceMonitoriza.setPlatform(platformService.getPlatformById(serviceForm.getPlatform()));
				serviceMonitoriza.setTimeout(serviceForm.getTimeout());
				serviceMonitoriza.setTimer(timerService.getTimerMonitorizaById(serviceForm.getTimer()));
				serviceMonitoriza.setServiceType(serviceForm.getServiceType());

				ServiceMonitoriza service = serviceService.saveServiceMonitoriza(serviceMonitoriza);
				listNewService.add(service);
			} catch (Exception e) {
				listNewService = StreamSupport.stream(serviceService.getAllServiceMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		dtOutput.setData(listNewService);

		return dtOutput;
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletetimer", method = RequestMethod.POST)
	public String deleteTimer(@RequestParam("id") Long timerId, @RequestParam("index") String index) {
		try {
			timerService.deleteTimerMonitoriza(timerId);
		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteservice", method = RequestMethod.POST)
	public String deleteservice(@RequestParam("id") Long idService, @RequestParam("index") String index) {
		try {
			serviceService.deleteServiceMonitoriza(idService);
		} catch (EmptyResultDataAccessException e) {
			index = "-1";
			throw e;
		}
		return index;
	}

}
