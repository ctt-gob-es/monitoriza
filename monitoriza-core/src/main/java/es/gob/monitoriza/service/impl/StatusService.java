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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.StatusService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ServiceMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 30/10/2018.
 */
package es.gob.monitoriza.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.StatusVipResponseException;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusDTO;
import es.gob.monitoriza.service.IStatusService;
import es.gob.monitoriza.utilidades.NumberConstants;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/**
 * <p>Class that implements the communication with the status servlet.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * 
 * @version 1.3, 30/10/2018.
 */
@Service
public class StatusService implements IStatusService {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IStatusService#completeStatus()
	 */
	@Override
	public StatusDTO completeStatus() {
				
		StatusDTO status = new StatusDTO();
		
		Type listType = new TypeToken<StatusDTO>() {}.getType();
				
		String jsonFromServlet;
		
		try {
			jsonFromServlet = getRequestFromStatusServlet();
			status = new Gson().fromJson(jsonFromServlet, listType);
		} catch (StatusVipResponseException svre) {
			status.setError(svre.getMessage());
		} catch (JsonSyntaxException e) {
			String errorJson = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB018);
			LOGGER.error(errorJson);
			status.setError(errorJson);
			
		}				

		status.setData(checkStatus(status.getData()));

		return status;
	}

	/**
	 * Method that process the servlet response, setting readable values.
	 * @param status List of status per service
	 * @return List<RowStatusDTO>
	 */
	private List<RowStatusDTO> checkStatus(List<RowStatusDTO> status) {
		for (RowStatusDTO s : status) {
			switch (s.getStatus()) {
			case "Correcto":
				s.setStatusAux(0L);
				break;
			case "Caido":
				s.setStatusAux(2L);
				break;
			case "Degradado":
				s.setStatusAux(1L);
				break;
			default:
				s.setStatusAux(2L);
			}
			
			if (s.getAverageTime() == null) {
				s.setAverageTime("timeout");
			}
		}
		return status;
	}
	
	/**
	 * Method that calls the status servlet.
	 * @return String that represents the status information in JSON format
	 * @throws StatusVipResponseException 
	 */
	private String getRequestFromStatusServlet() throws StatusVipResponseException {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(StaticMonitorizaProperties.getProperty(StaticConstants.MONITORIZA_VIP_STATUS_SERVLET));
		String result = null;
				
		//Execute and get the response.
		HttpResponse response = null;
		HttpEntity entity = null;
		
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			
			if (entity != null) {
			    try (InputStream instream = entity.getContent();) {
			    	result = new BufferedReader(new InputStreamReader(instream))
			    			  .lines().collect(Collectors.joining());
			    } 
			}
						
			if (response.getStatusLine().getStatusCode() / NumberConstants.NUM100 != 2) {
				String error = Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB017, new Object[]{response.getStatusLine().getStatusCode()});
				LOGGER.error(error);
				throw new StatusVipResponseException(error);
			}
						
		} catch (IOException e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB016, new Object[]{ httpget.getURI()}), e);
		}
		
		return result;
		
	}

}
