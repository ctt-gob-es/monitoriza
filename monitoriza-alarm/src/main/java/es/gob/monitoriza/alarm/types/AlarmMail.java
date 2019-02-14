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
 * @version 1.4, 30/01/2019.
 */
package es.gob.monitoriza.alarm.types;

import java.time.LocalDateTime;
import java.util.List;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class that represents the type Alarm.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.4, 30/01/2019.
 */
public class AlarmMail extends AlarmType {

	/**
	 * Attribute that represents the name of the service associated to the alarm.
	 */
	private String serviceName;
	
	/**
	 * Attribute that represents the status of the service associated to the alarm.
	 */
	private String serviceStatus;
		
	/**
	 * Attribute that represents . 
	 */
	private Long umbralDegradado;
	
	/**
	 * Attribute that represents the name of the SPIE node.
	 */
	private String nodeName;
	
	/**
	 * Attribute that represents the URL of the SPIE node.
	 */
	private String nodeUrl;
		
	/**
	 * Constructor method for the class Alarm.java.
	 * @param serviceNameParam Name of the service associated to the alarm.
	 * @param serviceStatusParam Status of the service associated to the alarm.
	 */
	public AlarmMail(final String serviceNameParam, final String serviceStatusParam, final List<String> addressesParam, final Long blockedTimeParam, final String nodeNameParam, final String nodeUrlParam, final String subjectParam, final String bodyParam) {
		super(LocalDateTime.now(), addressesParam, blockedTimeParam, subjectParam, bodyParam);
		this.serviceName = serviceNameParam;
		this.serviceStatus = serviceStatusParam;
		this.nodeName = nodeNameParam;
		this.nodeUrl = nodeUrlParam;
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
	 * Gets the value of the attribute {@link #umbralDegradado}.
	 * @return the value of the attribute {@link #umbralDegradado}.
	 */
	public Long getUmbralDegradado() {
		return umbralDegradado;
	}
	
	/**
	 * Sets the value of the attribute {@link #umbralDegradado}.
	 * @param umbralDegradado The value for the attribute {@link #umbralDegradado}.
	 */
	public void setUmbralDegradado(Long umbralDegradado) {
		this.umbralDegradado = umbralDegradado;
	}
		
	/**
	 * Gets the value of the attribute {@link #nodeName}.
	 * @return the value of the attribute {@link #nodeName}.
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Sets the value of the attribute {@link #nodeName}.
	 * @param nodeNameParam The value for the attribute {@link #nodeName}.
	 */
	public void setNodeName(String nodeNameParam) {
		this.nodeName = nodeNameParam;
	}

	/**
	 * Gets the value of the attribute {@link #nodeName}.
	 * @return the value of the attribute {@link #nodeName}.
	 */
	public String getNodeUrl() {
		return nodeUrl;
	}

	/**
	 * Sets the value of the attribute {@link #nodeUrl}.
	 * @param nodeUrlParam The value for the attribute {@link #nodeUrl}.
	 */
	public void setNodeUrl(String nodeUrlParam) {
		this.nodeUrl = nodeUrlParam;
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
