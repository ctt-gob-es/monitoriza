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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.dto.UserEditDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TemplateMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.ResumeMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.ResumeDatatableRepository;
import es.gob.monitoriza.service.IResumeMonitorizaService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("resumeMonitorizaService")
public class ResumeMonitorizaService implements IResumeMonitorizaService {

	@Autowired
	private ResumeMonitorizaRepository repository;

	@Autowired
	private ResumeDatatableRepository dtRepository;

	@Override
	public ResumeMonitoriza getResumeMonitorizaById(final Long resumeId) {
		return this.repository.findByIdResumeMonitoriza(resumeId);
	}

	@Override
	public void deleteResumeMonitoriza(final Long resumeId) {
		this.repository.deleteById(resumeId);
	}

	@Override
	public Iterable<ResumeMonitoriza> getAllResumeMonitoriza() {
		return this.repository.findAll();
	}

	@Override
	public DataTablesOutput<ResumeMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public ResumeMonitoriza saveResumeMonitoriza(final ResumeDTO resumeDto) {
		final ResumeMonitoriza resumeMonitoriza = null;

		/*if (templateDto.get != null) {
			userMonitoriza = this.repository.findByIdTemplateMonitoriza(templateDto.getIdUserMonitoriza());
		} else {
			userMonitoriza = new UserMonitoriza();
		}

		templateMonitoriza.setLogin(templateDto.getLogin());
		templateMonitoriza.setAttemptsNumber(NumberConstants.NUM0);
		templateMonitoriza.setEmail(templateDto.getEmail());
		templateMonitoriza.setIsBlocked(Boolean.FALSE);
		templateMonitoriza.setLastAccess(null);
		templateMonitoriza.setLastIpAccess(null);
		templateMonitoriza.setName(templateDto.getName());
		templateMonitoriza.setSurnames(templateDto.getSurnames());*/

		return this.repository.save(resumeMonitoriza);
	}

	@Override
	public TemplateMonitoriza updateUserMonitoriza(final UserEditDTO userEditDto) {
		// TODO Auto-generated method stub
		return null;
	}


}
