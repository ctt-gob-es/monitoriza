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
 * @version 1.3, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old
 * Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.5, 25/01/2019.
 */
@Entity
@Table(name = "ALARM_MONITORIZA")
public class AlarmMonitoriza implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 5742847859798785716L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlarm;

	/**
	 * Attribute that represents the name of the alarm.
	 */
	private String name;

	/**
	 * Attribute that represents the time in milliseconds that this alarm will
	 * be blocked.
	 */
	private Long blockedTime;

	/**
	 * Attribute that represents the emails for degraded alarms configured in this alarm.
	 */
	private Set<MailMonitoriza> emailsDegraded;

	/**
	 * Attribute that represents the emails for down alarms configured in this alarm.
	 */
	private Set<MailMonitoriza> emailsDown;

	/**
	 * Gets the value of the attribute {@link #idAlarm}.
	 * 
	 * @return the value of the attribute {@link #idAlarm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_ALARM", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alarm_monitoriza")
	@GenericGenerator(name = "sq_alarm_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQ_ALARM_MONITORIZA"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlarm() {
		// CHECKSTYLE:ON
		return idAlarm;
	}

	/**
	 * Sets the value of the attribute {@link #idAlarm}.
	 * 
	 * @param idAlarm
	 *            The value for the attribute {@link #idAlarm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdAlarm(Long idAlarm) {
		// CHECKSTYLE:ON
		this.idAlarm = idAlarm;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * 
	 * @return the value of the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		// CHECKSTYLE:ON
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * 
	 * @param name
	 *            The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String name) {
		// CHECKSTYLE:ON
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #blockedTime}.
	 * @return the value of the attribute {@link #blockedTime}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "BLOCKED_TIME", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
	public Long getBlockedTime() {
		// CHECKSTYLE:ON
		return blockedTime;
	}

	/**
	 * Sets the value of the attribute {@link #blockedTime}.
	 * @param blockedTime The value for the attribute {@link #blockedTime}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setBlockedTime(Long blockedTime) {
		// CHECKSTYLE:ON
		this.blockedTime = blockedTime;
	}

	/**
	 * Gets the value of the attribute {@link #emailsDegraded}.
	 * @return the value of the attribute {@link #emailsDegraded}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "R_ALARM_MAIL_DEGRADED", joinColumns = @JoinColumn(name = "ID_ALARM", referencedColumnName = "ID_ALARM", nullable = false), inverseJoinColumns = @JoinColumn(name = "ID_MAIL", referencedColumnName = "ID_MAIL", nullable = false))
	public Set<MailMonitoriza> getEmailsDegraded() {
		// CHECKSTYLE:ON
		return emailsDegraded;
	}

	/**
	 * Sets the value of the attribute {@link #emailsDegraded}.
	 * @param emailsDegraded The value for the attribute {@link #emailsDegraded}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setEmailsDegraded(Set<MailMonitoriza> emailsDegraded) {
		// CHECKSTYLE:ON
		this.emailsDegraded = emailsDegraded;
	}

	/**
	 * Gets the value of the attribute {@link #emailsDown}.
	 * @return the value of the attribute {@link #emailsDown}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "R_ALARM_MAIL_DOWN", joinColumns = @JoinColumn(name = "ID_ALARM", referencedColumnName = "ID_ALARM", nullable = false), inverseJoinColumns = @JoinColumn(name = "ID_MAIL", referencedColumnName = "ID_MAIL", nullable = false))
	public Set<MailMonitoriza> getEmailsDown() {
		// CHECKSTYLE:ON
		return emailsDown;
	}

	/**
	 * Sets the value of the attribute {@link #emailsDown}.
	 * @param emailsDown The value for the attribute {@link #emailsDown}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setEmailsDown(Set<MailMonitoriza> emailsDown) {
		// CHECKSTYLE:ON
		this.emailsDown = emailsDown;
	}

}
