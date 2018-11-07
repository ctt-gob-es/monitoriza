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
 * <b>File:</b><p>es.gob.monitoriza.service.IServiceMonitorizaService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 28/10/2018.
 */
package es.gob.monitoriza.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import es.gob.monitoriza.persistence.configuration.dto.AlarmDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;

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
 * @version 1.2, 28/10/2018.
 */
public interface IAlarmMonitorizaService {

	/**
	 * Method that obtains the configuration for mail by its identifier.
	 * 
	 * @param alarmId Identifier of the alarm.
	 * @return {@link AlarmMonitoriza}
	 */
	AlarmMonitoriza getAlarmMonitorizaById(Long alarmId);

	/**
	 * Method that stores a timer in the persistence.
	 * 
	 * @param alarm a {@link AlarmDTO} with the information of the service.
	 * @return {@link AlarmMonitoriza} The service.
	 */
	AlarmMonitoriza saveAlarmMonitoriza(AlarmDTO alarmDto);

	/**
	 * Method that deletes a alarm in the persistence.
	 * 
	 * @param alarmId {@link Long} that represents the user identifier to delete.
	 */
	void deleteAlarmMonitoriza(Long alarmId);

	/**
	 * Method that gets all the alarms from the persistence.
	 * 
	 * @return a {@link Iterable<AlarmMonitoriza>} with the information of all
	 *         services.
	 */
	Iterable<AlarmMonitoriza> getAllAlarmMonitoriza();
	
	/**
	 * Method that gets all the timer from the persistence.
	 * @param mail {@link MailMonitoriza} 
	 * @return a {@link Iterable<AlarmMonitoriza>} with the information of all
	 *         services.
	 */
	Iterable<AlarmMonitoriza> getAllAlarmMonitorizaByMail(MailMonitoriza mail);
	
	/**
	 * Method that finds all alarms in the persistence.
	 * @param input DataTablesInput with datatable configuration
	 * @return DataTablesOutput<AlarmMonitoriza>
	 */ 
	DataTablesOutput<AlarmMonitoriza> findAll(DataTablesInput input);

}
