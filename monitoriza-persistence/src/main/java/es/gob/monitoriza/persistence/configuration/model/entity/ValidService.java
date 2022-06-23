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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ValidService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 23/06/2022.
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>VALID_SERVICE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 23/06/2022.
 */
@Entity
@Table(name = "VALID_SERVICE")
public class ValidService implements Serializable {
		
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
	private Long idValidService;
	
	/**
	 * Attribute that represents the application of the valid service. 
	 */
	private String application;
	
	/**
	 * Attribute that represents the host string for connections to the valid service. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port string for connections to the valid service. 
	 */
	private String port;
	
	/**
	 * Attribute that indicates whether the access to the valid service is through secured connection (https).
	 */
	private Boolean isSecure;
	
	/**
	 * Attribute that indicates if the process validation job is enable/disable.
	 */
	private Boolean isEnableValidationJob;
	
	/**
	 * Attribute that represents the cron expression to process validation job. 
	 */
	private String cronExpression;
	
	/**
	 * Attribute that represents the user string for connections to the valid service. 
	 */
	private String user;
	
	/**
	 * Attribute that represents the pass string for connections to the valid service. 
	 */
	private String pass;
	
	/**
	 * Attribute that represents the type of authentication of the valid service. 
	 */
	private AuthenticationType authenticationType;
	
	/**
	 * Attribute that represents the certificate for the valid service. 
	 */
	private SystemCertificate validServiceCertificate;
	
	/**
	 * Gets the value of the attribute {@link #idPlatform}.
	 * @return the value of the attribute {@link #idPlatform}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_VALID_SERVICE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_valid_service")
	@GenericGenerator(
	                  name = "sq_valid_service",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_VALID_SERVICE"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	public Long getIdValidService() {
		// CHECKSTYLE:ON
		return idValidService;
	}
	
	/**
	 * Sets the value of the attribute {@link #idValidService}.
	 * @param idValidService The value for the attribute {@link #idValidService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdValidService(Long idValidService) {
		// CHECKSTYLE:ON
		this.idValidService = idValidService;
	}
	
	/**
	 * Gets the value of the attribute {@link #application}.
	 * @return the value of the attribute {@link #application}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "APPLICATION", nullable = false, length = NumberConstants.NUM100, unique = true)
	public String getApplication() {
		return application;
	}

	
	/**
	 * Sets the value of the attribute {@link #application}.
	 * @param application The value for the attribute {@link #application}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setApplication(String application) {
		this.application = application;
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
		return host;
	}

	
	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param isBlockedParam The value for the attribute {@link #host}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PORT", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
	public String getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param port The value for the attribute {@link #port}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPort(String port) {
		this.port = port;
	}
			
	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_SECURE", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecure The value for the attribute {@link #isSecure}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}
	
	/**
	 * Gets the value of the attribute {@link #isEnableValidationJob}.
	 * @return the value of the attribute {@link #isEnableValidationJob}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_ENABLE_VALIDATION_JOB", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getIsEnableValidationJob() {
		return isEnableValidationJob;
	}

	/**
	 * Sets the value of the attribute {@link #isEnableValidationJob}.
	 * @param isSisEnableValidationJobecure The value for the attribute {@link #isEnableValidationJob}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsEnableValidationJob(Boolean isEnableValidationJob) {
		this.isEnableValidationJob = isEnableValidationJob;
	}
	
	/**
	 * Gets the value of the attribute {@link #cronExpression}.
	 * @return the value of the attribute {@link #cronExpression}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CRON_EXPRESSION", nullable = true, length = NumberConstants.NUM100)
	public String getCronExpression() {
		return cronExpression;
	}
	
	/**
	 * Sets the value of the attribute {@link #cronExpression}.
	 * @param cronExpression The value for the attribute {@link #cronExpression}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	/**
	 * Gets the value of the attribute {@link #user}.
	 * @return the value of the attribute {@link #user}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "USERNAME", nullable = true, length = NumberConstants.NUM100)
	public String getUser() {
		return user;
	}
	
	/**
	 * Sets the value of the attribute {@link #user}.
	 * @param user The value for the attribute {@link #user}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Gets the value of the attribute {@link #pass}.
	 * @return the value of the attribute {@link #pass}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PASS", nullable = true, length = NumberConstants.NUM255)
	public String getPass() {
		return pass;
	}
	
	/**
	 * Sets the value of the attribute {@link #pass}.
	 * @param pass The value for the attribute {@link #pass}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * Gets the value of the attribute {@link #authenticationType}.
	 * @return the value of the attribute {@link #authenticationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_AUTHENTICATION_TYPE", nullable = false)
	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	/**
	 * Sets the value of the attribute {@link #authenticationType}.
	 * @param authenticationType The value for the attribute {@link #authenticationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}
	
	/**
	 * Gets the value of the attribute {@link #validServiceCertificate}.
	 * @return the value of the attribute {@link #validServiceCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	//@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ID_AUTH_CERTIFICATE", nullable = true)
	@JsonIgnore
	public SystemCertificate getValidServiceCertificate() {
		// CHECKSTYLE:On
		return validServiceCertificate;
	}

	/**
	 * Sets the value of the attribute {@link #validServiceCertificate}.
	 * @param validServiceCertificate The value for the attribute {@link #validServiceCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setValidServiceCertificate(SystemCertificate validServiceCertificate) {
		this.validServiceCertificate = validServiceCertificate;
	}
		
}
