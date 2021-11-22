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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AlarmDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApp;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;

/**
 * <p>
 * Class that represents the backing form for transfer information about a alert statistic
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 */
public class AlertStatisticDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idAlertStatistic;

	/**
	 * Attribute that represents the maximum date to search.
	 */
	private String maxDate;

	/**
	 * Attribute that represents the minimum date to search.
	 */
	private String minDate;

	/**
	 * Attribute that represents the app identifier to search.
	 */
	private Long appID;

	/**
	 * Attribute that represents the template identifier to search.
	 */
	private Long templateID;

	/**
	 * Attribute that represents the alert type identifier to search.
	 */
	private Long typeID;

	/**
	 * Attribute that represents the node identifier to search.
	 */
	private Long nodeID;

	/**
	 * Attribute that represents the severity identifier to search.
	 */
	private Long severityID;

	/**
	 * Attribute that represents the number of ocurrencies
	 */
	private Long ocurrency;

	/**
	 * Attribute that represents the list of applications
	 */
	private List<AlertDIMApp> applicationsList;

	/**
	 * Attribute that represents the list of types
	 */
	private List<AlertDIMType> typesList;

	/**
	 * Attribute that represents the list of templates
	 */
	private List<AlertDIMTemplate> templatesList;

	/**
	 * Attribute that represents the list of nodes
	 */
	private List<AlertDIMNode> nodesList;

	/**
	 * Attribute that represents the list of levels
	 */
	private List<AlertDIMSeverity> levelsList;


	public String getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(final String maxDate) {
		this.maxDate = maxDate;
	}

	public String getMinDate() {
		return this.minDate;
	}

	public void setMinDate(final String minDate) {
		this.minDate = minDate;
	}

	public Long getIdAlertStatistic() {
		return this.idAlertStatistic;
	}

	public void setIdAlertStatistic(final Long idAlertStatistic) {
		this.idAlertStatistic = idAlertStatistic;
	}

	public Long getAppID() {
		return this.appID;
	}

	public void setAppID(final Long appID) {
		this.appID = appID;
	}

	public Long getTemplateID() {
		return this.templateID;
	}

	public void setTemplateID(final Long templateID) {
		this.templateID = templateID;
	}

	public Long getTypeID() {
		return this.typeID;
	}

	public void setTypeID(final Long typeID) {
		this.typeID = typeID;
	}

	public Long getNodeID() {
		return this.nodeID;
	}

	public void setNodeID(final Long nodeID) {
		this.nodeID = nodeID;
	}

	public Long getSeverityID() {
		return this.severityID;
	}

	public void setSeverityID(final Long severityID) {
		this.severityID = severityID;
	}

	public Long getOcurrency() {
		return this.ocurrency;
	}

	public void setOcurrency(final Long ocurrency) {
		this.ocurrency = ocurrency;
	}

	public List<AlertDIMApp> getApplicationsList() {
		return this.applicationsList;
	}

	public void setApplicationsList(final List<AlertDIMApp> applicationsList) {
		this.applicationsList = applicationsList;
	}

	public List<AlertDIMType> getTypesList() {
		return this.typesList;
	}

	public void setTypesList(final List<AlertDIMType> typesList) {
		this.typesList = typesList;
	}

	public List<AlertDIMTemplate> getTemplatesList() {
		return this.templatesList;
	}

	public void setTemplatesList(final List<AlertDIMTemplate> templatesList) {
		this.templatesList = templatesList;
	}

	public List<AlertDIMNode> getNodesList() {
		return this.nodesList;
	}

	public void setNodesList(final List<AlertDIMNode> nodesList) {
		this.nodesList = nodesList;
	}

	public List<AlertDIMSeverity> getLevelsList() {
		return this.levelsList;
	}

	public void setLevelsList(final List<AlertDIMSeverity> levelsList) {
		this.levelsList = levelsList;
	}

}
