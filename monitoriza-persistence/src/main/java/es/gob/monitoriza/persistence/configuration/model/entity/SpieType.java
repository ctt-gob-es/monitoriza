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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.NodeAfirma.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>09/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 15/02/2019.
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>SPIE_TYPE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 15/02/2019.
 */
@Entity
@Table(name = "SPIE_TYPE")
public class SpieType implements Serializable {
	
	/**
	 * Attribute that represents the class serial version identifier. 
	 */
	private static final long serialVersionUID = -4299283176302847810L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_CONN_HSM_AFIRMA = 1L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_MODE_EMERGENCY_AFIRMA = 2L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_CONN_TSA = 3L;
			
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_VAL_METHODS = 4L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_RESPONSE_TIMES = 5L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_CONN_HSM_TSA = 6L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_MODE_EMERGENCY_TSA = 7L;
	
	/**
	 * Attribute that represents . 
	 */
	public static final Long ID_CONN_AFIRMA = 8L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idSpieType;
	
	/**
	 * Attribute that represents the name of the SPIE type. 
	 */
	private String tokenName;
	
	/**
	 * Attribute that represents the context string for invoking the SPIE. 
	 */
	private String context;
		
	/**
	 * Attribute that represents the type of platform. 
	 */
	private CPlatformType platformType;
	
	/**
	 * Attribute that represents the sempahore error level for the SPIE type. 
	 */
	private Integer semaphoreErrorLevel;
	
	/**
	 * Attribute that represents the HTML resolver class name for parsing the SPIE result. 
	 */
	private String resolverClass;
		
	/**
	 * Gets the value of the attribute {@link #idSpieType}.
	 * @return the value of the attribute {@link #idSpieType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_SPIE_TYPE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_spie_type")
	@GenericGenerator(
	                  name = "sq_spie_type",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SPIE_TYPE"),
	                          @Parameter(name = "initial_value", value = "9"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdSpieType() {
		// CHECKSTYLE:ON
		return idSpieType;
	}

	/**
	 * Sets the value of the attribute {@link #idSpieType}.
	 * @param idSpieType The value for the attribute {@link #idSpieType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdSpieType(Long idSpieType) {
		// CHECKSTYLE:ON
		this.idSpieType = idSpieType;
	}

	/**
	 * Gets the value of the attribute {@link #tokenName}.
	 * @return the value of the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "TOKENNAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getTokenName() {
		// CHECKSTYLE:ON
		return tokenName;
	}

	/**
	 * Sets the value of the attribute {@link #tokenName}.
	 * @param tokenName The value for the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTokenName(String tokenName) {
		// CHECKSTYLE:ON
		this.tokenName = tokenName;
	}

	/**
	 * Gets the value of the attribute {@link #context}.
	 * @return the value of the attribute {@link #context}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CONTEXT", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getContext() {
		// CHECKSTYLE:ON
		return context;
	}

	/**
	 * Sets the value of the attribute {@link #context}.
	 * @param context The value for the attribute {@link #context}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setContext(String context) {
		// CHECKSTYLE:ON
		this.context = context;
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
	 * Gets the value of the attribute {@link #semaphoreErrorLevel}.
	 * @return the value of the attribute {@link #semaphoreErrorLevel}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SEMAPHORE_LEVEL", nullable = false, length = NumberConstants.NUM1, unique = false)
	@JsonView(DataTablesOutput.View.class)	
	public Integer getSemaphoreErrorLevel() {
		// CHECKSTYLE:ON
		return semaphoreErrorLevel;
	}

	/**
	 * Sets the value of the attribute {@link #semaphoreErrorLevel}.
	 * @param semaphoreErrorLevelParam The value for the attribute {@link #semaphoreErrorLevel}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSemaphoreErrorLevel(Integer semaphoreErrorLevelParam) {
		// CHECKSTYLE:ON
		this.semaphoreErrorLevel = semaphoreErrorLevelParam;
	}

	/**
	 * Gets the value of the attribute {@link #resolverClass}.
	 * @return the value of the attribute {@link #resolverClass}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "RESOLVER_CLASS", nullable = true, length = NumberConstants.NUM100, unique = false)
	@JsonView(DataTablesOutput.View.class)	
	public final String getResolverClass() {
		// CHECKSTYLE:ON
		return resolverClass;
	}

	/**
	 * Sets the value of the attribute {@link #resolverClass}.
	 * @param resolverClassParam The value for the attribute {@link #resolverClass}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public final void setResolverClass(String resolverClassParam) {
		// CHECKSTYLE:ON
		this.resolverClass = resolverClassParam;
	}	

}
