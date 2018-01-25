/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.constant.GeneralConstants.java.</p>
 * <b>Description:</b><p> Interface that contains general constants.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2018.
 */
package es.gob.monitoriza.constant;

/** 
 * <p>Interface that contains general constants.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.1, 25/01/2018.
 */
public interface GeneralConstants {
	
	/**
	 * Constant attribute that represents the Monitoriza Logger name.
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
	public static final String DOT = ".";
	
	/**
	 * Constant that represents the comma character ','.
	 */
	public static final String COMMA = ",";
	
	/**
	 * Constant that represents SEPARATOR literal. 
	 */
	public static final String SEPARATOR = "_";

	/**
	 * Constant that represents the En_dash character '-'.
	 */
	public static final String EN_DASH = "-";
	
	/**
	 * Constant that represents the En_dash character with spaces ' - '.
	 */
	public static final String EN_DASH_WITH_SPACES = "-";
	
	/**
	 * Constant that represents the colon character ':'.
	 */
	public static final String COLON = ":";
	
	/**
	 * Constant that represents the path separator character '//'.
	 */
	public static final String DOUBLE_PATH_SEPARATOR = "//";
	
	/**
	 * Constant that represents the slash character '/'.
	 */
	public static final String SLASH = "/";

	/**
	 * Constant that represents the slash character '\n'.
	 */
	public static final String LINE_FEED = "\n";
	
	/**
	 * Constant that represents the key of the static property which contents the root path of the directories to store @firma services requests.
	 */
	public static final String ROOT_PATH_DIRECTORY = "directories.request.root.path";
	
	/**
	 * Constant that represents the key of the static property which contents the path for the principal service requests.
	 */
	public static final String GRUPO_PRINCIPAL_PATH_DIRECTORY = "directories.grupo.principal.path";
	
	/**
	 * Constant that represents the key of the static property which contents the path for the confirmation service requests.
	 */
	public static final String GRUPO_CONFIRMACION_PATH_DIRECTORY = "directories.grupo.confirmacion.path";
	
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
	
	/**
	 * Constant that represents the static property that indicates the average time threshold under a service
	 * is considered degraded.
	 */
	public static final String DEGRADED_THRESHOLD = "degraded.threshold";
	
	public static final String MONITORIZA_TIMER = "timer";
	
	/**
	 * Constant that represents the static property that indicates the frequency in which the soap requests
	 * are sent to @firma or ts@.
	 */
	public static final String FREQUENCY = "freq";

	/**
	 * Constant that represents the static property that indicates the mail issuer.
	 */
	public static final String MAIL_ATTRIBUTE_ISSUER = "mail.attribute.issuer";

	/**
	 * Constant that represents the static property that indicates the destination host.
	 */	
	public static final String MAIL_ATTRIBUTE_HOST = "mail.attribute.host";

	/**
	 * Constant that represents the static property that indicates the destination port.
	 */		
	public static final String MAIL_ATTRIBUTE_PORT = "mail.attribute.port";

	/**
	 * Constant that represents the static property that indicates if the server needs authentication.
	 */		
	public static final String MAIL_ATTRIBUTE_AUTHENTICATION = "mail.attribute.authentication";

	/**
	 * Constant that represents the static property that indicates the user of the authentication.
	 */			
	public static final String MAIL_ATTRIBUTE_USER = "mail.attribute.user";

	/**
	 * Constant that represents the static property that indicates the password of the authentication.
	 */		
	public static final String MAIL_ATTRIBUTE_PASSWORD = "mail.attribute.password";

	/**
	 * Constant that represents the string 'smtp'.
	 */		
	public static final String SMTP = "smtp";
	
	/**
	 * Constant that represents the string 'service'.
	 */
	public static final String SERVICE = "service";

	/**
	 * Constant that represents the string 'blockAlarmTime'.
	 */
	public static final String BLOCK_TIME_KEY = "blockAlarmTime";
	
	/**
	 * Constant that represents the string 'mailAddress'.
	 */
	public static final String MAIL_ADDRESS = "mailAddress";
	
	/**
	 * Constant that represents the string 'alarm'.
	 */
	public static final String ALARM = "alarm";

	/**
	 * Constant that represents the string 'subject'.
	 */
	public static final String SUBJECT = "subject";

	/**
	 * Constant that represents the string 'body'.
	 */
	public static final String BODY = "body";

	/**
	 * Constant that represents the string 'degenerated'.
	 */
	public static final String DEGENERATED = "degenetated";

	/**
	 * Constant that represents the string 'downed'.
	 */
	public static final String DOWNED = "downed";
}
