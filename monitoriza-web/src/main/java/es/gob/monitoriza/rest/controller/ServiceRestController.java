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
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.8, 25/01/2019.
 */
package es.gob.monitoriza.rest.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.RequestFileNotFoundException;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.TimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.IRequestServiceFileService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;
import es.gob.monitoriza.utilidades.FileUtils;

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
 * @version 1.8, 25/01/2019.
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
	
	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IRequestServiceFileService fileService;
	
	
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
	 * @param idPlatform Platform identifier
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path = "/loadservicetype", method = RequestMethod.GET)
	public List<String> loadservicetype(@RequestParam("idPlatform") Long idPlatform) {

		PlatformMonitoriza platform = platformService.getPlatformById(idPlatform);
		List<String> serviceTypes = new ArrayList<String>();

		if (platform != null
				&& platform.getPlatformType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA)) {

			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.OCSP_SERVICE.toUpperCase());

		} else if (platform != null
				&& platform.getPlatformType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA)) {

			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.RFC3161_SERVICE.toUpperCase());
		} else {
			serviceTypes.add(GeneralConstants.HTTP_SERVICE.toUpperCase());
		}

		return serviceTypes;
	}

	/**
	 * Method that maps the request to get the service types for the selected
	 * platform type. to the view.
	 * @param idPlatform Platform identifier
	 * @param serviceType String that represents the service type.
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
		case GeneralConstants.HTTP_SERVICE:
			baseEndpoint.append(platform.getServiceContext());
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
	 * @param timerForm Object that represents the backing timer form.
	 * @param bindingResult Object that represents the form validation result.
	 * @return {@link DataTablesOutput<TimerMonitoriza>}
	 */
	@RequestMapping(value = "/savetimer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	@Transactional
	public @ResponseBody DataTablesOutput<TimerMonitoriza> saveTimer(
			@Validated(OrderedValidation.class) @RequestBody TimerDTO timerForm, BindingResult bindingResult) {
		DataTablesOutput<TimerMonitoriza> dtOutput = new DataTablesOutput<>();
		
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
				
				TimerMonitoriza timerMonitoriza = timerService.saveTimerMonitoriza(timerForm);
				
				listNewTimer.add(timerMonitoriza);
				
			} catch (Exception e) {
				listNewTimer = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				dtOutput.setError(GeneralConstants.ROW_INDEX_ERROR);
				throw e;
			}
		}
		dtOutput.setData(listNewTimer);

		return dtOutput;
	}

	/**
	 * Method that maps the save service web request to the controller and saves
	 * it in the persistence.
	 * @param serviceForm Backing form object with the service data.
	 * @param file ZIP file with requests for this service
	 * @param bindingResult Validation binding object.
	 * @return {@link DataTablesOutput<ServiceMonitoriza>}
	 */
	@RequestMapping(path = "/saveservice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@JsonView(DataTablesOutput.View.class)
	@Transactional
	public @ResponseBody DataTablesOutput<ServiceMonitoriza> saveService(
			@Validated(OrderedValidation.class) @RequestPart("serviceForm") ServiceDTO serviceForm, @RequestPart("file") MultipartFile file, BindingResult bindingResult) {
		
		DataTablesOutput<ServiceMonitoriza> dtOutput = new DataTablesOutput<>();
		List<ServiceMonitoriza> listNewService = new ArrayList<ServiceMonitoriza>();
				
		// Se controla manualmente el error 'requerido' para el campo nameWsdl, ya que depende del tipo de servicio
		if (serviceForm.getServiceType().equalsIgnoreCase(GeneralConstants.SOAP_SERVICE)) {
			
			if ("".equals(serviceForm.getNameWsdl())) {
				FieldError wsdlFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_ENDPOINT, "El campo 'Endpoint' es obligatorio.");
				bindingResult.addError(wsdlFieldError);
			}
			
			if (serviceForm.getNameWsdl() != null && (serviceForm.getNameWsdl().length() < 1 || serviceForm.getNameWsdl().length() > NumberConstants.NUM30)) {
				FieldError wsdlFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_ENDPOINT, "El tamaño debe estar entre 1 y 30.");
				bindingResult.addError(wsdlFieldError);
			}
			
		}
		
		// Al ser file un campo individual de la petición, se controla la validación por separado. 
		// En modo edición, existe un identificador de fichero, pero se manda un fichero vacío por
		// requisitos de la petición (no acepta null) / Error: "Required request part 'file' is not present".
		if ((file == null || file.isEmpty()) && serviceForm.getIdFile() == null) {
			
			FieldError fileNullError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_FILE, "El campo 'Archivo de peticiones' es obligatorio.");
			bindingResult.addError(fileNullError);
			
		} else if (!file.isEmpty() && !checkAllowedFormat(file)) {
			
			FieldError fileTypeError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_FILE, "El archivo de peticiones debe tener formato ZIP.");
			bindingResult.addError(fileTypeError);
		}				
					
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
						
				// Se almacena el servicio y se comprueba si es necesario actualizar el timer programado
				ServiceMonitoriza service = serviceService.saveServiceMonitoriza(serviceForm, file);
							
				listNewService.add(service);	
					
			} catch (Exception e) {
				listNewService = StreamSupport.stream(serviceService.getAllServiceMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				dtOutput.setError(GeneralConstants.ROW_INDEX_ERROR);
			}
		}
		
		dtOutput.setData(listNewService);

		return dtOutput;
	}


	/**
	 * Method that deletes a timer from persistence and updates the scheduled timers.
	 * @param timerId Identifier of the timer to be deleted
	 * @param index Index of the row in the data table
	 * @return The index of the row (timer) to be deleted, -1 in case of error.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletetimer", method = RequestMethod.POST)
	@Transactional
	public String deleteTimer(final @RequestParam("id") Long timerId, final @RequestParam("index") String index) {
		
		String indexResult = index;
		
		try {
			timerService.deleteTimerMonitoriza(timerId);		
			
		} catch (Exception e) {
			indexResult = GeneralConstants.ROW_INDEX_ERROR;
		}
		return indexResult;
	}

	/**
	 * Method that deletes a service from persistence and updates the scheduled timer.
	 * @param idService Identifier of the service to be deleted
	 * @param index Index of the row in the data table
	 * @return The index of the row (service) to be deleted, -1 in case of error.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteservice", method = RequestMethod.POST)
	@Transactional
	public String deleteservice(@RequestParam("id") Long idService, @RequestParam("index") String index) {
		
		String indexResult = index;
		
		try {
			
			serviceService.deleteServiceMonitoriza(idService);
			
		} catch (EmptyResultDataAccessException e) {
			indexResult = GeneralConstants.ROW_INDEX_ERROR;
			throw e;
		}
		return indexResult;
	}
		
	/**
	 * Method to discard non zip files. 
	 * @param file Multipart file to check
	 * @return true if the file matches supported extension and includes signature format number for zip files.
	 * @throws IOException 
	 */
	private boolean checkAllowedFormat(final MultipartFile file) {
		
		final String extension = file.getOriginalFilename().split("\\.")[1];
		boolean esZip = false;
		byte[ ] fileBytes;
		
		try {
			
			fileBytes = file.getBytes();
		
			final String isoZipSignature = new String(fileBytes, StandardCharsets.ISO_8859_1).substring(NumberConstants.NUM0, NumberConstants.NUM2);
						
			// Se comprueba si es zip
			if (FileUtils.ZIP_EXTENSION.equals(extension) && FileUtils.ZIP_ISO8859_1_SIGNATURE.equalsIgnoreCase(isoZipSignature) && FileUtils.isZipType(file.getContentType())) {
				
				esZip = true;
			} 
			
		} catch (IOException e) {
			
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB011, new Object[ ] { file.getOriginalFilename() }), e.getCause());
		}
						
		return esZip;
		
	}
		
	
	/**
	 * Method that copy to the response the contents of the file requested.
	 * @param idFile Identifier of the file to download
	 * @param response HttpServletResponse
	 * @throws IOException Exception launched if there is an error copying the file to the response 
	 * @throws RequestFileNotFoundException Exception launched if there is no file in persistence with the passed identifier
	 */
	@RequestMapping(value = "/downloadFile", produces = "application/zip")
	public void downloadFile(@RequestParam("idFile") Long idFile, HttpServletResponse response) throws IOException, RequestFileNotFoundException {
				
		final RequestServiceFile file = fileService.getRequestFileById(idFile);
		
		String fileName = "attachment; filename=" + file.getFilename();
		
		response.setHeader("Content-Disposition", fileName);
		response.setContentType(file.getContentType());
		response.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
		FileCopyUtils.copy(file.getFiledata(), response.getOutputStream());
		response.flushBuffer();
	} 
		
}
