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

import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;

/** 
 * <p>Interface that provides CRUD functionality for the NodeMonitoriza entity.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/10/2018.
 */
public interface NodeMonitorizaRepository extends JpaRepository<NodeMonitoriza, Long> {
	
	/**
	  * Method that obtains from the persistence a node identified by its primary key. 
	 * @param id String that represents the primary key of the node in the persistence.
	 * @return Object that represents a node from the persistence. 
	 */
	NodeMonitoriza findByIdNode(Long id);

}
