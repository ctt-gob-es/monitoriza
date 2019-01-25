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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail.java.</p>
 * <b>Description:</b><p>Class that maps the <i>CONF_SERVER_MAIL</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>METHOD_VALIDATION</i> database table as a Plain Old
 * Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.1, 25/01/2019.
 */
@Entity
@Table(name = "METHOD_VALIDATION")
public class MethodValidation {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 9090589742672624291L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idMethodValidation;

	/**
	 * Attribute that represents the percent accept.
	 */
	private String methodName;

	/**
	 * Attribute that represents the Configuration SPIE
	 */
	private ConfSpie confSpie;

	/**
	 * @return the idMethodValidation
	 */
	@Id
	@Column(name = "ID_METHOD_VALIDATION", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_method_validation")
	@GenericGenerator(name = "sq_method_validation", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQ_METHOD_VALIDATION"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdMethodValidation() {
		return idMethodValidation;
	}

	/**
	 * @param idMethodValidation
	 *            the idMethodValidation to set
	 */
	public void setIdMethodValidation(Long idMethodValidation) {
		this.idMethodValidation = idMethodValidation;
	}

	/**
	 * @return the methodName
	 */
	@Column(name = "METHOD_NAME", nullable = false, precision = NumberConstants.NUM200)
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the idConfSpie
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CONF_SPIE", nullable = true)
	public ConfSpie getConfSpie() {
		return confSpie;
	}

	/**
	 * @param idConfSpie
	 *            the idConfSpie to set
	 */
	public void setConfSpie(ConfSpie confSpie) {
		this.confSpie = confSpie;
	}

}
