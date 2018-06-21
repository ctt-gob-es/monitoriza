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
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.status.thread.RequestProcessorThread.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that performs the calculations to get the service status executing the requests in a new thread.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 19/02/2018.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 19/02/2018.
 */
package es.gob.monitoriza.status.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.log4j.Logger;

import es.gob.monitoriza.alarm.AlarmManager;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.invoker.ocsp.OcspInvoker;
import es.gob.monitoriza.invoker.rfc3161.Rfc3161Invoker;
import es.gob.monitoriza.invoker.soap.HttpSoapInvoker;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.status.RunningServices;
import es.gob.monitoriza.status.StatusUptodate;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that performs the calculations to get the service status executing the requests in a new thread.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 19/02/2018.
 */
/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 27 abr. 2018.
 */
/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 27 abr. 2018.
 */
public final class RequestProcessorThread implements Runnable {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the Object that holds the configuration for the service being processed in this thread. 
	 */
	private ServiceDTO service;

	/**
	 * Attribute that represents the reference to the Map where the status of each service are stored. 
	 */
	private Map<String, StatusUptodate> statusHolder;
		
	/**
	 * Attribute that represents . 
	 */
	private static KeyStore ssl = loadSslTruststore();

	/**
	 * Private constructor method for the class RequestProcessor.java. 
	 * @param service DTOService that represents the service being processed in this thread.
	 * @param statusHolder Reference to the Map that holds the current status for the processed services. 
	 */
	public RequestProcessorThread(final ServiceDTO service, final Map<String, StatusUptodate> statusHolder) {

		this.service = service;
		this.statusHolder = statusHolder;
	}

	/**
	 * Method that launch requests for a service and process the status. 
	 */
	@Override
	public void run() {
		
		RunningServices.getInstance();
		RunningServices.getRequestsRunning().put(service.getServiceId(), Boolean.TRUE);

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

			try {

				LOGGER.info(Language.getFormatResMonitoriza(LogMessages.STARTING_TO_SEND_STORED_REQUESTS, new Object[ ] { serviceDir.toPath().toString(), service.getWsdl() }));

				// Enviamos las peticiones del grupo principal
				grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaProperties.getProperty(StaticConstants.GRUPO_PRINCIPAL_PATH_DIRECTORY)));

				do {

					// Se procesa el grupo...
					if (grupoAProcesar != null && grupoAProcesar.exists() && grupoAProcesar.listFiles() != null) {

						for (File request: grupoAProcesar.listFiles()) {
							// Vamos recorriendo los ficheros, y si es una
							// petición, la enviamos a @Firma o TS@
							if (request != null) {

								LOGGER.info(Language.getFormatResMonitoriza(LogMessages.SENDING_REQUEST, new Object[ ] { request.getName() }));

								if (service.getServiceId().equals(GeneralConstants.OCSP_SERVICE)) {
									tiempoTotal = OcspInvoker.sendRequest(request, service, ssl);
								} else if (service.getServiceId().equals(GeneralConstants.RFC3161_SERVICE)) {
									tiempoTotal = Rfc3161Invoker.sendRequest(request, service, ssl);
								} else {
									tiempoTotal = HttpSoapInvoker.sendRequest(request, service, ssl);
								}

								totalRequests++;

								// Si no ha podido calcularse el tiempo de la
								// request o
								// se considera perdida,
								// se aumenta el nº de requests perdidas.
								if (isServiceRequestLost(service, tiempoTotal)) {
									totalRequestsLost++;
									// En otro caso, se añade el tiempo de la
									// request al
									// total para hacer la media a posteriori.
								} else {
									totalTimes += tiempoTotal;
								}
							}

						}

						// Calcular el tiempo medio acumulado si hay algún
						// tiempo disponible...
						if (totalRequests != totalRequestsLost) {
							tiempoMedio = totalTimes / (totalRequests - totalRequestsLost);
						}
						// Calcular % de perdidas para el grupo principal
						perdidas = Math.round((float) (totalRequestsLost / totalRequests) * 100.0f);

						// Si se cumplen las condiciones, se obtiene el posible
						// próximo grupo de confirmación...
						if (perdidas > Integer.parseInt(service.getLostThreshold()) || tiempoMedio > service.getDegradedThreshold()) {
							LOGGER.info(Language.getFormatResMonitoriza(LogMessages.CONFIRMATION_REQUIRED, new Object[ ] { service.getWsdl(), perdidas, tiempoMedio == null ? "N/A": tiempoMedio }));
							necesarioConfirmar = Boolean.TRUE;
							grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaProperties.getProperty(StaticConstants.GRUPO_CONFIRMACION_PATH_DIRECTORY)) + groupIndex);
							groupIndex++;

							// Al ser necesario procesar el siguiente grupo
							// de confirmación,
							// dormimos el hilo para simular la espera...
							try {
								LOGGER.info(Language.getFormatResMonitoriza(LogMessages.REQUEST_THREAD_WAIT, new Object[ ] { grupoAProcesar.getAbsolutePath() }));
								Thread.sleep(Long.parseLong(StaticMonitorizaProperties.getProperty(StaticConstants.CONFIRMATION_WAIT_TIME)));
								
							} catch (InterruptedException e) {
								LOGGER.info(Language.getFormatResMonitoriza(LogMessages.CONFIRMATION_WAIT_INTERRUPTED, new Object[ ] { service.getServiceId() }));
							}
						} else {
							necesarioConfirmar = Boolean.FALSE;
						}

					} else {
						// Si no existe el grupo, no es necesario (ni posible)
						// confirmar
						necesarioConfirmar = Boolean.FALSE;
					}

				}
				while (necesarioConfirmar);

				// Si se ha obtenido una respuesta definitiva (no perdida/degradada) o no
				// hay más grupos de confirmación,
				// pasamos a calcular el estado del servicio con los datos
				// obtenidos.
				StatusUptodate statusUptodate = new StatusUptodate(calcularEstadoDelServicio(service, tiempoMedio, perdidas), LocalDateTime.now());
				statusHolder.put(service.getServiceId(), statusUptodate);

			} catch (InvokerException e) {
				RunningServices.getRequestsRunning().put(service.getServiceId(), Boolean.FALSE);
				LOGGER.error(Language.getFormatResMonitoriza(LogMessages.ERROR_PROCESSING_SERVICE, new Object[ ] { service.getServiceId() }), e);
			}

		}
		
		RunningServices.getRequestsRunning().put(service.getServiceId(), Boolean.FALSE);

	}

	/**
	 * Method that gets the status of a service from its resulting execution average time and throws an alarm
	 * if the status is not OK.
	 * @param serviceName Object with the configuration attributes for this service.
	 * @param tiempoMedio Long that represents the resulting average time for executing a batch of requests for this service.
	 * @return String that represents the status of the service:
	 * 			- CORRECTO
	 * 			- DEGRADADO
	 * 			- CAIDO
	 */
	private String calcularEstadoDelServicio(final ServiceDTO service, final Long tiempoMedio, final Integer perdidas) {

		String estado = null;
		boolean sendAlarm = Boolean.parseBoolean(StaticMonitorizaProperties.getProperty(StaticConstants.ALARM_ACTIVE));

		if (tiempoMedio != null && tiempoMedio <= service.getDegradedThreshold()) {
			estado = ServiceStatusConstants.CORRECTO;
		} else if (tiempoMedio != null && tiempoMedio > service.getDegradedThreshold()) {
			estado = ServiceStatusConstants.DEGRADADO;
		} else if (tiempoMedio == null || perdidas > Integer.parseInt(service.getLostThreshold())) {
			estado = ServiceStatusConstants.CAIDO;
		}

		// Se comprueba si es necesario lanzar alarma
		if (!estado.equals(ServiceStatusConstants.CORRECTO) && sendAlarm) {
			try {
				AlarmManager.throwNewAlarm(service.getServiceId(), estado, tiempoMedio);
			} catch (AlarmException e) {
				LOGGER.error(Language.getFormatResMonitoriza(LogMessages.ERROR_THROWING_ALARM, new Object[ ] { service.getServiceId(), estado }), e);
			}
		}

		return estado;
	}

	/**
	 * Method that gets a boolean that indicates if the request for a service is considered lost.
	 * @param service DTOService that contains configuration data for the service.
	 * @param tiempoTotal Time in milliseconds that has taken to complete the service request.
	 * @return true if the request is considered lost, false if the request is in time.
	 */
	private boolean isServiceRequestLost(final ServiceDTO service, final Long tiempoTotal) {

		boolean resultado = Boolean.FALSE;

		if (tiempoTotal == null || tiempoTotal > service.getTimeout()) {
			resultado = Boolean.TRUE;
		}

		return resultado;
	}

	/**
	 * Gets the {@link service}.
	 * @return {@link ServiceDTO}.
	 */
	public ServiceDTO getService() {
		return service;
	}

	/**
	 * Sets the {@link service}.
	 * @param service
	 */
	public void setService(ServiceDTO service) {
		this.service = service;
	}

	/**
	 * Gets the {@link statusHolder}.
	 * @return {@link Map<String, StatusUptodate>}.
	 */
	public Map<String, StatusUptodate> getStatusHolder() {
		return statusHolder;
	}

	/**
	 * Sets the {@link currentstatusHolder}.
	 * @param currentstatusHolder
	 */
	public void setStatusHolder(Map<String, StatusUptodate> statusHolder) {
		this.statusHolder = statusHolder;
	}
	
	/**
	 * 
	 * @return
	 */
	private static KeyStore loadSslTruststore() {

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new FileInputStream(StaticMonitorizaProperties.getProperty(StaticConstants.SSL_TRUSTTORE_PATH));) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResMonitoriza(LogMessages.ERROR_ACCESS_CERTIFICATE_SSL);
			cer = KeyStore.getInstance(StaticMonitorizaProperties.getProperty(StaticConstants.SSL_TRUSTSTORE_TYPE));

			cer.load(readStream, StaticMonitorizaProperties.getProperty(StaticConstants.SSL_TRUSTTORE_PASSWORD).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}

}
