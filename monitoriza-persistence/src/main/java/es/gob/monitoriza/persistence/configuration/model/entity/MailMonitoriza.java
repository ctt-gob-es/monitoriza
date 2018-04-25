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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>MAIL_MONITORIZA</i> database table as a Plain Old Java Object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 9 abr. 2018.
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

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>MAIL_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 9 abr. 2018.
 */
@Entity
@Table(name = "MAIL_MONITORIZA")
public class MailMonitoriza implements Serializable {

	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = 9090589742672624291L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idMail;
	
	/**
	 * Attribute that represents a valid email address. 
	 */
	private String emailAddress;
	
	/**
	 * Attribute that represents the alarms using this mail. 
	 */
	private Set<AlarmMonitoriza> alarms;

	/**
	 * Gets the value of the attribute {@link #idMail}.
	 * @return the value of the attribute {@link #idMail}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_MAIL", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_mail_monitoriza")
	@GenericGenerator(
	                  name = "sq_mail_monitoriza",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_MAIL_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdMail() {
		return idMail;
	}

	/**
	 * Sets the value of the attribute {@link #idMail}.
	 * @param isBlockedParam The value for the attribute {@link #idMail}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdMail(Long idMail) {
		this.idMail = idMail;
	}

	/**
	 * Gets the value of the attribute {@link #emailAddress}.
	 * @return the value of the attribute {@link #emailAddress}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "EMAIL_ADDRESS", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the value of the attribute {@link #emailAddress}.
	 * @param isBlockedParam The value for the attribute {@link #emailAddress}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the value of the attribute {@link #emailAddress}.
	 * @return the value of the attribute {@link #emailAddress}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	           name="R_ALARM_MAIL",
	           joinColumns=@JoinColumn(name="ID_MAIL", referencedColumnName="ID_MAIL", nullable = false), 
	           inverseJoinColumns=@JoinColumn(name="ID_ALARM", referencedColumnName="ID_ALARM", nullable = false))
	public Set<AlarmMonitoriza> getAlarms() {
		return alarms;
	}

	/**
	 * Sets the value of the attribute {@link #alarms}.
	 * @param isBlockedParam The value for the attribute {@link #alarms}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAlarms(Set<AlarmMonitoriza> alarms) {
		this.alarms = alarms;
	}
		

}
