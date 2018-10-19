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
 * <b>File:</b><p>es.gob.monitoriza.service.INodeMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>09/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 09/10/2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;

/** 
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/10/2018.
 */
public interface INodeMonitorizaService {
	
	/**
	 * Method that obtains the configuration for a node by its identifier.
	 * @param nodeId The node identifier.
	 * @return {@link NodeMonitoriza}
	 */
	NodeMonitoriza getNodeById(Long nodeId);
	
	/**
	 * Method that stores a node configuration in the persistence.
	 * @param node a {@link NodeMonitoriza} with the information of the node configuration.
	 * @return {@link NodeMonitoriza} The node configuration. 
	 */
	NodeMonitoriza savePlatform(NodeMonitoriza node);
			
	/**
	 * Method that deletes a node configuration in the persistence.
	 * @param nodeId {@link Integer} that represents the user identifier to delete.
	 */
	void deleteNodeById(Long nodeId);
	
	/**
	 * Method that returns a list of @firma nodes to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<NodeMonitoriza> findAllAfirma(DataTablesInput input);
	
	/**
	 * Method that returns a list of TS@ nodes to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<NodeMonitoriza> findAllTsa(DataTablesInput input);
	
	/**
	 * Method that returns all nodes.
	 * @return Iterable<NodeMonitoriza>
	 */
	Iterable<NodeMonitoriza> getAllNode();

}
