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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.RowStatusDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>11/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 18/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents a row of the datatable status semaphore.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 18/10/2018.
 */
public class RowStatusDTO {
	
	/**
	 * Attribute that represents the status of the service. 
	 */
	private Long statusAux;
	
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
	private String averageTime;
	
	/**
	 * Attribute that represents the date time of the sampling. 
	 */
	private String samplingTime;
	
	/**
	 * Attribute that represents the time result for each request file. 
	 */
	private Map<String,String> partialRequestResult;
	
	
	/**
	 * 
	 * @return
	 */
	@JsonView(StatusDTO.View.class)
	public Long getStatusAux() {
		return statusAux;
	}

	
	/**
	 * 
	 * @param statusAux
	 */
	public void setStatusAux(Long statusAux) {
		this.statusAux = statusAux;
	}

	/**
	 * Gets the value of the attribute {@link #status}.
	 * @return the value of the attribute {@link #status}.
	 */
	@JsonView(StatusDTO.View.class)
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
	@JsonView(StatusDTO.View.class)
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
	@JsonView(StatusDTO.View.class)
	public String getAverageTime() {
		return averageTime;
	}

	/**
	 * Sets the value of the attribute {@link #averageTime}.
	 * @param averageTime the value for the attribute {@link #averageTime} to set.
	 */
	public void setAverageTime(final String averageTime) {
		this.averageTime = averageTime;
	}

	/**
	 * Gets the value of the attribute {@link #samplingTime}.
	 * @return the value of the attribute {@link #samplingTime}.
	 */
	@JsonView(StatusDTO.View.class)
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
	@JsonView(StatusDTO.View.class)
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
