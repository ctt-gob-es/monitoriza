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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertTypeTemplateMonitorizaRepository;
import es.gob.monitoriza.service.IAlertTypeTemplateMonitorizaService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertTypeTemplateService")
public class AlertTypeTemplateMonitorizaService implements IAlertTypeTemplateMonitorizaService {

	@Autowired
	private AlertTypeTemplateMonitorizaRepository repository;

	
	@Override
	@Transactional
	public AlertTypeTemplateMonitoriza saveAlertTypeTemplateMonitoriza(
			AlertTypeTemplateMonitoriza alertTypeTemplateMonitoriza) {
		return this.repository.saveAndFlush(alertTypeTemplateMonitoriza);
	}

	@Override
	public List<AlertTypeTemplateMonitoriza> getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(TemplateMonitoriza template){
		return this.repository.getAllAlertTypeTemplateMonitorizaByTemplateMonitoriza(template);
	}

	@Override
	public void deleteAlertTypeTemplateMonitoriza(AlertTypeTemplateMonitoriza typeTemplate) {
		repository.delete(typeTemplate);
		
	}


}
