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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.UserMonitorizaService.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>6/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.4, 11/05/2022.
 */
package es.gob.monitoriza.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.TemplateDTO;
import es.gob.monitoriza.persistence.configuration.dto.TemplateDeleteDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserEditDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertMailNoticeConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertResumeType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.TemplateMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.TemplateDatatableRepository;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;
import es.gob.monitoriza.service.IAlertConfigSystemService;
import es.gob.monitoriza.service.IAlertGrayLogNoticeConfigService;
import es.gob.monitoriza.service.IAlertMailNoticeConfigService;
import es.gob.monitoriza.service.IAlertResumeTypeService;
import es.gob.monitoriza.service.IAlertTypeMonitorizaService;
import es.gob.monitoriza.service.IAlertTypeTemplateMonitorizaService;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.service.ITemplateMonitorizaService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("templateMonitorizaService")
public class TemplateMonitorizaService implements ITemplateMonitorizaService {

	@Autowired
	private TemplateMonitorizaRepository repository;

	@Autowired
	private TemplateDatatableRepository dtRepository;
	
	@Autowired
	private IAlertTypeMonitorizaService alertTypeMonitorizaService;
	
	@Autowired
	private IAlertTypeTemplateMonitorizaService alertTypeTemplateMonitorizaService;
	
	@Autowired
	private IApplicationMonitorizaService applicationMonitorizaService;

	@Autowired
	private IAlertResumeTypeService alertResumeTypeService;
	
	@Autowired
	private IAlertConfigMonitorizaService alertConfigMonitorizaService;
	
	@Autowired
	private IAlertConfigSystemService alertConfigSystemService;
	
	@Autowired
	private IAlertMailNoticeConfigService alertMailNoticeConfigService;
	
	@Autowired
	private IAlertGrayLogNoticeConfigService alertGrayLogNoticeConfigService;

	@Override
	public TemplateMonitoriza getTemplateMonitorizaById(final Long templateId) {
		return this.repository.findByIdTemplateMonitoriza(templateId);
	}

	@Override
	public void deleteTemplateMonitoriza(final Long templateId) {
		this.repository.deleteById(templateId);
	}

	@Override
	public Iterable<TemplateMonitoriza> getAllTemplateMonitoriza() {
		return this.repository.findAll();
	}

	@Override
	public DataTablesOutput<TemplateMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public TemplateMonitoriza saveTemplateMonitorizaWithDTO(final TemplateDTO templateDto) {
		TemplateMonitoriza templateMonitoriza = null;
		
		if(templateDto.getIdTemplateMonitoriza() !=  null){
			templateMonitoriza = this.getTemplateMonitorizaById(templateDto.getIdTemplateMonitoriza());
			templateMonitoriza.setDescription(templateDto.getDescription());
			templateMonitoriza.setName(templateDto.getTemplate());
		}else{
			templateMonitoriza = new TemplateMonitoriza();
			templateMonitoriza.setDescription(templateDto.getDescription());
			templateMonitoriza.setName(templateDto.getDescription());
		}


		return this.repository.save(templateMonitoriza);
	}
	

	@Override
	@Transactional
	public TemplateMonitoriza saveTemplateMonitoriza(TemplateMonitoriza template) {		
		TemplateMonitoriza templateSave = this.repository.saveAndFlush(template);
		this.repository.flush();

		return templateSave;
	}

	@Override
	public TemplateMonitoriza updateUserMonitoriza(final UserEditDTO userEditDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTemplateMonitorizaWithDTO(TemplateDeleteDTO templateDeleteDTO) {
		//ALERT_APPLICATION
		if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListApplicationMonitoriza())){
			//ALERT_CONFIG
			if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAllAlertConfigMonitoriza())){
				//ALERT_CONFIG_SYSTEM
				if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAllAlertConfigSystem())){					
					//ALERT_GRAYLOG_NOTICE_CONFIG
					if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAllAlertGraylogNoticeConfig())){
						for(AlertGraylogNoticeConfig alertGraylogNoticeConfig: templateDeleteDTO.getListAllAlertGraylogNoticeConfig()){
							alertGrayLogNoticeConfigService.deleteAlertGraylogNoticeConfig(alertGraylogNoticeConfig.getNotSysConfigId());;
						}
					}
					//ALERT_MAIL_NOTICE_CONFIG
					if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAllAlertMailNoticeConfig())){
						for(AlertMailNoticeConfig alertMailNoticeConfig: templateDeleteDTO.getListAllAlertMailNoticeConfig()){
							alertMailNoticeConfigService.deleteAlertMailNoticeConfig(alertMailNoticeConfig.getIdNotSysConfig());
						}
						
					}					
					
				}
				
				for(AlertConfigMonitoriza alertConfigMonitoriza: templateDeleteDTO.getListAllAlertConfigMonitoriza()){
					alertConfigSystemService.deleteAlertConfigSystemByAlertConfigMonitoriza(alertConfigMonitoriza);
					alertConfigMonitorizaService.deleteAlertConfigMonitoriza(alertConfigMonitoriza.getIdAlertConfigMonitoriza());		
					
				}
				
			}
			//ALERT_RESUME_TYPES
			if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAllAlertResumeType())){
				for(AlertResumeType alertResumeType : templateDeleteDTO.getListAllAlertResumeType()){
					alertResumeTypeService.deleteAlertResumeType(alertResumeType.getIdResType());
				}
			}
			
			for(ApplicationMonitoriza applicationMonitoriza : templateDeleteDTO.getListApplicationMonitoriza()){
				applicationMonitorizaService.deleteApplicationMonitoriza(applicationMonitoriza.getIdApplicationMonitoriza());
			}
		}
		
		
		if(CollectionUtils.isNotEmpty(templateDeleteDTO.getListAlertTypeMonitoriza())){
			//ALERT_TYPE_MONITORIZA
			for(AlertTypeTemplateMonitoriza alertTypeTemplateMonitoriza: templateDeleteDTO.getListAlertTypeTemplateMonitoriza()){
				alertTypeTemplateMonitorizaService.deleteAlertTypeTemplateMonitoriza(alertTypeTemplateMonitoriza);
			}
			
			//ALERT_TYPE
			for(AlertTypeMonitoriza alertTypeMonitoriza: templateDeleteDTO.getListAlertTypeMonitoriza()){
				alertTypeMonitorizaService.deleteAlertTypeMonitoriza(alertTypeMonitoriza.getIdTypeMonitoriza());
			}
		}
		
		
		//ALERT_APP_TEMPLATE
		repository.delete(templateDeleteDTO.getTemplate());
		repository.flush();
	}


}
