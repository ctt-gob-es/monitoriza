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
 * @version 1.1, 10/10/2018.
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

import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that maps the <i>TIMER_SCHEDULED</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 10/10/2018.
 */
@Entity
@Table(name = "TIMER_SCHEDULED")
public class TimerScheduled implements Serializable {

	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = 8821785055684750117L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTimerScheduled;
	
	/**
	 * Attribute that represents the timer configured to this service. 
	 */
	private TimerMonitoriza timer;
	
	/**
	 * Attribute that represents. 
	 */
	private Boolean updated;

	/**
	 * Gets the value of the attribute {@link #idTimerScheduled}.
	 * @return the value of the attribute {@link #idTimerScheduled}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_TIMER_SCHEDULED", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_timer_scheduled")
	@GenericGenerator(
	                  name = "sq_timer_scheduled",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_TIMER_SCHEDULED"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	public Long getIdTimerScheduled() {
		return idTimerScheduled;
	}

	/**
	 * Sets the value of the attribute {@link #idTimerScheduled}.
	 * @param isBlockedParam The value for the attribute {@link #idTimerScheduled}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdTimerScheduled(Long idTimerScheduled) {
		this.idTimerScheduled = idTimerScheduled;
	}

	/**
	 * Gets the value of the attribute {@link #timer}.
	 * @return the value of the attribute {@link #timer}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIMER", nullable = false)
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
