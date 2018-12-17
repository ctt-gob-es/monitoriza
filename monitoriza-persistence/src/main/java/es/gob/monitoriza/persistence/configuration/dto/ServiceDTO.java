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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.ServiceForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 05/12/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>
 * Class that represents the backing form for adding/editing a service.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.1, 05/12/2018.
 */
public class ServiceDTO {
	
	/**
	 * Constant string that represents the name of the mapped form in the template view. 
	 */
	public static final String FORM_OBJECT_VALUE = "serviceForm";
	
	/**
	 * Constant string that represents the identifier of the field "Endpoint" in the form of the template view. 
	 */
	public static final String FIELD_ENDPOINT = "nameWsdl";
	
	/**
	 * Constant string that represents the identifier of the field "file" in the form of the template view. 
	 */
	public static final String FIELD_FILE = "file";

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idService;

	/**
	 * Attribute that represents the name of the service.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.service.name.notempty}")
	@Size(min = NumberConstants.NUM1, max = NumberConstants.NUM100, groups = ThenCheckIt.class)
	private String name;

	/**
	 * Attribute that represents the time interval in milliseconds that must
	 * pass before the request for this service is cancelled.
	 */
	@NotNull(message = "{form.valid.service.timeout.notempty}")
	private Long timeout;

	/**
	 * Attribute that represents the name of the service in the WSDL endpoint
	 * path.
	 */
	private String nameWsdl;

	/**
	 * Attribute that represents the average time in milliseconds that a service
	 * request must take to be considered degraded.
	 */
	@NotNull(message = "{form.valid.service.degradedThreshold.notempty}")
	private Long degradedThreshold;

	/**
	 * Attribute that represents the average time in milliseconds that a service
	 * request must take to be considered lost.
	 */
	@NotNull(message = "{form.valid.service.lostThreshold.notempty}")
	private Long lostThreshold;

	/**
	 * Attribute that represents the timer configured to this service.
	 */
	@NotNull(message = "{form.valid.service.timer.notempty}")
	private Long timer;

	/**
	 * Attribute that represents the alarm configured to this service.
	 */
	 @NotNull(message="{form.valid.service.alarm.notempty}")
	 private Long alarm;

	/**
	 * Attribute that represents the platform that could be configured to this
	 * service.
	 */
	@NotNull(message = "{form.valid.service.platform.notempty}")
	private Long platform;
	
	/**
	 * Attribute that represents the uploaded file of the system certificate. 
	 */
	private MultipartFile file;
		
	/**
	 * Attribute that represents the identifier of the request file. 
	 */
	private Long idFile;
	
	/**
	 * Attribute that represents the file content as a base 64 String. 
	 */
	private String fileData;

	/**
	 * Attribute that represents the type of service selected.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.service.serviceType.notempty}")
	@Size(min = NumberConstants.NUM1, max = NumberConstants.NUM30, groups = ThenCheckIt.class)
	private String serviceType;
	
	/**
	 * Gets the value of the attribute {@link #idService}.
	 * @return the value of the attribute {@link #idService}.
	 */
	public Long getIdService() {
		return idService;
	}

	/**
	 * Sets the value of the attribute {@link #idService}.
	 * @param idServiceParam the value for the attribute {@link #idService} to set.
	 */
	public void setIdService(Long idServiceParam) {
		this.idService = idServiceParam;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param nameParam the value for the attribute {@link #name} to set.
	 */
	public void setName(String nameParam) {
		this.name = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #timeout}.
	 * @return the value of the attribute {@link #timeout}.
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * Sets the value of the attribute {@link #timeout}.
	 * @param timeoutParam the value for the attribute {@link #timeout} to set.
	 */
	public void setTimeout(Long timeoutParam) {
		this.timeout = timeoutParam;
	}

	/**
	 * Gets the value of the attribute {@link #nameWsdl}.
	 * @return the value of the attribute {@link #nameWsdl}.
	 */
	public String getNameWsdl() {
		return nameWsdl;
	}

	/**
	 * Sets the value of the attribute {@link #nameWsdl}.
	 * @param nameWsdlParam the value for the attribute {@link #nameWsdl} to set.
	 */
	public void setNameWsdl(String nameWsdlParam) {
		this.nameWsdl = nameWsdlParam;
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
	public void setDegradedThreshold(Long degradedThresholdParam) {
		this.degradedThreshold = degradedThresholdParam;
	}

	/**
	 * Gets the value of the attribute {@link #lostThreshold}.
	 * @return the value of the attribute {@link #lostThreshold}.
	 */
	public Long getLostThreshold() {
		return lostThreshold;
	}

	/**
	 * Sets the value of the attribute {@link #lostThreshold}.
	 * @param lostThresholdParam the value for the attribute {@link #lostThreshold} to set.
	 */
	public void setLostThreshold(Long lostThresholdParam) {
		this.lostThreshold = lostThresholdParam;
	}

	/**
	 * Gets the value of the attribute {@link #timer}.
	 * @return the value of the attribute {@link #timer}.
	 */
	public Long getTimer() {
		return timer;
	}

	/**
	 * Sets the value of the attribute {@link #timer}.
	 * @param timerParam the value for the attribute {@link #timer} to set.
	 */
	public void setTimer(Long timerParam) {
		this.timer = timerParam;
	}

	/**
	 * Gets the value of the attribute {@link #platform}.
	 * @return the value of the attribute {@link #platform}.
	 */
	public Long getPlatform() {
		return platform;
	}

	/**
	 * Sets the value of the attribute {@link #platform}.
	 * @param platformParam the value for the attribute {@link #platform} to set.
	 */
	public void setPlatform(Long platformParam) {
		this.platform = platformParam;
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
	 * Gets the value of the attribute {@link #alarm}.
	 * @return the value of the attribute {@link #alarm}.
	 */
	public Long getAlarm() {
		return alarm;
	}

	/**
	 * Sets the value of the attribute {@link #alarm}.
	 * @param alarmParam the value for the attribute {@link #alarm} to set.
	 */
	public void setAlarm(Long alarmParam) {
		this.alarm = alarmParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #file}.
	 * @return the value of the attribute {@link #file}.
	 */	
	public MultipartFile getFile() {
		return file;
	}
	
	/**
	 * Sets the value of the attribute {@link #file}.
	 * @param sslCertificate the value for the attribute {@link #file} to set.
	 */
	public void setFile(final MultipartFile sslCertificate) {
		this.file = sslCertificate;
	}

	/**
	 * Gets the value of the attribute {@link #idFile}.
	 * @return the value of the attribute {@link #idFile}.
	 */
	public Long getIdFile() {
		return idFile;
	}

	/**
	 * Sets the value of the attribute {@link #idFile}.
	 * @param idFileParam the value for the attribute {@link #idFile} to set.
	 */
	public void setIdFile(Long idFileParam) {
		this.idFile = idFileParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #fileData}.
	 * @return the value of the attribute {@link #fileData}.
	 */
	public String getFileData() {
		return fileData;
	}
	
	/**
	 * Sets the value of the attribute {@link #fileData}.
	 * @param fileDataParam the value for the attribute {@link #fileData} to set.
	 */
	public void setFileData(String fileDataParam) {
		this.fileData = fileDataParam;
	}
		
}
