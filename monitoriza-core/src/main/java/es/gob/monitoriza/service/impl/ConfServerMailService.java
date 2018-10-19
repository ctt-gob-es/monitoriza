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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ConfServerMailService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ConfServerMailMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.persistence.configuration.model.repository.ConfServerMailRepository;
import es.gob.monitoriza.service.IConfServerMailService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for ConfServerMail.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16/10/2018.
 */
@Service
public class ConfServerMailService implements IConfServerMailService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ConfServerMailRepository repository;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfServerMailService#getAllConfServerMail()
	 */
	@Override
	public ConfServerMail getAllConfServerMail() {
		List<ConfServerMail> allConf = repository.findAll();
		if (!allConf.isEmpty()) {
			return allConf.get(0);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfServerMailService#getConfServerMailById(java.lang.Long)
	 */
	@Override
	public ConfServerMail getConfServerMailById(Long idConfServerMail) {
		return repository.findByIdConfServerMail(idConfServerMail);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfServerMailService#saveConfServerMail(es.gob.valet.persistence.configuration.model.entity.ConfServerMail)
	 */
	@Override
	public ConfServerMail saveConfServerMail(ConfServerMail confServerMail) {
		return repository.save(confServerMail);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfServerMailService#deleteConfServerMail(java.lang.Long)
	 */
	@Override
	public void deleteConfServerMail(Long idConfServerMail) {
		repository.deleteById(idConfServerMail);
	}

}
