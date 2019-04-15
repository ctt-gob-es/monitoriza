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

import java.io.IOException;

import es.gob.monitoriza.persistence.configuration.dto.LogFileInfoDTO;
import es.gob.monitoriza.persistence.configuration.dto.LogFilesDTO;

/**
 * <p>Interface that provides communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring SPLs.</p>
 * @version 1.0, 20/03/2019.
 */
public interface ILogConsumerService {

	/**
	 * Method that configure the service instance.
	 * @param url The URL to the service.
	 * @param key Authentication key.
	 * @throws IOException When it is no possible connect to service.
	 */
	void configure(String url, String key) throws IOException;

	/**
	 * Method to list the log files.
	 * @return Log files.
	 */
	LogFilesDTO getLogFiles();


	/**
	 * Method that select a log file from the selected SPL.
	 * @param logFileName Name/Id from the file.
	 * @return The log file's information needed to search.
	 */
	LogFileInfoDTO openLogFile(String logFileName);
}
