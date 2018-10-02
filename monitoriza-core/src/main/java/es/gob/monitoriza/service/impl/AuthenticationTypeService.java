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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.AuthenticationTypeService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 29 ago. 2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.AuthenticationType;
import es.gob.monitoriza.persistence.configuration.model.repository.AuthenticationTypeRepository;
import es.gob.monitoriza.service.IAuthenticationTypeService;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 29 ago. 2018.
 */
@Service
public class AuthenticationTypeService implements IAuthenticationTypeService {

	/**
	 * Attribute that represents the injected interface that provides CRUD operations for the persistence.
	 */
	@Autowired
	private AuthenticationTypeRepository repository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAuthenticationTypeService#getAllAuthenticationTypes()
	 */
	@Override
	public List<AuthenticationType> getAllAuthenticationTypes() {

		return repository.findAll();
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAuthenticationTypeService#getAuthenticationTypeById(java.lang.Long)
	 */
	@Override
	public AuthenticationType getAuthenticationTypeById(final Long authenticationTypeId) {

		return repository.findByIdAuthenticationType(authenticationTypeId);
	}

	/**
	 * Get repository
	 * @return repository
	 */
	public AuthenticationTypeRepository getRepository() {
		return repository;
	}

	/**
	 * Set repository.
	 * @param repositoryP set repository
	 */
	public void setRepository(final AuthenticationTypeRepository repositoryP) {
		this.repository = repositoryP;
	}

}
