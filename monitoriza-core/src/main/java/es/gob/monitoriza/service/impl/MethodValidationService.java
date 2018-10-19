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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.MethodValidationService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for MethodValidationMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.persistence.configuration.model.entity.MethodValidation;
import es.gob.monitoriza.persistence.configuration.model.repository.MethodValidationRepository;
import es.gob.monitoriza.service.IMethodValidationService;

/**
 * <p>
 * Class that implements the communication with the operations of the
 * persistence layer for MethodValidation.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16/10/2018.
 */
@Service
public class MethodValidationService implements IMethodValidationService {

	/**
	 * Attribute that represents the injected interface that provides CRUD
	 * operations for the persistence.
	 */
	@Autowired
	private MethodValidationRepository repository;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#getAllMethodValidation()
	 */
	@Override
	public List<MethodValidation> getAllMethodValidation() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#getMethodValidationById(java.lang.Long)
	 */
	@Override
	public MethodValidation getMethodValidationById(Long idMethodValidation) {
		return repository.findByIdMethodValidation(idMethodValidation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#saveMethodValidation(es.gob.valet.persistence.configuration.model.entity.MethodValidation)
	 */
	@Override
	public MethodValidation saveMethodValidation(MethodValidation methodValidation) {
		return repository.save(methodValidation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#deleteMethodValidation(java.lang.Long)
	 */
	@Override
	public void deleteMethodValidation(Long idMethodValidation) {
		repository.deleteById(idMethodValidation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#createAllMethods()
	 */
	@Override
	public List<MethodValidation> createAllMethods(List<String> methodValidations, ConfSpie confSpie) {
		List<MethodValidation> result = new ArrayList<MethodValidation>();
		List<MethodValidation> methodsOld = new ArrayList<MethodValidation>();
		
		methodsOld = getAllMethodValidation();
		
		// Eliminamos todos los métodos anteriores
		for(MethodValidation mv: methodsOld) {
			deleteMethodValidation(mv.getIdMethodValidation());
		}
		
		// Añadimos los que nos envían
		for(String s: methodValidations) {
			MethodValidation mv = new MethodValidation();
			mv.setMethodName(s);
			mv.setConfSpie(confSpie);
			result.add(saveMethodValidation(mv));
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.gob.valet.persistence.configuration.services.ifaces.IMethodValidationService#getAllMethodValidationString(java.lang.Long)
	 */
	@Override
	public List<String> getAllMethodValidationString() {
		List<String> result = new ArrayList<String>();
		List<MethodValidation> methods = new ArrayList<MethodValidation>();
		
		methods = getAllMethodValidation();
		
		for(MethodValidation mv: methods) {
			result.add(mv.getMethodName());
		}
		
		return result;
	}

}
