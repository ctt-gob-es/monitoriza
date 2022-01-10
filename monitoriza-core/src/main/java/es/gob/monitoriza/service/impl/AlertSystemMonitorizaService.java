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
 * @version 1.4, 10/01/2022.
 */
package es.gob.monitoriza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.persistence.configuration.dto.AlertSystemDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertGraylogSystemConfig;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemType;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertGraylogSystemConfigRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertSystemMonitorizaRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertSystemTypeRepository;
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
	private AlertSystemTypeRepository typeRepository;

	@Autowired
	private AlertGraylogSystemConfigRepository graylogSystemRepository;

	@Autowired
	private AlertSystemDatatableRepository dtRepository;

	private static final String TYPE_GRAYLOG = "graylog"; //$NON-NLS-1$

	private static final String TYPE_EMAIL = "email"; //$NON-NLS-1$

	@Override
	public AlertSystemMonitoriza getAlertSystemMonitorizaById(final Long alertSystemId) {
		return this.repository.findByIdAlertSystemMonitoriza(alertSystemId);
	}

	@Override
	@Transactional
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
	@Transactional
	public AlertSystemMonitoriza saveAlertSystemMonitoriza(final AlertSystemDTO alertSystemDto) {

		AlertSystemMonitoriza alertSystemMonitoriza;

		if (alertSystemDto.getIdAlertSystemMonitoriza() != null) {
			// Si el id ya existe, quiere decir que se esta editando un registro
			alertSystemMonitoriza = this.repository.findByIdAlertSystemMonitoriza(alertSystemDto.getIdAlertSystemMonitoriza());

			// Si se esta editando un registro y se ha seleccionado como tipo correo electronico,
			// se elimina el registro de la tabla ALERT_GRAYLOG_SYSTEM_CONFIG
			if(TYPE_EMAIL.equals(alertSystemDto.getType())) {
				final AlertGraylogSystemConfig graylogSysConf = this.graylogSystemRepository.findByIdAlertGraylogSystemConfig(alertSystemDto.getIdAlertSystemMonitoriza());
				if(graylogSysConf != null) {
					this.graylogSystemRepository.delete(graylogSysConf);
					alertSystemMonitoriza.setGraylogSystemConfig(null);
				}
			}
		} else {
			alertSystemMonitoriza = new AlertSystemMonitoriza();
		}

		alertSystemMonitoriza.setName(alertSystemDto.getName());
		
		AlertSystemType type = typeRepository.findByIdAlertSystemType(alertSystemDto.getType());
		
		alertSystemMonitoriza.setType(type);

		alertSystemMonitoriza =  this.repository.save(alertSystemMonitoriza);

		if (alertSystemDto.getType().equals(TYPE_GRAYLOG)) {
			final AlertGraylogSystemConfig alertGraylogConfig = new AlertGraylogSystemConfig();
			alertGraylogConfig.setIdAlertGraylogSystemConfig(alertSystemMonitoriza.getIdAlertSystemMonitoriza());
			alertGraylogConfig.setHost(alertSystemDto.getHost());
			alertGraylogConfig.setPort(alertSystemDto.getPort());
			this.graylogSystemRepository.save(alertGraylogConfig);
		}

		return alertSystemMonitoriza;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertSystemMonitorizaService#getAllAlertSystemType()
	 */
	@Override
	public List<AlertSystemType> getAllAlertSystemType() {
		
		return typeRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertSystemMonitorizaService#getAllAlertSystemResumeEnabled()
	 */
	@Override
	@Transactional
	public List<AlertSystemMonitoriza> getAllAlertSystemResumeEnabled() throws DatabaseException {
		
		List<AlertSystemMonitoriza> result = null;
		
		try {
			
			result = repository.findByTypeIsResumeEnabled(Boolean.TRUE);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e,e.getMessage());		
		}
		
		return result;
	}


}
