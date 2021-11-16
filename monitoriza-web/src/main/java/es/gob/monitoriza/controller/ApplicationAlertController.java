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
 * <b>File:</b><p>es.gob.monitoriza.controller.ConfServerMailController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.persistence.configuration.dto.AlertAuditDTO;
import es.gob.monitoriza.persistence.configuration.dto.AlertConfigDTO;
import es.gob.monitoriza.persistence.configuration.dto.AlertStatisticDTO;
import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.dto.ApplicationDTO;
import es.gob.monitoriza.persistence.configuration.dto.GrayLogConfigDTO;
import es.gob.monitoriza.persistence.configuration.dto.MailResumeConfigDTO;
import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApp;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSeverityMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;
import es.gob.monitoriza.service.IAlertDIMAppService;
import es.gob.monitoriza.service.IAlertDIMNodeService;
import es.gob.monitoriza.service.IAlertDIMSeverityService;
import es.gob.monitoriza.service.IAlertDIMTemplateService;
import es.gob.monitoriza.service.IAlertDIMTypeService;
import es.gob.monitoriza.service.IAlertSeverityMonitorizaService;
import es.gob.monitoriza.service.IAlertSystemMonitorizaService;
import es.gob.monitoriza.service.IAlertTypeMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.service.IResumeMonitorizaService;
import es.gob.monitoriza.service.ITemplateMonitorizaService;

/**
 * <p>
 * Class ApplicationAlertController.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 * @version 1.0, 10/11/2021.
 */

@Controller
public class ApplicationAlertController {

	/**
	 * Attribute that represents the service object for accessing the repository for templates.
	 */
	@Autowired
	private ITemplateMonitorizaService templateService;

	/**
	 * Attribute that represents the service object for accessing the repository for alert systems.
	 */
	@Autowired
	private IAlertSystemMonitorizaService alertSystemService;

	/**
	 * Attribute that represents the service object for accessing the repository for resumes.
	 */
	@Autowired
	private IResumeMonitorizaService resumeService;

	/**
	 * Attribute that represents the service object for accessing the repository for applications.
	 */
	@Autowired
	private IApplicationMonitorizaService applicationService;

	/**
	 * Attribute that represents the service object for accessing the repository for alert types.
	 */
	@Autowired
	private IAlertTypeMonitorizaService alertTypeMonitorizaService;

	/**
	 * Attribute that represents the service object for accessing the repository for severity types.
	 */
	@Autowired
	private IAlertSeverityMonitorizaService alertSeverityMonitorizaService;

	/**
	 * Attribute that represents the service object for accessing the repository for alert configurations.
	 */
	@Autowired
	private IAlertConfigMonitorizaService alertConfigMonitorizaService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMAppService alertAppService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMTypeService alertTypeService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMTemplateService alertTemplateService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMNodeService alertNodeService;

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertDIMSeverityService alertSeverityService;

	/**
	 * Attribute that represents if the application is enabled
	 */
	private static final String APP_ENABLED_S = "S"; //$NON-NLS-1$

	/**
	 * Method that maps the list audits web requests to the controller and forwards
	 * the list of alert audits to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applcontrolpanel", method = RequestMethod.GET)
	public String applControlPanel(final Model model) {

		model.addAttribute("auditsform", new AlertAuditDTO());

		return "fragments/applcontrolpanel.html";
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of alert systems to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applnotificationsystem", method = RequestMethod.GET)
	public String applNotificationSystem(final Model model) {

		return "fragments/applnotificationsystem.html";
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of applications to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applications", method = RequestMethod.GET)
	public String applications(final Model model) {

		return "fragments/applications.html";
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of alert configurations to the view.
	 * @param applicationId Application identifier.
	 * @param appName Application name.º
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "alertconfigurations", method = RequestMethod.GET)
	public String alertConfigurations(@RequestParam("applicationId") final Long applicationId, @RequestParam("appName") final String appName, final Model model) {

		model.addAttribute("applicationId", applicationId); //$NON-NLS-1$
		model.addAttribute("appName", appName); //$NON-NLS-1$

		return "fragments/alertconfigurations.html"; //$NON-NLS-1$
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of templates to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "appltemplates", method = RequestMethod.GET)
	public String applTemplates(final Model model) {

		model.addAttribute("importtemplateform", new TemplateDTO());

		return "fragments/appltemplates.html"; //$NON-NLS-1$
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of resumes to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applsummaries", method = RequestMethod.GET)
	public String applSummaries(final Model model) {

		model.addAttribute("alertSystemsList", new ArrayList<AlertSystemMonitoriza>()); //$NON-NLS-1$

		model.addAttribute("applicationsList", new ArrayList<ApplicationMonitoriza>()); //$NON-NLS-1$

		model.addAttribute("summaryform", new ResumeDTO()); //$NON-NLS-1$

		return "fragments/applsummaries.html"; //$NON-NLS-1$
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of stats to the view.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applstats", method = RequestMethod.GET)
	public String applStats(final Model model) {

		// Se obtiene la lista de aplicaciones
		final List<AlertDIMApp> applications = this.alertAppService.getAllAlertDIMApp();
		model.addAttribute("applicationsList", applications);

		// Se obtiene la lista de tipos de alarmas
		final List<AlertDIMType> types = this.alertTypeService.getAllAlertDIMType();
		model.addAttribute("typesList", types);

		// Se obtiene la lista de plantillas
		final List<AlertDIMTemplate> templates = this.alertTemplateService.getAllAlertDIMTemplate();
		model.addAttribute("templatesList", templates);

		// Se obtiene la lista de nodos
		final List<AlertDIMNode> nodes = this.alertNodeService.getAllAlertDIMNode();
		model.addAttribute("nodesList", nodes);

		// Se obtiene la lista de niveles
		final List<AlertDIMSeverity> severityList = this.alertSeverityService.getAllAlertDIMSeverity();
		model.addAttribute("severityList", severityList);

		model.addAttribute("statsform", new AlertStatisticDTO());

		return "fragments/applstats.html"; //$NON-NLS-1$
	}

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add a alert system.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addnotifsystem", method = RequestMethod.POST)
    public String addNotifSystem(final Model model){

		model.addAttribute("notifsystemform", new AlertSystemDTO()); //$NON-NLS-1$

		return "modal/notifSystemForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form with the alert system information.
	 * @param notifSystemId alert system identifier
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editnotifsystem", method = RequestMethod.POST)
    public String editNotifSystem(@RequestParam("id") final Long notifSystemId, final Model model){

		final AlertSystemMonitoriza alertSystem = this.alertSystemService.getAlertSystemMonitorizaById(notifSystemId);
		final AlertSystemDTO alertSystemForm = new AlertSystemDTO();

		alertSystemForm.setIdAlertSystemMonitoriza(alertSystem.getIdAlertSystemMonitoriza());
		alertSystemForm.setName(alertSystem.getName());
		alertSystemForm.setType(alertSystem.getType());

		if (alertSystem.getGraylogSystemConfig() != null) {
			alertSystemForm.setHost(alertSystem.getGraylogSystemConfig().getHost());
			alertSystemForm.setPort(alertSystem.getGraylogSystemConfig().getPort());
		}

		model.addAttribute("notifsystemform", alertSystemForm); //$NON-NLS-1$

		return "modal/notifSystemForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add a application.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addapplication", method = RequestMethod.POST)
    public String addApplication(final Model model){

		List<TemplateMonitoriza> templatesList = new ArrayList<TemplateMonitoriza>();

		templatesList = StreamSupport.stream(this.templateService.getAllTemplateMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("templatesList", templatesList); //$NON-NLS-1$

		model.addAttribute("applicationform", new ApplicationDTO()); //$NON-NLS-1$

		return "modal/applicationForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form with the application information
	 * @param appId Application identifier.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editapplication", method = RequestMethod.POST)
    public String editApplication(@RequestParam("id") final Long appId, final Model model){

		final ApplicationMonitoriza application = this.applicationService.getApplicationMonitorizaById(appId);
		final ApplicationDTO applicationForm = new ApplicationDTO();

		applicationForm.setIdApplicationMonitoriza(appId);
		applicationForm.setName(application.getName());
		applicationForm.setTemplateID(application.getTemplateMonitoriza().getIdTemplateMonitoriza());
		applicationForm.setCipherKey(application.getCipherKey());
		applicationForm.setResponsibleName(application.getResponsibleName());
		applicationForm.setResponsibleEmail(application.getResponsibleEmail());
		applicationForm.setResponsiblePhone(application.getResponsiblePhone());

		if (APP_ENABLED_S.equals(application.getEnabled())) {
			applicationForm.setEnabled(Boolean.TRUE);
		} else {
			applicationForm.setEnabled(Boolean.FALSE);
		}

		model.addAttribute("applicationform", applicationForm); //$NON-NLS-1$

		// Se cargan las plantillas por si se quiere editar este campo
		List<TemplateMonitoriza> templatesList = new ArrayList<TemplateMonitoriza>();

		templatesList = StreamSupport.stream(this.templateService.getAllTemplateMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("templatesList", templatesList); //$NON-NLS-1$

		return "modal/applicationForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "importtemplate", method = RequestMethod.POST)
    public String importTemplate(final Model model){

		model.addAttribute("importtemplateform", new TemplateDTO()); //$NON-NLS-1$

		return "modal/importTemplateForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add a new resume.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "newresumeinfo", method = RequestMethod.POST)
    public String addSummary(final Model model){

		// Se cargan los sistemas de notificacion para el select
		List<AlertSystemMonitoriza> alertSystemsList = new ArrayList<AlertSystemMonitoriza>();

		alertSystemsList = StreamSupport.stream(this.alertSystemService.getAllAlertSystemMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSystemsList", alertSystemsList); //$NON-NLS-1$

		// Se cargan las aplicaciones para el select
		List<ApplicationMonitoriza> applicationsList = new ArrayList<ApplicationMonitoriza>();

		applicationsList = StreamSupport.stream(this.applicationService.getAllApplicationMonitoriza().spliterator(), false)
						.collect(Collectors.toList());

		model.addAttribute("applicationsList", applicationsList); //$NON-NLS-1$

		// Si encontramos aplicaciones cargaremos las alertas de la primera que encuentre
		if (applicationsList.size() > 0) {
			final List<AlertConfigMonitoriza> alertConfigList = applicationsList.get(0).getAlertConfigMonitoriza();
			model.addAttribute("alertConfigList", alertConfigList); //$NON-NLS-1$
		}

		model.addAttribute("summaryform", new ResumeDTO()); //$NON-NLS-1$

		return "modal/summaryForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for edit a resume.
	 * @param resumeId Resume identifier.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editresumeinfo", method = RequestMethod.POST)
    public String editSummary(@RequestParam("id") final Long resumeId, final Model model){

		// Se cargan los sistemas de notificacion para el select
		List<AlertSystemMonitoriza> allAlertSystemsList = new ArrayList<AlertSystemMonitoriza>();

		allAlertSystemsList = StreamSupport.stream(this.alertSystemService.getAllAlertSystemMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSystemsList", allAlertSystemsList); //$NON-NLS-1$

		// Se cargan las aplicaciones para el select
		List<ApplicationMonitoriza> allApplicationsList = new ArrayList<ApplicationMonitoriza>();

		allApplicationsList = StreamSupport.stream(this.applicationService.getAllApplicationMonitoriza().spliterator(), false)
						.collect(Collectors.toList());

		model.addAttribute("applicationsList", allApplicationsList); //$NON-NLS-1$

		// Si encontramos aplicaciones cargaremos las alertas de la primera que encuentre
		if (allApplicationsList.size() > 0) {
			final List<AlertConfigMonitoriza> alertConfigList = allApplicationsList.get(0).getAlertConfigMonitoriza();
			model.addAttribute("alertConfigList", alertConfigList); //$NON-NLS-1$
		}

		// Se cargan la informacion del resumen
		final ResumeMonitoriza resume = this.resumeService.getResumeMonitorizaById(resumeId);
		final ResumeDTO resumeForm = new ResumeDTO();
		resumeForm.setIdResumeMonitoriza(resumeId);
		resumeForm.setName(resume.getName());
		if ("S".equals(resume.getEnabled())) { //$NON-NLS-1$
			resumeForm.setIsEnabled(Boolean.TRUE);
		} else {
			resumeForm.setIsEnabled(Boolean.FALSE);
		}
		resumeForm.setDescription(resume.getDescription());
		resumeForm.setPeriodicity(resume.getPeriodicity());

		model.addAttribute("alertSystemsSaved", resume.getAlertResumeSystem()); //$NON-NLS-1$

		model.addAttribute("summaryform", resumeForm); //$NON-NLS-1$

		return "modal/summaryForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add or edit a email configuration.
	 * @param alertSystemId Alert system identifier.
	 * @param emailConfiguration Email addresses configurated.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "emailinformation", method = RequestMethod.POST)
    public String emailInformation(@RequestParam("idAlertSystem") final Long alertSystemId, @RequestParam("emailConfiguration") final String emailConfiguration, final Model model){

		final MailResumeConfigDTO mailResumeConfig = new MailResumeConfigDTO();
		mailResumeConfig.setIdAlertSystem(alertSystemId);
		mailResumeConfig.setEmailAddress(emailConfiguration);

		model.addAttribute("emailinfoform", mailResumeConfig); //$NON-NLS-1$

		return "modal/emailInformation.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add a new alert configuration.
	 * @param appId Application identifier.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	*/
	@RequestMapping(value = "newalertconfig", method = RequestMethod.POST)
    public String addAlertConfig(@RequestParam("appId") final Long appId, final Model model){

		// Se cargan los sistemas de notificacion para el select correspondiente
		List<AlertSystemMonitoriza> alertSystemsList = new ArrayList<AlertSystemMonitoriza>();

		alertSystemsList = StreamSupport.stream(this.alertSystemService.getAllAlertSystemMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSystemsList", alertSystemsList); //$NON-NLS-1$

		// Se cargan los tipos de alerta para el select correspondiente
		List<AlertTypeMonitoriza> alertTypesMonitorizaList = new ArrayList<AlertTypeMonitoriza>();

		alertTypesMonitorizaList = StreamSupport.stream(this.alertTypeMonitorizaService.getAllAlertTypeMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertTypesList", alertTypesMonitorizaList); //$NON-NLS-1$

		// Se cargan los tipos de criticidad para el select correspondiente
		List<AlertSeverityMonitoriza> alertSeverityList = new ArrayList<AlertSeverityMonitoriza>();

		alertSeverityList = StreamSupport.stream(this.alertSeverityMonitorizaService.getAllAlertSeverity().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSeverityList", alertSeverityList); //$NON-NLS-1$

		final AlertConfigDTO alertConfigForm = new AlertConfigDTO();
		alertConfigForm.setAppID(appId);

		model.addAttribute("alertconfigform", alertConfigForm); //$NON-NLS-1$

		return "modal/alertConfigForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for edit a alert configuration.
	 * @param alertConfigId Alert configuration identifier.
	 * @param appId Application identifier.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	*/
	@RequestMapping(value = "editalertconfig", method = RequestMethod.POST)
    public String editAlertConfig(@RequestParam("alertConfigId") final Long alertConfigId, @RequestParam("appId") final Long appId, final Model model){

		// Se cargan los sistemas de notificacion para el select correspondiente
		List<AlertSystemMonitoriza> alertSystemsList = new ArrayList<AlertSystemMonitoriza>();

		alertSystemsList = StreamSupport.stream(this.alertSystemService.getAllAlertSystemMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSystemsList", alertSystemsList); //$NON-NLS-1$

		// Se cargan los tipos de alerta para el select correspondiente
		List<AlertTypeMonitoriza> alertTypesMonitorizaList = new ArrayList<AlertTypeMonitoriza>();

		alertTypesMonitorizaList = StreamSupport.stream(this.alertTypeMonitorizaService.getAllAlertTypeMonitoriza().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertTypesList", alertTypesMonitorizaList); //$NON-NLS-1$

		// Se cargan los tipos de criticidad para el select correspondiente
		List<AlertSeverityMonitoriza> alertSeverityList = new ArrayList<AlertSeverityMonitoriza>();

		alertSeverityList = StreamSupport.stream(this.alertSeverityMonitorizaService.getAllAlertSeverity().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("alertSeverityList", alertSeverityList); //$NON-NLS-1$

		// Se carga la informacion de la alerta
		final AlertConfigMonitoriza alertConfig = this.alertConfigMonitorizaService.getAlertConfigMonitorizaById(alertConfigId);
		final AlertConfigDTO alertConfigForm = new AlertConfigDTO();
		alertConfigForm.setIdAlertConfigMonitoriza(alertConfigId);
		alertConfigForm.setAppID(appId);
		alertConfigForm.setTypeID(alertConfig.getAlertTypeMonitoriza().getIdTypeMonitoriza());
		alertConfigForm.setSeverity(alertConfig.getAlertSeverityMonitoriza().getSeverityTypeId());
		alertConfigForm.setIsAllowBlock(alertConfig.getAllowBlock());
		if (alertConfig.getAllowBlock()) {
			alertConfigForm.setBlockCondition(alertConfig.getBlockCondition());
			alertConfigForm.setBlockInterval(alertConfig.getBlockInterval());
			alertConfigForm.setBlockPeriod(alertConfig.getBlockPeriod());
		}
		alertConfigForm.setIsEnable(alertConfig.getEnable());

		model.addAttribute("alertconfigform", alertConfigForm); //$NON-NLS-1$

		return "modal/alertConfigForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form for add or edit a graylog configuration.
	 * @param alertSystemId Alert system identifier.
	 * @param keysList List of keys configurated.
	 * @param valuesList List of values configurated.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	*/
	@RequestMapping(value = "grayloginformation", method = RequestMethod.POST)
    public String grayLogInformation(@RequestParam("idAlertSystemConf") final Long alertSystemId,
    									@RequestParam("keysList[]") final String[] keysList,
    									@RequestParam("valuesList[]") final String[] valuesList,
    									final Model model){

		final GrayLogConfigDTO grayLogConfig = new GrayLogConfigDTO();
		grayLogConfig.setIdAlertSystemConf(alertSystemId);
		grayLogConfig.setKeysList(keysList);
		grayLogConfig.setValuesList(valuesList);

		model.addAttribute("grayloginfoform", grayLogConfig); //$NON-NLS-1$
		model.addAttribute("keysList", keysList); //$NON-NLS-1$
		model.addAttribute("valuesList", valuesList); //$NON-NLS-1$

		return "modal/grayLogInformation.html"; //$NON-NLS-1$
    }

}
