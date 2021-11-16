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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.persistence.configuration.model.entity.AlertAudit;
import es.gob.monitoriza.persistence.configuration.model.repository.AlertAuditRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.datatable.AlertAuditDatatableRepository;
import es.gob.monitoriza.service.IAlertAuditService;

/**
 * <p>Class that implements the communication with the operations of the persistence layer.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 */
@Service("alertAuditService")
public class AlertAuditService implements IAlertAuditService {

	@Autowired
	private AlertAuditRepository repository;

	@Autowired
	private AlertAuditDatatableRepository dtRepository;

	@Override
	public Iterable<AlertAudit> getAllAlertAudit() {
		return this.repository.findAll();
	}

	@Override
	public DataTablesOutput<AlertAudit> findAll(final DataTablesInput input) {
		return this.dtRepository.findAll(input);
	}

	@Override
	public List<AlertAudit> findByCriteria(final Date actualDate, final Date periodDate) {
		return this.repository.findAll(new Specification<AlertAudit>() {
			@Override
			public Predicate toPredicate(final Root<AlertAudit> root, final CriteriaQuery<?> query,
					final CriteriaBuilder criteriaBuilder) {
				final List<Predicate> predicates = new ArrayList<>();

				if (periodDate != null && actualDate != null) {
					predicates.add(criteriaBuilder.between(root.get("timestamp"), actualDate, periodDate));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}
}
