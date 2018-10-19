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
 * <b>File:</b><p>es.gob.monitoriza.form.ConfServerMailForm.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16 oct. 2018.
 */
package es.gob.monitoriza.form;

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
 * @version 1.0, 16 oct. 2018.
 */
public class ConfSpieForm {

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
	 * @return the idConfSpie
	 */
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
	public Long getFrequencyAFirma() {
		return frequencyAFirma;
	}

	/**
	 * @param frequencyAfirma
	 *            the frequencyAfirma to set
	 */
	public void setFrequencyAFirma(Long frequencyAFirma) {
		this.frequencyAFirma = frequencyAFirma;
	}

	/**
	 * @return the frequencyTsa
	 */
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

	/**
	 * @return the methodValidations
	 */
	public List<String> getMethodValidations() {
		return methodValidations;
	}

	/**
	 * @param methodValidations
	 *            the methodValidations to set
	 */
	public void setMethodValidations(List<String> methodValidations) {
		this.methodValidations = methodValidations;
	}

	/**
	 * @return the frequencyAFirmaTime
	 */
	public Time getFrequencyAFirmaTime() {
		return frequencyAFirmaTime;
	}

	/**
	 * @param frequencyAFirmaTime
	 *            the frequencyAFirmaTime to set
	 */
	public void setFrequencyAFirmaTime(Time frequencyAFirmaTime) {
		this.frequencyAFirmaTime = frequencyAFirmaTime;
	}

	/**
	 * @return the frequencyTsaTime
	 */
	public Time getFrequencyTsaTime() {
		return frequencyTsaTime;
	}

	/**
	 * @param frequencyTsaTime
	 *            the frequencyTsaTime to set
	 */
	public void setFrequencyTsaTime(Time frequencyTsaTime) {
		this.frequencyTsaTime = frequencyTsaTime;
	}

}
