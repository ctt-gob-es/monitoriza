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
 * <b>File:</b><p>es.gob.monitoriza.controller.SplController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 28/10/2018.
 */
package es.gob.monitoriza.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.SplDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SplMonitoriza;
import es.gob.monitoriza.service.ILogConsumerService;
import es.gob.monitoriza.service.ISplService;

/**
 * <p>Class that manages the requests related to the SPL administration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 15/03/2019.
 */
@Controller
public class SplController {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);

	/**
	 * Attribute that represents the service object for managing the SPLs.
	 */
	@Autowired
	private ISplService splService;

	/**
	 * Attribute that represents the service object for accessing to the logs.
	 */
	@Autowired
	private ILogConsumerService logConsumerService;

	/**
	 * Method that maps the web requests to the controller and forwards the list of SPL
	 * to the view.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "spl")
    public String spl(final Model model){
        return "fragments/spl.html";
    }

	/**
	 * Method that maps the add SPL web request to the controller and sets
	 * the backing form.
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addspl", method = RequestMethod.POST)
    public String addSpl(final Model model){
		model.addAttribute("splform", new SplDTO());
		return "modal/splForm";
    }

    /**
     * Method that maps the edit web request to the controller and loads
     * the SPL to the backing form.
     * @param afirmaId Identifier of the SPL to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "editspl", method = RequestMethod.POST)
    public String editSpl(@RequestParam("id") final Long splId, final Model model) {
    	final SplMonitoriza spl = this.splService.getSplById(splId);

    	final SplDTO splForm = new SplDTO();
    	splForm.setIdSpl(splId);
    	splForm.setName(spl.getName());
    	splForm.setDescription(spl.getDescription());
    	splForm.setType(spl.getType());
    	splForm.setUrl(spl.getUrl());
    	splForm.setKey(spl.getKey());
    	splForm.setIsSecure(spl.getIsSecure());

    	model.addAttribute("splform", splForm);
        return "modal/splForm";
    }

    /**
     * Method that maps the connecting request to the controller, open the
     * connection to the SPL and list its log files.
     * @param afirmaId Identifier of the SPL to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     * @throws IOException Error related with the connection to the SPL.
     */
    @RequestMapping(value = "connectspl", method = RequestMethod.POST)
    public String connectSpl(@RequestParam("id") final Long splId, final Model model) throws IOException {

    	final SplMonitoriza spl = this.splService.getSplById(splId);

    	// Configuramos el servicio para que en las siguientes llamadas se pueda
    	// recuperar la informacion de los logs
    	this.logConsumerService.configure(spl.getUrl(), spl.getKey());

        return "fragments/logfiles.html";
    }

    /**
     * Method that maps the connecting request to the controller, open the
     * connection to the SPL and list its log files.
     * @param afirmaId Identifier of the SPL to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     * @throws IOException Error related with the connection to the SPL.
     */
    @RequestMapping(value = "getlogfiles", method = RequestMethod.POST)
    public String getLogFiles(@RequestParam("id") final Long splId, final Model model) throws IOException {

    	final SplMonitoriza spl = this.splService.getSplById(splId);

    	this.logConsumerService.configure(spl.getUrl(), spl.getKey());

        return "fragments/logfiles.html";
    }
}
