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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.DailySpieMonitoringService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.DailySpieDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;
import es.gob.monitoriza.persistence.configuration.model.repository.DailySpieMonitoringRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SpieStatisticsRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.DailySpieDataTableRepository;
import es.gob.monitoriza.service.IDailySpieMonitoringService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for DailySpieMonitorig.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 30/01/2019.
 */
@Service("dailySpieMonitoringService")
public class DailySpieMonitoringService implements IDailySpieMonitoringService {
	
	/**
	 * Attribute that represents the {#JpaRepository} for {#DailySpieMonitorig}. 
	 */
	@Autowired
	private DailySpieMonitoringRepository repository;
	
	/**
	 * Attribute that represents the {#DataTablesRepository} for {#DailySpieMonitorig}. 
	 */
	@Autowired
	private DailySpieDataTableRepository dtRepository;
	
	/**
	 * Attribute that represents the {#JpaRepository} for {#SpieStatistics}. 
	 */
	@Autowired
	private SpieStatisticsRepository statRepository;
		

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailySpieMonitoringService#getDailySpieMonitoringById(java.lang.Long)
	 */
	@Override
	public DailySpieMonitorig getDailySpieMonitoringById(Long idDailySpie) {
		
		return repository.findByIdDailySpie(idDailySpie);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailySpieMonitoringService#saveDailySpieMonitoring(es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig)
	 */
	@Override
	@org.springframework.transaction.annotation.Transactional
	public void saveDailySpieMonitoring(DailySpieMonitorig dailySpie) {
		
		try {
			repository.saveAndFlush(dailySpie);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}

	}	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailySpieMonitoringService#findDailySpieTimeRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public DataTablesOutput<DailySpieMonitorig> findDailySpieTimeRangeDT(LocalDateTime min, LocalDateTime max) {
		
		return dtRepository.findDailySpieTimeRange(min, max);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailySpieMonitoringService#findDailySpieTimeRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public List<DailySpieDTO> findDailySpieTimeRange(LocalDateTime min, LocalDateTime max, String system, String service) {
		
		List<DailySpieDTO> dailiesResult = new ArrayList<DailySpieDTO>();
		List<DailySpieMonitorig> dailies = repository.findDailySpieTimeRange(min, max, system.isEmpty()?null:system, service.isEmpty()?null:service);
		
		DailySpieDTO dailyDto = null;
		
		for (DailySpieMonitorig daily : dailies) {
			dailyDto = new DailySpieDTO(daily.getStatus(), daily.getPlatform(), daily.getNode(), daily.getService(), daily.getSamplingTime());
			dailiesResult.add(dailyDto);
		}
		
		return dailiesResult;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailySpieMonitoringService#dumpAndDeleteMonitoringData()
	 */
	@Override
	@Transactional
	public void dumpAndDeleteSpieMonitoringData() {
		
		try {
			statRepository.dumpFromDailySpieNative();
			repository.deleteOldDailySpie();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}

}
