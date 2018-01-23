/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.AfirmaServicesNames.java.</p>
 * <b>Description:</b><p> Interface that contains the list of services offered by the @Firma platform.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/01/2018.
 */
package es.gob.monitoriza.dto;

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
	private String timeout;
	
	/**
	 * Attribute that represents . 
	 */
	private String wsdl;
	
	/**
	 * Constructor method for the class DTOService.java.
	 * @param serviceId 
	 */
	public DTOService(final String serviceId) {
		this.serviceId = serviceId;
	}

	
	/**
	 * Gets the service identifier
	 * @return String that represents the service identifier
	 */
	public String getServiceId() {
		return serviceId;
	}

	
	/**
	 * Sets the service identifier
	 * @param serviceId String with the service identifier to set.
	 */
	public void setServiceId(final String serviceId) {
		this.serviceId = serviceId;
	}

	
	/**
	 * Gets the timer identifier.
	 * @return String that represents the timer identifier.
	 */
	public String getTimerId() {
		return timerId;
	}

	
	/**
	 * Sets the timer identifier.
	 * @param timerId String with the timer identifier to set.
	 */
	public void setTimerId(final String timerId) {
		this.timerId = timerId;
	}

	
	/**
	 * Gets the timeout for this service in milliseconds.
	 * @return String that represents the timeout for this service in milliseconds.
	 */
	public String getTimeout() {
		return timeout;
	}

	
	/**
	 * Sets the timeout for this service in milliseconds.
	 * @param timeout String with the timeout to set in milliseconds.
	 */
	public void setTimeout(final String timeout) {
		this.timeout = timeout;
	}
			
	
	/**
	 * Gets the wsdl name for http invocation.
	 * @return String that represents the wsdl name
	 */
	public String getWsdl() {
		return wsdl;
	}

	
	/**
	 * Sets the wsdl name for this service.
	 * @param wsdl String with the wsdl name to set
	 */
	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
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
