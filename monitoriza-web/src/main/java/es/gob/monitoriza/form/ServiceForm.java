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

import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;

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
	private String name;
	
	/**
	 * Attribute that represents the time interval in milliseconds that must pass
	 * before the request for this service is cancelled. 
	 */
	private Long timeout;
	
	/**
	 * Attribute that represents the name of the service in the WSDL endpoint path. 
	 */
	private String nameWsdl;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered degraded. 
	 */
	private Long degradedThreshold;
	
	/**
	 * Attribute that represents the average time in milliseconds that a service 
	 * request must take to be considered lost. 
	 */
	private Long lostThreshold;
	
	/**
	 * Attribute that represents the timer configured to this service. 
	 */
	private TimerMonitoriza timer;
	
	/**
	 * Attribute that represents the alarm configured to this service. 
	 */
	private AlarmMonitoriza alarm;
	
	/**
	 * Attribute that represents the platform that could be configured to this service. 
	 */
	private PlatformMonitoriza platform;
	
	/**
	 * Attribute that represents the type of service selected. 
	 */
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

	
	public TimerMonitoriza getTimer() {
		return timer;
	}

	
	public void setTimer(TimerMonitoriza timer) {
		this.timer = timer;
	}

	
	public AlarmMonitoriza getAlarm() {
		return alarm;
	}

	
	public void setAlarm(AlarmMonitoriza alarm) {
		this.alarm = alarm;
	}

	
	public PlatformMonitoriza getPlatform() {
		return platform;
	}

	
	public void setPlatform(PlatformMonitoriza platform) {
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
