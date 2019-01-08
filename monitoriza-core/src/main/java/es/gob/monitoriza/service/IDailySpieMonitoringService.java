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
 * <b>File:</b><p>es.gob.monitoriza.service.IDailySpieMonitoringService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 04/01/2019.
 */
package es.gob.monitoriza.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.DailySpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;

/** 
 * <p>Interface that provides communication with the operations of the persistence
 * layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 04/01/2019.
 */
public interface IDailySpieMonitoringService {
	
	/**
	 * Method that gets a {@link #DailySpieMonitorig} by its identifier.
	 * @param idDailySpie Identifier of the {@link #DailySpieMonitorig}
	 * @return {@link #DailySpieMonitorig}
	 */
	DailySpieMonitorig getDailySpieMonitoringById(Long idDailySpie);
	
	/**
	 * Method that stores a {@link #DailySpieMonitorig} in the persistence.
	 * @param dailySpie {@link #DailySpieMonitorig} to save
	 */
	void saveDailySpieMonitoring(DailySpieMonitorig dailySpie);
	
	/**
	 * Method that gets daily monitoring between two timestamp.
	 * @param min Minimum range timestamp
	 * @param max Maximum range timestamp
	 * @return DataTablesOutput<DailyVipMonitorig>
	 */
	DataTablesOutput<DailySpieMonitorig> findDailySpieTimeRangeDT(LocalDateTime min, LocalDateTime max);
	
	/**
	 * Method that gets daily monitoring between two datetime.
	 * @param min Minimum range datetime
	 * @param max Maximum range datetime
	 * @param system String that represents the name of the platform type to filter
	 * @param service String that represents the name or part of the name of the service to filter
	 * @return List<DailyVipDTO>
	 */
	List<DailySpieDTO> findDailySpieTimeRange(LocalDateTime min, LocalDateTime max, String system, String service);
	
	/**
	 * Group and dumps the data stored in the SPIE daily monitoring into the SPIE statistics table.
	 * Then deletes older monitoring data.
	 */
	void dumpAndDeleteSpieMonitoringData();

}
