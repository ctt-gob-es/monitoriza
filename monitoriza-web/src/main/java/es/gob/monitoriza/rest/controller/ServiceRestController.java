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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.ServiceRestController.java.</p>
 * <b>Description:</b><p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 abr. 2018.
 */
package es.gob.monitoriza.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.service.IPlatformService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;

/** 
 * <p>Class that manages the REST requests related to the Services administration and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 abr. 2018.
 */
@RestController
public class ServiceRestController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IServiceMonitorizaService serviceService;
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ITimerMonitorizaService timerService;
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IPlatformService platformService;
			
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of services
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path="/servicesdatatable", method=RequestMethod.GET)
    public DataTablesOutput<ServiceMonitoriza> services(@Valid DataTablesInput input){
		return (DataTablesOutput<ServiceMonitoriza>) serviceService.findAll(input);
				   	
    }
	
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of timees
	 * to the view.  
	 * @param input Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path="/timersdatatable", method=RequestMethod.GET)
    public DataTablesOutput<TimerMonitoriza> timers(@Valid DataTablesInput input){
		return (DataTablesOutput<TimerMonitoriza>) timerService.findAll(input);
				   	
    }
	
	/**
	 * Method that maps the request to get the service types for the selected platform type.
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path= "/loadservicetype", method=RequestMethod.GET)
    public List<String> loadservicetype(@RequestParam("idPlatform") Long idPlatform){
		
		PlatformMonitoriza platform = platformService.getPlatformById(idPlatform);
		List<String> serviceTypes = new ArrayList<String>();
		
		if (platform != null && platform.getPlatformType().getIdPlatformType().equals(IPlatformService.ID_PLATFORM_TYPE_AFIRMA)) {
			
			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.OCSP_SERVICE.toUpperCase());
			
		} else if (platform != null && platform.getPlatformType().getIdPlatformType().equals(IPlatformService.ID_PLATFORM_TYPE_TSA)) {
			
			serviceTypes.add(GeneralConstants.SOAP_SERVICE.toUpperCase());
			serviceTypes.add(GeneralConstants.RFC3161_SERVICE.toUpperCase());
		} 
								
        return serviceTypes;
    }
	
	/**
	 * Method that maps the request to get the service types for the selected platform type.
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(path= "/loadbaseendpoint", method=RequestMethod.GET)
    public Map<String,String> loadbaseendpoint(@RequestParam("idPlatform") Long idPlatform, @RequestParam("serviceType") String serviceType){
		
		PlatformMonitoriza platform = platformService.getPlatformById(idPlatform);
		StringBuilder baseEndpoint = new StringBuilder();
		baseEndpoint.append("http://").append(platform.getHost()).append(GeneralConstants.COLON).append(platform.getPort());
		
		switch (serviceType.toLowerCase()) {
			case GeneralConstants.SOAP_SERVICE:
				baseEndpoint.append(platform.getServiceContext());
				break;
			case GeneralConstants.OCSP_SERVICE:
				baseEndpoint.append(platform.getOcspContext());
				break;
			case GeneralConstants.RFC3161_SERVICE:
				baseEndpoint.append(platform.getRfc3161Context());
				break;
			default:
				break;
		}
				
								
        return Collections.singletonMap("response", baseEndpoint.toString());
    }

}
