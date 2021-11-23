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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ALERT_DIM_TEMPLATES.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/11/2021.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * <p>Class that maps the <i>ALERT_DIM_SEVERITY</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_DIM_SEVERITY")
public class AlertDIMSeverity implements Serializable {

	/**
	 * Attribute that represents .
	 */
	private static final long serialVersionUID = -2097621406548994561L;

	/**
	 * Attribute that represents the object identifier.
	 */
	private Long idSeverity;

	/**
	 * Attribute that represents the name of the severity.
	 */
	private String name;


	/**
	 * Gets the value of the attribute {@link #idSeverity}.
	 *
	 * @return the value of the attribute {@link #idSeverity}.
	 */
	@Id
	@Column(name = "SEVERITY_ID", unique = true, nullable = false)
	@GeneratedValue(generator = "sq_alert_dim_severity")
	@GenericGenerator(name = "sq_alert_dim_severity", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_DIM_SEVERITY"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdSeverity() {
		return this.idSeverity;
	}

	/**
	 * Sets the value of the attribute {@link #idSeverity}.
	 *
	 * @param idSeverity
	 *            The value for the attribute {@link #idSeverity}.
	 */
	public void setIdSeverity(final Long idSeverity) {
		this.idSeverity = idSeverity;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "SEVERITY_NAME", nullable = false)
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
	public void setName(final String name) {
		this.name = name;
	}

}
