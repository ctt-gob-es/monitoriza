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

import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;

/**
 * <p>
 * Class that represents the backing form for adding/editing a resume.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 * @version 1.1, 25/01/2019.
 */
public class ResumeDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idResumeMonitoriza= null;

	/**
	 * Attribute that represents the name of the resume
	 */
	private String name;

	/**
	 * Attribute that represents the description of the resume
	 */
	private String description;

	/**
	 * Attribute that represents the periodicity.
	 */
	private Long periodicity;

	/**
	 * Attribute that represents if a resume is active
	 */
	private Boolean isEnabled;

	/**
	 * Attribute that represents the relation of this resume with the applications
	 */
	private List<Long> applicationsIdArray;

	/**
	 * Attribute that represents the relation of this resume with the alert types
	 */
	private List<Long> alertsTypesIdArray;

	/**
	 * Attribute that represents the relation of this resume with the alert systems
	 */
	private List<Long> notifSystemsIdArray;

	/**
	 * Attribute that represents the emails configurated for the alert systems
	 */
	private List<List<String>> emailConfigurationArray;

	/**
	 * Attribute that represents the alert systems for a resume
	 */
	List<AlertSystemMonitoriza> alertSystemsList;

	/**
	 * Attribute that represents the applications for a resume
	 */
	List<ApplicationMonitoriza> applicationsList;

	/**
	 * Attribute that represents the alerts configurations for the applications
	 */
	List<AlertConfigMonitoriza> alertConfigList;

	public Long getIdResumeMonitoriza() {
		return this.idResumeMonitoriza;
	}

	public void setIdResumeMonitoriza(final Long id) {
		this.idResumeMonitoriza = id;
	}

	public Long getPeriodicity() {
		return this.periodicity;
	}

	public void setPeriodicity(final Long periodicity) {
		this.periodicity = periodicity;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(final Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<Long> getApplicationsIdArray() {
		return this.applicationsIdArray;
	}

	public void setApplicationsIdArray(final List<Long> applicationsIdArray) {
		this.applicationsIdArray = applicationsIdArray;
	}

	public List<Long> getAlertsTypesIdArray() {
		return this.alertsTypesIdArray;
	}

	public void setAlertsTypesIdArray(final List<Long> alertsTypesIdArray) {
		this.alertsTypesIdArray = alertsTypesIdArray;
	}

	public List<AlertSystemMonitoriza> getAlertSystemsList() {
		return this.alertSystemsList;
	}

	public void setAlertSystemsList(final List<AlertSystemMonitoriza> alertSystemsList) {
		this.alertSystemsList = alertSystemsList;
	}

	public List<ApplicationMonitoriza> getApplicationsList() {
		return this.applicationsList;
	}

	public void setApplicationsList(final List<ApplicationMonitoriza> applicationsList) {
		this.applicationsList = applicationsList;
	}

	public List<AlertConfigMonitoriza> getAlertConfigList() {
		return this.alertConfigList;
	}

	public void setAlertConfigList(final List<AlertConfigMonitoriza> alertConfigList) {
		this.alertConfigList = alertConfigList;
	}

	public List<Long> getNotifSystemsIdArray() {
		return this.notifSystemsIdArray;
	}

	public void setNotifSystemsIdArray(final List<Long> notifSystemsIdArray) {
		this.notifSystemsIdArray = notifSystemsIdArray;
	}

	public List<List<String>> getEmailConfigurationArray() {
		return this.emailConfigurationArray;
	}

	public void setEmailConfigurationArray(final List<List<String>> emailConfigurationArray) {
		this.emailConfigurationArray = emailConfigurationArray;
	}

}
