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
 * <b>File:</b><p>es.gob.monitoriza.service.IMethodValidationService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.service;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.persistence.configuration.model.entity.MethodValidation;

/**
 * <p>
 * Interface that provides communication with the operations of the persistence
 * layer.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16/10/2018.
 */
public interface IMethodValidationService {

	/**
	 * Gets all methodValidations.
	 * 
	 * @return List methodValidations.
	 */
	List<MethodValidation> getAllMethodValidation();

	/**
	 * Method that gets methodValidation by ID of MethodValidation.
	 * 
	 * @param idMethodValidation
	 *            Id of MethodValidation
	 * @return {@link MethodValidation} an object that represents the
	 *         MethodValidation.
	 */
	MethodValidation getMethodValidationById(Long idMethodValidation);

	/**
	 * Method that saves MethodValidation.
	 * 
	 * @param MethodValidation
	 *            to update.
	 * @return {@link MethodValidation} an object that represents the
	 *         MethodValidation.
	 */
	MethodValidation saveMethodValidation(MethodValidation methodValidation);

	/**
	 * Method that delete a MethodValidation.
	 * 
	 * @param idMethodValidation
	 *            Id of MethodValidation
	 */
	void deleteMethodValidation(Long idMethodValidation);

	/**
	 * Method that create all method validation by a list of string
	 * 
	 * @param methodValidations, confSpie
	 * 
	 * @return {@link List<MethodValidation>}
	 */
	List<MethodValidation> createAllMethods(List<String> methodValidations, ConfSpie confSpie);

	/**
	 * Method that gets all method validation by ID of Conf Spie in String
	 * 
	 * @return {@link List<String>}
	 */
	List<String> getAllMethodValidationString();

}
