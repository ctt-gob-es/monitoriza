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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
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

import es.gob.monitoriza.constant.NumberConstants;


/** 
 * <p>Class that maps the <i>ALERT_STATISTICS</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_STATISTICS")
public class AlertStatistics implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = -2495715627452487648L;
	
	/**
	 * Attribute that represents the object identifier. 
	 */
	private Long idAlertStatistic;
	
	/**
	 * Attribute that represents . 
	 */
	private AlertDIMApplication application;
	
	/**
	 * Attribute that represents. 
	 */
	private AlertDIMNode node;
	
	/**
	 * Attribute that represents. 
	 */
	private AlertDIMSeverity severity;
	
	/**
	 * Attribute that represents the object identifier. 
	 */
	private AlertDIMTemplate template;
	
	/**
	 * Attribute that represents the object identifier. 
	 */
	private AlertDIMType type;
	
	/**
	 * Attribute that represents the time instance of the alert.
	 */
	private Long timestamp;
	
	/**
	 * Attribute that represents . 
	 */
	private Integer occurrences;
	
	/**
	 * Gets the value of the attribute {@link #idAlertStatistic}.
	 *
	 * @return the value of the attribute {@link #idAlertStatistic}.
	 */
	@Id
	@Column(name = "STATISTIC_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_statistics")
	@GenericGenerator(name = "sq_alert_statistics", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_STATISTICS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public final Long getIdAlertStatistic() {
		return idAlertStatistic;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertStatistic}.
	 *
	 * @param idAlertStatistic
	 *            The value for the attribute {@link #idAlertStatistic}.
	 */
	public final void setIdAlertStatistic(Long idAlertStatistic) {
		this.idAlertStatistic = idAlertStatistic;
	}

	/**
	 * Gets the value of the attribute {@link #application}.
	 *
	 * @return the value of the attribute {@link #application}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APP_ID", nullable = false)
	public final AlertDIMApplication getApplication() {
		return application;
	}

	/**
	 * Sets the value of the attribute {@link #application}.
	 *
	 * @param application
	 *            The value for the attribute {@link #application}.
	 */
	public final void setApplication(AlertDIMApplication application) {
		this.application = application;
	}

	/**
	 * Gets the value of the attribute {@link #node}.
	 *
	 * @return the value of the attribute {@link #node}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NODE", nullable = false)
	public final AlertDIMNode getNode() {
		return node;
	}

	/**
	 * Sets the value of the attribute {@link #node}.
	 *
	 * @param node
	 *            The value for the attribute {@link #node}.
	 */
	public final void setNode(AlertDIMNode node) {
		this.node = node;
	}

	/**
	 * Gets the value of the attribute {@link #severity}.
	 *
	 * @return the value of the attribute {@link #severity}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SEVERITY", nullable = false)
	public final AlertDIMSeverity getSeverity() {
		return severity;
	}

	/**
	 * Sets the value of the attribute {@link #severity}.
	 *
	 * @param severity
	 *            The value for the attribute {@link #severity}.
	 */
	public final void setSeverity(AlertDIMSeverity severity) {
		this.severity = severity;
	}

	/**
	 * Gets the value of the attribute {@link #template}.
	 *
	 * @return the value of the attribute {@link #template}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	public final AlertDIMTemplate getTemplate() {
		return template;
	}

	/**
	 * Sets the value of the attribute {@link #template}.
	 *
	 * @param template
	 *            The value for the attribute {@link #template}.
	 */
	public final void setTemplate(AlertDIMTemplate template) {
		this.template = template;
	}

	/**
	 * Gets the value of the attribute {@link #type}.
	 *
	 * @return the value of the attribute {@link #type}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	public final AlertDIMType getType() {
		return type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 *
	 * @param type
	 *            The value for the attribute {@link #type}.
	 */
	public final void setType(AlertDIMType type) {
		this.type = type;
	}

	/**
	 * Gets the value of the attribute {@link #timestamp}.
	 *
	 * @return the value of the attribute {@link #timestamp}.
	 */
	@Column(name = "TIMESTAMP", nullable = false)
	public final Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the value of the attribute {@link #timestamp}.
	 *
	 * @param timestamp
	 *            The value for the attribute {@link #timestamp}.
	 */
	public final void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the value of the attribute {@link #occurrences}.
	 *
	 * @return the value of the attribute {@link #occurrences}.
	 */
	@Column(name = "OCURRENCY", nullable = false)
	public final Integer getOccurrences() {
		return occurrences;
	}

	/**
	 * Sets the value of the attribute {@link #occurrences}.
	 *
	 * @param occurrences
	 *            The value for the attribute {@link #occurrences}.
	 */
	public final void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}
	

}
