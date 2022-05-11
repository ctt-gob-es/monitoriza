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
 * <b>File:</b><p>es.gob.monitoriza.controller.NodeControler.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>09/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 11/05/2022.
 */
package es.gob.monitoriza.controller;

import es.gob.monitoriza.utilidades.loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.NodeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.service.INodeMonitorizaService;

/** 
 * <p>Class that manages the requests related to the Node administration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 11/05/2022.
 */
@Controller
public class NodeController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private INodeMonitorizaService nodeService;
		
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of SPIE nodes
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "nodesafirma", method = RequestMethod.GET)
    public String nodesAfirma(Model model){
        return "fragments/nodesafirma.html";
    }
	
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of SPIE nodes
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "nodestsa", method = RequestMethod.GET)
    public String nodeTsa(Model model){
        return "fragments/nodestsa.html";
    }
	
	/**
	 * Method that maps the add new node web request to the controller and sets the backing form. 
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addafirmanode", method = RequestMethod.POST)
    public String addNodeAfirma(Model model){
			
		model.addAttribute("nodeafirmaform", new NodeDTO());
		
		return "modal/nodeAfirmaForm";
		//return "modal/logForm";
    }	
	
	/**
	 * Method that maps the add new node web request to the controller and sets the backing form. 
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "addtsanode", method = RequestMethod.POST)
    public String addNodeTsa(Model model){
		model.addAttribute("nodetsaform", new NodeDTO());
		return "modal/nodeTsaForm";
    }	
	
	/**
     * Method that maps the edit node web request to the controller and loads the node to the
     * backing form.
     * @param nodeId Identifier of the node to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "editafirmanode", method = RequestMethod.POST)
    public String editNodeAfirma(@RequestParam("id") Long nodeId, Model model){
    	NodeMonitoriza node = nodeService.getNodeById(nodeId);
    	NodeDTO nodeForm = new NodeDTO();
    	
    	nodeForm.setHost(node.getHost());
    	nodeForm.setCheckAfirma(node.getCheckAfirma());
    	nodeForm.setCheckEmergencyDB(node.getCheckEmergencyDB());
    	nodeForm.setCheckHsm(node.getCheckHsm());
    	nodeForm.setCheckServices(node.getCheckServices());
    	nodeForm.setCheckTsa(node.getCheckTsa());
    	nodeForm.setHost(node.getHost());
    	nodeForm.setIdNode(node.getIdNode());
    	nodeForm.setType(node.getNodeType().getIdPlatformType());
    	nodeForm.setIsSecure(node.getIsSecure());
    	nodeForm.setName(node.getName());
    	nodeForm.setPort(node.getPort());
    	    	    	
    	model.addAttribute("nodeafirmaform", nodeForm);
    	
        return "modal/nodeAfirmaForm";
    }
    
    /**
     * Method that maps the edit node web request to the controller and loads the node to the
     * backing form.
     * @param nodeId Identifier of the node to be edited.
     * @param model Holder object for model attributes.
     * @return String that represents the name of the view to forward.
     */
    @RequestMapping(value = "edittsanode", method = RequestMethod.POST)
    public String editNodeTsa(@RequestParam("id") Long nodeId, Model model){
    	NodeMonitoriza node = nodeService.getNodeById(nodeId);
    	NodeDTO nodeForm = new NodeDTO();
    	
    	nodeForm.setHost(node.getHost());
    	nodeForm.setCheckAfirma(node.getCheckAfirma());
    	nodeForm.setCheckEmergencyDB(node.getCheckEmergencyDB());
    	nodeForm.setCheckHsm(node.getCheckHsm());
    	nodeForm.setCheckServices(node.getCheckServices());
    	nodeForm.setCheckTsa(node.getCheckTsa());
    	nodeForm.setHost(node.getHost());
    	nodeForm.setIdNode(node.getIdNode());
    	nodeForm.setType(node.getNodeType().getIdPlatformType());
    	nodeForm.setIsSecure(node.getIsSecure());
    	nodeForm.setName(node.getName());
    	nodeForm.setPort(node.getPort());
    	
    	model.addAttribute("nodetsaform", nodeForm);
        return "modal/nodeTsaForm";
    }

}
