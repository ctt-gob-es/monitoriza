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
 * @version 2.3, 03/05/2019.
 */
package es.gob.monitoriza.status.thread;

import java.io.File;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import es.gob.monitoriza.alarm.AlarmManager;
import es.gob.monitoriza.alarm.types.AlarmMail;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.exception.AlarmException;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18n.IAlarmMailText;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.invoker.http.HttpInvoker;
import es.gob.monitoriza.invoker.ocsp.OcspInvoker;
import es.gob.monitoriza.invoker.rfc3161.Rfc3161Invoker;
import es.gob.monitoriza.invoker.soap.SoapInvoker;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.DailyVipMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.service.impl.AlarmMonitorizaService;
import es.gob.monitoriza.service.impl.DailyVipMonitoringService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.status.RunningServices;
import es.gob.monitoriza.status.StatusUptodate;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;
import es.gob.monitoriza.utilidades.UtilsGrayLog;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that performs the calculations to get the service status executing the requests in a new thread.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 2.3, 03/05/2019.
 */
public final class RequestProcessorThread implements Runnable {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the identifier of the scheduled timer for this service thread. 
	 */
	private String idTimerTask;

	/**
	 * Attribute that represents the Object that holds the configuration for the service being processed in this thread. 
	 */
	private ConfigServiceDTO service;

	/**
	 * Attribute that represents the reference to the Map where the status of each service are stored. 
	 */
	private Map<String, StatusUptodate> statusHolder;
		
	/**
	 * Attribute that represents . 
	 */
	private static KeyStore ssl;
	
	/**
	 * Attribute that represents . 
	 */
	private static KeyStore authClient;

	/**
	 * Private constructor method for the class RequestProcessor.java. 
	 * @param idTimerTaskParam Identifier of the scheduled timer.
	 * @param serviceParam DTOService that represents the service being processed in this thread.
	 * @param statusHolderParam Reference to the Map that holds the current status for the processed services. 
	 * @param sslTrustStoreParam Truststore of Monitoriz@
	 * @param rfc3161KeystoreParam Keystore for authenticating RFC3161 service
	 */
	public RequestProcessorThread(final String idTimerTaskParam, final ConfigServiceDTO serviceParam, final Map<String, StatusUptodate> statusHolderParam, final KeyStore sslTrustStoreParam, final KeyStore rfc3161KeystoreParam) {

		this.idTimerTask = idTimerTaskParam;
		this.service = serviceParam;
		this.statusHolder = statusHolderParam;
		ssl = sslTrustStoreParam;
		authClient = rfc3161KeystoreParam;
	}

	/**
	 * Method that launch requests for a service and process the status. 
	 */
	@Override
	public void run() {

		LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS004, new Object[ ] { idTimerTask, service.getServiceName() }));

		RunningServices.getInstance();
		RunningServices.getRequestsRunning().put(service.getServiceName(), Boolean.TRUE);

		Long tiempoTotal = null;
		Long tiempoMedio = null;
		Integer perdidas = null;
		float totalRequests = 0;
		float totalRequestsLost = 0;
		Long totalTimes = 0L;
		boolean necesarioConfirmar = Boolean.TRUE;
		File grupoAProcesar = null;
		Map<String, String> partialRequestResult = new HashMap<String, String>();
		int groupIndex = 1;

		File serviceDir = new File(service.getDirectoryPath());
		// Comprobamos que la ruta proporcioanda es correcta.
		// Si existe un directorio con el nombre del servicio, continuo...
		if (serviceDir != null && serviceDir.exists() && serviceDir.isDirectory()) {

			LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS003, new Object[ ] { idTimerTask, serviceDir.toPath().toString(), service.getWsdl() }));

			// Enviamos las peticiones del grupo principal
			grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaConfig.getProperty(StaticConstants.GRUPO_PRINCIPAL_PATH_DIRECTORY)));

			do {

				// Se procesa el grupo...
				if (grupoAProcesar != null && grupoAProcesar.exists() && grupoAProcesar.listFiles() != null) {

					for (File request: grupoAProcesar.listFiles()) {
						// Vamos recorriendo los ficheros, y si es una
						// petición, la enviamos a @Firma o TS@
						if (request != null) {

							try {
								if (service.getServiceType().equalsIgnoreCase(GeneralConstants.OCSP_SERVICE)) {
									tiempoTotal = OcspInvoker.sendRequest(idTimerTask, request, service, ssl);
								} else if (service.getServiceType().equalsIgnoreCase(GeneralConstants.RFC3161_SERVICE)) {
									tiempoTotal = Rfc3161Invoker.sendRequest(idTimerTask, request, service, ssl, authClient);
								} else if (service.getServiceType().equalsIgnoreCase(GeneralConstants.HTTP_SERVICE)) {
									tiempoTotal = HttpInvoker.sendRequest(idTimerTask, request, service, ssl);
								} else {
									tiempoTotal = SoapInvoker.sendRequest(idTimerTask, request, service, ssl);
								}
							} catch (InvokerException e) {

								LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS002, new Object[ ] { idTimerTask, service.getServiceName() }), e);

							} finally {
								totalRequests++;

								// Si no ha podido calcularse el tiempo de
								// la request o se considera perdida,
								// se aumenta el nº de requests perdidas.
								if (isServiceRequestLost(tiempoTotal)) {
									totalRequestsLost++;
									// En otro caso, se añade el tiempo de
									// la request al total para hacer la media a
									// posteriori.
								} else {
									totalTimes += tiempoTotal;
								}

								// Se almacena el resultado parcial para la
								// petición del grupo actual
								partialRequestResult.put(request.getAbsolutePath(), isServiceRequestLost(tiempoTotal) ? "Sin respuesta" : tiempoTotal.toString());

							}

						}

					}

					// Calcular el tiempo medio acumulado si hay algún
					// tiempo disponible...
					if (totalRequests != totalRequestsLost) {
						tiempoMedio = (long) Math.round(totalTimes / (totalRequests - totalRequestsLost));
					}
					// Calcular % de perdidas para el grupo principal
					perdidas = Math.round((totalRequestsLost / totalRequests) * NumberConstants.NUM100);

					// Si se cumplen las condiciones, se obtiene el posible
					// próximo grupo de confirmación...
					if (perdidas > Integer.parseInt(service.getLostThreshold()) || tiempoMedio > service.getDegradedThreshold()) {
						LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS005, new Object[ ] { idTimerTask, service.getServiceName(), perdidas, tiempoMedio == null ? "N/A" : tiempoMedio }));
						necesarioConfirmar = Boolean.TRUE;
						grupoAProcesar = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaConfig.getProperty(StaticConstants.GRUPO_CONFIRMACION_PATH_DIRECTORY)) + groupIndex);
						groupIndex++;

						// Al ser necesario procesar el siguiente grupo
						// de confirmación,
						// dormimos el hilo para simular la espera...
						try {
							LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS006, new Object[ ] { idTimerTask, grupoAProcesar.getAbsolutePath() }));
							Thread.sleep(Long.parseLong(StaticMonitorizaConfig.getProperty(StaticConstants.CONFIRMATION_WAIT_TIME)));

						} catch (InterruptedException e) {
							LOGGER.info(Language.getFormatResMonitoriza(IStatusLogMessages.STATUS007, new Object[ ] { idTimerTask, service.getServiceName() }));
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

			// Si se ha obtenido una respuesta definitiva (no perdida/degradada)
			// o no
			// hay más grupos de confirmación,
			// pasamos a calcular el estado del servicio con los datos
			// obtenidos.
			StatusUptodate statusUptodate = new StatusUptodate(calcularEstadoDelServicio(tiempoMedio, perdidas), service.getPlatform(), tiempoMedio, LocalDateTime.now(), partialRequestResult);
			statusHolder.put(service.getServiceName(), statusUptodate);
			RunningServices.getRequestsRunning().put(service.getServiceName(), Boolean.FALSE);

			saveDailyVipMonitoring(service.getServiceName(), service.getPlatform(), statusUptodate);



		}
	}

	

	/**
	 * Method that gets the status of a service from its resulting execution average time and throws an alarm
	 * if the status is not OK.
	 * @param tiempoMedio Long that represents the resulting average time for executing a batch of requests for this service.
	 * @param perdidas Number of lost requests
	 * @return String that represents the status of the service:
	 * 			- CORRECTO
	 * 			- DEGRADADO
	 * 			- CAIDO
	 */
	private String calcularEstadoDelServicio(final Long tiempoMedio, final Integer perdidas) {
		
				
		String estado = null;
		String grayLogErrorCode = null;
		String subjectAlarm = null;
		String bodyAlarm = null;
				
		boolean sendAlarm = Boolean.parseBoolean(StaticMonitorizaConfig.getProperty(StaticConstants.ALARM_ACTIVE));
		boolean servicioCaido = perdidas == null || perdidas > Integer.parseInt(service.getLostThreshold());

		if (tiempoMedio != null && tiempoMedio <= service.getDegradedThreshold() && !servicioCaido) {
			estado = ServiceStatusConstants.CORRECTO;
		} else if (tiempoMedio != null && tiempoMedio > service.getDegradedThreshold() && !servicioCaido) {
			estado = ServiceStatusConstants.DEGRADADO;
			grayLogErrorCode = GrayLogErrorCodes.ALARM_SERVICE_DEGRADED;
			subjectAlarm = generateSubject(estado);
			bodyAlarm = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_ALARM_DEGRADED, new Object[ ] { service.getPlatform().concat(GeneralConstants.EN_DASH_WITH_SPACES).concat(getServiceConfigUrl(service)), tiempoMedio, service.getDegradedThreshold()});
		} else if (tiempoMedio == null || servicioCaido) {
			estado = ServiceStatusConstants.CAIDO;
			subjectAlarm = generateSubject(estado);
			grayLogErrorCode = GrayLogErrorCodes.ALARM_SERVICE_LOST;
			bodyAlarm = Language.getFormatResAlarmMonitoriza(IAlarmMailText.BODY_MAIL_ALARM_LOST, new Object[ ] { service.getPlatform().concat(GeneralConstants.EN_DASH_WITH_SPACES).concat(getServiceConfigUrl(service))});
		}
		
		// Se comprueba si es necesario lanzar alarma
		if (!estado.equals(ServiceStatusConstants.CORRECTO) && sendAlarm) {
			try {
				// Se obtiene el servicio de alarmas para cargar la configuración de bloqueo y direcciones actualizadas.
				AlarmMonitorizaService alarmMonitorizaService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.ALARM_VIP_SERVICE, AlarmMonitorizaService.class);
				AlarmMonitoriza alarmMonitoriza = alarmMonitorizaService.getAlarmMonitorizaById(service.getIdAlarm());
				
				// Se informa el objeto con los datos de la alarma.
				// Creamos una nueva alarma.
				AlarmMail alarm = new AlarmMail(service.getServiceName(), estado, getAddressesFromAlarm(alarmMonitoriza, estado), alarmMonitoriza.getBlockedTime(), null, null, subjectAlarm, bodyAlarm);
				
				AlarmManager.throwNewAlarm(alarm);
			} catch (AlarmException e) {
				LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS003, new Object[ ] { idTimerTask, service.getServiceName(), estado }), e);
			}
			
			// Registramos la alarma en GrayLog si así está configurado.
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, grayLogErrorCode, bodyAlarm); 
		}

		return estado;
	}
	
	/**
	 * Saves the VIP status sample in persistence.
	 * @param serviceName VIP service being monitored
	 * @param platform Platform of the service being monitored
	 * @param status Status result of the service.
	 */
	private void saveDailyVipMonitoring(final String serviceName, final String platform, final StatusUptodate status) {
		
		DailyVipMonitorig daily = new DailyVipMonitorig();
		
		daily.setPlatform(platform);
		daily.setSamplingTime(status.getStatusUptodate());
		daily.setService(serviceName);
		daily.setStatus(status.getStatusValue());
			
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.DAILY_VIP_MONITORING_SERVICE, DailyVipMonitoringService.class).saveDailyVipMonitoring(daily);
		} catch (DatabaseException e) {
			String msg = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS018, new Object[]{idTimerTask});
			LOGGER.error(msg, e);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_STATUS_VIP_SAVE, msg);
		}	
	}

	/**
	 * Method that gets a boolean that indicates if the request for a service is considered lost.
	 * @param tiempoTotal Time in milliseconds that has taken to complete the service request.
	 * @return true if the request is considered lost, false if the request is in time.
	 */
	private boolean isServiceRequestLost(final Long tiempoTotal) {

		boolean resultado = Boolean.FALSE;

		if (tiempoTotal == null || tiempoTotal > service.getTimeout()) {
			resultado = Boolean.TRUE;
		}

		return resultado;
	}

	/**
	 * Gets the {@link service}.
	 * @return {@link ConfigServiceDTO}.
	 */
	public ConfigServiceDTO getService() {
		return service;
	}

	/**
	 * Sets the {@link service}.
	 * @param serviceParam ServiceDTO containing its configuration
	 */
	public void setService(ConfigServiceDTO serviceParam) {
		this.service = serviceParam;
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
	 * @param currentstatusHolder Current status holder
	 */
	public void setStatusHolder(Map<String, StatusUptodate> currentstatusHolder) {
		this.statusHolder = currentstatusHolder;
	}	
	
	/**
	 * Method that returns the URL of the service.
	 * @param service Configuration information of the service.
	 * @return Complete URL of the service.
	 */
	private static String getServiceConfigUrl(final ConfigServiceDTO service) {
		
		String serviceUrl = null;
		
		switch (service.getServiceType().toLowerCase()) {
			
			case GeneralConstants.SOAP_SERVICE:
				serviceUrl = service.getSoapUrl().concat(service.getWsdl());
				break;
			case GeneralConstants.OCSP_SERVICE:
				serviceUrl = service.getBaseUrl().concat(service.getOcspContext());
				break;
			case GeneralConstants.RFC3161_SERVICE:
				serviceUrl = service.getBaseUrl().concat(service.getRfc3161Context());
				break;
			case GeneralConstants.HTTP_SERVICE:
				serviceUrl = service.getSoapUrl().concat(service.getWsdl());
				break;
		}
		
		return serviceUrl;
	}
	
	/**
	 * Method that retrieves the addresses from a MailMonitoriza object.
	 * @param alarmMonitoriza Object with the VIP alarm configuration.
	 * @param estado Service status.
	 * @return List<String> that represents the mail addresses 
	 */
	private static List<String> getAddressesFromAlarm(final AlarmMonitoriza alarmMonitoriza, final String estado) {
		
		Set<MailMonitoriza> mailSet = null;
		final List<String> mailList = new ArrayList<String>();
		
		if (ServiceStatusConstants.DEGRADADO.equals(estado)) {
			mailSet = alarmMonitoriza.getEmailsDegraded();
		} else if (ServiceStatusConstants.CAIDO.equals(estado)) {
			mailSet = alarmMonitoriza.getEmailsDown();
		}
		
		Iterator<MailMonitoriza> mailIterator = mailSet.iterator();
		
		while (mailIterator.hasNext()) {
			
			mailList.add(mailIterator.next().getEmailAddress());
		}
		
		return mailList;
	}
	
	/**
	 * Method that generate the subject for single alarm mails.
	 * @param status String that represents the result status of the service.
	 * @return String that represents the text of the alarm mail subject.
	 */
	private String generateSubject(final String status) {
		
		StringBuffer subject = new StringBuffer();
		
		subject.append(service.getPlatform()).append(GeneralConstants.EN_DASH_WITH_SPACES).append(service.getServiceName()).append(UtilsStringChar.SPECIAL_BLANK_SPACE_STRING).append(status);
		
		return subject.toString();
	}
	
}
