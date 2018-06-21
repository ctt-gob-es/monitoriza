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
 * <b>File:</b><p>es.gob.monitoriza.status.StatusUptodate.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>21 jun. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 21 jun. 2018.
 */
package es.gob.monitoriza.status;

import java.time.LocalDateTime;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 21 jun. 2018.
 */
public class StatusUptodate {
	
	/**
	 * Attribute that represents the value of the status for the service. 
	 */
	private String statusValue;
	
	/**
	 * Attribute that represents the date and time of the last status update. 
	 */
	private LocalDateTime statusUptodate;
	
	/**
	 * Constructor method for the class StatusUptodate.java.
	 */
	public StatusUptodate() {
		
	}

	/**
	 * Constructor method for the class StatusUptodate.java.
	 * @param statusValue
	 * @param statusUptodate 
	 */
	public StatusUptodate(String statusValue, LocalDateTime statusUptodate) {
		super();
		this.statusValue = statusValue;
		this.statusUptodate = statusUptodate;
	}

	/**
	 * Gets the value of the attribute {@link #statusValue}.
	 * @return the value of the attribute {@link #statusValue}.
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the value of the attribute {@link #statusValue}.
	 * @param statusValue the value for the attribute {@link #statusValue} to set.
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * Gets the value of the attribute {@link #statusUptodate}.
	 * @return the value of the attribute {@link #statusUptodate}.
	 */
	public LocalDateTime getStatusUptodate() {
		return statusUptodate;
	}

	/**
	 * Sets the value of the attribute {@link #statusUptodate}.
	 * @param statusUptodate the value for the attribute {@link #statusUptodate} to set.
	 */
	public void setStatusUptodate(LocalDateTime statusUptodate) {
		this.statusUptodate = statusUptodate;
	}
	

}
