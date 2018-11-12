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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>TIMER_MONITORIZA</i> database table as a Plain Old Java Object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/10/2018.
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
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>SPIE_SCHEDULED</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/10/2018.
 */
@Entity
@Table(name = "SPIE_SCHEDULED")
public class SpieScheduled implements Serializable {
	
	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = -3808126640043401255L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idSpieScheduled;
	
	/**
	 * Attribute that represents the type of platform. 
	 */
	private CPlatformType platformType;
	
	/**
	 * Attribute that represents. 
	 */
	private Boolean updated;

	/**
	 * Gets the value of the attribute {@link #idSpieScheduled}.
	 * @return the value of the attribute {@link #idSpieScheduled}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_SPIE_SCHEDULED", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_spie_scheduled")
	@GenericGenerator(
	                  name = "sq_spie_scheduled",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SPIE_SCHEDULED"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	public Long getIdSpieScheduled() {
		// CHECKSTYLE:ON
		return idSpieScheduled;
	}

	/**
	 * Sets the value of the attribute {@link #idSpieScheduled}.
	 * @param idSpieScheduled The value for the attribute {@link #idSpieScheduled}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdSpieScheduled(Long idSpieScheduled) {
		// CHECKSTYLE:ON
		this.idSpieScheduled = idSpieScheduled;
	}

	/**
	 * Gets the value of the attribute {@link #platformType}.
	 * @return the value of the attribute {@link #platformType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PLATFORM_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public CPlatformType getPlatformType() {
		// CHECKSTYLE:ON
		return platformType;
	}

	/**
	 * Sets the value of the attribute {@link #platformType}.
	 * @param platformType The value for the attribute {@link #platformType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPlatformType(CPlatformType platformType) {
		// CHECKSTYLE:ON
		this.platformType = platformType;
	}

	/**
	 * Gets the value of the attribute {@link #updated}.
	 * @return the value of the attribute {@link #updated}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_UPDATED", nullable = false, precision = 1)
	@Type(type = "yes_no")
	public Boolean isUpdated() {
		// CHECKSTYLE:ON
		return updated;
	}

	/**
	 * Sets the value of the attribute {@link #updated}.
	 * @param updated The value for the attribute {@link #updated}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setUpdated(boolean updated) {
		// CHECKSTYLE:ON
		this.updated = updated;
	}
	

}
