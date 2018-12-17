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
 * <b>File:</b><p>es.gob.monitoriza.service.IPlatformController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14 dic. 2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 14/12/2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.AfirmaDTO;
import es.gob.monitoriza.persistence.configuration.dto.ClaveDTO;
import es.gob.monitoriza.persistence.configuration.dto.TsaDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 17/10/2018.
 */
public interface IPlatformService {
		
	/**
	 * Method that obtains the configuration for a platform by its identifier.
	 * @param platformId The platform identifier.
	 * @return {@link PlatformMonitoriza}
	 */
	PlatformMonitoriza getPlatformById(Long platformId);
	
	/**
	 * Method that obtains a platform type by its identifier.
	 * @param platformId The platform type identifier.
	 * @return {@link CPlatformType}
	 */
	CPlatformType getPlatformTypeById(Long platformId);
			
	/**
	 * Method that deletes a @firma configuration in the persistence.
	 * @param platformId {@link Integer} that represents the user identifier to delete.
	 */
	void deletePlatformById(Long platformId);
	
	/**
	 * Method that deletes a @firma configuration in the persistence.
	 * @param platform {@link PlatformMonitoriza} that represents the platform to delete.
	 */
	void deletePlatform(PlatformMonitoriza platform);
	
	/**
	 * Method that gets all the platform configuration from the persistence.
	 * @return a {@link Iterable<PlatformMonitoriza>} with the information of all users.
	 */
	Iterable<PlatformMonitoriza> getAllPlatform();
	
	/**
	 * Method that gets all the platform configuration from the persistence.
	 * @return a {@link Iterable<CPlatformType>} with the information of all users.
	 */
	Iterable<CPlatformType> getAllPlatformType();
		
	/**
	 * Method that returns a list of platforms to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<PlatformMonitoriza> findAll(DataTablesInput input);
	
	/**
	 * Method that returns a list of @firma platforms to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<PlatformMonitoriza> findAllAfirma(DataTablesInput input);
	
	/**
	 * Method that returns a list of ts@ platforms to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<PlatformMonitoriza> findAllTsa(DataTablesInput input);
	
	/**
	 * Method that stores @firma configuration in the persistence and updates corresponding scheduled timers.
	 * @param afirmaDto a {@link AfirmaDTO} with the information of the platform configuration.
	 * @return {@link PlatformMonitoriza} The @firma configuration. 
	 */
	PlatformMonitoriza savePlatformAfirma(AfirmaDTO afirmaDto);
	
	/**
	 * Method that stores TS@ configuration in the persistence and updates corresponding scheduled timers.
	 * @param tsaDto a {@link TsaDTO} with the information of the platform configuration.
	 * @return {@link PlatformMonitoriza} The @firma configuration. 
	 */
	PlatformMonitoriza savePlatformTsa(TsaDTO tsaDto);

	/**
	 * Method that returns a list of platforms to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @param platformType Type of platform
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<PlatformMonitoriza> findAllByPlatFormType(DataTablesInput input, Long platformType);
	
	/**
	 * Method that stores Cl@ve configuration in the persistence and updates corresponding scheduled timers.
	 * @param claveDTO a {@link ClaveDTO} with the information of the platform configuration.
	 * @param platformTypeId with the information of the platform type.
	 * @return {@link PlatformMonitoriza} The cl@ve configuration. 
	 */
	PlatformMonitoriza savePlatformClave(ClaveDTO claveDTO, Long platformTypeId);
	
}