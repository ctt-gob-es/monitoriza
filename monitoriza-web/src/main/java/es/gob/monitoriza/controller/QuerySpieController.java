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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.QuerySpieController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/01/2019.
 */
package es.gob.monitoriza.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.gob.monitoriza.persistence.configuration.dto.DailySpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.service.IDailySpieMonitoringService;
import es.gob.monitoriza.service.IPlatformService;


/** 
 * <p>Class that manages the requests related to the queriying of daily VIP data by time range.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/01/2019.
 */
@Controller
public class QuerySpieController {
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private IDailySpieMonitoringService dailySpieService;
	
	/**
	 * Attribute that represents the service object for accessing the
	 * platform repository.
	 */
	@Autowired
	private IPlatformService platformService;
	
	/**
	 * Method that maps the list users web requests to the controller and forwards the list of platforms
	 * to the view.  
	 * @param model Holder object for model attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "queryspietimerange", method = RequestMethod.GET)
    public String querySpieTimeRange(Model model){
		
		List<CPlatformType> systems = new ArrayList<CPlatformType>();
		systems = StreamSupport.stream(platformService.getAllPlatformType().spliterator(), false)
				.collect(Collectors.toList());
		
		model.addAttribute("querySpieTimeResult", new ArrayList<DailySpieDTO>());
		model.addAttribute("systems", systems);
		
        return "fragments/queryspietimerange.html";
    }
	
	/**
	 * Method that maps the queries for service status between a time range.
	 * @param model Holder object for model attributes.
	 * @param minTime Lower time in seconds of day
	 * @param maxTime Upper time in seconds of day
	 * @param system String that represents the name of the platform type to filter
	 * @param service String that represents the name or part of the name of the service to filter
	 * @return String that represents the name of the view to forward.
	 */
	@RequestMapping(value = "queryspietime", method = RequestMethod.GET)
    public String querySpieTime(Model model, @RequestParam("minTimeSeconds") Long minTime, @RequestParam("maxTimeSeconds") Long maxTime, @RequestParam("system") String system, @RequestParam("service") String service){
		
		LocalTime minLocalTime = LocalTime.ofSecondOfDay(minTime);
		LocalTime maxLocalTime = LocalTime.ofSecondOfDay(maxTime);
		
		List<DailySpieDTO> dailiesResult = dailySpieService.findDailySpieTimeRange(LocalDateTime.of(LocalDate.now(), minLocalTime), LocalDateTime.of(LocalDate.now(), maxLocalTime), system, service);
		
		model.addAttribute("querySpieTimeResult", dailiesResult);
		
        return "fragments/queryspietable.html";
    }

}
