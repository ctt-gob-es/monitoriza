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
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.ApplicationDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.ApplicationMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.TemplateMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.ApplicationDatatableRepository;
import es.gob.monitoriza.service.IApplicationMonitorizaService;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("applicationMonitorizaService")
public class ApplicationMonitorizaService implements IApplicationMonitorizaService {

	@Autowired
	private ApplicationMonitorizaRepository appMonitorizaRepository;

	@Autowired
	private TemplateMonitorizaRepository templMonitorizaRepository;

	@Autowired
	private ApplicationDatatableRepository dtRepository;

	@Override
	public ApplicationMonitoriza getApplicationMonitorizaById(final Long appId) {
		return this.appMonitorizaRepository.findByIdApplicationMonitoriza(appId);
	}

	@Override
	public void deleteApplicationMonitoriza(final Long appId) {
		this.appMonitorizaRepository.deleteById(appId);
	}

	@Override
	public Iterable<ApplicationMonitoriza> getAllApplicationMonitoriza() {
		return this.appMonitorizaRepository.findAll();
	}

	@Override
	public DataTablesOutput<ApplicationMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public ApplicationMonitoriza saveApplicationMonitoriza(final ApplicationDTO appDto) {
		ApplicationMonitoriza appMonitoriza = null;

		if (appDto.getIdApplicationMonitoriza() != null) {
			appMonitoriza = this.appMonitorizaRepository.findByIdApplicationMonitoriza(appDto.getIdApplicationMonitoriza());
		} else {
			appMonitoriza = new ApplicationMonitoriza();
		}

		appMonitoriza.setName(appDto.getName());
		if (Boolean.TRUE == appDto.getEnabled()){
			appMonitoriza.setEnabled("S"); //$NON-NLS-1$
		} else {
			appMonitoriza.setEnabled("N"); //$NON-NLS-1$
		}
		appMonitoriza.setCipherKey(appDto.getCipherKey());
		appMonitoriza.setAppKey(UtilsStringChar.randomString(99));
		appMonitoriza.setResponsibleName(appDto.getResponsibleName());
		appMonitoriza.setResponsibleEmail(appDto.getResponsibleEmail());
		appMonitoriza.setResponsiblePhone(appDto.getResponsiblePhone());

		final TemplateMonitoriza templateMonitoriza = this.templMonitorizaRepository.findByIdTemplateMonitoriza(appDto.getTemplateID());
		appMonitoriza.setTemplateMonitoriza(templateMonitoriza);

		return this.appMonitorizaRepository.save(appMonitoriza);
	}

	@Override
	public List<ApplicationMonitoriza> getAllApplicationMonitorizaByTemplateMonitoriza(TemplateMonitoriza template) {
		return appMonitorizaRepository.findAllByTemplateMonitoriza(template);
	}


}
