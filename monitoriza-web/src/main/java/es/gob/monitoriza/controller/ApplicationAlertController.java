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

import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.dto.ApplicationDTO;
import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.service.IAlertSystemMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
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
 * @version 1.1, 28/10/2018.
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
	 * Attribute that represents the service object for accessing the repository for applications.
	 */
	@Autowired
	private IApplicationMonitorizaService applicationService;

	private static final String APP_ENABLED_S = "S"; //$NON-NLS-1$

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of users to the view.
	 *
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "applcontrolpanel", method = RequestMethod.GET)
	public String applControlPanel(final Model model) {

		return "fragments/applcontrolpanel.html";
	}

	@RequestMapping(value = "applnotificationsystem", method = RequestMethod.GET)
	public String applNotificationSystem(final Model model) {

		return "fragments/applnotificationsystem.html"; //$NON-NLS-1$
	}

	@RequestMapping(value = "applications", method = RequestMethod.GET)
	public String applications(final Model model) {

		return "fragments/applications.html"; //$NON-NLS-1$
	}

	@RequestMapping(value = "appltemplates", method = RequestMethod.GET)
	public String applTemplates(final Model model) {

		model.addAttribute("importtemplateform", new TemplateDTO());

		return "fragments/appltemplates.html"; //$NON-NLS-1$
	}

	@RequestMapping(value = "applsummaries", method = RequestMethod.GET)
	public String applSummaries(final Model model) {

		model.addAttribute("alertSystemsList", new ArrayList<AlertSystemMonitoriza>()); //$NON-NLS-1$

		model.addAttribute("applicationsList", new ArrayList<ApplicationMonitoriza>()); //$NON-NLS-1$

		model.addAttribute("summaryform", new ResumeDTO()); //$NON-NLS-1$

		return "fragments/applsummaries.html"; //$NON-NLS-1$
	}

	@RequestMapping(value = "applstats", method = RequestMethod.GET)
	public String applStats(final Model model) {

		return "fragments/applstats.html"; //$NON-NLS-1$
	}

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addnotifsystem", method = RequestMethod.POST)
    public String addNotifSystem(final Model model){

		model.addAttribute("notifsystemform", new AlertSystemDTO()); //$NON-NLS-1$

		return "modal/notifSystemForm.html"; //$NON-NLS-1$
    }

	/**
	 * Method that maps the add new node web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editnotifsystem", method = RequestMethod.POST)
    public String editNotifSystem(@RequestParam("id") final Long notifSystemId, final Model model){

		final AlertSystemMonitoriza alertSystem = this.alertSystemService.getAlertSystemMonitorizaById(notifSystemId);
		final AlertSystemDTO alertSystemForm = new AlertSystemDTO();

		alertSystemForm.setName(alertSystem.getName());
		alertSystemForm.setType(alertSystem.getType());

		if(alertSystem.getGraylogSystemConfig() != null) {
			alertSystemForm.setHost(alertSystem.getGraylogSystemConfig().getHost());
			alertSystemForm.setPort(alertSystem.getGraylogSystemConfig().getPort());
		}

		model.addAttribute("notifsystemform", alertSystemForm); //$NON-NLS-1$

		return "modal/notifSystemForm.html"; //$NON-NLS-1$
    }


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
	 * Method that maps the add new node web request to the controller and sets the backing form.
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
		applicationForm.setAppKey(application.getAppKey());
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

	@RequestMapping(value = "importtemplate", method = RequestMethod.POST)
    public String importTemplate(final Model model){

		model.addAttribute("importtemplateform", new TemplateDTO()); //$NON-NLS-1$

		return "modal/importTemplateForm.html"; //$NON-NLS-1$
    }

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

}
