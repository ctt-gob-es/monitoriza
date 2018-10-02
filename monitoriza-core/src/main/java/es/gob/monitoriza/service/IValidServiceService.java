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
 * <b>File:</b><p>IUserMonitorizaService.IValidServiceService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 ago. 2018.
 */
package es.gob.monitoriza.service;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 ago. 2018.
 */
public interface IValidServiceService {
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IValidServiceService#getAllValidServices()
	 */
	List<ValidService> getAllValidServices();
	
	/**
	 * Method that obtains an valid service by its identifier.
	 * @param validServiceId The valid service identifier.
	 * @return {@link ValidService}
	 */
	ValidService getValidServiceById(Long validServiceId);

	/**
	 * Method that stores a valid service in the persistence.
	 * @param valid service a {@link ValidService} with the information of the valid service.
	 * @return {@link ValidService} The valid service.
	 */
	ValidService saveValidService(ValidService validService);
}
