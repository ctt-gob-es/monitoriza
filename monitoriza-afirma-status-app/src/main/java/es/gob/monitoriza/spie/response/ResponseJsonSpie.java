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
 * <b>File:</b><p>es.gob.monitoriza.response.ResponseJsonMonitoriza.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 15/02/2019.
 */
package es.gob.monitoriza.spie.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO;
import es.gob.monitoriza.spie.status.StatusSpieHolder;

/** 
 * <p>Class that builds the JSON response for servlet admin calls.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 15/02/2019.
 */
public final class ResponseJsonSpie {
	
	/**
	 * Constructor method for the class ResponseJsonMonitoriza.java. 
	 */
	private ResponseJsonSpie(){
		
	}
	
	/**
	 * Method that populates the status servlet response and serializes it to JSON string.
	 * @return String that represents the status response in JSON format.
	 * @throws JsonProcessingException Error during JSON parsing
	 */
	public static String getResponseStatus() throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		StatusSpieDTO dtStatus = new StatusSpieDTO();
		List<RowStatusSpieDTO> responseList = new ArrayList<>();
		
		for (Map.Entry<String,RowStatusSpieDTO> entry : StatusSpieHolder.getInstance().getCurrentStatusHolder().entrySet()) {
										
			responseList.add(entry.getValue());
						
		}
		
		dtStatus.setData(responseList);
				
		return objectMapper.writeValueAsString(dtStatus);
		 
	}
	
}
