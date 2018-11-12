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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.SummaryStatusDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>08/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 08/11/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;


/** 
 * <p>Data transfer object that contains a summary of the VIP and SPIE status alerts</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 08/11/2018.
 */
public class SummaryStatusDTO {
	
	/**
	 * Attribute that represents the semaphore value for the status alert. 
	 */
	private Integer status;
	
	/**
	 * Attribute that represents the type of alert: VIP or SPIE. 
	 */
	private String type;
		
	/**
	 * Attribute that represents the description of the alert. 
	 */
	private String description;
	
	
	/**
	 * Constructor method for the class SummaryStatusDTO.java. 
	 */
	public SummaryStatusDTO() {
		super();
	}

	/**
	 * Constructor method for the class SummaryStatusDTO.java.
	 * @param statusParam {@link #status} to set
	 * @param typeParam {@link #type} to set
	 * @param descriptionParam  {@link #description} to set
	 */
	public SummaryStatusDTO(final Integer statusParam, final String typeParam, final String descriptionParam) {
		super();
		this.status = statusParam;
		this.type = typeParam;
		this.description = descriptionParam;
	}

	/**
	 * Gets the value of the attribute {@link #status}.
	 * @return the value of the attribute {@link #status}.
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the value of the attribute {@link #status}.
	 * @param statusParam The value for the attribute {@link #status}.
	 */
	public void setStatus(final Integer statusParam) {
		this.status = statusParam;
	}

	/**
	 * Gets the value of the attribute {@link #type}.
	 * @return the value of the attribute {@link #type}.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 * @param typeParam The value for the attribute {@link #type}.
	 */
	public void setType(String typeParam) {
		this.type = typeParam;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 * @return the value of the attribute {@link #description}.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 * @param descriptionParam The value for the attribute {@link #description}.
	 */
	public void setDescription(String descriptionParam) {
		this.description = descriptionParam;
	}
		

}
