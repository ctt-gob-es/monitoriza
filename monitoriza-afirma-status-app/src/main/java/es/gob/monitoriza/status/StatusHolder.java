/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.status.StatusHolder.java.</p>
 * <b>Description:</b><p> Class that stores in memory the status for the services that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/01/2018.
 */
package es.gob.monitoriza.status;

import java.util.HashMap;
import java.util.Map;

/** 
 * <p> Class that stores in memory the status for the services that are being processed.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 16/01/2018.
 */
public final class StatusHolder {
		
	/**
	 * Attribute that represents the current map of statuses for services being processed. 
	 */
	private Map<String,String> currentstatusHolder;
	
	/**
	 * Attribute that represents the instance for this class. 
	 */
	private static StatusHolder instance;
	
	/**
	 * Constructor method for the class StatusHolder.java. 
	 */
	private StatusHolder() {
		currentstatusHolder = new HashMap<String, String>();
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
	public Map<String, String> getCurrenttatusHolder() {
		return currentstatusHolder;
	}

	
	/**
	 * Sets the {@link currentstatusHolder}.
	 * @param currentstatusHolder
	 */
	public void setCurrenttatusHolder(Map<String, String> currentstatusHolder) {
		this.currentstatusHolder = currentstatusHolder;
	}
	
}
