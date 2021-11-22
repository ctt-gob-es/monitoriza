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
 * <b>File:</b><p>es.gob.monitoriza.task.AlertStatisticsTask.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.cron;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.IAuditToStatisticsControlResults;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.AuditToStatisticDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAuditControl;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics;
import es.gob.monitoriza.service.IAlertStatisticsBIService;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;

/** 
 * <p>Class that define the tasks related to the Alert statistics from Event Manager.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Component
public class AlertStatisticsJob implements SchedulerObjectInterface {
		
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
			
	/**
	 * Attribute that represents the service object for accessing the valid service repository.
	 */
	@Autowired
	private IAlertStatisticsBIService biService;
	
	/**
     * Attribute that represents the task execution result. 
     */
    @SuppressWarnings("rawtypes")
	private ScheduledFuture future;
	
	/**
	 * Groups data from ALERT AUDIT pending data and feeds the STATISTICS model.
	 */
	private void feedStatisticsFromAlertAudit() {
		
		List<AlertAuditControl> list = new ArrayList<AlertAuditControl>();
		
		try {
			list = biService.getPendingControlExecutions();
		} catch (DatabaseException e) {
			
			LOGGER.error("No se han podido recuperar los registros de la tabla de control del proceso de volcado 'AUDIT->STATISTICS' de alertas",  e.getCause());
		}
		
		for (AlertAuditControl control : list) {
			
			// Se actualizan los momentos de ejecucion
			control.setExecBegin(new Date());
			
			try {
							
				dumpDataRangeDate(control.getAuditBegin(), control.getAuditEnd());				
				control.setResult(IAuditToStatisticsControlResults.RESULT_OK);
							
			} catch (DatabaseException e) {
				
				control.setResult(IAuditToStatisticsControlResults.RESULT_ERROR);
				LOGGER.error(e.getMessage(), e);
			}			
			
			control.setExecEnd(new Date());
			biService.saveAlertAuditControl(control);
		}
		
	// Pruebas
//		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
//		
//		Date begin = new Date();
//		Date end = new Date();
//		try {
//			begin = formatter.parse("27-10-2021 00:00:00");
//			end = formatter.parse("15-11-2021 23:59:59");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//				
//		biService.auditToStatisticsDumpData(begin, end);
	}

	/**
	 * 
	 * @param auditBegin
	 * @param auditEnd
	 */
	@Transactional
	private void dumpDataRangeDate(Date auditBegin, Date auditEnd) throws DatabaseException {
		
		List<AuditToStatisticDTO> dataToDump = biService.auditToStatisticsDumpData(auditBegin, auditEnd);
		
    	AlertStatistics stats = null;
    	String moment = null;
    	try {    		
    		
    		for (AuditToStatisticDTO dto : dataToDump) {
    			
    			stats = new AlertStatistics();
    			
    			moment = dto.getMoment();
    			
    			// Calculamos las dimensiones
    			resolveDIMApplication(stats, dto);
    			resolveDIMNode(stats, dto);
    			resolveDIMSeverity(stats, dto);
    			resolveDIMTemplate(stats, dto);
    			resolveDIMType(stats, dto);
    			
    			stats.setOccurrences(dto.getOccurrences());
    			stats.setTimestamp(Long.parseLong(moment));
    			
    			biService.saveAlertStatistics(stats);
    		}
    		
    	} catch (DatabaseException e) {
    		
    		throw new DatabaseException(e, "Ha ocurrido un error procesando el volcado de auditoria para la fecha: " + moment);
    		
    	}
		
	}

	/**
	 * 
	 * @param stats
	 * @param dto
	 */
	private void resolveDIMType(AlertStatistics stats, AuditToStatisticDTO dto) throws DatabaseException {
		
		AlertDIMType dimType = biService.getDIMTypeByName(dto.getTypeName());
				
		if (dimType != null) {
			    				
			stats.setType(dimType);
			
		} else {
			
			dimType = new AlertDIMType();
			dimType.setName(dto.getTypeName());
			AlertDIMType newDimType = biService.saveDimType(dimType);
			stats.setType(newDimType);
		}
		
	}

	/**
	 * 
	 * @param stats
	 * @param dto
	 */
	private void resolveDIMTemplate(AlertStatistics stats, AuditToStatisticDTO dto) throws DatabaseException {
		
		AlertDIMTemplate dimTemplate = biService.getDIMTemplateByName(dto.getTemplateName());
				
		if (dimTemplate != null) {
			    				
			stats.setTemplate(dimTemplate);
			
		} else {
			
			dimTemplate = new AlertDIMTemplate();
			dimTemplate.setName(dto.getTemplateName());
			AlertDIMTemplate newDimTemplate = biService.saveDimTemplate(dimTemplate);
			stats.setTemplate(newDimTemplate);
		}
	}

	/**
	 * 
	 * @param stats
	 * @param dto
	 */
	private void resolveDIMSeverity(AlertStatistics stats, AuditToStatisticDTO dto) throws DatabaseException {
		
		AlertDIMSeverity dimSeverity = biService.getDIMSeverityByName(dto.getSeverityName());
				
		if (dimSeverity != null) {
			    				
			stats.setSeverity(dimSeverity);
			
		} else {
			
			dimSeverity = new AlertDIMSeverity();
			dimSeverity.setName(dto.getSeverityName());
			AlertDIMSeverity newDimSeverity = biService.saveDimSeverity(dimSeverity);
			stats.setSeverity(newDimSeverity);
		}
		
	}

	/**
	 * 
	 * @param stats
	 * @param dto
	 */
	private void resolveDIMNode(AlertStatistics stats, AuditToStatisticDTO dto) throws DatabaseException {
		
		AlertDIMNode dimNode = biService.getDIMNodeByName(dto.getNodeName());
				
		if (dimNode != null) {
			    				
			stats.setNode(dimNode);
			
		} else {
			
			dimNode = new AlertDIMNode();
			dimNode.setName(dto.getNodeName());
			AlertDIMNode newDimNode = biService.saveDimNode(dimNode);
			stats.setNode(newDimNode);
		}
		
	}

	/**
	 * @param stats
	 * @param dto
	 */
	private void resolveDIMApplication(AlertStatistics stats, AuditToStatisticDTO dto) throws DatabaseException {
		
		AlertDIMApplication dimApp = biService.getDIMAppByName(dto.getAppName());
				
		if (dimApp != null) {
			    				
			stats.setApplication(dimApp);
			
		} else {
			
			dimApp = new AlertDIMApplication();
			dimApp.setName(dto.getAppName());
			AlertDIMApplication newDimApp = biService.saveDimApplication(dimApp);
			stats.setApplication(newDimApp);
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.cron.SchedulerObjectInterface#start()
	 */
	@Override
	public void start() {
		future = new ConcurrentTaskScheduler().schedule(new Runnable() {
            @Override
            public void run() {
            	try {
            		feedStatisticsFromAlertAudit();
				} catch (Exception e) {
					LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE007), e.getCause());
				}
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
            	Date nextExec = null;

            	try {
                	String cronExpression = StaticMonitorizaConfig.getProperty(StaticConstants.CRON_FEED_ALERT_STATISTICS);
                	CronTrigger trigger = new CronTrigger(cronExpression);
                    nextExec = trigger.nextExecutionTime(triggerContext);
            	} catch (IllegalArgumentException iae) {
            		
            	}
                
                return nextExec;
            }
        });	
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.cron.SchedulerObjectInterface#stop()
	 */
	@Override
	public void stop() {
		future.cancel(false);
		
	}

}
