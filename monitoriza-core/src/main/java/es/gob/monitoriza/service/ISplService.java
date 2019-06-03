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
 * <b>File:</b><p>es.gob.monitoriza.service.IPlatformController.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 04/01/2019.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.SplDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.SplMonitoriza;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring SPLs.</p>
 * @version 1.0, 14/03/2019.
 */
public interface ISplService {

	/**
	 * Method that obtains the information for a SPL by its identifier.
	 * @param splId The SPL identifier.
	 * @return {@link SplMonitoriza}
	 */
	SplMonitoriza getSplById(Long splId);

	/**
	 * Method that obtains the information for a SPL by its URL.
	 * @param url The SPL URL.
	 * @return {@link SplMonitoriza}
	 */
	SplMonitoriza getSplByUrl(String url);

	/**
	 * Method that deletes a SPL in the persistence.
	 * @param splId {@link Integer} that represents the SPL to delete.
	 */
	void deleteSplById(Long splId);

	/**
	 * Method that deletes a SPL in the persistence.
	 * @param spl {@link SplMonitoriza} that represents the spl to delete.
	 */
	void deletePlatform(SplMonitoriza spl);

	/**
	 * Method that gets all the SPLs from the persistence.
	 * @return a {@link Iterable<SplMonitoriza>} with all SPLs.
	 */
	Iterable<SplMonitoriza> getAllSpl();

	/**
	 * Method that returns a list of SPLs to be showed in DataTable.
	 * @param input DataTableInput with filtering, paging and sorting configuration.
	 * @return A set of DataTable rows that matches the query.
	 */
	DataTablesOutput<SplMonitoriza> findAll(DataTablesInput input);

	/**
	 * Method that stores SPL information in the persistence and updates corresponding scheduled timers.
	 * @param splDto a {@link SplDTO} with the information of the SPL.
	 * @return {@link SplMonitoriza} The SPL's information.
	 */
	SplMonitoriza saveSpl(SplDTO splDto);
}
