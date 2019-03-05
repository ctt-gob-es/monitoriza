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
 * <b>File:</b><p>es.gob.monitoriza.controller.WebAdminController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 05/03/2019.
 */
package es.gob.monitoriza.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusVipDTO;
import es.gob.monitoriza.service.IStatusService;
import es.gob.monitoriza.service.ISystemNotificationService;

/**
 * <p>Class that maps the request for the front view to the controller.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * 
 * @version 1.4, 05/03/2019.
 */
@Controller
public class WebAdminController {

	/**
	 * Attribute that represents the service. 
	 */
	@Autowired
	private IStatusService statusService;
	
	/**
	 * Attribute that represents the service. 
	 */
	@Autowired
	private ISystemNotificationService sysNotService;

	/**
	 * Method that maps the invalid session request.
	* @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "invalidSession")
	public String invalid(Model model) {
		return "invalidSession.html";
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards
	 * the list of users to the view.
	 * 
	 * @param model Holder object for model attributes.
	 * @param request Request object
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "inicio")
	public String index(Model model, final HttpServletRequest request) {
		StatusVipDTO statusVipDto = statusService.completeStatusVip();
		StatusSpieDTO statusSpieDto = statusService.completeStatusSpie();

		model.addAttribute("statusVip", statusVipDto);
		model.addAttribute("statusSpie", statusSpieDto);
		model.addAttribute("spieAvgDetails", statusService.getSpieAvgTimesDetails(statusSpieDto.getData()));
		model.addAttribute("summary", statusService.getSummaryStatus(statusVipDto, statusSpieDto, request.getLocale()));
		model.addAttribute("hasPendingNotifications", sysNotService.hasPendingNotifications());
	
		return "fragments/statusInit.html";
	}

}

