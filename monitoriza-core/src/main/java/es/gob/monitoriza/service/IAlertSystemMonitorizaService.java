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
 * @version 1.3, 10/01/2022.
 */
package es.gob.monitoriza.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemType;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertSystemMonitorizaService {

	/**
	 * Method that obtains a template by its identifier.
	 * @param templateId The template identifier.
	 * @return {@link TemplateMonitoriza}
	 */
	AlertSystemMonitoriza getAlertSystemMonitorizaById(Long templateId);

	/**
	 * Method that deletes a user in the persistence.
	 * @param templateId {@link Integer} that represents the template identifier to delete.
	 */
	void deleteAlertSystemMonitoriza(Long templateId);

	/**
	 * Method that gets all the templates from the persistence.
	 * @return a {@link Iterable<TemplateMonitoriza>} with the information of all templates.
	 */
	Iterable<AlertSystemMonitoriza> getAllAlertSystemMonitoriza();

	/**
	 * Method that gets all the templates from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<AlertSystemMonitoriza> findAll(DataTablesInput input);

	/**
	 * Method that stores a template in the persistence.
	 * @param templateDto a {@link TemplateDTO} with the information of the template.
	 * @return {@link TemplateMonitoriza} The template.
	 */
	AlertSystemMonitoriza saveAlertSystemMonitoriza(AlertSystemDTO templateDto);
	
	/**
	 * Method that gets all {@link AlertSystemType} from persistence
	 * @return List<AlertSystemType>
	 */
	List<AlertSystemType> getAllAlertSystemType();
	
	/**
	 * Method that obtains from persistence all {@link AlertSystemMonitoriza} that can be used with 'resumes'.
	 * @return List<AlertSystemMonitoriza>
	 */
	List<AlertSystemMonitoriza> getAllAlertSystemResumeEnabled();
}
