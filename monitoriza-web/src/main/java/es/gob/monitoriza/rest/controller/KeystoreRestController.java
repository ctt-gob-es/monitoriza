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
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import es.gob.monitoriza.form.SslForm;
import es.gob.monitoriza.i18n.LanguageWeb;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;
import es.gob.monitoriza.utilidades.UtilsCertificate;

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
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IKeystoreService keystoreService; 
	
	/**
	 * KeyStore representation of the data to add.
	 */
	private KeyStore ksFromDataToAdd = null;
	
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
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path="/ssldatatable", method=RequestMethod.GET)
    public DataTablesOutput<SystemCertificate> listSslCertificates(@Valid DataTablesInput input){
		
		return (DataTablesOutput<SystemCertificate>) certificateService.findAllSsl(input);
				   	
    }
	
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path="/authdatatable", method=RequestMethod.GET)
    public DataTablesOutput<SystemCertificate> listAuthCertificates(@Valid DataTablesInput input){
		
		return (DataTablesOutput<SystemCertificate>) certificateService.findAllAuth(input);
				   	
    }
	
	/**
     * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
     * @param file 
     * @param sslForm Object that represents the backing user form. 
     * @return 
     */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/savessl", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody DataTablesOutput<SystemCertificate> saveSsl(@RequestParam("file") MultipartFile file, @RequestParam("alias") String alias) throws IOException {
    	
		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();
		
		boolean error = false;
		byte[] certBytes = null;
		
		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (alias != null && alias.length() != alias.trim().length()) {
			
			LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object [] {alias}));
			error = true;
		}
		
		if (file == null) {
			LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object [] {alias}));
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
			LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object [] {alias}));
			error = true;
		}
						
		if (!error) {
						
			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_TRUSTSTORE_SSL)); 
						
				X509Certificate cert = UtilsCertificate.getCertificate(certBytes);
				
				String issuer = UtilsCertificate.getCertificateIssuerId(cert);
				String subject = UtilsCertificate.getCertificateId(cert);
				
				Keystore ko = null;
				
				// Alta de certificado

				// Valida el certificado y lo añade al almacén truststore
				// ssl del sistema
				ko = keyStoreFacade.storeCertificate(alias, cert, null);
										
				// Modificamos el keystore correspondiente, añadiendo el certificado
				keystoreService.saveKeystore(ko);
				
				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setAlias(alias);
				sysCert.setIssuer(issuer);
				sysCert.setSubject(subject);
				sysCert.setKeystore(ko);
				sysCert.setKey(false);
								
				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);
				
				List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();
				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);
				
				// Importación correcta
				LOGGER.info(LanguageWeb.getFormatResWebMonitoriza(LogMessages.SYS_CERT_ADDED, new Object [] {alias}));
				
			} catch (Exception e) {
								
				LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object [] {alias}),e);
			
			} 
		}

		return dtOutput;

    }
	
	/**
     * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
     * @param file 
     * @param sslForm Object that represents the backing user form. 
     * @return 
     */
	@JsonView(DataTablesOutput.View.class)
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/updatessl", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody DataTablesOutput<SystemCertificate> updateSsl(@RequestBody SslForm sslForm) throws IOException {
    	
		DataTablesOutput<SystemCertificate> dtOutput = new DataTablesOutput<>();
		
		boolean error = false;
				
		// Comprobamos que se ha indicado el alias sin espacios ni caracteres
		// especiales.
		if (sslForm.getAlias() != null && sslForm.getAlias().length() != sslForm.getAlias().trim().length()) {
			
			LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_NOT_BLANK_ALIAS, new Object [] {sslForm.getAlias()}));
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
			LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_SPECIAL_CHAR_ALIAS, new Object [] {sslForm.getAlias()}));
			error = true;
		}
						
		if (!error) {
						
			try {
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(Keystore.ID_TRUSTSTORE_SSL)); 
								
				SystemCertificate oldCert = sysCertService.getSystemCertificateById(sslForm.getIdSystemCertificate());
				// Acualiza el alias del certificado
				Keystore ko = keyStoreFacade.updateCertificate(oldCert.getAlias(), sslForm.getAlias());
										
				// Modificamos el keystore correspondiente, añadiendo el certificado
				keystoreService.saveKeystore(ko);
				
				SystemCertificate sysCert = new SystemCertificate();
				sysCert.setIdSystemCertificate(sslForm.getIdSystemCertificate());
				sysCert.setAlias(sslForm.getAlias());
				sysCert.setIssuer(sslForm.getIssuer());
				sysCert.setSubject(sslForm.getSubject());
				sysCert.setKeystore(ko);
				sysCert.setKey(false);
								
				// Añade el certificado a la persistencia
				sysCertService.saveSystemCertificate(sysCert);
				
				List<SystemCertificate> listSystemCertificate = new ArrayList<SystemCertificate>();
				listSystemCertificate.add(sysCert);
				dtOutput.setData(listSystemCertificate);
				
				// Importación correcta
				LOGGER.info(LanguageWeb.getFormatResWebMonitoriza(LogMessages.SYS_CERT_ADDED, new Object [] {sslForm.getAlias()}));
				
			} catch (Exception e) {
								
				LOGGER.error(LanguageWeb.getFormatResWebMonitoriza(LogMessages.ERROR_ADDING_SYS_CERTS, new Object [] {sslForm.getAlias()}),e);
			
			} 
		}

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
}
