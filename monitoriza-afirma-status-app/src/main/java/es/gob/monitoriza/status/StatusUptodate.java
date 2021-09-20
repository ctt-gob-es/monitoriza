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
 * <b>File:</b><p>es.gob.monitoriza.status.StatusUptodate.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>21 jun. 2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 07/09/2021.
 */
package es.gob.monitoriza.status;

import java.time.LocalDateTime;
import java.util.Map;

import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that stores the last results for a service invocation.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.6, 07/09/2021.
 */
public class StatusUptodate {
	
	/**
	 * Attribute that represents the value of the status for the service. 
	 */
	private String statusValue;
	
	/**
	 * Attribute that represents the value of the average time taken to complete the request to the service. 
	 */
	private Long averageTime;
	
	/**
	 * Attribute that represents the platform/system of the service. 
	 */
	private String platform;
	
	/**
	 * Attribute that represents the time result for each request file. 
	 */
	private Map<String,String> partialRequestResult;
	
	/**
	 * Attribute that represents the date and time of the last status update. 
	 */
	private LocalDateTime statusUptodate;
	
	/**
	 * Attribute that represents the timer identifier in the log. 
	 */
	private String idTimerLog;
	
	/**
	 * Constructor method for the class StatusUptodate.java.
	 */
	public StatusUptodate() {
		
	}

	/**
	 * Constructor method for the class StatusUptodate.java.
	 * @param statusValueParam The status value.
	 * @param platformParam The platform name.
	 * @param averageTimeParam  The average time for the service. 
	 * @param statusUptodateParam The sampling time.
	 * @param partialRequestResultParam The time details for each request. 
	 */
	public StatusUptodate(final String statusValueParam, final String platformParam, final Long averageTimeParam, final String idTimerLog, final LocalDateTime statusUptodateParam, final Map<String,String> partialRequestResultParam) {
		super();
		this.statusValue = statusValueParam;
		this.platform = platformParam;
		this.averageTime = averageTimeParam;
		this.statusUptodate = statusUptodateParam;
		this.idTimerLog = idTimerLog;
		this.partialRequestResult = partialRequestResultParam;
	}

	/**
	 * Gets the value of the attribute {@link #statusValue}.
	 * @return the value of the attribute {@link #statusValue}.
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the value of the attribute {@link #statusValue}.
	 * @param statusValueParam the value for the attribute {@link #statusValue} to set.
	 */
	public void setStatusValue(String statusValueParam) {
		this.statusValue = statusValueParam;
	}
		
	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param platformParam the value for the attribute {@link #platform} to set.
	 */
	public void setPlatform(String platformParam) {
		this.platform = platformParam;
	}

	/**
	 * Gets the value of the attribute {@link #averageTime}.
	 * @return the value of the attribute {@link #averageTime}.
	 */
	public Long getAverageTime() {
		return averageTime;
	}

	/**
	 * Sets the value of the attribute {@link #averageTime}.
	 * @param averageTimeParam the value for the attribute {@link #averageTime} to set.
	 */
	public void setAverageTime(final Long averageTimeParam) {
		this.averageTime = averageTimeParam;
	}

	/**
	 * Gets the value of the attribute {@link #statusUptodate}.
	 * @return the value of the attribute {@link #statusUptodate}.
	 */
	public LocalDateTime getStatusUptodate() {
		return statusUptodate;
	}

	/**
	 * Sets the value of the attribute {@link #statusUptodate}.
	 * @param statusUptodateParam the value for the attribute {@link #statusUptodate} to set.
	 */
	public void setStatusUptodate(LocalDateTime statusUptodateParam) {
		this.statusUptodate = statusUptodateParam;
	}

	/**
	 * Gets the value of the attribute {@link #partialRequestResult}.
	 * @return the value of the attribute {@link #partialRequestResult}.
	 */
	public Map<String, String> getPartialRequestResult() {
		return partialRequestResult;
	}

	/**
	 * Sets the value of the attribute {@link #partialRequestResult}.
	 * @param partialRequestResultParam the value for the attribute {@link #partialRequestResult} to set.
	 */
	public void setPartialRequestResult(Map<String, String> partialRequestResultParam) {
		this.partialRequestResult = partialRequestResultParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #idTimerLog}.
	 * @return the value of the attribute {@link #idTimerLog}.
	 */	
	public final String getIdTimerLog() {
		return idTimerLog;
	}

	/**
	 * Sets the value of the attribute {@link #idTimerLog}.
	 * @param partialRequestResultParam the value for the attribute {@link #idTimerLog} to set.
	 */
	public final void setIdTimerLog(String idTimerLog) {
		this.idTimerLog = idTimerLog;
	}

	/**
	 * Method that returns the advanced information of the service in a formatted String.
	 * @return String "request_file_path1/request_time1;request_file_path2/request_time2;....;request_file_pathn/request_timen"
	 */
	public String generateAdvancedInfoString() {
		
		final StringBuffer advancedInfo = new StringBuffer();
		
		for (Map.Entry<String, String> entry : partialRequestResult.entrySet()) {
			advancedInfo.append(entry.getKey()).append(UtilsStringChar.SYMBOL_SLASH_STRING).append(entry.getValue() == null?-1:entry.getValue());
			advancedInfo.append(UtilsStringChar.SYMBOL_SEMICOLON_STRING);
	    }
		
		advancedInfo.deleteCharAt(advancedInfo.length()-1);
		
		return advancedInfo.toString();
		
	}
	

}
