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
 * <b>File:</b><p>es.gob.monitoriza.rest.controller.SystemNotificationRestController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.rest.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

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
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationOrigin;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationPriority;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationType;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification;
import es.gob.monitoriza.service.ISystemNotificationService;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that manages the REST requests related to the System Notification and JSON communication.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@RestController
public class SystemNotificationRestController {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ISystemNotificationService sysNotificationService;
	
	/**
	 * Method that maps the list users web requests to the controller and
	 * forwards the list of users to the view.
	 * 
	 * @param input
	 *            Holder object for datatable attributes.
	 * @return String that represents the name of the view to forward.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/sysnotificationdatatable", method = RequestMethod.GET)
	public DataTablesOutput<SystemNotification> dtSysNotifications(@NotEmpty DataTablesInput input) {
		
		// Se eliminan las notificaciones expiradas.
		sysNotificationService.clearExpiredNotifications();

		DataTablesOutput<SystemNotification> result = sysNotificationService.findAllSystemNotifications(input);
		
		// Cambiamos los valores de los tokens de la descripción.
		List<SystemNotification> sysnotListToShow = result.getData();
		
		if (sysnotListToShow != null) {
			String origin = null;
			String priority = null;
			String type = null;
			for (SystemNotification sysnot: sysnotListToShow) {
				origin = Language.getResPersistenceMonitoriza(sysnot.getNotificationOrigin().getTokenName());
				CNotificationOrigin originToShow = new CNotificationOrigin();
				originToShow.setTokenName(origin);
				sysnot.setNotificationOrigin(originToShow);
				priority = Language.getResPersistenceMonitoriza(sysnot.getNotificationPriority().getTokenName());
				CNotificationPriority priorityToShow = new CNotificationPriority();
				priorityToShow.setTokenName(priority);
				sysnot.setNotificationPriority(priorityToShow);
				type = Language.getResPersistenceMonitoriza(sysnot.getNotificationType().getTokenName());
				CNotificationType typeToShow = new CNotificationType();
				typeToShow.setTokenName(type);
				sysnot.setNotificationType(typeToShow);
				
			}
		}
		
		return result;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

	}
	
	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param selected String that represents the identifiers of the notifications to be deleted separated by comma.
	 * @param indexes String that represents the row indexes to be deleted separated by comma.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/deletesysnot", method = RequestMethod.GET)
	public String deleteSysNot(@RequestParam("ids") String selected, @RequestParam("indexes") String indexes) {
		
		String[] rowIndexes = indexes.split(UtilsStringChar.SYMBOL_COMMA_STRING);
		String[] ids = selected.split(UtilsStringChar.SYMBOL_COMMA_STRING);
		
		try {
			SystemNotification systemNotification = null;
			
			for (String id : Arrays.asList(ids)) {
				systemNotification = sysNotificationService.getSystemNotificationById(Long.parseLong(id));
				sysNotificationService.deleteSystemNotification(systemNotification);
			}
					
		} catch (Exception e) {
			rowIndexes[0] = GeneralConstants.ROW_INDEX_ERROR;
		}
		
		
		return indexes;
	}
	
	/**
	 * Method that maps the delete user request from datatable to the controller
	 * and performs the delete of the user identified by its id.
	 * 
	 * @param selected String that represents the identifiers of the notifications to be deleted separated by comma.
	 * @param indexes String that represents the row indexes to be deleted separated by comma.
	 * @return String that represents the name of the view to redirect.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(path = "/checksysnot", method = RequestMethod.GET)
	public String checkSysNot(@RequestParam("ids") String selected, @RequestParam("indexes") String indexes) {
		
		String[] rowIndexes = indexes.split(UtilsStringChar.SYMBOL_COMMA_STRING);
		String[] ids = selected.split(UtilsStringChar.SYMBOL_COMMA_STRING);
		
		try {
			SystemNotification systemNotification = null;
			
			for (String id : Arrays.asList(ids)) {
				systemNotification = sysNotificationService.getSystemNotificationById(Long.parseLong(id));
				sysNotificationService.checkNotification(systemNotification);
			}
					
		} catch (Exception e) {
			rowIndexes[0] = GeneralConstants.ROW_INDEX_ERROR;
		}
		
		
		return indexes;
	}

}
