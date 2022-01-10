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

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IResumeMonitorizaService {

	/**
	 * Method that obtains a resume by its identifier.
	 * @param templateId The resume identifier.
	 * @return {@link TemplateMonitoriza}
	 */
	ResumeMonitoriza getResumeMonitorizaById(Long resumeId);

	/**
	 * Method that deletes a resume in the persistence.
	 * @param templateId {@link Integer} that represents the resume identifier to delete.
	 */
	void deleteResumeMonitoriza(Long resumeId);

	/**
	 * Method that gets all the resumes from the persistence.
	 * @return a {@link Iterable<ResumeMonitoriza>} with the information of all resumes.
	 */
	Iterable<ResumeMonitoriza> getAllResumeMonitoriza();

	/**
	 * Method that gets all the resumes from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<ResumeMonitoriza> findAll(DataTablesInput input);

	/**
	 * Method that stores a resume in the persistence.
	 * @param templateDto a {@link ResumeDTO} with the information of the resume.
	 * @return {@link ResumeMonitoriza} The resume.
	 */
	ResumeMonitoriza saveResumeMonitoriza(ResumeDTO resumeDto);
	
	/**
	 * Method that stores a resume in the persistence.
	 * @param templateDto a {@link ResumeMonitoriza} with the information of the resume.
	 * @return {@link ResumeMonitoriza} The resume.
	 */
	ResumeMonitoriza saveResumeMonitoriza(ResumeMonitoriza resume);
	
	/**
	 * Method that loads the lazy attribute resumeTypes.
	 * @param resume
	 */
	void loadLazyListResumeType(ResumeMonitoriza resume);

	/**
	 * Method that loads the lazy attribute resumeSystems.
	 * @param resume
	 */
	void loadLazyListAlertResumeSystem(ResumeMonitoriza resume);
}
