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
 * <b>File:</b><p>es.gob.monitoriza.controller.PlatformController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 11/05/2022.
 */
package es.gob.monitoriza.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import es.gob.monitoriza.utilidades.loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.AfirmaDTO;
import es.gob.monitoriza.persistence.configuration.dto.TsaDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.ISystemCertificateService;

/**
 * <p>Class that manages the requests related to the Platform administration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.5, 11/05/2022.
 */
@Controller
public class PlatformController {

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
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of platforms
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "platformafirma", method = RequestMethod.GET)
    public String platformAfirma(final Model model){
        return "fragments/platformafirma.html";
    }

	/**
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "platformtsa", method = RequestMethod.GET)
    public String platformTsa(final Model model){
        return "fragments/platformtsa.html";
    }

	/**
	 * Method that maps the add afirma platform web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addafirma", method = RequestMethod.POST)
    public String addAfirma(final Model model){
		model.addAttribute("afirmaform", new AfirmaDTO());
		return "modal/afirmaForm";
    }

	/**
	 * Method that maps the add tsa platform web request to the controller and sets the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addtsa", method = RequestMethod.POST)
    public String addTsa(final Model model){

		final List<SystemCertificate> listCertificates = StreamSupport.stream(this.sysCertService.getAllAuth().spliterator(), false)
				.collect(Collectors.toList());

		model.addAttribute("tsaform", new TsaDTO());
		model.addAttribute("authcerts", listCertificates);
		return "modal/tsaForm";
    }

    /**
     * Method that maps the edit afirma platform web request to the controller and loads the afirma platform to the
     * backing form.
     * @param afirmaId Identifier of the @firma platform to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "editafirma", method = RequestMethod.POST)
    public String editAfirma(@RequestParam("id") final Long afirmaId, final Model model){
    	final PlatformMonitoriza afirma = this.platformService.getPlatformById(afirmaId);
    	final AfirmaDTO afirmaForm = new AfirmaDTO();

    	afirmaForm.setHost(afirma.getHost());
    	afirmaForm.setIdPlatform(afirmaId);
    	afirmaForm.setName(afirma.getName());
    	afirmaForm.setOcspContext(afirma.getOcspContext());
    	afirmaForm.setServiceContext(afirma.getServiceContext());
    	afirmaForm.setPort(afirma.getPort());
    	afirmaForm.setIsSecure(afirma.getIsSecure());

    	model.addAttribute("afirmaform", afirmaForm);
        return "modal/afirmaForm";
    }

    /**
     * Method that maps the edit tsa platform web request to the controller and loads the tsa platform to the
     * backing form.
     * @param tsaId Identifier of the ts@ platform to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "editatsa", method = RequestMethod.POST)
    public String editTsa(@RequestParam("id") final Long tsaId, final Model model){
    	final PlatformMonitoriza tsa = this.platformService.getPlatformById(tsaId);
    	final List<SystemCertificate> listCertificates = StreamSupport.stream(this.sysCertService.getAllAuth().spliterator(), false)
				.collect(Collectors.toList());

    	final TsaDTO tsaForm = new TsaDTO();

    	tsaForm.setHost(tsa.getHost());
    	tsaForm.setIdPlatform(tsaId);
    	tsaForm.setName(tsa.getName());
    	tsaForm.setServiceContext(tsa.getServiceContext());
    	tsaForm.setPort(tsa.getPort());
    	tsaForm.setRfc3161Context(tsa.getRfc3161Context());
    	tsaForm.setIsSecure(tsa.getIsSecure());
    	tsaForm.setRfc3161Port(tsa.getRfc3161Port());
    	tsaForm.setUseRfc3161Auth(tsa.getUseRfc3161Auth());

    	if (tsa.getUseRfc3161Auth() && tsa.getRfc3161Certificate() != null) {
    		tsaForm.setRfc3161Certificate(tsa.getRfc3161Certificate().getIdSystemCertificate());
    	}

    	model.addAttribute("tsaform", tsaForm);
    	model.addAttribute("authcerts", listCertificates);
        return "modal/tsaForm";
    }

}
