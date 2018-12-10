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
 * <b>Date:</b><p>09/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 09/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.VipStatistics;

/** 
 * <p>Interface that provides CRUD functionality for the VipStatistics entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/12/2018.
 */
@Repository
public interface VipStatisticsRepository extends JpaRepository<VipStatistics, Long> {
	
	/**
	  * Method that obtains from the persistence a node identified by its primary key. 
	 * @param id Long that represents the primary key of the node in the persistence.
	 * @return Object that represents a VIP Statistic register from the persistence. 
	 */
	VipStatistics findByIdVipStat(Long id);
		
	
	/**
	 * Method that calculates the percent status per service and sampling date time and dump the results into VIP_STATISTICS table.
	 */
	@Query(value = "insert into VIP_STATISTICS select NEXT VALUE FOR SQ_VIP_STATISTICS, daily.status as status, daily.platform as platform, daily.service as service, TO_DATE(daily.sd,'DD-MM-YYYY') as samplingdate, (100.00 * daily.N) / total.N as percent_status from ( select status, platform , service, TO_CHAR(sampling_time,'DD-MM-YYYY') as sd, count(*) as N from daily_vip_monitoring WHERE TO_CHAR(sampling_time,'DD-MM-YYYY') != TO_CHAR(CURDATE(), 'DD-MM-YYYY') group by status, platform, service, sd ) daily join ( select service, TO_CHAR(sampling_time,'DD-MM-YYYY') as sd , count(*) as N from daily_vip_monitoring group by sd, service ) total on total.sd = daily.sd and total.service =  daily.service order by 4,3"
		   , nativeQuery = true)
	@Modifying
	@Transactional
	void dumpFromDailyVipNative();
}
