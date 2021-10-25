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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AlarmDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

/**
 * <p>
 * Class that represents the backing form for adding/editing an alert system.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 */
public class AlertConfigDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idAlertConfigMonitoriza;

	/**
	 * Attribute that represents the alert type
	 */
	private Long typeID;

	/**
	 * Attribute that represents the sevirity
	 */
	private String severity;

	/**
	 * Attribute that represents if the alert is enabled
	 */
	private String enable;

	/**
	 * Attribute that represents if allow block
	 */
	private String allowBlock;

	/**
	 * Attribute that represents the condition to block
	 */
	private Long blockCondition;

	/**
	 * Attribute that represents the interval to block
	 */
	private Long blockInterval;

	/**
	 * Attribute that represents the period to block
	 */
	private Long blockPeriod;

	/**
	 * Attribute that represents the block time
	 */
	private String blockTime;

	/**
	 * Attribute that represents the last time that the alert happened
	 */
	private String lastTime;

	public Long getIdAlertConfigMonitoriza() {
		return this.idAlertConfigMonitoriza;
	}

	public void setIdAlertConfigMonitoriza(final Long idAlertConfigMonitoriza) {
		this.idAlertConfigMonitoriza = idAlertConfigMonitoriza;
	}

	public Long getTypeID() {
		return this.typeID;
	}

	public void setTypeID(final Long typeID) {
		this.typeID = typeID;
	}

	public String getSeverity() {
		return this.severity;
	}

	public void setSeverity(final String severity) {
		this.severity = severity;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(final String enable) {
		this.enable = enable;
	}

	public String getAllowBlock() {
		return this.allowBlock;
	}

	public void setAllowBlock(final String allowBlock) {
		this.allowBlock = allowBlock;
	}

	public Long getBlockCondition() {
		return this.blockCondition;
	}

	public void setBlockCondition(final Long blockCondition) {
		this.blockCondition = blockCondition;
	}

	public Long getBlockInterval() {
		return this.blockInterval;
	}

	public void setBlockInterval(final Long blockInterval) {
		this.blockInterval = blockInterval;
	}

	public Long getBlockPeriod() {
		return this.blockPeriod;
	}

	public void setBlockPeriod(final Long blockPeriod) {
		this.blockPeriod = blockPeriod;
	}

	public String getBlockTime() {
		return this.blockTime;
	}

	public void setBlockTime(final String blockTime) {
		this.blockTime = blockTime;
	}

	public String getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(final String lastTime) {
		this.lastTime = lastTime;
	}

}