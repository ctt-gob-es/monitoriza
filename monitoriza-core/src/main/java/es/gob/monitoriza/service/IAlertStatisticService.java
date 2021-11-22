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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
public interface IAlertStatisticService {

	/**
	 * Method that gets all the alert statistics from the persistence.
	 * @return a {@link Iterable<AlertStatistic>} with the information of all alert audits.
	 */
	Iterable<AlertStatistics> getAllAlertStatistic();

	/**
	 * Method that gets all the alert statistics from the persistence.
	 * @return a {@link DataTablesOutput<AlertStatistic>} with the information for the datatable.
	 */
	DataTablesOutput<AlertStatistics> findAll(final DataTablesInput input);

	/**
	 *  Method that gets all the alert statistics with the indicated filters.
	 * @param minDate Minimum date.
	 * @param maxDate Maximum date.
	 * @param appID Application identifier.
	 * @param templateID Template identifier.
	 * @param typeID Alert type identifier.
	 * @param nodeID Node identifier.
	 * @param severityID Severity identifier.
	 * @return List of statistics filtered.
	 */
	List<AlertStatistics> findByFilters(final Date minDate, final Date maxDate, final AlertDIMApplication appID, final AlertDIMTemplate templateID,
			final AlertDIMType typeID, final AlertDIMNode nodeID, final AlertDIMSeverity severityID);
}
