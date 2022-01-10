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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.ApplicationResumeDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * <p>Class that represents a data structure for presenting the alert data of a 'resume'.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
public class ApplicationResumeDTO {
	
	/**
	 * Attribute that represents the name of the Application configured in a Resume. 
	 */
	private String appName;
	
	/**
	 * Attribute that represents the number of alerts with 'INFO' severity. 
	 */
	private Integer numInfo = 0;
	
	/**
	 * Attribute that represents the number of alerts with 'WARNING' severity.
	 */
	private Integer numWarn = 0;
	
	/**
	 * Attribute that represents the number of alerts with 'ERROR' severity.
	 */
	private Integer numError = 0;
	
	/**
	 * Attribute that represents the number of alerts with 'FATAL' severity.
	 */
	private Integer numFatal = 0;
	
	/**
	 * Attribute that represents the alerts configured in the Application and Resume . 
	 */
	private List<AlertResumeDTO> alertas = new ArrayList<AlertResumeDTO>();
	
		
	/**
	 * Constructor method for the class ApplicationResumeDTO.java.
	 * @param appName 
	 */
	public ApplicationResumeDTO(String appName) {
		super();
		this.appName = appName;
	}


	/**
	 * Gets the value of the attribute {@link appName}.
	 * @return the value of the attribute {@link #appName}.
	 */	
	public final String getAppName() {
		return appName;
	}
		
	/**
	 * Gets the value of the attribute {@link alertas}.
	 * @return the value of the attribute {@link #alertas}.
	 */	
	public final List<AlertResumeDTO> getAlertas() {
		return alertas;
	}
	
	/**
	 * Method that adds a new {@link AlertResumeDTO} to the {@link #alertas} in a ordered way.
	 * @param alertas {@link AlertResumeDTO}being added.
	 */
	public final void addOrderedAlerta(AlertResumeDTO alert) {
		this.alertas.add(alert);
		Collections.sort(this.alertas);
	}

	
	/**
	 * Gets the value of the attribute {@link numInfo}.
	 * @return the value of the attribute {@link #numInfo}.
	 */	
	public final Integer getNumInfo() {
		return numInfo;
	}

	/**
	 * Gets the value of the attribute {@link numWarn}.
	 * @return the value of the attribute {@link #numWarn}.
	 */	
	public final Integer getNumWarn() {
		return numWarn;
	}
	
	/**
	 * Gets the value of the attribute {@link numError}.
	 * @return the value of the attribute {@link #numError}.
	 */	
	public final Integer getNumError() {
		return numError;
	}

	/**
	 * Gets the value of the attribute {@link numFatal}.
	 * @return the value of the attribute {@link #numFatal}.
	 */	
	public final Integer getNumFatal() {
		return numFatal;
	}
	
	/**
	 * Method that adds one to the number of alerts with severity 'INFO'.
	 */
	public void addNumInfo() {
		
		this.numInfo++;
		
	}
	
	/**
	 * Method that adds one to the number of alerts with severity 'WARNING'.
	 */
	public void addNumWarn() {
		
		this.numWarn++;
		
	}

	/**
	 * Method that adds one to the number of alerts with severity 'ERROR'.
	 */
	public void addNumError() {
		
		this.numError++;
	}
	
	/**
	 * Method that adds one to the number of alerts with severity 'FATAL'.
	 */
	public void addNumFatal() {
		
		this.numFatal++;
	}	
	
}
