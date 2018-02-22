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
/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.configuration.ServicesManager.java.</p>
 * <b>Description:</b><p>Interface that provides the methods for managing the configuration of the @firma/ts@ services.</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>19 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 19 ene. 2018.
 */
package es.gob.monitoriza.configuration;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.dto.DTOService;

/** 
 * <p>Interface that provides the methods for managing the configuration of the @firma/ts@ services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 19/01/2018.
 */
public interface ServicesManager {
	
	/**
	 * Method that gets the services configuration from persistence (database or static properties file)
	 * @return
	 */
	List<DTOService> getServices();
	
	/**
	 * Method that gets the services  from persistence (database or static properties file)
	 * @param timerId The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	List<DTOService> getServicesByTimer(String timerId);

}
