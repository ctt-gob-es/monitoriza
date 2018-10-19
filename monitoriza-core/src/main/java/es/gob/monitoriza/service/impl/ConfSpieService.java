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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ConfSpieService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ConfSpieMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.persistence.configuration.model.repository.ConfSpieRepository;
import es.gob.monitoriza.service.IConfSpieService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for ConfSpie.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16/10/2018.
 */
@Service
public class ConfSpieService implements IConfSpieService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private ConfSpieRepository repository;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfSpieService#getAllConfSpie()
	 */
	@Override
	public ConfSpie getAllConfSpie() {
		List<ConfSpie> allConf = repository.findAll();
		if (!allConf.isEmpty()) {
			return allConf.get(0);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfSpieService#getConfSpieById(java.lang.Long)
	 */
	@Override
	public ConfSpie getConfSpieById(Long idConfSpie) {
		return repository.findByIdConfSpie(idConfSpie);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfSpieService#saveConfSpie(es.gob.valet.persistence.configuration.model.entity.ConfSpie)
	 */
	@Override
	public ConfSpie saveConfSpie(ConfSpie confSpie) {
		return repository.save(confSpie);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IConfSpieService#deleteConfSpie(java.lang.Long)
	 */
	@Override
	public void deleteConfSpie(Long idConfSpie) {
		repository.deleteById(idConfSpie);
	}

}
