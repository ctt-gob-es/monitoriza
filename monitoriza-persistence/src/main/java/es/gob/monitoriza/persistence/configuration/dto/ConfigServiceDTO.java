/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the terms
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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO.java.</p>
 * <b>Description:</b><p>Class for transferring service data from persistence.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 14/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.List;
import java.util.Objects;

/** 
 * <p>Data transfer object class that encapsulates the information of the services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 14/01/2019.
 */
public class ConfigServiceDTO {
	
	/**
	 * Attribute that represents the service identifier. 
	 */
	private Long serviceId;
	
	/**
	 * Attribute that represents the service name. 
	 */
	private String serviceName;
	
	/**
	 * Attribute that represents the timer identifier for this service. 
	 */
	private String timerName;
	
	/**
	 * Attribute that represents the timeout (milliseconds) for this service. 
	 */
	private Long timeout;
	
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String wsdl;
	
	/**
	 * Attribute that represents the context path for the OCSP services in the platform associated to the service. 
	 */
	private String ocspContext;
	
	/**
	 * Attribute that represents the context path for the RFC3161 services in the platform associated to the service. 
	 */
	private String rfc3161Context;
	
	/**
	 * Attribute that indicate if the TS@ platform associated uses authentication for the RFC3161 service. 
	 */
	private Boolean useRfc3161Auth;
	
	/**
	 * Attribute that represents the certificate selected for RFC3161 service authentication in TS@ platform. 
	 */
	private String rfc3161Cert;
	
	/**
	 * Attribute that represents the password for the RFC3161 keystore. 
	 */
	private String rfc3161Password;
	
	/**
	 * Attribute that represents the threshold response time in milliseconds above which the service is considered degraded. 
	 */
	private Long degradedThreshold;
	
	/**
	 * Attribute that represents the threshold percentage of lost requests (degraded or timed out)
	 * above which is necessary to call the next confirmation group. 
	 */
	private String lostThreshold;
	
	/**
	 * Attribute that represents the path in which the requests are stored. 
	 */
	private String directoryPath;
		
	/**
	 * Attribute that represents the url for SOAP service invocation. 
	 */
	private String soapUrl;
	
	/**
	 * Attribute that represents the base url of the platform. 
	 */
	private String baseUrl;
	
	/**
	 * Attribute that represents the type of the service. 
	 */
	private String serviceType;
	
	/**
	 * Attribute that represents the platform of this service. 
	 */
	private String platform;
	
	/**
	 * Attribute that represents the time in milliseconds that the alarm associated to this service will
	 * be blocked.
	 */
	private Long blockTimeAlarm;
	
	/**
	 * Attribute that represents the list of mail addresses to which send alarms when the service is degraded. 
	 */
	private List<String> listMailDegraded;
	
	/**
	 * Attribute that represents the list of mail addresses to which send alarms when the service is down. 
	 */
	private List<String> listMailDown;
	
	/**
	 * Attribute that represents the AlarmMonitoriza identifier. 
	 */
	private Long idAlarm;
	
	
	/**
	 * Constructor method for the class DTOService.java.
	 * @param serviceNameParam Name of the service.
	 */
	public ConfigServiceDTO(final String serviceNameParam) {
		this.serviceName = serviceNameParam;
	}
		
	
	/**
	 * Constructor method for the class ServiceDTO.java.
	 * @param serviceId
	 * @param serviceName
	 * @param timerId
	 * @param timeout
	 * @param wsdl
	 * @param degradedThreshold
	 * @param lostThreshold
	 * @param directoryPath
	 * @param afirmaService 
	 */
	public ConfigServiceDTO(Long serviceId, String serviceName, String timerName, Long timeout, String wsdl, Long degradedThreshold, String lostThreshold, String directoryPath, String platform, String serviceType, Long idPlatform) {
		super();
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.timerName = timerName;
		this.timeout = timeout;
		this.wsdl = wsdl;
		this.degradedThreshold = degradedThreshold;
		this.lostThreshold = lostThreshold;
		this.directoryPath = directoryPath;
		this.serviceType = serviceType;
		this.platform = platform;
	}

	/**
	 * Gets the value of the attribute {@link #serviceId}.
	 * @return the value of the attribute {@link #serviceId}
	 */
	public Long getServiceId() {
		return serviceId;
	}
	
	/**
	 * Sets the value of the attribute {@link #serviceId}. 
	 * @param serviceIdParam the value for the attribute {@link #serviceId} to set.
	 */
	public void setServiceId(final Long serviceIdParam) {
		this.serviceId = serviceIdParam;
	}

	/**
	 * Gets the value of the attribute {@link #serviceName}. 
	 * @return the value of the attribute {@link #serviceName}
	 */	
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the value of the attribute {@link #serviceName}.
	 * @param serviceNameParam the value for the attribute {@link #serviceName} to set.
	 */
	public void setServiceName(String serviceNameParam) {
		this.serviceName = serviceNameParam;
	}

	/**
	 * Gets the value of the attribute {@link #timerName}.
	 * @return the value of the attribute {@link #timerName}.
	 */
	public String getTimerName() {
		return timerName;
	}

	
	/**
	 * Sets the value of the attribute {@link #timerName}.
	 * @param timerNameParam the value for the attribute {@link #timerName} to set.
	 */
	public void setTimerName(final String timerNameParam) {
		this.timerName = timerNameParam;
	}

	
	/**
	 * Gets the value of the attribute {@link #timeout}.
	 * @return the value of the attribute {@link #timerName}.
	 */
	public Long getTimeout() {
		return timeout;
	}

	
	/**
	 * Sets the value of the attribute {@link #timeout}.
	 * @param timeoutParam the value for the attribute {@link #timeout} to set.
	 */
	public void setTimeout(final Long timeoutParam) {
		this.timeout = timeoutParam;
	}
			
	
	/**
	 * Gets the value of the attribute {@link #wsdl}.
	 * @return the value of the attribute {@link #wsdl}.
	 */
	public String getWsdl() {
		return wsdl;
	}
	
	/**
	 * Sets the value of the attribute {@link #wsdl}.
	 * @param wsdlParam the value for the attribute {@link #wsdl} to set.
	 */
	public void setWsdl(final String wsdlParam) {
		this.wsdl = wsdlParam;
	}
			
	/**
	 * Gets the value of the attribute {@link #ocspContext}.
	 * @return the value of the attribute {@link #ocspContext}.
	 */
	public String getOcspContext() {
		return ocspContext;
	}

	/**
	 * Sets the value of the attribute {@link #ocspContext}.
	 * @param ocspContextParam the value for the attribute {@link #ocspContext} to set.
	 */
	public void setOcspContext(String ocspContextParam) {
		this.ocspContext = ocspContextParam;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Context}.
	 * @return the value of the attribute {@link #rfc3161Context}.
	 */
	public String getRfc3161Context() {
		return rfc3161Context;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Context}.
	 * @param rfc3161ContextParam the value for the attribute {@link #rfc3161Context} to set.
	 */
	public void setRfc3161Context(String rfc3161ContextParam) {
		this.rfc3161Context = rfc3161ContextParam;
	}
				
	/**
	 * Gets the value of the attribute {@link #useRfc3161Auth}.
	 * @return the value of the attribute {@link #useRfc3161Auth}.
	 */
	public Boolean getUseRfc3161Auth() {
		return useRfc3161Auth;
	}

	/**
	 * Sets the value of the attribute {@link #useRfc3161Auth}.
	 * @param useRfc3161AuthParam the value for the attribute {@link #useRfc3161Auth} to set.
	 */
	public void setUseRfc3161Auth(Boolean useRfc3161AuthParam) {
		this.useRfc3161Auth = useRfc3161AuthParam;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Cert}.
	 * @return the value of the attribute {@link #rfc3161Cert}.
	 */
	public String getRfc3161Cert() {
		return rfc3161Cert;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Cert}.
	 * @param rfc3161CertParam the value for the attribute {@link #rfc3161Cert} to set.
	 */
	public void setRfc3161Cert(String rfc3161CertParam) {
		this.rfc3161Cert = rfc3161CertParam;
	}
			
	/**
	 * Gets the value of the attribute {@link #rfc3161Password}.
	 * @return the value of the attribute {@link #rfc3161Password}.
	 */
	public String getRfc3161Password() {
		return rfc3161Password;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Password}.
	 * @param rfc3161PasswordParam the value for the attribute {@link #rfc3161Password} to set.
	 */
	public void setRfc3161Password(String rfc3161PasswordParam) {
		this.rfc3161Password = rfc3161PasswordParam;
	}

	/**
	 * Gets the value of the attribute {@link #degradedThreshold}.
	 * @return the value of the attribute {@link #degradedThreshold}.
	 */
	public Long getDegradedThreshold() {
		return degradedThreshold;
	}
	
	/**
	 * Sets the value of the attribute {@link #degradedThreshold}.
	 * @param degradedThresholdParam the value for the attribute {@link #degradedThreshold} to set.
	 */
	public void setDegradedThreshold(final Long degradedThresholdParam) {
		this.degradedThreshold = degradedThresholdParam;
	}
			
	/**
	 * Gets the value of the attribute {@link #lostThreshold}.
	 * @return the value of the attribute {@link #lostThreshold}.
	 */
	public String getLostThreshold() {
		return lostThreshold;
	}
	
	/**
	 * Sets the value of the attribute {@link #lostThreshold}.
	 * @param lostThresholdParam the value for the attribute {@link #lostThreshold} to set.
	 */
	public void setLostThreshold(final String lostThresholdParam) {
		this.lostThreshold = lostThresholdParam;
	}
	
	
	/**
	 * Gets the value of the attribute {@link #directoryPath}.
	 * @return the value of the attribute {@link #directoryPath}.
	 */
	public String getDirectoryPath() {
		return directoryPath;
	}

	/**
	 * Sets the value of the attribute {@link #directoryPath}.
	 * @param directoryPathParam the value for the attribute {@link #directoryPath} to set.
	 */
	public void setDirectoryPath(final String directoryPathParam) {
		this.directoryPath = directoryPathParam;
	}
		
	/**
	 * Gets the value of the attribute {@link #soapUrl}.
	 * @return the value of the attribute {@link #soapUrl}.
	 */
	public String getSoapUrl() {
		return soapUrl;
	}

	/**
	 * Sets the value of the attribute {@link #soapUrl}.
	 * @param soapUrlParam the value for the attribute {@link #soapUrl} to set.
	 */
	public void setSoapUrl(String soapUrlParam) {
		this.soapUrl = soapUrlParam;
	}
				
	
	/**
	 * Gets the value of the attribute {@link #baseUrl}.
	 * @return the value of the attribute {@link #baseUrl}.
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Sets the value of the attribute {@link #baseUrl}.
	 * @param baseUrlParam the value for the attribute {@link #baseUrl} to set.
	 */
	public void setBaseUrl(String baseUrlParam) {
		this.baseUrl = baseUrlParam;
	}

	/**
	 * Gets the value of the attribute {@link #serviceType}.
	 * @return the value of the attribute {@link #serviceType}.
	 */	
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * Sets the value of the attribute {@link #serviceType}.
	 * @param serviceTypeParam the value for the attribute {@link #serviceType} to set.
	 */
	public void setServiceType(String serviceTypeParam) {
		this.serviceType = serviceTypeParam;
	}
		
	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */	
	public String getPlatform() {
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param platformParam the value for the attribute {@link #platform} to set.
	 */
	public void setPlatform(String platformParam) {
		this.platform = platformParam;
	}
	
	
	/**
	 * Gets the value of the attribute {@link #blockTimeAlarm}.
	 * @return the value of the attribute {@link #blockTimeAlarm}.
	 */	
	public Long getBlockTimeAlarm() {
		return blockTimeAlarm;
	}

	/**
	 * Sets the value of the attribute {@link #blockTimeAlarm}.
	 * @param blockTimeAlarmParam the value for the attribute {@link #blockTimeAlarm} to set.
	 */
	public void setBlockTimeAlarm(Long blockTimeAlarmParam) {
		this.blockTimeAlarm = blockTimeAlarmParam;
	}

	/**
	 * Gets the value of the attribute {@link #listMailDegraded}.
	 * @return the value of the attribute {@link #listMailDegraded}.
	 */	
	public List<String> getListMailDegraded() {
		return listMailDegraded;
	}

	/**
	 * Sets the value of the attribute {@link #listMailDegraded}.
	 * @param listMailDegradedParam the value for the attribute {@link #listMailDegraded} to set.
	 */
	public void setListMailDegraded(List<String> listMailDegradedParam) {
		this.listMailDegraded = listMailDegradedParam;
	}

	/**
	 * Gets the value of the attribute {@link #listMailDown}.
	 * @return the value of the attribute {@link #listMailDown}.
	 */	
	public List<String> getListMailDown() {
		return listMailDown;
	}

	/**
	 * Sets the value of the attribute {@link #listMailDown}.
	 * @param listMailDownParam the value for the attribute {@link #listMailDown} to set.
	 */	
	public void setListMailDown(List<String> listMailDownParam) {
		this.listMailDown = listMailDownParam;
	}
	
	
	/**
	 * Gets the value of the attribute {@link #idAlarm}.
	 * @return the value of the attribute {@link #idAlarm}.
	 */	
	public Long getIdAlarm() {
		return idAlarm;
	}
	
	/**
	 * Sets the value of the attribute {@link #idAlarm}.
	 * @param idAlarmParam the value for the attribute {@link #idAlarm} to set.
	 */	
	public void setIdAlarm(Long idAlarmParam) {
		this.idAlarm = idAlarmParam;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(final Object o) {

        if (o == this) {
        	return true;
        }
        if (!(o instanceof ConfigServiceDTO)) {
            return false;
        }
        ConfigServiceDTO service = (ConfigServiceDTO) o;
        
        return serviceId.equals(service.getServiceId());
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }
		

}
