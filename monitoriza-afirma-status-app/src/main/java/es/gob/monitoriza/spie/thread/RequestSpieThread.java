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
 * @version 1.0, 29/10/2018.
 */
package es.gob.monitoriza.spie.thread;

import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.Map;

import es.gob.monitoriza.configuration.manager.AdminSpieManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SpieType;
import es.gob.monitoriza.spie.html.impl.HtmlAvgResponseTimeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlEmergencyModeResolver;
import es.gob.monitoriza.spie.html.impl.HtmlHsmConnResolver;
import es.gob.monitoriza.spie.html.impl.HtmlTsaConnResolver;
import es.gob.monitoriza.spie.invoker.SpieInvoker;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 29/10/2018.
 */
public class RequestSpieThread implements Runnable {
	
	/**
	 * Attribute that represents the system trustore. 
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
		
		if (node.getCheckHsm() != null && node.getCheckHsm()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_HSM_AFIRMA);
			final String htmlHsmResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
			final HtmlHsmConnResolver hsmResult = new HtmlHsmConnResolver(spieType.getSemaphoreErrorLevel());
						
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), hsmResult.solveHtmlResult(htmlHsmResult, confSpie), null, LocalDateTime.now()));
		}
		
		if (node.getCheckEmergencyDB() != null && node.getCheckEmergencyDB()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_MODE_EMERGENCY_AFIRMA);
			final String htmlEmergencyModeResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
			final HtmlEmergencyModeResolver emergencyResult = new HtmlEmergencyModeResolver(spieType.getSemaphoreErrorLevel());
						
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), emergencyResult.solveHtmlResult(htmlEmergencyModeResult, confSpie), null, LocalDateTime.now()));
		}
		
		if (node.getCheckTsa() != null && node.getCheckTsa()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_CONN_TSA);
			final String htmlTsaResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
			final HtmlTsaConnResolver tsaResult = new HtmlTsaConnResolver(spieType.getSemaphoreErrorLevel());
						
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), tsaResult.solveHtmlResult(htmlTsaResult, confSpie), null, LocalDateTime.now()));
		}
		
		if (node.getCheckServices() != null && node.getCheckServices()) {
			spieType = adminSpieManager.getSpieTypeById(SpieType.ID_RESPONSE_TIMES);
			final String htmlAvgResult = SpieInvoker.sendRequest(spieBaseAddress, spieType.getContext(), ssl);
			final HtmlAvgResponseTimeResolver avgResult = new HtmlAvgResponseTimeResolver(spieType.getSemaphoreErrorLevel());
						
			// Se actualiza el mapa de resultados
			spieHolder.put(spieType.getIdSpieType(), new RowStatusSpieDTO(spieType.getPlatformType().getName(), node.getName(), spieBaseAddress, spieType.getTokenName(), avgResult.solveHtmlResult(htmlAvgResult, confSpie), avgResult.getDetailResults(), LocalDateTime.now()));
		}
	}
	
	/**
	 * Method that checks which SPIEs are available for this TS@ node and executes the invoker.
	 * @param spieBaseAddress Node address: protocol://ip:port
	 */
	private void checkSpieTsa(final String spieBaseAddress) {
		// TODO Auto-generated method stub
		
	}

}
