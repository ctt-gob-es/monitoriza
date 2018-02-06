/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.status.RequestProcessor.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that gets the average response times for @firma services.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 21/12/2017.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 15/01/2018.
 */
package es.gob.monitoriza.status;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.dto.DTOService;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.invoker.ocps.OcspInvoker;
import es.gob.monitoriza.invoker.rfc3161.Rfc3161Invoker;
import es.gob.monitoriza.invoker.soap.HttpSoapInvoker;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that gets the average response times for @firma services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 15/01/2018.
 */
public final class RequestProcessor {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents a instance of this class. 
	 */
	private static RequestProcessor instance;

	/**
	 * Attribute that represents the path where the pairs are stored.
	 */
	private static String requestDirectory = StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);
	
	private static Map<String,Boolean> requestsRunning = new HashMap<String, Boolean>();

	/**
	 * Private constructor method for the class RequestProcessor.java. 
	 */
	private RequestProcessor() {

	}

	/**
	 * Gets an instance of the class.
	 * @return	A {@link RequestProcessor} object.
	 */
	public static synchronized RequestProcessor getInstance() {

		if (instance == null) {
			instance = new RequestProcessor();
		}

		return instance;
	}

	/**
	 * Method that performs the invocation of service by service name.
	 * 
	 * @param rootPath initial path where is store every petition.
	 * @param responsesDir Path where the responses will be stored.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	public void startInvoker(Map<String, String> statusHolder, List<DTOService> servicios) throws InvokerException {

		LOGGER.info(Language.getFormatResMonitoriza(LogMessages.PATH_DIRECTORY_REQUESTS, new Object[ ] { requestDirectory }));
		// Recorremos los subdirectorios que separan las peticiones por
		// servicio.
		for (DTOService s: servicios) {

			if (requestsRunning.get(s.getServiceId()) == null || !requestsRunning.get(s.getServiceId())) {	
				processRequests(s, statusHolder);
			}
		}
	}

	/**
	 * Method that launch requests for a service and process the status. 
	 * 
	 * @param service Directory where search requests.
	 * @param responsesDir Directory where store the responses.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	private void processRequests(DTOService service, Map<String, String> statusHolder) throws InvokerException {
		
		requestsRunning.put(service.getServiceId(), Boolean.TRUE);

		Long tiempoTotal = null;
		Long tiempoMedio = null;
		Integer perdidas = null;
		int totalRequests = 0;
		int totalRequestsLost = 0;
		Long totalTimes = 0L;
		boolean necesarioConfirmar = Boolean.TRUE;
		File grupoAProcesar = null;
		int groupIndex = 1;

		File serviceDir = new File(service.getDirectoryPath());
		// Comprobamos que la ruta proporcioanda es correcta.
		// Si existe un directorio con el nombre del servicio, continúo...
		if (serviceDir != null && serviceDir.exists() && serviceDir.isDirectory()) {

			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.STARTING_TO_SEND_STORED_REQUESTS, new Object[ ] { serviceDir.toPath().toString(), service.getWsdl() }));

			// Enviamos las peticiones del grupo principal
			grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaProperties.getProperty(StaticConstants.GRUPO_PRINCIPAL_PATH_DIRECTORY)));

			// Se procesa el grupo...
			if (grupoAProcesar != null && grupoAProcesar.exists() && grupoAProcesar.listFiles() != null && necesarioConfirmar) {

				for (File request: grupoAProcesar.listFiles()) {
					// Vamos recorriendo los ficheros, y si es una petición,
					// la enviamos a @Firma o TS@
					if (request != null) {

						LOGGER.info(Language.getFormatResMonitoriza(LogMessages.SENDING_REQUEST, new Object[ ] { request.getName() }));

						if (service.getServiceId().equals(GeneralConstants.OCSP_SERVICE)) {
							tiempoTotal = OcspInvoker.sendRequest(request, service);
						} else if (service.getServiceId().equals(GeneralConstants.RFC3161_SERVICE)){
							tiempoTotal = Rfc3161Invoker.sendRequest(request, service);
						} else {
							tiempoTotal = HttpSoapInvoker.sendRequest(request, service);
						} 

						totalRequests++;

						// Si no ha podido calcularse el tiempo de la request o
						// se considera perdida,
						// se aumenta el nº de requests perdidas.
						if (isServiceRequestLost(service, tiempoTotal)) {
							totalRequestsLost++;
							// En otro caso, se añade el tiempo de la request al
							// total para hacer la
							// media a posteriori.
						} else {
							totalTimes += tiempoTotal;
						}
					}

				}

				// Calcular el tiempo medio acumulado si hay algún tiempo
				// disponible...
				if (totalRequests != totalRequestsLost) {
					tiempoMedio = tiempoTotal / (totalRequests - totalRequestsLost);
				}
				// Calcular % de perdidas para el grupo principal
				perdidas = Math.round((totalRequestsLost / totalRequests) * 100);

				// Si se cumplen las condiciones, se obtiene el posible próximo
				// grupo de confirmación...
				if (perdidas > Integer.parseInt(service.getLostThreshold()) || tiempoMedio > service.getTimeout()) {
					necesarioConfirmar = Boolean.TRUE;
					grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaProperties.getProperty(StaticConstants.GRUPO_CONFIRMACION_PATH_DIRECTORY)) + groupIndex);
					groupIndex++;
					
					// TODO Al ser necesario procesar el siguiente grupo de confirmación,
					// dormimos el hilo para simular la espera...
					try {
						Thread.sleep(Long.parseLong(StaticMonitorizaProperties.getProperty(StaticConstants.CONFIRMATION_WAIT_TIME)));
					} catch (InterruptedException e) {
						LOGGER.info(Language.getFormatResMonitoriza(LogMessages.CONFIRMATION_WAIT_INTERRUPTED, new Object[ ] { service.getServiceId() }));
					}
				} else {
					necesarioConfirmar = Boolean.FALSE;
				}

			}

			// Si se ha obtenido una respuesta definitiva (no perdida) o no hay
			// más grupos de confirmación,
			// pasamos a calcular el estado del servicio con los datos
			// obtenidos.
			statusHolder.put(service.getServiceId(), calcularEstadoDelServicio(service, tiempoMedio));

		}
		
		requestsRunning.put(service.getServiceId(), Boolean.FALSE);

	}

	/**
	 * 
	 * @param serviceName
	 * @param tiempoTotal
	 * @return
	 */
	private String calcularEstadoDelServicio(final DTOService service, final Long tiempoMedio) {

		String estado = null;
		
		if (tiempoMedio != null && tiempoMedio <= service.getDegradedThreshold()) {
			estado = ServiceStatusConstants.CORRECTO;
		} else if (tiempoMedio != null && tiempoMedio > service.getDegradedThreshold()) {
			estado = ServiceStatusConstants.DEGRADADO;
		} else {
			estado = ServiceStatusConstants.CAIDO;
		}
		
		return estado;
	}

	/**
	 * 
	 * @param service
	 * @param tiempoTotal
	 * @return
	 */
	private boolean isServiceRequestLost(final DTOService service, final Long tiempoTotal) {
		
		boolean resultado = Boolean.FALSE;
		
		if (tiempoTotal == null || tiempoTotal > service.getTimeout()) {
			resultado = Boolean.TRUE;
		}
		
		return resultado;
	}
	
	

}
