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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.DailySpieDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.time.LocalDateTime;

/** 
 * <p>Data transfer object that contains the results of status queries by time range</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/01/2019.
 */
public class DailySpieDTO {
			
		
	/**
	 * Attribute that represents the status value for the service. 
	 */
	private String status;
	
	/**
	 * Attribute that represents the name of the system of the service. 
	 */
	private String system;
	
	/**
	 * Attribute that represents the name of the node of the service. 
	 */
	private String node;
		
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String service;
	
	/**
	 * Attribute that represents the sampling time of the obtained status. 
	 */
	private LocalDateTime sampligTime;
	
	
	
	/**
	 * Constructor method for the class DailySpieDTO.java.
	 * @param statusParam {@link #status} to set
	 * @param systemParam {@link #system} to set
	 * @param nodeParam {@link #node} to set
	 * @param serviceParam {@link #service} to set
	 * @param sampligTimeParam {@link #sampligTime} to set
	 */
	public DailySpieDTO(String statusParam, String systemParam, String nodeParam, String serviceParam, LocalDateTime sampligTimeParam) {
		super();
		
		this.status = statusParam;
		this.system = systemParam;
		this.service = serviceParam;
		this.node = nodeParam;
		this.sampligTime = sampligTimeParam;
	}
	

	/**
	 * Gets the value of the attribute {@link #status}.
	 * @return the value of the attribute {@link #status}.
	 */
	public String getStatus() {
	
		return status;
	}

	/**
	 * Sets the value of the attribute {@link #status}.
	 * @param statusParam The value for the attribute {@link #status}.
	 */
	public void setStatus(String statusParam) {
		this.status = statusParam;
	}

	/**
	 * Gets the value of the attribute {@link #system}.
	 * @return the value of the attribute {@link #system}.
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * Sets the value of the attribute {@link #system}.
	 * @param systemParam The value for the attribute {@link #system}.
	 */
	public void setSystem(String systemParam) {
		this.system = systemParam;
	}

		
	/**
	 * Gets the value of the attribute {@link #service}.
	 * @return the value of the attribute {@link #service}.
	 */
	public String getService() {
		return service;
	}
			
	/**
	 * Sets the value of the attribute {@link #node}.
	 * @param nodeParam The value for the attribute {@link #node}.
	 */
	public void setNode(String nodeParam) {
		this.node = nodeParam;
	}

		
	/**
	 * Gets the value of the attribute {@link #node}.
	 * @return the value of the attribute {@link #node}.
	 */
	public String getNode() {
		return node;
	}

	/**
	 * Sets the value of the attribute {@link #service}.
	 * @param serviceParam The value for the attribute {@link #service}.
	 */
	public void setService(String serviceParam) {
		this.service = serviceParam;
	}

	/**
	 * Gets the value of the attribute {@link #sampligTime}.
	 * @return the value of the attribute {@link #sampligTime}.
	 */
	public LocalDateTime getSamplingTime() {
		return sampligTime;
	}

	/**
	 * Sets the value of the attribute {@link #sampligTime}.
	 * @param sampligTimeParam The value for the attribute {@link #sampligTime}.
	 */
	public void setSamplingTime(LocalDateTime sampligTimeParam) {
		this.sampligTime = sampligTimeParam;
	}
	
	
}
