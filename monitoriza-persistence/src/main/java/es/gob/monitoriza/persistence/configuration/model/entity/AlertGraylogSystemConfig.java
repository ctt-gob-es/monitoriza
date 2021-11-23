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
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the <i>ALERT_GRAYLOG_SYSTEM_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
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
	private Long port;

	/**
	 * Gets the value of the attribute {@link #idAlertSystemMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idAlertSystemMonitoriza}.
	 */
	@Id
	@Column(name = "SYSTEM_ID", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
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
	@JsonView(DataTablesOutput.View.class)
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
	@JsonView(DataTablesOutput.View.class)
	public Long getPort() {
		return this.port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 *
	 * @param port
	 *            The value for the attribute {@link #port}.
	 */
	public void setPort(final Long port) {
		this.port = port;
	}
}
