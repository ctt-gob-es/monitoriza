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

import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogSystemConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertGraylogSystemConfigRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertSystemMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlertSystemDatatableRepository;
import es.gob.monitoriza.service.IAlertSystemMonitorizaService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertSystemMonitorizaService")
public class AlertSystemMonitorizaService implements IAlertSystemMonitorizaService {

	@Autowired
	private AlertSystemMonitorizaRepository repository;

	@Autowired
	private AlertGraylogSystemConfigRepository graylogSystemRepository;

	@Autowired
	private AlertSystemDatatableRepository dtRepository;

	private static final String TYPE_GRAYLOG = "graylog"; //$NON-NLS-1$

	@Override
	public AlertSystemMonitoriza getAlertSystemMonitorizaById(final Long alertSystemId) {
		return this.repository.findByIdAlertSystemMonitoriza(alertSystemId);
	}

	@Override
	public void deleteAlertSystemMonitoriza(final Long alertSystemId) {
		this.repository.deleteById(alertSystemId);
	}

	@Override
	public Iterable<AlertSystemMonitoriza> getAllAlertSystemMonitoriza() {
		return this.repository.findAll();
	}

	@Override
	public DataTablesOutput<AlertSystemMonitoriza> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public AlertSystemMonitoriza saveAlertSystemMonitoriza(final AlertSystemDTO alertSystemDto) {

		AlertSystemMonitoriza alertSystemMonitoriza;

		if (alertSystemDto.getIdAlertSystemMonitoriza() != null) {
			alertSystemMonitoriza = this.repository.findByIdAlertSystemMonitoriza(alertSystemDto.getIdAlertSystemMonitoriza());
		} else {
			alertSystemMonitoriza = new AlertSystemMonitoriza();
		}

		alertSystemMonitoriza.setName(alertSystemDto.getName());
		alertSystemMonitoriza.setType(alertSystemDto.getType());

		alertSystemMonitoriza =  this.repository.save(alertSystemMonitoriza);

		if(alertSystemDto.getType().equals(TYPE_GRAYLOG)) {
			final AlertGraylogSystemConfig alertGraylogConfig = new AlertGraylogSystemConfig();
			alertGraylogConfig.setIdAlertGraylogSystemConfig(alertSystemMonitoriza.getIdAlertSystemMonitoriza());
			alertGraylogConfig.setHost(alertSystemDto.getHost());
			alertGraylogConfig.setPort(alertSystemDto.getPort());
			this.graylogSystemRepository.save(alertGraylogConfig);
		}

		return alertSystemMonitoriza;
	}


}
