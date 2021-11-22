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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.AlertStatisticsBIService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.gob.monitoriza.constant.IAuditToStatisticsControlResults;
import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.dto.AuditToStatisticDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAuditControl;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertAuditControlRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertAuditRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertDIMApplicationRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertDIMNodeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertDIMSeverityRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertDIMTemplateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertDIMTypeRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertStatisticsRepository;
import es.gob.monitoriza.service.IAlertStatisticsBIService;
import es.gob.monitoriza.utilidades.UtilsFecha;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Service
public class AlertStatisticsBIService implements IAlertStatisticsBIService {
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertAuditRepository auditRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertStatisticsRepository statsRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertAuditControlRepository controlRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertDIMApplicationRepository dimAppRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertDIMNodeRepository dimNodeRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertDIMSeverityRepository dimSeverityRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertDIMTemplateRepository dimTemplateRepository;
	
	/**
	 * Attribute that represents . 
	 */
	@Autowired
	private AlertDIMTypeRepository dimTypeRepository;
	
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#auditToStatisticsDumpData(java.util.Date, java.util.Date)
	 */
	@Override
	@Transactional
	public List<AuditToStatisticDTO> auditToStatisticsDumpData(Date begin, Date end) throws DatabaseException {
		
		List<AuditToStatisticDTO> list = null;
		
		try {
			
			list = auditRepository.auditToStatisticsDumpData(begin, end);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
								
		return list;
		
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getLastExecution()
	 */
	@Override
	@Transactional
	public List<AlertAuditControl> getPendingControlExecutions() {

		List<AlertAuditControl> listTotal = new ArrayList<AlertAuditControl>();
		List<AlertAuditControl> listError = new ArrayList<AlertAuditControl>();
		
		try {

			listError = controlRepository.findByResultOrderByExecEndDesc(IAuditToStatisticsControlResults.RESULT_ERROR);
			// listOk =
			// controlRepository.findByResultOrderByExecEndDesc(IAuditToStatisticsControlResults.RESULT_OK);

			// Se agregan los procesos pendientes por haber fallado
			listTotal.addAll(listError);

			AlertAuditControl control = new AlertAuditControl();

			try {
				control.setAuditBegin(UtilsFecha.sumarDias(UtilsFecha.getHoyComienzoDia(), NumberConstants.NUM_NEG_2));
				control.setAuditEnd(UtilsFecha.sumarDias(UtilsFecha.getHoyFinDia(), NumberConstants.NUM_NEG_1));
				listTotal.add(control);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (DataAccessException e) {
			throw new DatabaseException(e, e.getMessage());
		}

		return listTotal;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getDIMAppByName(java.lang.String)
	 */
	@Override
	public AlertDIMApplication getDIMAppByName(String name) throws DatabaseException {
		
		AlertDIMApplication result = null;
		
		try  {
			
			result = dimAppRepository.findByName(name);
					
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getDIMNodeByName(java.lang.String)
	 */
	@Override
	public AlertDIMNode getDIMNodeByName(String name) throws DatabaseException {
		
		AlertDIMNode result = null;
		
		try {
			
			result = dimNodeRepository.findByName(name);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getDIMSeverityByName(java.lang.String)
	 */
	@Override
	public AlertDIMSeverity getDIMSeverityByName(String name) throws DatabaseException {
		
		AlertDIMSeverity result = null;
		
		try {
			
			result = dimSeverityRepository.findByName(name);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getDIMTemplateByName(java.lang.String)
	 */
	@Override
	public AlertDIMTemplate getDIMTemplateByName(String name) throws DatabaseException {
		
		AlertDIMTemplate result = null;
		
		try {
			
			result = dimTemplateRepository.findByName(name);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#getDIMTypeByName(java.lang.String)
	 */
	@Override
	public AlertDIMType getDIMTypeByName(String name) throws DatabaseException {
		
		AlertDIMType result = null;
		
		try {
			
			result = dimTypeRepository.findByName(name);
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveDimApplication(es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMApplication)
	 */
	@Override
	public AlertDIMApplication saveDimApplication(AlertDIMApplication dimApp) throws DatabaseException {
		
		AlertDIMApplication result = null;
		
		try {
			
			result = dimAppRepository.save(dimApp);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveDimNode(es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMNode)
	 */
	@Override
	public AlertDIMNode saveDimNode(AlertDIMNode dimNode) throws DatabaseException {
		
		AlertDIMNode result = null;
		
		try {
			
			result = dimNodeRepository.save(dimNode);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveDimSeverity(es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMSeverity)
	 */
	@Override
	public AlertDIMSeverity saveDimSeverity(AlertDIMSeverity dimSeverity) throws DatabaseException {
		
		AlertDIMSeverity result = null;
		
		try {
			
			result = dimSeverityRepository.save(dimSeverity);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
		
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveDimTemplate(es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplate)
	 */
	@Override
	public AlertDIMTemplate saveDimTemplate(AlertDIMTemplate dimTemplate) throws DatabaseException {
		
		AlertDIMTemplate result = null;
		
		try {
			
			result = dimTemplateRepository.save(dimTemplate);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveDimType(es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMType)
	 */
	@Override
	public AlertDIMType saveDimType(AlertDIMType dimType) throws DatabaseException {
		
		AlertDIMType result = null;
		
		try {
			
			result = dimTypeRepository.save(dimType);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
	
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveAlertStatistics(es.gob.monitoriza.persistence.configuration.model.entity.AlertStatistics)
	 */
	@Override
	public AlertStatistics saveAlertStatistics(AlertStatistics stats) throws DatabaseException {
		
		AlertStatistics result = null;
		
		try {
			
			result = statsRepository.save(stats);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
	
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertStatisticsBIService#saveAlertAuditControl(es.gob.monitoriza.persistence.configuration.model.entity.AlertAuditControl)
	 */
	@Override
	@Transactional
	public AlertAuditControl saveAlertAuditControl(AlertAuditControl control) throws DatabaseException {
		
		AlertAuditControl result = null;
		
		try {
			
			result = controlRepository.save(control);
			
		} catch (DataAccessException e) {
		
			throw new DatabaseException(e, e.getMessage());
		}
	
		return result;
	}

}
