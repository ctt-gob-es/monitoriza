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
 * <b>Date:</b><p>21 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 21 mar. 2018.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import es.gob.monitoriza.form.UserForm;
import es.gob.monitoriza.form.UserFormEdit;
import es.gob.monitoriza.form.UserFormPassword;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IUserMonitorizaService;
import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>
 * Class that manages the REST requests related to the Users administration and
 * JSON communication.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 21 mar. 2018.
 */
@RestController
public class UserRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

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
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/usersdatatable", method = RequestMethod.GET)
	public DataTablesOutput<UserMonitoriza> users(@Valid DataTablesInput input) {
		return (DataTablesOutput<UserMonitoriza>) userService.findAll(input);

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
	@RequestMapping(path = "/deleteuser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam("id") Long userId, @RequestParam("index") String index) {
		userService.deleteUserMonitoriza(userId);

		return index;
	}

	/**
	 * Method that maps the save user web request to the controller and saves it
	 * in the persistence.
	 * 
	 * @param userForm
	 *            Object that represents the backing user form.
	 * @param bindingResult
	 *            Object that represents the form validation result.
	 * @return {@link DataTablesOutput<UserMonitoriza>}
	 */
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<UserMonitoriza> save(
			@Validated(OrderedValidation.class) @RequestBody UserForm userForm, BindingResult bindingResult) {
		DataTablesOutput<UserMonitoriza> dtOutput = new DataTablesOutput<>();
		UserMonitoriza userMonitoriza = null;
		List<UserMonitoriza> listNewUser = new ArrayList<UserMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (userForm.getIdUserMonitoriza() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitoriza());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				if (!"".equals(userForm.getPassword())) {
					String pwd = userForm.getPassword();
					BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
					String hashPwd = bc.encode(pwd);

					userMonitoriza.setPassword(hashPwd);
				}

				userMonitoriza.setLogin(userForm.getLogin());
				userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
				userMonitoriza.setEmail(userForm.getEmail());
				userMonitoriza.setIsBlocked(Boolean.FALSE);
				userMonitoriza.setLastAccess(null);
				userMonitoriza.setLastIpAccess(null);
				userMonitoriza.setName(userForm.getName());
				userMonitoriza.setSurnames(userForm.getSurnames());

				UserMonitoriza user = userService.saveUserMonitoriza(userMonitoriza);

				listNewUser.add(user);
			} catch (Exception e) {
				listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewUser);

		return dtOutput;

	}

	@RequestMapping(value = "/saveuseredit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<UserMonitoriza> saveEdit(
			@Validated(OrderedValidation.class) @RequestBody UserFormEdit userForm, BindingResult bindingResult) {
		DataTablesOutput<UserMonitoriza> dtOutput = new DataTablesOutput<>();
		UserMonitoriza userMonitoriza = null;
		List<UserMonitoriza> listNewUser = new ArrayList<UserMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false)
					.collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (userForm.getIdUserMonitorizaEdit() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitorizaEdit());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				userMonitoriza.setLogin(userForm.getLogin());
				userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
				userMonitoriza.setEmail(userForm.getEmail());
				userMonitoriza.setIsBlocked(Boolean.FALSE);
				userMonitoriza.setLastAccess(null);
				userMonitoriza.setLastIpAccess(null);
				userMonitoriza.setName(userForm.getName());
				userMonitoriza.setSurnames(userForm.getSurnames());

				UserMonitoriza user = userService.saveUserMonitoriza(userMonitoriza);

				listNewUser.add(user);
			} catch (Exception e) {
				listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false)
						.collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewUser);

		return dtOutput;

	}

	@RequestMapping(value = "/saveuserpassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String savePassword(@Validated(OrderedValidation.class) @RequestBody UserFormPassword userFormPassword,
			BindingResult bindingResult) {
		String result = "";
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaById(userFormPassword.getIdUserMonitorizaPass());

		if (bindingResult.hasErrors()) {
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			result = json.toString();
		} else {
			String oldPwd = userFormPassword.getOldPassword();
			String pwd = userFormPassword.getPassword();

			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			String hashPwd = bc.encode(pwd);

			try {
				if (bc.matches(oldPwd, userMonitoriza.getPassword())) {
					userMonitoriza.setPassword(hashPwd);

					userService.saveUserMonitoriza(userMonitoriza);
					result = "0";
				} else {
					result = "-1";
				}
			} catch (Exception e) {
				result = "-2";
				throw e;
			}
		}

		return result;
	}

	@RequestMapping(value = "/menueditsave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String saveEditMenu(@Validated(OrderedValidation.class) @RequestBody UserFormEdit userForm,
			BindingResult bindingResult) {
		UserMonitoriza userMonitoriza = null;
		String result = "";

		if (bindingResult.hasErrors()) {
			JSONObject json = new JSONObject();
			for (FieldError o : bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			result = json.toString();
		} else {
			try {
				if (userForm.getIdUserMonitorizaEdit() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitorizaEdit());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				userMonitoriza.setLogin(userForm.getLogin());
				userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
				userMonitoriza.setEmail(userForm.getEmail());
				userMonitoriza.setIsBlocked(Boolean.FALSE);
				userMonitoriza.setLastAccess(null);
				userMonitoriza.setLastIpAccess(null);
				userMonitoriza.setName(userForm.getName());
				userMonitoriza.setSurnames(userForm.getSurnames());

				userService.saveUserMonitoriza(userMonitoriza);

				result = "0";
			} catch (Exception e) {
				result = "-1";
				throw e;
			}
		}

		return result;
	}

}
