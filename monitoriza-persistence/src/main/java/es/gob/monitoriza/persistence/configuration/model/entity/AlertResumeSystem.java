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

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_RESUME_SYSTEMS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_RESUME_SYSTEMS")
public class AlertResumeSystem implements Serializable {

	private static final long serialVersionUID = 2522387082795476957L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idResSystem;

	/**
	 * Attribute that represents the alert system.
	 */
	private AlertSystemMonitoriza alertSystemMonitoriza;

	/**
	 * Attribute that represents the resume.
	 */
	private ResumeMonitoriza resumeMonitoriza;

	private List<AlertMailResumeConfig> alertMailsResumeConfig;

	/**
	 * Gets the value of the attribute {@link #idResSystem}.
	 *
	 * @return the value of the attribute {@link #idResSystem}.
	 */
	@Id
	@Column(name = "RES_SYS_CONFIG_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_resume_systems")
	@GenericGenerator(name = "sq_alert_resume_systems", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_RESUME_SYSTEMS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdResSystem() {
		return this.idResSystem;
	}

	/**
	 * Sets the value of the attribute {@link #idResSystem}.
	 *
	 * @param idResSystem
	 *            The value for the attribute {@link #idResSystem}.
	 */
	public void setIdResSystem(final Long idResSystem) {
		this.idResSystem = idResSystem;
	}

	/**
	 * Gets the value of the attribute {@link #resumeMonitoriza}.
	 *
	 * @return the value of the attribute {@link #resumeMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
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

	/**
	 * Gets the value of the attribute {@link #alertSystemMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertSystemMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSTEM_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertSystemMonitoriza getAlertSystemMonitoriza() {
		return this.alertSystemMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertSystemMonitoriza}.
	 *
	 * @param alertSystemMonitoriza
	 *            The value for the attribute {@link #alertSystemMonitoriza}.
	 */
	public void setAlertSystemMonitoriza(final AlertSystemMonitoriza alertSystemMonitoriza) {
		this.alertSystemMonitoriza = alertSystemMonitoriza;
	}

	@OneToMany(mappedBy = "alertResumeSystem" ,orphanRemoval = true)
	public List<AlertMailResumeConfig> getAlertMailsResumeConfig() {
		return this.alertMailsResumeConfig;
	}

	public void setAlertMailsResumeConfig(final List<AlertMailResumeConfig> alertMailsResumeConfig) {
		this.alertMailsResumeConfig = alertMailsResumeConfig;
	}
}
