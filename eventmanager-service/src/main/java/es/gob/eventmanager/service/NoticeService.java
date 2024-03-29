/* ******************************************************************************
 * Copyright (C) 2021 MINHAFP, Gobierno de España
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
package es.gob.eventmanager.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.gob.eventmanager.configuration.ManagerConfigurationServices;
import es.gob.eventmanager.constant.INotificationSystemTypes;
import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.message.Alert;
import es.gob.eventmanager.message.Event;
import es.gob.eventmanager.message.EventResponse;
import es.gob.eventmanager.message.EventResponseType;
import es.gob.eventmanager.notifier.email.EMailTimeLimitedOperation;
import es.gob.eventmanager.notifier.graylog.GrayLogConfigDTO;
import es.gob.eventmanager.notifier.graylog.GrayLogTimeLimitedOperation;
import es.gob.eventmanager.persistence.model.entity.AlertAudit;
import es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertGraylogSystemConfig;
import es.gob.eventmanager.persistence.model.entity.AlertMailNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertSystemMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertTypeMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ApplicationMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ConfServerMail;
import es.gob.eventmanager.persistence.model.entity.TemplateMonitoriza;

/**
 * Servicio para la notificaci&oacute;n de eventos.
 * @version 1.6, 10/01/2022.
 */
public class NoticeService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger("eventmanager-service");

	private static final Charset CHARSET = StandardCharsets.UTF_8;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeService() {
        super();
    }
     
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		// Establecemos el juego de caracteres con el que se va a leer el contenido de la peticion
		request.setCharacterEncoding(CHARSET.displayName().toLowerCase());
		response.setCharacterEncoding(CHARSET.displayName().toLowerCase());

		final Event event;
		try {
			event = RequestValidator.validate(request);
		}
		catch (final SecurityException e) {
			LOGGER.warn("La peticion no cumple con los requisitos de seguridad", e);
			responseError(EventResponseType.ERROR_AUTHENTICATION, "La peticion no cumple con los requisitos de seguridad", response);
			return;
		}
		catch (final IOException e) {
			LOGGER.warn("El contenido de la peticion no es valido o no ha podido leerse", e);
			responseError(EventResponseType.ERROR_INVALID_REQUEST, "La peticion no cumple con los requisitos de seguridad", response);
			return;
		}
		catch (final Exception e) {
			LOGGER.warn("Error desconocido al validar la peticion", e);
			responseError(EventResponseType.UNKNOWN, "Error desconocido al validar la peticion", response);
			return;
		}

		// Procesamos el evento
		notifyEvent(event);

		// Registramos la auditoria del evento
		registerAudit(event);

		// Registramos el evento en los resumenes a los que pueda pertenecer
		registerOnResumes(event);

		// Enviamos la respuesta de exito
		responseOk(response);
	}

	/**
	 * Contesta al cliente devolviendo una respuesta de error.
	 * @param type Tipo de error.
	 * @param message Mensaje de error.
	 * @param httpResponse Respuesta HTTP sobre la que enviar el mensaje.
	 * @throws IOException Cuando ocurre un error durante la respuesta.
	 */
	private static void responseError(final EventResponseType type, final String message, final HttpServletResponse httpResponse) throws IOException {
		final EventResponse response = new EventResponse(type.getCode());
		response.setDescription(message);
		try (Writer writer = httpResponse.getWriter()) {
			writer.write(buildJsonMessage(response));
		}
	}

	/**
	 * Contesta al cliente devolviendo una respuesta de &eacute;xito.
	 * @param httpResponse Respuesta HTTP sobre la que enviar el mensaje.
	 * @throws IOException Cuando ocurre un error durante la respuesta
	 */
	public static void responseOk(final HttpServletResponse httpResponse) throws IOException {
		final EventResponse response = new EventResponse(EventResponseType.OK.getCode());
		try (Writer writer = httpResponse.getWriter()) {
			writer.write(buildJsonMessage(response));
		}
	}

	/**
	 * Construye un JSON que representa la respuesta de un evento.
	 * @param eventResponse Respuesta de un evento.
	 * @return Cadena JSON.
	 */
	private static String buildJsonMessage(final EventResponse eventResponse) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		return gson.toJson(eventResponse);
	}

	/**
	 * Emite las notificaciones pertinentes a las alertas del evento recibido.
	 * @param event Evento en el que se notifican alertas.
	 */
	private void notifyEvent(final Event event) {
		
		boolean isEnabledAlert = true;
		boolean isBlockedAlert = false;
		Date currentDate = Calendar.getInstance().getTime();
		
		try {
			// Obtenemos la aplicacion de la persistencia
			ApplicationMonitoriza app = ManagerConfigurationServices.getInstance().getEventManagerBO().getApplicationMonitorizaByName(event.getSystemId());
			// Obtenemos la informacion del tipo de alerta
			TemplateMonitoriza template = app.getTemplateMonitoriza();
			ManagerConfigurationServices.getInstance().getEventManagerBO().loadLazyListAlertTypesInTemplate(template);
			List<AlertTypeMonitoriza> listAlertTypes = template.getListAlertTypes();

			// Recorremos las alertas recibidas en la peticion
			for (Alert alert: event.getAlerts()) {

				// Buscamos la alerta entre los tipos configurados
				for (AlertTypeMonitoriza alertType: listAlertTypes) {

					if (alertType.getName().equals(alert.getCode())) {

						// Cargamos la configuracion de la alerta segun
						// el nombre de la aplicacion y el tipo de alerta
						AlertConfigMonitoriza config = ManagerConfigurationServices.getInstance().getEventManagerBO().getAlertConfigByAlertTypeAndApplication(alertType.getIdTypeMonitoriza(), app.getIdApplicationMonitoriza());
						
						if (config == null) {
							String msg = "No existe configuración asociada para la aplicación ["+ event.getSystemId() + "] y el tipo de alerta ["+ alert.getCode() + "]. No se procesará el evento.";
							throw new EventManagerException(msg);
						}
						
						// Se actualiza el momento de ultima recepcion de alertade este tipo
						config.setLastTime(new Date());
						ManagerConfigurationServices.getInstance().getEventManagerBO().saveAlertConfig(config);

						// Logica de envio
						isEnabledAlert = config.getIsEnabled();
						
						if (AlertConfigManager.isBlockedAlert(config.getIdAlertConfigMonitoriza()) == null || !AlertConfigManager.isBlockedAlert(config.getIdAlertConfigMonitoriza())) {
							
							isBlockedAlert = false;
							
						} else if (AlertConfigManager.isBlockedAlert(config.getIdAlertConfigMonitoriza())) {
							
							isBlockedAlert = true;
						}
						
						// Comprobamos si la alarma se encuentra deshabilitada,
						// en cuyo caso no hacemos nada.
						if (isEnabledAlert) {

							// se comprueba si está bloqueada y ha pasado el
							// tiempo para el autodesbloqueo
							if (isBlockedAlert && config.getBlockTime() != null && AlertConfigManager.checkIfUnblockAlert(config, currentDate)) {

								// ha pasado el tiempo de bloqueo y hay que
								// desbloquear.
								isBlockedAlert = false;
								AlertConfigManager.unblockAlert(alertType.getIdTypeMonitoriza(), app.getIdApplicationMonitoriza());
								
								
							} else if (!isBlockedAlert && config.getAllowBlock()) {
								// Se comprueba si se dan las condiciones para bloquear la alerta
								// y en caso afirmativo se realiza el bloqueo, actualizando la alerta
								// en base de datos.
								isBlockedAlert = AlertConfigManager.checkBlockadeAndAlertCount(config, currentDate);
										
							}

							if (!isBlockedAlert) {

								// se envía un email a los destinatarios y/o
								// notificacion GrayLog, según la configuración.
								ManagerConfigurationServices.getInstance().getEventManagerBO().loadLazyAlertSystemsInAlertConfig(config);
								List<AlertSystemMonitoriza> alertSystems = config.getSystemsMonitoriza();
								notifyToConfiguredSystems(config, alertSystems, alert);
							}

						}
												
					}
				}
			}

		} catch (EventManagerException e) {

			LOGGER.warn("Ha ocurrido un error al enviar la notificacion: aplicacion: {}, nodo: {}", event.getSystemId(), event.getNode(), e);
		}

	}

	/**
	 * 
	 * @param alertConfig
	 * @param alertSystems
	 */
	private void notifyToConfiguredSystems(final AlertConfigMonitoriza alertConfig, final List<AlertSystemMonitoriza> alertSystems, final Alert alert) throws EventManagerException {

		AlertGraylogSystemConfig graylogConfig = null;

		for (AlertSystemMonitoriza system: alertSystems) {

			if (system.getType().getName().equalsIgnoreCase(INotificationSystemTypes.GRAYLOG)) {

				graylogConfig = system.getGraylogSystemConfig();

				try {
					validateGrayLogConfig(graylogConfig);
					notifyToGrayLog(alertConfig, graylogConfig, alert);
				} catch (EventManagerException e) {
					LOGGER.warn("No ha sido posible realizar la notificacion de la alerta [" + alert.getCode() + "] a Graylog", e.getCause());
				}
			}

			if (system.getType().getName().equalsIgnoreCase(INotificationSystemTypes.EMAIL)) {

				try {
					List<AlertMailNoticeConfig> mailNoticeConfigs = ManagerConfigurationServices.getInstance().getEventManagerBO().getMailNoticesByAlertConfig(alertConfig.getIdAlertConfigMonitoriza());
					notifyToEMail(alertConfig, mailNoticeConfigs, alert);
				} catch (EventManagerException e) {
					LOGGER.warn("No ha sido posible realizar la notificacion de la alerta [" + alert.getCode() + "] a las direcciones de email configuradas", e.getCause());
				}

			}

		}

	}

	/**
	 * 
	 * @param alertConfig
	 * @param mailNoticeConfigs
	 * @param alert
	 */
	private void notifyToEMail(AlertConfigMonitoriza alertConfig, List<AlertMailNoticeConfig> mailNoticeConfigs, Alert alert) throws EventManagerException {
		
		if (mailNoticeConfigs != null && mailNoticeConfigs.size() > 0) {
			
			ConfServerMail configMailServer =  ManagerConfigurationServices.getInstance().getEventManagerBO().getMailServerConfig();
			validateMailServerConfig(configMailServer);
			
			List<String> addresses = new ArrayList<String>();
			for (AlertMailNoticeConfig alertMailNotice : mailNoticeConfigs) {
				addresses.add(alertMailNotice.getMail());
			}
			
			StringBuilder subject = new StringBuilder();
			subject.append("Alerta de aplicación [").append(alertConfig.getApplicationMonitoriza().getName()).append("]. Severidad [").append(alertConfig.getAlertSeverityMonitoriza().getName()).append("]. Código [").append(alert.getCode()).append("].");
			// Prueba resumen
			//String subject = "Alertas del resumen [Dirección] producidas en la fecha [01/12/2021]";
			//String msg = "<Este correo ha sido generado autom\u00E1ticamente desde una direcci\u00f3n que no acepta correos entrantes, por favor, no responda.>\n\n\nAplicación [tsa]: \n\n Se han producido el siguiente número de alarmas por criticidad:  \n\n [2] alarmas con criticidad [FATAL]. \n\n [2] alarmas con criticidad [ERROR] \n\n\n Desglose de alarmas: \n\n [01/12/2021 14:57:27] - [TSA_COD_001] - [ERROR] - [La configuracion estatica no es correcta: revisar fichero staticTSAConfig.properties]  \n\n [01/12/2021 14:57:27] - [TSA_COD_002] - [FATAL] - [Fallo de conexión con el servidor NTP: 10.254.205.7]  \n\n [01/12/2021 14:52:41] - [TSA_COD_001] - [ERROR] - [La configuracion estatica no es correcta: revisar fichero staticTSAConfig.properties]  \n\n [01/12/2021 14:52:41] - [TSA_COD_002] - [FATAL] - [Fallo de conexión con el servidor NTP: 10.254.205.7] \n\n\nAplicación [FIRe_DES]: \n\n Se han producido el siguiente número de alarmas por criticidad:  \n\n [3] alarmas con criticidad [ERROR] \n\n\n Desglose de alarmas: \n\n [01/12/2021 13:21:17] - [005] - [ERROR] - [No se puede conectar con un proveedor de firma en la nube: fnmt]  \n\n [01/12/2021 12:55:52] - [005] - [ERROR] - [No se puede conectar con un proveedor de firma en la nube: fnmt]  \n\n [01/12/2021 11:10:43] - [005] - [ERROR] - [No se puede conectar con un proveedor de firma en la nube: Prueba tras cambio]" ;
			
			EMailTimeLimitedOperation emailOperation = new EMailTimeLimitedOperation(configMailServer, addresses, subject.toString(), alert.getMessage());
			emailOperation.startOperation();
		}
		
	}

	/**
	 * 
	 * @param configMailServer
	 */
	private void validateMailServerConfig(ConfServerMail configMailServer) throws EventManagerException {
		
		if (configMailServer == null) {
			
			throw new EventManagerException("No existe configuracion del servidor de correos en el sistema");
			
		} else if (configMailServer.getHostMail() == null || configMailServer.getHostMail().isEmpty() || configMailServer.getPortMail() == null || configMailServer.getPortMail() < 0) {
			
			throw new EventManagerException("La configuracion del servidor de correos no es correcta");
		}
		
	}

	/**
	 * 
	 * @param graylogConfig
	 */
	private void validateGrayLogConfig(AlertGraylogSystemConfig graylogConfig) throws EventManagerException {

		if (graylogConfig == null) {

			throw new EventManagerException("La configuracion de GrayLog no es valida");
			
		} else if (graylogConfig.getHost() == null || graylogConfig.getHost().isEmpty() || graylogConfig.getPort() == null || graylogConfig.getPort() < 0) {

			throw new EventManagerException("La configuracion de GrayLog con identificador" + graylogConfig.getIdAlertGraylogSystemConfig() + "no es valida");
		}

	}

	/**
	 * 
	 * @param alertConfig
	 * @param alert
	 * @throws EventManagerException 
	 */
	private void notifyToGrayLog(AlertConfigMonitoriza alertConfig, AlertGraylogSystemConfig graylogConfig, Alert alert) throws EventManagerException {
		
		List<AlertGraylogNoticeConfig> glNoticeConfigs = ManagerConfigurationServices.getInstance().getEventManagerBO().getGLNoticesByAlertConfig(alertConfig.getIdAlertConfigMonitoriza());
		Map<String,String> grayLogDeclaredFields = new HashMap<String, String>();
		
		if (glNoticeConfigs != null && glNoticeConfigs.size() > 0) {
			
			for (AlertGraylogNoticeConfig glNotice : glNoticeConfigs) {
				grayLogDeclaredFields.put(glNotice.getPkey(), glNotice.getValue());
			}
		}
		
		GrayLogConfigDTO graylogDTO = new GrayLogConfigDTO();
		graylogDTO.setHost(graylogConfig.getHost());
		graylogDTO.setPort(graylogConfig.getPort());
		graylogDTO.setGrayLogDeclaredFields(grayLogDeclaredFields);
		GrayLogTimeLimitedOperation glOperation = new GrayLogTimeLimitedOperation(graylogDTO, alert);
		glOperation.startOperation();
		
	}

	/**
	 * Registra con fines estad&iacute;sticos la informaci&oacute;n de las
	 * alertas recibidas.
	 * @param event Evento en el que se notifican alertas.
	 */
	@Transactional
	private void registerAudit(final Event event) {
		
		AlertAudit auditoria = null;
				
		try {
			// Obtenemos la aplicacion de la persistencia
			ApplicationMonitoriza app = ManagerConfigurationServices.getInstance().getEventManagerBO().getApplicationMonitorizaByName(event.getSystemId());
			// Obtenemos la informacion del tipo de alerta
			TemplateMonitoriza template = app.getTemplateMonitoriza();
			ManagerConfigurationServices.getInstance().getEventManagerBO().loadLazyListAlertTypesInTemplate(template);
			List<AlertTypeMonitoriza> listAlertTypes = template.getListAlertTypes();
			
			// Recorremos las alertas recibidas en la peticion
			for (Alert alert : event.getAlerts()) {				
					
				// Buscamos la alerta entre los tipos configurados
				for (AlertTypeMonitoriza alertType : listAlertTypes) {
					
					if (alertType.getName().equals(alert.getCode())) {
																		
						// Cargamos la configuracion de la alerta segun
						// el nombre de la aplicacion y el tipo de alerta
						AlertConfigMonitoriza config = ManagerConfigurationServices.getInstance().getEventManagerBO().getAlertConfigByAlertTypeAndApplication(alertType.getIdTypeMonitoriza(), app.getIdApplicationMonitoriza());
						
						auditoria = new AlertAudit();
						auditoria.setAlertName(alertType.getName());
						auditoria.setAppName(app.getName());
						auditoria.setAppTemplateName(template.getName());
						auditoria.setNode(event.getNode());
						auditoria.setSeverity(config.getAlertSeverityMonitoriza().getName());
						auditoria.setTimestamp(new Date());
						auditoria.setDescription(alert.getMessage());
						
						ManagerConfigurationServices.getInstance().getEventManagerBO().registerAlertAudit(auditoria);					
					}
				}
				
			}			
			
		} catch (EventManagerException e) {
			//LOGGER.warn("Ha ocurrido un error al registrar los datos de la auditoria", e);
			LOGGER.warn("Ha ocurrido un error al registrar la auditoria del evento con origen: aplicacion: {}, nodo: {}",  event.getSystemId(), event.getNode(), e);
		}

	}

	/**
	 * Registra con fines estad&iacute;sticos la informaci&oacute;n de las
	 * alertas recibidas.
	 * @param event Evento en el que se notifican alertas.
	 */
	private void registerOnResumes(final Event event) {
		// TODO Auto-generated method stub

	}

}
