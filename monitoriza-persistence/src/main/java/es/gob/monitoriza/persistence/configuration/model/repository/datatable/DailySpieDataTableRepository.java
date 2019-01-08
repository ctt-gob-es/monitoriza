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
package es.gob.monitoriza.persistence.configuration.model.repository.datatable;

import java.time.LocalDateTime;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;

/** 
 * <p>Interface that provides CRUD functionality for the DailySpieMonitorig entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 04/01/2019.
 */
@Repository
public interface DailySpieDataTableRepository extends DataTablesRepository<DailySpieMonitorig, Long> {
		
	/**
	 * Method that gets daily monitoring between two timestamp.
	 * @param min Minimum range timestamp
	 * @param max Maximum range timestamp
	 * @return DataTablesOutput<DailyVipMonitorig>
	 */
	@Query("SELECT daily FROM DailySpieMonitorig daily WHERE daily.samplingTime BETWEEN ?1 and ?2")
	DataTablesOutput<DailySpieMonitorig> findDailySpieTimeRange(LocalDateTime min, LocalDateTime max);
}
