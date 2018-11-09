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
 * <b>File:</b><p>es.gob.monitoriza.spie.response.AvgTimesServiceJson.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>06/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 06/11/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that store the results of the average response times per service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 06/11/2018.
 */
public class AvgTimesServiceDTO {
	
	/**
	 * Attribute that represents the name of the @Firma service. 
	 */
	private String serviceName;
	
	/**
	 * Attribute that represents the average time in which the service returns a response. 
	 */
	private Float avgTime;
	
	/**
	 * Attribute that represents the maximum time for the service to return a response. 
	 */
	private Long maxTime;
	
	/**
	 * Attribute that represents the number of transactions that have exceeded the maximum time. 
	 */
	private Long numTransAboveMax;
	
	/**
	 * Attribute that represents the total number of tranactions processed per service. 
	 */
	private Long totalNumTrans;

	/**
	 * Gets the {@link #serviceName}.
	 * @return {@link String}.
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the {@link #serviceName}.
	 * @param serviceNameParam value for {@link #serviceName} to set
	 */
	public void setServiceName(final String serviceNameParam) {
		this.serviceName = serviceNameParam;
	}

	/**
	 * Gets the {@link #avgTime}.
	 * @return {@link Float}.
	 */
	@JsonView(StatusSpieDTO.View.class)
	public Float getAvgTime() {
		return avgTime;
	}

	/**
	 * Sets the {@link #avgTime}.
	 * @param avgTimeParam value for {@link #avgTime} to set
	 */
	public void setAvgTime(final Float avgTimeParam) {
		this.avgTime = avgTimeParam;
	}

	/**
	 * Gets the {@link #maxTime}.
	 * @return {@link Long}.
	 */
	@JsonView(StatusSpieDTO.View.class)
	public Long getMaxTime() {
		return maxTime;
	}

	/**
	 * Sets the {@link #maxTime}.
	 * @param maxTimeParam value for {@link #maxTime} to set
	 */
	public void setMaxTime(final Long maxTimeParam) {
		this.maxTime = maxTimeParam;
	}

	/**
	 * Gets the {@link #numTransAboveMax}.
	 * @return {@link Long}.
	 */
	@JsonView(StatusSpieDTO.View.class)
	public Long getNumTransAboveMax() {
		return numTransAboveMax;
	}

	/**
	 * Sets the {@link #numTransAboveMax}.
	 * @param numTransAboveMaxParam value for {@link #numTransAboveMax} to set
	 */
	public void setNumTransAboveMax(final Long numTransAboveMaxParam) {
		this.numTransAboveMax = numTransAboveMaxParam;
	}

	/**
	 * Gets the {@link #totalNumTrans}.
	 * @return {@link Long}.
	 */
	@JsonView(StatusSpieDTO.View.class)
	public Long getTotalNumTrans() {
		return totalNumTrans;
	}

	/**
	 * Sets the {@link #totalNumTrans}.
	 * @param totalNumTransParam value for {@link #totalNumTrans} to set
	 */
	public void setTotalNumTrans(final Long totalNumTransParam) {
		this.totalNumTrans = totalNumTransParam;
	}
	
}
