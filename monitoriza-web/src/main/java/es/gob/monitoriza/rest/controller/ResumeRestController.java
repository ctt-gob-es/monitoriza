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
import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailResumeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlertMailResumeConfigService;
import es.gob.monitoriza.service.IAlertResumeSystemService;
import es.gob.monitoriza.service.IAlertResumeTypeService;
import es.gob.monitoriza.service.IAlertSystemMonitorizaService;
import es.gob.monitoriza.service.IAlertTypeMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.service.IResumeMonitorizaService;

/**
 * <p>Class that manages the REST requests related to the Users administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/11/2021.
 */

@RestController
public class ResumeRestController {

	@Autowired
	private IResumeMonitorizaService resumeService;

	@Autowired
	private IApplicationMonitorizaService applicationService;

	@Autowired
	private IAlertTypeMonitorizaService alertTypeService;

	@Autowired
	private IAlertResumeTypeService alertResumeTypeService;

	@Autowired
	private IAlertSystemMonitorizaService alertSystemService;

	@Autowired
	private IAlertResumeSystemService alertResumeSystemService;

	@Autowired
	private IAlertMailResumeConfigService alertMailResumeConfigService;

	/**
	 * Attribute that represents the span text.
	 */
	private static final String SPAN = "_span"; //$NON-NLS-1$

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constant that represents the key Json 'errorSaveResume'.
	 */
	private static final String KEY_JS_ERROR_RESUME = "errorSaveResume"; //$NON-NLS-1$

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 *
	 * @param resumeForm Object that represents the backing resume form.
	 * @param bindingResult Object that represents the form validation result.
	 * @return {@link DataTablesOutput<ResumeMonitoriza>}
	 */
	@RequestMapping(value = "/saveresume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<ResumeMonitoriza> save(@Validated(OrderedValidation.class) @RequestBody final ResumeDTO resumeForm, final BindingResult bindingResult) {

		final DataTablesOutput<ResumeMonitoriza> dtOutput = new DataTablesOutput<ResumeMonitoriza>();
		List<ResumeMonitoriza> listNewResume = new ArrayList<ResumeMonitoriza>();
		final JSONObject json = new JSONObject();

		if (bindingResult.hasErrors()) {
			listNewResume = StreamSupport.stream(this.resumeService.getAllResumeMonitoriza().spliterator(), false).collect(Collectors.toList());
			for (final FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {

				final ResumeMonitoriza resume = this.resumeService.saveResumeMonitoriza(resumeForm);

				// Si ya existian relaciones de resumen - sistema de notificacion las eliminamos
				// para crear las nuevas indicadas en el formulario
				this.alertResumeSystemService.deleteAlertResumeSystemByResumeMonitoriza(resume);

				// Guardamos los datos en la tabla con las nuevas relaciones entre sistemas de notificacion y resumen
				for (int j = 0 ; j < resumeForm.getNotifSystemsIdArray().size() ; j++) {
					final AlertSystemMonitoriza alertSystem = this.alertSystemService.getAlertSystemMonitorizaById(resumeForm.getNotifSystemsIdArray().get(j));
					AlertResumeSystem alertResumeSystem = new AlertResumeSystem();
					alertResumeSystem.setResumeMonitoriza(resume);
					alertResumeSystem.setAlertSystemMonitoriza(alertSystem);
					alertResumeSystem = this.alertResumeSystemService.saveAlertResumeSystem(alertResumeSystem);
					if (resumeForm.getEmailConfigurationArray() != null && !resumeForm.getEmailConfigurationArray().isEmpty()) {
						saveMailResumeConfig(resumeForm.getEmailConfigurationArray().get(j) , alertResumeSystem);
					}
				}

				// Si ya existian relaciones de resumen - aplicacion / tipo de alerta
				// para crear las nuevas indicadas en el formulario
				this.alertResumeTypeService.deleteAlertResumeTypeByResumeMonitoriza(resume);

				// Guardamos los datos en la tabla con las nuevas relaciones relacion entre app, tipo de alerta y resumen
				for (int i = 0; i < resumeForm.getApplicationsIdArray().size() ; i++) {
					final ApplicationMonitoriza appMonit = this.applicationService.getApplicationMonitorizaById(resumeForm.getApplicationsIdArray().get(i));
					final AlertTypeMonitoriza alertTypeMon = this.alertTypeService.getAlertTypeMonitorizaById(resumeForm.getAlertsTypesIdArray().get(i));
					final AlertResumeType alertResumeType = new AlertResumeType();
					alertResumeType.setApplicationMonitoriza(appMonit);
					alertResumeType.setAlertTypeMonitoriza(alertTypeMon);
					alertResumeType.setResumeMonitoriza(resume);
					this.alertResumeTypeService.saveAlertResumeType(alertResumeType);
				}

				listNewResume.add(resume);

			} catch (final Exception e) {
				LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022), e);
				listNewResume = StreamSupport.stream(this.resumeService.getAllResumeMonitoriza().spliterator(), false).collect(Collectors.toList());
				json.put(KEY_JS_ERROR_RESUME, Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022));
				dtOutput.setError(json.toString());
			}
		}

		dtOutput.setData(listNewResume);

		return dtOutput;
	}

	/**
	 * Method that maps the list resumes web requests to the controller and
	 * forwards the list of templates to the view.
	 *
	 * @param input Holder object for datatable attributes.
	 * @return DataTablesOutput<ResumeMonitoriza> that represents the list of resumes.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/resumesdatatable", method = RequestMethod.GET)
	public DataTablesOutput<ResumeMonitoriza> resumes(@NotEmpty final DataTablesInput input) {
		return this.resumeService.findAll(input);
	}

	/**
	 * Method that maps the list of alert systems for a resume and
	 * forwards the list of templates to the view.
	 *
	 * @param input Holder object for datatable attributes.
	 * @param resumeId the resume id
	 * @return DataTablesOutput<AlertSystemDTO> the alert systems of a resume.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/resumealertssystemsdt", method = RequestMethod.POST)
	public DataTablesOutput<AlertSystemDTO> resumeAlertSystems(@NotEmpty final DataTablesInput input, @RequestParam("resumeId") final Long resumeId) {
		final ResumeMonitoriza resume = this.resumeService.getResumeMonitorizaById(resumeId);
		final DataTablesOutput<AlertSystemDTO> result = new DataTablesOutput<AlertSystemDTO>();
		final List<AlertSystemDTO> alertSystems = new ArrayList<AlertSystemDTO>();

		if (resume != null && resume.getAlertResumeSystem() != null) {

			for (final AlertResumeSystem alertSys : resume.getAlertResumeSystem()) {
				final AlertSystemDTO sysDTO = new AlertSystemDTO();
				sysDTO.setIdAlertSystemMonitoriza(alertSys.getAlertSystemMonitoriza().getIdAlertSystemMonitoriza());
				sysDTO.setName(alertSys.getAlertSystemMonitoriza().getName());
				String resumeMailAddresses = ""; //$NON-NLS-1$
				if (alertSys.getAlertMailsResumeConfig() != null) {

					for(final AlertMailResumeConfig mailResConf : alertSys.getAlertMailsResumeConfig()) {
						resumeMailAddresses += mailResConf.getMail() + "\n"; //$NON-NLS-1$
					}
				}
				sysDTO.setResumeEmailAddresses(resumeMailAddresses);
				alertSystems.add(sysDTO);
			}
		}
		result.setData(alertSystems);
		return result;
	}

	/**
	 * Method that maps the list of applications and alert types for a resume and
	 * forwards the list of templates to the view.
	 *
	 * @param input Holder object for datatable attributes.
	 * @param resumeId the resume id
	 * @return DataTablesOutput<AlertResumeType> that represents the alert resume types.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/resumeappsalerttypessdt", method = RequestMethod.POST)
	public DataTablesOutput<AlertResumeType> resumeAppsAndAlertTypes(@NotEmpty final DataTablesInput input, @RequestParam("resumeId") final Long resumeId) {
		final ResumeMonitoriza resume = this.resumeService.getResumeMonitorizaById(resumeId);
		final DataTablesOutput<AlertResumeType> result = new DataTablesOutput<AlertResumeType>();
		if (resume != null && !resume.getResumeTypes().isEmpty()) {
			result.setData(new ArrayList<>(resume.getResumeTypes()));
		}
		return result;
	}

	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the resume identified by its id.
	 *
	 * @param resumeId Identifier of the resume to be deleted.
	 * @param index Row index of the datatable.
	 * @return String that represents the deleted index.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteresume", method = RequestMethod.POST)
	@Transactional
	public String deleteResume(@RequestParam("id") final Long resumeId, @RequestParam("index") final String index) {

		final ResumeMonitoriza resume = this.resumeService.getResumeMonitorizaById(resumeId);

		// Si ya existian relaciones de resumen - configuracion de email se eliminan
		if (resume.getAlertResumeSystem() != null && !resume.getAlertResumeSystem().isEmpty()) {
			//Se itera con las relaciones con la tabla ALERT_RESUME_SYSTEMS
			for (final AlertResumeSystem alertResSys : resume.getAlertResumeSystem()) {
				//Se itera con las relaciones de la tabla ALERT_MAIL_RESUME_CONFIG para eliminarlas si existieran
				for(final AlertMailResumeConfig alertMailRes : alertResSys.getAlertMailsResumeConfig()) {
					this.alertMailResumeConfigService.deleteAlertMailResumeConfig(alertMailRes);
				}
			}
		}

		// Si ya existian relaciones de resumen - aplicacion / tipo de alerta se eliminan
		if (resume.getResumeTypes() != null && !resume.getResumeTypes().isEmpty()) {
			this.alertResumeTypeService.deleteAlertResumeTypeByResumeMonitoriza(resume);
		}

		this.resumeService.deleteResumeMonitoriza(resumeId);

		return index;
	}

	/**
	 * Method that save a AlertMailResumeConfig in the database.
	 * @param emailConfigurationArray Mails to be saved.
	 * @param alertResumeSystem AlertResumeSystem of the mails.
	 */
	private void saveMailResumeConfig(final List<String> emailConfigurationArray, final AlertResumeSystem alertResumeSystem) {
		for (int i = 0; i < emailConfigurationArray.size() ; i++) {
			if (emailConfigurationArray.get(i) != null && !emailConfigurationArray.get(i).isEmpty()) {
					final AlertMailResumeConfig mailResumeConfig = new AlertMailResumeConfig();
					mailResumeConfig.setResSysConfigId(alertResumeSystem.getIdResSystem());
					mailResumeConfig.setMail(emailConfigurationArray.get(i));
					this.alertMailResumeConfigService.saveAlertMailResumeConfig(mailResumeConfig);
			}
		}
	}
}