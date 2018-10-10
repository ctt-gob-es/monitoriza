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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.StatusDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>8 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 8 oct. 2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.Map;

/**
 * <p>
 * Data transfer objetc that encapsulates the information of the status.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 8 oct. 2018.
 */
public class StatusDTO {

	private Long statusAux;
	private String status;
	private String service;
	private float averageTime;
	private String samplingTime;
	private Map<String, Long> partialRequestResult;

	/**
	 * @return the statusAux
	 */
	public Long getStatusAux() {
		return statusAux;
	}

	/**
	 * @param statusAux
	 *            the statusAux to set
	 */
	public void setStatusAux(Long statusAux) {
		this.statusAux = statusAux;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the averageTime
	 */
	public float getAverageTime() {
		return averageTime;
	}

	/**
	 * @param averageTime
	 *            the averageTime to set
	 */
	public void setAverageTime(float averageTime) {
		this.averageTime = averageTime;
	}

	/**
	 * @return the samplingTime
	 */
	public String getSamplingTime() {
		return samplingTime;
	}

	/**
	 * @param samplingTime
	 *            the samplingTime to set
	 */
	public void setSamplingTime(String samplingTime) {
		this.samplingTime = samplingTime;
	}

	/**
	 * @return the partialRequestResult
	 */
	public Map<String, Long> getPartialRequestResult() {
		return partialRequestResult;
	}

	/**
	 * @param partialRequestResult
	 *            the partialRequestResult to set
	 */
	public void setPartialRequestResult(Map<String, Long> partialRequestResult) {
		this.partialRequestResult = partialRequestResult;
	}

}
