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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the <i>ALERT_MAIL_NOTICE_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_MAIL_NOTICE_CONFIG")
@IdClass(AlertMailNoticeConfigID.class)
public class AlertMailNoticeConfig implements Serializable {

	private static final long serialVersionUID = 7339848317472070010L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNotSysConfig;

	/**
	 * Attribute that represents the mail.
	 */
	private String mail;

	/**
	 * Attribute that represents the alert configuration system.
	 */
    private AlertConfigSystem alertConfigSystem;

	/**
	 * Gets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @return the value of the attribute {@link #idNotSysConfig}.
	 */
	@Id
	@Column(name = "NOT_SYS_CONFIG_ID", unique = true, nullable = false)
	public Long getIdNotSysConfig() {
		return this.idNotSysConfig;
	}

	/**
	 * Sets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @param resSysConfigId
	 *            The value for the attribute {@link #idNotSysConfig}.
	 */
	public void setIdNotSysConfig(final Long idNotSysConfig) {
		this.idNotSysConfig = idNotSysConfig;
	}

	/**
	 * Gets the value of the attribute {@link #mail}.
	 *
	 * @return the value of the attribute {@link #mail}.
	 */
	@Id
	@Column(name = "MAIL", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getMail() {
		return this.mail;
	}

	/**
	 * Sets the value of the attribute {@link #mail}.
	 *
	 * @param mail
	 *            The value for the attribute {@link #mail}.
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOT_SYS_CONFIG_ID", nullable = false ,insertable=false, updatable=false)
	public AlertConfigSystem getAlertConfigSystem() {
		return this.alertConfigSystem;
	}

	public void setAlertConfigSystem(final AlertConfigSystem alertConfigSystem) {
		this.alertConfigSystem = alertConfigSystem;
	}

}
