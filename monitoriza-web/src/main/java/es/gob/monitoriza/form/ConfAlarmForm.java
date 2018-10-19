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
public class ConfAlarmForm {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in
	 * the form.
	 */
	private String idAlarm;

	private String description;

	private Long timeBlock;

	private Boolean active;

	private String mails;

	private String mailsConcat;

	/**
	 * Gets the value of the attribute {@link #idAlarm}.
	 * 
	 * @return the value of the attribute {@link #idAlarm}.
	 */
	public String getIdAlarm() {
		return idAlarm;
	}

	/**
	 * Sets the value of the attribute {@link #idAlarm}.
	 * 
	 * @param idAlarm
	 *            The value for the attribute {@link #idAlarm}.
	 */
	public void setIdAlarm(String idAlarm) {
		this.idAlarm = idAlarm;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 * 
	 * @return the value of the attribute {@link #description}.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 * 
	 * @param description
	 *            The value for the attribute {@link #description}.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #timeBlock}.
	 * 
	 * @return the value of the attribute {@link #timeBlock}.
	 */
	public Long getTimeBlock() {
		return timeBlock;
	}

	/**
	 * Sets the value of the attribute {@link #timeBlock}.
	 * 
	 * @param timeBlock
	 *            The value for the attribute {@link #timeBlock}.
	 */
	public void setTimeBlock(Long timeBlock) {
		this.timeBlock = timeBlock;
	}

	/**
	 * Gets the value of the attribute {@link #active}.
	 * 
	 * @return the value of the attribute {@link #active}.
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the value of the attribute {@link #active}.
	 * 
	 * @param active
	 *            The value for the attribute {@link #active}.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the value of the attribute {@link #mails}.
	 * 
	 * @return the value of the attribute {@link #mails}.
	 */
	public String getMails() {
		return mails;
	}

	/**
	 * Sets the value of the attribute {@link #mails}.
	 * 
	 * @param mails
	 *            The value for the attribute {@link #mails}.
	 */
	public void setMails(String mails) {
		this.mails = mails;
	}

	/**
	 * Gets the value of the attribute {@link #mailsConcat}.
	 * 
	 * @return the value of the attribute {@link #mailsConcat}.
	 */
	public String getMailsConcat() {
		return mailsConcat;
	}

	/**
	 * Sets the value of the attribute {@link #mailsConcat}.
	 * 
	 * @param mailsConcat
	 *            The value for the attribute {@link #mailsConcat}.
	 */
	public void setMailsConcat(String mailsConcat) {
		this.mailsConcat = mailsConcat;
	}

}
