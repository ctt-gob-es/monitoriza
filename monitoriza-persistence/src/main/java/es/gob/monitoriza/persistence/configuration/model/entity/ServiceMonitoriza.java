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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>SERVICE_MONITORIZA</i> database table as a Plain Old Java Object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 9 abr. 2018.
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

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>SERVICE_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 9 abr. 2018.
 */
@Entity
@Table(name = "SERVICE_MONITORIZA")
public class ServiceMonitoriza implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 7130444201662862067L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idService;
		
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String name;
	
	/**
	 * Attribute that represents the time interval in milliseconds that must pass
	 * before the request for this service is cancelled. 
	 */
	private Long timeout;
	
	/**
	 * Attribute that represents the name of the service in the WSDL endpoint path. 
	 */
	private String nameWsdl;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered degraded. 
	 */
	private Long degradedThreshold;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered lost. 
	 */
	private Long lostThreshold;
	
	/**
	 * Attribute that represents the timer configured to this service. 
	 */
	private TimerMonitoriza timer;
	
	/**
	 * Attribute that represents the alarm configured to this service. 
	 */
	private AlarmMonitoriza alarm;
	
	/**
	 * Attribute that represents the @firma platform that could be configured to this service. 
	 */
	private PlatformMonitoriza platform;
		
	/**
	 * Gets the value of the attribute {@link #idService}.
	 * @return the value of the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_SERVICE_MONITORIZA", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_service_monitoriza")
	@GenericGenerator(
	                  name = "sq_service_monitoriza",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SERVICE_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdService() {
		return idService;
	}

	/**
	 * Sets the value of the attribute {@link #idService}.
	 * @param isBlockedParam The value for the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdService(Long idService) {
		this.idService = idService;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param isBlockedParam The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #timeout}.
	 * @return the value of the attribute {@link #timeout}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SERVICE_TIMEOUT", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * Sets the value of the attribute {@link #timeout}.
	 * @param isBlockedParam The value for the attribute {@link #timeout}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	/**
	 * Gets the value of the attribute {@link #nameWsdl}.
	 * @return the value of the attribute {@link #nameWsdl}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME_ENDPOINT_WSDL", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getNameWsdl() {
		return nameWsdl;
	}

	/**
	 * Sets the value of the attribute {@link #idService}.
	 * @param isBlockedParam The value for the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNameWsdl(String nameWsdl) {
		this.nameWsdl = nameWsdl;
	}

	/**
	 * Gets the value of the attribute {@link #degradedThreshold}.
	 * @return the value of the attribute {@link #degradedThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "DEGRADED_THRESHOLD", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public Long getDegradedThreshold() {
		return degradedThreshold;
	}

	/**
	 * Sets the value of the attribute {@link #degradedThreshold}.
	 * @param isBlockedParam The value for the attribute {@link #degradedThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setDegradedThreshold(Long degradedThreshold) {
		this.degradedThreshold = degradedThreshold;
	}

	/**
	 * Gets the value of the attribute {@link #lostThreshold}.
	 * @return the value of the attribute {@link #lostThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "LOST_THRESHOLD", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public Long getLostThreshold() {
		return lostThreshold;
	}

	/**
	 * Sets the value of the attribute {@link #lostThreshold}.
	 * @param isBlockedParam The value for the attribute {@link #lostThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLostThreshold(Long lostThreshold) {
		this.lostThreshold = lostThreshold;
	}

	/**
	 * Gets the value of the attribute {@link #timer}.
	 * @return the value of the attribute {@link #timer}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIMER_SERVICE", nullable = false)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public TimerMonitoriza getTimer() {
		return timer;
	}

	/**
	 * Sets the value of the attribute {@link #timer}.
	 * @param isBlockedParam The value for the attribute {@link #timer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTimer(TimerMonitoriza timer) {
		this.timer = timer;
	}

	/**
	 * Gets the value of the attribute {@link #alarm}.
	 * @return the value of the attribute {@link #alarm}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ALARM_SERVICE", nullable = false)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public AlarmMonitoriza getAlarm() {
		return alarm;
	}

	/**
	 * Sets the value of the attribute {@link #alarm}.
	 * @param isBlockedParam The value for the attribute {@link #alarm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAlarm(AlarmMonitoriza alarm) {
		this.alarm = alarm;
	}

	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PLATFORM_SERVICE", nullable = true)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public PlatformMonitoriza getPlatform() {
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param isBlockedParam The value for the attribute {@link #platform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPlatform(PlatformMonitoriza platform) {
		this.platform = platform;
	}

}
