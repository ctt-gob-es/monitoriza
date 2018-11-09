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

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents a row of the datatable status semaphore.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 31/10/2018.
 */
public class RowStatusSpieDTO {
			
	/**
	 * Attribute that represents the status of the service. 
	 */
	private Integer statusValue;
	
	/**
	 * Attribute that represents the name of the node. 
	 */
	private String nodeName;
	
	/**
	 * Attribute that represents the address of the node. 
	 */
	private String nodeAddress;
	
	/**
	 * Attribute that represents the name of the platform. 
	 */
	private String system;
	
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String spieService;
			
	/**
	 * Attribute that represents the date time of the sampling. 
	 */
	private String samplingTime;
	
	/**
	 * Attribute that represents the time result for each request file. 
	 */
	private List<AvgTimesServiceDTO> partialRequestResult;
	
	/**
	 * Attribute that represents the local date time in which the results were obtained. 
	 */
	private LocalDateTime statusUptodate;
	
	/**
	 * Constructor method for the class StatusSpieUptodate.java.
	 * @param systemParam {@link #system} to set
	 * @param nodeNameParam {@link #nodeName} to set
	 * @param nodeAddressParam {@link #nodeAddress} to set
	 * @param spieServiceParam {@link #spieService} to set
	 * @param statusValueParam {@link #statusValue} to set
	 * @param partialRequestResultParam {@link #partialRequestResult} to set
	 * @param statusUptodateParam {@link #statusUptodate} to set
	 */
	public RowStatusSpieDTO(final String systemParam, final String nodeNameParam, final String nodeAddressParam, final String spieServiceParam, final Integer statusValueParam, final List<AvgTimesServiceDTO> partialRequestResultParam, final LocalDateTime statusUptodateParam) {
		super();
		this.system = systemParam;
		this.nodeName = nodeNameParam;
		this.nodeAddress = nodeAddressParam;
		this.spieService = spieServiceParam;
		this.statusValue = statusValueParam;
		this.partialRequestResult = partialRequestResultParam;
		this.statusUptodate = statusUptodateParam;
	}


	/**
	 * Gets the value of the attribute {@link #statusValue}.
	 * @return the value of the attribute {@link #statusValue}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public Integer getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the value of the attribute {@link #statusValue}.
	 * @param statusValueParam the value for the attribute {@link #statusValue} to set.
	 */
	public void setStatusValue(Integer statusValueParam) {
		this.statusValue = statusValueParam;
	}

	/**
	 * Gets the value of the attribute {@link #nodeName}.
	 * @return the value of the attribute {@link #nodeName}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Sets the value of the attribute {@link #nodeName}.
	 * @param nodeNameParam the value for the attribute {@link #nodeName} to set.
	 */
	public void setNodeName(String nodeNameParam) {
		this.nodeName = nodeNameParam;
	}

	/**
	 * Gets the value of the attribute {@link #nodeAddress}.
	 * @return the value of the attribute {@link #nodeAddress}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public String getNodeAddress() {
		return nodeAddress;
	}

	/**
	 * Sets the value of the attribute {@link #nodeAddress}.
	 * @param nodeAddressParam the value for the attribute {@link #nodeAddress} to set.
	 */
	public void setNodeAddress(String nodeAddressParam) {
		this.nodeAddress = nodeAddressParam;
	}

	/**
	 * Gets the value of the attribute {@link #system}.
	 * @return the value of the attribute {@link #system}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public String getSystem() {
		return system;
	}

	/**
	 * Sets the value of the attribute {@link #system}.
	 * @param systemParam the value for the attribute {@link #system} to set.
	 */
	public void setSystem(String systemParam) {
		this.system = systemParam;
	}

	/**
	 * Gets the value of the attribute {@link #spieService}.
	 * @return the value of the attribute {@link #spieService}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public String getSpieService() {
		return spieService;
	}

	/**
	 * Sets the value of the attribute {@link #spieService}.
	 * @param spieServiceParam the value for the attribute {@link #spieService} to set.
	 */
	public void setSpieService(String spieServiceParam) {
		this.spieService = spieServiceParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #samplingTime}.
	 * @return the value of the attribute {@link #samplingTime}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public String getSamplingTime() {
		return samplingTime;
	}

	/**
	 * Sets the value of the attribute {@link #samplingTime}.
	 * @param samplingTimeParam the value for the attribute {@link #samplingTime} to set.
	 */
	public void setSamplingTime(String samplingTimeParam) {
		this.samplingTime = samplingTimeParam;
	}

	/**
	 * Gets the value of the attribute {@link #partialRequestResult}.
	 * @return the value of the attribute {@link #partialRequestResult}.
	 */
	@JsonView(StatusVipDTO.View.class)
	public List<AvgTimesServiceDTO> getPartialRequestResult() {
		return partialRequestResult;
	}

	/**
	 * Sets the value of the attribute {@link #partialRequestResult}.
	 * @param partialRequestResultParam the value for the attribute {@link #partialRequestResult} to set.
	 */
	public void setPartialRequestResult(List<AvgTimesServiceDTO> partialRequestResultParam) {
		this.partialRequestResult = partialRequestResultParam;
	}

	/**
	 * Gets the value of the attribute {@link #statusUptodate}.
	 * @return the value of the attribute {@link #statusUptodate}.
	 */
	@JsonView(StatusVipDTO.View.class)
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
	
	

}
