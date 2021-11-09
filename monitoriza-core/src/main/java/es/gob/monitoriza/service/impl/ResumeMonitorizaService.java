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
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.ResumeDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertMailResumeConfigRepository;
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

	@Autowired
	private AlertMailResumeConfigRepository mailRepository;

	@Override
	public ResumeMonitoriza getResumeMonitorizaById(final Long resumeId) {
		return this.repository.findByIdResumeMonitoriza(resumeId);
	}

	@Override
	@Transactional
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
		ResumeMonitoriza resumeMonitoriza = null;

		if (resumeDto.getIdResumeMonitoriza() != null) {
			resumeMonitoriza = this.repository.findByIdResumeMonitoriza(resumeDto.getIdResumeMonitoriza());
		} else {
			resumeMonitoriza = new ResumeMonitoriza();
		}

		resumeMonitoriza.setName(resumeDto.getName());
		resumeMonitoriza.setDescription(resumeDto.getDescription());
		resumeMonitoriza.setPeriodicity(resumeDto.getPeriodicity());
		if (Boolean.TRUE == resumeDto.getIsEnabled()) {
			resumeMonitoriza.setEnabled("S"); //$NON-NLS-1$
		} else {
			resumeMonitoriza.setEnabled("N"); //$NON-NLS-1$
		}

		return this.repository.save(resumeMonitoriza);
	}


}
