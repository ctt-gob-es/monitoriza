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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.CNotificationPriority.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;


/** 
 * <p>Class that maps the <i>C_NOTIFICATION_PRIORITY</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@Entity
@Table(name = "C_NOTIFICATION_PRIORITY")
public class CNotificationPriority implements Serializable {

	/**
	 * Class serial version. 
	 */
	private static final long serialVersionUID = -4907848942333537125L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNotificationPriority;
	
	/**
	 * Attribute that represents the name of the timer. 
	 */
	private String tokenName;
	
	/**
	 * Attribute that represents the name of the timer. 
	 */
	private Integer level;
	
	/**
	 * Gets the value of the attribute {@link #idNotificationPriority}.
	 * @return the value of the attribute {@link #idNotificationPriority}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_NOTIFICATION_PRIORITY", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@JsonView(DataTablesOutput.View.class)	
	public Long getIdNotificationPriority() {
		// CHECKSTYLE:ON
		return idNotificationPriority;
	}

	/**
	 * Sets the value of the attribute {@link #idNotificationPriority}.
	 * @param idNotificationPriorityParam The value for the attribute {@link #idNotificationPriority}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdNotificationPriority(Long idNotificationPriorityParam) {
		// CHECKSTYLE:ON
		this.idNotificationPriority = idNotificationPriorityParam;
	}

	/**
	 * Gets the value of the attribute {@link #tokenName}.
	 * @return the value of the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "TOKEN_NAME", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getTokenName() {
		// CHECKSTYLE:ON
		return tokenName;
	}

	/**
	 * Sets the value of the attribute {@link #tokenName}.
	 * @param nameParam The value for the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTokenName(String nameParam) {
		// CHECKSTYLE:ON
		this.tokenName = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #level}.
	 * @return the value of the attribute {@link #level}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "LEVEL", nullable = false, length = NumberConstants.NUM1, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public Integer getLevel() {
		// CHECKSTYLE:ON
		return level;
	}

	/**
	 * Sets the value of the attribute {@link #level}.
	 * @param levelParam The value for the attribute {@link #level}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLevel(Integer levelParam) {
		// CHECKSTYLE:ON
		this.level = levelParam;
	}
	
	

}
