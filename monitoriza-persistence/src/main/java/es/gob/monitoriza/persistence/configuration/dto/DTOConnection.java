/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.dto.DTOConnection.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 ene. 2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;


/** 
 * <p>Class for transferring platforms connection data from persistence.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 ene. 2018.
 */
public class DTOConnection {
	
	/**
	 * Attribute that represents the protocol conection mode HTTP or HTTPS. 
	 */
	private String secureMode;
	
	/**
	 * Attribute that represents the host for connecting to the platform. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port for connecting the platform. 
	 */
	private String port;
	
	private String rfc3161Port;
	
	/**
	 * Attribute that represents the context for the web services. 
	 */
	private String serviceContext;
	
	/**
	 * Attribute that represents the context for the OCSP service. 
	 */
	private String ocspContext;
	
	/**
	 * Attribute that represents the context for the RFC3161 service. 
	 */
	private String rfc3161Context;

	
	/**
	 * Gets the value of the attribute {@link #secureMode} 
	 * @return the value of the attribute {@link #secureMode}
	 */
	public String getSecureMode() {
		return secureMode;
	}

	
	/**
	 * Sets the value of the attribute {@link #secureMode} 
	 * @param serviceId the value for the attribute {@link #secureMode} to set.
	 */
	public void setSecureMode(String secureMode) {
		this.secureMode = secureMode;
	}

	
	/**
	 * Gets the value of the attribute {@link #host} 
	 * @return the value of the attribute {@link #host}
	 */
	public String getHost() {
		return host;
	}

	
	/**
	 * Sets the value of the attribute {@link #host} 
	 * @param serviceId the value for the attribute {@link #host} to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}

	
	/**
	 * Gets the value of the attribute {@link #port} 
	 * @return the value of the attribute {@link #port}
	 */
	public String getPort() {
		return port;
	}

	
	/**
	 * Sets the value of the attribute {@link #port} 
	 * @param serviceId the value for the attribute {@link #port} to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	
	/**
	 * Gets the value of the attribute {@link #serviceContext} 
	 * @return the value of the attribute {@link #serviceContext}
	 */
	public String getServiceContext() {
		return serviceContext;
	}

	
	/**
	 * Sets the value of the attribute {@link #serviceContext} 
	 * @param serviceId the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

	
	/**
	 * Gets the value of the attribute {@link #ocspContext} 
	 * @return the value of the attribute {@link #ocspContext}
	 */
	public String getOcspContext() {
		return ocspContext;
	}

	
	/**
	 * Sets the value of the attribute {@link #ocspContext} 
	 * @param serviceId the value for the attribute {@link #ocspContext} to set.
	 */
	public void setOcspContext(String ocspContext) {
		this.ocspContext = ocspContext;
	}


	
	/**
	 * Gets the value of the attribute {@link #rfc3161Context} 
	 * @return the value of the attribute {@link #rfc3161Context}
	 */
	public String getRfc3161Context() {
		return rfc3161Context;
	}


	
	/**
	 * Sets the value of the attribute {@link #rfc3161Context} 
	 * @param serviceId the value for the attribute {@link #rfc3161Context} to set.
	 */
	public void setRfc3161Context(String rfc3161Context) {
		this.rfc3161Context = rfc3161Context;
	}


	
	public String getRfc3161Port() {
		return rfc3161Port;
	}


	
	public void setRfc3161Port(String rfc3161Port) {
		this.rfc3161Port = rfc3161Port;
	}
	
	
	
}
