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
 * @version 1.3, 05/03/2019.
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
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/** 
 * <p>Class that maps the <i>NODE_AFIMRA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 05/03/2019.
 */
@Entity
@Table(name = "NODE_MONITORIZA")
public class NodeMonitoriza implements Serializable {
	
	/**
	 * Constant attribute that represents the string <i>"yes_no"</i>.
	 */
	private static final String CONS_YES_NO = "yes_no";

	/**
	 * Attribute that represents the class serial version identifier. 
	 */
	private static final long serialVersionUID = 3719684495489223373L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNode;
	
	/**
	 * Attribute that represents the name of the node. 
	 */
	private String name;
	
	/**
	 * Attribute that represents the host string for connections to the node. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port string for connections to the node. 
	 */
	private String port;
	
	/**
	 * Attribute that represents the type of platform. 
	 */
	private CPlatformType nodeType;
		
	/**
	 * Attribute that indicates if the SPIE for emergency database connection will be checked.
	 */
	private Boolean checkEmergencyDB;
	
	/**
	 * Attribute that indicates if the SPIE for TS@ connection will be checked.
	 */
	private Boolean checkTsa;
	
	/**
	 * Attribute that indicates if the SPIE for @Firma connection will be checked.
	 */
	private Boolean checkAfirma;
	
	/**
	 * Attribute that indicates if the SPIE for HSM connection will be checked.
	 */
	private Boolean checkHsm;
	
	/**
	 * Attribute that indicates if the SPIE for service response times will be checked.
	 */
	private Boolean checkServices;
	
	/**
	 * Attribute that indicates if the SPIE for validation methods connection will be checked.
	 */
	private Boolean checkValidMethod;
	
	/**
	 * Attribute that indicates whether the access to the node is through secured connection (https).
	 */
	private Boolean isSecure;

	/**
	 * Gets the value of the attribute {@link #idNode}.
	 * @return the value of the attribute {@link #idNode}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_NODE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_node_monitoriza")
	@GenericGenerator(
	                  name = "sq_node_monitoriza",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_NODE_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "2"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdNode() {
		// CHECKSTYLE:ON
		return idNode;
	}

	/**
	 * Sets the value of the attribute {@link #idNode}.
	 * @param idNode The value for the attribute {@link #idNode}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdNode(Long idNode) {
		// CHECKSTYLE:ON
		this.idNode = idNode;
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
	 * @param name The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String name) {
		// CHECKSTYLE:ON
		this.name = name;
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
	@Column(name = "PORT", nullable = false, length = NumberConstants.NUM10, unique = true)
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
	 * Gets the value of the attribute {@link #nodeType}.
	 * @return the value of the attribute {@link #nodeType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NODE_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public CPlatformType getNodeType() {
		// CHECKSTYLE:ON
		return nodeType;
	}

	/**
	 * Sets the value of the attribute {@link #nodeType}.
	 * @param nodeType The value for the attribute {@link #nodeType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNodeType(CPlatformType nodeType) {
		// CHECKSTYLE:ON
		this.nodeType = nodeType;
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
	 * Gets the value of the attribute {@link #checkEmergencyDB}.
	 * @return the value of the attribute {@link #checkEmergencyDB}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_EMERGENCYDB", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckEmergencyDB() {
		// CHECKSTYLE:ON
		return checkEmergencyDB;
	}

	/**
	 * Sets the value of the attribute {@link #checkEmergencyDB}.
	 * @param checkEmergencyDB The value for the attribute {@link #checkEmergencyDB}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckEmergencyDB(Boolean checkEmergencyDB) {
		// CHECKSTYLE:ON
		this.checkEmergencyDB = checkEmergencyDB;
	}

	/**
	 * Gets the value of the attribute {@link #checkTsa}.
	 * @return the value of the attribute {@link #checkTsa}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_TSA", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckTsa() {
		// CHECKSTYLE:ON
		return checkTsa;
	}

	/**
	 * Sets the value of the attribute {@link #checkTsa}.
	 * @param checkTsa The value for the attribute {@link #checkTsa}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckTsa(Boolean checkTsa) {
		// CHECKSTYLE:ON
		this.checkTsa = checkTsa;
	}

	/**
	 * Gets the value of the attribute {@link #checkAfirma}.
	 * @return the value of the attribute {@link #checkAfirma}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_AFIRMA", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckAfirma() {
		// CHECKSTYLE:ON
		return checkAfirma;
	}

	/**
	 * Sets the value of the attribute {@link #checkAfirma}.
	 * @param checkAfirma The value for the attribute {@link #checkAfirma}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckAfirma(Boolean checkAfirma) {
		// CHECKSTYLE:ON
		this.checkAfirma = checkAfirma;
	}

	/**
	 * Gets the value of the attribute {@link #checkHsm}.
	 * @return the value of the attribute {@link #checkHsm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_HSM", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckHsm() {
		// CHECKSTYLE:ON
		return checkHsm;
	}

	/**
	 * Sets the value of the attribute {@link #checkHsm}.
	 * @param checkHsm The value for the attribute {@link #checkHsm}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckHsm(Boolean checkHsm) {
		// CHECKSTYLE:ON
		this.checkHsm = checkHsm;
	}

	/**
	 * Gets the value of the attribute {@link #checkServices}.
	 * @return the value of the attribute {@link #checkServices}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_SERVICES", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckServices() {
		// CHECKSTYLE:ON
		return checkServices;
	}

	/**
	 * Sets the value of the attribute {@link #checkService}.
	 * @param checkService The value for the attribute {@link #checkService}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckServices(Boolean checkService) {
		// CHECKSTYLE:ON
		this.checkServices = checkService;
	}

	/**
	 * Gets the value of the attribute {@link #checkValidMethod}.
	 * @return the value of the attribute {@link #checkValidMethod}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "CHECK_VALIDATION_METHOD", nullable = true, precision = 1)
	@Type(type = CONS_YES_NO)
	@JsonView(DataTablesOutput.View.class)
	public Boolean getCheckValidMethod() {
		// CHECKSTYLE:ON
		return checkValidMethod;
	}

	/**
	 * Sets the value of the attribute {@link #checkValidMethod}.
	 * @param checkValidMethod The value for the attribute {@link #checkValidMethod}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCheckValidMethod(Boolean checkValidMethod) {
		// CHECKSTYLE:ON
		this.checkValidMethod = checkValidMethod;
	}
	

}
