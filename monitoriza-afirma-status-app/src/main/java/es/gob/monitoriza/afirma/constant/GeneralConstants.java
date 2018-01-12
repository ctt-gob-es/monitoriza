/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.Constants.GeneralConstants.java.</p>
 * <b>Description:</b><p> Interface that contains general constants.</p>
 * <b>Project:</b><p>Autotester.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/1/2018.
 */
package es.gob.monitoriza.afirma.constant;

/** 
 * <p>Class that contains .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 11/01/2018.
 */
public interface GeneralConstants {
	
	/**
	 * Constant attribute that represents the SPIE Logger name.
	 */
	public static final String LOGGER_NAME_MONITORIZA_LOG = "Monitoriza-Server";
	
	/**
	 * Constant attribute that represents HTTP secure mode.
	 */
	public static final String SECUREMODE_HTTP = "http";
	
	/**
	 * Constant attribute that represents HTTPS secure mode.
	 */
	public static final String SECUREMODE_HTTPS = "https";
	
	/**
	 * Constant that represents the dot character '.'.
	 */	
	String DOT = ".";
	
	/**
	 * Constant that represents SEPARATOR literal. 
	 */
	String SEPARATOR = "_";
	
	/**
	 * Constant that represents the colon character ':'.
	 */
	String COLON = ":";
	
	/**
	 * Constant that represents the path separator character '//'.
	 */
	String DOUBLE_PATH_SEPARATOR = "//";
	
	/**
	 * Constant that represents the key of the static property which contents the root path of the directories to store @firma services requests.
	 */
	String ROOT_PATH_DIRECTORY = "directories.request.root.path";
	
	/**
	 * Constant that represents the static property that indicates if the connection with @Firna should be in a secure way (HHTPS) o not (HTTP).
	 */
	public static final String AFIRMA_CONNECTION_SECURE_MODE = "afirma.connection.secure.mode";

	/**
	 * Constant that represents the static property that indicates the IP where the server @Firma is running.
	 */
	public static final String AFIRMA_CONNECTION_IP = "afirma.connection.ip";

	/**
	 * Constant that represents the static property that indicates the port that the server @Firma has available.
	 */
	public static final String AFIRMA_CONNECTION_PORT = "afirma.connection.port";

	/**
	 * Constant that represents the static property that indicates the URL path of the services.
	 */
	public static final String AFIRMA_CONNECTION_SERVICE_PATH = "afirma.connection.service.path";
	
	/**
	 * Constant that represents the static property that indicates the timeout of the connection.
	 */
	public static final String AFIRMA_CONNECTION_TIMEOUT = "afirma.connection.timeout";
}
