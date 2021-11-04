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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.ResumeMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_RESUMES</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.eventmanager.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_RESUMES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_RESUMES")
public class ResumeMonitoriza implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 676968876441116981L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idResumeMonitoriza;

	/**
	 * Attribute that represents the resume name.
	 */
	private String name;

	/**
	 * Attribute that represents the resume's description.
	 */
	private String description;

	/**
	 * Attribute that represents the resume's periodicity.
	 */
	private Long periodicity;

	/**
	 * Attribute that represents if the resume is enabled.
	 */
	private String enabled;

	/**
	 * Attribute that represents the systems configurated for this resume.
	 */
	private List<AlertSystemMonitoriza> alertSystemsMonitoriza;

	/**
	 * Attribute that represents the alerts configurated for this resume.
	 */
	private List<AlertConfigMonitoriza> alertsConfigMonitoriza;

	/**
	 * Attribute that represents the relations with applications and types.
	 */
    private Set<AlertResumeType> resumeTypes;

	/**
	 * Attribute that represents the alert systems configurated for a resume.
	 */
	private Set<AlertResumeSystem> alertResumeSystems;

	/**
	 * Gets the value of the attribute {@link #idTemplateMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idTemplateMonitoriza}.
	 */
	@Id
	@Column(name = "RESUME_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_resume_monitoriza")
	@GenericGenerator(name = "sq_resume_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_RESUMES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	
	public Long getIdResumeMonitoriza() {
		return this.idResumeMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #resumeId}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #resumeId}.
	 */
	public void setIdResumeMonitoriza(final Long resumeId) {
		this.idResumeMonitoriza = resumeId;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false, precision = NumberConstants.NUM19)
	
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
	 * Gets the value of the attribute {@link #description}.
	 *
	 * @return the value of the attribute {@link #description}.
	 */
	@Column(name = "DESCRIPTION", nullable = false, precision = NumberConstants.NUM19)
	
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 *
	 * @param name
	 *            The value for the attribute {@link #description}.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #periodicity}.
	 *
	 * @return the value of the attribute {@link #periodicity}.
	 */
	@Column(name = "PERIODICITY", nullable = false, precision = NumberConstants.NUM19)
	
	public Long getPeriodicity() {
		return this.periodicity;
	}

	/**
	 * Sets the value of the attribute {@link #periodicity}.
	 *
	 * @param name
	 *            The value for the attribute {@link #periodicity}.
	 */
	public void setPeriodicity(final Long periodicity) {
		this.periodicity = periodicity;
	}

	/**
	 * Gets the value of the attribute {@link #enabled}.
	 *
	 * @return the value of the attribute {@link #enabled}.
	 */
	@Column(name = "ENABLED", nullable = false, precision = NumberConstants.NUM19)
	
	public String getEnabled() {
		return this.enabled;
	}

	/**
	 * Sets the value of the attribute {@link #enabled}.
	 *
	 * @param name
	 *            The value for the attribute {@link #enabled}.
	 */
	public void setEnabled(final String enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the value of the attribute {@link #alertSystemsMonitoriza}.
	 * @return the value of the attribute {@link #alertSystemsMonitoriza}.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ALERT_RESUME_SYSTEMS", joinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "RESUME_ID", nullable = true), inverseJoinColumns = @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID", nullable = true))
	public List<AlertSystemMonitoriza> getAlertSystemsMonitoriza() {
		return this.alertSystemsMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertSystemsMonitoriza}.
	 * @param emailsDegraded The value for the attribute {@link #alertSystemsMonitoriza}.
	 */
	public void setAlertSystemsMonitoriza(final List<AlertSystemMonitoriza> alertSystemsMonitoriza) {
		this.alertSystemsMonitoriza = alertSystemsMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #alertsConfigMonitoriza}.
	 * @return the value of the attribute {@link #alertsConfigMonitoriza}.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ALERT_RESUME_LIST", joinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "RESUME_ID", nullable = true), inverseJoinColumns = @JoinColumn(name = "ALERT_CONFIG_ID", referencedColumnName = "ALERT_CONFIG_ID", nullable = true))
	public List<AlertConfigMonitoriza> getAlertConfigMonitoriza() {
		return this.alertsConfigMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertsConfigMonitoriza}.
	 * @param alertsConfigMonitoriza The value for the attribute {@link #alertsConfigMonitoriza}.
	 */
	public void setAlertConfigMonitoriza(final List<AlertConfigMonitoriza> alertsConfigMonitoriza) {
		this.alertsConfigMonitoriza = alertsConfigMonitoriza;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeMonitoriza" , cascade = CascadeType.ALL)
	public Set<AlertResumeType> getResumeTypes() {
		return this.resumeTypes;
	}

	public void setResumeTypes(final Set<AlertResumeType> resumeTypes) {
		this.resumeTypes = resumeTypes;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeMonitoriza")
	public Set<AlertResumeSystem> getAlertResumeSystem() {
		return this.alertResumeSystems;
	}

	public void setAlertResumeSystem(final Set<AlertResumeSystem> alertResumeSystems) {
		this.alertResumeSystems = alertResumeSystems;
	}

}
