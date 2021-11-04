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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertSeverityMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_SEVERITY</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * <p>
 * Class that maps the <i>ALERT_SEVERITY</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_SEVERITY")
public class AlertSeverityMonitoriza implements Serializable {

	private static final long serialVersionUID = -8267084543031731102L;

	/**
	 * Attribute that represents the id
	 */
	private Long severityTypeId;

	/**
	 * Attribute that represents if the name
	 */
	private String name;

	/**
	 * Attribute that represents if the alerts configuration for this severity
	 */
	private Set<AlertConfigMonitoriza> alertsConfigMonitoriza;

	/**
	 * Gets the value of the attribute {@link #severityTypeId}.
	 *
	 * @return the value of the attribute {@link #severityTypeId}.
	 */
	@Id
	@Column(name = "ID_SEVERITY_TYPE", nullable = false)
	public Long getSeverityTypeId() {
		return this.severityTypeId;
	}

	/**
	 * Sets the value of the attribute {@link #severityTypeId}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #severityTypeId}.
	 */
	public void setSeverityTypeId(final Long severityTypeId) {
		this.severityTypeId = severityTypeId;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #name}.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #alertsConfigMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertsConfigMonitoriza}.
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertSeverityMonitoriza", cascade = CascadeType.ALL)
	public Set<AlertConfigMonitoriza> getAlertsConfigMonitoriza() {
		return this.alertsConfigMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertsConfigMonitoriza}.
	 *
	 * @param alertsConfigMonitoriza
	 *            The value for the attribute {@link #alertsConfigMonitoriza}.
	 */
	public void setAlertsConfigMonitoriza(final Set<AlertConfigMonitoriza> alertsConfigMonitoriza) {
		this.alertsConfigMonitoriza = alertsConfigMonitoriza;
	}


}
