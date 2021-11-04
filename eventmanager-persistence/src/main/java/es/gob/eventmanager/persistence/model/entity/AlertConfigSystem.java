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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertConfigSystem.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_CONFIG_SYSTEMS</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.eventmanager.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_CONFIG_SYSTEMS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_CONFIG_SYSTEMS")
public class AlertConfigSystem implements Serializable {

	/**
	 * Class id . 
	 */
	private static final long serialVersionUID = 172212084507353638L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNotSysConfig;

	/**
	 * Attribute that represents the alert system.
	 */
	private AlertSystemMonitoriza alertSystemMonitoriza;

	/**
	 * Attribute that represents the alert configuration.
	 */
	private AlertConfigMonitoriza alertConfigMonitoriza;

	private List<AlertMailNoticeConfig> alertMailsNoticeConfig;

	private List<AlertGraylogNoticeConfig> alertGraylogNoticeConfigs;

	/**
	 * Gets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @return the value of the attribute {@link #idNotSysConfig}.
	 */
	@Id
	@Column(name = "NOT_SYS_CONFIG_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_config_systems")
	@GenericGenerator(name = "sq_alert_config_systems", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_CONFIG_SYSTEMS"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdNotSysConfig() {
		return this.idNotSysConfig;
	}

	/**
	 * Sets the value of the attribute {@link #idNotSysConfig}.
	 *
	 * @param idNotSysConfig
	 *            The value for the attribute {@link #idNotSysConfig}.
	 */
	public void setIdNotSysConfig(final Long idNotSysConfig) {
		this.idNotSysConfig = idNotSysConfig;
	}

	/**
	 * Gets the value of the attribute {@link #alertConfigMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertConfigMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALERT_CONFIG_ID", nullable = false)
	public AlertConfigMonitoriza getAlertConfigMonitoriza() {
		return this.alertConfigMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertConfigMonitoriza}.
	 *
	 * @param name
	 *            The value for the attribute {@link #alertConfigMonitoriza}.
	 */
	public void setAlertConfigMonitoriza(final AlertConfigMonitoriza alertConfigMonitoriza) {
		this.alertConfigMonitoriza = alertConfigMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #alertSystemMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertSystemMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSTEM_ID", nullable = false)
	public AlertSystemMonitoriza getAlertSystemMonitoriza() {
		return this.alertSystemMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertSystemMonitoriza}.
	 *
	 * @param alertSystemMonitoriza
	 *            The value for the attribute {@link #alertSystemMonitoriza}.
	 */
	public void setAlertSystemMonitoriza(final AlertSystemMonitoriza alertSystemMonitoriza) {
		this.alertSystemMonitoriza = alertSystemMonitoriza;
	}

	@OneToMany(mappedBy = "alertConfigSystem" ,orphanRemoval = true)
	public List<AlertMailNoticeConfig> getAlertMailsNoticeConfig() {
		return this.alertMailsNoticeConfig;
	}

	public void setAlertMailsNoticeConfig(final List<AlertMailNoticeConfig> alertMailsNoticeConfig) {
		this.alertMailsNoticeConfig = alertMailsNoticeConfig;
	}

	@OneToMany(mappedBy = "alertConfigSystem" ,orphanRemoval = true)
	public List<AlertGraylogNoticeConfig> getAlertGraylogNoticeConfigs() {
		return this.alertGraylogNoticeConfigs;
	}

	public void setAlertGraylogNoticeConfigs(final List<AlertGraylogNoticeConfig> alertGraylogResumeConfigs) {
		this.alertGraylogNoticeConfigs = alertGraylogResumeConfigs;
	}
}
