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
 * <b>File:</b><p>es.gob.monitoriza.rest.elements.NodeRestResponse.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>11/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 05/03/2019.
 */
package es.gob.monitoriza.rest.elements;

import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * <p>Class that represents the status response of invoking a node REST service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 05/03/2019.
 */
public class NodeRestStatusResponse implements Serializable {

	/**
	 * Attribute that represents the serial id. 
	 */
	private static final long serialVersionUID = -3047842590264683264L;
	
	/**
	 * Attribute that represents the status.
	 */
	private Integer status;

	/**
	 * Attribute that represents the description.
	 */
	private String description;
	
	/**
	 * Attribute that represents the time when the Node Rest Service was called. 
	 */
	private LocalDateTime dateTime;
	
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
	public void setDescription(final String descriptionParam) {
		this.description = descriptionParam;
	}

	/**
	 * Gets the value of the attribute {@link #dateTime}.
	 * @return the value of the attribute {@link #v}.
	 */
	public final LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the value of the attribute {@link #dateTime}.
	 * @param dateTimeParam The value for the attribute {@link #dateTime}.
	 */
	public final void setDateTime(LocalDateTime dateTimeParam) {
		this.dateTime = dateTimeParam;
	}

	
}
