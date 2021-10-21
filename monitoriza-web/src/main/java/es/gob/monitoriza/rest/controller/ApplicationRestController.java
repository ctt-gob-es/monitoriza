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
 * <b>File:</b><p>es.gob.monitoriza.controller.UserRestController.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>21/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 14/03/2019.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotEmpty;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ApplicationDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IApplicationMonitorizaService;

/**
 * <p>Class that manages the REST requests related to the Users administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.7, 14/03/2019.
 */
@RestController
public class ApplicationRestController {

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IApplicationMonitorizaService applicationService;

	/**
	 * Attribute that represents the span text.
	 */
	private static final String SPAN = "_span"; //$NON-NLS-1$

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constant that represents the key Json 'errorSaveAlertSystem'.
	 */
	private static final String KEY_JS_ERROR_APPLICATION = "errorSaveApplication"; //$NON-NLS-1$

	/**
	 * Method that maps the list templates web requests to the controller and
	 * forwards the list of templates to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/applicationsdatatable", method = RequestMethod.GET)
	public DataTablesOutput<ApplicationMonitoriza> applications(@NotEmpty final DataTablesInput input) {
		return this.applicationService.findAll(input);
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 *
	 * @param applicationForm
	 *            Object that represents the backing user form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<ApplicationMonitoriza>}
	 */
	@RequestMapping(value = "/saveapplication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<ApplicationMonitoriza> save(@Validated(OrderedValidation.class) @RequestBody final ApplicationDTO applicationForm, final BindingResult bindingResult) {
		final DataTablesOutput<ApplicationMonitoriza> dtOutput = new DataTablesOutput<ApplicationMonitoriza>();
		List<ApplicationMonitoriza> listNewApplication = new ArrayList<ApplicationMonitoriza>();
		final JSONObject json = new JSONObject();

		if (bindingResult.hasErrors()) {
			listNewApplication = StreamSupport.stream(this.applicationService.getAllApplicationMonitoriza().spliterator(), false).collect(Collectors.toList());
			for (final FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				final ApplicationMonitoriza application = this.applicationService.saveApplicationMonitoriza(applicationForm);

				listNewApplication.add(application);
			} catch (final Exception e) {
				LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022), e);
				listNewApplication = StreamSupport.stream(this.applicationService.getAllApplicationMonitoriza().spliterator(), false).collect(Collectors.toList());
				json.put(KEY_JS_ERROR_APPLICATION, Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022));
				dtOutput.setError(json.toString());
			}
		}

		dtOutput.setData(listNewApplication);

		return dtOutput;
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
	@RequestMapping(path = "/deleteapplication", method = RequestMethod.POST)
	@Transactional
	public String deleteApplication(@RequestParam("id") final Long appId, @RequestParam("index") final String index) {

		this.applicationService.deleteApplicationMonitoriza(appId);

		return index;
	}

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alertsfromapplication", method = RequestMethod.POST)
	@Transactional
    public List<AlertConfigMonitoriza> getAlertsFromApplication(@RequestParam("id") final Long applicationId, final Model model){

		final ApplicationMonitoriza application = this.applicationService.getApplicationMonitorizaById(applicationId);

		return application.getAlertConfigMonitoriza();
    }


}