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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.specification.KeystoreSpecification.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 may. 2018.
 */
package es.gob.monitoriza.persistence.configuration.model.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;

/**
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 may. 2018.
 */
public class UserMonitorizaSpecification implements Specification<SystemCertificate> {

	/**
	 * Attribute that represents .
	 */
	private static final long serialVersionUID = -5918881206596479770L;

	/**
	 * Attribute that represents user.
	 */
	private final UserMonitoriza criteriaUser;

	/**
	 * Constructor method for the class PlatformSpecification.java.
	 * @param criteria
	 */
	public UserMonitorizaSpecification(final UserMonitoriza criteriaUser) {
		super();
		this.criteriaUser = criteriaUser;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
	 */
	@Override
	public Predicate toPredicate(final Root<SystemCertificate> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(builder.equal(root.get("userMonitoriza").get("idUserMonitoriza"), criteriaUser.getIdUserMonitoriza()));

		return builder.and(predicates.toArray(new Predicate[predicates.size()]));

	}

	/**
	 * Get criteriaUser.
	 * @return criteriaUser
	 */
	public UserMonitoriza getCriteriaUser() {
		return criteriaUser;
	}

}
