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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfig.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_GRAYLOG_NOTICE_CONFIG</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 * Class that maps the <i>ALERT_GRAYLOG_NOTICE_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_GRAYLOG_NOTICE_CONFIG")
@IdClass(AlertGraylogNoticeConfigID.class)
public class AlertGraylogNoticeConfig implements Serializable {

	private static final long serialVersionUID = 3689789509473277343L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNotSysConfig;

	/**
	 * Attribute that represents the pkey.
	 */
	private String pkey;

	/**
	 * Attribute that represents the value for the pkey.
	 */
	private String value;

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
	public Long getNotSysConfigId() {
		return this.idNotSysConfig;
	}

	/**
	 * Sets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @param resSysConfigId
	 *            The value for the attribute {@link #idNotSysConfig}.
	 */
	public void setNotSysConfigId(final Long notSysConfigId) {
		this.idNotSysConfig = notSysConfigId;
	}

	/**
	 * Gets the value of the attribute {@link #pkey}.
	 *
	 * @return the value of the attribute {@link #pkey}.
	 */
	@Id
	@Column(name = "PKEY", nullable = false)
	public String getPkey() {
		return this.pkey;
	}

	/**
	 * Sets the value of the attribute {@link #mail}.
	 *
	 * @param mail
	 *            The value for the attribute {@link #mail}.
	 */
	public void setPkey(final String pkey) {
		this.pkey = pkey;
	}

	/**
	 * Gets the value of the attribute {@link #value}.
	 *
	 * @return the value of the attribute {@link #value}.
	 */
	@Id
	@Column(name = "VALUE", nullable = false)
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the attribute {@link #value}.
	 *
	 * @param mail
	 *            The value for the attribute {@link #value}.
	 */
	public void setValue(final String value) {
		this.value = value;
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
