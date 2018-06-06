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
 * <b>File:</b><p>es.gob.monitoriza.form.SslForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 17 may. 2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class that represents the backing form for adding/editing a system certificate.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 17 may. 2018.
 */
public class CertificateForm {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idSystemCertificate;
	
	/**
	 * Attribute that represents the value of the input alias of the system certificate in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.keystore.alias.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
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
	 * Attribute that represents the uploaded file of the system certificate. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.keystore.file.notnull}")
	private MultipartFile file;
	
	/**
	 * Attribute that represents byte array of the system certificate. 
	 */
	private byte[] certBytes;
	
	/**
	 * Gets the value of the attribute {@link #idSystemCertificate}.
	 * @return the value of the attribute {@link #idSystemCertificate}.
	 */	
	public Long getIdSystemCertificate() {
		return idSystemCertificate;
	}
	
	/**
	 * Sets the value of the attribute {@link #idSystemCertificate}.
	 * @param idSystemCertificate the value for the attribute {@link #idSystemCertificate} to set.
	 */
	public void setIdSystemCertificate(Long idSystemCertificate) {
		this.idSystemCertificate = idSystemCertificate;
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
	 * @param alias the value for the attribute {@link #alias} to set.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
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
	 * @param issuer the value for the attribute {@link #issuer} to set.
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
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
	 * @param subject the value for the attribute {@link #subject} to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @param file the value for the attribute {@link #file} to set.
	 */
	public void setFile(MultipartFile sslCertificate) {
		this.file = sslCertificate;
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
	 * @param certBytes the value for the attribute {@link #certBytes} to set.
	 */
	public void setCertBytes(byte[ ] certBytes) {
		this.certBytes = certBytes;
	}
		
}
