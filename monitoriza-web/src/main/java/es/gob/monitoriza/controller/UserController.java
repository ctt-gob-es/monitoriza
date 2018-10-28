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
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.persistence.configuration.dto.CertificateDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserEditDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserPasswordDTO;
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
 * @version 1.2, 28/10/2018.
 */
@Controller
public class UserController {

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
	public String index(final Model model) {
		model.addAttribute("userFormPassword", new UserPasswordDTO());
		model.addAttribute("userformEdit", new UserEditDTO());
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
	public String addUser(final Model model) {
		model.addAttribute("userform", new UserDTO());
		model.addAttribute("accion", "add");
		return "modal/userForm";
	}

	/**
	 * Method that opens the modal form password.
	 * @param username String that represents the user's name
	 * @param model view Model object
	 * @return String that represents the navigation HTML fragment
	 */
	@RequestMapping(value = "menupass")
	public String menuPass(@RequestParam("username") final String username, final Model model) {
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaByLogin(username);
		UserPasswordDTO userFormPassword = new UserPasswordDTO();

		userFormPassword.setIdUserMonitorizaPass(userMonitoriza.getIdUserMonitoriza());

		model.addAttribute("userFormPassword", userFormPassword);
		return "modal/userFormPass.html";
	}

	/**
	 * Method that opens the modal form user edit.
	 * @param username String that represents the user's name
	 * @param model view Model object
	 * @return String that represents the navigation HTML fragment
	 */
	@RequestMapping(value = "menuedit")
	public String menuEdit(@RequestParam("username") final String username, final Model model) {
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaByLogin(username);
		UserEditDTO userFormEdit = new UserEditDTO();

		userFormEdit.setIdUserMonitorizaEdit(userMonitoriza.getIdUserMonitoriza());
		userFormEdit.setNameEdit(userMonitoriza.getName());
		userFormEdit.setSurnamesEdit(userMonitoriza.getSurnames());
		userFormEdit.setEmailEdit(userMonitoriza.getEmail());
		userFormEdit.setLoginEdit(userMonitoriza.getLogin());

		model.addAttribute("userformEdit", userFormEdit);
		return "modal/userFormEdit.html";
	}

	/**
	 * Method that maps the add user certificate web requests to the controller and forwards to the form
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @param idUserMonitoriza Identifier for the idUserMonitoriza
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "/managecertuser/{idUserMonitoriza}")
	public String manageCertUser(final Model model, @PathVariable("idUserMonitoriza") final Long idUserMonitoriza) {
		model.addAttribute("idUserMonitoriza", idUserMonitoriza);
		return "modal/certUser.html";
	}

	/**
	 * Method that maps the add user web request to the controller and sets the
	 * backing form.
	 *
	 * @param model
	 *            Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addcertuser", method = RequestMethod.POST)
	public String addcertuserForm(final Model model) {
		model.addAttribute("certUserForm", new CertificateDTO());
		model.addAttribute("accion", "add");
		return "modal/certUserForm";
	}

	/**
	 * Get userService.
	 * @return userService
	 */
	public IUserMonitorizaService getUserService() {
		return userService;
	}

	/**
	 * SetuserService.
	 * @param userServiceP set userService
	 */
	public void setUserService(final IUserMonitorizaService userServiceP) {
		this.userService = userServiceP;
	}

}
