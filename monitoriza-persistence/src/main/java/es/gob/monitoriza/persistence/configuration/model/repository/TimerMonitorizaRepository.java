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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.repository.TimerMonitorizaRepository.java.</p>
 * <b>Description:</b><p>Interface that provides CRUD functionality for the TimerMonitoriza entity.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 09/11/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;

/** 
 * <p>Interface that provides CRUD functionality for the TimerMonitoriza entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 09/11/2018.
 */
@Repository
public interface TimerMonitorizaRepository extends JpaRepository<TimerMonitoriza, Long> {
	
	/**
	  * Method that obtains from the persistence a timer identified by its primary key. 
	 * @param id String that represents the primary key of the service in the persistence.
	 * @return Object that represents a service from the persistence. 
	 */
	TimerMonitoriza findByIdTimer(Long id);
	
	/**
	 * Method that obtains all timers that has at least one service that uses RFC3161 authentication.
	 * @return List<TimerMonitoriza>
	 */
	@Query("SELECT tm FROM ServiceMonitoriza sm JOIN sm.timer tm JOIN sm.platform pm JOIN pm.platformType pt WHERE pt.idPlatformType = 2 AND pm.useRfc3161Auth = true")
	List<TimerMonitoriza> findTimersAnyServiceUsingRFC3161Auth();
	

}
