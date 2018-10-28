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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.CertificateDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.NumberConstants;

/** 
 * <p>Class that represents the backing form for adding/editing a system certificate.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
public class CertificateDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idSystemCertificate;
	
	/**
	 * Attribute that represents the value of the input alias of the system certificate in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.keystore.alias.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
	private String alias;
	
	/**
	 * Attribute that represents the issuer of the system certificate. 
	 */
	private String issuer;
	
	/**
	 * Attribute that represents the subject of the system certificate. 
	 */
	private String subject;
	
	/**
	 * Attribute that represents the serial number of the system certificate. 
	 */
	private BigInteger serialNumber;
	
	/**
	 * Attribute that represents the uploaded file of the system certificate. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.keystore.file.notnull}")
	private MultipartFile file;
	
	/**
	 * Attribute that represents byte array of the system certificate. 
	 */
	private byte[] certBytes;
	
	/**
	 * Attribute that represents the user of the system certificate. 
	 */
	private Long idUserMonitoriza;
	
	/**
	 * Attribute that represents the status of the system certificate. 
	 */
	private Long idStatusCertificate;
	
	/**
	 * Gets the value of the attribute {@link #idSystemCertificate}.
	 * @return the value of the attribute {@link #idSystemCertificate}.
	 */	
	public Long getIdSystemCertificate() {
		return idSystemCertificate;
	}
	
	/**
	 * Sets the value of the attribute {@link #idSystemCertificate}.
	 * @param idSystemCertificateParam the value for the attribute {@link #idSystemCertificate} to set.
	 */
	public void setIdSystemCertificate(final Long idSystemCertificateParam) {
		this.idSystemCertificate = idSystemCertificateParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #alias}.
	 * @return the value of the attribute {@link #alias}.
	 */	
	public String getAlias() {
		return alias;
	}
	
	/**
	 * Sets the value of the attribute {@link #alias}.
	 * @param aliasParam the value for the attribute {@link #alias} to set.
	 */
	public void setAlias(final String aliasParam) {
		this.alias = aliasParam;
	}
		
	
	/**
	 * Gets the value of the attribute {@link #issuer}.
	 * @return the value of the attribute {@link #issuer}.
	 */	
	public String getIssuer() {
		return issuer;
	}

	
	/**
	 * Sets the value of the attribute {@link #issuer}.
	 * @param issuerParam the value for the attribute {@link #issuer} to set.
	 */
	public void setIssuer(final String issuerParam) {
		this.issuer = issuerParam;
	}

	
	/**
	 * Gets the value of the attribute {@link #subject}.
	 * @return the value of the attribute {@link #subject}.
	 */	
	public String getSubject() {
		return subject;
	}

	
	/**
	 * Sets the value of the attribute {@link #subject}.
	 * @param subjectParam the value for the attribute {@link #subject} to set.
	 */
	public void setSubject(final String subjectParam) {
		this.subject = subjectParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #serialNumber}.
	 * @return the value of the attribute {@link #serialNumber}.
	 */		
	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the value of the attribute {@link #serialNumber}.
	 * @param serialNumberParam the value for the attribute {@link #serialNumber} to set.
	 */
	public void setSerialNumber(BigInteger serialNumberParam) {
		this.serialNumber = serialNumberParam;
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
	 * @param sslCertificateParam the value for the attribute {@link #file} to set.
	 */
	public void setFile(final MultipartFile sslCertificateParam) {
		this.file = sslCertificateParam;
	}

	
	/**
	 * Gets the value of the attribute {@link #certBytes}.
	 * @return the value of the attribute {@link #certBytes}.
	 */	
	public byte[ ] getCertBytes() {
		return certBytes;
	}

	
	/**
	 * Sets the value of the attribute {@link #certBytes}.
	 * @param certBytesP the value for the attribute {@link #certBytes} to set.
	 */
	public void setCertBytes(final byte[ ] certBytesP) {
		this.certBytes = certBytesP;
	}
	
	/**
	 * Gets the value of the attribute {@link #idUserMonitoriza}.
	 * @return the value of the attribute {@link #idUserMonitoriza}.
	 */	
	public Long getIdUserMonitoriza() {
		return idUserMonitoriza;
	}
	
	/**
	 * Sets the value of the attribute {@link #idUserMonitoriza}.
	 * @param idUserMonitorizaParam the value for the attribute {@link #idUserMonitoriza} to set.
	 */
	public void setIdUserMonitoriza(final Long idUserMonitorizaParam) {
		this.idUserMonitoriza = idUserMonitorizaParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #idStatusCertificate}.
	 * @return the value of the attribute {@link #idStatusCertificate}.
	 */	
	public Long getIdStatusCertificate() {
		return idStatusCertificate;
	}
	
	/**
	 * Sets the value of the attribute {@link #idStatusCertificate}.
	 * @param idStatusCertificateParam the value for the attribute {@link #idStatusCertificate} to set.
	 */
	public void setIdStatusCertificate(final Long idStatusCertificateParam) {
		this.idStatusCertificate = idStatusCertificateParam;
	}
		
}
