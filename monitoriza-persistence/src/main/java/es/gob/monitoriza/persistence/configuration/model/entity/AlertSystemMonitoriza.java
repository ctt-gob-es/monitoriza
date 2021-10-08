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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_SYSTEMS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_SYSTEMS")
public class AlertSystemMonitoriza implements Serializable {

	private static final long serialVersionUID = -4586421204584655609L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertSystemMonitoriza;

	/**
	 * Attribute that represents the alert system name.
	 */
	private String name;

	/**
	 * Attribute that represents the alert system type.
	 */
	private String type;

	/**
	 * Attribute that represents the relation with templateMonitoriza object.
	 */
	private AlertGraylogSystemConfig graylogSystemConfig;

	/**
	 * Attribute that represents the resumes configurated for this alert system.
	 */
	private List<ResumeMonitoriza> resumesMonitoriza;

	/**
	 * Gets the value of the attribute {@link #idAlertSystemMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idAlertSystemMonitoriza}.
	 */
	@Id
	@Column(name = "SYSTEM_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_systems_monitoriza")
	@GenericGenerator(name = "sq_alert_systems_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_SYSTEMS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertSystemMonitoriza() {
		return this.idAlertSystemMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertSystemMonitoriza}.
	 *
	 * @param alertSystemId
	 *            The value for the attribute {@link #idAlertSystemMonitoriza}.
	 */
	public void setIdAlertSystemMonitoriza(final Long alertSystemId) {
		this.idAlertSystemMonitoriza = alertSystemId;
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
	 * Gets the value of the attribute {@link #type}.
	 *
	 * @return the value of the attribute {@link #type}.
	 */
	@Column(name = "TYPE", nullable = false, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 *
	 * @param name
	 *            The value for the attribute {@link #type}.
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Gets the value of the attribute {@link #graylogSystemConfig}.
	 * @return the value of the attribute {@link #graylogSystemConfig}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SYSTEM_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertGraylogSystemConfig getGraylogSystemConfig() {
		// CHECKSTYLE:ON
		return this.graylogSystemConfig;
	}

	/**
	 * Sets the value of the attribute {@link #graylogSystemConfig}.
	 * @param requestFile The value for the attribute {@link #graylogSystemConfig}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setGraylogSystemConfig(final AlertGraylogSystemConfig graylogSystemConfig) {
		// CHECKSTYLE:ON
		this.graylogSystemConfig = graylogSystemConfig;
	}

	/**
	 * Gets the value of the attribute {@link #resumesMonitoriza}.
	 * @return the value of the attribute {@link #resumesMonitoriza}.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ALERT_RESUME_SYSTEMS", joinColumns = @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "RESUME_ID", nullable = false))
	public List<ResumeMonitoriza> getResumesMonitoriza() {
		return this.resumesMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #resumesMonitoriza}.
	 * @param emailsDegraded The value for the attribute {@link #resumesMonitoriza}.
	 */
	public void setResumesMonitoriza(final List<ResumeMonitoriza> resumesMonitoriza) {
		this.resumesMonitoriza = resumesMonitoriza;
	}
}
