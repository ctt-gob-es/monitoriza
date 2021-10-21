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

import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertTypeMonitorizaService {

	/**
	 * Method that obtains a alert type by its identifier.
	 * @param typeId The alert type identifier.
	 * @return {@link AlertTypeMonitoriza}
	 */
	AlertTypeMonitoriza getAlertTypeMonitorizaById(Long typeId);

	/**
	 * Method that deletes a alert type in the persistence.
	 * @param typeId {@link Integer} that represents the alert type identifier to delete.
	 */
	void deleteAlertTypeMonitoriza(Long typeId);

	/**
	 * Method that gets all the alert types from the persistence.
	 * @return a {@link Iterable<AlertTypeMonitoriza>} with the information of all alert types.
	 */
	Iterable<AlertTypeMonitoriza> getAllAlertTypeMonitoriza();

	/**
	 * Method that stores a alert type in the persistence.
	 * @param alertTypeMonitoriza a {@link AlertTypeMonitoriza} with the information of the alert type.
	 * @return {@link AlertTypeMonitoriza} The alert type.
	 */
	AlertTypeMonitoriza saveAlertTypeMonitoriza(AlertTypeMonitoriza alertTypeMonitoriza);

}
