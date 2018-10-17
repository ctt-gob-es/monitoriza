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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.NodeRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
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
import es.gob.monitoriza.form.NodeForm;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.INodeMonitorizaService;

/** 
 * <p>Class that manages the REST requests related to the Node administration
 * and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16/10/2018.
 */
@RestController
public class NodeRestController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private INodeMonitorizaService nodeService;
	
	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/nodeafirmadatatable", method = RequestMethod.GET)
	public DataTablesOutput<NodeMonitoriza> dtAfirma(@Valid DataTablesInput input) {

		return (DataTablesOutput<NodeMonitoriza>) nodeService.findAllAfirma(input);

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
	@RequestMapping(path = "/nodetsadatatable", method = RequestMethod.GET)
	public DataTablesOutput<NodeMonitoriza> dtTsa(@Valid DataTablesInput input) {

		return (DataTablesOutput<NodeMonitoriza>) nodeService.findAllTsa(input);

	}
	
	/**
	 * Method that maps the save @firma node web request to the controller and saves it
	 * in the persistence.
	 * 
	 * @param nodeAfirmaForm Object that represents the backing platform form.
	 * @param bindingResult Object that represents the form validation result.
	 * @return {@link DataTablesOutput<NodeMonitoriza>}
	 */
	@RequestMapping(value = "/saveafirmanode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<NodeMonitoriza> saveAfirmaNode(
			@Validated(OrderedValidation.class) @RequestBody NodeForm nodeAfirmaForm, BindingResult bindingResult) {
		DataTablesOutput<NodeMonitoriza> dtOutput = new DataTablesOutput<>();
		NodeMonitoriza nodeAfirma = null;
		List<NodeMonitoriza> listNewAfirma = new ArrayList<NodeMonitoriza>();
		//boolean afirmaHaCambiado = false;
		
		if (bindingResult.hasErrors()) {
			listNewAfirma = StreamSupport.stream(nodeService.getAllNode().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put("invalid-" + o.getField(), o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (nodeAfirmaForm.getIdNode() != null) {
					nodeAfirma = nodeService.getNodeById(nodeAfirmaForm.getIdNode());
					//afirmaHaCambiado = isAfirmaUpdatedForm(nodeAfirmaForm, nodeAfirma);
				} else {
					nodeAfirma = new NodeMonitoriza();
				}
		
				nodeAfirma.setHost(nodeAfirmaForm.getHost());
				nodeAfirma.setName(nodeAfirmaForm.getName());
				nodeAfirma.setPort(nodeAfirmaForm.getPort());
				nodeAfirma.setIsSecure(nodeAfirmaForm.getIsSecure());
				nodeAfirma.setCheckEmergencyDB(nodeAfirmaForm.getCheckEmergencyDB());
				nodeAfirma.setCheckHsm(nodeAfirmaForm.getCheckHsm());
				nodeAfirma.setCheckServices(nodeAfirmaForm.getCheckServices());
				nodeAfirma.setCheckTsa(nodeAfirmaForm.getCheckTsa());
				nodeAfirma.setCheckValidMethod(nodeAfirmaForm.getCheckValidMethod());
				CPlatformType afirmaType = new CPlatformType();
				afirmaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
				nodeAfirma.setNodeType(afirmaType);
		
				NodeMonitoriza afirma = nodeService.savePlatform(nodeAfirma);
				listNewAfirma.add(afirma);
				
				// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
//				if (afirmaHaCambiado && nodeAfirma.getIdPlatform() != null) {
//					
//					updateScheduledTimerFromPlatform(nodeAfirma);
//				}
				
			}catch(Exception e) {
				listNewAfirma = StreamSupport.stream(nodeService.getAllNode().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		
		dtOutput.setData(listNewAfirma);

		return dtOutput;

	}
	
	/**
	 * Method that maps the save ts@ node web request to the controller and saves it
	 * in the persistence.
	 * 
	 * @param nodeTsaForm Object that represents the backing node form.
	 * @param bindingResult Object that represents the form validation result.
	 * @return {@link DataTablesOutput<NodeMonitoriza>}
	 */
	@RequestMapping(value = "/savetsanode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<NodeMonitoriza> saveTsaNode(
			@Validated(OrderedValidation.class) @RequestBody NodeForm nodeTsaForm, BindingResult bindingResult) {
		DataTablesOutput<NodeMonitoriza> dtOutput = new DataTablesOutput<>();
		NodeMonitoriza nodeTsa = null;
		List<NodeMonitoriza> listNewAfirma = new ArrayList<NodeMonitoriza>();
		//boolean afirmaHaCambiado = false;
		
		if (bindingResult.hasErrors()) {
			listNewAfirma = StreamSupport.stream(nodeService.getAllNode().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put("invalid-" + o.getField(), o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (nodeTsaForm.getIdNode() != null) {
					nodeTsa = nodeService.getNodeById(nodeTsaForm.getIdNode());
					//afirmaHaCambiado = isAfirmaUpdatedForm(nodeAfirmaForm, nodeAfirma);
				} else {
					nodeTsa = new NodeMonitoriza();
				}
		
				nodeTsa.setHost(nodeTsaForm.getHost());
				nodeTsa.setName(nodeTsaForm.getName());
				nodeTsa.setPort(nodeTsaForm.getPort());
				nodeTsa.setIsSecure(nodeTsaForm.getIsSecure());
				nodeTsa.setCheckEmergencyDB(nodeTsaForm.getCheckEmergencyDB());
				nodeTsa.setCheckHsm(nodeTsaForm.getCheckHsm());
				nodeTsa.setCheckAfirma(nodeTsaForm.getCheckAfirma());
				
				CPlatformType tsaType = new CPlatformType();
				tsaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
				nodeTsa.setNodeType(tsaType);
		
				NodeMonitoriza afirma = nodeService.savePlatform(nodeTsa);
				listNewAfirma.add(afirma);
				
				// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
//				if (afirmaHaCambiado && nodeAfirma.getIdPlatform() != null) {
//					
//					updateScheduledTimerFromPlatform(nodeAfirma);
//				}
				
			}catch(Exception e) {
				listNewAfirma = StreamSupport.stream(nodeService.getAllNode().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}
		
		dtOutput.setData(listNewAfirma);

		return dtOutput;

	}
	
	/**
	 * Method that maps the delete node request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param nodeId Identifier of the platform to be deleted.
	 * @param index Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletenode", method = RequestMethod.POST)
	public String deleteNode(@RequestParam("id") Long nodeId, @RequestParam("index") String index) {
		try {
			nodeService.deleteNodeById(nodeId);
		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

}
