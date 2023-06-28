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
 * <b>Date:</b><p>16/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.10, 11/05/2022.
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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;


import es.gob.monitoriza.utilidades.loggers.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
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
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.exception.RepositoryDeleteException;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.IWebViewMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.CertificateDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IStatusCertificateService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.ITimerMonitorizaService;
import es.gob.monitoriza.service.ITimerScheduledService;
import es.gob.monitoriza.service.IValidServiceService;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;
import es.gob.monitoriza.utilidades.UtilsCertificate;
import es.gob.monitoriza.utilidades.UtilsStringChar;
import es.gob.monitoriza.vo.PickListElementVO;
import es.gob.monitoriza.vo.PickListVO;
import es.gob.monitoriza.webservice.ClientManager;

/** 
 * <p>Class that manages the REST requests related to the Keystore administration
 * and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.10, 11/05/2022.
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
	private ITimerScheduledService scheduledService;
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ITimerMonitorizaService timerService;

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
	 * Attribute that represents the view message wource. 
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/ssldatatable", method = RequestMethod.GET)
	public DataTablesOutput<SystemCertificate> listSslCertificates(@NotEmpty DataTablesInput input) {

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
	public DataTablesOutput<SystemCertificate> listAuthCertificates(@NotEmpty DataTablesInput input) {

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
	public DataTablesOutput<SystemCertificate> listValidServiceCertificates(@NotEmpty DataTablesInput input) {

		return (DataTablesOutput<SystemCertificate>) certificateService.findAllValidService(input);

	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file Object that contains the uploaded file information.
	 * @param alias String that represents the SSL certificate alias to be stored
	 * @throws Exception If the method fails
	 * @return DataTablesOutput<SystemCertificate>
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

			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB003, new Object[ ] { alias }));
			json.put(FIELD_ALIAS + "_span", "El campo alias no puede tener espacios blancos");
			error = true;
		}

		if (alias == null) {

			json.put(FIELD_ALIAS + "_span", "El campo alias es obligatorio");
			error = true;
		}
		
		if (sysCertService.getSystemCertificateByAlias(alias) != null) {
			json.put(FIELD_ALIAS + "_span", "Ya existe en el sistema un certificado con alias: " + alias);
			error = true;
		}

		if (file == null || file.getSize() == 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB009, new Object[ ] { alias }));
			json.put(FIELD_FILE + "_span", "Es obligatorio seleccionar un archivo de certifiado");
			error = true;
		} else {
			certBytes = file.getBytes();
		}

		String listChar = StaticMonitorizaConfig.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = UtilsStringChar.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = alias.indexOf(characters[i]);
			if (esta >= 0) {
				char special = alias.charAt(esta);
				res += special + UtilsStringChar.SPECIAL_BLANK_SPACE_STRING;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB004, new Object[ ] { alias }));
			json.put(FIELD_FILE + "_span", "El formato del campo alias es incorrecto");
			error = true;
		}

		if (!error) {

			try {
				
				listSystemCertificate = keystoreService.saveSsl(alias, certBytes);

			} catch (Exception e) {
				LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB001, new Object[ ] { alias }), e);
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
	 * Method that maps the load authentication certificate web request to the controller and get the list of certificates in the uploaded keystore.
	 * @param file MultipartFile that represents the uploaded certificate
	 * @param password String that represents the password for the keystore 
	 * @return PickListForm
	 * @throws IOException If the method fails
	 */
	@JsonView(PickListVO.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PickListVO loadauth(@RequestParam(FIELD_FILE) MultipartFile file, @RequestParam(FIELD_AUTH_PASSWORD) String password) throws IOException {

		byte[ ] ksBytes = null;
		PickListVO pickList = new PickListVO();
		List<PickListElementVO> listAliases = new ArrayList<>();
		String error = null;
		ksBytes = file.getBytes();

		try {

			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161));

			ksFromDataToAdd = KeystoreFacade.getKeystore(ksBytes, keyStoreFacade.getKeystoreType(file.getOriginalFilename()), password);
			ksPassword = password;

			for (String alias: keyStoreFacade.listAllAliases(ksFromDataToAdd)) {
				listAliases.add(new PickListElementVO(alias, alias));
			}

		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB002, new Object[ ] { file.getOriginalFilename() }));
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
	 * @param file Multipart object that represents the uploaded keystore
	 * @param password String that represents the password for the keystore. 
	 * @return PickListForm
	 * @throws IOException If the method fails
	 */
	@JsonView(PickListVO.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/loadvalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PickListVO loadValidService(@RequestParam(FIELD_FILE) MultipartFile file, @RequestParam(FIELD_VALID_SERVICE_PASSWORD) String password) throws IOException {

		byte[ ] ksBytes = null;
		PickListVO pickList = new PickListVO();
		List<PickListElementVO> listAliases = new ArrayList<>();
		String error = null;
		ksBytes = file.getBytes();

		try {

			IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE));

			ksFromDataToAdd = KeystoreFacade.getKeystore(ksBytes, keyStoreFacade.getKeystoreType(file.getOriginalFilename()), password);
			ksPassword = password;

			for (String alias: keyStoreFacade.listAllAliases(ksFromDataToAdd)) {
				listAliases.add(new PickListElementVO(alias, alias));
			}

		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB002, new Object[ ] { file.getOriginalFilename() }));
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
	 * Method that maps the update ssl certificate web request to the controller and updates it in the persistence.
	 * @param sslForm Object that represents the backing ssl certificate form.
	 * @param bindingResult Object that represents the validation results
	 * @return DataTablesOutput<SystemCertificate>
	 * @throws IOException If the method fails
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatessl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> updateSsl(@RequestBody CertificateDTO sslForm, BindingResult bindingResult) throws IOException {

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

			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB003, new Object[ ] { sslForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaConfig.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = UtilsStringChar.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = sslForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = sslForm.getAlias().charAt(esta);
				res += special + UtilsStringChar.SPECIAL_BLANK_SPACE_STRING;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB004, new Object[ ] { sslForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_TRUSTSTORE_SSL));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(sslForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				KeystoreMonitoriza ko = keyStoreFacade.updateCertificateAlias(oldCert.getAlias(), sslForm.getAlias());

				// Modificamos el keystore correspondiente, añadiendo el
				// certificado
				keystoreService.saveKeystore(ko);

				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setIdSystemCertificate(sslForm.getIdSystemCertificate());
				sysCert.setAlias(sslForm.getAlias());
				sysCert.setIssuer(sslForm.getIssuer());
				sysCert.setSubject(sslForm.getSubject());
				sysCert.setSerialNumber(sslForm.getSerialNumber());
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(oldCert.getStatusCertificate().getIdStatusCertificate()));

				sysCert.setKeystore(ko);
				sysCert.setKey(false);

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);

				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(IWebLogMessages.WEB002, new Object[ ] { sslForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB001, new Object[ ] { sslForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that maps the delete ssl certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param systemCertificateId Identifier of the ssl certificate to be deleted.
	 * @param index Row index of the datatable.
	 * @param request Object containing the HTTP servlet request information
	 * @throws RepositoryDeleteException Error deleting the SSL certificate from persistence 
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletessl", method = RequestMethod.POST)
	public void deleteSsl(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index, HttpServletRequest request) throws RepositoryDeleteException {
		
		String error = null;
		
		try {
			
			keystoreService.delete(systemCertificateId, KeystoreMonitoriza.ID_TRUSTSTORE_SSL);
		
		} catch (CryptographyException e) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_CRYPTO, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		} catch (DataIntegrityViolationException dive) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_USED, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		}
				
	}

	/**
	 * Method that maps the delete ssl certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param systemCertificateId Identifier of the ssl certificate to be deleted.
	 * @param index Row index of the datatable.
	 * @param request Object containing the HTTP servlet request information
	 * @throws RepositoryDeleteException Error deleting the RFC3161 certificate from persistence
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deleteauth", method = RequestMethod.POST)
	public void deleteAuth(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index, HttpServletRequest request) throws RepositoryDeleteException {
		
		String error = null;

		try {

			keystoreService.delete(systemCertificateId, KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161);

		} catch (CryptographyException e) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_CRYPTO, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		} catch (DataIntegrityViolationException dive) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_USED, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		}
		
	}

	/**
	 * Method that maps the delete valid service certificate request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param systemCertificateId Identifier of the ssl certificate to be deleted.
	 * @param index Row index of the datatable.
	 * @param request Object containing the HTTP servlet request information
	 * @throws RepositoryDeleteException Error deleting the validation service certificate from persistence
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletevalidservicekeystore", method = RequestMethod.POST)
	@Transactional
	public void deleteValidService(@RequestParam("id") Long systemCertificateId, @RequestParam("index") String index, HttpServletRequest request) throws RepositoryDeleteException {
		
		String error = null;

		try {

			keystoreService.delete(systemCertificateId, KeystoreMonitoriza.ID_VALID_SERVICE_STORE);

		} catch (CryptographyException e) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_CRYPTO, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		} catch (DataIntegrityViolationException dive) {
			error = messageSource.getMessage(IWebViewMessages.ERROR_AUTH_DELETE_USED, null, request.getLocale());
			throw new RepositoryDeleteException(error);
		}
	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param aliases List<PickListElement> with the selected aliases for the certificates to be saved in persistence.
	 * @return DataTablesOutput<SystemCertificate>
	 * @throws Exception If the method fails
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/saveauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> saveAuth(@RequestBody List<PickListElementVO> aliases) throws Exception {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		try {

			if (aliases != null && !aliases.isEmpty()) {

				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161));
				char[ ] password = ksPassword.toCharArray();

				Iterator<PickListElementVO> aliasIt = aliases.iterator();
				String alias;
				Key key;
				Certificate cert;
				KeystoreMonitoriza ko = null;
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

						ko = keyStoreFacade.storeCertificate(alias, cert, key);
						// Modificamos el keystore correspondiente, añadiendo el
						// certificado

						if (sysCertService.getSystemCertificateByKsAndIssAndSn(ko, issuer, serialNumber) != null) {
							LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB014, new Object[] {alias}));
							throw new Exception(GeneralConstants.CERTIFICATE_STORED);
						}

						keystoreService.saveKeystore(ko);

						sysCert.setAlias(alias);
						sysCert.setIssuer(issuer);
						sysCert.setSubject(subject);
						sysCert.setKeystore(ko);
						sysCert.setKey(true);
						sysCert.setSerialNumber(serialNumber);
						sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(new Long(NumberConstants.NUM1)));

						// Añade el certificado a la persistencia
						sysCertService.saveSystemCertificate(sysCert);
						listSystemCertificate.add(sysCert);

						// Importación correcta
						LOGGER.info(Language.getFormatResWebMonitoriza(IWebLogMessages.WEB001, new Object[ ] { alias }));
						
						// Se comprueba qué timers se ven afectados de un cambio en el keystore de autenticación
						List<TimerMonitoriza> timersRfc3161 = timerService.findTimersAnyServiceUsingRFC3161Auth();
						
						if (timersRfc3161 != null && !timersRfc3161.isEmpty()) {
							for (TimerMonitoriza timer : timersRfc3161) {
								
								TimerScheduled scheduled = scheduledService.getTimerScheduledByIdTimer(timer.getIdTimer());
								
								if (scheduled != null) {
									
									scheduled.setUpdated(false);
									scheduledService.saveTimerScheduled(scheduled);
								}
							}
						}					
				}
			}

		} catch (Exception e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB007, new Object[ ] { aliases }), e);
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			throw e;
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;
	}

	/**
	 * Method that maps the save valid service certificate web request to the controller and saves it in the persistence.
	 * @param aliases List<PickListElement> with the alisases of the certificates to be stored in persistence.
	 * @return DataTablesOutput<SystemCertificate>
	 * @throws Exception If the method fails
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/savevalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> saveValidService(@RequestBody List<PickListElementVO> aliases) throws Exception {

		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();

		List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();

		try {

			if (aliases != null && !aliases.isEmpty()) {

				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE));
				char[ ] password = ksPassword.toCharArray();

				Iterator<PickListElementVO> aliasIt = aliases.iterator();
				String alias;
				Key key;
				Certificate cert;
				KeystoreMonitoriza ko = null;
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

					ko = keyStoreFacade.storeCertificate(alias, cert, key);
					// Modificamos el keystore correspondiente, añadiendo el
					// certificado

					if (sysCertService.getSystemCertificateByKsAndIssAndSn(ko, issuer, serialNumber) != null) {
						LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB014, new Object[ ] { alias }));
						throw new Exception(GeneralConstants.CERTIFICATE_STORED);
					}

					keystoreService.saveKeystore(ko);

					sysCert.setAlias(alias);
					sysCert.setIssuer(issuer);
					sysCert.setSubject(subject);
					sysCert.setKeystore(ko);
					sysCert.setKey(true);
					sysCert.setSerialNumber(serialNumber);
					sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(new Long(NumberConstants.NUM1)));

					// Añade el certificado a la persistencia
					sysCertService.saveSystemCertificate(sysCert);
					listSystemCertificate.add(sysCert);

					// Importación correcta
					LOGGER.info(Language.getFormatResWebMonitoriza(IWebLogMessages.WEB001, new Object[ ] { alias }));

				}
			}
		} catch (Exception e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB007, new Object[ ] { aliases }), e);
			listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
			throw e;
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;
	}

	/**
	 * Method that maps the save authentication RFC3161 certificate web request to the controller and saves it in the persistence.
	 * @param authForm Object that represents the backing certificate form.
	 * @param bindingResult Object that represents the validation results 
	 * @return DataTablesOutput<SystemCertificate>
	 * @throws IOException If the method fails
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updateauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> updateAuth(@RequestBody CertificateDTO authForm, BindingResult bindingResult) throws IOException {

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

			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB003, new Object[ ] { authForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaConfig.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = UtilsStringChar.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = authForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = authForm.getAlias().charAt(esta);
				res += special + UtilsStringChar.SPECIAL_BLANK_SPACE_STRING;;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB004, new Object[ ] { authForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(authForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				KeystoreMonitoriza ko = keyStoreFacade.updateCertificateAlias(oldCert.getAlias(), authForm.getAlias());

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
				sysCert.setStatusCertificate(statusCertService.getStatusCertificateById(oldCert.getStatusCertificate().getIdStatusCertificate()));

				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);

				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);

				// Importación correcta
				LOGGER.info(Language.getFormatResWebMonitoriza(IWebLogMessages.WEB002, new Object[ ] { authForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB001, new Object[ ] { authForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that maps the save authentication valid service certificate web request to the controller and saves it in the persistence.
	 * @param validServForm Object that represents the backing certificate form. 
	 * @param bindingResult Object that represents the validation results.
	 * @return {@link DataTablesOutput<SystemCertificate>}
	 * @throws IOException If the method fails
	 */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/updatevalidservicekeystore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public @ResponseBody DataTablesOutput<SystemCertificate> updateValidService(@RequestBody CertificateDTO validServForm, BindingResult bindingResult) throws IOException {

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

			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB003, new Object[ ] { validServForm.getAlias() }));
			error = true;
		}

		String listChar = StaticMonitorizaConfig.getProperty(StaticConstants.LIST_CHARACTER_SPECIAL);
		String[ ] characters = listChar.split(",");
		String res = UtilsStringChar.EMPTY_STRING;
		for (int i = 0; i < characters.length; i++) {
			int esta = validServForm.getAlias().indexOf(characters[i]);
			if (esta >= 0) {
				char special = validServForm.getAlias().charAt(esta);
				res += special + UtilsStringChar.SPECIAL_BLANK_SPACE_STRING;;
			}
		}

		if (res.length() > 0) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB004, new Object[ ] { validServForm.getAlias() }));
			error = true;
		}

		if (!error) {

			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE));

				SystemCertificate oldCert = sysCertService.getSystemCertificateById(validServForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				KeystoreMonitoriza ko = keyStoreFacade.updateCertificateAlias(oldCert.getAlias(), validServForm.getAlias());

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
				LOGGER.info(Language.getFormatResWebMonitoriza(IWebLogMessages.WEB002, new Object[ ] { validServForm.getAlias() }));

			} catch (Exception e) {
				listSystemCertificate = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());
				LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB001, new Object[ ] { validServForm.getAlias() }), e);

			}
		}

		dtOutput.setData(listSystemCertificate);
		return dtOutput;

	}

	/**
	 * Method that downloads a certificate.
	 * @param idSystemCertificate id certificate to download
	 * @param response to write the certificate to download
	 * @throws IOException If the method fails
	 */
	@RequestMapping(value = "/downloadCertificate/{idSystemCertificate}")
	public void downloadCertificate(@PathVariable("idSystemCertificate") Long idSystemCertificate, HttpServletResponse response) throws IOException {
		byte[ ] x509CertBytes = null;
		try {
			SystemCertificate systemCertificate = certificateService.getSystemCertificateById(idSystemCertificate);
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(systemCertificate.getKeystore());
			KeystoreMonitoriza ks = keystoreService.getKeystoreById(systemCertificate.getKeystore().getIdKeystore());
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
			FileCopyUtils.copy(x509CertBytes != null ? x509CertBytes : new byte[0], response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB008, new Object[] {idSystemCertificate}));
		}
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