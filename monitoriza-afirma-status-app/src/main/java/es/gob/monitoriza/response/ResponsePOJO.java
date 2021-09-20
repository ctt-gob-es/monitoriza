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
 * <b>File:</b><p>es.gob.monitoriza.response.ResponsePOJO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 07/09/2021.
 */
package es.gob.monitoriza.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents each of the service results for the JSON response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 07/09/2021.
 */
public class ResponsePOJO {
	
	/**
	 * Attribute that represents the status of the service. 
	 */
	private String status;
	
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String service;
	
	/**
	 * Attribute that represents the average response time. 
	 */
	private Long averageTime;
	
	/**
	 * Attribute that represents the average response time. 
	 */
	private String idTimerLog;
	
	/**
	 * Attribute that represents the date time of the sampling. 
	 */
	private String samplingTime;
	
	/**
	 * Attribute that represents the time result for each request file. 
	 */
	private Map<String,String> partialRequestResult;
	
	/**
	 * Gets the value of the attribute {@link #status}.
	 * @return the value of the attribute {@link #status}.
	 */
	@JsonView(DatatableStatus.View.class)
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the attribute {@link #status}.
	 * @param status the value for the attribute {@link #status} to set.
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * Gets the value of the attribute {@link #service}.
	 * @return the value of the attribute {@link #service}.
	 */
	@JsonView(DatatableStatus.View.class)
	public String getService() {
		return service;
	}

	/**
	 * Sets the value of the attribute {@link #service}.
	 * @param service the value for the attribute {@link #service} to set.
	 */
	public void setService(final String service) {
		this.service = service;
	}

	/**
	 * Gets the value of the attribute {@link #averageTime}.
	 * @return the value of the attribute {@link #averageTime}.
	 */
	@JsonView(DatatableStatus.View.class)
	public Long getAverageTime() {
		return averageTime;
	}

	/**
	 * Sets the value of the attribute {@link #averageTime}.
	 * @param averageTime the value for the attribute {@link #averageTime} to set.
	 */
	public void setAverageTime(final Long averageTime) {
		this.averageTime = averageTime;
	}
	
	/**
	 * Gets the value of the attribute {@link #idTimerLog}.
	 * @return the value of the attribute {@link #idTimerLog}.
	 */
	@JsonView(DatatableStatus.View.class)	
	public final String getIdTimerLog() {
		return idTimerLog;
	}

	/**
	 * Sets the value of the attribute {@link #idTimerLog}.
	 * @param averageTime the value for the attribute {@link #idTimerLog} to set.
	 */
	public final void setIdTimerLog(String idTimerLog) {
		this.idTimerLog = idTimerLog;
	}

	/**
	 * Gets the value of the attribute {@link #samplingTime}.
	 * @return the value of the attribute {@link #samplingTime}.
	 */
	@JsonView(DatatableStatus.View.class)
	public String getSamplingTime() {
		return samplingTime;
	}

	/**
	 * Sets the value of the attribute {@link #samplingTime}.
	 * @param samplingTime the value for the attribute {@link #samplingTime} to set.
	 */
	public void setSamplingTime(final String samplingTime) {
		this.samplingTime = samplingTime;
	}

	/**
	 * Gets the value of the attribute {@link #partialRequestResult}.
	 * @return the value of the attribute {@link #partialRequestResult}.
	 */
	@JsonView(DatatableStatus.View.class)
	public Map<String, String> getPartialRequestResult() {
		return partialRequestResult;
	}

	/**
	 * Sets the value of the attribute {@link #partialRequestResult}.
	 * @param momentoMuestreo the value for the attribute {@link #partialRequestResult} to set.
	 */
	public void setPartialRequestResult(final Map<String, String> partialRequestResult) {
		this.partialRequestResult = partialRequestResult;
	}

}
