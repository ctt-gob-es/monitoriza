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
 * @version 1.1, 22/11/2021.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * Class that maps the <i>ALERT_AUDIT</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_AUDIT")
public class AlertAudit implements Serializable {
	
	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 5093164658287043429L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertAudit;
	
	/**
	 * Attribute that represents name of the {@link ApplicationMonitoriza} that sent the alert.
	 */
	private String appName;
	
	/**
	 * Attribute that represents name of the alert.
	 */
	private String alertName;
	
	/**
	 * Attribute that represents name of application template.
	 */
	private String appTemplateName;
	
	/**
	 * Attribute that represents name of node that sent the alert.
	 */
	private String node;

	/**
	 * Attribute that represents the criticality level of the alert.
	 */
	private String severity;
	
	/**
	 * Attribute that represents the time instance of the alert.
	 */
	private Date timestamp;
	
	/**
	 * Attribute that represents description of the alert.
	 */
	private String description;

	/**
	 * Gets the value of the attribute {@link #idAlertAudit}.
	 *
	 * @return the value of the attribute {@link #idAlertAudit}.
	 */
	@Id
	@Column(name = "ALERT_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_audit")
	@GenericGenerator(name = "sq_alert_audit", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_AUDIT"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertAudit() {
		return idAlertAudit;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertAudit}.
	 *
	 * @param idAlertAudit
	 *            The value for the attribute {@link #idAlertAudit}.
	 */
	public void setIdAlertAudit(Long idAlertAudit) {
		this.idAlertAudit = idAlertAudit;
	}

	/**
	 * Gets the value of the attribute {@link #appName}.
	 *
	 * @return the value of the attribute {@link #appName}.
	 */
	@Column(name = "APP_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getAppName() {
		return appName;
	}

	/**
	 * Sets the value of the attribute {@link #appName}.
	 *
	 * @param appName
	 *            The value for the attribute {@link #appName}.
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Gets the value of the attribute {@link #alertName}.
	 *
	 * @return the value of the attribute {@link #alertName}.
	 */
	@Column(name = "ALERT_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getAlertName() {
		return alertName;
	}

	/**
	 * Sets the value of the attribute {@link #alertName}.
	 *
	 * @param alertName
	 *            The value for the attribute {@link #alertName}.
	 */
	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	/**
	 * Gets the value of the attribute {@link #appTemplateName}.
	 *
	 * @return the value of the attribute {@link #appTemplateName}.
	 */
	@Column(name = "APP_TEMPLATE_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getAppTemplateName() {
		return appTemplateName;
	}

	/**
	 * Sets the value of the attribute {@link #appTemplateName}.
	 *
	 * @param appTemplateName
	 *            The value for the attribute {@link #appTemplateName}.
	 */
	public void setAppTemplateName(String appTemplateName) {
		this.appTemplateName = appTemplateName;
	}

	/**
	 * Gets the value of the attribute {@link #node}.
	 *
	 * @return the value of the attribute {@link #node}.
	 */
	@Column(name = "NODE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getNode() {
		return node;
	}

	/**
	 * Sets the value of the attribute {@link #node}.
	 *
	 * @param node
	 *            The value for the attribute {@link #node}.
	 */
	public void setNode(String node) {
		this.node = node;
	}

	/**
	 * Gets the value of the attribute {@link #severity}.
	 *
	 * @return the value of the attribute {@link #severity}.
	 */
	@Column(name = "SEVERITY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getSeverity() {
		return severity;
	}

	/**
	 * Sets the value of the attribute {@link #severity}.
	 *
	 * @param name
	 *            The value for the attribute {@link #severity}.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * Gets the value of the attribute {@link #timestamp}.
	 *
	 * @return the value of the attribute {@link #timestamp}.
	 */
	@Column(name = "TIMESTAMP", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(DataTablesOutput.View.class)
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the value of the attribute {@link #timestamp}.
	 *
	 * @param timestamp
	 *            The value for the attribute {@link #timestamp}.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 *
	 * @return the value of the attribute {@link #description}.
	 */
	@Column(name = "DESCRIPTION", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public final String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 *
	 * @param description
	 *            The value for the attribute {@link #description}.
	 */
	public final void setDescription(String description) {
		this.description = description;
	}
	
	
}
