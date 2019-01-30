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
 * @version 1.1, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.NodeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.CPlatformType;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.NodeMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.NodeDatatableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.CNodeTypeSpecification;
import es.gob.monitoriza.service.INodeMonitorizaService;


/** 
 * <p>Class that implements the communication with the operations of the persistence layer for NodeMonitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
@Service("nodeMonitorizaService")
public class NodeMonitorizaService implements INodeMonitorizaService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private NodeMonitorizaRepository repository;
		
	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence. 
	 */
	@Autowired
    private NodeDatatableRepository dtRepository; 

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
	public NodeMonitoriza saveNode(NodeMonitoriza node) {
		
		return repository.save(node);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#deleteNodeById(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteNodeById(Long nodeId) {
		
		repository.deleteById(nodeId);

	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#findAllAfirma(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<NodeMonitoriza> findAllAfirma(DataTablesInput input) {
		
		CPlatformType nodeType = new CPlatformType();
		nodeType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		CNodeTypeSpecification byNodeType = new CNodeTypeSpecification(nodeType);
		
		return dtRepository.findAll(input, byNodeType);
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#findAllTsa(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<NodeMonitoriza> findAllTsa(DataTablesInput input) {
		
		CPlatformType nodeType = new CPlatformType();
		nodeType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		CNodeTypeSpecification byNodeType = new CNodeTypeSpecification(nodeType);
		
		return dtRepository.findAll(input, byNodeType);
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#getAllNode()
	 */
	@Override
	public Iterable<NodeMonitoriza> getAllNode() {
		
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#getByPlatformType(java.lang.Long)
	 */
	@Override
	public List<NodeMonitoriza> getByPlatformType(Long idPlatformType) {
		
		return repository.findByNodeTypeIdPlatformType(idPlatformType);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#saveNodeAfirma(es.gob.monitoriza.persistence.configuration.dto.NodeDTO)
	 */
	@Override
	@Transactional
	public NodeMonitoriza saveNodeAfirma(NodeDTO nodeAfirmaDto) {
		
		NodeMonitoriza nodeAfirma = null;
		//boolean afirmaHaCambiado = false;
		
		if (nodeAfirmaDto.getIdNode() != null) {
			nodeAfirma = repository.findByIdNode(nodeAfirmaDto.getIdNode());
			//afirmaHaCambiado = isAfirmaUpdatedForm(nodeAfirmaDto, nodeAfirma);
		} else {
			nodeAfirma = new NodeMonitoriza();
		}

		nodeAfirma.setHost(nodeAfirmaDto.getHost());
		nodeAfirma.setName(nodeAfirmaDto.getName());
		nodeAfirma.setPort(nodeAfirmaDto.getPort());
		nodeAfirma.setIsSecure(nodeAfirmaDto.getIsSecure());
		nodeAfirma.setCheckEmergencyDB(nodeAfirmaDto.getCheckEmergencyDB());
		nodeAfirma.setCheckHsm(nodeAfirmaDto.getCheckHsm());
		nodeAfirma.setCheckServices(nodeAfirmaDto.getCheckServices());
		nodeAfirma.setCheckTsa(nodeAfirmaDto.getCheckTsa());
		nodeAfirma.setCheckValidMethod(nodeAfirmaDto.getCheckValidMethod());
		CPlatformType afirmaType = new CPlatformType();
		afirmaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA);
		nodeAfirma.setNodeType(afirmaType);

		NodeMonitoriza afirmaNode = repository.save(nodeAfirma);
				
		// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
//		if (afirmaHaCambiado && nodeAfirma.getIdPlatform() != null) {
//			
//			updateScheduledTimerFromPlatform(nodeAfirma);
//		}
		
		return afirmaNode;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.INodeMonitorizaService#saveNodeTsa(es.gob.monitoriza.persistence.configuration.dto.NodeDTO)
	 */
	@Override
	@Transactional
	public NodeMonitoriza saveNodeTsa(NodeDTO nodeTsaDto) {
		
		NodeMonitoriza nodeTsa = null;
		//boolean afirmaHaCambiado = false;
		
		if (nodeTsaDto.getIdNode() != null) {
			nodeTsa = repository.findByIdNode(nodeTsaDto.getIdNode());
			//afirmaHaCambiado = isAfirmaUpdatedForm(nodeAfirmaForm, nodeAfirma);
		} else {
			nodeTsa = new NodeMonitoriza();
		}

		nodeTsa.setHost(nodeTsaDto.getHost());
		nodeTsa.setName(nodeTsaDto.getName());
		nodeTsa.setPort(nodeTsaDto.getPort());
		nodeTsa.setIsSecure(nodeTsaDto.getIsSecure());
		nodeTsa.setCheckEmergencyDB(nodeTsaDto.getCheckEmergencyDB());
		nodeTsa.setCheckHsm(nodeTsaDto.getCheckHsm());
		nodeTsa.setCheckAfirma(nodeTsaDto.getCheckAfirma());
		
		CPlatformType tsaType = new CPlatformType();
		tsaType.setIdPlatformType(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA);
		nodeTsa.setNodeType(tsaType);

		NodeMonitoriza tsaNode = repository.save(nodeTsa);
				
		// Si la plataforma ha cambiado y no es nueva (sin asociar), se actualiza el estado de los timers programados asociados.
//		if (afirmaHaCambiado && nodeAfirma.getIdPlatform() != null) {
//			
//			updateScheduledTimerFromPlatform(nodeAfirma);
//		}
		
		return tsaNode;
	}

}
