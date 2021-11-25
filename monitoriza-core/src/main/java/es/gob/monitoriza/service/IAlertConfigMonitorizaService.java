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
 * <b>File:</b><p>es.gob.monitoriza.service.IUserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.AlertConfigDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertConfigMonitorizaService {

	/**
	 * Method that obtains a alert config by its identifier.
	 * @param alertConfigId The alert config identifier.
	 * @return {@link AlertConfigMonitoriza}
	 */
	AlertConfigMonitoriza getAlertConfigMonitorizaById(Long alertConfigId);

	/**
	 * Method that deletes a alert config in the persistence.
	 * @param templateId {@link Integer} that represents the alert config identifier to delete.
	 */
	void deleteAlertConfigMonitoriza(Long alertConfigId);

	/**
	 * Method that gets all the alert configurations from the persistence.
	 * @return a {@link Iterable<AlertConfigMonitoriza>} with the information of all alert configurations.
	 */
	Iterable<AlertConfigMonitoriza> getAllAlertConfigMonitoriza();

	/**
	 * Method that gets all the alert configurations from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<AlertConfigMonitoriza> findAll(DataTablesInput input);

	/**
	 * Method that stores a alert config in the persistence.
	 * @param alertConfigDTO a {@link AlertConfigDTO} with the information of the alert configuration.
	 * @return {@link AlertConfigMonitoriza} The alert configuration.
	 */
	AlertConfigMonitoriza saveAlertConfigMonitoriza(AlertConfigDTO alertConfigDTO);

	/**
	 * Method that obtains all alert configurations for a application.
	 * @param appMonitoriza Application to search.
	 * @return Alert configurations list.
	 */
	List<AlertConfigMonitoriza> getAllAlertConfigMonitorizaByApplicationMonitoriza(ApplicationMonitoriza appMonitoriza);

}
