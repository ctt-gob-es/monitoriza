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
 * <b>File:</b><p>es.gob.eventmanager.bo.EventManagerBO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 09/11/2021.
 */
package es.gob.eventmanager.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.eventmanager.bo.IEventManagerBO;
import es.gob.eventmanager.constant.NumberConstants;
import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.persistence.model.entity.AlertAudit;
import es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertMailNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertSeverityMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertSystemMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertTypeMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ApplicationMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ConfServerMail;
import es.gob.eventmanager.persistence.model.entity.TemplateMonitoriza;
import es.gob.eventmanager.persistence.model.repository.AlertAuditRepository;
import es.gob.eventmanager.persistence.model.repository.AlertConfigMonitorizaRepository;
import es.gob.eventmanager.persistence.model.repository.AlertGraylogNoticeConfigRepository;
import es.gob.eventmanager.persistence.model.repository.AlertMailNoticeConfigRepository;
import es.gob.eventmanager.persistence.model.repository.AlertSeverityMonitorizaRepository;
import es.gob.eventmanager.persistence.model.repository.AlertTypeMonitorizaRepository;
import es.gob.eventmanager.persistence.model.repository.ApplicationMonitorizaRepository;
import es.gob.eventmanager.persistence.model.repository.ConfServerMailRepository;
import es.gob.eventmanager.persistence.model.repository.TemplateMonitorizaRepository;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 09/11/2021.
 */
@Service("eventManagerBO")
public class EventManagerBO implements IEventManagerBO {
	
	@Autowired
	ApplicationMonitorizaRepository appRepository;
	
	@Autowired
	AlertAuditRepository alertAuditRepository;
	
	@Autowired
	AlertConfigMonitorizaRepository alertConfigRepository;
	
	@Autowired
	TemplateMonitorizaRepository templateRepository;
	
	@Autowired
	AlertTypeMonitorizaRepository alertTypeRepository;
	
	@Autowired
	AlertGraylogNoticeConfigRepository gsNoticeRepository;
	
	@Autowired
	AlertMailNoticeConfigRepository mailNoticeRepository;
	
	@Autowired
	ConfServerMailRepository configMailRepository;
	
	@Autowired
	AlertSeverityMonitorizaRepository alertSeverityRepository;
		
	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getApplicationMonitorizaByName(java.lang.String)
	 */
	@Override
	@Transactional
	public ApplicationMonitoriza getApplicationMonitorizaByName(String appName) throws EventManagerException {
		
		ApplicationMonitoriza app = null;
		
		try {	
			app = appRepository.findByName(appName);
		} catch (DataAccessException e) {
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return app;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#registerAlertAudit(es.gob.eventmanager.persistence.model.entity.AlertAudit)
	 */
	@Override
	@Transactional
	public void registerAlertAudit(AlertAudit alertAudit) throws EventManagerException {
		
		try {	
			
			alertAuditRepository.save(alertAudit);
			
		} catch (DataAccessException e) {
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAlertConfigByAlertTypeAndApplication(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public AlertConfigMonitoriza getAlertConfigByAlertTypeAndApplication(Long idAlertType, Long idApp) throws EventManagerException {
		
		AlertConfigMonitoriza alertConfigMonitoriza = null;
		
		try {	
			alertConfigMonitoriza = alertConfigRepository.findByAlertTypeAndApp(idAlertType, idApp);
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return alertConfigMonitoriza;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#loadLazyListAlertTypesInTemplate(es.gob.eventmanager.persistence.model.entity.TemplateMonitoriza)
	 */
	@Override
	@Transactional
	public void loadLazyListAlertTypesInTemplate(TemplateMonitoriza template) throws EventManagerException {
		
		TemplateMonitoriza loadedTemplate = templateRepository.findByIdTemplateMonitoriza(template.getIdTemplateMonitoriza());
		List<AlertTypeMonitoriza> alertTypes = loadedTemplate.getListAlertTypes();
		alertTypes.size();
		
		template.setListAlertTypes(alertTypes);		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAllAlertTypes()
	 */
	@Override
	@Transactional
	public List<AlertTypeMonitoriza> getAllAlertTypes() throws EventManagerException {
		
		List<AlertTypeMonitoriza> list = null;
		
		try {
			
			list = alertTypeRepository.findAll();
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAlertConfigById(java.lang.Long)
	 */
	@Override
	@Transactional
	public AlertConfigMonitoriza getAlertConfigById(Long idAlerConfig) throws EventManagerException {
		
		AlertConfigMonitoriza alertConfigMonitoriza = null;
		
		try {	
			alertConfigMonitoriza = alertConfigRepository.getOne(idAlerConfig);
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return alertConfigMonitoriza;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAllAlertConfig()
	 */
	@Override
	@Transactional
	public List<AlertConfigMonitoriza> getAllAlertConfig() throws EventManagerException {
		
		List<AlertConfigMonitoriza> list = null;
		
		try {
			
			list = alertConfigRepository.findAll();
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#saveAlertConfig(es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza)
	 */
	@Override
	@Transactional
	public void saveAlertConfig(AlertConfigMonitoriza alertConfig) throws EventManagerException {
		
		try {
		
			alertConfigRepository.save(alertConfig);
		
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAllSystemGSNoticeConfig(java.lang.Long)
	 */
	@Override
	public List<AlertGraylogNoticeConfig> getAllSystemGSNoticeConfig(Long idNotSysConfig) throws EventManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getAllSystemMailNoticeConfig(java.lang.Long)
	 */
	@Override
	public List<AlertMailNoticeConfig> getAllSystemMailNoticeConfig(Long idNotSysConfig) throws EventManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getGLNoticesByAlertConfig(java.lang.Long)
	 */
	@Override
	public List<AlertGraylogNoticeConfig> getGLNoticesByAlertConfig(Long idAlertConfig) throws EventManagerException {
		
		List<AlertGraylogNoticeConfig> list = null;
		
		try {
			
			list = gsNoticeRepository.findByAlertConfig(idAlertConfig);
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getMailNoticesByAlertConfig(java.lang.Long)
	 */
	@Override
	public List<AlertMailNoticeConfig> getMailNoticesByAlertConfig(Long idAlertConfig) throws EventManagerException {
		
		List<AlertMailNoticeConfig> list = null;
		
		try {
			
			list = mailNoticeRepository.findByAlertConfig(idAlertConfig);
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getMailServerConfig()
	 */
	@Override
	public ConfServerMail getMailServerConfig() throws EventManagerException {
			
		List<ConfServerMail> list = null;
		
		try {
			
			list = configMailRepository.findAll();
			
			if (list.size() == 0) {
				throw new EventManagerException("No existe servidor de correo configurado en el sistema");
			}
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return list.get(NumberConstants.NUM0);
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#getSeverityById(java.lang.Long)
	 */
	@Override
	public AlertSeverityMonitoriza getSeverityById(Long idSeverity) throws EventManagerException {
		
		AlertSeverityMonitoriza severity = null;
		
		try {
			
			severity = alertSeverityRepository.findBySeverityTypeId(idSeverity);
			
		} catch (DataAccessException e) {
			
			throw new EventManagerException(e.getMessage(), e.getCause());
		}
		
		return severity;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.eventmanager.bo.IEventManagerBO#loadLazyAlertSystemsInAlertConfig(es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza)
	 */
	@Override
	@Transactional
	public void loadLazyAlertSystemsInAlertConfig(AlertConfigMonitoriza alertConfig) throws EventManagerException {
		
		try {
			
    		AlertConfigMonitoriza loadedConfig = alertConfigRepository.findByIdAlertConfigMonitoriza(alertConfig.getIdAlertConfigMonitoriza());
    		List<AlertSystemMonitoriza> alertSystems = loadedConfig.getSystemsMonitoriza();
    		alertSystems.size();
    		
    		alertConfig.setSystemsMonitoriza(alertSystems);
    		
		} catch (DataAccessException e) {
    		throw new EventManagerException(e.getMessage(), e.getCause());
    	}
		
	}	

}
