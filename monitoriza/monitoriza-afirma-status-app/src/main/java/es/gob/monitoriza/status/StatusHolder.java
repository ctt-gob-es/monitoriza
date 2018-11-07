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
package es.gob.monitoriza.status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * <p> Class that stores in memory the status for the services that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 16/01/2018.
 */
public final class StatusHolder {
		
	/**
	 * Attribute that represents the current map of statuses for services being processed. 
	 */
	private Map<String,StatusUptodate> currentStatusHolder;
	
	/**
	 * Attribute that represents the instance for this class. 
	 */
	private static StatusHolder instance;
	
	/**
	 * Constructor method for the class StatusHolder.java. 
	 */
	private StatusHolder() {
		currentStatusHolder = new ConcurrentHashMap<String, StatusUptodate>();
	}
	
	/**
	 * Gets an instance of the class.
	 * @return	A {@link StatusHolder} object.
	 */
	public static synchronized StatusHolder getInstance() {
		
		if (instance == null) {
			instance = new StatusHolder();
		}
		return instance;
	}
			
	/**
	 * Gets the {@link currentstatusHolder}.
	 * @return {@link Map}.
	 */
	public Map<String, StatusUptodate> getCurrenttatusHolder() {
		return currentStatusHolder;
	}

	
	/**
	 * Sets the {@link currentstatusHolder}.
	 * @param currentstatusHolder
	 */
	public void setCurrenttatusHolder(Map<String, StatusUptodate> currentstatusHolder) {
		this.currentStatusHolder = currentstatusHolder;
	}
	
}
