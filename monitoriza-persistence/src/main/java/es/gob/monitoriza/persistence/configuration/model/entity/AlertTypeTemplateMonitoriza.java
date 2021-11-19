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

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Cacheable
@Entity
@Table(name = "ALERT_TEMPLATE_TYPES")
public class AlertTypeTemplateMonitoriza implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -717975907234143514L;
	
	private AlertTypeTemplateMonitorizaID idAlertTypeTemplateMonitorizaID;

	/**
	 * Attribute that represents the object ID.
	 */
	private AlertTypeMonitoriza alertTypeMonitoriza;

	/**
	 * Attribute that represents the alert type name.
	 */
	private TemplateMonitoriza templateMonitoriza;

	
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		@EmbeddedId
		public AlertTypeTemplateMonitorizaID getIdAlertTypeTemplateMonitorizaID() {
			// CHECKSTYLE:ON
			return idAlertTypeTemplateMonitorizaID;
		}
		
		// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		@MapsId("idTemplateMonitoriza")
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "TEMPLATE_ID", nullable = false, insertable = false, updatable = false)
		public TemplateMonitoriza getTemplateMonitoriza() {
			// CHECKSTYLE:ON
			return templateMonitoriza;
		}
		
		// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		@MapsId("idTypeMonitoriza")
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "TYPE_ID", nullable = false, insertable = false, updatable = false)
		public AlertTypeMonitoriza getAlertTypeMonitoriza() {
			// CHECKSTYLE:ON
			return alertTypeMonitoriza;
		}

		/**
		 * @param idAlertTypeTemplateMonitorizaID the idAlertTypeTemplateMonitorizaID to set
		 */
		// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		public void setIdAlertTypeTemplateMonitorizaID(AlertTypeTemplateMonitorizaID idAlertTypeTemplateMonitorizaID) {
			// CHECKSTYLE:ON
			this.idAlertTypeTemplateMonitorizaID = idAlertTypeTemplateMonitorizaID;
		}

		/**
		 * @param alertTypeMonitoriza the alertTypeMonitoriza to set
		 */
		// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		public void setAlertTypeMonitoriza(AlertTypeMonitoriza alertTypeMonitoriza) {
			// CHECKSTYLE:ON
			this.alertTypeMonitoriza = alertTypeMonitoriza;
		}

		/**
		 * @param templateMonitoriza the templateMonitoriza to set
		 */
		// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
		// because Hibernate JPA needs not final access methods.
		public void setTemplateMonitoriza(TemplateMonitoriza templateMonitoriza) {
			// CHECKSTYLE:ON
			this.templateMonitoriza = templateMonitoriza;
		}

	

}
