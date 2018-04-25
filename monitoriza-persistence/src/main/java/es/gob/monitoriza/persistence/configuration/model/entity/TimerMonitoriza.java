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
 * <b>Date:</b><p>9 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 9 abr. 2018.
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
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>TIMER_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 9 abr. 2018.
 */
@Entity
@Table(name = "TIMER_MONITORIZA")
public class TimerMonitoriza implements Serializable {

	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = 8821785055684750117L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTimer;
	
	/**
	 * Attribute that represents the name of the timer. 
	 */
	private String name;
	
	/**
	 * Attribute that represents. 
	 */
	private Long frequency;

	/**
	 * Gets the value of the attribute {@link #idTimer}.
	 * @return the value of the attribute {@link #idTimer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_TIMER", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_timer_monitoriza")
	@GenericGenerator(
	                  name = "sq_timer_monitoriza",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_TIMER_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)	
	public Long getIdTimer() {
		return idTimer;
	}

	/**
	 * Sets the value of the attribute {@link #idTimer}.
	 * @param isBlockedParam The value for the attribute {@link #idTimer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdTimer(Long idTimer) {
		this.idTimer = idTimer;
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
	 * Gets the value of the attribute {@link #frequency}.
	 * @return the value of the attribute {@link #frequency}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "FREQUENCY", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public Long getFrequency() {
		return frequency;
	}

	/**
	 * Sets the value of the attribute {@link #frequency}.
	 * @param isBlockedParam The value for the attribute {@link #frequency}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	

}
