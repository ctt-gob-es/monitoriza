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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertAudit.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_STATISTICS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 09/11/2021.
 */
@Entity
@Table(name = "ALERT_STATISTICS")
public class AlertStatistic implements Serializable {

	private static final long serialVersionUID = 6321654953274851564L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertStatistic;

	/**
	 * Attribute that represents the alert.
	 */
	private AlertDIMApp alertDIMApp;

	/**
	 * Attribute that represents the type.
	 */
	private AlertDIMType alertDIMType;

	/**
	 * Attribute that represents the template.
	 */
	private AlertDIMTemplate alertDIMTemplate;

	/**
	 * Attribute that represents the node.
	 */
	private AlertDIMNode alertDIMNode;

	/**
	 * Attribute that represents the severity.
	 */
	private AlertDIMSeverity alertDIMSeverity;

	/**
	 * Attribute that represents the time instance of the alert.
	 */
	private Date timestamp;

	/**
	 * Attribute that represents the number of ocurrencies of the alert.
	 */
	private Long ocurrency;

	/**
	 * Gets the value of the attribute {@link #idAlertStatistic}.
	 *
	 * @return the value of the attribute {@link #idAlertStatistic}.
	 */
	@Id
	@Column(name = "STATISTIC_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_statistics")
	@GenericGenerator(name = "sq_alert_statistics", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_STATISTICS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertStatistic() {
		return this.idAlertStatistic;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertStatistic}.
	 *
	 * @param idAlertStatistic
	 *            The value for the attribute {@link #idAlertStatistic}.
	 */
	public void setIdAlertStatistic(final Long idAlertStatistic) {
		this.idAlertStatistic = idAlertStatistic;
	}

	/**
	 * Gets the value of the attribute {@link #alertDIMApp}.
	 *
	 * @return the value of the attribute {@link #alertDIMApp}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APP_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertDIMApp getAlertDIMApp() {
		return this.alertDIMApp;
	}

	/**
	 * Sets the value of the attribute {@link #alertDIMApp}.
	 *
	 * @param alertDIMApp
	 *            The value for the attribute {@link #alertDIMApp}.
	 */
	public void setAlertDIMApp(final AlertDIMApp alertDIMApp) {
		this.alertDIMApp = alertDIMApp;
	}

	/**
	 * Gets the value of the attribute {@link #alertDIMType}.
	 *
	 * @return the value of the attribute {@link #alertDIMType}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertDIMType getAlertDIMType() {
		return this.alertDIMType;
	}

	/**
	 * Sets the value of the attribute {@link #alertDIMType}.
	 *
	 * @param alertDIMType
	 *            The value for the attribute {@link #alertDIMType}.
	 */
	public void setAlertDIMType(final AlertDIMType alertDIMType) {
		this.alertDIMType = alertDIMType;
	}

	/**
	 * Gets the value of the attribute {@link #alertDIMTemplate}.
	 *
	 * @return the value of the attribute {@link #alertDIMTemplate}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertDIMTemplate getAlertDIMTemplate() {
		return this.alertDIMTemplate;
	}

	/**
	 * Sets the value of the attribute {@link #alertDIMTemplate}.
	 *
	 * @param alertDIMTemplate
	 *            The value for the attribute {@link #alertDIMTemplate}.
	 */
	public void setAlertDIMTemplate(final AlertDIMTemplate alertDIMTemplate) {
		this.alertDIMTemplate = alertDIMTemplate;
	}

	/**
	 * Gets the value of the attribute {@link #alertDIMNode}.
	 *
	 * @return the value of the attribute {@link #alertDIMNode}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NODE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertDIMNode getAlertDIMNode() {
		return this.alertDIMNode;
	}

	/**
	 * Sets the value of the attribute {@link #alertDIMNode}.
	 *
	 * @param alertDIMNode
	 *            The value for the attribute {@link #alertDIMNode}.
	 */
	public void setAlertDIMNode(final AlertDIMNode alertDIMNode) {
		this.alertDIMNode = alertDIMNode;
	}

	/**
	 * Gets the value of the attribute {@link #alertDIMSeverity}.
	 *
	 * @return the value of the attribute {@link #alertDIMSeverity}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SEVERITY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertDIMSeverity getAlertDIMSeverity() {
		return this.alertDIMSeverity;
	}

	/**
	 * Sets the value of the attribute {@link #alertDIMSeverity}.
	 *
	 * @param alertDIMSeverity
	 *            The value for the attribute {@link #alertDIMSeverity}.
	 */
	public void setAlertDIMSeverity(final AlertDIMSeverity alertDIMSeverity) {
		this.alertDIMSeverity = alertDIMSeverity;
	}

	/**
	 * Gets the value of the attribute {@link #timestamp}.
	 *
	 * @return the value of the attribute {@link #timestamp}.
	 */
	@Column(name = "TIMESTAMP", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the value of the attribute {@link #timestamp}.
	 *
	 * @param timestamp
	 *            The value for the attribute {@link #timestamp}.
	 */
	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the value of the attribute {@link #ocurrency}.
	 *
	 * @return the value of the attribute {@link #ocurrency}.
	 */
	@Column(name = "OCURRENCY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getOcurrency() {
		return this.ocurrency;
	}

	/**
	 * Sets the value of the attribute {@link #ocurrency}.
	 *
	 * @param ocurrency
	 *            The value for the attribute {@link #ocurrency}.
	 */
	public void setOcurrency(final Long ocurrency) {
		this.ocurrency = ocurrency;
	}
}
