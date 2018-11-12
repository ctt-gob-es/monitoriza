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
 * <b>File:</b><p>es.gob.monitoriza.status.StatusHolder.java.</p>
 * <b>Description:</b><p> Class that stores in memory the status for the services that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/01/2018.
 */
package es.gob.monitoriza.spie.status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.gob.monitoriza.persistence.configuration.dto.RowStatusSpieDTO;

/** 
 * <p>Class that stores in memory the status for the SPIEs that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 26/10/2018.
 */
public final class StatusSpieHolder {
		
	/**
	 * Attribute that represents the current map of statuses for services being processed. 
	 */
	private Map<Long, RowStatusSpieDTO> currentStatusHolder;
	
	/**
	 * Attribute that represents the instance for this class. 
	 */
	private static StatusSpieHolder instance;
	
	/**
	 * Constructor method for the class StatusHolder.java. 
	 */
	private StatusSpieHolder() {
		currentStatusHolder = new ConcurrentHashMap<Long, RowStatusSpieDTO>();
	}
	
	/**
	 * Gets an instance of the class.
	 * @return	A {@link StatusSpieHolder} object.
	 */
	public static synchronized StatusSpieHolder getInstance() {
		
		if (instance == null) {
			instance = new StatusSpieHolder();
		}
		return instance;
	}
			
	/**
	 * Gets the {@link #currentStatusHolder}.
	 * @return {@link Map}.
	 */
	public Map<Long, RowStatusSpieDTO> getCurrentStatusHolder() {
		return currentStatusHolder;
	}

	
	/**
	 * Sets the {@link #currentStatusHolder}.
	 * @param currentstatusHolderParam value for {@link #currentStatusHolder} to set
	 */
	public void setCurrentStatusHolder(final Map<Long, RowStatusSpieDTO> currentstatusHolderParam) {
		this.currentStatusHolder = currentstatusHolderParam;
	}
	
}
