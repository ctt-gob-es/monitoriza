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
 * <b>Date:</b><p>19/02/2018.</p>
 * @author Gobierno de España.
 * @version 1.8, 26/09/2023.
 */
package es.gob.monitoriza.spie.thread;

import java.security.KeyStore;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.service.impl.KeystoreService;
import es.gob.monitoriza.service.impl.NodeMonitorizaService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.status.RunningServices;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;

/** 
 * <p>Class that manages the thread pool for processing each service in a separate thread.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.8, 26/09/2023.
 */
public class RequestLauncherSpie {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_STATUS_LOG);
		
	/**
	 * Method that performs the invocation of service by service name.

	 * @param platformType Identifier of the platform type
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	public void startInvoker(final Long platformType) {
					
		Integer threads = null;
				
		try {
			threads = Integer.parseInt(StaticMonitorizaConfig.getProperty(StaticConstants.REQUEST_THREAD_POOL_SIZE));
		} catch (NumberFormatException e) {
			LOGGER.error(Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS001), e);
		}
		
		if (threads == null || threads <= 0) {
			threads = Runtime.getRuntime().availableProcessors();
		}
		
		// Se crea un pool de hilos para ejecutar las peticiones de los servicios del timer
		ExecutorService executor = Executors.newFixedThreadPool(threads);
						
		KeyStore ssl = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).loadSslTruststore();
						
		List<NodeMonitoriza> nodes = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.NODE_MONITORIZA_SERVICE, NodeMonitorizaService.class).getByPlatformType(platformType);

		// Se procesa cada nodo en un hilo del pool
		for (NodeMonitoriza node: nodes) {

			RunningServices.getInstance();

			RequestSpieThread rpt = new RequestSpieThread(node, ssl);
			executor.execute(rpt);

		}

		// Cuando se han lanzado todos los hilos, se prepara el pool para
		// que se pare cuando terminen los hilos pendientes.
		// Esto se hace porque en la siguiente ejecución del timer volverá
		// a crearse un nuevo pool.
		//shutdownAndAwaitTermination(executor, servicios.get(0));
	}
	
	/**
	 * Method for shutting down the thread pool.
	 * @param pool Thread pool for requests.
	 * @param service ServiceDTO object with the configuration of a service of current timer 
	 **/
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
