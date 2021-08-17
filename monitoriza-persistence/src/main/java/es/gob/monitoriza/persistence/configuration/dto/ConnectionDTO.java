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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.DTOConnection.java.</p>
 * <b>Description:</b><p>Class for transferring platforms connection data from persistence.</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 17/08/2021
 */
package es.gob.monitoriza.persistence.configuration.dto;


/** 
 * <p>Data transfer object that encapsulates the information of a connection configuration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 17/08/2021
 */
public class ConnectionDTO {
	
	/**
	 * Attribute that represents the protocol conection mode HTTP or HTTPS. 
	 */
	private Boolean secureMode;
		
	/**
	 * Attribute that represents the host for connecting to the platform. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port for connecting the platform. 
	 */
	private String port;
	
	/**
	 * Attribute that represents the port for connecting the platform in secure mode. 
	 */
	private String securePort;
	
	/**
	 * Attribute that represents the port for the RFC3161 service. 
	 */
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
	 * Constructor method for the class ConnectionDTO.java. 
	 */
	public ConnectionDTO() {
		
	}		
	
	/**
	 * Constructor method for the class ConnectionDTO.java.
	 * @param secureMode
	 * @param host
	 * @param port
	 * @param rfc3161Port
	 * @param serviceContext
	 * @param ocspContext
	 * @param rfc3161Context 
	 */
	public ConnectionDTO(Boolean secureMode, String host, String port, String rfc3161Port, String serviceContext, String ocspContext, String rfc3161Context) {
		super();
		this.secureMode = secureMode;
		this.host = host;
		this.port = port;
		this.rfc3161Port = rfc3161Port;
		this.serviceContext = serviceContext;
		this.ocspContext = ocspContext;
		this.rfc3161Context = rfc3161Context;
	}


	/**
	 * Gets the value of the attribute {@link #secureMode}.
	 * @return the value of the attribute {@link #secureMode}
	 */
	public Boolean getSecureMode() {
		return secureMode;
	}

	
	/**
	 * Sets the value of the attribute {@link #secureMode} 
	 * @param secureModeParam the value for the attribute {@link #secureMode} to set.
	 */
	public void setSecureMode(Boolean secureModeParam) {
		this.secureMode = secureModeParam;
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
	 * @param host the value for the attribute {@link #host} to set.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
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
	 * @param portParam the value for the attribute {@link #port} to set.
	 */
	public void setPort(String portParam) {
		this.port = portParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #securePort} 
	 * @return the value of the attribute {@link #securePort}
	 */
	public String getSecurePort() {
		return securePort;
	}
	
	/**
	 * Sets the value of the attribute {@link #securePort} 
	 * @param securePortParam the value for the attribute {@link #securePort} to set.
	 */
	public void setSecurePort(String securePortParam) {
		this.securePort = securePortParam;
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
	 * @param serviceContextParam the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContextParam) {
		this.serviceContext = serviceContextParam;
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
	 * @param ocspContextParam the value for the attribute {@link #ocspContext} to set.
	 */
	public void setOcspContext(String ocspContextParam) {
		this.ocspContext = ocspContextParam;
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
	 * @param rfc3161Context the value for the attribute {@link #rfc3161Context} to set.
	 */
	public void setRfc3161Context(String rfc3161ContextParam) {
		this.rfc3161Context = rfc3161ContextParam;
	}


	/**
	 * Gets the value of the attribute {@link #rfc3161Port} 
	 * @return the value of the attribute {@link #rfc3161Port}
	 */
	public String getRfc3161Port() {
		return rfc3161Port;
	}


	/**
	 * Sets the value of the attribute {@link #rfc3161Port} 
	 * @param rfc3161Port the value for the attribute {@link #rfc3161Port} to set.
	 */
	public void setRfc3161Port(String rfc3161PortParam) {
		this.rfc3161Port = rfc3161PortParam;
	}
	
	
	
}
