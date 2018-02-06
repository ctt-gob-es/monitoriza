/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.ServiceStatus.java.</p>
 * <b>Description:</b><p> Interface with the possible statuses for the services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/01/2018.
 */
package es.gob.monitoriza.constant;

/** 
 * <p>Interface with the possible statuses for the services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16/01/2018.
 */
public interface ServiceStatusConstants {
	
	/**
	 * Attribute that represents the value for the CORRECTO status. 
	 */
	String CORRECTO = "Correcto";
	
	/**
	 * Attribute that represents the value for the DEGRADADO status. 
	 */
	String DEGRADADO = "Degradado";
	
	/**
	 * Attribute that represents the value for the CAIDO status. 
	 */
	String CAIDO = "Caído";

}
