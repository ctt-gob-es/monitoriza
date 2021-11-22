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
 * <b>File:</b><p>es.gob.monitoriza.service.IAlertStatisticsBIService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.service;

import java.util.Date;
import java.util.List;

import es.gob.monitoriza.persistence.configuration.dto.AuditToStatisticDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAuditControl;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
public interface IAlertStatisticsBIService {
	
	/**
	 * Method that dumps data from alert auditory to alert statistics using a date range.
	 * @param begin Date that represents the start date
	 * @param end Date that represents the end date
	 * @return List<AuditToStatisticDTO>
	 */
	List<AuditToStatisticDTO> auditToStatisticsDumpData(final Date begin, final Date end) throws DatabaseException;
	
	/**
	 * Method that gets pending data ranges correspondent to audit data to dump 
	 * @return List<AlertAuditControl>
	 */
	List<AlertAuditControl> getPendingControlExecutions() throws DatabaseException;
	
	/**
	 * Method that obtains a {@link AlertDIMApplication} from persistence using name as filter
	 * @param name String that represents the name filter
	 * @return {@link AlertDIMApplication}
	 * @throws DatabaseException
	 */
	AlertDIMApplication getDIMAppByName(final String name) throws DatabaseException;
	
	/**
	 * Method that obtains a {@link AlertDIMNode} from persistence using name as filter
	 * @param name String that represents the name filter
	 * @return {@link AlertDIMNode}
	 * @throws DatabaseException
	 */
	AlertDIMNode getDIMNodeByName(final String name) throws DatabaseException;
	
	/**
	 * Method that obtains a {@link AlertDIMSeverity} from persistence using name as filter
	 * @param name String that represents the name filter
	 * @return {@link AlertDIMSeverity}
	 * @throws DatabaseException
	 */
	AlertDIMSeverity getDIMSeverityByName(final String name) throws DatabaseException;
	 
	/**
	 * Method that obtains a {@link AlertDIMTemplate} from persistence using name as filter
	 * @param name String that represents the name filter
	 * @return {@link AlertDIMTemplate}
	 * @throws DatabaseException
	 */
	AlertDIMTemplate getDIMTemplateByName(final String name) throws DatabaseException;
	
	/**
	 * Method that obtains a {@link AlertDIMType} from persistence using name as filter
	 * @param name String that represents the name filter
	 * @return {@link AlertDIMType}
	 * @throws DatabaseException
	 */
	AlertDIMType getDIMTypeByName(final String name) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertDIMApplication} or update its value in database
	 * @param dimApp {@link AlertDIMApplication} to create/update
	 * @throws DatabaseException
	 */
	AlertDIMApplication saveDimApplication(final AlertDIMApplication dimApp) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertDIMNode} or update its value in database
	 * @param dimNode {@link AlertDIMNode} to create/update
	 * @throws DatabaseException
	 */
	AlertDIMNode saveDimNode(final AlertDIMNode dimNode) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertDIMNode} or update its value in database
	 * @param dimSeverity {@link AlertDIMNode} to create/update
	 * @throws DatabaseException
	 */
	AlertDIMSeverity saveDimSeverity(final AlertDIMSeverity dimSeverity) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertDIMTemplate} or update its value in database
	 * @param dimTemplate {@link AlertDIMTemplate} to create/update
	 * @throws DatabaseException
	 */
	AlertDIMTemplate saveDimTemplate(final AlertDIMTemplate dimTemplate) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertDIMType} or update its value in database
	 * @param dimType {@link AlertDIMType} to create/update
	 * @throws DatabaseException
	 */
	AlertDIMType saveDimType(final AlertDIMType dimType) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertStatistics} or update its value in database
	 * @param stats {@link AlertStatistics}
	 * @return new or updated {@link AlertStatistics}
	 * @throws DatabaseException
	 */
	AlertStatistics saveAlertStatistics(final AlertStatistics stats) throws DatabaseException;
	
	/**
	 * Method that creates a new {@link AlertAuditControl} or update its value in database
	 * @param stats {@link AlertAuditControl}
	 * @return new or updated {@link AlertAuditControl}
	 * @throws DatabaseException
	 */
	AlertAuditControl saveAlertAuditControl(final AlertAuditControl control) throws DatabaseException;
	
}
