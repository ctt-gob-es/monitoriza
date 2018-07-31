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

import java.io.IOException;
import java.security.cert.X509Certificate;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.form.UserForm;
import es.gob.monitoriza.form.UserFormEdit;
import es.gob.monitoriza.form.UserFormPassword;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.rest.exception.OrderedValidation;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IStatusCertificateService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.IUserMonitorizaService;
import es.gob.monitoriza.utilidades.NumberConstants;
import es.gob.monitoriza.utilidades.UtilsCertificate;

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
	 * Attribute that represents the identifier of the html input file field for the keystore file.
	 */
	private static final String FIELD_FILE = "file";

	/**
	 * Attribute that represents the identifier of the html input id field for the user.
	 */
	private static final String FIELD_ID_USER = "idUserMonitoriza";

	/**
	 * Attribute that represents the span text.
	 */
	private static final String SPAN = "_span";

	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IUserMonitorizaService userService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ISystemCertificateService certService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IKeystoreService keystoreService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IStatusCertificateService statusCertService;

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
	public DataTablesOutput<UserMonitoriza> users(@Valid final DataTablesInput input) {
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
	public String deleteUser(@RequestParam("id") final Long userId, @RequestParam("index") final String index) {
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
	public @ResponseBody DataTablesOutput<UserMonitoriza> save(@Validated(OrderedValidation.class) @RequestBody final UserForm userForm, final BindingResult bindingResult) {
		DataTablesOutput<UserMonitoriza> dtOutput = new DataTablesOutput<UserMonitoriza>();
		UserMonitoriza userMonitoriza = null;
		List<UserMonitoriza> listNewUser = new ArrayList<UserMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false).collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (userForm.getIdUserMonitoriza() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitoriza());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				if (!StringUtils.isEmpty(userForm.getPassword())) {
					String pwd = userForm.getPassword();
					BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
					String hashPwd = bcpe.encode(pwd);

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
				listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false).collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewUser);

		return dtOutput;

	}

	/**
	 * Method that saves a user.
	 * @param userForm UserForm
	 * @param bindingResult  BindingResult
	 * @return DataTablesOutput<UserMonitoriza> users
	 */
	@RequestMapping(value = "/saveuseredit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<UserMonitoriza> saveEdit(@Validated(OrderedValidation.class) @RequestBody final UserFormEdit userForm, final BindingResult bindingResult) {
		DataTablesOutput<UserMonitoriza> dtOutput = new DataTablesOutput<>();
		UserMonitoriza userMonitoriza = null;
		List<UserMonitoriza> listNewUser = new ArrayList<UserMonitoriza>();

		if (bindingResult.hasErrors()) {
			listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false).collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		} else {
			try {
				if (userForm.getIdUserMonitorizaEdit() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitorizaEdit());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				userMonitoriza.setLogin(userForm.getLoginEdit());
				userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
				userMonitoriza.setEmail(userForm.getEmailEdit());
				userMonitoriza.setIsBlocked(Boolean.FALSE);
				userMonitoriza.setLastAccess(null);
				userMonitoriza.setLastIpAccess(null);
				userMonitoriza.setName(userForm.getNameEdit());
				userMonitoriza.setSurnames(userForm.getSurnamesEdit());

				UserMonitoriza user = userService.saveUserMonitoriza(userMonitoriza);

				listNewUser.add(user);
			} catch (Exception e) {
				listNewUser = StreamSupport.stream(userService.getAllUserMonitoriza().spliterator(), false).collect(Collectors.toList());
				throw e;
			}
		}

		dtOutput.setData(listNewUser);

		return dtOutput;

	}

	/**
	 * Method that changes the password.
	 * @param userFormPassword UserFormPassword
	 * @param bindingResult BindingResult
	 * @return String result
	 */
	@RequestMapping(value = "/saveuserpassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String savePassword(@Validated(OrderedValidation.class) @RequestBody final UserFormPassword userFormPassword, final BindingResult bindingResult) {
		String result = "";
		UserMonitoriza userMonitoriza = userService.getUserMonitorizaById(userFormPassword.getIdUserMonitorizaPass());

		if (bindingResult.hasErrors()) {
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			result = json.toString();
		} else {
			String oldPwd = userFormPassword.getOldPassword();
			String pwd = userFormPassword.getPassword();

			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			String hashPwd = bcpe.encode(pwd);

			try {
				if (bcpe.matches(oldPwd, userMonitoriza.getPassword())) {
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

	/**
	 * Method that edits the user.
	 * @param userForm UserFormEdit
	 * @param bindingResult BindingResult
	 * @return String result
	 */
	@RequestMapping(value = "/menueditsave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String saveEditMenu(@Validated(OrderedValidation.class) @RequestBody final UserFormEdit userForm, final BindingResult bindingResult) {
		UserMonitoriza userMonitoriza = null;
		String result = "";

		if (bindingResult.hasErrors()) {
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + SPAN, o.getDefaultMessage());
			}
			result = json.toString();
		} else {
			try {
				if (userForm.getIdUserMonitorizaEdit() != null) {
					userMonitoriza = userService.getUserMonitorizaById(userForm.getIdUserMonitorizaEdit());
				} else {
					userMonitoriza = new UserMonitoriza();
				}
				userMonitoriza.setLogin(userForm.getLoginEdit());
				userMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
				userMonitoriza.setEmail(userForm.getEmailEdit());
				userMonitoriza.setIsBlocked(Boolean.FALSE);
				userMonitoriza.setLastAccess(null);
				userMonitoriza.setLastIpAccess(null);
				userMonitoriza.setName(userForm.getNameEdit());
				userMonitoriza.setSurnames(userForm.getSurnamesEdit());

				userService.saveUserMonitoriza(userMonitoriza);

				result = "0";
			} catch (Exception e) {
				result = "-1";
				throw e;
			}
		}

		return result;
	}

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/certuserdatatable/{idUserMonitoriza}", method = RequestMethod.GET)
	public DataTablesOutput<SystemCertificate> listCertificatesUser(@Valid final DataTablesInput input, @PathVariable(FIELD_ID_USER) final Long idUserMonitoriza) {
		return (DataTablesOutput<SystemCertificate>) certService.findCertUserByUser(input, idUserMonitoriza);
	}

	/**
	 * Method that maps the save user certificate web request to the controller and saves it in the persistence.
	 * @param file
	 * @param user
	 * @return DataTable
	 * @throws Exception
	 */
	@RequestMapping(value = "/savecertuser/{idUserMonitoriza}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonView(DataTablesOutput.View.class)
	public @ResponseBody DataTablesOutput<SystemCertificate> savecertuser(@RequestParam(FIELD_FILE) final MultipartFile file, @PathVariable(FIELD_ID_USER) final Long idUserMonitoriza) {
		DataTablesOutput<SystemCertificate> dtOutput = null;
		List<SystemCertificate> listNewSystemCert = new ArrayList<SystemCertificate>();
		SystemCertificate systemCertificate = new SystemCertificate();

		try {
			dtOutput = new DataTablesOutput<SystemCertificate>();
			byte[ ] ksBytes = file.getBytes();

			Keystore keystoreUser = keystoreService.getKeystoreById(Keystore.ID_USER_STORE);
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreUser);
			X509Certificate certificate = null;
			certificate = UtilsCertificate.getCertificate(ksBytes);
			String alias = UtilsCertificate.createCertificateAlias(certificate, null);
			// Valida el certificado y lo añade al almacén truststore
			// ssl del sistema
			keystoreUser = keyStoreFacade.storeCertificate(alias, certificate, null);
			// Modificamos el keystore correspondiente, añadiendo el certificado
			keystoreService.saveKeystore(keystoreUser);

			systemCertificate.setAlias(alias);
			systemCertificate.setIssuer(UtilsCertificate.getCertificateIssuerId(certificate));
			systemCertificate.setKey(Boolean.FALSE);
			systemCertificate.setKeystore(keystoreUser);
			systemCertificate.setSerialNumber(UtilsCertificate.getCertificateSerialNumber(certificate));
			// De momento lo ponemos como válido, pero en este punto habría que
			// validar
			// contra @firma y si no es válido no debe permitir crear el
			// registro
			systemCertificate.setStatusCertificate(statusCertService.getStatusCertificateById(1L));
			systemCertificate.setSubject(UtilsCertificate.getCertificateId(certificate));
			systemCertificate.setUserMonitoriza(userService.getUserMonitorizaById(idUserMonitoriza));
			certService.saveSystemCertificate(systemCertificate);
			listNewSystemCert.add(systemCertificate);
		} catch (Exception e) {
			listNewSystemCert = StreamSupport.stream(certService.findCertUserByUser(idUserMonitoriza).spliterator(), false).collect(Collectors.toList());
			try {
				throw e;
			} catch (Exception e1) {
				LOGGER.error(e.getMessage());
			}
		}

		dtOutput.setData(listNewSystemCert);

		return dtOutput;
	}

	/**
	 * Method that maps the delete system certificate request from datatable to the controller
	 * and performs the delete of the system certificate identified by its id.
	 *
	 * @param systermCertificateId
	 *            Identifier of the system certificate to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 * @throws CryptographyException
	 * @throws IOException
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletecertuser", method = RequestMethod.POST)
	public String deleteCertUser(@RequestParam("id") final Long systermCertId, @RequestParam("index") final String index) throws CryptographyException, IOException {

		SystemCertificate systemCertificate = certService.getSystemCertificateById(systermCertId);
		Keystore keystoreUser = keystoreService.getKeystoreById(Keystore.ID_USER_STORE);
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreUser);
		keyStoreFacade.deleteCertificate(systemCertificate.getAlias());

		certService.deleteSystemCertificate(systermCertId);

		return index;
	}

	/**
	 * Get userService.
	 * @return userService
	 */
	public IUserMonitorizaService getUserService() {
		return userService;
	}

	/**
	 * Set userService.
	 * @param userServiceP set userService
	 */
	public void setUserService(final IUserMonitorizaService userServiceP) {
		this.userService = userServiceP;
	}

	/**
	 * Get certService.
	 * @return certService
	 */
	public ISystemCertificateService getCertService() {
		return certService;
	}

	/**
	 * Set certService
	 * @param certServiceP set certService
	 */
	public void setCertService(final ISystemCertificateService certServiceP) {
		this.certService = certServiceP;
	}

	/**
	 * Get keystoreService.
	 * @return keystoreService
	 */
	public IKeystoreService getKeystoreService() {
		return keystoreService;
	}

	/**
	 * Set keystoreService.
	 * @param keystoreServiceP
	 */
	public void setKeystoreService(final IKeystoreService keystoreServiceP) {
		this.keystoreService = keystoreServiceP;
	}

	/**
	 * Get statusCertService.
	 * @return statusCertService
	 */
	public IStatusCertificateService getStatusCertService() {
		return statusCertService;
	}

	/**
	 * Set statusCertService.
	 * @param staCertServiceP set statusCertService
	 */
	public void setStatusCertService(final IStatusCertificateService staCertServiceP) {
		this.statusCertService = staCertServiceP;
	}

}
