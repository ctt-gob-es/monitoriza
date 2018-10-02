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
 * <b>File:</b><p>es.gob.monitoriza.form.ValidServiceForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 29 ago. 2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 29 ago. 2018.
 */
public class ValidServiceForm {
	
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idValidService;
	
	/**
	 * Attribute that represents the value of the input application of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.application.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String application = "";

	/**
	 * Attribute that represents the value of the input host of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.host.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String host = "";
	
	/**
	 * Attribute that represents the value of the input port of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.port.notempty}")
    @Size(min=1, max=5, groups=ThenCheckIt.class)
    private String port = "";
	
	/**
	 * Attribute that represents the value of the input isSecure of the user in the form. 
	 */
    private Boolean isSecure = false;
	
	/**
	 * Attribute that represents the value of the input user of the user in the form. 
	 */
	private String user = "";
	
	/**
	 * Attribute that represents the value of the input pass of the user in the form. 
	 */
	private String pass = "";
	
	/**
	 * Attribute that represents the value of the input authenticationType of the user in the form. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.validservice.accessType.notnull}")
	private Long authenticationType;
	
	/**
	 * Attribute that indicates if the process validation job is enable/disable.
	 */
	private Boolean isEnableValidationJob = false;
	
	/**
	 * Attribute that represents the cron expression to process validation job. 
	 */
	@Size(min=0, max=100, groups=ThenCheckIt.class)
	private String cronExpression;
	
	/**
	 * Attribute that represents the value of the input validServiceCertificate of the user in the form. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.validservice.certificate.notnull}")
	private Long validServiceCertificate;
	
	/**
	 * Gets the value of the attribute {@link #idValidService}.
	 * @return the value of the attribute {@link #idValidService}.
	 */	
	public Long getIdValidService() {
		return idValidService;
	}

	/**
	 * Sets the value of the attribute {@link #idValidService}.
	 * @return the value of the attribute {@link #idValidService}.
	 */
	public void setIdValidService(Long idValidService) {
		this.idValidService = idValidService;
	}
	
	/**
	 * Gets the value of the attribute {@link #application}.
	 * @return the value of the attribute {@link #application}.
	 */
	public String getApplication() {
		return application;
	}
	
	/**
	 * Sets the value of the attribute {@link #application}.
	 * @param application the value for the attribute {@link #application} to set.
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	
	/**
	 * Gets the value of the attribute {@link #host}.
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param login the value for the attribute {@link #host} to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param port the value for the attribute {@link #port} to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecure the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}
	
	/**
	 * Gets the value of the attribute {@link #isEnableValidationJob}.
	 * @return the value of the attribute {@link #isEnableValidationJob}.
	 */
	public Boolean getIsEnableValidationJob() {
		return isEnableValidationJob;
	}

	/**
	 * Sets the value of the attribute {@link #isEnableValidationJob}.
	 * @param isEnableValidationJob the value for the attribute {@link #isEnableValidationJob} to set.
	 */
	public void setIsEnableValidationJob(Boolean isEnableValidationJob) {
		this.isEnableValidationJob = isEnableValidationJob;
	}
	
	/**
	 * Gets the value of the attribute {@link #cronExpression}.
	 * @return the value of the attribute {@link #cronExpression}.
	 */	
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * Sets the value of the attribute {@link #cronExpression}.
	 * @param cronExpression the value for the attribute {@link #cronExpression} to set.
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	/**
	 * Gets the value of the attribute {@link #user}.
	 * @return the value of the attribute {@link #user}.
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Sets the value of the attribute {@link #user}.
	 * @param user the value for the attribute {@link #user} to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Gets the value of the attribute {@link #pass}.
	 * @return the value of the attribute {@link #pass}.
	 */
	public String getPass() {
		return pass;
	}
	
	/**
	 * Sets the value of the attribute {@link #pass}.
	 * @param pass the value for the attribute {@link #pass} to set.
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	/**
	 * Gets the value of the attribute {@link #authenticationType}.
	 * @return the value of the attribute {@link #authenticationType}.
	 */
	public Long getAuthenticationType() {
		return authenticationType;
	}
	
	/**
	 * Sets the value of the attribute {@link #authenticationType}.
	 * @param authenticationType the value for the attribute {@link #authenticationType} to set.
	 */
	public void setAuthenticationType(Long authenticationType) {
		this.authenticationType = authenticationType;
	}

	/**
	 * Gets the value of the attribute {@link #validServiceCertificate}.
	 * @return the value of the attribute {@link #validServiceCertificate}.
	 */
	public Long getValidServiceCertificate() {
		return validServiceCertificate;
	}

	/**
	 * Sets the value of the attribute {@link #validServiceCertificate}.
	 * @param validServiceCertificate the value for the attribute {@link #validServiceCertificate} to set.
	 */
	public void setValidServiceCertificate(Long validServiceCertificate) {
		this.validServiceCertificate = validServiceCertificate;
	}
}
