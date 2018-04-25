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
 * <b>File:</b><p>es.gob.monitoriza.controller.ServiceController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>19 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 19 abr. 2018.
 */
package es.gob.monitoriza.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.form.ServiceForm;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.service.IPlatformService;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 19 abr. 2018.
 */
@Controller
public class ServiceController {
	
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
	 * Method that maps the list users web requests to the controller and forwards the list of users
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "serviceadmin")
    public String serviceadmin(Model model){
		
		List<PlatformMonitoriza> platforms = new ArrayList<PlatformMonitoriza>();
		List<String> serviceTypes = new ArrayList<String>();
				
		List<PlatformMonitoriza> plataforms = StreamSupport.stream(platformService.getAllPlatform().spliterator(), false).collect(Collectors.toList());
		platforms.addAll(plataforms);
		
		model.addAttribute("serviceform", new ServiceForm());
		model.addAttribute("platforms", platforms);
		model.addAttribute("serviceTypes", serviceTypes);
				
        return "fragments/serviceadmin.html";
    }
		
}
