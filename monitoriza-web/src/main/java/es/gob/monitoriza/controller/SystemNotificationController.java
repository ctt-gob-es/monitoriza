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
 * <b>File:</b><p>es.gob.monitoriza.controller.SystemNotificationController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class that maps the request for the system notification form to the controller.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@Controller
public class SystemNotificationController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of platforms
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "sysNotifications",  method = RequestMethod.GET)
    public String sysNotifications(Model model){
        return "fragments/sysnotifications.html";
    }
	
	/**
	 *  Method that loads the necessary information to show the confirmation modal to remove selected system notifications.
	 * @param ids Parameter that represents IDs of system notifications.
	 * @param indexes Parameter that represents the indexes of the rows of the selected notifications.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "/loadconfirmdiscardsysnot",  method = RequestMethod.POST)
	public String loadConfirmDiscardSysNot(@RequestParam("ids") String ids, @RequestParam("indexes") String indexes, Model model){
		
		model.addAttribute("ids", ids);
		model.addAttribute("indexes", indexes);
		return "modal/discardSysNot.html";
	}

}
