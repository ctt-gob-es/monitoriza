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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AfirmaServicesNames.java.</p>
 * <b>Description:</b><p>Class for transferring service data from persistence.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 22 ene. 2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.Objects;

/** 
 * <p>Class for transferring service data from persistence.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22 ene. 2018.
 */
public class DTOService {
	
	/**
	 * Attribute that represents the service identifier. 
	 */
	private String serviceId;
	
	/**
	 * Attribute that represents the timer identifier for this service. 
	 */
	private String timerId;
	
	/**
	 * Attribute that represents the timeout (milliseconds) for this service. 
	 */
	private Long timeout;
	
	/**
	 * Attribute that represents the name of the service. 
	 */
	private String wsdl;
	
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
	 * Attribute that represents a flag that indicates if the service belongs to the @firma (true) platform or ts@ platform (false). 
	 */
	private boolean afirmaService;
	
	/**
	 * Constructor method for the class DTOService.java.
	 * @param serviceId 
	 */
	public DTOService(final String serviceId) {
		this.serviceId = serviceId;
	}
	
	/**
	 * Gets the value of the attribute {@link #serviceId} 
	 * @return the value of the attribute {@link #serviceId}
	 */
	public String getServiceId() {
		return serviceId;
	}

	
	/**
	 * Sets the value of the attribute {@link #serviceId} 
	 * @param serviceId the value for the attribute {@link #serviceId} to set.
	 */
	public void setServiceId(final String serviceId) {
		this.serviceId = serviceId;
	}

	
	/**
	 * Gets the value of the attribute {@link #timerId}.
	 * @return the value of the attribute {@link #timerId}.
	 */
	public String getTimerId() {
		return timerId;
	}

	
	/**
	 * Sets the value of the attribute {@link #timerId}.
	 * @param timerId the value for the attribute {@link #timerId} to set.
	 */
	public void setTimerId(final String timerId) {
		this.timerId = timerId;
	}

	
	/**
	 * Gets the value of the attribute {@link #timeout}.
	 * @return the value of the attribute {@link #timerId}.
	 */
	public Long getTimeout() {
		return timeout;
	}

	
	/**
	 * Sets the value of the attribute {@link #timeout}.
	 * @param timeout the value for the attribute {@link #timeout} to set.
	 */
	public void setTimeout(final Long timeout) {
		this.timeout = timeout;
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
	 * @param wsdl the value for the attribute {@link #wsdl} to set.
	 */
	public void setWsdl(final String wsdl) {
		this.wsdl = wsdl;
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
	 * @param degradedThreshold the value for the attribute {@link #degradedThreshold} to set.
	 */
	public void setDegradedThreshold(final Long degradedThreshold) {
		this.degradedThreshold = degradedThreshold;
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
	 * @param lostThreshold the value for the attribute {@link #lostThreshold} to set.
	 */
	public void setLostThreshold(final String lostThreshold) {
		this.lostThreshold = lostThreshold;
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
	 * @param directoryPath the value for the attribute {@link #directoryPath} to set.
	 */
	public void setDirectoryPath(final String directoryPath) {
		this.directoryPath = directoryPath;
	}
		
	/**
	 * Gets the value of the attribute {@link #afirmaService}
	 * @return the value of the attribute {@link #afirmaService}.
	 */
	public boolean isAfirmaService() {
		return afirmaService;
	}
	
	/**
	 * Sets the value of the attribute {@link #afirmaService}.
	 * @param afirmaService the value for the attribute {@link #afirmaService} to set.
	 */
	public void setAfirmaService(boolean afirmaService) {
		this.afirmaService = afirmaService;
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
        if (!(o instanceof DTOService)) {
            return false;
        }
        DTOService service = (DTOService) o;
        
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
