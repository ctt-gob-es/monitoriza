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
 * <b>File:</b><p>es.gob.monitoriza.service.IAlertAuditService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 **/ 
package es.gob.monitoriza.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 **/
public interface IAlertAuditService {

	/**
	 * Method that gets all the alert audit from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<AlertAudit> findAll(DataTablesInput input);
	
	/**
	 * Method that gets all the alert audit from the persistence for populating a datatable.
	 * @param input DataTable input configuration object
	 * @return DataTablesOutput object for drawing the datatable.
	 */
	DataTablesOutput<AlertAudit> findAll(DataTablesInput input, Date min, Date max);
	
	/**
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	List<AlertAudit> findAllAlertAudit() throws DatabaseException;
	

	 /* Method that gets all the alert audits from the persistence.
	 * @return a {@link Iterable<AlertAudit>} with the information of all alert audits.
	 */
	Iterable<AlertAudit> getAllAlertAudit();
	

	/**
	 * Method that returns the list of alert audits with the indicated period.
	 * @param actualDate Actual date.
	 * @param periodDate Date with the period to filter with the actual date.
	 * @return List of alert audits filtered.
	 */
	List<AlertAudit> findByCriteria(final Date actualDate, final Date periodDate);

}
