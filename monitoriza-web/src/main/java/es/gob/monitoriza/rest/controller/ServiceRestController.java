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
 * @version 2.2, 11/05/2022.
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
import javax.validation.constraints.NotEmpty;

import es.gob.monitoriza.utilidades.loggers.Logger;
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
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
  * @version 2.2, 11/05/2022.
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
	public DataTablesOutput<ServiceMonitoriza> services(@NotEmpty final DataTablesInput input) {
		return this.serviceService.findAll(input);
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
	public DataTablesOutput<TimerMonitoriza> timers(@NotEmpty final DataTablesInput input) {
		return this.timerService.findAll(input);
	}

	/**
	 * Method that maps the request to get the service types for the selected
	 * platform type. to the view.
	 *
	 * @param idPlatform Platform identifier
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path = "/loadservicetype", method = RequestMethod.GET)
	public List<String> loadservicetype(@RequestParam("idPlatform") final Long idPlatform) {

		final PlatformMonitoriza platform = this.platformService.getPlatformById(idPlatform);
		final List<String> serviceTypes = new ArrayList<>();

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
	public Map<String, String> loadbaseendpoint(@RequestParam("idPlatform") final Long idPlatform,
			@RequestParam("serviceType") final String serviceType) {

		final PlatformMonitoriza platform = this.platformService.getPlatformById(idPlatform);
		final StringBuilder baseEndpoint = new StringBuilder();
		
		String protocol = "http://";
		
		if (platform.getIsSecure()) {
			protocol = "https://";
		}
		
		baseEndpoint.append(protocol).append(platform.getHost()).append(UtilsStringChar.SYMBOL_COLON_STRING)
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
	public @ResponseBody DataTablesOutput<TimerMonitoriza> saveTimer(
			@Validated(OrderedValidation.class) @RequestBody final TimerDTO timerForm, final BindingResult bindingResult) {
		final DataTablesOutput<TimerMonitoriza> dtOutput = new DataTablesOutput<>();

		List<TimerMonitoriza> listNewTimer = new ArrayList<>();

		if (bindingResult.hasErrors()) {
			listNewTimer = StreamSupport.stream(this.timerService.getAllTimerMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			final JSONObject json = new JSONObject();
			for (final FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "Timer_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {

				final TimerMonitoriza timerMonitoriza = this.timerService.saveTimerMonitoriza(timerForm);

				listNewTimer.add(timerMonitoriza);

			} catch (final Exception e) {
				listNewTimer = StreamSupport.stream(this.timerService.getAllTimerMonitoriza().spliterator(), false)
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
	public @ResponseBody DataTablesOutput<ServiceMonitoriza> saveService(
			@Validated(OrderedValidation.class) @RequestPart("serviceForm") final ServiceDTO serviceForm, @RequestPart("file") final MultipartFile file, final BindingResult bindingResult) {

		final DataTablesOutput<ServiceMonitoriza> dtOutput = new DataTablesOutput<>();
		List<ServiceMonitoriza> listNewService = new ArrayList<>();

		// Se realizan validaciones en servidor, modificando el objecto bindingResult.
		validateRequiredNameWsdlField(serviceForm, bindingResult);
		validateZipFile(serviceForm, file, bindingResult);
		validateTemporal(serviceForm, bindingResult);

		if (bindingResult.hasErrors()) {
			listNewService = StreamSupport.stream(this.serviceService.getAllServiceMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			final JSONObject json = new JSONObject();
			for (final FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());

		} else {
			try {

				// Se almacena el servicio y se comprueba si es necesario actualizar el timer programado
				final ServiceMonitoriza service = this.serviceService.saveServiceMonitoriza(serviceForm, file);

				listNewService.add(service);

			} catch (final Exception e) {
				listNewService = StreamSupport.stream(this.serviceService.getAllServiceMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				dtOutput.setError(GeneralConstants.ROW_INDEX_ERROR);
			}
		}

		dtOutput.setData(listNewService);

		return dtOutput;
	}
	

	/**
	 * Modifies the {@link BindingResult} validating the nameWsdl field.
	 * @param serviceForm Backing form object with the service data.
	 * @param bindingResult Validation binding object.
	 */
	private void validateRequiredNameWsdlField(final ServiceDTO serviceForm, final BindingResult bindingResult) {
		// Se controla manualmente el error 'requerido' para el campo nameWsdl,
		// ya que depende del tipo de servicio
		if (serviceForm.getServiceType().equalsIgnoreCase(GeneralConstants.SOAP_SERVICE)) {

			if (UtilsStringChar.EMPTY_STRING.equals(serviceForm.getNameWsdl())) {
				final FieldError wsdlFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_ENDPOINT, "El campo 'Endpoint' es obligatorio.");
				bindingResult.addError(wsdlFieldError);
			}

			if (serviceForm.getNameWsdl() != null && (serviceForm.getNameWsdl().length() < 1 || serviceForm.getNameWsdl().length() > NumberConstants.NUM30)) {
				final FieldError wsdlFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_ENDPOINT, "El tamaño debe estar entre 1 y 30.");
				bindingResult.addError(wsdlFieldError);
			}

		}
	}
	
	/**
	 * Modifies the {@link BindingResult} validating the temporal fields: timeout, uDegraded and uLost.
	 * @param serviceForm Backing form object with the service data.
	 * @param bindingResult Validation binding object.
	 */
	private void validateTemporal(ServiceDTO serviceForm, BindingResult bindingResult) {
		
		if (serviceForm.getDegradedThreshold() != null && !serviceForm.getDegradedThreshold().isEmpty() && !UtilsStringChar.isPositiveNumber(serviceForm.getDegradedThreshold())) {
			final FieldError uDegradedFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_UDEGRADED, "El campo 'Umbral degradado' deber ser un valor numérico positivo.");
			bindingResult.addError(uDegradedFieldError);
		}
		
		if (serviceForm.getLostThreshold() != null && !serviceForm.getLostThreshold().isEmpty() && !UtilsStringChar.isPositiveNumber(serviceForm.getLostThreshold())) {
			final FieldError uLostFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_ULOST, "El campo 'Umbral perdido' deber ser un valor numérico positivo.");
			bindingResult.addError(uLostFieldError);
		}
		
		if (serviceForm.getTimeout() != null && !serviceForm.getTimeout().isEmpty() && !UtilsStringChar.isPositiveNumber(serviceForm.getTimeout())) {
			final FieldError timeoutFieldError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_TIMEOUT, "El campo 'timeout' deber ser un valor numérico positivo.");
			bindingResult.addError(timeoutFieldError);
		}
		
	}


	/**
	 * Modifies the {@link BindingResult} validating the file field.
	 * @param serviceForm Backing form object with the service data.
	 * @param file ZIP file with requests for this service
	 * @param bindingResult Validation binding object.
	 */
	private void validateZipFile(final ServiceDTO serviceForm, final MultipartFile file, final BindingResult bindingResult) {

		// Al ser file un campo individual de la petición, se controla la
		// validación por separado.
		// En modo edición, existe un identificador de fichero, pero se manda un
		// fichero vacío por
		// requisitos de la petición (no acepta null) / Error: "Required request
		// part 'file' is not present".
		if ((file == null || file.isEmpty()) && serviceForm.getIdFile() == null) {

			final FieldError fileNullError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_FILE, "El campo 'Archivo de peticiones' es obligatorio.");
			bindingResult.addError(fileNullError);

		} else if (!file.isEmpty() && !checkAllowedFormat(file)) {

			final FieldError fileTypeError = new FieldError(ServiceDTO.FORM_OBJECT_VALUE, ServiceDTO.FIELD_FILE, "El archivo de peticiones debe tener formato ZIP.");
			bindingResult.addError(fileTypeError);
		}

	}

	/**
	 * Method that deletes a timer from persistence and updates the scheduled timers.
	 * @param timerId Identifier of the timer to be deleted
	 * @param index Index of the row in the data table
	 * @return The index of the row (timer) to be deleted, -1 in case of error.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletetimer", method = RequestMethod.POST)
	public String deleteTimer(final @RequestParam("id") Long timerId, final @RequestParam("index") String index) {

		String indexResult = index;

		try {
			this.timerService.deleteTimerMonitoriza(timerId);

		} catch (final Exception e) {
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
	public String deleteservice(@RequestParam("id") final Long idService, @RequestParam("index") final String index) {

		String indexResult = index;

		try {

			this.serviceService.deleteServiceMonitoriza(idService);

		} catch (final EmptyResultDataAccessException e) {
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

		} catch (final IOException e) {

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
	@RequestMapping(value = "/downloadFile", produces = "application/zip", method = RequestMethod.POST)
	public void downloadFile(@RequestParam("idFile") final Long idFile, final HttpServletResponse response) throws IOException, RequestFileNotFoundException {

		final RequestServiceFile file = this.fileService.getRequestFileById(idFile);

		final String fileName = "attachment; filename=" + file.getFilename();

		response.setHeader("Content-Disposition", fileName);
		response.setContentType(file.getContentType());
		response.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
		FileCopyUtils.copy(file.getFiledata(), response.getOutputStream());
		response.flushBuffer();
	}
}
