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
 * @version 1.2, 25/01/2018.
 */
package es.gob.monitoriza.constant;

/** 
 * <p>Interface that contains general constants.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.2, 25/01/2018.
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
	 * Attribute that represents the service identifier for OCSP services. 
	 */
	public static final String OCSP_SERVICE = "ocsp";
	
	/**
	 * Attribute that represents the service identifier for RFC3161 services. 
	 */
	public static final String RFC3161_SERVICE = "rfc3161";
		
	/**
	 * Attribute that represents . 
	 */
	public static final String MONITORIZA_TIMER = "timer";
	
	/**
	 * Constant that represents the static property that indicates the frequency in which the soap requests
	 * are sent to @firma or ts@.
	 */
	public static final String FREQUENCY = "freq";
	
	
	/**
	 * Constant attribute that identifies the provider Sun for X.509 content type.
	 */
	public static final String TRUST_MANAGER_FACTORY_SUN_X509 = "SunX509";
	

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
	 * Constant that represents the string 'degraded'.
	 */
	public static final String DEGRADED = "degraded";

	/**
	 * Constant that represents the string 'downed'.
	 */
	public static final String DOWNED = "downed";
}

