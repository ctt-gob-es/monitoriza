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
 * <b>File:</b><p>es.gob.monitoriza.cron.AlertResumeTask.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.cron;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.gob.monitoriza.alert.send.EMailTimeLimitedOperation;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.INotificationSystemTypes;
import es.gob.monitoriza.constant.IResumeConstants;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.exception.AlertException;
import es.gob.monitoriza.i18n.IAlertSeverityConstants;
import es.gob.monitoriza.i18n.ITaskResumeMailText;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.AlertResumeDTO;
import es.gob.monitoriza.persistence.configuration.dto.ApplicationResumeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailResumeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.service.IAlertAuditService;
import es.gob.monitoriza.service.IConfServerMailService;
import es.gob.monitoriza.service.IResumeMonitorizaService;
import es.gob.monitoriza.utilidades.UtilsFecha;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Task that processes configured {@link ResumeMonitoriza} and determines if it must be sent and builds formatted text with the data.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
@Component
public class AlertResumeTask {
	
	@Autowired
	IResumeMonitorizaService resumeService;
	
	@Autowired
	IAlertAuditService auditService;
	
	@Autowired
	IConfServerMailService mailService;
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_RESUME_TASK_LOG);
	
	/**
	 * Scheduled method that executes at fixed rate and processes the configured alert 'resumes'.
	 * This fixed rate must be exactly 1 hour, since this is the minimum periodicity of a 'resume'.
	 */
	@Scheduled(fixedRate = 3600000)
	void processAlertResumes() {
		
		LOGGER.info("Inicio de la tarea de procesado de resúmenes de alertas");
				
		List<ResumeMonitoriza> resumenes = StreamSupport.stream(resumeService.getAllResumeMonitoriza().spliterator(), false).collect(Collectors.toList());
		
		for (ResumeMonitoriza resume : resumenes) {
			
			LOGGER.info("Se procesa la ejecución del resumen [" + resume.getName() + "]");
			String lastSentString = resume.getLastSentTime() == null?IResumeConstants.RESUME_NEVER_SENT:UtilsFecha.toString(UtilsFecha.FORMATO_FECHA_ESTANDAR, resume.getLastSentTime());
			LOGGER.info("Último envío del resumen: " + lastSentString);
			LOGGER.info("Periodicidad del resumen: " + resume.getPeriodicity());
			
			LOGGER.info("Se procede a determinar, si debe enviarse el resumen [" + resume.getName() + "]");
			Long lastSent = resume.getLastSentTime() == null?null:resume.getLastSentTime().getTime();
			
			// Se comprueba si el resumen esta pendiente de enviar y si existen alertas configuradas:
			// Esto sucedera si nunca se ha enviado (lastSent == null)
			// o si el tiempo transcurrido desde el ultimo envio es mayor o igual a la periodicidad
			if (lastSent == null || ( lastSent != null && System.currentTimeMillis() - lastSent >= resume.getPeriodicity() * NumberConstants.NUM3600000)) {
				 
				LOGGER.info("El resumen [" + resume.getName() + "] debe enviarse por haber transcurrido el tiempo necesario");
				LOGGER.info("Se procede a recopilar las alertas registradas en la auditoria que cumplan las condiciones");
				
				resumeService.loadLazyListResumeType(resume);
				
				if (resume.getResumeTypes().size() > 0) {
					
					processResume(resume);
					
				} else {
					
					LOGGER.info("No existen alertas configurardas para el resumen: " + resume.getName());
				}
			} else {
				
				LOGGER.info("No se dan las condiciones temporales para enviar el resumen: " + resume.getName());
			}
				
		}
	}

	/**
	 * Method that process a 'Resume' and determines if there are alerts to send.
	 * @param resume {@link ResumeMonitoriza} to process.
	 */
	private void processResume(ResumeMonitoriza resume) {

		
		Set<String> alertTypes = resume.getResumeTypes().stream().map(resumeType -> new String(resumeType.getAlertTypeMonitoriza().getName())).collect(Collectors.toSet());
		Set<String> apps = resume.getResumeTypes().stream().map(resumeType -> new String(resumeType.getApplicationMonitoriza().getName())).collect(Collectors.toSet());

		// Se calcula la fecha tope que deben tener las alertas que forman parte
		// del resumen
		Date limit = resolveTimeLimit(resume);

		List<AlertAudit> alertsForResume = auditService.getAlertAuditForResume(alertTypes, apps, limit);
		LOGGER.info("Se han encontrado [" + alertsForResume.size() + "] alertas para el resumen [" + resume.getName() + "]");

		if (alertsForResume.size() > 0) {
			
			String subject = Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_SUBJECT, new Object[ ] { resume.getName(), UtilsFecha.toString(UtilsFecha.FORMATO_FECHA_ESTANDAR, new Date()) });
						
			Map<String, ApplicationResumeDTO> mapApps = new HashMap<String, ApplicationResumeDTO>();
			ApplicationResumeDTO appResumeDto = null;
			for (AlertAudit alert : alertsForResume) {
				
				// Si la aplicacion aun no ha sido añadida
				if (mapApps.get(alert.getAppName()) == null) {
					
					appResumeDto = new ApplicationResumeDTO(alert.getAppName());
					updateNumberServerity(appResumeDto, alert.getSeverity());
					appResumeDto.addOrderedAlerta(new AlertResumeDTO( UtilsFecha.toString(UtilsFecha.FORMATO_FECHA_HORA, alert.getTimestamp()), alert.getSeverity(), alert.getAlertName(), alert.getDescription()));
					
					
				} else {
					
					appResumeDto = mapApps.get(alert.getAppName());
					updateNumberServerity(appResumeDto, alert.getSeverity());
					appResumeDto.addOrderedAlerta(new AlertResumeDTO( UtilsFecha.toString(UtilsFecha.FORMATO_FECHA_HORA, alert.getTimestamp()), alert.getSeverity(), alert.getAlertName(), alert.getDescription()));
					
				}
				
				mapApps.put(alert.getAppName(), appResumeDto);
				
			}
			
			String body = getResumeBody(resume, mapApps);
			
			resumeService.loadLazyListAlertResumeSystem(resume);
			
			boolean allFailed = sendResumeToConfiguredSystems(resume.getAlertResumeSystem(), subject, body);
			
			if (!allFailed) {
				// actualizar momento de envio
				resume.setLastSentTime(new Date());
				resumeService.saveResumeMonitoriza(resume);
				
			} else {
				// Todos los envios han fallado, no se actualiza el momento de envío
				LOGGER.info("Han fallado todos los envios de resumen a todos los sistemas configurados. No se actualiza el momento de envio.");
			}
			
//			LOGGER.info(subject.toString());
//			LOGGER.info("\n\n");
//			LOGGER.info(body.toString());					
			
		} else {

			LOGGER.info("No se envia el resumen ya que no hay alertas configuradas.");
		}
	}

	/**
	 * 
	 * @param subject
	 * @param body
	 * @return
	 */
	private boolean sendResumeToConfiguredSystems(Set<AlertResumeSystem> systems, String subject, String body) {
		
		LOGGER.info("Cargando la configuración de los sistemas de notificación del resumen...");
		
		// La configuración del servidor de correos es unica.
		// Se obtiene solo una vez...
		ConfServerMail mailConfig = mailService.getAllConfServerMail();
		
		boolean allFailed = true;
		for (AlertResumeSystem system: systems) {
			
			LOGGER.info("Obtenido sistema de notificación: " + system.getAlertSystemMonitoriza().getName());

			if (system.getAlertSystemMonitoriza().getType().getName().equalsIgnoreCase(INotificationSystemTypes.EMAIL)) {

				allFailed = sendResumeEMail(mailConfig, system.getAlertMailsResumeConfig(), subject, body);
				
			}

		}
		
		return allFailed;
	}

	/**
	 * 
	 * @param alertMailsResumeConfig
	 * @param subject
	 * @param body
	 */
	private boolean sendResumeEMail(ConfServerMail mailConfig, List<AlertMailResumeConfig> alertMailsResumeConfig, String subject, String body) {

		// En estos momentos, el unico sistema a contemplar es el email.
		// Se procede a recuperar direcciones destino.
		List<String> addresses = new ArrayList<String>();
		boolean success = true;
		for (AlertMailResumeConfig mail: alertMailsResumeConfig) {

			addresses.add(mail.getMail());
		}

		EMailTimeLimitedOperation emailOperation;
		try {
			
			LOGGER.info("Enviado el resumen a las direcciones de correo configuradas en el sistema de notificación...");
			emailOperation = new EMailTimeLimitedOperation(mailConfig, addresses, subject, body);
			emailOperation.startOperation();
			
			
			if (emailOperation.getException() != null) {
				success = false;
				LOGGER.info("Ha ocurrido un error durante el envío: " + emailOperation.getException().getMessage());
			} else {
				LOGGER.info("Se ha enviado corréctamente el resumen");
			}
			
		} catch (AlertException e) {
			
			LOGGER.info("Ha ocurrido un error durante el envío: " + e.getMessage());
			e.printStackTrace();
			success = false;
		}
		
		return !success;
	}

	/**
	 * Method that builds the message body of the 'resume'.
	 * @param resume {@link ResumeMonitoriza} being processed.
	 * @param mapApps {@link ApplicationResumeDTO} data alert of the 'resume'.
	 * @return String that represents message body of the 'resume'.
	 */
	private String getResumeBody(ResumeMonitoriza resume, Map<String, ApplicationResumeDTO> mapApps) {
		
		StringBuilder body = new StringBuilder();
		// <Este correo ha sido generado automáticamente desde una dirección que no acepta correos entrantes, por favor, no responda.>
		body.append(Language.getResTaskMonitoriza(ITaskResumeMailText.RESUME_HEADER));
		body.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		
		for (Map.Entry<String, ApplicationResumeDTO> entry : mapApps.entrySet()) {
				
			body.append(getSeverityCount(entry));
			body.append(getAlertBreakdown(entry));
			body.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
		
		return body.toString();
	}

	/**
	 * <p>Method that builds a list of alert data for the 'resume' message body:</p>
	 * 
	 * <p> Desglose de alarmas:</p>
	 * <p> [timestamp] - [alert_type] - [severity] - [msg]</p>
	 * <p>	.....</p>
	 * <p>	.....</p>
	 * <p> [timestamp] - [alert_type] - [severity] - [msg]</p>
	 * </p>
	 * <p>for each alert of the {@link ApplicationMonitoriza} being presented in the 'resume'.</p>
	 * 
	 * @param entry {@link Entry<String, ApplicationResumeDTO>} with the pair 'appname' , {@link ApplicationResumeDTO} being processed.
	 * @return String that represents the data alert breakdown of a {@link ApplicationMonitoriza}.
	 */
	private String getAlertBreakdown(Entry<String, ApplicationResumeDTO> entry) {
		
		StringBuilder alertBreakdown = new StringBuilder();
		
		// Desglose de alarmas:
		alertBreakdown.append(Language.getResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_BRAKDOWN_HEADER));
		alertBreakdown.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		
		for (AlertResumeDTO alertResume : entry.getValue().getAlertas()) {
			
			// [timestamp] - [alert_type] - [severity] - [msg]
			alertBreakdown.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_BREAKDOWN_DETAIL, new Object[]{alertResume.getFecha().format(alertResume.getFormatter()), alertResume.getTipo(), alertResume.getSeveridad(), alertResume.getMensaje()}));
			alertBreakdown.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
				
		return alertBreakdown.toString();
	}

	/**
	 * <p>Method that builds a summary with the number of alerts per each severity, for the 'resume' message body:</p>
	 * </p>
	 * <p>Aplicación [appname]:</p>
	 * <p>Se han producido el siguiente número de alarmas por criticidad:</p>
	 * <p>	[n] alarmas con criticidad [severity]</p>
	 * <p>		......</p>
	 * <p>		......</p>
	 * <p>	[n] alarmas con criticidad [severity]</p>
	 * </p>
	 * @param entry {@link Entry<String, ApplicationResumeDTO> entry} with the pair ['appname' , {@link ApplicationResumeDTO}] being processed.
	 * @return String that represents the summary with the number of alerts per each severity.
	 */
	private Object getSeverityCount(Entry<String, ApplicationResumeDTO> entry) {
		
		StringBuilder severityCount = new StringBuilder();
		
		// Aplicación [appname]: \n\nSe han producido el siguiente número de alarmas por criticidad:
		severityCount.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_HEADER_APP, new Object[] {entry.getKey()}));
		severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING).append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		
		// [n] alarmas con criticidad [severity].
		// ...
		
		if (entry.getValue().getNumFatal() > 0) {
			
			severityCount.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_SEVERITY_COUNT, new Object[]{entry.getValue().getNumFatal(), IAlertSeverityConstants.SEVERITY_FATAL}));
			severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
		
		if (entry.getValue().getNumError() > 0) {
			
			severityCount.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_SEVERITY_COUNT, new Object[]{entry.getValue().getNumError(), IAlertSeverityConstants.SEVERITY_ERROR}));
			severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
		
		if (entry.getValue().getNumWarn() > 0) {
			
			severityCount.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_SEVERITY_COUNT, new Object[]{entry.getValue().getNumWarn(), IAlertSeverityConstants.SEVERITY_WARNING}));
			severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
		
		if (entry.getValue().getNumInfo() > 0) {
			
			severityCount.append(Language.getFormatResTaskMonitoriza(ITaskResumeMailText.RESUME_ALERT_SEVERITY_COUNT, new Object[]{entry.getValue().getNumInfo(), IAlertSeverityConstants.SEVERITY_INFO}));
			severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		}
		
		severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		severityCount.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
		
		return severityCount.toString();
	}

	/**
	 * Method that updates the number of alerts of a certain severity.
	 * @param appResumeDto {@link ApplicationResumeDTO} that contains the data being updated.
	 * @param severity String that represents the name of the severity whose alert number is being updated.
	 */
	private void updateNumberServerity(ApplicationResumeDTO appResumeDto, String severity) {
				
		if (IAlertSeverityConstants.SEVERITY_FATAL.equals(severity)) {
			appResumeDto.addNumFatal();
		} else if (IAlertSeverityConstants.SEVERITY_ERROR.equals(severity)) {
			appResumeDto.addNumError();
		} else if (IAlertSeverityConstants.SEVERITY_WARNING.equals(severity)) {
			appResumeDto.addNumWarn();
		} else if (IAlertSeverityConstants.SEVERITY_INFO.equals(severity)) {
			appResumeDto.addNumInfo();
		}
	}

	/**
	 * Method that calculate the datetime limit of the alert being included in the 'resume'.
	 * @param resume {@link ResumeMonitoriza} that represents the 'resume' whose datetime limit is being calculated. 
	 * @return {@link Date} that represents the datetime limit.
	 */
	private Date resolveTimeLimit(ResumeMonitoriza resume) {
		
		Long lastSent = resume.getLastSentTime() == null?null:resume.getLastSentTime().getTime();
		Date limit = null;
		
		if (lastSent != null) {
			
			LOGGER.info("Existe fecha de ultimo envio. Se calcula el limite de alertas en base a este valor...");
			limit = resume.getLastSentTime();
			
		} else {
			
			try {
				
				LOGGER.info("No existe fecha de ultimo envio. Se calcula el limite de alertas como la diferencia entre el momento actual y la periodicidad...");
				limit = UtilsFecha.sumarHoras(new Date(), Math.toIntExact(Math.negateExact(resume.getPeriodicity())));
				
			} catch (ParseException e) {
				
				LOGGER.error("Error parseando la periodicidad del resumen [" + resume.getName() + "] con valor [" + resume.getPeriodicity() + "]", e);
				
			}
		}
		
		return limit;
	}
	
	/**
	 * Method that validate if a mail server configuration is complete and not null.
	 * @param configMailServer {@link ConfServerMail} that represents the mail server configuration being validated.
	 */
	private void validateMailServerConfig(ConfServerMail configMailServer) throws AlertException {
		
		if (configMailServer == null) {
			
			throw new AlertException("No existe configuracion del servidor de correos en el sistema");
			
		} else if (configMailServer.getHostMail() == null || configMailServer.getHostMail().isEmpty() || configMailServer.getPortMail() == null || configMailServer.getPortMail() < 0) {
			
			throw new AlertException("La configuracion del servidor de correos no es correcta");
		}
		
	}
	
}
