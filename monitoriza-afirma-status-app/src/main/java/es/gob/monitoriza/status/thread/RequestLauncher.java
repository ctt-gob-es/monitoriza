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
 * @version 1.3, 28/10/2018.
 */
package es.gob.monitoriza.status.thread;

import java.security.KeyStore;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.status.RunningServices;
import es.gob.monitoriza.status.StatusUptodate;
import es.gob.monitoriza.utilidades.NumberConstants;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the thread pool for processing each service in a separate thread.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 28/10/2018.
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
	 * @param statusHolder Reference to the Map that holds the current status for the processed services. 
	 * @param servicios DTOService that represents the service being processed in this thread.
	 * @param sslTrustStore Truststore of Monitoriz@
	 * @param rfc3161Keystore Keystore for authenticating RFC3161 service
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	public void startInvoker(final Map<String, StatusUptodate> statusHolder, final List<ConfigServiceDTO> servicios, final KeyStore sslTrustStore, final KeyStore rfc3161Keystore) {

		LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS002, new Object[ ] { requestDirectory }));
				
		Integer threads = null;
				
		try {
			threads = Integer.parseInt(StaticMonitorizaProperties.getProperty(StaticConstants.REQUEST_THREAD_POOL_SIZE));
		} catch (NumberFormatException e) {
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS001, new Object[ ] { requestDirectory }), e);
		}
		
		if (threads == null || threads <= 0) {
			threads = Runtime.getRuntime().availableProcessors();
		}
		
		// Se crea un pool de hilos para ejecutar las peticiones de los servicios del timer
		ExecutorService executor = Executors.newFixedThreadPool(threads);

		// Se procesa cada servicio en un hilo del pool
		for (ConfigServiceDTO s: servicios) {

			RunningServices.getInstance();
			
			if (RunningServices.getRequestsRunning().get(s.getServiceName()) == null || !RunningServices.getRequestsRunning().get(s.getServiceName())) {
    			RequestProcessorThread rpt = new RequestProcessorThread(s, statusHolder, sslTrustStore, rfc3161Keystore);
    
    			executor.execute(rpt);
			} 			
			
		}

		// Cuando se han lanzado todos los hilos, se prepara el pool para
		// que se pare cuando terminen los hilos pendientes.
		// Esto se hace porque en la siguiente ejecución del timer volverá
		// a crearse un nuevo pool.
		shutdownAndAwaitTermination(executor, servicios.get(0));
	}
	
	/**
	 * Method for shutting down the thread pool.
	 * @param pool Thread pool for requests.
	 * @param service ServiceDTO object with the configuration of a service of current timer 
	 */
	private void shutdownAndAwaitTermination(final ExecutorService pool, final ConfigServiceDTO service) {
		
		// Se impide el lanzamiento de nuevas tareas en este pool
	    pool.shutdown(); 
	    try {
	        // Se espera la terminación de hilos actuales
	        if (!pool.awaitTermination(NumberConstants.NUM60, TimeUnit.SECONDS)) {
	        	// Cancelación de hilos en ejecución para el pool
	            pool.shutdownNow(); 
	            // Se espera a la obtención de respuesta de cancelación de los hilos pendientes
	            if (!pool.awaitTermination(NumberConstants.NUM60, TimeUnit.SECONDS)) {
	                LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS015, new Object[]{service.getTimerName()}));
	            }
	        }
	    } catch (InterruptedException ie) {
	        // Si el hilo actual se cancela, se cancelan también el pool
	        pool.shutdownNow();
	       
	    }
	}
		
}
