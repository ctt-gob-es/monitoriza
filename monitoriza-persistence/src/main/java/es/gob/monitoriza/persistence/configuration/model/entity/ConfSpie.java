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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie.java.</p>
 * <b>Description:</b><p>Class that maps the <i>CONF_SPIE</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
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
 * <p>
 * Class that maps the <i>CONF_SPIE</i> database table as a Plain Old Java
 * Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.5, 25/01/2019.
 */
@Entity
@Table(name = "CONF_SPIE")
public class ConfSpie implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 9090589742672624291L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idConfSpie;

	/**
	 * Attribute that represents the percent accept.
	 */
	private Long percentAccept;

	/**
	 * Attribute that represents the frequency @firma.
	 */
	private Long frequencyAfirma;

	/**
	 * Attribute that represents the frequency TS@.
	 */
	private Long frequencyTsa;

	/**
	 * @return the idConfSpie
	 */
	@Id
	@Column(name = "ID_CONF_SPIE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_conf_spie")
	@GenericGenerator(name = "sq_conf_spie", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQ_CONF_SPIE"), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1") })
	public Long getIdConfSpie() {
		return idConfSpie;
	}

	/**
	 * @param idConfSpie
	 *            the idConfSpie to set
	 */
	public void setIdConfSpie(Long idConfSpie) {
		this.idConfSpie = idConfSpie;
	}

	/**
	 * @return the percentAccept
	 */
	@Column(name = "PERCENT_ACCEPT", nullable = false, precision = NumberConstants.NUM19)
	public Long getPercentAccept() {
		return percentAccept;
	}

	/**
	 * @param percentAccept
	 *            the percentAccept to set
	 */
	public void setPercentAccept(Long percentAccept) {
		this.percentAccept = percentAccept;
	}

	/**
	 * @return the frequencyAfirma
	 */
	@Column(name = "FREQUENCY_AFIRMA", nullable = false, precision = NumberConstants.NUM19)
	public Long getFrequencyAfirma() {
		return frequencyAfirma;
	}

	/**
	 * @param frequencyAfirma
	 *            the frequencyAfirma to set
	 */
	public void setFrequencyAfirma(Long frequencyAfirma) {
		this.frequencyAfirma = frequencyAfirma;
	}

	/**
	 * @return the frequencyTsa
	 */
	@Column(name = "FREQUENCY_TSA", nullable = false, precision = NumberConstants.NUM19)
	public Long getFrequencyTsa() {
		return frequencyTsa;
	}

	/**
	 * @param frequencyTsa
	 *            the frequencyTsa to set
	 */
	public void setFrequencyTsa(Long frequencyTsa) {
		this.frequencyTsa = frequencyTsa;
	}

}
