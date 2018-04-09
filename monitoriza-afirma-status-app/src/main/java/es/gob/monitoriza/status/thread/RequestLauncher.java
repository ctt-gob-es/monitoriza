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
 * <b>File:</b><p>es.gob.monitoriza.status.thread.RequestLauncherThread.java.</p>
 * <b>Description:</b>
 * <p>Class that manages the thread pool for processing each service in a separate thread.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>19 feb. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 19 feb. 2018.
 */
package es.gob.monitoriza.status.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.status.RunningServices;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the thread pool for processing each service in a separate thread.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 19 feb. 2018.
 */
public class RequestLauncher {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the path where the pairs are stored.
	 */
	private static String requestDirectory = StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);

	/**
	 * Method that performs the invocation of service by service name.
	 * 
	 * @param rootPath initial path where is store every petition.
	 * @param responsesDir Path where the responses will be stored.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	public void startInvoker(final Map<String, String> statusHolder, final List<ServiceDTO> servicios) {

		LOGGER.info(Language.getFormatResMonitoriza(LogMessages.PATH_DIRECTORY_REQUESTS, new Object[ ] { requestDirectory }));
				
		Integer threads = null;
		
		try {
			threads = Integer.parseInt(StaticMonitorizaProperties.getProperty(StaticConstants.REQUEST_THREAD_POOL_SIZE));
		} catch (NumberFormatException e) {
			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.ERROR_FORMAT_THREAD_POOL_SIZE, new Object[ ] { requestDirectory }));
		}
		
		if (threads == null || threads <= 0) {
			threads = Runtime.getRuntime().availableProcessors();
		}
								
		ExecutorService executor = Executors.newFixedThreadPool(threads);

		// Recorremos los subdirectorios que separan las peticiones por
		// servicio.
		for (ServiceDTO s: servicios) {

			RunningServices.getInstance();
			
			if (RunningServices.getRequestsRunning().get(s.getServiceId()) == null || !RunningServices.getRequestsRunning().get(s.getServiceId())) {
    			RequestProcessorThread rpt = new RequestProcessorThread(s, statusHolder);
    
    			executor.execute(rpt);
			} 			
			
		}

		executor.shutdown();
	}

}
