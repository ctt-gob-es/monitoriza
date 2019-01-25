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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AuthenticationType.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>C_AUTHENTICATION_TYPE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 25/01/2019.
 */
@Entity
@Table(name = "C_AUTHENTICATION_TYPE")
public class AuthenticationType implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -7101917130553871676L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAuthenticationType;

	/**
	 * Attribute that represents the name of the token with the description stored in properties file for internationalization.
	 */
	private String tokenName;

	/**
	 * Gets the value of the attribute {@link #idAuthenticationType}.
	 * @return the value of the attribute {@link #idAuthenticationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_AUTHENTICATION_TYPE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_c_authentication_type")
	@GenericGenerator(
	                  name = "sq_c_authentication_type",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_C_AUTHENTICATION_TYPE"),
	                          @Parameter(name = "initial_value", value = "4"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	public Long getIdAuthenticationType() {
		// CHECKSTYLE:ON
		return idAuthenticationType;
	}

	/**
	 * Sets the value of the attribute {@link #idAuthenticationType}.
	 * @param idAuthenticationTypeParam The value for the attribute {@link #idAuthenticationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdAuthenticationType(Long idAuthenticationTypeParam) {
		// CHECKSTYLE:ON
		this.idAuthenticationType = idAuthenticationTypeParam;
	}

	/**
	 * Gets the value of the attribute {@link #tokenName}.
	 * @return the value of the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "TOKEN_NAME", nullable = false, length = NumberConstants.NUM30)
	public String getTokenName() {
		// CHECKSTYLE:ON
		return tokenName;
	}

	/**
	 * Sets the value of the attribute {@link #tokenName}.
	 * @param tokenNameParam The value for the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTokenName(String tokenNameParam) {
		// CHECKSTYLE:ON
		this.tokenName = tokenNameParam;
	}

}
