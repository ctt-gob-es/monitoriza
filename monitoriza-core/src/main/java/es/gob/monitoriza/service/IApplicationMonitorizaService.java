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

import es.gob.monitoriza.persistence.configuration.dto.ApplicationDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IApplicationMonitorizaService {

	/**
	 * Method that obtains a app by its identifier.
	 * @param templateId The app identifier.
	 * @return {@link ApplicationMonitoriza}
	 */
	ApplicationMonitoriza getApplicationMonitorizaById(Long appId);

	/**
	 * Method that deletes a application in the persistence.
	 * @param appId {@link Integer} that represents the application identifier to delete.
	 */
	void deleteApplicationMonitoriza(Long appId);

	/**
	 * Method that gets all the templates from the persistence.
	 * @return a {@link Iterable<ApplicationMonitoriza>} with the information of all applications.
	 */
	Iterable<ApplicationMonitoriza> getAllApplicationMonitoriza();

	/**
	 * Method that gets all the apps from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<ApplicationMonitoriza> findAll(DataTablesInput input);

	/**
	 * Method that stores a app in the persistence.
	 * @param appDto a {@link ApplicationDTO} with the information of the application.
	 * @return {@link ApplicationMonitoriza} The application.
	 */
	ApplicationMonitoriza saveApplicationMonitoriza(ApplicationDTO appDto);
	
	List<ApplicationMonitoriza> getAllApplicationMonitorizaByTemplateMonitoriza(TemplateMonitoriza template);
}
