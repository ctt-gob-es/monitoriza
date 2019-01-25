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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.ValidServiceDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;

/** 
 * <p>Class that represents the data transfer object and backing form for the validation service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 25/01/2019.
 */
public class ValidServiceDTO {
	
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idValidService;
	
	/**
	 * Attribute that represents the value of the input application of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.application.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String application = "";

	/**
	 * Attribute that represents the value of the input host of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.host.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String host = "";
	
	/**
	 * Attribute that represents the value of the input port of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.validservice.port.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM5, groups=ThenCheckIt.class)
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
	@Size(min=NumberConstants.NUM0, max=NumberConstants.NUM100, groups=ThenCheckIt.class)
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
	 * @param idValidServiceParam value of the attribute {@link #idValidService} to set.
	 */
	public void setIdValidService(Long idValidServiceParam) {
		this.idValidService = idValidServiceParam;
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
	 * @param applicationParam the value for the attribute {@link #application} to set.
	 */
	public void setApplication(String applicationParam) {
		this.application = applicationParam;
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
	 * @param hostParam the value for the attribute {@link #host} to set.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
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
	 * @param portParam the value for the attribute {@link #port} to set.
	 */
	public void setPort(String portParam) {
		this.port = portParam;
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
	 * @param isSecureParam the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(Boolean isSecureParam) {
		this.isSecure = isSecureParam;
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
	 * @param isEnableValidationJobParam the value for the attribute {@link #isEnableValidationJob} to set.
	 */
	public void setIsEnableValidationJob(Boolean isEnableValidationJobParam) {
		this.isEnableValidationJob = isEnableValidationJobParam;
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
	 * @param cronExpressionParam the value for the attribute {@link #cronExpression} to set.
	 */
	public void setCronExpression(String cronExpressionParam) {
		this.cronExpression = cronExpressionParam;
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
	 * @param userParam the value for the attribute {@link #user} to set.
	 */
	public void setUser(String userParam) {
		this.user = userParam;
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
	 * @param passParam the value for the attribute {@link #pass} to set.
	 */
	public void setPass(String passParam) {
		this.pass = passParam;
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
	 * @param authenticationTypeParam the value for the attribute {@link #authenticationType} to set.
	 */
	public void setAuthenticationType(Long authenticationTypeParam) {
		this.authenticationType = authenticationTypeParam;
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
	 * @param validServiceCertificateParam the value for the attribute {@link #validServiceCertificate} to set.
	 */
	public void setValidServiceCertificate(Long validServiceCertificateParam) {
		this.validServiceCertificate = validServiceCertificateParam;
	}
}
