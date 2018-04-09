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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.dao.impl.UserMonitorizaRepository.java.</p>
 * <b>Description:</b><p>Interface that provides CRUD functionality for the UserMonitoriza entity.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>7 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 7 mar. 2018.
 */
package es.gob.monitoriza.persistence.configuration.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;

/** 
 * <p>Interface that provides CRUD functionality for the UserMonitoriza entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 7 mar. 2018.
 */
@Repository
public interface UserMonitorizaRepository extends PagingAndSortingRepository<UserMonitoriza, Long> {
	
	/**
	 * Method that obtains from the persistence a user identified by its login. 
	 * @param login String that represents the username used to log in.
	 * @return Object that represents a user from the persistence. 
	 */
	UserMonitoriza findByLogin(String login);
	
	/**
	  * Method that obtains from the persistence a user identified by its primary key. 
	 * @param id String that represents the primary key of the user in the persistence.
	 * @return Object that represents a user from the persistence. 
	 */
	UserMonitoriza findByIdUserMonitoriza(Long id);
	
}
