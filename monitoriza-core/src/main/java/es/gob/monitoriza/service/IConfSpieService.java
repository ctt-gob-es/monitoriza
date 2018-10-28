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
 * <b>File:</b><p>es.gob.monitoriza.service.IConfSpieService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.service;

import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;

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
 * @version 1.1, 28/10/2018.
 */
public interface IConfSpieService {

	/**
	 * Gets the unique confSpies.
	 * 
	 * @return Unique confSpies.
	 */
	ConfSpie getAllConfSpie();

	/**
	 * Method that gets confSpie by ID of ConfSpie.
	 * 
	 * @param idConfSpie Identifier of ConfSpie
	 * @return {@link ConfSpie} an object that represents the ConfSpie.
	 */
	ConfSpie getConfSpieById(Long idConfSpie);

	/**
	 * Method that saves ConfSpie.
	 * 
	 * @param confSpie to update.
	 * @return {@link ConfSpieDTO} an object that represents the ConfSpie.
	 */
	ConfSpie saveConfSpie(ConfSpieDTO confSpie);

	/**
	 * Method that delete a ConfSpie.
	 * 
	 * @param idConfSpie Identifier of ConfSpie
	 */
	void deleteConfSpie(Long idConfSpie);

}
