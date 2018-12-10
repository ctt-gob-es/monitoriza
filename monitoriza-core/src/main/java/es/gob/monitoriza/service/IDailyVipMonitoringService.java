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
 * <b>File:</b><p>es.gob.monitoriza.service.IDailyVipMonitoringService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/11/2018.
 */
package es.gob.monitoriza.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.DailyVipDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.DailyVipMonitorig;

/** 
 * <p>Interface that provides communication with the operations of the persistence
 * layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 12/11/2018.
 */
public interface IDailyVipMonitoringService {
	
	/**
	 * Method that gets a {@link #DailyVipMonitorig} by its identifier.
	 * @param idDailyVip Identifier of the {@link #DailyVipMonitorig}
	 * @return {@link #DailyVipMonitorig}
	 */
	DailyVipMonitorig getDailyVipMonitoringById(Long idDailyVip);
	
	/**
	 * Method that stores a {@link #DailyVipMonitorig} in the persistence.
	 * @param dailyVip {@link #DailyVipMonitorig} to save
	 */
	void saveDailyVipMonitoring(DailyVipMonitorig dailyVip);
	
	/**
	 * Method that gets daily monitoring between two timestamp.
	 * @param min Minimum range timestamp
	 * @param max Maximum range timestamp
	 * @return DataTablesOutput<DailyVipMonitorig>
	 */
	DataTablesOutput<DailyVipMonitorig> findDailyVipTimeRangeDT(LocalDateTime min, LocalDateTime max);
	
	/**
	 * Method that gets daily monitoring between two datetime.
	 * @param min Minimum range datetime
	 * @param max Maximum range datetime
	 * @param system String that represents the name of the platform type to filter
	 * @param service String that represents the name or part of the name of the service to filter
	 * @return List<DailyVipDTO>
	 */
	List<DailyVipDTO> findDailyVipTimeRange(LocalDateTime min, LocalDateTime max, String system, String service);
	
	/**
	 * 
	 */
	void dumpAndDeleteMonitoringData();

}
