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
 * <b>File:</b><p>es.gob.monitoriza.controller.KeystoreController.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.3 15/02/2019.
 */
package es.gob.monitoriza.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.persistence.configuration.dto.CertificateDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.service.ISystemCertificateService;

/**
 * <p>Class that maps the request for the keystores forms to the controller.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 15/02/2019.
 */
@Controller
public class KeystoreController {

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Method that maps the list keystore web requests to the controller and forwards the list of keystores
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "ssladmin", method = RequestMethod.GET)
	public String keystoressladmin(final Model model) {

		return "fragments/ssladmin.html";
	}

	/**
	 * Method that maps the list keystore web requests to the controller and forwards the list of keystores
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "authadmin", method = RequestMethod.GET)
	public String keystoreauthadmin(final Model model) {

		return "fragments/authadmin.html";
	}
	
	/**
	 * Method that maps the request to the validation keystore page.
	 * @param model Holder object for model attributes
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "keystorevalidserviceadmin", method = RequestMethod.GET)
	public String keystoreValidServiceAdmin(final Model model) {

		return "fragments/keystorevalidserviceadmin.html";
	}

	/**
	 * Method that maps the add keystore web requests to the controller and forwards to the form
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addssl", method = RequestMethod.POST)
	public String addSsl(final Model model) {

		model.addAttribute("sslform", new CertificateDTO());

		return "modal/sslForm.html";
	}

	/**
	 * Method that maps the add keystore web requests to the controller and forwards to the form
	 * to the view.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addauth", method = RequestMethod.POST)
	public String addAuth() {

		return "modal/authForm.html";
	}
	
	/**
	 * Method that maps the add keystore web requests to the controller and forwards to the form
	 * to the view.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addvalidservicekeystore", method = RequestMethod.POST)
	public String addValidService() {

		return "modal/validServForm.html";
	}

	/**
	 * Method that maps the save ssl certificate web request to the controller and saves it in the persistence.
	 * @param file Object that represents the certificate file.
	 * @param model Holder object for model attributes.
	 * @throws IOException Exception thrown if the action fails
	 * @return String that represents the name of the view to forward.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "loadssl", method = RequestMethod.POST)
	public String loadssl(@RequestParam("file") final MultipartFile file, final Model model) throws IOException {

		CertificateDTO sslForm = new CertificateDTO();

		sslForm.setCertBytes(file.getBytes());
		sslForm.setFile(file);

		model.addAttribute("sslform", sslForm);

		return "modal/sslForm.html";

	}

	/**
	 * Method that maps the edit certificate web request to the controller and loads the certificate to the
	 * backing form.
	 * @param certId Identifier of the certificate to be edited.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "editcert", method = RequestMethod.POST)
	public String editSsl(@RequestParam("id") final Long certId, final Model model) {
		SystemCertificate sysCert = sysCertService.getSystemCertificateById(certId);
		CertificateDTO certForm = new CertificateDTO();

		certForm.setAlias(sysCert.getAlias());
		certForm.setIssuer(sysCert.getIssuer());
		certForm.setSubject(sysCert.getSubject());
		certForm.setSerialNumber(sysCert.getSerialNumber());
		certForm.setIdSystemCertificate(certId);
		certForm.setIdStatusCertificate(sysCert.getStatusCertificate().getIdStatusCertificate());

		model.addAttribute("certform", certForm);
		return "modal/certEditForm";
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
	 * @param sysCertServiceP set sysCertService
	 */
	public void setSysCertService(final ISystemCertificateService sysCertServiceP) {
		this.sysCertService = sysCertServiceP;
	}

}
