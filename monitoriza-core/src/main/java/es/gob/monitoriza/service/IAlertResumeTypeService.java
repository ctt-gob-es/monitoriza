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

import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertResumeTypeService {

	/**
	 * Method that obtains a alertResume by its identifier.
	 * @param resTypeId The resume type identifier.
	 * @return {@link AlertResumeType}
	 */
	AlertResumeType getAlertResumeTypeId(Long resTypeId);

	/**
	 * Method that deletes a resume type in the persistence.
	 * @param resTypeId {@link Integer} that represents the resume type identifier to delete.
	 */
	void deleteAlertResumeType(Long resTypeId);

	/**
	 * Method that deletes a resume type in the persistence.
	 * @param resumeMonitoriza {@link ResumeMonitoriza} that represents the resume to delete.
	 */
	void deleteAlertResumeTypeByResumeMonitoriza(ResumeMonitoriza resumeMonitoriza);

	/**
	 * Method that gets all the resume types from the persistence.
	 * @return a {@link Iterable<AlertResumeTypes>} with the information of all resume types.
	 */
	Iterable<AlertResumeType> getAllAlertResumeTypes();

	/**
	 * Method that stores a resume type in the persistence.
	 * @param alertResumeType a {@link AlertResumeType} with the information of the resume type.
	 * @return {@link AlertResumeType} The alert resume.
	 */
	AlertResumeType saveAlertResumeType(AlertResumeType alertResumeType);
	
	List<AlertResumeType> getAllAlertResumeTypeByApplicationMonitoriza(ApplicationMonitoriza appMonitoriza);


}
