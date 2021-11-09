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
 * <b>File:</b><p>es.gob.eventmanager.bo.IEventManagerBO.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 09/11/2021.
 */
package es.gob.eventmanager.bo;

import java.util.List;

import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.persistence.model.entity.AlertAudit;
import es.gob.eventmanager.persistence.model.entity.AlertConfigMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertGraylogNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertMailNoticeConfig;
import es.gob.eventmanager.persistence.model.entity.AlertSeverityMonitoriza;
import es.gob.eventmanager.persistence.model.entity.AlertTypeMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ApplicationMonitoriza;
import es.gob.eventmanager.persistence.model.entity.ConfServerMail;
import es.gob.eventmanager.persistence.model.entity.TemplateMonitoriza;

/** 
 * <p>Interface for business logic.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 09/11/2021.
 */
public interface IEventManagerBO {

	/**
	 * Metodo que obtiene la entidad {@link ApplicationMonitoriza} de la persistencia a partir de su nombre 
	 * @param appName String que representa el atributo name de la entidad que queremos obtener
	 * @return La entidad de base de datos {@link ApplicationMonitoriza} cuyo atributo name 
	 * coincide con el parametro proporcionado
	 */
	ApplicationMonitoriza getApplicationMonitorizaByName(String appName) throws EventManagerException;
	
	/**
	 * Me
	 * @param alertAudit
	 * @throws EventManagerException
	 */
	void registerAlertAudit(AlertAudit alertAudit) throws EventManagerException;
	
	/**
	 * 
	 * @param idAlerConfig
	 * @return
	 */
	AlertConfigMonitoriza getAlertConfigById(final Long idAlerConfig) throws EventManagerException;
	
	/**
	 * 
	 * @param idAlerType
	 * @param idApp
	 * @return
	 */
	AlertConfigMonitoriza getAlertConfigByAlertTypeAndApplication(final Long idAlerType, final Long idApp) throws EventManagerException;
	
	/**
	 * 
	 * @param template
	 */
	void loadLazyListAlertTypesInTemplate(TemplateMonitoriza template) throws EventManagerException;
	
	/**
	 * 
	 * @return
	 */
	List<AlertTypeMonitoriza> getAllAlertTypes() throws EventManagerException;
	
	/**
	 * 
	 * @return
	 * @throws EventManagerException
	 */
	List<AlertConfigMonitoriza> getAllAlertConfig() throws EventManagerException;
	
	/**
	 * 
	 * @param alertConfig
	 */
	void saveAlertConfig(AlertConfigMonitoriza alertConfig) throws EventManagerException;
	
	/**
	 * 
	 * @param idNotSysConfig
	 * @return
	 * @throws EventManagerException
	 */
	List<AlertGraylogNoticeConfig> getAllSystemGSNoticeConfig(final Long idNotSysConfig) throws EventManagerException;
	
	/**
	 * 
	 * @param idNotSysConfig
	 * @return
	 * @throws EventManagerException
	 */
	List<AlertMailNoticeConfig> getAllSystemMailNoticeConfig(final Long idNotSysConfig) throws EventManagerException;
	
	/**
	 * 
	 * @param idAlertConfig
	 * @return
	 * @throws EventManagerException
	 */
	List<AlertGraylogNoticeConfig> getGLNoticesByAlertConfig(final Long idAlertConfig) throws EventManagerException;
	
	/**
	 * 
	 * @param idAlertConfig
	 * @return
	 * @throws EventManagerException
	 */
	List<AlertMailNoticeConfig> getMailNoticesByAlertConfig(final Long idAlertConfig) throws EventManagerException;
	
	/**
	 * 
	 * @return
	 * @throws EventManagerException
	 */
	ConfServerMail getMailServerConfig() throws EventManagerException;
	
	/**
	 * 
	 * @param idSeverity
	 * @return
	 * @throws EventManagerException
	 */
	AlertSeverityMonitoriza getSeverityById(final Long idSeverity) throws EventManagerException;
	
	/**
	 * 
	 * @param template
	 */
	void loadLazyAlertSystemsInAlertConfig(AlertConfigMonitoriza alertConfig) throws EventManagerException;
	
}
