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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertTypeTemplateMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_TEMPLATE_TYPES</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_TEMPLATE_TYPES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Embeddable
public class AlertTypeTemplateMonitorizaID implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -717975907234143514L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTypeMonitoriza;

	/**
	 * Attribute that represents the alert type name.
	 */
	private Long idTemplateMonitoriza;


	/**
	 * @return the idTypeMonitoriza
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
	@Column(name = "TYPE_ID", nullable = false, precision = NumberConstants.NUM19)
	public Long getIdTypeMonitoriza() {
		// CHECKSTYLE:ON
		return idTypeMonitoriza;
	}

	/**
	 * @return the idTemplateMonitoriza
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
	@Column(name = "TEMPLATE_ID", nullable = false, precision = NumberConstants.NUM19)
	public Long getIdTemplateMonitoriza() {
		// CHECKSTYLE:ON
		return idTemplateMonitoriza;
	}

	/**
	 * @param idTypeMonitoriza the idTypeMonitoriza to set
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
	public void setIdTypeMonitoriza(Long idTypeMonitoriza) {
		// CHECKSTYLE:ON
		this.idTypeMonitoriza = idTypeMonitoriza;
	}

	/**
	 * @param idTemplateMonitoriza the idTemplateMonitoriza to set
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
	public void setIdTemplateMonitoriza(Long idTemplateMonitoriza) {
		// CHECKSTYLE:ON
		this.idTemplateMonitoriza = idTemplateMonitoriza;
	}

	@Override
    public int hashCode() {
        return this.idTypeMonitoriza.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof AlertTypeTemplateMonitorizaID)) {
            return false;
        }

        final AlertTypeTemplateMonitorizaID other = (AlertTypeTemplateMonitorizaID)obj;

        return this.idTypeMonitoriza.equals(other.getIdTypeMonitoriza());
    }

}
