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
 * <b>File:</b><p>es.gob.monitoriza.controller.UserController.java.</p>
 * <b>Description:</b><p>Class that manages the requests related to the Users administration.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 8 mar. 2018.
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
import es.gob.monitoriza.form.UserForm;
import es.gob.monitoriza.form.UserFormEdit;
import es.gob.monitoriza.form.UserFormPassword;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.service.IUserMonitorizaService;

/**
 * <p>
 * Class that manages the requests related to the Users administration.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 8 mar. 2018.
 */
@Controller
public class UserController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IUserMonitorizaService userService;

	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "useradmin")
	public String index(Model model) {
		model.addAttribute("userFormPassword", new UserFormPassword());
		model.addAttribute("userformEdit", new UserFormEdit());
		return "fragments/useradmin.html";
	}

	/**
	 * Method that maps the add user web request to the controller and sets the
	 * backing form.
	 * 
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "adduser", method = RequestMethod.POST)
	public String addUser(Model model) {
		model.addAttribute("userform", new UserForm());
		model.addAttribute("accion", "add");
		return "modal/userForm";
	}

	@RequestMapping(value = "menupass")
	public String menuEdit(@RequestParam("username") String username, Model model) {
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaByLogin(username);
		UserFormPassword userFormPassword = new UserFormPassword();

		userFormPassword.setIdUserMonitorizaPass(userMonitoriza.getIdUserMonitoriza());

		model.addAttribute("userFormPassword", userFormPassword);
		return "modal/userFormPass.html";
	}

	@RequestMapping(value = "menuedit")
	public String menuPass(@RequestParam("username") String username, Model model) {
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaByLogin(username);
		UserFormEdit userFormEdit = new UserFormEdit();

		userFormEdit.setIdUserMonitorizaEdit(userMonitoriza.getIdUserMonitoriza());
		userFormEdit.setName(userMonitoriza.getName());
		userFormEdit.setSurnames(userMonitoriza.getSurnames());
		userFormEdit.setEmail(userMonitoriza.getEmail());
		userFormEdit.setLogin(userMonitoriza.getLogin());

		model.addAttribute("userformEdit", userFormEdit);
		return "modal/userFormEdit.html";
	}

}
