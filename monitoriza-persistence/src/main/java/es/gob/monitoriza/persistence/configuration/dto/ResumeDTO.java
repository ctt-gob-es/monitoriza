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
	private final Long idResumeMonitoriza= null;

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
	private String enabled;

	private Long alertSystemsList;

	private Long applicationsList;

	private Long alertConfigList;

	public Long getPeriodicity() {
		return this.periodicity;
	}

	public void setPeriodicity(final Long periodicity) {
		this.periodicity = periodicity;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(final String enabled) {
		this.enabled = enabled;
	}

	public Long getIdResumeMonitoriza() {
		return this.idResumeMonitoriza;
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

	public Long getAlertSystemsList() {
		return this.alertSystemsList;
	}

	public void setAlertSystemsList(final Long alertSystemsList) {
		this.alertSystemsList = alertSystemsList;
	}

	public Long getApplicationsList() {
		return this.applicationsList;
	}

	public void setApplicationsList(final Long applicationsList) {
		this.applicationsList = applicationsList;
	}

	public Long getAlertConfigList() {
		return this.alertConfigList;
	}

	public void setAlertConfigList(final Long alertConfigList) {
		this.alertConfigList = alertConfigList;
	}
}
