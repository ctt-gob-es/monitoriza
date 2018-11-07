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
 * @version 1.2, 02/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>SERVICE_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 02/10/2018.
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
	 * Attribute that represents the type of service. 
	 */
	private String serviceType;
	
	/**
	 * Attribute that represents the ZIP file containing the requests. 
	 */
	private RequestServiceFile requestFile;
		
	/**
	 * Gets the value of the attribute {@link #idService}.
	 * @return the value of the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_SERVICE", unique = true, nullable = false, precision = NumberConstants.NUM19)
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
		// CHECKSTYLE:ON
		return idService;
	}

	/**
	 * Sets the value of the attribute {@link #idService}.
	 * @param idService The value for the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdService(Long idService) {
		// CHECKSTYLE:ON
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
		// CHECKSTYLE:ON
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param name The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String name) {
		// CHECKSTYLE:ON
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
		// CHECKSTYLE:ON
		return timeout;
	}

	/**
	 * Sets the value of the attribute {@link #timeout}.
	 * @param timeout The value for the attribute {@link #timeout}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTimeout(Long timeout) {
		// CHECKSTYLE:ON
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
		// CHECKSTYLE:ON
		return nameWsdl;
	}

	/**
	 * Sets the value of the attribute {@link #idService}.
	 * @param nameWsdl The value for the attribute {@link #idService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNameWsdl(String nameWsdl) {
		// CHECKSTYLE:ON
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
		// CHECKSTYLE:ON
		return degradedThreshold;
	}

	/**
	 * Sets the value of the attribute {@link #degradedThreshold}.
	 * @param degradedThreshold The value for the attribute {@link #degradedThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setDegradedThreshold(Long degradedThreshold) {
		// CHECKSTYLE:ON
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
		// CHECKSTYLE:ON
		return lostThreshold;
	}

	/**
	 * Sets the value of the attribute {@link #lostThreshold}.
	 * @param lostThreshold The value for the attribute {@link #lostThreshold}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLostThreshold(Long lostThreshold) {
		// CHECKSTYLE:ON
		this.lostThreshold = lostThreshold;
	}

	/**
	 * Gets the value of the attribute {@link #timer}.
	 * @return the value of the attribute {@link #timer}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIMER_SERVICE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public TimerMonitoriza getTimer() {
		// CHECKSTYLE:ON
		return timer;
	}

	/**
	 * Sets the value of the attribute {@link #timer}.
	 * @param timer The value for the attribute {@link #timer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTimer(TimerMonitoriza timer) {
		// CHECKSTYLE:ON
		this.timer = timer;
	}

	/**
	 * Gets the value of the attribute {@link #alarm}.
	 * @return the value of the attribute {@link #alarm}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ALARM_SERVICE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public AlarmMonitoriza getAlarm() {
		// CHECKSTYLE:ON
		return alarm;
	}

	/**
	 * Sets the value of the attribute {@link #alarm}.
	 * @param alarm The value for the attribute {@link #alarm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAlarm(AlarmMonitoriza alarm) {
		// CHECKSTYLE:ON
		this.alarm = alarm;
	}

	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PLATFORM_SERVICE", nullable = true)
	@JsonView(DataTablesOutput.View.class)
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public PlatformMonitoriza getPlatform() {
		// CHECKSTYLE:ON
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param platform The value for the attribute {@link #platform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPlatform(PlatformMonitoriza platform) {
		// CHECKSTYLE:ON
		this.platform = platform;
	}

	/**
	 * Gets the value of the attribute {@link #serviceType}.
	 * @return the value of the attribute {@link #serviceType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
	@Column(name = "SERVICE_TYPE", nullable = false, length = NumberConstants.NUM100, unique = false)
	@JsonView(DataTablesOutput.View.class)
	public String getServiceType() {
		// CHECKSTYLE:ON
		return serviceType;
	}

	/**
	 * Sets the value of the attribute {@link #serviceType}.
	 * @param serviceType The value for the attribute {@link #serviceType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setServiceType(String serviceType) {
		// CHECKSTYLE:ON
		this.serviceType = serviceType;
	}
	/**
	 * Gets the value of the attribute {@link #requestFile}.
	 * @return the value of the attribute {@link #requestFile}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_REQUEST_FILE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public RequestServiceFile getRequestFile() {
		// CHECKSTYLE:ON
		return requestFile;
	}
	
	/**
	 * Sets the value of the attribute {@link #requestFile}.
	 * @param requestFile The value for the attribute {@link #requestFile}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setRequestFile(RequestServiceFile requestFile) {
		// CHECKSTYLE:ON
		this.requestFile = requestFile;
	}

			

}
