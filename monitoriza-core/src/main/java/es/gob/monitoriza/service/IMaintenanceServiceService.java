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
 * <b>File:</b><p>es.gob.monitoriza.service.IMaintenanceServiceService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 14/03/2019.
 */
package es.gob.monitoriza.service;

import es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 14/03/2019.
 */
public interface IMaintenanceServiceService {
	
	/**
	 * Method that obtains the configuration for {@link #MaintenanceService} by its identifier.
	 * 
	 * @param maintenanceServiceId Identifier of the {@link MaintenanceService}.
	 * @return {@link MaintenanceService}
	 */
	MaintenanceService getMaintenanceServiceById(Long maintenanceServiceId);
	
	/**
	 * Method that obtains the configuration for {@link #MaintenanceService} by its identifier.
	 * 
	 * @param service String that represents the service
	 * @return {@link MaintenanceService}
	 */
	MaintenanceService getMaintenanceServiceByService(String service);
	
	/**
	 * Method that stores a {@link MaintenanceService} in the persistence.
	 * 
	 * @param maintenanceService a {@link MaintenanceService} to save.
	 * @return {@link MaintenanceService} The service.
	 */
	MaintenanceService saveMaintenanceService(MaintenanceService maintenanceService);

}
