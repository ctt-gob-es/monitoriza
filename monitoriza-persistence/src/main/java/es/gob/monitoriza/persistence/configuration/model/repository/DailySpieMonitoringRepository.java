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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.repository.NodeAfirmaRepository.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;

/** 
 * <p>Interface that provides CRUD functionality for the DailySpieMonitorig entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/01/2019.
 */
@Repository
public interface DailySpieMonitoringRepository extends JpaRepository<DailySpieMonitorig, Long> {
	
	/**
	  * Method that obtains from the persistence a node identified by its primary key. 
	 * @param id Long that represents the primary key of the node in the persistence.
	 * @return Object that represents a daily SPIE register from the persistence. 
	 */
	DailySpieMonitorig findByIdDailySpie(Long id);
		
	
	/**
	 * Method that gets daily monitoring between two datetime.
	 * @param min Minimum range datetime
	 * @param max Maximum range datetime
	 * @param system String that represents the name of the platform type to filter
	 * @param service String that represents the name or part of the name of the service to filter
	 * @return List<DailySpieMonitorig>
	 */
	@Query("SELECT daily FROM DailySpieMonitorig daily WHERE daily.samplingTime BETWEEN :min and :max and (:system is null or daily.platform = :system) and (:service is null or daily.service like %:service%)")
	List<DailySpieMonitorig> findDailySpieTimeRange(@Param("min") LocalDateTime min, @Param("max") LocalDateTime max, @Param("system") String system, @Param("service") String service);
	
	/**
	 * Method that deletes the results whose sampling time are older than today.
	 */
	@Query("DELETE FROM DailySpieMonitorig daily WHERE daily.samplingTime < current_date")
	@Modifying
	@Transactional
	void deleteOldDailySpie();
}
