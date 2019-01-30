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
 * <b>File:</b><p>es.gob.monitoriza.alarm.types.IAlarmType.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/01/2019..
 */
package es.gob.monitoriza.alarm.types;

import java.time.LocalDateTime;
import java.util.List;

/** 
 * <p>Class with the basic information of a mail alarm.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30/01/2019.
 */
public abstract class AlarmType {

	/**
	 * Attribute that represents the creation date of the alarm.
	 */
	private LocalDateTime dateOfCreation;
	
	/**
	 * Attribute that represents the addresses to which send this alarm. 
	 */
	private List<String> addresses;
	
	/**
	 * Attribute that represents the time (ms) during this alarm will be blocked. 
	 */
	private Long blockedTime;
	
	/**
	 * Attribute that represents the subject text of the alarm.
	 */
	private String subject;
	
	/**
	 * Attribute that represents the body text of the alarm. 
	 */
	private String body;
	
	/**
	 * Attribute that represents the summary text of the alarm. 
	 */
	private String summary;

	/**
	 * Constructor method for the class AlarmType.java.
	 * @param dateOfCreation
	 * @param addresses
	 * @param blockedTime 
	 */
	protected AlarmType(final LocalDateTime dateOfCreation, final List<String> addresses, final Long blockedTime, final String subjectParam, final String bodyParam) {
		super();
		this.dateOfCreation = dateOfCreation;
		this.addresses = addresses;
		this.blockedTime = blockedTime;
		this.subject = subjectParam;
		this.body = bodyParam;
	}
				
	/**
	 * Gets the value of the attribute {@link #dateOfCreation}.
	 * @return the value of the attribute {@link #dateOfCreation}.
	 */
	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}

	/**
	 * Sets the value of the attribute {@link #dateOfCreation}.
	 * @param dateOfCreation The value for the attribute {@link #dateOfCreation}.
	 */
	public void setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
	/**
	 * Gets the value of the attribute {@link #addresses}.
	 * @return the value of the attribute {@link #addresses}.
	 */
	public List<String> getAddresses() {
		return addresses;
	}

	/**
	 * Sets the value of the attribute {@link #addresses}.
	 * @param addresses The value for the attribute {@link #addresses}.
	 */
	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
	
	/**
	 * Gets the value of the attribute {@link #blockedTime}.
	 * @return the value of the attribute {@link #blockedTime}.
	 */
	public Long getBlockedTime() {
		return blockedTime;
	}

	/**
	 * Sets the value of the attribute {@link #blockedTime}.
	 * @param blockedTime The value for the attribute {@link #blockedTime}.
	 */
	public void setBlockedTime(Long blockedTime) {
		this.blockedTime = blockedTime;
	}

	/**
	 * Gets the value of the attribute {@link #subject}.
	 * @return the value of the attribute {@link #subject}.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the value of the attribute {@link #subject}.
	 * @param subjectParam The value for the attribute {@link #subject}.
	 */
	public void setSubject(String subjectParam) {
		this.subject = subjectParam;
	}

	/**
	 * Gets the value of the attribute {@link #body}.
	 * @return the value of the attribute {@link #body}.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the value of the attribute {@link #body}.
	 * @param bodyParam The value for the attribute {@link #body}.
	 */
	public void setBody(String bodyParam) {
		this.body = bodyParam;
	}

	/**
	 * Gets the value of the attribute {@link #summary}.
	 * @return the value of the attribute {@link #summary}.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the value of the attribute {@link #summary}.
	 * @param summaryParam The value for the attribute {@link #summary}.
	 */
	public void setSummary(String summaryParam) {
		this.summary = summaryParam;
	}			
		
}
