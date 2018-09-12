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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.PlatformAfirmaRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 12/09/2018.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.List;
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
import es.gob.monitoriza.form.AfirmaForm;
import es.gob.monitoriza.form.TsaForm;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.ITimerScheduledService;

/**
 * <p>
 * Class that manages the REST requests related to the Platforms administration
 * and JSON communication.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.1, 12/09/2018.
 */
@RestController
public class PlatformRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IPlatformService platformService;
	
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
	private ITimerScheduledService scheduledService;
	
	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/afirmadatatable", method = RequestMethod.GET)
	public DataTablesOutput<PlatformMonitoriza> dtAfirma(@Valid DataTablesInput input) {

		return (DataTablesOutput<PlatformMonitoriza>) platformService.findAllAfirma(input);

	}
	
	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/tsadatatable", method = RequestMethod.GET)
	public DataTablesOutput<PlatformMonitoriza> dtTsa(@Valid DataTablesInput input) {

		return (DataTablesOutput<PlatformMonitoriza>) platformService.findAllTsa(input);

	}

	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param userId
	 *            Identifier of the user to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteplatform", method = RequestMethod.POST)
	public String deleteAfirma(@RequestParam("id") Long platformId, @RequestParam("index") String index) {
		try {
			PlatformMonitoriza platform = platformService.getPlatformById(platformId);
			platformService.deletePlatform(platform);
		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 * 
	 * @param userForm
	 *            Object that represents the backing user form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<PlatformMonitoriza>}
	 */
	@RequestMapping(value = "/saveafirma", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<PlatformMonitoriza> saveAfirma(
			@Validated(OrderedValidation.class) @RequestBody AfirmaForm afirmaForm, BindingResult bindingResult) {
		DataTablesOutput<PlatformMonitoriza> dtOutput = new DataTablesOutput<>();
		PlatformMonitoriza platformAfirma = null;
		List<PlatformMonitoriza> listNewAfirma = new ArrayList<PlatformMonitoriza>();
		boolean afirmaHaCambiado = false;
		
		if (bindingResult.hasErrors()) {
			listNewAfirma = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put("invalid-" + o.getField(), o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (afirmaForm.getIdPlatform() != null) {
					platformAfirma = platformService.getPlatformById(afirmaForm.getIdPlatform());
					afirmaHaCambiado = isAfirmaUpdatedForm(afirmaForm, platformAfirma);
				} else {
					platformAfirma = new PlatformMonitoriza();
				}
		
				platformAfirma.setHost(afirmaForm.getHost());
				platformAfirma.setName(afirmaForm.getName());
				platformAfirma.setOcspContext(afirmaForm.getOcspContext());
				platformAfirma.setPort(afirmaForm.getPort());
				platformAfirma.setIsSecure(afirmaForm.getIsSecure());
				platformAfirma.setHttpsPort(afirmaForm.getHttpsPort());
				platformAfirma.setServiceContext(afirmaForm.getServiceContext());
				CPlatformType afirmaType = new CPlatformType();
				afirmaType.setIdPlatformType(IPlatformService.ID_PLATFORM_TYPE_AFIRMA);
				platformAfirma.setPlatformType(afirmaType);
		
				PlatformMonitoriza afirma = platformService.savePlatform(platformAfirma);
				listNewAfirma.add(afirma);
				
				// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
				if (afirmaHaCambiado && platformAfirma.getIdPlatform() != null) {
					
					updateScheduledTimerFromPlatform(platformAfirma);
				}
				
			}catch(Exception e) {
				listNewAfirma = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		
		dtOutput.setData(listNewAfirma);

		return dtOutput;

	}
	
	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 * 
	 * @param userForm
	 *            Object that represents the backing user form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<PlatformMonitoriza>}
	 */
	@RequestMapping(value = "/savetsa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<PlatformMonitoriza> saveTsa(
			@Validated(OrderedValidation.class) @RequestBody TsaForm tsaForm, BindingResult bindingResult) {
		DataTablesOutput<PlatformMonitoriza> dtOutput = new DataTablesOutput<>();
		PlatformMonitoriza platformTsa = null;
		List<PlatformMonitoriza> listNewTsa = new ArrayList<PlatformMonitoriza>();
		boolean tsaHaCambiado = false;
		
		if (bindingResult.hasErrors()) {
			listNewTsa = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (tsaForm.getIdPlatform() != null) {
					platformTsa = platformService.getPlatformById(tsaForm.getIdPlatform());
					tsaHaCambiado = isTsaUpdatedForm(tsaForm, platformTsa);
				} else {
					platformTsa = new PlatformMonitoriza();
				}
		
				platformTsa.setHost(tsaForm.getHost());
				platformTsa.setName(tsaForm.getName());
				platformTsa.setPort(tsaForm.getPort());
				platformTsa.setIsSecure(tsaForm.getIsSecure());
				platformTsa.setHttpsPort(tsaForm.getHttpsPort());
				platformTsa.setServiceContext(tsaForm.getServiceContext());
				CPlatformType afirmaType = new CPlatformType();
				afirmaType.setIdPlatformType(IPlatformService.ID_PLATFORM_TYPE_TSA);
				platformTsa.setPlatformType(afirmaType);
				platformTsa.setRfc3161Context(tsaForm.getRfc3161Context());
				platformTsa.setRfc3161Port(tsaForm.getRfc3161Port());
				platformTsa.setUseRfc3161Auth(tsaForm.getUseRfc3161Auth());
				platformTsa.setRfc3161Certificate(sysCertService.getSystemCertificateById(tsaForm.getRfc3161Certificate()));
				
				if (tsaForm.getRfc3161Certificate() == null || tsaForm.getRfc3161Certificate().longValue() == -1) {
					platformTsa.setUseRfc3161Auth(false);
				}
						
				PlatformMonitoriza tsa = platformService.savePlatform(platformTsa);
				
				// Se construye objeto vacío para evitar warning de datatables
				if (!tsaForm.getUseRfc3161Auth()) {
					platformTsa.setRfc3161Certificate(new SystemCertificate());
				}
												
				listNewTsa.add(tsa);
				
				// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
				if (tsaHaCambiado && platformTsa.getIdPlatform() != null) {
					
					updateScheduledTimerFromPlatform(platformTsa);
				}
				
			}catch(Exception e) {
				listNewTsa = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		
		dtOutput.setData(listNewTsa);

		return dtOutput;

	}
	
	/**
	 * Method that sets the scheduled timers of the services which uses this platform as updated
	 * @param platform
	 */
	private void updateScheduledTimerFromPlatform(PlatformMonitoriza platform) {
		
		List<ServiceMonitoriza> servicesUsingThisPlatform = StreamSupport.stream(serviceService.getAllByPlatform(platform).spliterator(), false).collect(Collectors.toList());
		
		TimerScheduled scheduled = null;
		for (ServiceMonitoriza service : servicesUsingThisPlatform) {
			
			scheduled = scheduledService.getTimerScheduledByIdTimer(service.getTimer().getIdTimer());
			scheduled.setUpdated(false);
			scheduledService.saveTimerScheduled(scheduled);
			
		}
		
	}
	
	/**
	 * Method thar checks if there are changes between the afirma form and the persisted platform
	 * @param afirmaForm
	 * @param service
	 * @return
	 */
	private boolean isAfirmaUpdatedForm(AfirmaForm afirmaForm, PlatformMonitoriza platform) {
		
		return !(afirmaForm.getIsSecure().equals(platform.getIsSecure()) &&
				 afirmaForm.getHost().equals(platform.getHost()) &&
				 afirmaForm.getHttpsPort().equals(platform.getHttpsPort()) &&
				 afirmaForm.getName().equals(platform.getName()) &&
				 afirmaForm.getOcspContext().equals(platform.getOcspContext()) &&
				 afirmaForm.getPort().equals(platform.getPort()) &&
				 afirmaForm.getIsSecure().equals(platform.getIsSecure()) &&
				 afirmaForm.getServiceContext().equals(platform.getServiceContext()));
		
	}
	
	/**
	 * Method thar checks if there are changes between the TS@ form and the persisted platform
	 * @param afirmaForm
	 * @param service
	 * @return
	 */
	private boolean isTsaUpdatedForm(TsaForm tsaForm, PlatformMonitoriza platform) {
		
		return !(tsaForm.getIsSecure().equals(platform.getIsSecure()) &&
				tsaForm.getHost().equals(platform.getHost()) &&
				tsaForm.getHttpsPort().equals(platform.getHttpsPort()) &&
				tsaForm.getName().equals(platform.getName()) &&
				tsaForm.getRfc3161Certificate().equals(platform.getRfc3161Certificate().getIdSystemCertificate())) &&
				tsaForm.getRfc3161Context().equals(platform.getRfc3161Context()) &&
				tsaForm.getRfc3161Port().equals(platform.getRfc3161Port()) &&
				tsaForm.getPort().equals(platform.getPort()) &&
				tsaForm.getIsSecure().equals(platform.getIsSecure()) &&
				tsaForm.getServiceContext().equals(platform.getServiceContext());
		
	}

}
