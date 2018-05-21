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
 * <b>File:</b><p>es.gob.monitoriza.form.ServiceForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 abr. 2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class that represents the backing form for adding/editing a service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 abr. 2018.
 */
public class ServiceForm {
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idService;
		
	/**
	 * Attribute that represents the name of the service. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.service.name.notempty}")
	@Size(min=1, max=30, groups=ThenCheckIt.class)
	private String name;
	
	/**
	 * Attribute that represents the time interval in milliseconds that must pass
	 * before the request for this service is cancelled. 
	 */
	@NotNull(message="{form.valid.service.timeout.notempty}")
	private Long timeout;
	
	/**
	 * Attribute that represents the name of the service in the WSDL endpoint path. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.service.nameWsdl.notempty}")
	@Size(min=1, max=30, groups=ThenCheckIt.class)
	private String nameWsdl;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered degraded. 
	 */
	@NotNull(message="{form.valid.service.degradedThreshold.notempty}")
	private Long degradedThreshold;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered lost. 
	 */
	@NotNull(message="{form.valid.service.lostThreshold.notempty}")
	private Long lostThreshold;
	
	/**
	 * Attribute that represents the timer configured to this service. 
	 */
	@NotNull(message="{form.valid.service.timer.notempty}")
	private Long timer;
	
	/**
	 * Attribute that represents the alarm configured to this service. 
	 */
//	@NotNull(message="{form.valid.service.timeout.notempty}")
//	private Long alarm;
	
	/**
	 * Attribute that represents the platform that could be configured to this service. 
	 */
	@NotNull(message="{form.valid.service.platform.notempty}")
	private Long platform;
	
	/**
	 * Attribute that represents the type of service selected. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.service.serviceType.notempty}")
	@Size(min=1, max=30, groups=ThenCheckIt.class)
	private String serviceType;
			
	
	public Long getIdService() {
		return idService;
	}

	
	public void setIdService(Long idService) {
		this.idService = idService;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public Long getTimeout() {
		return timeout;
	}

	
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	
	public String getNameWsdl() {
		return nameWsdl;
	}

	
	public void setNameWsdl(String nameWsdl) {
		this.nameWsdl = nameWsdl;
	}

	
	public Long getDegradedThreshold() {
		return degradedThreshold;
	}

	
	public void setDegradedThreshold(Long degradedThreshold) {
		this.degradedThreshold = degradedThreshold;
	}

	
	public Long getLostThreshold() {
		return lostThreshold;
	}

	
	public void setLostThreshold(Long lostThreshold) {
		this.lostThreshold = lostThreshold;
	}
	
	public Long getTimer() {
		return timer;
	}


	public void setTimer(Long timer) {
		this.timer = timer;
	}


	public Long getPlatform() {
		return platform;
	}


	public void setPlatform(Long platform) {
		this.platform = platform;
	}


	/**
	 * 
	 * @return
	 */
	public String getServiceType() {
		return serviceType;
	}

	
	/**
	 * 
	 * @param serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
