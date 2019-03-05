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
 * <b>File:</b><p>es.gob.monitoriza.service.ISystemNotificationService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationOrigin;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationPriority;
import es.gob.monitoriza.persistence.configuration.model.entity.CNotificationType;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
public interface ISystemNotificationService {
	
	/**
	 * Method that obtains a system notification by its identifier.
	 * @param sysNotificationId The system notification identifier.
	 * @return {@link SystemNotification}
	 */
	SystemNotification getSystemNotificationById(Long sysNotificationId);
	
	/**
	 * Method that obtains a system notification by its origin.
	 * @param originNotificationId The system notification origin identifier.
	 * @return {@link SystemNotification}
	 */
	SystemNotification getSystemNotificationByOrigin(Long originNotificationId);
	
	/**
	 * Method that obtains a system notification by its identifier.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return {@link DataTablesOutput<SystemNotification>}
	 */
	DataTablesOutput<SystemNotification> findAllSystemNotifications(DataTablesInput input);
	
	/**
	 * Method that register a new system notification.
	 * @param idNotificationType Notification type identifier.
	 * @param idNotificationOrigin Notification origin identifier.
	 * @param idNotificationPriority Notification priority identifier.
	 * @param msg String that represents the description of the notification.
	 */
	void registerSystemNotification(Long idNotificationType, Long idNotificationOrigin, Long idNotificationPriority, String msg);
	
	/**
	 * Method that deletes a system notification from persistence.
	 * @param systemNotification The system notification to delete.
	 */
	void deleteSystemNotification(SystemNotification systemNotification);
	
	/**
	 * Method that obtains a notification type by its identifier.
	 * @param idNotificationType The system notification identifier.
	 * @return @link CNotificationType}
	 */
	CNotificationType getNotificationTypeById(Long idNotificationType);
	
	/**
	 * Method that obtains a notification origin by its identifier.
	 * @param idNotificationOrigin The system notification identifier.
	 * @return {@link CNotificationOrigin}
	 */
	CNotificationOrigin getNotificationOriginById(Long idNotificationOrigin);
	
	/**
	 * Method that obtains a notification priority by its identifier.
	 * @param idNotificationPriority The system notification identifier.
	 * @return {@link CNotificationPriority}
	 */
	CNotificationPriority getNotificationPriorityById(Long idNotificationPriority);
	
	/**
	 * Method that deletes all expired notifications.
	 */
	void clearExpiredNotifications();
	
	/**
	 * Method that checks if there are pending notifications.
	 * @return {@link true} if there are pending notifications, 
	 */
	Boolean hasPendingNotifications();
	
	/**
	 * Mark the notificacion as reviewd by the admin
	 * @param sysNot The system notification to mark.
	 */
	void checkNotification(SystemNotification sysNot);

}
