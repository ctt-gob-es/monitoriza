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
 * <b>Date:</b><p>05/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/03/2019.
 */
package es.gob.monitoriza.rest.elements;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * <p>Class that stores in memory the status for the SPIEs that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 05/03/2019.
 */
public final class StatusNodeHolder {
		
	/**
	 * Attribute that represents the current map of statuses for services being processed. 
	 */
	private Map<String, NodeRestStatusResponse> currentStatusHolder;
	
	/**
	 * Attribute that represents the instance for this class. 
	 */
	private static StatusNodeHolder instance;
	
	/**
	 * Constructor method for the class StatusHolder.java. 
	 */
	private StatusNodeHolder() {
		currentStatusHolder = new ConcurrentHashMap<String, NodeRestStatusResponse>();
	}
	
	/**
	 * Gets an instance of the class.
	 * @return	A {@link StatusNodeHolder} object.
	 */
	public static synchronized StatusNodeHolder getInstance() {
		
		if (instance == null) {
			instance = new StatusNodeHolder();
		}
		return instance;
	}
			
	/**
	 * Gets the {@link #currentStatusHolder}.
	 * @return {@link Map}.
	 */
	public Map<String, NodeRestStatusResponse> getCurrentStatusHolder() {
		return currentStatusHolder;
	}

	
	/**
	 * Sets the {@link #currentStatusHolder}.
	 * @param currentstatusHolderParam value for {@link #currentStatusHolder} to set
	 */
	public void setCurrentStatusHolder(final Map<String, NodeRestStatusResponse> currentstatusHolderParam) {
		this.currentStatusHolder = currentstatusHolderParam;
	}
	
}
