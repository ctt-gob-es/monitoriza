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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.KeystoreRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16 may. 2018.
 */
package es.gob.monitoriza.rest.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.axis.utils.ByteArray;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.form.CertificateForm;
import es.gob.monitoriza.form.PickListElement;
import es.gob.monitoriza.form.PickListForm;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IStatusCertificateService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.IValidServiceService;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;
import es.gob.monitoriza.utilidades.StatusCertificateEnum;
import es.gob.monitoriza.utilidades.UtilsCertificate;
import es.gob.monitoriza.utilidades.UtilsXml;
import es.gob.monitoriza.webservice.ClientManager;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16 may. 2018.
 */
@RestController
public class KeystoreRestController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the identifier of the html input text field for the SSL alias certificate. 
	 */
	private static final String FIELD_ALIAS = "alias";

	/**
	 * Attribute that represents the identifier of the html input file field for the keystore file. 
	 */
	private static final String FIELD_FILE = "file";

	/**
	 * Attribute that represents the identifier of the html input password field for the RFC3161 keystore's password. 
	 */
	private static final String FIELD_AUTH_PASSWORD = "authkeystorepass";

	/**
	 * Attribute that represents the identifier of the html input password field for the valid service keystore's password. 
	 */
	private static final String FIELD_VALID_SERVICE_PASSWORD = "validservicekeystorepass";

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IKeystoreService keystoreService;

	/**
	 * KeyStore representation of the data to add.
	 */
	private KeyStore ksFromDataToAdd = null;

	/**
	 * Attribute that represents the password for the loaded keystore. 
	 */
	private String ksPassword;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ISystemCertificateService certificateService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IStatusCertificateService statusCertService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IValidServiceService validServiceService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ClientManager clientManager;

	/**
	 * Attribute that represents the context object.
	 */
	@Autowired
	private ServletContext context;

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/ssldatatable", method = RequestMethod.GET)
	public DataTablesOutput<SystemCertificate> listSslCertificates(@Valid DataTablesInput input) {

		return (DataTablesOutput<SystemCertificate>) certificateService.findAllSsl(input);

	}

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/authdatatable", method = RequestMethod.GET)
	public DataTablesOutput<SystemCertificate> listAuthCertificates(@Valid DataTablesInput input) {

		return (DataTablesOutput<SystemCertificate>) certificateService.findAllAuth(input);

	}

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/validservicekeystoredatatable", method = RequestMethod.GET)
	public DataTablesOutput<SystemCertificate> listValidServiceCertificates(@Valid DataTablesInput input) {

		return (DataTablesOutput<SystemCertificate>) certificateService.findAllValidService(input);

	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savessl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DataTablesOutput<SystemCertificate> saveSsl(@RequestParam(FIELD_FILE) MultipartFile file, @RequestParam(FIELD_ALIAS) String alias) throws Exception {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		boolean error = false;
		byte[ ] certBytes = null;
		JSONObject json = new JSONObject();
		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (alias != null && alias.length() != alias.trim().length()) {

			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object[ ] { alias }));
			json.put(FIELD_ALIAS + "_span", "El campo alias no puede tener espacios blancos");
			error = true;
		}

		if (alias == null) {

			json.put(FIELD_ALIAS + "_span", "El campo alias es obligatorio");
			error = true;
		}

		if (file == null || file.getSize() == 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_NULL_FILE_CERT, new Object[ ] { alias }));
			json.put(FIELD_FILE + "_span", "Es obligatorio seleccionar un archivo de certifiado");
			error = true;
		} else {
			certBytes = file.getBytes();
		}

		String listChar = StaticMonitorizaProperties.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = GeneralConstants.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = alias.indexOf(characters[i]);
			if (esta >= 0) {
				char special = alias.charAt(esta);
				res += special + GeneralConstants.BLANK;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object[ ] { alias }));
			json.put(FIELD_FILE + "_span", "El formato del campo alias es incorrecto");
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_TRUSTSTORE_SSL));

				X509Certificate cert = UtilsCertificate.getCertificate(certBytes);

				String issuer = UtilsCertificate.getCertificateIssuerId(cert);
				String subject = UtilsCertificate.getCertificateId(cert);
				BigInteger serialNumber = UtilsCertificate.getCertificateSerialNumber(cert);

				Keystore ko = null;

				// Alta de certificado

				// Valida el certificado y lo añade al almacén truststore
				// ssl del sistema

				ko = keyStoreFacade.storeCertificate(alias, cert, null);
				// Modificamos el keystore correspondiente, añadiendo el
				// certificado
				if (sysCertService.getSystemCertificateByKsAndIssAndSn(ko, issuer, serialNumber) != null) {
					LOGGER.error("Error al guardar el certificado, el certificado con alias " + alias + " ya existe en el almacén");
					throw new Exception(GeneralConstants.CERTIFICATE_STORED);
				}

				keystoreService.saveKeystore(ko);

				SystemCertificate sysCert = new SystemCertificate();

				sysCert.setAlias(alias);
				sysCert.setIssuer(issuer);
				sysCert.setSubject(subject);
				sysCert.setKeystore(ko);
				sysCert.setKey(true);
				sysCert.setSerialNumber(serialNumber);
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(StatusCertificateEnum.VALID.getId()));

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);
				listSystemCertificate.add(sysCert);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.KEY_PAIR_ADDED, new Object[ ] { alias }));

			} catch (Exception e) {
				LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object[ ] { alias }), e);
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				throw e;

			}
		} else {
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			dtOutput.setError(json.toString());
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(PickListForm.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PickListForm loadauth(@RequestParam(FIELD_FILE) MultipartFile file, @RequestParam(FIELD_AUTH_PASSWORD) String password) throws IOException {

		byte[ ] ksBytes = null;
		PickListForm pickList = new PickListForm();
		List<PickListElement> listAliases = new ArrayList<>();
		String error = null;
		ksBytes = file.getBytes();

		try {

			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_AUTHCLIENT_RFC3161));

			ksFromDataToAdd = KeystoreFacade.getKeystore(ksBytes, keyStoreFacade.getKeystoreType(file.getOriginalFilename()), password);
			ksPassword = password;

			for (String alias: keyStoreFacade.listAllAliases(ksFromDataToAdd)) {
				listAliases.add(new PickListElement(alias, alias));
			}

		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException e) {
			LOGGER.equals(Language.getFormatResWebMonitoriza(LogMessages.ERROR_LISTING_ALIASES, new Object[ ] { file.getOriginalFilename() }));
		} catch (IOException ioe) {

			if (ioe.getCause() instanceof UnrecoverableKeyException) {
				// La contraseña es incorrecta
				error = "La contraseña introducida no es correcta.";
			}
		}

		pickList.setLista(listAliases);
		pickList.setError(error);

		return pickList;

	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(PickListForm.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadvalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PickListForm loadValidService(@RequestParam(FIELD_FILE) MultipartFile file, @RequestParam(FIELD_VALID_SERVICE_PASSWORD) String password) throws IOException {

		byte[ ] ksBytes = null;
		PickListForm pickList = new PickListForm();
		List<PickListElement> listAliases = new ArrayList<>();
		String error = null;
		ksBytes = file.getBytes();

		try {

			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_VALID_SERVICE_STORE));

			ksFromDataToAdd = KeystoreFacade.getKeystore(ksBytes, keyStoreFacade.getKeystoreType(file.getOriginalFilename()), password);
			ksPassword = password;

			for (String alias: keyStoreFacade.listAllAliases(ksFromDataToAdd)) {
				listAliases.add(new PickListElement(alias, alias));
			}

		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException e) {
			LOGGER.equals(Language.getFormatResWebMonitoriza(LogMessages.ERROR_LISTING_ALIASES, new Object[ ] { file.getOriginalFilename() }));
		} catch (IOException ioe) {

			if (ioe.getCause() instanceof UnrecoverableKeyException) {
				// La contraseña es incorrecta
				error = "La contraseña introducida no es correcta.";
			}
		}

		pickList.setLista(listAliases);
		pickList.setError(error);

		return pickList;

	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatessl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DataTablesOutput<SystemCertificate> updateSsl(@RequestBody CertificateForm sslForm, BindingResult bindingResult) throws IOException {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		boolean error = false;

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		if (bindingResult.hasErrors()) {
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		}

		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (sslForm.getAlias() != null && sslForm.getAlias().length() != sslForm.getAlias().trim().length()) {

			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object[ ] { sslForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaProperties.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = GeneralConstants.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = sslForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = sslForm.getAlias().charAt(esta);
				res += special + GeneralConstants.BLANK;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object[ ] { sslForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_TRUSTSTORE_SSL));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(sslForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				Keystore ko = keyStoreFacade.updateCertificate(oldCert.getAlias(), sslForm.getAlias());

				// Modificamos el keystore correspondiente, añadiendo el
				// certificado
				keystoreService.saveKeystore(ko);

				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setIdSystemCertificate(sslForm.getIdSystemCertificate());
				sysCert.setAlias(sslForm.getAlias());
				sysCert.setIssuer(sslForm.getIssuer());
				sysCert.setSubject(sslForm.getSubject());
				sysCert.setSerialNumber(sslForm.getSerialNumber());
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(sslForm.getIdSystemCertificate()));
				;
				sysCert.setKeystore(ko);
				sysCert.setKey(false);

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);

				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.SYS_CERT_ADDED, new Object[ ] { sslForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object[ ] { sslForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that maps the delete ssl certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param userId
	 *            Identifier of the ssl certificate to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletessl", method = RequestMethod.POST)
	public String deleteSsl(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index) {
		try {
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_TRUSTSTORE_SSL));
			SystemCertificate cert = sysCertService.getSystemCertificateById(systemCertificateId);

			Keystore ko = keyStoreFacade.deleteCertificate(cert.getAlias());

			keystoreService.saveKeystore(ko);
			sysCertService.deleteSystemCertificate(systemCertificateId);

		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

	/**
	 * Method that maps the delete ssl certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param userId
	 *            Identifier of the ssl certificate to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteauth", method = RequestMethod.POST)
	@Transactional
	public String deleteAuth(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index) {
		try {
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_AUTHCLIENT_RFC3161));
			SystemCertificate cert = sysCertService.getSystemCertificateById(systemCertificateId);

			Keystore ko = keyStoreFacade.deleteCertificate(cert.getAlias());

			keystoreService.saveKeystore(ko);
			sysCertService.deleteSystemCertificate(systemCertificateId);

		} catch (Exception e) {
			index = "-1";
		}
		return index;
	}

	/**
	 * Method that maps the delete valid service certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param userId
	 *            Identifier of the ssl certificate to be deleted.
	 * @param index
	 *            Row index of the datatable.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletevalidservicekeystore", method = RequestMethod.POST)
	public String deleteValidService(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index) throws Exception {
		try {
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_VALID_SERVICE_STORE));
			SystemCertificate cert = sysCertService.getSystemCertificateById(systemCertificateId);

			Keystore ko = keyStoreFacade.deleteCertificate(cert.getAlias());

			keystoreService.saveKeystore(ko);
			sysCertService.deleteSystemCertificate(systemCertificateId);

		} catch (Exception e) {
			index = "-1";
			throw e;
		}
		return index;
	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> saveAuth(@RequestBody List<PickListElement> aliases) throws Exception {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		try {

			if (aliases != null && !aliases.isEmpty()) {

				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_AUTHCLIENT_RFC3161));
				char[ ] password = ksPassword.toCharArray();

				Iterator<PickListElement> aliasIt = aliases.iterator();
				String alias;
				Key key;
				Certificate cert;
				Keystore ko = null;
				SystemCertificate sysCert = new SystemCertificate();

				while (aliasIt.hasNext()) {
					alias = aliasIt.next().getText();
					key = ksFromDataToAdd.getKey(alias, password);
					cert = ksFromDataToAdd.getCertificate(alias);
					String issuer = null;
					String subject = null;
					BigInteger serialNumber = null;
					if (cert != null) {
						X509Certificate x509Cert = UtilsCertificate.getCertificate(cert.getEncoded());
						issuer = UtilsCertificate.getCertificateIssuerId(x509Cert);
						subject = UtilsCertificate.getCertificateId(x509Cert);
						serialNumber = UtilsCertificate.getCertificateSerialNumber(x509Cert);
					}

					// Valida el certificado y lo añade al almacén truststore
					// ssl del sistema
					String certificateBase64 = Base64.getEncoder().encodeToString(cert.getEncoded());

					List<ValidService> validServices = validServiceService.getAllValidServices();
					ValidService validService = null;
					if (!validServices.isEmpty()) {
						validService = validServices.get(0);
					}

					if (validService != null) {

						String protocol = validService.getIsSecure() != null && validService.getIsSecure() ? UtilsCertificate.PROTOCOL_HTTPS : UtilsCertificate.PROTOCOL_HTTP;
						String host = validService.getHost();
						String port = validService.getPort();

						String result = "";
						String endpoint = protocol + "://" + host + ":" + port + UtilsCertificate.VALID_SERVICE_ENDPOINT;
						Object[ ] peticion = UtilsXml.getXmlValidation(context.getRealPath(UtilsCertificate.PATH_CERT_VALIDATION_REPORT), validService.getApplication(), certificateBase64);
						try {
							result = clientManager.getDSSCertificateServiceClientResult(endpoint, validService, peticion);
						} catch (Exception e) {
							LOGGER.error("Se ha producido un error al obtener el servicio DSSCertificate: " + e.getMessage());
						}

						Long statusCertificateId = UtilsCertificate.processStatusCertificate(result);
						boolean validResult = Boolean.FALSE;
						if (statusCertificateId.equals(StatusCertificateEnum.VALID.getId()) || statusCertificateId.equals(StatusCertificateEnum.UNKNOWN.getId())) {
							validResult = Boolean.TRUE;
						}

						if (!validResult) {
							LOGGER.error("Error al validar el certificado con alias " + alias + " , certificado no válido");
							throw new Exception(GeneralConstants.CERTIFICATE_NOT_VALID);
						}

						ko = keyStoreFacade.storeCertificate(alias, cert, key);
						// Modificamos el keystore correspondiente, añadiendo el
						// certificado

						if (sysCertService.getSystemCertificateByKsAndIssAndSn(ko, issuer, serialNumber) != null) {
							LOGGER.error("Error al guardar el certificado, el certificado con alias " + alias + " ya existe en el almacén");
							throw new Exception(GeneralConstants.CERTIFICATE_STORED);
						}

						keystoreService.saveKeystore(ko);

						sysCert.setAlias(alias);
						sysCert.setIssuer(issuer);
						sysCert.setSubject(subject);
						sysCert.setKeystore(ko);
						sysCert.setKey(true);
						sysCert.setSerialNumber(serialNumber);
						sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(statusCertificateId));

						// Añade el certificado a la persistencia
						sysCertService.saveSystemCertificate(sysCert);
						listSystemCertificate.add(sysCert);

						// Importación correcta
						LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.KEY_PAIR_ADDED, new Object[ ] { alias }));
					} else {
						LOGGER.error("Error al guardar el certificado, el certificado con alias " + alias + " ya existe en el almacén");
						throw new Exception(GeneralConstants.VALID_SERVICE_NOT_CONFIGURED);
					}
				}

			}

		} catch (Exception e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_KEY_PAIR, new Object[ ] { aliases }), e);
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			throw e;
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;
	}

	/**
	 * Method that maps the save valid service certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savevalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> saveValidService(@RequestBody List<PickListElement> aliases) throws Exception {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		try {

			if (aliases != null && !aliases.isEmpty()) {

				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_VALID_SERVICE_STORE));
				char[ ] password = ksPassword.toCharArray();

				Iterator<PickListElement> aliasIt = aliases.iterator();
				String alias;
				Key key;
				Certificate cert;
				Keystore ko = null;
				SystemCertificate sysCert = new SystemCertificate();

				while (aliasIt.hasNext()) {
					alias = aliasIt.next().getText();
					key = ksFromDataToAdd.getKey(alias, password);
					cert = ksFromDataToAdd.getCertificate(alias);
					String issuer = null;
					String subject = null;
					BigInteger serialNumber = null;
					if (cert != null) {
						X509Certificate x509Cert = UtilsCertificate.getCertificate(cert.getEncoded());
						issuer = UtilsCertificate.getCertificateIssuerId(x509Cert);
						subject = UtilsCertificate.getCertificateId(x509Cert);
						serialNumber = UtilsCertificate.getCertificateSerialNumber(x509Cert);
					}

					// Valida el certificado y lo añade al almacén valid service
					// valid service del sistema
					String certificateBase64 = Base64.getEncoder().encodeToString(cert.getEncoded());

					List<ValidService> validServices = validServiceService.getAllValidServices();
					ValidService validService = null;
					if (!validServices.isEmpty()) {
						validService = validServices.get(0);
					}

					if (validService != null) {

						String protocol = validService.getIsSecure() != null && validService.getIsSecure() ? UtilsCertificate.PROTOCOL_HTTPS : UtilsCertificate.PROTOCOL_HTTP;
						String host = validService.getHost();
						String port = validService.getPort();

						String result = "";
						String endpoint = protocol + "://" + host + ":" + port + UtilsCertificate.VALID_SERVICE_ENDPOINT;
						Object[ ] peticion = UtilsXml.getXmlValidation(context.getRealPath(UtilsCertificate.PATH_CERT_VALIDATION_REPORT), validService.getApplication(), certificateBase64);
						try {
							result = clientManager.getDSSCertificateServiceClientResult(endpoint, validService, peticion);
						} catch (Exception e) {
							LOGGER.error("Se ha producido un error al obtener el servicio DSSCertificate: " + e.getMessage());
						}

						Long statusCertificateId = UtilsCertificate.processStatusCertificate(result);
						boolean validResult = Boolean.FALSE;
						if (statusCertificateId.equals(StatusCertificateEnum.VALID.getId()) || statusCertificateId.equals(StatusCertificateEnum.UNKNOWN.getId())) {
							validResult = Boolean.TRUE;
						}

						if (!validResult) {
							LOGGER.error("Error al validar el certificado con alias " + alias + " , certificado no válido");
							throw new Exception(GeneralConstants.CERTIFICATE_NOT_VALID);
						}

						ko = keyStoreFacade.storeCertificate(alias, cert, key);
						// Modificamos el keystore correspondiente, añadiendo el
						// certificado

						if (sysCertService.getSystemCertificateByKsAndIssAndSn(ko, issuer, serialNumber) != null) {
							LOGGER.error("Error al guardar el certificado, el certificado con alias " + alias + " ya existe en el almacén");
							throw new Exception(GeneralConstants.CERTIFICATE_STORED);
						}

						keystoreService.saveKeystore(ko);

						sysCert.setAlias(alias);
						sysCert.setIssuer(issuer);
						sysCert.setSubject(subject);
						sysCert.setKeystore(ko);
						sysCert.setKey(true);
						sysCert.setSerialNumber(serialNumber);
						sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(statusCertificateId));

						// Añade el certificado a la persistencia
						sysCertService.saveSystemCertificate(sysCert);
						listSystemCertificate.add(sysCert);

						// Importación correcta
						LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.KEY_PAIR_ADDED, new Object[ ] { alias }));
					} else {
						LOGGER.error("Error al guardar el certificado, el certificado con alias " + alias + " ya existe en el almacén");
						throw new Exception(GeneralConstants.VALID_SERVICE_NOT_CONFIGURED);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_KEY_PAIR, new Object[ ] { aliases }), e);
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			throw e;
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;
	}

	/**
	 * Method that maps the save authentication RFC3161 certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param sslForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DataTablesOutput<SystemCertificate> updateAuth(@RequestBody CertificateForm authForm, BindingResult bindingResult) throws IOException {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		boolean error = false;

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		if (bindingResult.hasErrors()) {
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		}

		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (authForm.getAlias() != null && authForm.getAlias().length() != authForm.getAlias().trim().length()) {

			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object[ ] { authForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaProperties.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = GeneralConstants.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = authForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = authForm.getAlias().charAt(esta);
				res += special + GeneralConstants.BLANK;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object[ ] { authForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_AUTHCLIENT_RFC3161));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(authForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				Keystore ko = keyStoreFacade.updateCertificate(oldCert.getAlias(), authForm.getAlias());

				// Modificamos el keystore correspondiente, añadiendo el
				// certificado
				keystoreService.saveKeystore(ko);

				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setIdSystemCertificate(authForm.getIdSystemCertificate());
				sysCert.setAlias(authForm.getAlias());
				sysCert.setIssuer(authForm.getIssuer());
				sysCert.setSubject(authForm.getSubject());
				sysCert.setKeystore(ko);
				sysCert.setKey(true);
				sysCert.setSerialNumber(authForm.getSerialNumber());
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(authForm.getIdStatusCertificate()));

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);

				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.SYS_CERT_ADDED, new Object[ ] { authForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object[ ] { authForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that maps the save authentication valid service certificate web request to the controller and saves it in the persistence.
	 * @param file 
	 * @param validServForm Object that represents the backing user form. 
	 * @return 
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatevalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DataTablesOutput<SystemCertificate> updateValidService(@RequestBody CertificateForm validServForm, BindingResult bindingResult) throws IOException {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		boolean error = false;

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		if (bindingResult.hasErrors()) {
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			JSONObject json = new JSONObject();
			for (FieldError o: bindingResult.getFieldErrors()) {
				json.put(o.getField() + "_span", o.getDefaultMessage());
			}
			dtOutput.setError(json.toString());
		}

		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (validServForm.getAlias() != null && validServForm.getAlias().length() != validServForm.getAlias().trim().length()) {

			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object[ ] { validServForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaProperties.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = GeneralConstants.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = validServForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = validServForm.getAlias().charAt(esta);
				res += special + GeneralConstants.BLANK;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object[ ] { validServForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_VALID_SERVICE_STORE));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(validServForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				Keystore ko = keyStoreFacade.updateCertificate(oldCert.getAlias(), validServForm.getAlias());

				// Modificamos el keystore correspondiente, añadiendo el
				// certificado
				keystoreService.saveKeystore(ko);

				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setIdSystemCertificate(validServForm.getIdSystemCertificate());
				sysCert.setAlias(validServForm.getAlias());
				sysCert.setIssuer(validServForm.getIssuer());
				sysCert.setSubject(validServForm.getSubject());
				sysCert.setKeystore(ko);
				sysCert.setKey(true);
				sysCert.setSerialNumber(validServForm.getSerialNumber());
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(validServForm.getIdStatusCertificate()));

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);

				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(LogMessages.SYS_CERT_ADDED, new Object[ ] { validServForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object[ ] { validServForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that downloads a certificate.
	 * @param idSystemCertificate id certificate to download
	 * @param response to write the certificate to download
	 * @throws IOException exception
	 */
	@RequestMapping(value = "/downloadCertificate/{idSystemCertificate}")
	public void downloadCertificate(@PathVariable("idSystemCertificate") Long idSystemCertificate, HttpServletResponse response) throws IOException {
		byte[ ] x509CertBytes = null;
		try {
			SystemCertificate systemCertificate = certificateService.getSystemCertificateById(idSystemCertificate);
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(systemCertificate.getKeystore());
			Keystore ks = keystoreService.getKeystoreById(systemCertificate.getKeystore().getIdKeystore());
			KeyStore ksCetificate = KeystoreFacade.getKeystore(ks.getKeystore(), ks.getKeystoreType(), keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()));
			if (systemCertificate.getAlias() != null) {
				Certificate cert;
				cert = ksCetificate.getCertificate(systemCertificate.getAlias());
				if (cert != null) {
					x509CertBytes = cert.getEncoded();
				}
			}
			response.setHeader(UtilsCertificate.CONTENT_DISPOSITION_DOWNLOAD_CERTIFICATE, UtilsCertificate.HEADER_DOWNLOAD_CERTIFICATE);
			response.setContentType(UtilsCertificate.CONTENT_TYPE_DOWNLOAD_CERTIFICATE);
			FileCopyUtils.copy(x509CertBytes != null ? x509CertBytes : new ByteArray().toByteArray(), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Method that copy to the response the contents of the file requested
	 * @param idFile Identifier of the file to download
	 * @param response HttpServletResponse
	 * @throws IOException
	 * @throws RequestFileNotFoundException
	 */
	/*@RequestMapping(value = "/downloadCertificate", produces = "application/x-x509-ca-cert")
	public void downloadFile(@RequestParam("idSystemCertificate") Long idSystemCertificate, HttpServletResponse response) throws IOException {
	
		byte[ ] x509CertBytes = null;
		try {
			SystemCertificate systemCertificate = certificateService.getSystemCertificateById(idSystemCertificate);
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(systemCertificate.getKeystore());
			Keystore ks = keystoreService.getKeystoreById(systemCertificate.getKeystore().getIdKeystore());
			KeyStore ksCetificate = KeystoreFacade.getKeystore(ks.getKeystore(), ks.getKeystoreType(), keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()));
			if (systemCertificate.getAlias() != null) {
				Certificate cert;
				cert = ksCetificate.getCertificate(systemCertificate.getAlias());
				if (cert != null) {
					x509CertBytes = cert.getEncoded();
				}
			}
			String fileName = "attachment; filename=certificate.cer";
			response.setHeader("Content-Disposition", fileName);
			response.setContentType("application/x-x509-ca-cert");
			//response.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
			FileCopyUtils.copy(x509CertBytes != null ? x509CertBytes : new ByteArray().toByteArray(), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}*/

	/**
	 * Get keystoreService.
	 * @return keystoreService
	 */
	public IKeystoreService getKeystoreService() {
		return keystoreService;
	}

	/**
	 * Set keystoreService.
	 * @param keystoreService set keystoreService
	 */
	public void setKeystoreService(IKeystoreService keystoreService) {
		this.keystoreService = keystoreService;
	}

	/**
	 * Get ksFromDataToAdd.
	 * @return ksFromDataToAdd
	 */
	public KeyStore getKsFromDataToAdd() {
		return ksFromDataToAdd;
	}

	/**
	 * Set ksFromDataToAdd.
	 * @param ksFromDataToAdd set ksFromDataToAdd
	 */
	public void setKsFromDataToAdd(KeyStore ksFromDataToAdd) {
		this.ksFromDataToAdd = ksFromDataToAdd;
	}

	/**
	 * Get ksPassword.
	 * @return ksPassword
	 */
	public String getKsPassword() {
		return ksPassword;
	}

	/**
	 * Set ksPassword.
	 * @param ksPassword set ksPassword
	 */
	public void setKsPassword(String ksPassword) {
		this.ksPassword = ksPassword;
	}

	/**
	 * Get sysCertService.
	 * @return sysCertService
	 */
	public ISystemCertificateService getSysCertService() {
		return sysCertService;
	}

	/**
	 * Set sysCertService.
	 * @param sysCertService set sysCertService
	 */
	public void setSysCertService(ISystemCertificateService sysCertService) {
		this.sysCertService = sysCertService;
	}

	/**
	 * Get certificateService.
	 * @return certificateService
	 */
	public ISystemCertificateService getCertificateService() {
		return certificateService;
	}

	/**
	 * Set certificateService.
	 * @param certificateService set certificateService
	 */
	public void setCertificateService(ISystemCertificateService certificateService) {
		this.certificateService = certificateService;
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
	 * @param statusCertService set statusCertService
	 */
	public void setStatusCertService(IStatusCertificateService statusCertService) {
		this.statusCertService = statusCertService;
	}

	/**
	 * Get validServiceService.
	 * @return validServiceService
	 */
	public IValidServiceService getValidServiceService() {
		return validServiceService;
	}

	/**
	 * Set validServiceService.
	 * @param validServiceService set validServiceService
	 */
	public void setValidServiceService(IValidServiceService validServiceService) {
		this.validServiceService = validServiceService;
	}

	/**
	 * Get context.
	 * @return context
	 */
	public ServletContext getContext() {
		return context;
	}

	/**
	 * Set context.
	 * @param context set context
	 */
	public void setContext(ServletContext context) {
		this.context = context;
	}

	/**
	 * Get clientManager.
	 * @return clientManager
	 */
	public ClientManager getClientManager() {
		return clientManager;
	}

	/**
	 * Set clientManager.
	 * @param clientManagerP set clientManager
	 */
	public void setClientManager(ClientManager clientManagerP) {
		this.clientManager = clientManagerP;
	}

}
