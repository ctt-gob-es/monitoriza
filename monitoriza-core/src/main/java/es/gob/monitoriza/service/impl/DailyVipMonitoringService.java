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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.DailyVipMonitoringService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.DailyVipDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.DailyVipMonitorig;
import es.gob.monitoriza.persistence.configuration.model.repository.DailyVipMonitoringRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.VipStatisticsRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.DailyVipDataTableRepository;
import es.gob.monitoriza.service.IDailyVipMonitoringService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for DailyVipMonitorig.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
@Service("dailyVipMonitoringService")
public class DailyVipMonitoringService implements IDailyVipMonitoringService {
	
	/**
	 * Attribute that represents the {#JpaRepository} for {#DailyVipMonitorig}. 
	 */
	@Autowired
	private DailyVipMonitoringRepository repository;
	
	/**
	 * Attribute that represents the {#DataTablesRepository} for {#DailyVipMonitorig}. 
	 */
	@Autowired
	private DailyVipDataTableRepository dtRepository;
	
	/**
	 * Attribute that represents the {#JpaRepository} for {#VipStatistics}. 
	 */
	@Autowired
	private VipStatisticsRepository statRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailyVipMonitoringService#getDailyVipMonitoringById(java.lang.Long)
	 */
	@Override
	public DailyVipMonitorig getDailyVipMonitoringById(Long idDailyVip) {
		
		return repository.findByIdDailyVip(idDailyVip);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailyVipMonitoringService#saveDailyVipMonitoring(es.gob.monitoriza.persistence.configuration.model.entity.DailyVipMonitorig)
	 */
	@Override
	@Transactional
	public void saveDailyVipMonitoring(DailyVipMonitorig dailyVip) throws DatabaseException {
		
		try {
			repository.saveAndFlush(dailyVip);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailyVipMonitoringService#findDailyVipTimeRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public DataTablesOutput<DailyVipMonitorig> findDailyVipTimeRangeDT(LocalDateTime min, LocalDateTime max) {
		
		return dtRepository.findDailyVipTimeRange(min, max);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailyVipMonitoringService#findDailyVipTimeRange(java.time.LocalDateTime, java.time.LocalDateTime)
	 */
	@Override
	public List<DailyVipDTO> findDailyVipTimeRange(LocalDateTime min, LocalDateTime max, String system, String service) {
		
		List<DailyVipDTO> dailiesResult = new ArrayList<DailyVipDTO>();
		List<DailyVipMonitorig> dailies = repository.findDailyVipTimeRange(min, max, system.isEmpty()?null:system, service.isEmpty()?null:service);
		
		DailyVipDTO dailyDto = null;
		
		for (DailyVipMonitorig daily : dailies) {
			dailyDto = new DailyVipDTO(daily.getStatus(), daily.getPlatform(), daily.getService(), daily.getSamplingTime());
			dailiesResult.add(dailyDto);
		}
		
		return dailiesResult;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IDailyVipMonitoringService#dumpAndDeleteMonitoringData()
	 */
	@Override
	@Transactional
	public void dumpAndDeleteMonitoringData() {
		
		try {	
			statRepository.dumpFromDailyVipNative();
			repository.deleteOldDailyVip();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}

}
