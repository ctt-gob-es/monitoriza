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
 * <b>File:</b><p>es.gob.monitoriza.controller.ValidationServiceController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>28 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 11/05/2022.
 */
package es.gob.monitoriza.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.StringUtils;
import es.gob.monitoriza.utilidades.loggers.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.cron.ValidCertificatesJob;
import es.gob.monitoriza.enums.AuthenticationTypeEnum;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ValidServiceDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAuthenticationTypeService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.IValidServiceService;
import es.gob.monitoriza.utilidades.UtilsScheduler;

/** 
 * <p>Class that maps the request for the validation service form to the controller.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 11/05/2022.
 */
@Controller
public class ValidationServiceController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * authentication type repository.
	 */
	@Autowired
	private IAuthenticationTypeService authenticationTypeService;

	/**
	 * Attribute that represents the service object for accessing the
	 * valid service repository.
	 */
	@Autowired
	private IValidServiceService validServiceService;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ValidCertificatesJob validCertificatesJob;

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "validservicecertificate",  method = RequestMethod.GET)
	public String validService(Model model) {

		ValidServiceDTO validService = new ValidServiceDTO();

		model.addAttribute("authenticationTypes", authenticationTypeService.getAllAuthenticationTypes());

		List<ValidService> validServices = validServiceService.getAllValidServices();
		if (!validServices.isEmpty()) {
			validService.setIdValidService(validServices.get(0).getIdValidService());
			validService.setApplication(validServices.get(0).getApplication());
			validService.setAuthenticationType(validServices.get(0).getAuthenticationType() != null ? validServices.get(0).getAuthenticationType().getIdAuthenticationType() : -1);
			validService.setHost(validServices.get(0).getHost());
			validService.setIsSecure(validServices.get(0).getIsSecure());
			validService.setIsEnableValidationJob(validServices.get(0).getIsEnableValidationJob());
			validService.setCronExpression(validServices.get(0).getCronExpression());
			validService.setPass(validServices.get(0).getPass());
			validService.setPort(validServices.get(0).getPort());
			validService.setUser(validServices.get(0).getUser());
			validService.setValidServiceCertificate(validServices.get(0).getValidServiceCertificate() != null ? validServices.get(0).getValidServiceCertificate().getIdSystemCertificate() : -1);
		}
		model.addAttribute("validServiceForm", validService);

		List<SystemCertificate> listCertificates = StreamSupport.stream(sysCertService.getAllValidService().spliterator(), false).collect(Collectors.toList());

		model.addAttribute("validservicecerts", listCertificates);

		return "fragments/validservicecertificate.html";
	}

	/**
	 * Method that stores a ValidService in persistence.
	 * @param validServiceForm Object that maps the HTML form
	 * @param bindingResult Object that maps the validation errors in the form
	 * @return ValidService persisted
	 * @throws Exception Error saving the validation service
	 */
	@RequestMapping(value = "/savevalidservice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ValidServiceDTO saveValidService(@Validated(OrderedValidation.class) @RequestBody final ValidServiceDTO validServiceForm, final BindingResult bindingResult) throws Exception{

		ValidService validService = null;
		ValidServiceDTO validServiceDto = new ValidServiceDTO();
		
		
        if (!UtilsScheduler.validExpression(validServiceForm.getCronExpression())) {
            LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB029));
            
            final FieldError cronFieldError = new FieldError(ValidServiceDTO.FORM_OBJECT_VALUE, ValidServiceDTO.FIELD_CRON, "El formato del campo 'Hora de ejecución' no es correcto");
			bindingResult.addError(cronFieldError);   
			JSONObject json = new JSONObject();
			json.put(ValidServiceDTO.FIELD_CRON + "_span", "Formato incorrecto");
			validServiceDto.setError(json.toString());
        }
        
		if (!bindingResult.hasErrors()) {
			try {
				
				if (validServiceForm.getAuthenticationType().equals(AuthenticationTypeEnum.CERTIFICATE.getId())) {
					if (validServiceForm.getValidServiceCertificate().intValue() == -1) {
						LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB010));
						throw new Exception("NoCertForAuthentication");
					}
				}
								
				validService = validServiceService.saveValidService(validServiceForm);
				
				// Si todo va bien, se ejecuta el job
				if (!StringUtils.equals(validService.getCronExpression(), validServiceForm.getCronExpression()) && validServiceForm.getIsEnableValidationJob()) {
					validCertificatesJob.start();
				}				

			} catch (Exception e) {
				LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB010), e.getCause());
				throw e;
			}
		}
		
		return validServiceDto;
	}

	/**
	 * Get authenticationTypeService.
	 * @return authenticationTypeService
	 */
	public IAuthenticationTypeService getAuthenticationTypeService() {
		return authenticationTypeService;
	}

	/**
	 * Set authenticationTypeService.
	 * @param authenticationTypeServiceParam set authenticationTypeService
	 */
	public void setAuthenticationTypeService(IAuthenticationTypeService authenticationTypeServiceParam) {
		this.authenticationTypeService = authenticationTypeServiceParam;
	}

	/**
	 * Get validServiceService.
	 * @return validServiceService
	 */
	public IValidServiceService getValidServiceService() {
		return validServiceService;
	}

	/**
	 * Set validServiceService.
	 * @param validServiceServiceParam set validServiceService
	 */
	public void setValidServiceService(IValidServiceService validServiceServiceParam) {
		this.validServiceService = validServiceServiceParam;
	}

	/**
	 * Get sysCertService.
	 * @return sysCertService
	 */
	public ISystemCertificateService getSysCertService() {
		return sysCertService;
	}

	/**
	 * Set sysCertService.
	 * @param sysCertServiceParam set sysCertService
	 */
	public void setSysCertService(ISystemCertificateService sysCertServiceParam) {
		this.sysCertService = sysCertServiceParam;
	}

	/**
	 * Get validCertificatesJob.
	 * @return validCertificatesJob
	 */
	public ValidCertificatesJob getValidCertificatesJob() {
		return validCertificatesJob;
	}

	/**
	 * Set validCertificatesJob.
	 * @param validCertificatesJobParam set validCertificatesJob
	 */
	public void setValidCertificatesJob(ValidCertificatesJob validCertificatesJobParam) {
		this.validCertificatesJob = validCertificatesJobParam;
	}

}

