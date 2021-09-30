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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_RESUMES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
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
	private int periodicity;

	/**
	 * Attribute that represents if the resume is enabled.
	 */
	private String enabled;

	/**
	 * Gets the value of the attribute {@link #idTemplateMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idTemplateMonitoriza}.
	 */
	@Id
	@Column(name = "RESUME_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_resume_monitoriza")
	@GenericGenerator(name = "sq_resume_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_RESUMES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
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
	 * Gets the value of the attribute {@link #description}.
	 *
	 * @return the value of the attribute {@link #description}.
	 */
	@Column(name = "DESCRIPTION", nullable = false, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)
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
	@JsonView(DataTablesOutput.View.class)
	public int getPeriodicity() {
		return this.periodicity;
	}

	/**
	 * Sets the value of the attribute {@link #periodicity}.
	 *
	 * @param name
	 *            The value for the attribute {@link #periodicity}.
	 */
	public void setPeriodicity(final int periodicity) {
		this.periodicity = periodicity;
	}

	/**
	 * Gets the value of the attribute {@link #enabled}.
	 *
	 * @return the value of the attribute {@link #enabled}.
	 */
	@Column(name = "ENABLED", nullable = false, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)
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
}
