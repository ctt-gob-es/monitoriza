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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertAuditControl.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
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

import es.gob.monitoriza.constant.NumberConstants;


/**
 * <p>
 * Class that maps the <i>ALERT_AUDIT_CONTROL</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_AUDIT_CONTROL")
public class AlertAuditControl implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 6448650884150504559L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertAuditControl;
	
	/**
	 * Attribute that represents the time instance of.
	 */
	private Date execBegin;
	
	/**
	 * Attribute that represents the time instance of.
	 */
	private Date execEnd;
	
	/**
	 * Attribute that represents the time instance of.
	 */
	private Date auditBegin;
	
	/**
	 * Attribute that represents the time instance of.
	 */
	private Date auditEnd;
	
	/**
	 * Attribute that represents . 
	 */
	private Integer result;

	/**
	 * Gets the value of the attribute {@link #idAlertAuditControl}.
	 *
	 * @return the value of the attribute {@link #idAlertAuditControl}.
	 */
	@Id
	@Column(name = "ALERT_AUDIT_CONTROL_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_audit_control")
	@GenericGenerator(name = "sq_alert_audit_control", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_AUDIT_CONTROL"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public final Long getIdAlertAuditControl() {
		return idAlertAuditControl;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertAuditControl}.
	 *
	 * @param idAlertAuditControl
	 *            The value for the attribute {@link #idAlertAuditControl}.
	 */
	public final void setIdAlertAuditControl(Long idAlertAuditControl) {
		this.idAlertAuditControl = idAlertAuditControl;
	}

	/**
	 * Gets the value of the attribute {@link #execBegin}.
	 *
	 * @return the value of the attribute {@link #execBegin}.
	 */
	@Column(name = "EXECUTION_BEGIN")
	@Temporal(TemporalType.TIMESTAMP)
	public final Date getExecBegin() {
		return execBegin;
	}

	/**
	 * Sets the value of the attribute {@link #execBegin}.
	 *
	 * @param execBegin
	 *            The value for the attribute {@link #execBegin}.
	 */
	public final void setExecBegin(Date execBegin) {
		this.execBegin = execBegin;
	}

	/**
	 * Gets the value of the attribute {@link #execEnd}.
	 *
	 * @return the value of the attribute {@link #execEnd}.
	 */
	@Column(name = "EXECUTION_END")
	@Temporal(TemporalType.TIMESTAMP)
	public final Date getExecEnd() {
		return execEnd;
	}

	/**
	 * Sets the value of the attribute {@link #execEnd}.
	 *
	 * @param execEnd
	 *            The value for the attribute {@link #execEnd}.
	 */
	public final void setExecEnd(Date execEnd) {
		this.execEnd = execEnd;
	}

	/**
	 * Gets the value of the attribute {@link #auditBegin}.
	 *
	 * @return the value of the attribute {@link #auditBegin}.
	 */
	@Column(name = "AUDIT_BEGIN")
	@Temporal(TemporalType.TIMESTAMP)
	public final Date getAuditBegin() {
		return auditBegin;
	}

	/**
	 * Sets the value of the attribute {@link #auditBegin}.
	 *
	 * @param auditBegin
	 *            The value for the attribute {@link #auditBegin}.
	 */
	public final void setAuditBegin(Date auditBegin) {
		this.auditBegin = auditBegin;
	}

	/**
	 * Gets the value of the attribute {@link #auditEnd}.
	 *
	 * @return the value of the attribute {@link #auditEnd}.
	 */
	@Column(name = "AUDIT_END")
	@Temporal(TemporalType.TIMESTAMP)
	public final Date getAuditEnd() {
		return auditEnd;
	}

	/**
	 * Gets the value of the attribute {@link #auditEnd}.
	 *
	 * @param auditEnd
	 *            The value for the attribute {@link #auditEnd}.
	 */
	public final void setAuditEnd(Date auditEnd) {
		this.auditEnd = auditEnd;
	}

	/**
	 * Gets the value of the attribute {@link #alertName}.
	 *
	 * @return the value of the attribute {@link #alertName}.
	 */
	@Column(name = "RESULT", precision = NumberConstants.NUM1)
	public final Integer getResult() {
		return result;
	}

	/**
	 * Sets the value of the attribute {@link #result}.
	 *
	 * @param result
	 *            The value for the attribute {@link #result}.
	 */
	public final void setResult(Integer result) {
		this.result = result;
	}
	
}
