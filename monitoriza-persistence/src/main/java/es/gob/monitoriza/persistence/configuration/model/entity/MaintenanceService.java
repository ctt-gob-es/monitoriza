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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>MAINTENANCE_SERVICE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/03/2019.
 */
@Entity
@Table(name = "MAINTENANCE_SERVICE")
public class MaintenanceService implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 4691852423079503781L;
	
	/**
	 * Constant attribute that represents the string <i>"yes_no"</i>.
	 */
	private static final String CONS_YES_NO = "yes_no";
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idMaintenanceService;
	
	/**
	 * Attribute that represents the identifier name of the service. 
	 */
	private String service;
	
	/**
	 * Attribute that indicates if the service is marked as "in maintenance". 
	 */
	private Boolean isInMaintenance;
	
	/**
	 * Attribute that represents the value of the previous status. 
	 */
	private Integer statusOrigin;

	/**
	 * Gets the value of the attribute {@link #idMaintenanceService}.
	 * @return the value of the attribute {@link #idMaintenanceService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_MAINTENANCE_SERVICE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_maintenance_service")
	@GenericGenerator(
	                  name = "sq_maintenance_service",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_MAINTENANCE_SERVICE"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdMaintenanceService() {
		// CHECKSTYLE:ON
		return idMaintenanceService;
	}

	/**
	 * Sets the value of the attribute {@link #idMaintenanceService}.
	 * @param idMaintenanceServiceParam The value for the attribute {@link #idMaintenanceService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdMaintenanceService(Long idMaintenanceServiceParam) {
		// CHECKSTYLE:ON
		this.idMaintenanceService = idMaintenanceServiceParam;
	}

	/**
	 * Gets the value of the attribute {@link #service}.
	 * @return the value of the attribute {@link #service}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SERVICE", nullable = false, length = NumberConstants.NUM100)
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
	 * Gets the value of the attribute {@link #isInMaintenance}.
	 * @return the value of the attribute {@link #isInMaintenance}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_INMAINTENANCE", nullable = false, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getIsInMaintenance() {
		// CHECKSTYLE:ON
		return isInMaintenance;
	}

	/**
	 * Sets the value of the attribute {@link #isInMaintenance}.
	 * @param isInMaintenanceParam The value for the attribute {@link #isInMaintenance}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsInMaintenance(Boolean isInMaintenanceParam) {
		// CHECKSTYLE:ON
		this.isInMaintenance = isInMaintenanceParam;
	}

	/**
	 * Gets the value of the attribute {@link #statusOrigin}.
	 * @return the value of the attribute {@link #statusOrigin}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "STATUS_ORIGIN", nullable = true, length = NumberConstants.NUM1)
	@JsonView(DataTablesOutput.View.class)
	public Integer getStatusOrigin() {
		// CHECKSTYLE:ON
		return statusOrigin;
	}

	/**
	 * Sets the value of the attribute {@link #statusOrigin}.
	 * @param statusOriginParam The value for the attribute {@link #statusOrigin}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setStatusOrigin(Integer statusOriginParam) {
		// CHECKSTYLE:ON
		this.statusOrigin = statusOriginParam;
	}

}
