/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.i18.LogMessagesConstants.java.</p>
 * <b>Description:</b><p> Interface that contains the keys to the log monitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p> 09/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 06/02/2018.
 */
package es.gob.monitoriza.i18;

/** 
 * <p>Interface that contains the keys to the log monitoriza.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.3, 06/02/2018.
 */
public interface LogMessages {
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String INIT_SERVICE = "initService";

	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */			
	String PATH_DIRECTORY_REQUESTS = "pathDirectoryRequests";

	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String SENDING_REQUEST = "sendingRequest";

	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String RESPONSE_STORED = "responseStored";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String CONFIRMATION_WAIT_INTERRUPTED = "waitInterrupted";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */	
	String ERROR_NO_FILE_EXISTS = "errorNoFileExists";

	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */	
	String ERROR_READING_FILE = "errorReadingFile";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */	
	String ERROR_INPUT_OUTPUT_FILE = "errorInputOutputFile";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String ERROR_SENDING_SOAP_PETITION = "errorSendingSoapPetition";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String ERROR_SENDING_OCSP_PETITION = "errorSendingOcspPetition";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */		
	String ERROR_BAD_DIRECTORIES_STRUCTURE = "errorBadDirectoriesStructure";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String STARTING_TO_SEND_STORED_REQUESTS = "startingToSendStoredRequests";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */			
	String ERROR_INVALID_SERVICE_NAME = "errorInvalidServiceName";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */			
	String ERROR_PROPERTY_SERVICE = "errorPropertyService";

	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */		
	String ERROR_SENDING_MAIL = "errorSendingMail";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String ERROR_SERVICE_STATUS_OR_SERVICE_NAME_NULL = "errorServiceStatusOrServiceNameNull";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */		
	String ERROR_SERVICE_STATUS_NOT_MATCH = "errorServiceStatusNotMatch";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */		
	String ERROR_SERVICE_NAME_NOT_MATCH = "errorServiceNameNotMatch";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String SUMMARY_ALARM_SUBJECT_MAIL = "summaryAlarmSubjectMail";

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String BODY_MAIL_SUMMARY = "bodyMailSummary";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String INIT_RFC3161 = "initRFC3161";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String AUTH_CLIENT_RFC3161_ON = "authClientOn";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String AUTH_CLIENT_RFC3161_OFF = "authClientOff"; 
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String ERROR_ACCESS_CERTIFICATE_SSL = "errorAccessCertificateSSL";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String ERROR_UNDEFINED_CERTIFICATE_AUTH_CLIENT_RFC3161 = "errorUndefinedCertAuthRFC3161";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */
	String ERROR_KEYSTORE_ACCESS_AUTH_CLIENT_RFC3161 = "errorKeystoreAccessAuthRFC3161";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_NOCERT_IN_KEYSTORE_AUTH_CLIENT_RFC3161 = "errorNoCertInKeystore";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_PRIVATE_KEY_AUTH_CLIENT_RFC3161 = "errorPrivateKey";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_CERTIFICATE_CHAIN_AUTH_CLIENT_RFC3161 = "errorCertificateChain";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_CONTEXT_RFC3161 = "errorContextRFC3161";
	
	/**
	 * Attribute that represents . 
	 */
	String ERROR_MALFORMED_URL_RFC3161 = "errorMalformedUrlRfc3161";
}

