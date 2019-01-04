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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.NodeAfirma.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;

/** 
 * <p>Class that maps the <i>DAILY_SPIE_MONITORING</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/01/2019.
 */
@Entity
@Table(name = "DAILY_SPIE_MONITORING")
public class DailySpieMonitorig implements Serializable {
			
	/**
	 * Attribute that represents the class serial version identifier. 
	 */
	private static final long serialVersionUID = -7239360711259291729L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idDailySpie;
	
	/**
	 * Attribute that represents the status value for the service. 
	 */
	private String status;
	
	/**
	 * Attribute that represents the name of the platform of the service. 
	 */
	private String platform;
	
	/**
	 * Attribute that represents the name of the node of the service. 
	 */
	private String node;
		
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String service;
	
	/**
	 * Attribute that represents the sampling time of the obtained status. 
	 */
	private LocalDateTime sampligTime;
		
	/**
	 * Gets the value of the attribute {@link #idDailySpie}.
	 * @return the value of the attribute {@link #idDailySpie}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_DAILY_SPIE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_daily_spie_monitoring")
	@GenericGenerator(
	                  name = "sq_daily_spie_monitoring",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_DAILY_SPIE_MONITORING"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdDailySpie() {
		// CHECKSTYLE:ON
		return idDailySpie;
	}

	/**
	 * Sets the value of the attribute {@link #idDailySpie}.
	 * @param idDailySpieParam The value for the attribute {@link #idDailySpie}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdDailySpie(Long idDailySpieParam) {
		// CHECKSTYLE:ON
		this.idDailySpie = idDailySpieParam;
	}

	/**
	 * Gets the value of the attribute {@link #status}.
	 * @return the value of the attribute {@link #status}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "STATUS", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
	public String getStatus() {
		// CHECKSTYLE:ON
		return status;
	}

	/**
	 * Sets the value of the attribute {@link #status}.
	 * @param statusParam The value for the attribute {@link #status}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setStatus(String statusParam) {
		// CHECKSTYLE:ON
		this.status = statusParam;
	}

	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PLATFORM", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
	public String getPlatform() {
		// CHECKSTYLE:ON
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #node}.
	 * @param nodeParam The value for the attribute {@link #node}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNode(String nodeParam) {
		// CHECKSTYLE:ON
		this.node = nodeParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #node}.
	 * @return the value of the attribute {@link #node}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NODE", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
	public String getNode() {
		// CHECKSTYLE:ON
		return node;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param platformParam The value for the attribute {@link #platform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPlatform(String platformParam) {
		// CHECKSTYLE:ON
		this.platform = platformParam;
	}

		
	/**
	 * Gets the value of the attribute {@link #service}.
	 * @return the value of the attribute {@link #service}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SERVICE", length = NumberConstants.NUM100, nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public String getService() {
		// CHECKSTYLE:ON
		return service;
	}

	/**
	 * Sets the value of the attribute {@link #service}.
	 * @param serviceParam The value for the attribute {@link #service}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setService(String serviceParam) {
		// CHECKSTYLE:ON
		this.service = serviceParam;
	}

	/**
	 * Gets the value of the attribute {@link #sampligTime}.
	 * @return the value of the attribute {@link #sampligTime}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SAMPLING_TIME", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public LocalDateTime getSamplingTime() {
		// CHECKSTYLE:ON
		return sampligTime;
	}

	/**
	 * Sets the value of the attribute {@link #sampligTime}.
	 * @param sampligTimeParam The value for the attribute {@link #sampligTime}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSamplingTime(LocalDateTime sampligTimeParam) {
		// CHECKSTYLE:ON
		this.sampligTime = sampligTimeParam;
	}

}
