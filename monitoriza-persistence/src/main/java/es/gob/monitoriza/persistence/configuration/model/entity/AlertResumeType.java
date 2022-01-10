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
 * @version 1.3, 10/01/2022.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the <i>ALERT_TYPES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_RESUME_TYPES")
public class AlertResumeType implements Serializable {

	private static final long serialVersionUID = 9003533910857331973L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idResType;

	/**
	 * Attribute that represents the application.
	 */
	private ApplicationMonitoriza applicationMonitoriza;

	/**
	 * Attribute that represents the type.
	 */
	private AlertTypeMonitoriza alertTypeMonitoriza;

	/**
	 * Attribute that represents the resume.
	 */
	private ResumeMonitoriza resumeMonitoriza;

	/**
	 * Gets the value of the attribute {@link #idResType}.
	 *
	 * @return the value of the attribute {@link #idResType}.
	 */
	@Id
	@Column(name = "RES_TYPE_ID", unique = true, nullable = false)
	@GeneratedValue(generator = "sq_alert_resume_types")
	@GenericGenerator(name = "sq_alert_resume_types", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_RESUME_TYPES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdResType() {
		return this.idResType;
	}

	/**
	 * Sets the value of the attribute {@link #idResType}.
	 *
	 * @param idResType
	 *            The value for the attribute {@link #idResType}.
	 */
	public void setIdResType(final Long idResType) {
		this.idResType = idResType;
	}

	/**
	 * Gets the value of the attribute {@link #applicationMonitoriza}.
	 *
	 * @return the value of the attribute {@link #applicationMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APP_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public ApplicationMonitoriza getApplicationMonitoriza() {
		return this.applicationMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #applicationMonitoriza}.
	 *
	 * @param name
	 *            The value for the attribute {@link #applicationMonitoriza}.
	 */
	public void setApplicationMonitoriza(final ApplicationMonitoriza applicationMonitoriza) {
		this.applicationMonitoriza = applicationMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #alertTypeMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertTypeMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertTypeMonitoriza getAlertTypeMonitoriza() {
		return this.alertTypeMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertTypeMonitoriza}.
	 *
	 * @param name
	 *            The value for the attribute {@link #alertTypeMonitoriza}.
	 */
	public void setAlertTypeMonitoriza(final AlertTypeMonitoriza alertTypeMonitoriza) {
		this.alertTypeMonitoriza = alertTypeMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #resumeMonitoriza}.
	 *
	 * @return the value of the attribute {@link #resumeMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RESUME_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public ResumeMonitoriza getResumeMonitoriza() {
		return this.resumeMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #resumeMonitoriza}.
	 *
	 * @param name
	 *            The value for the attribute {@link #resumeMonitoriza}.
	 */
	public void setResumeMonitoriza(final ResumeMonitoriza resumeMonitoriza) {
		this.resumeMonitoriza = resumeMonitoriza;
	}

}
