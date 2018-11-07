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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.TimerDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>7 ago. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 29/08/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;


/** 
 * <p>Class Data transfer object that encapsulates the information for timer configuration..</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 29/08/2018.
 */
public class ConfigTimerDTO {
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTimer;
	
	/**
	 * Attribute that represents the name of the timer. 
	 */
	private String name;
	
	/**
	 * Attribute that represents. 
	 */
	private Long frequency;
		
	/**
	 * Constructor method for the class TimerDTO.java.
	 * @param idTimer Timer identifier
	 * @param name Timer name 
	 * @param frequency Frequency for the timer
	 */
	public ConfigTimerDTO(Long idTimer, String name, Long frequency) {
		super();
		this.idTimer = idTimer;
		this.name = name;
		this.frequency = frequency;
	}

	/**
	 * Gets the value of the attribute {@link #idTimer}.
	 * @return the value of the attribute {@link #idTimer}
	 */
	public Long getIdTimer() {
		return idTimer;
	}

	/**
	 * Sets the value of the attribute {@link #idTimer}.
	 * @param idTimer the value for the attribute {@link #idTimer} to set.
	 */
	public void setIdTimer(Long idTimer) {
		this.idTimer = idTimer;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param name the value for the attribute {@link #name} to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #frequency}. 
	 * @return the value of the attribute {@link #frequency}
	 */
	public Long getFrequency() {
		return frequency;
	}

	/**
	 * Sets the value of the attribute {@link #frequency}.
	 * @param frequency the value for the attribute {@link #frequency} to set.
	 */
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	
}
