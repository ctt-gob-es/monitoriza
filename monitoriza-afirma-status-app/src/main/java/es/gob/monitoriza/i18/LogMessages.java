/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.afirmaBC.Constants.LogMessagesConstants.java.</p>
 * <b>Description:</b><p> Interface that contains the keys to the log messages.</p>
 * <b>Project:</b><p>Autotester.</p>
 * <b>Date:</b><p> 09/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 11/1/2018.
 */
package es.gob.monitoriza.i18;

/** 
 * <p>Interface that contains the keys to the log messages.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 11/01/2018.
 */
public interface LogMessages {
	
	/**
	 * Message key declared in the 'messages.properties' file.
	 */
	String INIT_SERVICE = "initService";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */			
	String PATH_DIRECTORY_REQUESTS = "pathDirectoryRequests";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String SENDING_REQUEST = "sendingRequest";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String RESPONSE_STORED = "responseStored";
	
	/**
	 * Message key declared in the 'messages.properties' file.
	 */	
	String ERROR_NO_FILE_EXISTS = "errorNoFileExists";

	/**
	 * Message key declared in the 'messages.properties' file.
	 */	
	String ERROR_READING_FILE = "errorReadingFile";
	
	/**
	 * Message key declared in the 'messages.properties' file.
	 */	
	String ERROR_INPUT_OUTPUT_FILE = "errorInputOutputFile";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String ERROR_SENDING_SOAP_PETITION = "errorSendingSoapPetition";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */		
	String ERROR_BAD_DIRECTORIES_STRUCTURE = "errorBadDirectoriesStructure";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String STARTING_TO_SEND_STORED_REQUESTS = "startingToSendStoredRequests";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */			
	String ERROR_INVALID_SERVICE_NAME = "errorInvalidServiceName";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */			
	String ERROR_PROPERTY_SERVICE = "errorPropertyService";
}
