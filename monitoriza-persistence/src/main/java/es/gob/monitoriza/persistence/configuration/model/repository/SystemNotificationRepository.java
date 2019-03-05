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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.repository.PlatformAfirmaRepository.java.</p>
 * <b>Description:</b><p>Interface that provides CRUD functionality for the PlatformAfirma entity.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification;


/** 
 * <p>Interface that provides CRUD functionality for the PlatformMonitoriza entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
@Repository
public interface SystemNotificationRepository extends JpaRepository<SystemNotification, Long>, JpaSpecificationExecutor<SystemNotification> {
	
	/**
	  * Method that obtains from the persistence a system notification identified by its primary key. 
	 * @param id Long that represents the primary key of the system notification in the persistence.
	 * @return Object that represents a system notification from the persistence. 
	 */
	SystemNotification findByIdSystemNotification(Long id);
	
	/**
	  * Method that obtains from the persistence a system notification identified by its origin. 
	 * @param id Long that represents the primary key of the system notification origin in the persistence.
	 * @return Object that represents a system notification from the persistence. 
	 */
	SystemNotification findByNotificationOriginIdNotificationOrigin(Long id);
	
	/**
	 * Method that obtains from the persistence all system notifications that matches with the isOk parameter value.
	 * @param isOk Boolean that represents if the notification is reviewed by the admin.
	 * @return {@link List<SystemNotification>}
	 */
	List<SystemNotification> findByIsOk(Boolean isOk);
	

}
