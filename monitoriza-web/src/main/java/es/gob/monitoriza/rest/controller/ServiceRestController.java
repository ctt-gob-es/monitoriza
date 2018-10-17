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
 * @version 1.5, 17/10/2018.
 */
package es.gob.monitoriza.rest.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
import es.gob.monitoriza.exception.RequestFileNotFoundException;
import es.gob.monitoriza.form.ServiceForm;
import es.gob.monitoriza.form.TimerForm;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.IRequestServiceFileService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;
import es.gob.monitoriza.service.ITimerScheduledService;
import es.gob.monitoriza.utilidades.FileUtils;
import es.gob.monitoriza.utilidades.NumberConstants;

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
 * @version 1.5, 17/10/2018.
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
	private ITimerScheduledService scheduledService;

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
	private IAlarmMonitorizaService alarmService;
	
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
	 * @param model
	 *            Holder object for model attributes.
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
	 * @param timerForm Object that represents the backing timer form.
	 * @param bindingResult Object that represents the form validation result.
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
								
				// Sólo se actualiza la tarea programada del timer, si éste exsistía ya.
				// Un nuevo timer sin servicios no debe ser contemplado.
				if (timerForm.getIdTimer() != null) {
					// Actualizar en bd (tabla TIMER_SCHEDULED) el timer poniendo IS_UPDATED a false
					TimerScheduled scheduled = scheduledService.getTimerScheduledByIdTimer(timerForm.getIdTimer());
					
					if (scheduled != null) {
    					scheduled.setUpdated(false);
    					scheduledService.saveTimerScheduled(scheduled);
					}
				} 
				
				listNewTimer.add(timer);
			} catch (Exception e) {
				listNewTimer = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				dtOutput.setError("-1");
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
	 * @param bindingResult Validation binding object.
	 * @return {@link DataTablesOutput<ServiceMonitoriza>}
	 */
	@RequestMapping(path = "/saveservice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<ServiceMonitoriza> saveService(
			@Validated(OrderedValidation.class) @RequestPart("serviceForm") ServiceForm serviceForm, @RequestPart("file") MultipartFile file, BindingResult bindingResult) {
		
		DataTablesOutput<ServiceMonitoriza> dtOutput = new DataTablesOutput<>();
		ServiceMonitoriza serviceMonitoriza = null;
		List<ServiceMonitoriza> listNewService = new ArrayList<ServiceMonitoriza>();
		boolean elServicioHaCambiado = false;
		boolean nuevoTimerSeleccionado = false;
		TimerMonitoriza nuevoTimer = null;
		RequestServiceFile requestFile = new RequestServiceFile();
		serviceForm.setFile(file);
		
		// Se controla manualmente el error 'requerido' para el campo nameWsdl, ya que depende del tipo de servicio
		if (serviceForm.getServiceType().equalsIgnoreCase(GeneralConstants.SOAP_SERVICE)) {
			
			if ("".equals(serviceForm.getNameWsdl())) {
				FieldError wsdlFieldError = new FieldError(ServiceForm.FORM_OBJECT_VALUE, ServiceForm.FIELD_ENDPOINT, "El campo 'Endpoint' es obligatorio.");
				bindingResult.addError(wsdlFieldError);
			}
			
			if (serviceForm.getNameWsdl() != null && (serviceForm.getNameWsdl().length() < 1 || serviceForm.getNameWsdl().length() > 30)) {
				FieldError wsdlFieldError = new FieldError(ServiceForm.FORM_OBJECT_VALUE, ServiceForm.FIELD_ENDPOINT, "El tamaño debe estar entre 1 y 30.");
				bindingResult.addError(wsdlFieldError);
			}
			
		}
		
		// Al ser file un campo individual de la petición, se controla la validación por separado. 
		// En modo edición, existe un identificador de fichero, pero se manda un fichero vacío por
		// requisitos de la petición (no acepta null) / Error: "Required request part 'file' is not present".
		if ((file == null || file.isEmpty()) && serviceForm.getIdFile() == null) {
			
			FieldError fileNullError = new FieldError(ServiceForm.FORM_OBJECT_VALUE, ServiceForm.FIELD_FILE, "El campo 'Archivo de peticiones' es obligatorio.");
			bindingResult.addError(fileNullError);
			
		} else if (!file.isEmpty() && !checkAllowedFormat(file)) {
			
			FieldError fileTypeError = new FieldError(ServiceForm.FORM_OBJECT_VALUE, ServiceForm.FIELD_FILE, "El archivo de peticiones debe tener formato ZIP.");
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
				
				// Si es una edición, se recupera el servicio original de la persistencia
				// para comprobar si existen cambios respecto a los datos del formulario.
				if (serviceForm.getIdService() != null) {
					serviceMonitoriza = serviceService.getServiceMonitorizaById(serviceForm.getIdService());
					elServicioHaCambiado = isServiceUpdatedForm(serviceForm, serviceMonitoriza);
					
					// Si se ha seleccionado un nuevo timer, habrá que reprogamar el original y el nuevo.
					nuevoTimerSeleccionado = !serviceForm.getTimer().equals(serviceMonitoriza.getTimer().getIdTimer());
					
					if (nuevoTimerSeleccionado) {
						nuevoTimer = serviceMonitoriza.getTimer();
					}
					
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
				
				// Se añade/modifica el fichero de peticiones
				if (file != null && !file.isEmpty()) {
					
					requestFile.setIdRequestServiceFile(serviceForm.getIdFile());
					requestFile.setFilename(file.getOriginalFilename());
					requestFile.setContentType(file.getContentType());
					requestFile.setFiledata(file.getBytes());
					serviceMonitoriza.setRequestFile(requestFile);
				}
							
				ServiceMonitoriza service = serviceService.saveServiceMonitoriza(serviceMonitoriza);
							
				listNewService.add(service);	
						
				// Si el servicio ha cambiado o es nuevo, hay que gestionar la programación de timer asociado
				if (elServicioHaCambiado || serviceMonitoriza.getIdService() == null) {
					
    				// Actualizar en bd (tabla TIMER_SCHEDULED) el timer poniendo IS_UPDATED a false
    				TimerScheduled scheduled = scheduledService.getTimerScheduledByIdTimer(serviceForm.getTimer());
    				
    				// Si el timer asociado aún no ha sido programado, se añade para que la tarea programada lo haga.
    				if (scheduled == null) {
    					scheduled = new TimerScheduled();
    					scheduled.setTimer(timerService.getTimerMonitorizaById(serviceForm.getTimer()));
    				}
    				
    				// En cualquier caso, se pondrá la bandera a false para que la tarea lo procese
    				scheduled.setUpdated(false);
    				scheduledService.saveTimerScheduled(scheduled);    			
    
    				// Si además lo que se ha cambiado es el timer asociado al servicio, hay que actualizar el nuevo timer seleccionado y el antiguo.
    				if (nuevoTimerSeleccionado) {
    					
    					TimerScheduled scheduledOld = scheduledService.getTimerScheduledByIdTimer(nuevoTimer.getIdTimer());
    
    					// Si el antiguo timer nunca había sido programado, se ignora el cambio
    					if (scheduledOld != null) {
    						scheduledOld.setUpdated(false);
    						scheduledService.saveTimerScheduled(scheduledOld);
    					}
    				}
				}			
					
			} catch (Exception e) {
				listNewService = StreamSupport.stream(serviceService.getAllServiceMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				dtOutput.setError("-1");
			}
		}
		
		dtOutput.setData(listNewService);

		return dtOutput;
	}


	/**
	 * Method that deletes a timer from persistence
	 * @param timerId Identifier of the timer to be deleted
	 * @param index Index of the row in the data table
	 * @return The index of the row (timer) to be deleted, -1 in case of error.
	 */
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

	/**
	 * Method that deletes a service from persistence
	 * @param idService Identifier of the service to be deleted
	 * @param index Index of the row in the data table
	 * @return The index of the row (service) to be deleted, -1 in case of error.
	 */
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
	
	/**
	 * Method that checks if there are changes between the service form and the persisted service.
	 * @param serviceForm Form object for the service.
	 * @param service Entity object for the service.
	 * @return true if there are changes between the service form and the persisted service.
	 * @throws IOException 
	 */
	private boolean isServiceUpdatedForm(final ServiceForm serviceForm, final ServiceMonitoriza service) throws IOException {
		
		boolean filesAreEquals = serviceForm.getFile().isEmpty() || Arrays.equals(serviceForm.getFile().getBytes(), service.getRequestFile().getFiledata());
						
		return !(serviceForm.getAlarm().equals(service.getAlarm().getIdAlarm()) 
				&& serviceForm.getDegradedThreshold().equals(service.getDegradedThreshold())
				&& serviceForm.getLostThreshold().equals(service.getLostThreshold())
				&& serviceForm.getName().equals(service.getName())
				&& serviceForm.getNameWsdl().equals(service.getNameWsdl())
				&& serviceForm.getPlatform().equals(service.getPlatform().getIdPlatform())
				&& serviceForm.getServiceType().equals(service.getServiceType())
				&& serviceForm.getTimeout().equals(service.getTimeout())
				&& serviceForm.getTimer().equals(service.getTimer().getIdTimer())
				&& filesAreEquals);
		
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
	 * Method that copy to the response the contents of the file requested
	 * @param idFile Identifier of the file to download
	 * @param response HttpServletResponse
	 * @throws IOException
	 * @throws RequestFileNotFoundException
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
