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
 * @version 1.1, 10/01/2022.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotEmpty;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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
import es.gob.monitoriza.constant.INotificationSystemTypes;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.AlertConfigDTO;
import es.gob.monitoriza.persistence.configuration.dto.AlertConfigSystemDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;
import es.gob.monitoriza.service.IAlertConfigSystemService;
import es.gob.monitoriza.service.IAlertGrayLogNoticeConfigService;
import es.gob.monitoriza.service.IAlertMailNoticeConfigService;
import es.gob.monitoriza.service.IAlertSystemMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;

/**
 * <p>Class that manages the REST requests related to the alert configurations administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 10/01/2022.
 */
@RestController
public class AlertConfigRestController {

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IApplicationMonitorizaService applicationService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertConfigMonitorizaService alertConfigService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertSystemMonitorizaService alertSystemMonitorizaService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertConfigSystemService alertConfigSystemService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertGrayLogNoticeConfigService alertGrayLogNoticeConfigService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertMailNoticeConfigService alertMailNoticeConfigService;

	/**
	 * Attribute that represents the span text.
	 */
	private static final String SPAN = "_span"; //$NON-NLS-1$

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constant that represents the key Json 'errorSaveAlertConfig'.
	 */
	private static final String KEY_JS_ERROR_ALERT_CONFIG = "errorSaveAlertConfig"; //$NON-NLS-1$

	/**
	 * Method that maps the list  alert configurations web requests to the controller and
	 * forwards the list of alert configurations to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alertconfigsdatatable", method = RequestMethod.POST)
	public DataTablesOutput<AlertConfigMonitoriza> alertConfigs(@RequestParam("applicationId") final Long appId, @NotEmpty final DataTablesInput input) {
		final ApplicationMonitoriza appMonit = this.applicationService.getApplicationMonitorizaById(appId);
		final DataTablesOutput<AlertConfigMonitoriza> result = new DataTablesOutput<AlertConfigMonitoriza>();
		if (appMonit != null && !appMonit.getAlertConfigMonitoriza().isEmpty()) {
			final List<AlertConfigMonitoriza> alertConfigList = new ArrayList<>(appMonit.getAlertConfigMonitoriza());
			// Se ordenan alfabeticamente las alertas
			Collections.sort(alertConfigList, new Comparator<AlertConfigMonitoriza>() {
				@Override
				public int compare(final AlertConfigMonitoriza a1, final AlertConfigMonitoriza a2) {
					return a1.getAlertSeverityMonitoriza().getSeverityTypeId().compareTo(a2.getAlertSeverityMonitoriza().getSeverityTypeId());
				}
			});
			result.setData(alertConfigList);
		}
		return result;
	}

	/**
	 * Method that maps the list alert configurations web requests to the controller and
	 * forwards the list of alert configurations to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/alertconfigssystemsdt", method = RequestMethod.POST)
	public DataTablesOutput<AlertConfigSystemDTO> alertConfigSystems(@RequestParam("alertConfigId") final Long alertConfigId, @NotEmpty final DataTablesInput input) {
		final AlertConfigMonitoriza alertConfig = this.alertConfigService.getAlertConfigMonitorizaById(alertConfigId);
		final DataTablesOutput<AlertConfigSystemDTO> result = new DataTablesOutput<AlertConfigSystemDTO>();
		final List<AlertConfigSystemDTO> alertConfigSystemsList = new ArrayList<AlertConfigSystemDTO>();

		if (alertConfig != null && !alertConfig.getAlertConfigSystems().isEmpty()) {

			for (final AlertConfigSystem alertConfigSystem : alertConfig.getAlertConfigSystems()) {
				final AlertConfigSystemDTO alertConfigSystemDTO = new AlertConfigSystemDTO();
				alertConfigSystemDTO.setAlertSystemMonitoriza(alertConfigSystem.getAlertSystemMonitoriza());
				if(alertConfigSystem.getAlertMailsNoticeConfig() != null && !alertConfigSystem.getAlertMailsNoticeConfig().isEmpty()) {
					String alertMailAddresses = ""; //$NON-NLS-1$
					for(final AlertMailNoticeConfig alertMailNoticeConfig : alertConfigSystem.getAlertMailsNoticeConfig()) {

						alertMailAddresses +=  alertMailNoticeConfig.getMail() + "\n"; //$NON-NLS-1$
					}
					alertConfigSystemDTO.setResumeEmailAddresses(alertMailAddresses);
				}

				if(alertConfigSystem.getAlertGraylogNoticeConfigs() != null && !alertConfigSystem.getAlertGraylogNoticeConfigs().isEmpty()) {
					final String [] keysList = new String[alertConfigSystem.getAlertGraylogNoticeConfigs().size()];
					final String [] valuesList = new String[alertConfigSystem.getAlertGraylogNoticeConfigs().size()];
					for(int i = 0 ; i < alertConfigSystem.getAlertGraylogNoticeConfigs().size() ; i++) {
						keysList[i] = alertConfigSystem.getAlertGraylogNoticeConfigs().get(i).getPkey();
						valuesList[i] = alertConfigSystem.getAlertGraylogNoticeConfigs().get(i).getValue();
					}
					alertConfigSystemDTO.setKeysList(keysList);
					alertConfigSystemDTO.setValuesList(valuesList);
				}

				alertConfigSystemsList.add(alertConfigSystemDTO);
			}
		}
		result.setData(alertConfigSystemsList);
		return result;
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 *
	 * @param alertConfigForm
	 *            Object that represents the backing user form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<ApplicationMonitoriza>}
	 */
	@RequestMapping(value = "/savealertconfig", method = RequestMethod.POST)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<AlertConfigMonitoriza> save(@Validated(OrderedValidation.class) @RequestBody final AlertConfigDTO alertConfigForm, final BindingResult bindingResult) {
		final DataTablesOutput<AlertConfigMonitoriza> dtOutput = new DataTablesOutput<AlertConfigMonitoriza>();
		List<AlertConfigMonitoriza> listNewApplication = new ArrayList<AlertConfigMonitoriza>();
		final JSONObject json = new JSONObject();

		if (bindingResult.hasErrors()) {
			listNewApplication = StreamSupport.stream(this.alertConfigService.getAllAlertConfigMonitoriza().spliterator(), false).collect(Collectors.toList());
			for (final FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {

				final AlertConfigMonitoriza alertConfig = this.alertConfigService.saveAlertConfigMonitoriza(alertConfigForm);

				// Si ya existian relaciones de resumen - sistema de notificacion las eliminamos
				// para crear las nuevas indicadas en el formulario
				this.alertConfigSystemService.deleteAlertConfigSystemByAlertConfigMonitoriza(alertConfig);

				// Guardamos los datos en la tabla con las nuevas relaciones entre sistemas de notificacion y resumen
				for (int j = 0 ; j < alertConfigForm.getNotifSystemsIdArray().size() ; j++) {
					final AlertSystemMonitoriza alertSystem = this.alertSystemMonitorizaService.getAlertSystemMonitorizaById(Long.parseLong(alertConfigForm.getNotifSystemsIdArray().get(j)));
					AlertConfigSystem alertConfigSystem = new AlertConfigSystem();
					alertConfigSystem.setAlertConfigMonitoriza(alertConfig);
					alertConfigSystem.setAlertSystemMonitoriza(alertSystem);
					alertConfigSystem = this.alertConfigSystemService.saveAlertConfigSystem(alertConfigSystem);
					if (INotificationSystemTypes.GRAYLOG.equals(alertConfigSystem.getAlertSystemMonitoriza().getType().getName().toLowerCase())) {
						saveGrayLogNoticeConfig(alertConfigSystem.getIdNotSysConfig(), alertConfigForm.getKeysArray().get(j), alertConfigForm.getValuesArray().get(j));
					} else if (INotificationSystemTypes.EMAIL.equals(alertConfigSystem.getAlertSystemMonitoriza().getType().getName().toLowerCase())) {
						saveMailNoticeConfig(alertConfigSystem.getIdNotSysConfig(), alertConfigForm.getEmailConfigurationArray().get(j));
					}
				}

				listNewApplication.add(alertConfig);
			} catch (final Exception e) {
				LOGGER.error(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022), e);
				listNewApplication = StreamSupport.stream(this.alertConfigService.getAllAlertConfigMonitoriza().spliterator(), false).collect(Collectors.toList());
				json.put(KEY_JS_ERROR_ALERT_CONFIG, Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB022));
				dtOutput.setError(json.toString());
			}
		}

		dtOutput.setData(listNewApplication);

		return dtOutput;
	}

	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the alert config identified by its id.
	 *
	 * @param alertConfigId
	 *            Identifier of the alert config to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	*/
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletealertconfig", method = RequestMethod.POST)
	@Transactional
	public String deleteAlertConfig(@RequestParam("id") final Long alertConfigId, @RequestParam("index") final String index) {

		final AlertConfigMonitoriza alertConfig = this.alertConfigService.getAlertConfigMonitorizaById(alertConfigId);

		this.alertConfigSystemService.deleteAlertConfigSystemByAlertConfigMonitoriza(alertConfig);

		this.alertConfigService.deleteAlertConfigMonitoriza(alertConfigId);

		return index;
	}

	/**
	 * Method that save a AlertGraylogNoticeConfig in the database.
	 * @param alertConfSysId Id of the AlertConfigSystem relation.
	 * @param keysList List of GrayLog keys.
	 * @param valuesList List of GrayLog values.
	 */
	private void saveGrayLogNoticeConfig(final Long alertConfSysId, final List<String> keysList, final List<String> valuesList) {
		if (keysList != null && !keysList.isEmpty()) {
			for (int i = 0 ; i < keysList.size() ; i++) {
				if (keysList.get(i) != null && !keysList.get(i).isEmpty()) {
					final AlertGraylogNoticeConfig alertGrayLogNotConf = new AlertGraylogNoticeConfig();
					alertGrayLogNotConf.setNotSysConfigId(alertConfSysId);
					alertGrayLogNotConf.setPkey(keysList.get(i));
					alertGrayLogNotConf.setValue(valuesList.get(i));
					this.alertGrayLogNoticeConfigService.saveAlertGraylogNoticeConfig(alertGrayLogNotConf);
				}
			}
		}
	}

	/**
	 * Method that save a AlertMailNoticeConfig in the database.
	 * @param alertConfSysId Id of the AlertConfigSystem relation.
	 * @param emailList List of mails to save.
	 */
	private void saveMailNoticeConfig(final Long alertConfSysId, final List<String> emailList) {
		if (emailList != null && !emailList.isEmpty()) {
			for (int i = 0 ; i < emailList.size() ; i++) {
					if (!emailList.get(i).isEmpty()) {
						final AlertMailNoticeConfig alertMailNotConf = new AlertMailNoticeConfig();
						alertMailNotConf.setIdNotSysConfig(alertConfSysId);
						alertMailNotConf.setMail(emailList.get(i));
						this.alertMailNoticeConfigService.saveAlertMailNoticeConfig(alertMailNotConf);
				}
			}
		}
	}

}