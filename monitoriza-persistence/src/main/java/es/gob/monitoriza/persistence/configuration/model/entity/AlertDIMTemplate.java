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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTemplates.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * <p>Class that maps the <i>ALERT_DIM_TEMPLATES</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_DIM_TEMPLATES")
public class AlertDIMTemplate implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 7138914091807373559L;
	
	/**
	 * Attribute that represents the object identifier. 
	 */
	private Long idTemplate;
	
	/**
	 * Attribute that represents the name of the template. 
	 */
	private String name;
	

	/**
	 * Attribute that represents the alert statistics.
	 */
	private Set<AlertStatistic> alertStatistics;

	/**
	 * Gets the value of the attribute {@link #idTemplate}.
	 *
	 * @return the value of the attribute {@link #idTemplate}.
	 */
	@Id
	@Column(name = "TEMPLATE_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_dim_templates")
	@GenericGenerator(name = "sq_alert_dim_templates", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_DIM_TEMPLATES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdTemplate() {
		return idTemplate;
	}

	/**
	 * Sets the value of the attribute {@link #idTemplate}.
	 *
	 * @param idNode
	 *            The value for the attribute {@link #idTemplate}.
	 */
	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "TEMPLATE_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;

	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 *
	 * @param name
	 *            The value for the attribute {@link #name}.
	 */

	public void setName(String name) {
		this.name = name;
	}	

	/**
	 * Gets the value of the attribute {@link #alertStatistics}.
	 * @return the value of the attribute {@link #alertStatistics}.
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertDIMTemplate", cascade = CascadeType.ALL)
	public Set<AlertStatistic> getAlertConfigSystems() {
		return this.alertStatistics;
	}

	/**
	 * Sets the value of the attribute {@link #alertStatistics}.
	 * @param alertStatistics The value for the attribute {@link #alertStatistics}.
	 */
	public void setAlertConfigSystems(final Set<AlertStatistic> alertStatistics) {
		this.alertStatistics = alertStatistics;
	}

}
