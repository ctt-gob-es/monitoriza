package es.gob.monitoriza.timers;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

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
 * <b>File:</b><p>.TimersHolder.java.</p>
 * <b>Description:</b><p>Class that stores in memory the timers currently scheduled.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/09/2018.
 */

/** 
 * <p>Class that stores in memory the timers currently scheduled.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 12/09/2018.
 */
public class TimersHolder {

	/**
	 * Attribute that represents the current map of timers scheduled. 
	 */
	private Map<Long,Timer> currentTimersHolder;
	
	/**
	 * Attribute that represents the instance for this class. 
	 */
	private static TimersHolder instance;
	
	/**
	 * Constructor method for the class TimersHolder.java. 
	 */
	private TimersHolder() {
		currentTimersHolder = new ConcurrentHashMap<Long, Timer>();
	}
	
	/**
	 * Gets an instance of the class.
	 * @return	A {@link TimersHolder} object.
	 */
	public static synchronized TimersHolder getInstance() {
		
		if (instance == null) {
			instance = new TimersHolder();
		}
		return instance;
	}
			
	/**
	 * Gets the {@link currentTimersHolder}.
	 * @return {@link Map}.
	 */	
	public Map<Long, Timer> getCurrentTimersHolder() {
		return currentTimersHolder;
	}

	
	/**
	 * Sets the {@link currentTimersHolder}.
	 * @param currentTimersHolder
	 */
	public void setCurrentTimersHolder(Map<Long, Timer> currentTimersHolder) {
		this.currentTimersHolder = currentTimersHolder;
	}
	
}
