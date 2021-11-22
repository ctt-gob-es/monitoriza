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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.AlertControlPanelRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;
import es.gob.monitoriza.service.IAlertAuditService;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@RestController
public class AlertControlPanelRestController {
	
	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IAlertAuditService auditService;
	
	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 *
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/loadAuditInfoDatatable", method = RequestMethod.GET)
	public DataTablesOutput<AlertAudit> loadAuditInfoDatatable(@NotEmpty final DataTablesInput input, @RequestParam("dateMin") final String dateMin, @RequestParam("dateMax") final String dateMax) {
			
		DataTablesOutput<AlertAudit> result = null;
//		List<Order> listOrder = new ArrayList<>();
//		Order order = new Order(1, "desc");
//		listOrder.add(order);
//		input.setOrder(listOrder);
					
		if (dateMin.equals(UtilsStringChar.EMPTY_STRING) && dateMax.equals(UtilsStringChar.EMPTY_STRING)) {
			
			result = auditService.findAll(input);
			
		} else {
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ES","es"));
			Date min = null;
			Date max = null;
			
			try {
				
				if (!dateMin.equals(UtilsStringChar.EMPTY_STRING)) {
					 min = formatter.parse(dateMin);
				}
				
				if (!dateMax.equals(UtilsStringChar.EMPTY_STRING)) {
					max = formatter.parse(dateMax);
				}
			
				result = auditService.findAll(input, min, max);
				
			} catch (ParseException e) {
				
			}			
		}
		
		return result;
	}

}
