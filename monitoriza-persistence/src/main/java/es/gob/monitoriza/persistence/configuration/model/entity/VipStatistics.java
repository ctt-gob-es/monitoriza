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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.VipStatistics.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/12/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/12/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
 * <p>Class that maps the <i>VIP_STATISTICS</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/12/2018.
 */
@Entity
@Table(name = "VIP_STATISTICS")
public class VipStatistics implements Serializable {

	/**
	 * Attribute that represents the class serial version identifier. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribute that represents the table identifier. 
	 */
	private Long idVipStat;
	
	/**
	 * Attribute that represents the status value for the service. 
	 */
	private String status;
	
	/**
	 * Attribute that represents the name of the platform of the service. 
	 */
	private String platform;
		
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String service;
	
	/**
	 * Attribute that represents the sampling time of the obtained status. 
	 */
	private LocalDate samplingDate;
	
	/**
	 * Gets the value of the attribute {@link #idVipStat}.
	 * @return the value of the attribute {@link #idVipStat}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_VIP_STATISTICS", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_vip_statistics")
	@GenericGenerator(
	                  name = "sq_vip_statistics",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_VIP_STATISTICS"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdVipStat() {
		// CHECKSTYLE:ON
		return idVipStat;
	}

	/**
	 * Sets the value of the attribute {@link #idVipStat}.
	 * @param idVipStatParam The value for the attribute {@link #idVipStat}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdVipStat(Long idVipStatParam) {
		// CHECKSTYLE:ON
		this.idVipStat = idVipStatParam;
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
	 * Gets the value of the attribute {@link #samplingDate}.
	 * @return the value of the attribute {@link #samplingDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "DATE_GROUP", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public LocalDate getSamplingDate() {
		// CHECKSTYLE:ON
		return samplingDate;
	}

	/**
	 * Sets the value of the attribute {@link #samplingDate}.
	 * @param samplingDateParam The value for the attribute {@link #samplingDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSamplingDate(LocalDate samplingDateParam) {
		// CHECKSTYLE:ON
		this.samplingDate = samplingDateParam;
	}

}
