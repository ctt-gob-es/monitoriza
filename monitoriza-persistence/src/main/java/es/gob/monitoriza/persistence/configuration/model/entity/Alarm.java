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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALARM</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.2, 25/01/2019.
 */
@Entity
@Table(name = "ALARM")
public class Alarm implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 5742847859798785716L;

	/**
	 * Attribute that represents the object ID.
	 */
	private String idAlarm;

	/**
	 * Attribute that represents a description of alarm.
	 */
	private String description;

	/**
	 * Attribute that represents a time of block.
	 */
	private Long timeBlock;

	/**
	 * Attribute that represents if an alarm is active or not.
	 */
	private Boolean active;

	/**
	 * Attribute that represents a list of person where alarm is sended.
	 */
	private Set<MailMonitoriza> mails;

	/**
	 * Gets the value of the attribute {@link #idAlarm}.
	 * 
	 * @return the value of the attribute {@link #idAlarm}.
	 */
	@Id
	@Column(name = "ID_ALARM", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)
	public String getIdAlarm() {
		return idAlarm;
	}

	/**
	 * Sets the value of the attribute {@link #idAlarm}.
	 * 
	 * @param idAlarm
	 *            The value for the attribute {@link #idAlarm}.
	 */
	public void setIdAlarm(String idAlarm) {
		this.idAlarm = idAlarm;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 * 
	 * @return the value of the attribute {@link #description}.
	 */
	@Column(name = "DESCRIPTION", nullable = true, precision = NumberConstants.NUM200)
	@JsonView(DataTablesOutput.View.class)
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 * 
	 * @param description
	 *            The value for the attribute {@link #description}.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #timeBlocked}.
	 * 
	 * @return the value of the attribute {@link #timeBlocked}.
	 */
	@Column(name = "TIME_BLOCK", nullable = true, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)
	public Long getTimeBlock() {
		return timeBlock;
	}

	/**
	 * Sets the value of the attribute {@link #timeBlocked}.
	 * 
	 * @param timeBlocked
	 *            The value for the attribute {@link #timeBlocked}.
	 */
	public void setTimeBlock(Long timeBlock) {
		this.timeBlock = timeBlock;
	}

	/**
	 * Gets the value of the attribute {@link #active}.
	 * 
	 * @return the value of the attribute {@link #active}.
	 */
	@Column(name = "ACTIVE", nullable = true, precision = NumberConstants.NUM1)
	@Type(type = "yes_no")
	@JsonView(DataTablesOutput.View.class)
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the value of the attribute {@link #active}.
	 * 
	 * @param active
	 *            The value for the attribute {@link #active}.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the value of the attribute {@link #mails}.
	 * 
	 * @return the value of the attribute {@link #mails}.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "R_ALARM_MAIL", joinColumns = @JoinColumn(name = "ID_ALARM", referencedColumnName = "ID_ALARM", nullable = false), inverseJoinColumns = @JoinColumn(name = "ID_MAIL", referencedColumnName = "ID_MAIL", nullable = false))
	@JsonView(DataTablesOutput.View.class)
	public Set<MailMonitoriza> getMails() {
		return mails;
	}

	/**
	 * Sets the value of the attribute {@link #mails}.
	 * 
	 * @param mails
	 *            The value for the attribute {@link #mails}.
	 */
	public void setMails(Set<MailMonitoriza> mails) {
		this.mails = mails;
	}

}
