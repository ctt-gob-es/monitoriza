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
 * <b>File:</b><p>es.gob.monitoriza.service.IConfServerMailService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.service;

import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;

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
public interface IConfServerMailService {

	/**
	 * Gets the unique confServerMails.
	 * 
	 * @return Unique confServerMails.
	 */
	ConfServerMail getAllConfServerMail();

	/**
	 * Method that gets confServerMail by ID of ConfServerMail.
	 * 
	 * @param idConfServerMail
	 *            Id of ConfServerMail
	 * @return {@link ConfServerMail} an object that represents the ConfServerMail.
	 */
	ConfServerMail getConfServerMailById(Long idConfServerMail);

	/**
	 * Method that saves ConfServerMail.
	 * 
	 * @param ConfServerMail
	 *            to update.
	 * @return {@link ConfServerMail} an object that represents the ConfServerMail.
	 */
	ConfServerMail saveConfServerMail(ConfServerMail confServerMail);

	/**
	 * Method that delete a ConfServerMail.
	 * 
	 * @param idConfServerMail
	 *            Id of ConfServerMail
	 */
	void deleteConfServerMail(Long idConfServerMail);

}
