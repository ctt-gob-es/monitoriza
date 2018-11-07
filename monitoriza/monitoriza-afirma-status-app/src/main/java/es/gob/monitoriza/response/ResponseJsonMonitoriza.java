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
 * @version 1.1, 17/10/2018.
 */
package es.gob.monitoriza.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.gob.monitoriza.status.StatusHolder;
import es.gob.monitoriza.status.StatusUptodate;
import es.gob.monitoriza.utilidades.GeneralUtils;

/** 
 * <p>Class that builds the JSON response for servlet admin calls.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 17/10/2018.
 */
public class ResponseJsonMonitoriza {
	
	/**
	 * Constructor method for the class ResponseJsonMonitoriza.java. 
	 */
	private ResponseJsonMonitoriza(){
		
	}
	
	/**
	 * Method that populates the status servlet response and serializes it to JSON string
	 * @return String that represents the status response in JSON format.
	 * @throws JsonProcessingException
	 */
	public static String getResponseStatus() throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		DatatableStatus dtStatus = new DatatableStatus();
		List<ResponsePOJO> responseList = new ArrayList<>();
		ResponsePOJO response = null;
		StatusUptodate statusUptodate = null;
		
		for (Map.Entry<String,StatusUptodate> entry : StatusHolder.getInstance().getCurrenttatusHolder().entrySet()) {
			
			statusUptodate = entry.getValue();
			response = new ResponsePOJO();
			response.setAverageTime(statusUptodate.getAverageTime());
			response.setStatus(statusUptodate.getStatusValue());
			response.setSamplingTime(GeneralUtils.getFormattedDateTime(statusUptodate.getStatusUptodate()));
			response.setService(entry.getKey());
			response.setPartialRequestResult(statusUptodate.getPartialRequestResult());
				
			responseList.add(response);
						
		}
		
		dtStatus.setData(responseList);
				
		return objectMapper.writeValueAsString(dtStatus);
		 
	}
	
}
