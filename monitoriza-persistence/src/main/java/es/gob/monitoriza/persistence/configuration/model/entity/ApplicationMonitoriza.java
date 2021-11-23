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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the <i>ALERT_APPLICATIONS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_APPLICATIONS")
public class ApplicationMonitoriza implements Serializable {

	private static final long serialVersionUID = 1348078978679947747L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idApplicationMonitoriza;

	/**
	 * Attribute that represents the app's name.
	 */
	private String name;

	/**
	 * Attribute that represents the app's key.
	 */
	private String appKey;

	/**
	 * Attribute that represents the cipher key.
	 */
	private String cipherKey;

	/**
	 * Attribute that represents the relation with templateMonitoriza object.
	 */
	private TemplateMonitoriza templateMonitoriza;

	/**
	 * Attribute that represents the app's responsible name.
	 */
	private String responsibleName;

	/**
	 * Attribute that represents the app's responsible email.
	 */
	private String responsibleEmail;

	/**
	 * Attribute that represents the app's responsible phone.
	 */
	private String responsiblePhone;

	/**
	 * Attribute that represents if the app is enabled.
	 */
	private String enabled;

	/**
	 * Attribute that represents the relations with resumes and types.
	 */
    private Set<AlertResumeType> resume;

	/**
	 * Attribute that represents the alerts configurations for this application.
	 */
	private List<AlertConfigMonitoriza> alertConfigMonitoriza;

	public ApplicationMonitoriza() {
		this.templateMonitoriza = new TemplateMonitoriza();
	}

	public void setIdApplicationMonitoriza(final Long idApplicationMonitoriza) {
		this.idApplicationMonitoriza = idApplicationMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #idApplicationMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idApplicationMonitoriza}.
	 */
	@Id
	@Column(name = "APP_ID", unique = true, nullable = false)
	@GeneratedValue(generator = "sq_application_monitoriza")
	@GenericGenerator(name = "sq_application_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_APPLICATIONS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdApplicationMonitoriza() {
		return this.idApplicationMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 *
	 * @param name
	 *            The value for the attribute {@link #name}.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #appKey}.
	 *
	 * @return the value of the attribute {@link #appKey}.
	 */
	@Column(name = "APP_KEY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getAppKey() {
		return this.appKey;
	}

	/**
	 * Sets the value of the attribute {@link #appKey}.
	 *
	 * @param appKey
	 *            The value for the attribute {@link #appKey}.
	 */
	public void setAppKey(final String appKey) {
		this.appKey = appKey;
	}

	/**
	 * Gets the value of the attribute {@link #cipherKey}.
	 *
	 * @return the value of the attribute {@link #cipherKey}.
	 */
	@Column(name = "CIPHER_KEY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getCipherKey() {
		return this.cipherKey;
	}

	/**
	 * Sets the value of the attribute {@link #cipherKey}.
	 *
	 * @param cipherKey
	 *            The value for the attribute {@link #cipherKey}.
	 */
	public void setCipherKey(final String cipherKey) {
		this.cipherKey = cipherKey;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public TemplateMonitoriza getTemplateMonitoriza() {
		return this.templateMonitoriza;
	}

	public void setTemplateMonitoriza(final TemplateMonitoriza templateMonitoriza) {
		this.templateMonitoriza = templateMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #responsibleName}.
	 *
	 * @return the value of the attribute {@link #responsibleName}.
	 */
	@Column(name = "RESPONSIBLE_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getResponsibleName() {
		return this.responsibleName;
	}

	/**
	 * Sets the value of the attribute {@link #responsibleName}.
	 *
	 * @param name
	 *            The value for the attribute {@link #responsibleName}.
	 */
	public void setResponsibleName(final String responsibleName) {
		this.responsibleName = responsibleName;
	}

	/**
	 * Gets the value of the attribute {@link #responsibleEmail}.
	 *
	 * @return the value of the attribute {@link #responsibleEmail}.
	 */
	@Column(name = "RESPONSIBLE_EMAIL", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getResponsibleEmail() {
		return this.responsibleEmail;
	}

	/**
	 * Sets the value of the attribute {@link #responsibleEmail}.
	 *
	 * @param responsibleEmail
	 *            The value for the attribute {@link #responsibleEmail}.
	 */
	public void setResponsibleEmail(final String responsibleEmail) {
		this.responsibleEmail = responsibleEmail;
	}

	/**
	 * Gets the value of the attribute {@link #responsiblePhone}.
	 *
	 * @return the value of the attribute {@link #responsiblePhone}.
	 */
	@Column(name = "RESPONSIBLE_PHONE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getResponsiblePhone() {
		return this.responsiblePhone;
	}

	/**
	 * Sets the value of the attribute {@link #responsiblePhone}.
	 *
	 * @param responsiblePhone
	 *            The value for the attribute {@link #responsiblePhone}.
	 */
	public void setResponsiblePhone(final String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}

	/**
	 * Gets the value of the attribute {@link #enabled}.
	 *
	 * @return the value of the attribute {@link #enabled}.
	 */
	@Column(name = "ENABLED", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(final String enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the value of the attribute {@link #alertConfigsMonitoriza}.
	 * @return the value of the attribute {@link #alertConfigsMonitoriza}.
		*/
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "applicationMonitoriza")
	public List<AlertConfigMonitoriza> getAlertConfigMonitoriza() {
		return this.alertConfigMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertConfigsMonitoriza}.
	 * @param alertConfigsMonitoriza) The value for the attribute {@link #alertConfigsMonitoriza}.
	*/
	public void setAlertConfigMonitoriza(final List<AlertConfigMonitoriza> alertConfigMonitoriza) {
		this.alertConfigMonitoriza = alertConfigMonitoriza;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationMonitoriza", cascade = CascadeType.ALL)
	public Set<AlertResumeType> getAlertResumeTypes() {
		return this.resume;
	}

	public void setAlertResumeTypes(final Set<AlertResumeType> resume) {
		this.resume = resume;
	}

}
