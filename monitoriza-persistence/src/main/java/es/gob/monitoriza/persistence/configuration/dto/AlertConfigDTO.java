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

import es.gob.monitoriza.persistence.configuration.model.entity.AlertSeverityMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;

/**
 * <p>
 * Class that represents the backing form for adding/editing an alert system.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 */
public class AlertConfigDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idAlertConfigMonitoriza;

	/**
	 * Attribute that represents the application of the alert
	 */
	private Long appID;

	/**
	 * Attribute that represents the alert type
	 */
	private Long typeID;

	/**
	 * Attribute that represents the severity
	 */
	private Long severity;

	/**
	 * Attribute that represents if the alert is enabled
	 */
	private Boolean isEnable;

	/**
	 * Attribute that represents if allow block
	 */
	private Boolean isAllowBlock;

	/**
	 * Attribute that represents the condition to block
	 */
	private Long blockCondition;

	/**
	 * Attribute that represents the interval to block
	 */
	private Long blockIntervalSeconds;

	/**
	 * Attribute that represents the period to block
	 */
	private Long blockPeriodSeconds;

	/**
	 * Attribute that represents the alert types for the select
	 */
	private List<AlertTypeMonitoriza> alertTypesList;

	/**
	 * Attribute that represents the alert systems for the select
	 */
	private List<AlertSystemMonitoriza> alertSystemsList;

	/**
	 * Attribute that represents the severity list
	 */
	private List<AlertSeverityMonitoriza> alertSeverityList;

	/**
	 * Attribute that represents the alert systems for the select
	 */
	private List<String> notifSystemsIdArray;

	/**
	 * Attribute that represents the emails configurated for the alert systems
	 */
	private List<List<String>> emailConfigurationArray;

	/**
	 * Attribute that represents the keys for the graylog configuration
	 */
	private List<List<String>> keysArray;

	/**
	 * Attribute that represents the values for the graylog configuration
	 */
	private List<List<String>> valuesArray;

	public Long getIdAlertConfigMonitoriza() {
		return this.idAlertConfigMonitoriza;
	}

	public void setIdAlertConfigMonitoriza(final Long idAlertConfigMonitoriza) {
		this.idAlertConfigMonitoriza = idAlertConfigMonitoriza;
	}

	public Long getAppID() {
		return this.appID;
	}

	public void setAppID(final Long appID) {
		this.appID = appID;
	}

	public Long getTypeID() {
		return this.typeID;
	}

	public void setTypeID(final Long typeID) {
		this.typeID = typeID;
	}

	public Long getSeverity() {
		return this.severity;
	}

	public void setSeverity(final Long severity) {
		this.severity = severity;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(final Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsAllowBlock() {
		return this.isAllowBlock;
	}

	public void setIsAllowBlock(final Boolean isAllowBlock) {
		this.isAllowBlock = isAllowBlock;
	}

	public Long getBlockCondition() {
		return this.blockCondition;
	}

	public void setBlockCondition(final Long blockCondition) {
		this.blockCondition = blockCondition;
	}

	public Long getBlockIntervalSeconds() {
		return this.blockIntervalSeconds;
	}

	public void setBlockIntervalSeconds(final Long blockIntervalSeconds) {
		this.blockIntervalSeconds = blockIntervalSeconds;
	}

	public Long getBlockPeriodSeconds() {
		return this.blockPeriodSeconds;
	}

	public void setBlockPeriodSeconds(final Long blockPeriodSeconds) {
		this.blockPeriodSeconds = blockPeriodSeconds;
	}

	public List<AlertTypeMonitoriza> getAlertTypesList() {
		return this.alertTypesList;
	}

	public void setAlertTypesList(final List<AlertTypeMonitoriza> alertTypesList) {
		this.alertTypesList = alertTypesList;
	}

	public List<AlertSystemMonitoriza> getAlertSystemsList() {
		return this.alertSystemsList;
	}

	public void setAlertSystemsList(final List<AlertSystemMonitoriza> alertSystemsList) {
		this.alertSystemsList = alertSystemsList;
	}

	public List<AlertSeverityMonitoriza> getAlertSeverityList() {
		return this.alertSeverityList;
	}

	public void setAlertSeverityList(final List<AlertSeverityMonitoriza> alertSeverityList) {
		this.alertSeverityList = alertSeverityList;
	}

	public List<String> getNotifSystemsIdArray() {
		return this.notifSystemsIdArray;
	}

	public void setNotifSystemsIdArray(final List<String> notifSystemsIdArray) {
		this.notifSystemsIdArray = notifSystemsIdArray;
	}

	public List<List<String>> getEmailConfigurationArray() {
		return this.emailConfigurationArray;
	}

	public void setEmailConfigurationArray(final List<List<String>> emailConfigurationArray) {
		this.emailConfigurationArray = emailConfigurationArray;
	}

	public List<List<String>> getKeysArray() {
		return this.keysArray;
	}

	public void setKeysArray(final List<List<String>> keysArray) {
		this.keysArray = keysArray;
	}

	public List<List<String>> getValuesArray() {
		return this.valuesArray;
	}

	public void setValuesArray(final List<List<String>> valuesArray) {
		this.valuesArray = valuesArray;
	}
}