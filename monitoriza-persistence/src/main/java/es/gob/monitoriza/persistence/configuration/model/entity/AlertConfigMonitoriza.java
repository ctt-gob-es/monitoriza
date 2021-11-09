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
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_CONFIG</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_CONFIG")
public class AlertConfigMonitoriza implements Serializable {

	private static final long serialVersionUID = -2504352379844592842L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertConfigMonitoriza;

	/**
	 * Attribute that represents the alert type.
	 */
	private AlertTypeMonitoriza alertTypeMonitoriza;

	/**
	 * Attribute that represents the application of the alert.
	 */
	private ApplicationMonitoriza applicationMonitoriza;

	/**
	 * Attribute that represents the alert level.
	 */
	private AlertSeverityMonitoriza alertSeverityMonitoriza;

	/**
	 * Attribute that represents if the alert is enabled
	 */
	private Boolean enable;

	/**
	 * Attribute that represents if the alert allow block.
	 */
	private Boolean allowBlock;

	/**
	 * Attribute that represents the block condition.
	 */
	private Long blockCondition;

	/**
	 * Attribute that represents the block interval.
	 */
	private Long blockInterval;

	/**
	 * Attribute that represents the block period.
	 */
	private Long blockPeriod;

	/**
	 * Attribute that represents the date of the block.
	 */
	private Date blockTime;

	/**
	 * Attribute that represents the last time of the alert.
	 */
	private Date lastTime;

	/**
	 * Attribute that represents the resume of the alert.
	 */
	private List<ResumeMonitoriza> resumesMonitoriza;

	/**
	 * Attribute that represents the systems of the alert configuration.
	 */
	private Set<AlertConfigSystem> alertConfigSystems;

	/**
	 * Gets the value of the attribute {@link #idAlertConfigMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idAlertConfigMonitoriza}.
	 */
	@Id
	@Column(name = "ALERT_CONFIG_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_config_monitoriza")
	@GenericGenerator(name = "sq_alert_config_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_CONFIG"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertConfigMonitoriza() {
		return this.idAlertConfigMonitoriza;
	}

	public void setIdAlertConfigMonitoriza(final Long idAlertConfig) {
		this.idAlertConfigMonitoriza = idAlertConfig;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertTypeMonitoriza getAlertTypeMonitoriza() {
		return this.alertTypeMonitoriza;
	}

	public void setAlertTypeMonitoriza(final AlertTypeMonitoriza alertTypeMonitoriza) {
		this.alertTypeMonitoriza = alertTypeMonitoriza;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APP_ID", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public ApplicationMonitoriza getApplicationMonitoriza() {
		return this.applicationMonitoriza;
	}

	public void setApplicationMonitoriza(final ApplicationMonitoriza applicationMonitoriza) {
		this.applicationMonitoriza = applicationMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #alertSeverityMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertSeverityMonitoriza}.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SEVERITY_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public AlertSeverityMonitoriza getAlertSeverityMonitoriza() {
		return this.alertSeverityMonitoriza ;
	}

	/**
	 * Sets the value of the attribute {@link #alertSeverityMonitoriza}.
	 *
	 * @param name
	 *            The value for the attribute {@link #alertSeverityMonitoriza}.
	 */
	public void setAlertSeverityMonitoriza(final AlertSeverityMonitoriza alertSeverityMonitoriza) {
		this.alertSeverityMonitoriza = alertSeverityMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #enable}.
	 *
	 * @return the value of the attribute {@link #enable}.
	 */
	@Column(name = "ENABLE", nullable = false)
	@Type(type = "yes_no")
	@JsonView(DataTablesOutput.View.class)
	public Boolean getEnable() {
		return this.enable;
	}

	/**
	 * Sets the value of the attribute {@link #enable}.
	 *
	 * @param enable
	 *            The value for the attribute {@link #enable}.
	 */
	public void setEnable(final Boolean enable) {
		this.enable = enable;
	}

	/**
	 * Gets the value of the attribute {@link #allowBlock}.
	 *
	 * @return the value of the attribute {@link #allowBlock}.
	 */
	@Column(name = "ALLOW_BLOCK", nullable = false)
	@Type(type = "yes_no")
	@JsonView(DataTablesOutput.View.class)
	public Boolean getAllowBlock() {
		return this.allowBlock;
	}

	/**
	 * Sets the value of the attribute {@link #allowBlock}.
	 *
	 * @param allowBlock
	 *            The value for the attribute {@link #allowBlock}.
	 */
	public void setAllowBlock(final Boolean allowBlock) {
		this.allowBlock = allowBlock;
	}

	/**
	 * Gets the value of the attribute {@link #blockCondition}.
	 *
	 * @return the value of the attribute {@link #blockCondition}.
	 */
	@Column(name = "BLOCK_CONDITION", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getBlockCondition() {
		return this.blockCondition;
	}

	/**
	 * Sets the value of the attribute {@link #blockCondition}.
	 *
	 * @param blockCondition
	 *            The value for the attribute {@link #blockCondition}.
	 */
	public void setBlockCondition(final Long blockCondition) {
		this.blockCondition = blockCondition;
	}

	/**
	 * Gets the value of the attribute {@link #blockInterval}.
	 *
	 * @return the value of the attribute {@link #blockInterval}.
	 */
	@Column(name = "BLOCK_INTERVAL", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getBlockInterval() {
		return this.blockInterval;
	}

	/**
	 * Sets the value of the attribute {@link #blockInterval}.
	 *
	 * @param blockInterval
	 *            The value for the attribute {@link #blockInterval}.
	 */
	public void setBlockInterval(final Long blockInterval) {
		this.blockInterval = blockInterval;
	}

	/**
	 * Gets the value of the attribute {@link #blockPeriod}.
	 *
	 * @return the value of the attribute {@link #blockPeriod}.
	 */
	@Column(name = "BLOCK_PERIOD", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getBlockPeriod() {
		return this.blockPeriod;
	}

	/**
	 * Sets the value of the attribute {@link #blockPeriod}.
	 *
	 * @param blockPeriod
	 *            The value for the attribute {@link #blockPeriod}.
	 */
	public void setBlockPeriod(final Long blockPeriod) {
		this.blockPeriod = blockPeriod;
	}

	/**
	 * Gets the value of the attribute {@link #blockTime}.
	 *
	 * @return the value of the attribute {@link #blockTime}.
	 */
	@Column(name = "BLOCK_TIME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Date getBlockTime() {
		return this.blockTime;
	}

	/**
	 * Sets the value of the attribute {@link #blockTime}.
	 *
	 * @param appKey
	 *            The value for the attribute {@link #blockTime}.
	 */
	public void setBlockTime(final Date blockTime) {
		this.blockTime = blockTime;
	}

	/**
	 * Gets the value of the attribute {@link #lastTime}.
	 *
	 * @return the value of the attribute {@link #lastTime}.
	 */
	@Column(name = "LAST_TIME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Date getLastTime() {
		return this.lastTime;
	}

	/**
	 * Sets the value of the attribute {@link #lastTime}.
	 *
	 * @param lastTime
	 *            The value for the attribute {@link #lastTime}.
	 */
	public void setLastTime(final Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * Gets the value of the attribute {@link #resumesMonitoriza}.
	 * @return the value of the attribute {@link #resumesMonitoriza}.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ALERT_RESUME_LIST", joinColumns = @JoinColumn(name = "ALERT_CONFIG_ID", referencedColumnName = "ALERT_CONFIG_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "RESUME_ID", referencedColumnName = "RESUME_ID", nullable = false))
	public List<ResumeMonitoriza> getResumeMonitoriza() {
		return this.resumesMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #resumesMonitoriza}.
	 * @param resumesMonitoriza The value for the attribute {@link #resumesMonitoriza}.
	 */
	public void setResumeMonitoriza(final List<ResumeMonitoriza> resumesMonitoriza) {
		this.resumesMonitoriza = resumesMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #alertConfigSystems}.
	 * @return the value of the attribute {@link #alertConfigSystems}.
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertConfigMonitoriza", cascade = CascadeType.ALL)
	public Set<AlertConfigSystem> getAlertConfigSystems() {
		return this.alertConfigSystems;
	}

	/**
	 * Sets the value of the attribute {@link #alertConfigSystems}.
	 * @param alertConfigSystems The value for the attribute {@link #alertConfigSystems}.
	 */
	public void setAlertConfigSystems(final Set<AlertConfigSystem> alertConfigSystems) {
		this.alertConfigSystems = alertConfigSystems;
	}
}
