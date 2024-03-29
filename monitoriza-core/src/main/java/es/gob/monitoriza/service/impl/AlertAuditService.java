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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.AlertAuditService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.2, 10/01/2022.
 */
package es.gob.monitoriza.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertAuditRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlertAuditDataTableRepository;
import es.gob.monitoriza.persistence.configuration.model.specification.AlertAuditDateSpecification;
import es.gob.monitoriza.service.IAlertAuditService;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 10/01/2022.
 */
@Service
public class AlertAuditService implements IAlertAuditService {
	
	/**
	 * Attribute that represents the datatable repository for AlertAudit
	 */
	@Autowired
	private AlertAuditRepository repository;
	
	/**
	 * Attribute that represents the datatable repository for AlertAudit
	 */
	@Autowired
	private AlertAuditDataTableRepository dtRepository;

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput)
	 */
	@Override
	public DataTablesOutput<AlertAudit> findAll(DataTablesInput input) {
		
		return dtRepository.findAll(input);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#findAll(org.springframework.data.jpa.datatables.mapping.DataTablesInput, java.util.Date, java.util.Date)
	 */
	@Override
	public DataTablesOutput<AlertAudit> findAll(DataTablesInput input, Date min, Date max) {
		
		AlertAuditDateSpecification spec = new AlertAuditDateSpecification(min, max);
		return dtRepository.findAll(input, spec);
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#findAllAlertAudit()
	 */
	@Override
	public List<AlertAudit> findAllAlertAudit() throws DatabaseException {
		
		List<AlertAudit> list = null;
		
		try {
			
			list = repository.findAll();
			
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e,"");
		}
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#getAllAlertAudit()
	 */
	@Override
	public Iterable<AlertAudit> getAllAlertAudit() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#findByCriteria(java.util.Date, java.util.Date)
	 */
	@Override
	public List<AlertAudit> findByCriteria(Date actualDate, Date periodDate) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.service.IAlertAuditService#getAlertAuditForResume(java.util.List, java.util.Date)
	 */
	@Override
	public List<AlertAudit> getAlertAuditForResume(Set<String> alertTypes, Set<String> apps, Date limit) throws DatabaseException {
		
		List<AlertAudit> result = null;
		
		try {
			
			if (limit != null) {
				result = repository.getAuditAlertForResume(alertTypes, apps, limit);
			}
							 
		} catch (DataAccessException e) {
			
			throw new DatabaseException(e, e.getMessage());
			
		}
		
		return result;
	}
	
//	@Override
//	public List<AlertAudit> findByCriteria(final Date actualDate, final Date periodDate) {
//		return this.repository.findAll(new Specification<AlertAudit>() {
//			@Override
//			public Predicate toPredicate(final Root<AlertAudit> root, final CriteriaQuery<?> query,
//					final CriteriaBuilder criteriaBuilder) {
//				final List<Predicate> predicates = new ArrayList<>();
//
//				if (periodDate != null && actualDate != null) {
//					predicates.add(criteriaBuilder.between(root.get("timestamp"), actualDate, periodDate));
//				}
//				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//			}
//		});
//	}

}
