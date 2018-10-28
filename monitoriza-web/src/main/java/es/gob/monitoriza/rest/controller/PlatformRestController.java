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
 * @version 1.5, 28/10/2018.
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
import es.gob.monitoriza.persistence.configuration.dto.AfirmaDTO;
import es.gob.monitoriza.persistence.configuration.dto.TsaDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IPlatformService;

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
 * @version 1.5, 28/10/2018..
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
	 * @param platformId Identifier of the platform to be deleted.
	 * @param index Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteplatform", method = RequestMethod.POST)
	public String deleteAfirma(@RequestParam("id") Long platformId, @RequestParam("index") String index) {
		
		String rowIndex = index;
		
		try {
			PlatformMonitoriza platform = platformService.getPlatformById(platformId);
			platformService.deletePlatform(platform);
		} catch (Exception e) {
			rowIndex = GeneralConstants.ROW_INDEX_ERROR;
		}
		return rowIndex;
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence. It also updates the scheduled timers.
	 * 
	 * @param afirmaForm
	 *            Object that represents the backing platform form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<PlatformMonitoriza>}
	 */
	@RequestMapping(value = "/saveafirma", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<PlatformMonitoriza> saveAfirma(
			@Validated(OrderedValidation.class) @RequestBody AfirmaDTO afirmaForm, BindingResult bindingResult) {
		DataTablesOutput<PlatformMonitoriza> dtOutput = new DataTablesOutput<>();
		List<PlatformMonitoriza> listNewAfirma = new ArrayList<PlatformMonitoriza>();
				
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
				
				PlatformMonitoriza platformAfirma = platformService.savePlatformAfirma(afirmaForm);
				listNewAfirma.add(platformAfirma);
				
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
	 * in the persistence. It also updates the scheduled timers.
	 * 
	 * @param tsaForm
	 *            Object that represents the backing platform form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<PlatformMonitoriza>}
	 */
	@RequestMapping(value = "/savetsa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<PlatformMonitoriza> saveTsa(
			@Validated(OrderedValidation.class) @RequestBody TsaDTO tsaForm, BindingResult bindingResult) {
		DataTablesOutput<PlatformMonitoriza> dtOutput = new DataTablesOutput<>();
		List<PlatformMonitoriza> listNewTsa = new ArrayList<PlatformMonitoriza>();
				
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
						
				PlatformMonitoriza tsa = platformService.savePlatformTsa(tsaForm);
																
				listNewTsa.add(tsa);				
				
			}catch(Exception e) {
				listNewTsa = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		
		dtOutput.setData(listNewTsa);

		return dtOutput;

	}
	
}
