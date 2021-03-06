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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.SystemCertificateService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.StatusCertificate;
import es.gob.monitoriza.persistence.configuration.model.repository.StatusCertificateRepository;
import es.gob.monitoriza.service.IStatusCertificateService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer for StatusCertificate.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
@Service("statusCertificateService")
public class StatusCertificateService implements IStatusCertificateService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private StatusCertificateRepository repository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.ISystemCertificateService#getSystemCertificateById(java.lang.Long)
	 */
	@Override
	public StatusCertificate getStatusCertificateById(final Long statusCertId) {

		return repository.findByIdStatusCertificate(statusCertId);
	}

	/**
	 * Get the repository.
	 * @return repository
	 */
	public StatusCertificateRepository getRepository() {
		return repository;
	}

	/**
	 * Set repository.
	 * @param repositoryP set repository
	 */
	public void setRepository(final StatusCertificateRepository repositoryP) {
		this.repository = repositoryP;
	}

}
