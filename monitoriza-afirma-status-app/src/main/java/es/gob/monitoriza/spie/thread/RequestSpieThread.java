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
 * <b>File:</b><p>es.gob.monitoriza.spie.thread.RequestSpieThread.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>29/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 04/01/2019.
 */
package es.gob.monitoriza.spie.thread;

import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.log4j.Logger;

import es.gob.monitoriza.configuration.manager.AdminServicesManager;
import es.gob.monitoriza.configuration.manager.AdminSpieManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SpieType;
import es.gob.monitoriza.spie.html.IHtmlSpieResolver;
import es.gob.monitoriza.spie.html.impl.HtmlAvgResponseTimeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlEmergencyModeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlHsmConnResolver;
import es.gob.monitoriza.spie.html.impl.HtmlTsaConnResolver;
import es.gob.monitoriza.spie.invoker.SpieInvoker;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;

/** 
 * <p>Class that get the results of the SPIE services configured.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 04/01/2019.
 */
public class RequestSpieThread implements Runnable {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the system truststore. 
	 */
	private static KeyStore ssl;
	
	/**
	 * Attribute that represents the node. 
	 */
	private NodeMonitoriza node;
	
	/**
	 * Attribute that represents . 
	 */
	private Map<Long, RowStatusSpieDTO> spieHolder;
	
	/**
	 * Attribute that represents the application context. 
	 */
	private AdminSpieManager adminSpieManager = ApplicationContextProvider.getApplicationContext().getBean("adminSpieManager", AdminSpieManager.class);

	/**
	 * Constructor method for the class RequestSpieThread.java.
	 * @param spieHolderParam Reference to the Map that holds the current status for the processed SPIEs. 
	 * @param nodeParam the value for {@link #nodeUrl} to set 
	 * @param sslParam the value for {@link #ssl} to set
	 * 
	 */
	public RequestSpieThread(final Map<Long, RowStatusSpieDTO> spieHolderParam, final NodeMonitoriza nodeParam, final KeyStore sslParam ) {
		super();
		this.spieHolder = spieHolderParam;
		this.node = nodeParam;
		ssl = sslParam;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
				
		// Se va a mantener map de runnig services para evitar repetir?

		// Obtención de la dirección a invocar
		final StringBuffer spieBaseAddress = new StringBuffer();
		spieBaseAddress.append(node.getIsSecure() ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP).append(GeneralConstants.COLON).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(node.getHost()).append(GeneralConstants.COLON).append(node.getPort());
		
		if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_AFIRMA)) {
			checkSpieAfirma(spieBaseAddress.toString());
		} else if (node.getNodeType().getIdPlatformType().equals(PlatformMonitoriza.ID_PLATFORM_TYPE_TSA)){
			checkSpieTsa(spieBaseAddress.toString());
		}
				
	}
	
	/**
	 * Method that checks which SPIEs are available for this @Firma node and executes the invoker.
	 * @param spieBaseAddress Node address: protocol://ip:port
	 */
	private void checkSpieAfirma(final String spieBaseAddress) {
		
		final ConfSpieDTO confSpie = adminSpieManager.getSpieConfiguration();
		SpieType spieType = null;
		RowStatusSpieDTO status = null;
		String htmlInvokerResult = null;
		IHtmlSpieResolver resolver = null;
		
		try {
			
		
    		if (node.getCheckHsm() != null && node.getCheckHsm()) {
    			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
    			resolver = new HtmlHsmConnResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    		}
    		
    		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
    			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
    			resolver = new HtmlEmergencyModeResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    		}
    		
    		if (node.getCheckTsa() != null && node.getCheckTsa()) {
    			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_TSA);
    			resolver = new HtmlTsaConnResolver(spieType.getSemaphoreErrorLevel());
    			htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
        		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
        		// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    		}
    		
    		htmlInvokerResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlInvokerResult, confSpie), null, LocalDateTime.now());
    		
    		if (node.getCheckServices() != null && node.getCheckServices()) {
    			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
    			final String htmlAvgResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
    			HtmlAvgResponseTimeResolver avgResolver = new HtmlAvgResponseTimeResolver(spieType.getSemaphoreErrorLevel());
    			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), resolver.solveHtmlResult(htmlAvgResult, confSpie), avgResolver.getDetailResults(), LocalDateTime.now());
    			// Se actualiza el mapa de resultados
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
    			
    		}
    		
		} catch (InvokerException ie) {
			
			LOGGER.error(ie.getMessage(), ie.getCause());
						
		} finally {
			
			// Si el estado es null, no se ha podido obtener respuesta del nodo.
			// Se establece el estado de todos los SPIE configurados como "Sin acceso al nodo". 
			if (status == null) {
				LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS015, new Object[]{node.getName()}));
				setAndPersistAllStatusError(spieBaseAddress);
				
			} else {
				 
    			spieHolder.put(spieType.getIdSpieType(), status);
    			// Se persiste el resultado
    			saveDailySpieMonitoring(spieType.getTokenName(), status);
			}
		}
		
	}
	
	/**
	 * Method that checks which SPIEs are available for this TS@ node and executes the invoker.
	 * @param spieBaseAddress Node address: protocol://ip:port
	 */
	private void checkSpieTsa(final String spieBaseAddress) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Saves the SPIE status sample in persistence.
	 * @param service SPIE service type
	 * @param status Object that represents the status information of the SPIE
	 */
	private void saveDailySpieMonitoring(String service, RowStatusSpieDTO status) {
		
		DailySpieMonitorig daily = new DailySpieMonitorig();
		
		daily.setPlatform(status.getSystem());
		daily.setSamplingTime(status.getStatusUptodate());
		daily.setService(service);
		daily.setStatus(getSemaphoreNameById(status.getStatusValue()));
		daily.setNode(status.getNodeName());
		
		AdminServicesManager adminServicesManager = ApplicationContextProvider.getApplicationContext().getBean("adminServicesManager", AdminServicesManager.class);	
		
		adminServicesManager.saveDailySpie(daily);
	}
	
	/**
	 * Gets the semaphore name value by its identifier.
	 * @param id Semaphore identifier
	 * @return Semaphore name value
	 */
	private String getSemaphoreNameById(Integer id) {
		
		String name = null;
		
		if (id != null) {
    		switch (id) {
    			case 0:
    				name = SemaphoreEnum.GREEN.getName();
    				break;
    			case 1:
    				name = SemaphoreEnum.AMBER.getName();
    				break;
    			case 2:
    				name = SemaphoreEnum.RED.getName();
    				break;
    			default:
    				name = SemaphoreEnum.RED.getName();
    				break;
    		} 
				
		} else {
			name = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS017, new Object[]{node.getName()});
		}

		return name;
	}
	
	
	/**
	 * 
	 * @param spieBaseAddress
	 */
	private void setAndPersistAllStatusError(final String spieBaseAddress) {
		
		SpieType spieType = null;
		RowStatusSpieDTO status = null;
				
		if (node.getCheckHsm() != null && node.getCheckHsm()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
		
		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
		
		if (node.getCheckTsa() != null && node.getCheckTsa()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_TSA);
    		status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
    		// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
		}
						
		if (node.getCheckServices() != null && node.getCheckServices()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
			status = new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), null, null, LocalDateTime.now());
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), status);
			// Se persiste el resultado
			saveDailySpieMonitoring(spieType.getTokenName(), status);
			
		}
		
	}

}
