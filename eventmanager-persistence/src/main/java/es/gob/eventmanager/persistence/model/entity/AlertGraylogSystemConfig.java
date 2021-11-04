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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertGraylogSystemConfig.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_GRAYLOG_SYSTEM_CONFIG</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.gob.eventmanager.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_GRAYLOG_SYSTEM_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_GRAYLOG_SYSTEM_CONFIG")
public class AlertGraylogSystemConfig implements Serializable {

	private static final long serialVersionUID = -6061738727227308518L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertGraylogSystemConfig;

	/**
	 * Attribute that represents the alert system host.
	 */
	private String host;

	/**
	 * Attribute that represents the alert system port.
	 */
	private Integer port;

	/**
	 * Gets the value of the attribute {@link #idAlertSystemMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idAlertSystemMonitoriza}.
	 */
	@Id
	@Column(name = "SYSTEM_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	public Long getIdAlertGraylogSystemConfig() {
		return this.idAlertGraylogSystemConfig;
	}

	/**
	 * Sets the value of the attribute {@link #idAlertSystemMonitoriza}.
	 *
	 * @param alertSystemId
	 *            The value for the attribute {@link #idAlertSystemMonitoriza}.
	 */
	public void setIdAlertGraylogSystemConfig(final Long alertSystemId) {
		this.idAlertGraylogSystemConfig = alertSystemId;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 *
	 * @return the value of the attribute {@link #host}.
	 */
	@Column(name = "HOST", nullable = false)
	public String getHost() {
		return this.host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 *
	 * @param host
	 *            The value for the attribute {@link #host}.
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 *
	 * @return the value of the attribute {@link #port}.
	 */
	@Column(name = "PORT", nullable = false)
	public Integer getPort() {
		return this.port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 *
	 * @param port
	 *            The value for the attribute {@link #port}.
	 */
	public void setPort(final Integer port) {
		this.port = port;
	}
}
