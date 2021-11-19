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

import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertConfigSystemService {

	/**
	 * Method that obtains a AlertConfigSystem by its identifier.
	 * @param alertConfigSystemId The resume type identifier.
	 * @return {@link AlertConfigSystem}
	 */
	AlertConfigSystem getAlertConfigSystemId(Long alertConfigSystemId);

	/**
	 * Method that deletes a relation in the persistence.
	 * @param idConfSystem {@link Integer} that represents the relation identifier to delete.
	 */
	void deleteAlertConfigSystem(Long idConfSystem);

	/**
	 * Method that gets all the relations from the persistence.
	 * @return a {@link Iterable<AlertConfigSystem>} with the information of all relations.
	 */
	Iterable<AlertConfigSystem> getAllAlertConfigSystems();

	/**
	 * Method that stores a relation in the persistence.
	 * @param AlertConfigSystem a {@link AlertConfigSystem} with the information of the relation.
	 * @return {@link AlertConfigSystem} The relation.
	 */
	AlertConfigSystem saveAlertConfigSystem(AlertConfigSystem alertConfigSystem);

	/**
	 * Method that delete all alerts systems of an resume.
	 * @param alertConfigMonitoriza The {@link AlertConfigSystem} to delete
	 */
	void deleteAlertConfigSystemByAlertConfigMonitoriza(AlertConfigMonitoriza alertConfigMonitoriza);
	
	List<AlertConfigSystem> getAllAlertConfigSystemByAlertConfigMonitoriza(AlertConfigMonitoriza alertConfig);
}
