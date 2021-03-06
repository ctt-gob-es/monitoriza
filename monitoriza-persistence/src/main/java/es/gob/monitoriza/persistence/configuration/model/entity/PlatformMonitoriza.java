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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>PLATFORM_MONITORIZA</i> database table as a Plain Old Java Object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 05/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>PLATFORM_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.7, 05/03/2019.
 */
@Entity
@Table(name = "PLATFORM_MONITORIZA")
public class PlatformMonitoriza implements Serializable {
	
	/**
	 * Attribute that represents the identifier for the @Firma platform type. 
	 */
	public static final Long ID_PLATFORM_TYPE_AFIRMA = 1L;
	
	/**
	 * Attribute that represents the identifier for the @Firma platform type.
	 */
	public static final Long ID_PLATFORM_TYPE_TSA = 2L;
		
	/**
	 * Constant attribute that represents the string <i>"yes_no"</i>.
	 */
	private static final String CONS_YES_NO = "yes_no";
	
	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = -4037701570881756420L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idPlatform;
	
	/**
	 * Attribute that represents the name of the ts@ platform. 
	 */
	private String name;
	
	/**
	 * Attribute that represents the host string for connections to the platform. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port string for connections to the platform. 
	 */
	private String port;
	
	/**
	 * Attribute that indicates whether the access to the platform is through secured connection (https).
	 */
	private Boolean isSecure;
	
	/**
	 * Attribute that represents the context path for SOAP services. 
	 */
	private String serviceContext;
	
	/**
	 * Attribute that represents the context path for the OCSP service of @firma. 
	 */
	private String ocspContext;
	
	/**
	 * Attribute that represents the context path for the RFC3161 service of ts@. 
	 */
	private String rfc3161Context;
	
	/**
	 * Attribute that represents the port path for the RFC3161 service of ts@. 
	 */
	private String rfc3161Port;
	
	/**
	 * Attribute that represents the type of platform. 
	 */
	private CPlatformType platformType;
	
	/**
	 * Attribute that indicates if the connection with the RFC3161 uses client authentication. 
	 */
	private Boolean useRfc3161Auth;
	
	/**
	 * Attribute that represents the certificate for the RFC3161 authentication. 
	 */
	private SystemCertificate rfc3161Certificate;
	
	/**
	 * Gets the value of the attribute {@link #idPlatform}.
	 * @return the value of the attribute {@link #idPlatform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_PLATFORM", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_platform")
	@GenericGenerator(
	                  name = "sq_platform",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_PLATFORM_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "2"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdPlatform() {
		// CHECKSTYLE:ON
		return idPlatform;
	}
	
	/**
	 * Sets the value of the attribute {@link #idPlatform}.
	 * @param idPlatform The value for the attribute {@link #idPlatform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdPlatform(Long idPlatform) {
		// CHECKSTYLE:ON
		this.idPlatform = idPlatform;
	}


	/**
	 * Gets the value of the attribute {@link #ocspContext}.
	 * @return the value of the attribute {@link #ocspContext}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "OCSP_CONTEXT", nullable = true, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getOcspContext() {
		// CHECKSTYLE:ON
		return ocspContext;
	}

	
	/**
	 * Sets the value of the attribute {@link #ocspContext}.
	 * @param ocspContext The value for the attribute {@link #ocspContext}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setOcspContext(String ocspContext) {
		// CHECKSTYLE:ON
		this.ocspContext = ocspContext;
	}
	
	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		// CHECKSTYLE:ON
		return name;
	}

	
	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param nameParam The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String nameParam) {
		// CHECKSTYLE:ON
		this.name = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * @return the value of the attribute {@link #host}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "HOST", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getHost() {
		// CHECKSTYLE:ON
		return host;
	}

	
	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param host The value for the attribute {@link #host}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setHost(String host) {
		// CHECKSTYLE:ON
		this.host = host;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PORT", nullable = true, length = NumberConstants.NUM10)
	@JsonView(DataTablesOutput.View.class)
	public String getPort() {
		// CHECKSTYLE:ON
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param port The value for the attribute {@link #port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPort(String port) {
		// CHECKSTYLE:ON
		this.port = port;
	}
			
	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_SECURE", nullable = false, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getIsSecure() {
		// CHECKSTYLE:ON
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecure The value for the attribute {@link #isSecure}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsSecure(Boolean isSecure) {
		// CHECKSTYLE:ON
		this.isSecure = isSecure;
	}

	/**
	 * Gets the value of the attribute {@link #serviceContext}.
	 * @return the value of the attribute {@link #serviceContext}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SERVICE_CONTEXT", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getServiceContext() {
		// CHECKSTYLE:ON
		return serviceContext;
	}
	
	/**
	 * Sets the value of the attribute {@link #serviceContext}.
	 * @param serviceContext The value for the attribute {@link #serviceContext}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setServiceContext(String serviceContext) {
		// CHECKSTYLE:ON
		this.serviceContext = serviceContext;
	}
	
	/**
	 * Gets the value of the attribute {@link #rfc3161Context}.
	 * @return the value of the attribute {@link #rfc3161Context}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "RFC3161_CONTEXT", nullable = true, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getRfc3161Context() {
		// CHECKSTYLE:ON
		return rfc3161Context;
	}

	
	/**
	 * Sets the value of the attribute {@link #rfc3161Context}.
	 * @param rfc3161ContextParam The value for the attribute {@link #rfc3161Context}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setRfc3161Context(String rfc3161ContextParam) {
		// CHECKSTYLE:ON
		this.rfc3161Context = rfc3161ContextParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #rfc3161Port}.
	 * @return the value of the attribute {@link #rfc3161Port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "RFC3161_PORT", nullable = true, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)	
	public String getRfc3161Port() {
		// CHECKSTYLE:ON
		return rfc3161Port;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Port}.
	 * @param rfc3161PortParam The value for the attribute {@link #rfc3161Port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setRfc3161Port(String rfc3161PortParam) {
		// CHECKSTYLE:ON
		this.rfc3161Port = rfc3161PortParam;
	}

	/**
	 * Gets the value of the attribute {@link #platformType}.
	 * @return the value of the attribute {@link #platformType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PLATFORM_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public CPlatformType getPlatformType() {
		// CHECKSTYLE:ON
		return platformType;
	}

	/**
	 * Sets the value of the attribute {@link #platformType}.
	 * @param platformType The value for the attribute {@link #platformType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPlatformType(CPlatformType platformType) {
		// CHECKSTYLE:ON
		this.platformType = platformType;
	}
	
	
	/**
	 * Gets the value of the attribute {@link #useRfc3161Auth}.
	 * @return the value of the attribute {@link #useRfc3161Auth}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "RFC3161_USE_AUTH", nullable = false, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getUseRfc3161Auth() {
		// CHECKSTYLE:ON
		return useRfc3161Auth;
	}

	/**
	 * Sets the value of the attribute {@link #useRfc3161Auth}.
	 * @param useRfc3161Auth The value for the attribute {@link #useRfc3161Auth}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setUseRfc3161Auth(Boolean useRfc3161Auth) {
		// CHECKSTYLE:ON
		this.useRfc3161Auth = useRfc3161Auth;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Certificate}.
	 * @return the value of the attribute {@link #rfc3161Certificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "RFC3161_AUTH_CERTIFICATE", nullable = true)
	@JsonView(DataTablesOutput.View.class)	
	public SystemCertificate getRfc3161Certificate() {
		// CHECKSTYLE:On
		return rfc3161Certificate;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Certificate}.
	 * @param rfc3161Certificate The value for the attribute {@link #rfc3161Certificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setRfc3161Certificate(SystemCertificate rfc3161Certificate) {
		this.rfc3161Certificate = rfc3161Certificate;
	}
	
	
	

}
