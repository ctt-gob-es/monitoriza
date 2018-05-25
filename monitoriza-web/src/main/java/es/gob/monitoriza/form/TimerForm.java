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
 * <b>File:</b><p>es.gob.monitoriza.form.TimerForm.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 may. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 8 may. 2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 8 may. 2018.
 */
public class TimerForm {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idTimer;
	
	/**
	 * Attribute that represents the name of the timer. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.timer.name.notempty}")
	@Size(min=5, max=30, groups=ThenCheckIt.class)
	private String name;
	
	/**
	 * Attribute that represents. 
	 */
	@NotNull(message="{form.valid.timer.frequency.notempty}")
	private Long frequency;

	/**
	 * Gets the value of the attribute {@link #idTimer}.
	 * @return the value of the attribute {@link #idTimer}.
	 */
	public Long getIdTimer() {
		return idTimer;
	}

	/**
	 * Sets the value of the attribute {@link #idTimer}.
	 * @param isBlockedParam The value for the attribute {@link #idTimer}.
	 */
	public void setIdTimer(Long idTimer) {
		this.idTimer = idTimer;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param isBlockedParam The value for the attribute {@link #name}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #frequency}.
	 * @return the value of the attribute {@link #frequency}.
	 */
	public Long getFrequency() {
		return frequency;
	}

	/**
	 * Sets the value of the attribute {@link #frequency}.
	 * @param isBlockedParam The value for the attribute {@link #frequency}.
	 */
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
}
