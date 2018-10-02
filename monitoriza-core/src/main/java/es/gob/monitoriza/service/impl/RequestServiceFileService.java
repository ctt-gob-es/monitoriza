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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.RequestServiceFileService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>26/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 26/09/2018.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.exception.RequestFileNotFoundException;
import es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile;
import es.gob.monitoriza.persistence.configuration.model.repository.RequestServiceFileRepository;
import es.gob.monitoriza.service.IRequestServiceFileService;


/** 
 * <p>Class that implements the communication with the operations of the
 * persistence layer for RequestServiceFileService.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 26/09/2018.
 */
@Service
@Transactional
public class RequestServiceFileService implements IRequestServiceFileService {
	
	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private RequestServiceFileRepository repository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IRequestServiceFileService#getRequestFileById(java.lang.Long)
	 */
	@Override
	public RequestServiceFile getRequestFileById(Long fileId) throws RequestFileNotFoundException {
		
		return repository.findById(fileId).orElseThrow(() -> new RequestFileNotFoundException("File not found with id " + fileId));
	}

}
