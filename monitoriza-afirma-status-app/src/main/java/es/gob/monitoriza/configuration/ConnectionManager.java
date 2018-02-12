/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.configuration.ConnectionManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 ene. 2018.
 */
package es.gob.monitoriza.configuration;

import es.gob.monitoriza.persistence.configuration.dto.DTOConnection;

/** 
 * <p>Interface that provides methods for retrieve connection information from persistence.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 ene. 2018.
 */
public interface ConnectionManager {
	
	/**
	 * Gets connection parameters for @Firma platform from persistence.
	 * @return {@link #DTOConnection} containing connection parameters for @Firma platform.
	 */
	DTOConnection getAfirmaConnection();
	
	/**
	 * Gets connection parameters for TS@ platform from persistence.
	 * @return {@link #DTOConnection} containing connection parameters for TS@ platform.
	 */
	DTOConnection getTsaConnection();

}
