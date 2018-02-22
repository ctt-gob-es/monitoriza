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
 * <b>File:</b><p>es.gob.monitoriza.alarm.types.Alarm.java.</p>
 * <b>Description:</b><p> Class that represents the type Alarm.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24/01/2018.
 */
package es.gob.monitoriza.alarm.types;

import java.time.LocalDateTime;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class that represents the type Alarm.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 14/02/2018.
 */
public class Alarm {

	/**
	 * Attribute that represents the name of the service associated to the alarm.
	 */
	private String serviceName;
	
	/**
	 * Attribute that represents the status of the service associated to the alarm.
	 */
	private String serviceStatus;
	
	/**
	 * Attribute that represents the average time that has taken to the service to complete, provoking the alarm. 
	 */
	private Long avgTime;
	
	/**
	 * Attribute that represents the creation date of the alarm.
	 */
	private LocalDateTime dateOfCreation;
	
	
	/**
	 * Constructor method for the class Alarm.java. 
	 */
	public Alarm(){	
		setDateOfCreation(LocalDateTime.now());
	}
	
	/**
	 * Constructor method for the class Alarm.java.
	 * @param serviceNameParam Name of the service associated to the alarm.
	 * @param serviceStatusParam Status of the service associated to the alarm.
	 */
	public Alarm(String serviceNameParam, String serviceStatusParam, Long tiempoMedio) {
		setServiceName(serviceNameParam);
		setServiceStatus(serviceStatusParam);
		setAvgTime(tiempoMedio);
		setDateOfCreation(LocalDateTime.now());
	}

	/**
	 * Gets the value of the attribute {@link #serviceName}.
	 * @return the value of the attribute {@link #serviceName}.
	 */
	public String getServiceName() {
		return serviceName;
	}
	
	/**
	 * Sets the value of the attribute {@link #serviceName}.
	 * @param serviceName The value for the attribute {@link #serviceName}.
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
	 * Gets the value of the attribute {@link #serviceStatus}.
	 * @return the value of the attribute {@link #serviceStatus}.
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}
	
	/**
	 * Sets the value of the attribute {@link #serviceStatus}.
	 * @param serviceStatus The value for the attribute {@link #serviceStatus}.
	 */
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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
	 * Gets the value of the attribute {@link #avgTime}.
	 * @return the value of the attribute {@link #avgTime}.
	 */
	public Long getAvgTime() {
		return avgTime;
	}

	/**
	 * Sets the value of the attribute {@link #avgTime}.
	 * @param dateOfCreation The value for the attribute {@link #avgTime}.
	 */
	public void setAvgTime(Long tiempoMedio) {
		this.avgTime = tiempoMedio;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return serviceName + GeneralConstants.EN_DASH_WITH_SPACES + serviceStatus;
	}
	
}
