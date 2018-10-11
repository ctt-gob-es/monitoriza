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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.NodeMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>09/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 09/10/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.NodeMonitorizaRepository;
import es.gob.monitoriza.service.INodeMonitorizaService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for NodeMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/10/2018.
 */
@Service
public class NodeMonitorizaService implements INodeMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private NodeMonitorizaRepository repository; 

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#getNodeById(java.lang.Long)
	 */
	@Override
	public NodeMonitoriza getNodeById(Long nodeId) {
		
		return repository.findByIdNode(nodeId);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#savePlatform(es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza)
	 */
	@Override
	public NodeMonitoriza savePlatform(NodeMonitoriza node) {
		
		return repository.save(node);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#deleteNodeById(java.lang.Long)
	 */
	@Override
	public void deleteNodeById(Long nodeId) {
		
		repository.deleteById(nodeId);

	}

}
