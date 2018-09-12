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
 * <b>File:</b><p>es.gob.monitoriza.form.TimerForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 12/09/2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/**
 * <p>
 * Class that represents the backing form for adding/editing an alarm.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.1, 12/09/2018.
 */
public class AlarmForm {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private Long idAlarm;

	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.alarm.name.notempty}")
	@Size(min = 3, max = 254, groups = ThenCheckIt.class)
	private String name;

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
	 * @param idAlarm
	 *            the idAlarm to set
	 */
	public void setIdAlarm(Long idAlarm) {
		this.idAlarm = idAlarm;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the blockedTime
	 */
	public Long getBlockedTime() {
		return blockedTime;
	}

	/**
	 * @param blockedTime
	 *            the blockedTime to set
	 */
	public void setBlockedTime(Long blockedTime) {
		this.blockedTime = blockedTime;
	}

	/**
	 * @return the degradedConcat
	 */
	public String getDegradedConcat() {
		return degradedConcat;
	}

	/**
	 * @param degradedConcat
	 *            the degradedConcat to set
	 */
	public void setDegradedConcat(String degradedConcat) {
		this.degradedConcat = degradedConcat;
	}

	/**
	 * @return the downConcat
	 */
	public String getDownConcat() {
		return downConcat;
	}

	/**
	 * @param downConcat
	 *            the downConcat to set
	 */
	public void setDownConcat(String downConcat) {
		this.downConcat = downConcat;
	}

	/**
	 * @return the emailsDegraded
	 */
	public String getEmailsDegraded() {
		return emailsDegraded;
	}

	/**
	 * @param emailsDegraded the emailsDegraded to set
	 */
	public void setEmailsDegraded(String emailsDegraded) {
		this.emailsDegraded = emailsDegraded;
	}

	/**
	 * @return the emailsDown
	 */
	public String getEmailsDown() {
		return emailsDown;
	}

	/**
	 * @param emailsDown the emailsDown to set
	 */
	public void setEmailsDown(String emailsDown) {
		this.emailsDown = emailsDown;
	}

	

}
