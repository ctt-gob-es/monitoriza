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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * <p>Class that maps the <i>SYSTEM_NOTIFICATION</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@Entity
@Table(name = "SYSTEM_NOTIFICATION")
public class SystemNotification implements Serializable {

	/**
	 * Attribute that represents the serial number id. 
	 */
	private static final long serialVersionUID = 4407740596661309150L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idSystemNotification;
		
	/**
	 * Attribute that represents the description of the notification. 
	 */
	private String description;
	
	/**
	 * Attribute that indicates if the NOTIFICATION has been reviewed by the admin (true) or not (false).
	 */
	private Boolean isOk;
	
	/**
	 * Attribute that represents the priority level of the notification. 
	 */
	private CNotificationPriority notificationPriority;
	
	/**
	 * Attribute that represents the type of notification. 
	 */
	private CNotificationType notificationType;
	
	/**
	 * Attribute that represents the origin of notification. 
	 */
	private CNotificationOrigin notificationOrigin;
	
	/**
	 * Attribute that represents the creation date of the notification. 
	 */
	private LocalDateTime creationDate;
	
	/**
	 * Attribute that represents the expiration date of the notification.  
	 */
	private LocalDateTime expirationDate;
	
	/**
	 * Gets the value of the attribute {@link #idSystemNotification}.
	 * @return the value of the attribute {@link #idSystemNotification}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_SYSTEM_NOTIFICATION", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_system_notification")
	@GenericGenerator(
	                  name = "sq_system_notification",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SYSTEM_NOTIFICATION"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdSystemNotification() {
		// CHECKSTYLE:ON
		return idSystemNotification;
	}
	
	/**
	 * Sets the value of the attribute {@link #idSystemNotification}.
	 * @param idSystemNotificationParam The value for the attribute {@link #idSystemNotification}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdSystemNotification(Long idSystemNotificationParam) {
		// CHECKSTYLE:ON
		this.idSystemNotification = idSystemNotificationParam;
	}
	

	/**
	 * Gets the value of the attribute {@link #description}.
	 * @return the value of the attribute {@link #description}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "DESCRIPTION", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getDescription() {
		// CHECKSTYLE:ON
		return description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 * @param descriptionParam The value for the attribute {@link #description}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setDescription(String descriptionParam) {
		// CHECKSTYLE:ON
		this.description = descriptionParam;
	}
		
	
	/**
	 * Gets the value of the attribute {@link #isOk}.
	 * @return the value of the attribute {@link #isOk}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_OK", nullable = false, precision = 1)
	@Type(type = "yes_no")
	@JsonView(DataTablesOutput.View.class)
	public Boolean getIsOk() {
		// CHECKSTYLE:ON
		return isOk;
	}

	/**
	 * Sets the value of the attribute {@link #isOk}.
	 * @param isoKParam The value for the attribute {@link #isOk}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsOk(Boolean isoKParam) {
		// CHECKSTYLE:ON
		this.isOk = isoKParam;
	}

	/**
	 * Gets the value of the attribute {@link #notificationPriority}.
	 * @return the value of the attribute {@link #notificationPriority}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NOTIFICATION_PRIORITY", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public CNotificationPriority getNotificationPriority() {
		// CHECKSTYLE:ON
		return notificationPriority;
	}

	/**
	 * Sets the value of the attribute {@link #notificationPriority}.
	 * @param notificationPriorityParam The value for the attribute {@link #notificationPriority}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNotificationPriority(CNotificationPriority notificationPriorityParam) {
		// CHECKSTYLE:ON
		this.notificationPriority = notificationPriorityParam;
	}

	/**
	 * Gets the value of the attribute {@link #notificationType}.
	 * @return the value of the attribute {@link #notificationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NOTIFICATION_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public CNotificationType getNotificationType() {
		// CHECKSTYLE:ON
		return notificationType;
	}

	/**
	 * Sets the value of the attribute {@link #notificationType}.
	 * @param notificationTypeParam The value for the attribute {@link #notificationType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNotificationType(CNotificationType notificationTypeParam) {
		// CHECKSTYLE:ON
		this.notificationType = notificationTypeParam;
	}
		
	/**
	 * Gets the value of the attribute {@link #notificationOrigin}.
	 * @return the value of the attribute {@link #notificationOrigin}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NOTIFICATION_ORIGIN", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public CNotificationOrigin getNotificationOrigin() {
		// CHECKSTYLE:ON
		return notificationOrigin;
	}

	/**
	 * Sets the value of the attribute {@link #notificationOrigin}.
	 * @param notificationOriginParam The value for the attribute {@link #notificationOrigin}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setNotificationOrigin(CNotificationOrigin notificationOriginParam) {
		// CHECKSTYLE:ON
		this.notificationOrigin = notificationOriginParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #creationDate}.
	 * @return the value of the attribute {@link #creationDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@JoinColumn(name = "CREATION_DATE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public LocalDateTime getCreationDate() {
		// CHECKSTYLE:ON
		return creationDate;
	}

	/**
	 * Sets the value of the attribute {@link #creationDate}.
	 * @param creationDateParam The value for the attribute {@link #creationDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setCreationDate(LocalDateTime creationDateParam) {
		// CHECKSTYLE:ON
		this.creationDate = creationDateParam;
	}

	/**
	 * Gets the value of the attribute {@link #expirationDate}.
	 * @return the value of the attribute {@link #expirationDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@JoinColumn(name = "EXPIRATION_DATE", nullable = false)
	@JsonView(DataTablesOutput.View.class)	
	public LocalDateTime getExpirationDate() {
		// CHECKSTYLE:ON
		return expirationDate;
	}

	/**
	 * Sets the value of the attribute {@link #expirationDate}.
	 * @param expirationDateParam The value for the attribute {@link #expirationDate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setExpirationDate(LocalDateTime expirationDateParam) {
		// CHECKSTYLE:ON
		this.expirationDate = expirationDateParam;
	}	
	

}
