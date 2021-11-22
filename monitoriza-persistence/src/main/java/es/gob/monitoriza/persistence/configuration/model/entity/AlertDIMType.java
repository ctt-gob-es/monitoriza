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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertDIMTypes.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 12211/2021.
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

import es.gob.monitoriza.constant.NumberConstants;


/** 
 * <p>Class that maps the <i>ALERT_DIM_TYPES</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/11/2021.
 */
@Entity
@Table(name = "ALERT_DIM_TYPES")
public class AlertDIMType implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 2243183371834259067L;
	
	/**
	 * Attribute that represents the object identifier. 
	 */
	private Long idType;
	
	/**
	 * Attribute that represents the name of the application. 
	 */
	private String name;

	/**
	 * Gets the value of the attribute {@link #idType}.
	 *
	 * @return the value of the attribute {@link #idType}.
	 */
	@Id
	@Column(name = "TYPE_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_dim_types")
	@GenericGenerator(name = "sq_alert_dim_types", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_DIM_TYPES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdType() {
		return idType;
	}

	/**
	 * Sets the value of the attribute {@link #idType}.
	 *
	 * @param name
	 *            The value for the attribute {@link #idType}.
	 */
	public void setIdType(Long idType) {
		this.idType = idType;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "TYPE_NAME", nullable = false, precision = NumberConstants.NUM40)
	public String getName() {
		return name;
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
	
	

}
