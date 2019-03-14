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
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 2.0, 14/03/2019.
 */
package es.gob.monitoriza.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.exception.StatusSpieResponseException;
import es.gob.monitoriza.exception.StatusVipResponseException;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.IWebViewMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.AvgTimesServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusVipDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.StatusVipDTO;
import es.gob.monitoriza.persistence.configuration.dto.SummaryStatusDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.MaintenanceService;
import es.gob.monitoriza.persistence.configuration.model.utils.IStatusAdapter;
import es.gob.monitoriza.service.IMaintenanceServiceService;
import es.gob.monitoriza.service.IStatusService;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;

/**
 * <p>Class that implements the communication with the status servlet.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * 
 * @version 1.9, 05/03/2019.
 */
@Service("statusService")
public class StatusService implements IStatusService {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_WEB_LOG);
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private MessageSource message;
	
	/**
	 * Attribute that represents the service object for accessing the
	 * repository.
	 */
	@Autowired
	private IMaintenanceServiceService maintenanceService;
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IStatusService#completeStatus()
	 */
	@Override
	public StatusVipDTO completeStatusVip() {
				
		StatusVipDTO statusVip = new StatusVipDTO();
				
		Type listType = new TypeToken<StatusVipDTO>() {}.getType();
				
		String jsonFromVip;
				
		try {
			jsonFromVip = getRequestFromStatusVip();
			statusVip = new Gson().fromJson(jsonFromVip, listType);			
			
			
		} catch (StatusVipResponseException svre) {
			statusVip.setError(svre.getMessage());
		} catch (JsonSyntaxException e) {
			String errorJson = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB018);
			LOGGER.error(errorJson);
			statusVip.setError(errorJson);
		}					

		cleanDeletedVipServices(statusVip.getData());
		statusVip.setData(checkStatusVip(statusVip.getData()));

		return statusVip;
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IStatusService#completeStatus()
	 */
	@Override
	public StatusSpieDTO completeStatusSpie() {
				
		StatusSpieDTO statusSpie = new StatusSpieDTO();
		
		Type listType = new TypeToken<StatusSpieDTO>() {}.getType();
				
		String jsonFromSpie;
		
		try {
			jsonFromSpie = getRequestFromStatusSpie();
			statusSpie = new Gson().fromJson(jsonFromSpie, listType);
						
		} catch (StatusSpieResponseException ssre) {
			statusSpie.setError(ssre.getMessage());
		} catch (JsonSyntaxException e) {
			String errorJson = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB020);
			LOGGER.error(errorJson);
			statusSpie.setError(errorJson);
		}		
		
		cleanDeletedSpieServices(statusSpie.getData());
		statusSpie.setData(checkStatusSpie(statusSpie.getData()));

		return statusSpie;
	}

	/**
	 * Method that calls the status VIP.
	 * @return String that represents the status information in JSON format
	 * @throws StatusVipResponseException 
	 */
	private String getRequestFromStatusVip() throws StatusVipResponseException {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(StaticMonitorizaConfig.getProperty(StaticConstants.MONITORIZA_VIP_STATUS_SERVLET));
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
	
	/**
	 * Method that calls the status SPIE.
	 * @return String that represents the status information in JSON format
	 * @throws StatusSpieResponseException 
	 */
	private String getRequestFromStatusSpie() throws StatusSpieResponseException {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(StaticMonitorizaConfig.getProperty(StaticConstants.MONITORIZA_SPIE_STATUS_SERVLET));
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
				String error = Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB019, new Object[]{response.getStatusLine().getStatusCode()});
				LOGGER.error(error);
				throw new StatusSpieResponseException(error);
			}
						
		} catch (IOException e) {
			LOGGER.error(Language.getFormatResWebMonitoriza(IWebLogMessages.ERRORWEB016, new Object[]{ httpget.getURI()}), e);
		}
		
		return result;
		
	}
		
	/**
	 * Method that process the VIP status response, setting readable values.
	 * @param status List of status per service
	 * @return List<RowStatusVipDTO>
	 */
	private List<RowStatusVipDTO> checkStatusVip(List<RowStatusVipDTO> status) {

		MaintenanceService maintenance = null;

		for (RowStatusVipDTO s: status) {

			maintenance = maintenanceService.getMaintenanceServiceByService(s.getService());

			if (maintenance != null && maintenance.getIsInMaintenance()) {

				s.setStatusAux(SemaphoreEnum.BLUE.getId());
				
			} else if (maintenance != null) {

				s.setStatusAux(IStatusAdapter.vipToSemaphoreStatus(s.getStatus()));
			}

			if (s.getAverageTime() == null) {
				s.setAverageTime("timeout");
			}
		}
		return status;
	}
	
	/**
	 * Remove the deleted VIP services from the semaphore list.
	 * @param status {@link List<RowStatusVipDTO>}
	 */
	private void cleanDeletedVipServices(List<RowStatusVipDTO> status) {
		
		MaintenanceService maintenance = null;
		
		for (Iterator<RowStatusVipDTO> itStatus = status.iterator(); itStatus.hasNext();) {
			
			maintenance = maintenanceService.getMaintenanceServiceByService(itStatus.next().getService());
			
			if (maintenance == null) {
				itStatus.remove();
			}
			
		}
		
	}
	
	/**
	 * Remove the deleted SPIE services from the semaphore list.
	 * @param status {@link List<RowStatusSpieDTO>}
	 */
	private void cleanDeletedSpieServices(List<RowStatusSpieDTO> status) {
		
		MaintenanceService maintenance = null;
		
		Iterator<RowStatusSpieDTO> itStatus = status.iterator();
		String node = null;
		String spieType = null;
		RowStatusSpieDTO rowSpie = null;
		
		while (itStatus.hasNext()) {
			
			rowSpie = itStatus.next();
			node = rowSpie.getNodeName();
			spieType = rowSpie.getSpieService();
			
			maintenance = maintenanceService.getMaintenanceServiceByService(IStatusService.getUniqueNameStatusSpie(node, spieType));
			
			if (maintenance == null) {
				itStatus.remove();
			}
			
		}
		
	}
	
	/**
	 * Method that process the SPIE response, setting the maintenance values.
	 * @param status List of status per service
	 * @return List<RowStatusSpieDTO>
	 */
	private List<RowStatusSpieDTO> checkStatusSpie(List<RowStatusSpieDTO> status) {

		MaintenanceService maintenance = null;

		for (RowStatusSpieDTO s: status) {

			maintenance = maintenanceService.getMaintenanceServiceByService(IStatusService.getUniqueNameStatusSpie(s.getNodeName(), s.getSpieService()));

			if (maintenance != null && maintenance.getIsInMaintenance()) {

				s.setStatusValue(SemaphoreEnum.BLUE.getId());
			} 
		}
		
		return status;
	}
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IStatusService#getSpieAvgTimesDetails(java.util.List)
	 */
	@Override
	public List<AvgTimesServiceDTO> getSpieAvgTimesDetails(final List<RowStatusSpieDTO> spieStatus) {
		
		List<AvgTimesServiceDTO> avgDetails = new ArrayList<AvgTimesServiceDTO>();
		
		for (RowStatusSpieDTO s : spieStatus) {
			
			if (s.getPartialRequestResult() != null && !s.getPartialRequestResult().isEmpty()) {
				avgDetails = s.getPartialRequestResult();
			}
			
		}
		
		return avgDetails;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IStatusService#getSummaryStatus(es.gob.monitoriza.persistence.configuration.dto.StatusVipDTO, es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO)
	 */
	@Override
	public List<SummaryStatusDTO> getSummaryStatus(final StatusVipDTO statusVip, final StatusSpieDTO statusSpie, final Locale locale) {
		
		List<SummaryStatusDTO> summaryList = new ArrayList<>();
		SummaryStatusDTO summary = null;
		Integer semaphVip = null;
		String description = null;
		
		String uniqueName = null;
		MaintenanceService maintenance = null;
		
		for (RowStatusVipDTO vip : statusVip.getData()) {
			
			maintenance = maintenanceService.getMaintenanceServiceByService(vip.getService());
			semaphVip = SemaphoreEnum.GREEN.getId();
						
			switch (vip.getStatus()) {
				case ServiceStatusConstants.CAIDO:
					semaphVip = SemaphoreEnum.RED.getId();
					description = message.getMessage(IWebViewMessages.ERROR_VIP_SERVICE_DOWN, new Object[]{vip.getService()}, locale);
					break;
				case ServiceStatusConstants.DEGRADADO:
					semaphVip = SemaphoreEnum.AMBER.getId();
					description = message.getMessage(IWebViewMessages.ERROR_VIP_SERVICE_DEGRADED, new Object[]{vip.getService()}, locale);
					break;
				default:
					break;
				}
			
			if (!SemaphoreEnum.GREEN.getId().equals(semaphVip) && maintenance != null && !maintenance.getIsInMaintenance()) {
				summary = new SummaryStatusDTO(semaphVip, "VIP", description);
				summaryList.add(summary);
			}
		}
		
		Integer semaphSpie = null;
		
		for (RowStatusSpieDTO spie : statusSpie.getData()) {
			
			uniqueName = IStatusService.getUniqueNameStatusSpie(spie.getNodeName(), spie.getSpieService());
			maintenance = maintenanceService.getMaintenanceServiceByService(uniqueName);
			
			if (!SemaphoreEnum.GREEN.getId().equals(spie.getStatusValue()) && maintenance != null && !maintenance.getIsInMaintenance() && !SemaphoreEnum.BLUE.getId().equals(spie.getStatusValue())) {
				semaphSpie = spie.getStatusValue();
				
				description = message.getMessage(IWebViewMessages.ERROR_SPIE_SERVICE, new Object[]{spie.getSpieService(), spie.getNodeName()}, locale);
							
				summary = new SummaryStatusDTO(semaphSpie, "SPIE", description);
				summaryList.add(summary);
				
			}
		}
			
		return summaryList;
	}
		
}
