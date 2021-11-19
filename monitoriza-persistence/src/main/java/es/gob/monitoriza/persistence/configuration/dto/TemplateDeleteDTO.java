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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AlarmDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.List;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigSystem;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;

/**
 * <p>
 * Class that represents the backing form for adding/editing an system notification.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 * @version 1.1, 25/01/2019.
 */
public class TemplateDeleteDTO {

	private TemplateMonitoriza template;
	
	private List<AlertTypeMonitoriza> listAlertTypeMonitoriza;
	
	private List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza;
	
	private List<ApplicationMonitoriza> listApplicationMonitoriza;
	
	private List<AlertResumeType> listAllAlertResumeType;
	
	private List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza;
	
	private List<AlertConfigSystem> listAllAlertConfigSystem;
	
	private List<AlertMailNoticeConfig> listAllAlertMailNoticeConfig;
	
	private List<AlertGraylogNoticeConfig> listAllAlertGraylogNoticeConfig;

	/**
	 * @return the template
	 */
	public TemplateMonitoriza getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(TemplateMonitoriza template) {
		this.template = template;
	}
	
	

	/**
	 * @return the listAlertTypeMonitoriza
	 */
	public List<AlertTypeMonitoriza> getListAlertTypeMonitoriza() {
		return listAlertTypeMonitoriza;
	}

	/**
	 * @param listAlertTypeMonitoriza the listAlertTypeMonitoriza to set
	 */
	public void setListAlertTypeMonitoriza(List<AlertTypeMonitoriza> listAlertTypeMonitoriza) {
		this.listAlertTypeMonitoriza = listAlertTypeMonitoriza;
	}

	/**
	 * @return the listAlertTypeTemplateMonitoriza
	 */
	public List<AlertTypeTemplateMonitoriza> getListAlertTypeTemplateMonitoriza() {
		return listAlertTypeTemplateMonitoriza;
	}

	/**
	 * @param listAlertTypeTemplateMonitoriza the listAlertTypeTemplateMonitoriza to set
	 */
	public void setListAlertTypeTemplateMonitoriza(List<AlertTypeTemplateMonitoriza> listAlertTypeTemplateMonitoriza) {
		this.listAlertTypeTemplateMonitoriza = listAlertTypeTemplateMonitoriza;
	}

	/**
	 * @return the listApplicationMonitoriza
	 */
	public List<ApplicationMonitoriza> getListApplicationMonitoriza() {
		return listApplicationMonitoriza;
	}

	/**
	 * @param listApplicationMonitoriza the listApplicationMonitoriza to set
	 */
	public void setListApplicationMonitoriza(List<ApplicationMonitoriza> listApplicationMonitoriza) {
		this.listApplicationMonitoriza = listApplicationMonitoriza;
	}

	/**
	 * @return the listAllAlertResumeType
	 */
	public List<AlertResumeType> getListAllAlertResumeType() {
		return listAllAlertResumeType;
	}

	/**
	 * @param listAllAlertResumeType the listAllAlertResumeType to set
	 */
	public void setListAllAlertResumeType(List<AlertResumeType> listAllAlertResumeType) {
		this.listAllAlertResumeType = listAllAlertResumeType;
	}

	/**
	 * @return the listAllAlertConfigMonitoriza
	 */
	public List<AlertConfigMonitoriza> getListAllAlertConfigMonitoriza() {
		return listAllAlertConfigMonitoriza;
	}

	/**
	 * @param listAllAlertConfigMonitoriza the listAllAlertConfigMonitoriza to set
	 */
	public void setListAllAlertConfigMonitoriza(List<AlertConfigMonitoriza> listAllAlertConfigMonitoriza) {
		this.listAllAlertConfigMonitoriza = listAllAlertConfigMonitoriza;
	}

	/**
	 * @return the listAllAlertConfigSystem
	 */
	public List<AlertConfigSystem> getListAllAlertConfigSystem() {
		return listAllAlertConfigSystem;
	}

	/**
	 * @param listAllAlertConfigSystem the listAllAlertConfigSystem to set
	 */
	public void setListAllAlertConfigSystem(List<AlertConfigSystem> listAllAlertConfigSystem) {
		this.listAllAlertConfigSystem = listAllAlertConfigSystem;
	}

	/**
	 * @return the listAllAlertMailNoticeConfig
	 */
	public List<AlertMailNoticeConfig> getListAllAlertMailNoticeConfig() {
		return listAllAlertMailNoticeConfig;
	}

	/**
	 * @param listAllAlertMailNoticeConfig the listAllAlertMailNoticeConfig to set
	 */
	public void setListAllAlertMailNoticeConfig(List<AlertMailNoticeConfig> listAllAlertMailNoticeConfig) {
		this.listAllAlertMailNoticeConfig = listAllAlertMailNoticeConfig;
	}

	/**
	 * @return the listAllAlertGraylogNoticeConfig
	 */
	public List<AlertGraylogNoticeConfig> getListAllAlertGraylogNoticeConfig() {
		return listAllAlertGraylogNoticeConfig;
	}

	/**
	 * @param listAllAlertGraylogNoticeConfig the listAllAlertGraylogNoticeConfig to set
	 */
	public void setListAllAlertGraylogNoticeConfig(List<AlertGraylogNoticeConfig> listAllAlertGraylogNoticeConfig) {
		this.listAllAlertGraylogNoticeConfig = listAllAlertGraylogNoticeConfig;
	}
	
	
	

}
