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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.MaintenanceServiceService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 14/03/2019.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService;
import es.gob.monitoriza.persistence.configuration.model.repository.MaintenanceServiceRepository;
import es.gob.monitoriza.service.IMaintenanceServiceService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for MaintenanceServiceService.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 14/03/2019.
 */
@Service("maintenanceService")
public class MaintenanceServiceService implements IMaintenanceServiceService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private MaintenanceServiceRepository repository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMaintenanceServiceService#getMaintenanceServiceById(java.lang.Long)
	 */
	@Override
	public MaintenanceService getMaintenanceServiceById(Long maintenanceServiceId) {
		
		return repository.findByIdMaintenanceService(maintenanceServiceId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMaintenanceServiceService#getMaintenanceServiceByService(java.lang.String)
	 */
	@Override
	public MaintenanceService getMaintenanceServiceByService(String service) {
		
		return repository.findByService(service);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IMaintenanceServiceService#saveMaintenanceService(es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService)
	 */
	@Override
	@Transactional
	public MaintenanceService saveMaintenanceService(MaintenanceService maintenanceService) {
		
		return repository.saveAndFlush(maintenanceService);
	}

}
