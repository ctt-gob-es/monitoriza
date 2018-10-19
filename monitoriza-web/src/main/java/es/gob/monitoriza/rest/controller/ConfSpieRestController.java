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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.ConfSpieRestController.java.</p>
 * <b>Description:</b><p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.rest.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.gob.monitoriza.form.ConfSpieForm;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.service.IConfSpieService;
import es.gob.monitoriza.service.IMethodValidationService;

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
 * @version 1.0, 16/10/2018.
 */
@RestController
public class ConfSpieRestController {

	/**
	 * Attribute that represents the service object for accessing the
	 * ConfSpieRespository.
	 */
	@Autowired
	private IConfSpieService confSpieService;

	/**
	 * Attribute that represents the service object for accessing the
	 * MethodValidationRespository.
	 */
	@Autowired
	private IMethodValidationService methodValidationService;

	/**
	 * Method that maps the save configuration of server mail web request to the
	 * controller and saves it in the persistence.
	 *
	 * @param confServerMailForm
	 *            Object that represents the backing configuration server mail form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link ConfServerMail}
	 */
	@RequestMapping(value = "/saveconfspie", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ConfSpie save(@RequestBody ConfSpieForm confSpieForm, BindingResult bindingResult) {
		ConfSpie confSpie, result = new ConfSpie();

		if (bindingResult.hasErrors()) {
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
		} else {
			try {
				if (confSpieForm.getIdConfSpie() != null) {
					confSpie = confSpieService.getConfSpieById(confSpieForm.getIdConfSpie());
				} else {
					confSpie = new ConfSpie();
				}

				confSpie.setPercentAccept(confSpieForm.getPercentAccept());
				confSpie.setFrequencyAfirma(confSpieForm.getFrequencyAFirma());
				confSpie.setFrequencyTsa(confSpieForm.getFrequencyTsa());

				result = confSpieService.saveConfSpie(confSpie);
				methodValidationService.createAllMethods(confSpieForm.getMethodValidations(), result);
			} catch (Exception e) {
				throw e;
			}
		}

		return result;
	}

}
