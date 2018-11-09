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
 * <b>File:</b><p>es.gob.monitoriza.service.IStatusService.java.</p>
 * <b>Description:</b><p>Interface that provides communication with the operations of the persistence layer.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 09/11/2018.
 */
package es.gob.monitoriza.service;

import java.util.List;
import java.util.Locale;

import es.gob.monitoriza.persistence.configuration.dto.AvgTimesServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusVipDTO;
import es.gob.monitoriza.persistence.configuration.dto.SummaryStatusDTO;

/**
 * <p>Interface that provides communication with the status servlet.
 * </p><b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * 
 * @version 1.2, 09/11/2018.
 */
public interface IStatusService {

	/**
	 * Method that get the service status from the status servlet.
	 * @return StatusVipDTO
	 */
	StatusVipDTO completeStatusVip();
	
	/**
	 * Method that get the service status from the status servlet.
	 * @return StatusSpieDTO
	 */
	StatusSpieDTO completeStatusSpie();
	
	/**
	 * Method that gets the response details from average times SPIE.
	 * @param spieStatus List<RowStatusSpieDTO> of SPIE status results
	 * @return List<AvgTimesServiceDTO> corresponding to the details of average service times SPIE
	 */
	List<AvgTimesServiceDTO> getSpieAvgTimesDetails(List<RowStatusSpieDTO> spieStatus);
	
	/**
	 * Method that gets the summary information of the status alerts from both VIP and SPIE.
	 * @param statusVip {@link #StatusVipDTO} with VIP alerts
	 * @param statusSpie {@link #StatusSpieDTO} with VIP alerts
	 * @param locale Request locale
	 * @return {@link #List<SummaryStatusDTO>} with summary alerts
	 */
	List<SummaryStatusDTO> getSummaryStatus(StatusVipDTO statusVip, StatusSpieDTO statusSpie, Locale locale);

}
