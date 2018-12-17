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
 * <b>Date:</b><p>8 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>
 * Class that represents the backing form for adding/editing an alarm.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 24/10/2018.
 */
public class AlarmDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private Long idAlarm;

	/**
	 * Attribute that represents . 
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.alarm.name.notempty}")
	@Size(min = NumberConstants.NUM3, max = NumberConstants.NUM254, groups = ThenCheckIt.class)
	private String name;

	/**
	 * Attribute that represents . 
	 */
	@NotNull(message="{form.valid.alarm.blockedTime.notempty}")
	private Long blockedTime;

	/**
	 * Attribute that represents the concatenated mail addresses for degraded alarms. 
	 */
	private String degradedConcat;

	/**
	 * Attribute that represents the concatenated mail addresses for down alarms. 
	 */
	private String downConcat;

	/**
	 * Attribute that represents the concatenated mail addresses for degraded alarms. 
	 */
	private String emailsDegraded;

	/**
	 * Attribute that represents the concatenated mail addresses for down alarms. 
	 */
	private String emailsDown;

	/**
	 * @return the idAlarm
	 */
	public Long getIdAlarm() {
		return idAlarm;
	}

	/**
	 * @param idAlarmParam the idAlarm to set
	 */
	public void setIdAlarm(Long idAlarmParam) {
		this.idAlarm = idAlarmParam;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param nameParam the name to set
	 */
	public void setName(String nameParam) {
		this.name = nameParam;
	}

	/**
	 * @return the blockedTime
	 */
	public Long getBlockedTime() {
		return blockedTime;
	}

	/**
	 * @param blockedTimeParam
	 *            the blockedTime to set
	 */
	public void setBlockedTime(Long blockedTimeParam) {
		this.blockedTime = blockedTimeParam;
	}

	/**
	 * @return the degradedConcat
	 */
	public String getDegradedConcat() {
		return degradedConcat;
	}

	/**
	 * @param degradedConcatParam
	 *            the degradedConcat to set
	 */
	public void setDegradedConcat(String degradedConcatParam) {
		this.degradedConcat = degradedConcatParam;
	}

	/**
	 * @return the downConcat
	 */
	public String getDownConcat() {
		return downConcat;
	}

	/**
	 * @param downConcatParam
	 *            the downConcat to set
	 */
	public void setDownConcat(String downConcatParam) {
		this.downConcat = downConcatParam;
	}

	/**
	 * @return the emailsDegraded
	 */
	public String getEmailsDegraded() {
		return emailsDegraded;
	}

	/**
	 * @param emailsDegradedParam the emailsDegraded to set
	 */
	public void setEmailsDegraded(String emailsDegradedParam) {
		this.emailsDegraded = emailsDegradedParam;
	}

	/**
	 * @return the emailsDown
	 */
	public String getEmailsDown() {
		return emailsDown;
	}

	/**
	 * @param emailsDownParam the emailsDown to set
	 */
	public void setEmailsDown(String emailsDownParam) {
		this.emailsDown = emailsDownParam;
	}

}
