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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.sql.Time;
import java.util.List;

/**
 * <p>
 * Class that represents the backing form for adding/editing a @firma platform.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 28/10/2018.
 */
public class ConfSpieDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in
	 * the form.
	 */
	private Long idConfSpie;

	/**
	 * Attribute that represents the percent accept.
	 */
	private Long percentAccept;

	/**
	 * Attribute that represents the frequency @firma.
	 */
	private Long frequencyAFirma;

	/**
	 * Attribute that represents the frequency Ts@.
	 */
	private Long frequencyTsa;

	/**
	 * Attribute that represents the frequency @firma Time.
	 */
	private Time frequencyAFirmaTime;

	/**
	 * Attribute that represents the frequency Ts@ Time.
	 */
	private Time frequencyTsaTime;

	/**
	 * Attribute that represents the method validations.
	 */
	private List<String> methodValidations;
		
	/**
	 * Constructor method for the class ConfSpieDTO.java. 
	 */
	public ConfSpieDTO() {
		super();
	}

	/**
	 * Constructor method for the class ConfSpieDTO.java.
	 * @param idConfSpieParam Parameter for {@link idConfSpie}
	 * @param percentAcceptParam Parameter for {@link percentAccept}
	 * @param frequencyAFirmaParam Parameter for {@link frequencyAFirma}
	 * @param frequencyTsaParam Parameter for {@link frequencyTsa}
	 * @param frequencyAFirmaTimeParam Parameter for {@link frequencyAFirmaTime}
	 * @param frequencyTsaTimeParam Parameter for {@link frequencyTsaTime}
	 * @param methodValidationsParam Parameter for {@link methodValidations}
	 */
	public ConfSpieDTO(Long idConfSpieParam, Long percentAcceptParam, Long frequencyAFirmaParam, Long frequencyTsaParam, Time frequencyAFirmaTimeParam, Time frequencyTsaTimeParam, List<String> methodValidationsParam) {
		super();
		this.idConfSpie = idConfSpieParam;
		this.percentAccept = percentAcceptParam;
		this.frequencyAFirma = frequencyAFirmaParam;
		this.frequencyTsa = frequencyTsaParam;
		this.frequencyAFirmaTime = frequencyAFirmaTimeParam;
		this.frequencyTsaTime = frequencyTsaTimeParam;
		this.methodValidations = methodValidationsParam;
	}

	/**
	 * @return the idConfSpie
	 */
	public Long getIdConfSpie() {
		return idConfSpie;
	}

	/**
	 * @param idConfSpieParam
	 *            the idConfSpie to set
	 */
	public void setIdConfSpie(Long idConfSpieParam) {
		this.idConfSpie = idConfSpieParam;
	}

	/**
	 * @return the percentAccept
	 */
	public Long getPercentAccept() {
		return percentAccept;
	}

	/**
	 * @param percentAcceptParam
	 *            the percentAccept to set
	 */
	public void setPercentAccept(Long percentAcceptParam) {
		this.percentAccept = percentAcceptParam;
	}

	/**
	 * @return the frequencyAfirma
	 */
	public Long getFrequencyAFirma() {
		return frequencyAFirma;
	}

	/**
	 * @param frequencyAFirmaParam
	 *            the frequencyAfirma to set
	 */
	public void setFrequencyAFirma(Long frequencyAFirmaParam) {
		this.frequencyAFirma = frequencyAFirmaParam;
	}

	/**
	 * @return the frequencyTsa
	 */
	public Long getFrequencyTsa() {
		return frequencyTsa;
	}

	/**
	 * @param frequencyTsaParam
	 *            the frequencyTsa to set
	 */
	public void setFrequencyTsa(Long frequencyTsaParam) {
		this.frequencyTsa = frequencyTsaParam;
	}

	/**
	 * @return the methodValidations
	 */
	public List<String> getMethodValidations() {
		return methodValidations;
	}

	/**
	 * @param methodValidationsParam
	 *            the methodValidations to set
	 */
	public void setMethodValidations(List<String> methodValidationsParam) {
		this.methodValidations = methodValidationsParam;
	}

	/**
	 * @return the frequencyAFirmaTime
	 */
	public Time getFrequencyAFirmaTime() {
		return frequencyAFirmaTime;
	}

	/**
	 * @param frequencyAFirmaTimeParam
	 *            the frequencyAFirmaTime to set
	 */
	public void setFrequencyAFirmaTime(Time frequencyAFirmaTimeParam) {
		this.frequencyAFirmaTime = frequencyAFirmaTimeParam;
	}

	/**
	 * @return the frequencyTsaTime
	 */
	public Time getFrequencyTsaTime() {
		return frequencyTsaTime;
	}

	/**
	 * @param frequencyTsaTimeParam
	 *            the frequencyTsaTime to set
	 */
	public void setFrequencyTsaTime(Time frequencyTsaTimeParam) {
		this.frequencyTsaTime = frequencyTsaTimeParam;
	}

}
