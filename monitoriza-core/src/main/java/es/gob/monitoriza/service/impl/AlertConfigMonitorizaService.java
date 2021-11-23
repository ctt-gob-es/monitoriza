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

import es.gob.monitoriza.persistence.configuration.dto.AlertConfigDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertConfigMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSeverityMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ApplicationMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertConfigMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertSeverityMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertTypeMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.ApplicationMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlertConfigDatatableRepository;
import es.gob.monitoriza.service.IAlertConfigMonitorizaService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertConfigMonitorizaService")
public class AlertConfigMonitorizaService implements IAlertConfigMonitorizaService {

	@Autowired
	private AlertConfigMonitorizaRepository alertConfigMonitorizaRepository;

	@Autowired
	private AlertTypeMonitorizaRepository alertTypeMonitorizaRepository;

	@Autowired
	private ApplicationMonitorizaRepository applicationMonitorizaRepository;

	@Autowired
	private AlertSeverityMonitorizaRepository alertSeverityRepository;

	@Autowired
	private AlertConfigDatatableRepository dtRepository;

	@Override
	public AlertConfigMonitoriza getAlertConfigMonitorizaById(final Long alertConfigId) {
		return this.alertConfigMonitorizaRepository.findByIdAlertConfigMonitoriza(alertConfigId);
	}

	@Override
	public void deleteAlertConfigMonitoriza(final Long alertConfigId) {
		this.alertConfigMonitorizaRepository.deleteById(alertConfigId);
	}

	@Override
	public Iterable<AlertConfigMonitoriza> getAllAlertConfigMonitoriza() {
		return this.alertConfigMonitorizaRepository.findAll();
	}

	@Override
	public DataTablesOutput<AlertConfigMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public AlertConfigMonitoriza saveAlertConfigMonitoriza(final AlertConfigDTO alertConfigDTO) {

		AlertConfigMonitoriza alertConfig = new AlertConfigMonitoriza();

		if (alertConfigDTO.getIdAlertConfigMonitoriza() != null) {
			alertConfig = this.alertConfigMonitorizaRepository.findByIdAlertConfigMonitoriza(alertConfigDTO.getIdAlertConfigMonitoriza());
		}

		final AlertTypeMonitoriza alertTypeMonitoriza = this.alertTypeMonitorizaRepository.findByIdTypeMonitoriza(alertConfigDTO.getTypeID());
		alertConfig.setAlertTypeMonitoriza(alertTypeMonitoriza);

		final ApplicationMonitoriza appMonitoriza = this.applicationMonitorizaRepository.findByIdApplicationMonitoriza(alertConfigDTO.getAppID());
		alertConfig.setApplicationMonitoriza(appMonitoriza);

		final AlertSeverityMonitoriza severityMonitoriza = this.alertSeverityRepository.findBySeverityTypeId(alertConfigDTO.getSeverity());
		alertConfig.setAlertSeverityMonitoriza(severityMonitoriza);

		if (alertConfigDTO.getIsEnable() != null && alertConfigDTO.getIsEnable()) {
			alertConfig.setEnable(Boolean.TRUE);
		} else {
			alertConfig.setEnable(Boolean.FALSE);
		}

		if (alertConfigDTO.getIsAllowBlock() != null && alertConfigDTO.getIsAllowBlock()) {
			alertConfig.setAllowBlock(Boolean.TRUE);
		} else {
			alertConfig.setAllowBlock(Boolean.FALSE);
		}

		alertConfig.setBlockCondition(alertConfigDTO.getBlockCondition());
		alertConfig.setBlockInterval(alertConfigDTO.getBlockInterval());
		alertConfig.setBlockPeriod(alertConfigDTO.getBlockPeriod());

		return this.alertConfigMonitorizaRepository.save(alertConfig);
	}


	@Override
	public List<AlertConfigMonitoriza> getAllAlertConfigMonitorizaByApplicationMonitoriza(
			final ApplicationMonitoriza appMonitoriza) {
		return this.alertConfigMonitorizaRepository.findAllAlertConfigMonitorizaByApplicationMonitoriza(appMonitoriza);
	}


}
