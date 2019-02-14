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
 * <b>File:</b><p>es.gob.monitoriza.controller.PlatformClaveController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of Cl@ve suite systems</p>
 * <b>Date:</b><p>25/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 25/10/2018.
 */
package es.gob.monitoriza.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.ClaveDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.service.IPlatformService;

/**
 * <p>
 * Class that manages the requests related to the Platform Clave administration.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of Cl@ve suite systems.
 * </p>
 * 
 * @version 1.0, 25/10/2018.
 */
@Controller
public class PlatformClaveController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IPlatformService platformService;

	/**
	 * Method that maps the list clave plataform web requests to the controller and forwards
	 * the list of platforms to the view.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "platformclave")
	public String platformClave(Model model) {
		return "fragments/platformclave.html";
	}

	/**
	 * Method that maps the add clave platform web request to the controller and
	 * sets the backing form.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addclave", method = RequestMethod.POST)
	public String addClave(Model model) {
		model.addAttribute("claveDTO", new ClaveDTO());
		return "modal/claveForm";
	}

	/**
	 * Method that maps the edit clave platform web request to the controller and
	 * loads the clave platform to the backing form.
	 * 
	 * @param afirmaId
	 *            Identifier of the @firma platform to be edited.
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editclave", method = RequestMethod.POST)
	public String editClave(@RequestParam("id") Long claveId, Model model) {
		PlatformMonitoriza clave = platformService.getPlatformById(claveId);
		ClaveDTO claveDTO = new ClaveDTO();

		claveDTO.setIdPlatform(claveId);
		claveDTO.setHost(clave.getHost());
		claveDTO.setName(clave.getName());
		claveDTO.setPort(clave.getPort());
		claveDTO.setIsSecure(clave.getIsSecure());
		claveDTO.setServiceContext(clave.getServiceContext());

		model.addAttribute("claveDTO", claveDTO);
		return "modal/claveForm";
	}

}
