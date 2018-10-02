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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>26/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 26/09/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;

/** 
 * <p>Class that maps the <i>REQUEST_SERVICE_FILE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 26/09/2018.
 */
@Entity
@Table(name = "REQUEST_SERVICE_FILE")
public class RequestServiceFile implements Serializable{
		
	/**
	 * Serial version id. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idRequestServiceFile;
		
	/**
	 * Attribute that represents the name of the file. 
	 */
	private String filename;
	
	/**
	 * Attribute that represents the content type of the file. 
	 */
	private String contentType;
	
	/**
	 * Attribute that represents the content of the file. 
	 */
	private byte[] filedata;

	/**
	 * Gets the value of the attribute {@link #idRequestServiceFile}.
	 * @return the value of the attribute {@link #idRequestServiceFile}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_REQUEST_SERVICE_FILE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_request_service_file")
	@GenericGenerator(
	                  name = "sq_request_service_file",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_REQUEST_SERVICE_FILE"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	
	@JsonView(DataTablesOutput.View.class)
	public Long getIdRequestServiceFile() {
		// CHECKSTYLE:ON 
		return idRequestServiceFile;
	}

	/**
	 * Sets the value of the attribute {@link #idRequestServiceFile}.
	 * @param idRequestServiceFile The value for the attribute {@link #idRequestServiceFile}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdRequestServiceFile(Long idRequestServiceFile) {
		// CHECKSTYLE:ON
		this.idRequestServiceFile = idRequestServiceFile;
	}

	/**
	 * Gets the value of the attribute {@link #filename}.
	 * @return the value of the attribute {@link #filename}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "FILENAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getFilename() {
		// CHECKSTYLE:ON
		return filename;
	}

	/**
	 * Sets the value of the attribute {@link #filename}.
	 * @param idRequestServiceFile The value for the attribute {@link #filename}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setFilename(String filename) {
		// CHECKSTYLE:ON
		this.filename = filename;
	}

	/**
	 * Gets the value of the attribute {@link #contentType}.
	 * @return the value of the attribute {@link #contentType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CONTENT_TYPE", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getContentType() {
		// CHECKSTYLE:ON
		return contentType;
	}

	/**
	 * Sets the value of the attribute {@link #contentType}.
	 * @param contentType The value for the attribute {@link #contentType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setContentType(String contentType) {
		// CHECKSTYLE:ON
		this.contentType = contentType;
	}

	/**
	 * Gets the value of the attribute {@link #filedata}.
	 * @return the value of the attribute {@link #filedata}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "FILEDATA", nullable = false, length = NumberConstants.NUM100, unique = true)
	@Lob
	@JsonView(DataTablesOutput.View.class)
	public byte[ ] getFiledata() {
		// CHECKSTYLE:ON
		return filedata;
	}

	/**
	 * Sets the value of the attribute {@link #filedata}.
	 * @param filedata The value for the attribute {@link #filedata}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setFiledata(byte[ ] filedata) {
		// CHECKSTYLE:ON
		this.filedata = filedata;
	}
	
	

}
