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
 * Class that represents the backing form for adding/editing an application.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 * @version 1.1, 25/01/2019.
 */
public class ApplicationDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idApplicationMonitoriza = null;

	/**
	 * Attribute that represents the name of the application
	 */
	private String name;

	/**
	 * Attribute that represents the application key
	 */
	private String appKey;

	/**
	 * Attribute that represents the template id.
	 */
	private Long templateID;

	/**
	 * Attribute that represents the description of the responsible name
	 */
	private String responsibleName;

	/**
	 * Attribute that represents the responsible mail
	 */
	private String responsibleEmail;

	/**
	 * Attribute that represents the responsible phone
	 */
	private String responsiblePhone;

	/**
	 * Attribute that represents the value of the input isSecure of the user in the form.
	 */
    private Boolean isEnabled;

	public Long getIdApplicationMonitoriza() {
		return this.idApplicationMonitoriza;
	}

	public void setIdApplicationMonitoriza(final Long idApp) {
		this.idApplicationMonitoriza = idApp;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(final String appKey) {
		this.appKey = appKey;
	}

	public Long getTemplateID() {
		return this.templateID;
	}

	public void setTemplateID(final Long templateID) {
		this.templateID = templateID;
	}

	public String getResponsibleName() {
		return this.responsibleName;
	}

	public void setResponsibleName(final String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getResponsibleEmail() {
		return this.responsibleEmail;
	}

	public void setResponsibleEmail(final String responsibleEmail) {
		this.responsibleEmail = responsibleEmail;
	}

	public String getResponsiblePhone() {
		return this.responsiblePhone;
	}

	public void setResponsiblePhone(final String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}

	public Boolean getEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(final Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
