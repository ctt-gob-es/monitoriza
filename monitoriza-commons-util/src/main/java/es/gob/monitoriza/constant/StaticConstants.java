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
 * <b>File:</b><p>es.gob.monitoriza.constant.StaticConstants.java.</p>
 * <b>Description:</b>
 * <p>Interface that contains the static constants for the static configuration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 17/09/2018.
 */
package es.gob.monitoriza.constant;


/** 
 * <p>Interface that contains the static constants for the static configuration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 17/09/2018.
 */
public interface StaticConstants {
	
	/**
	 * Constant that represents the static property that indicates the timeout of the connection.
	 */
	public static final String AFIRMA_CONNECTION_TIMEOUT = "afirma.connection.timeout";
	
	/**
	 * Constant that represents the static property that indicates the average time threshold under a service
	 * is considered degraded.
	 */
	public static final String DEGRADED_THRESHOLD = "degraded.threshold";
	
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
	 * Constant that represents the key of the static property which contents the waiting time before a confirmation group is processed.
	 */
	public static final String CONFIRMATION_WAIT_TIME = "confirmation.wait.time";
	
	/**
	 * Constant that represents the static property that indicates if the connection with @Firna should be in a secure way (HHTPS) o not (HTTP).
	 */
	public static final String AFIRMA_CONNECTION_SECURE_MODE = "afirma.connection.secure.mode";
	
	/**
	 * Constant that represents the static property that indicates the IP where the server @Firma is running.
	 */
	public static final String AFIRMA_CONNECTION_HOST = "afirma.connection.host";

	/**
	 * Constant that represents the static property that indicates the port that the server @Firma has available.
	 */
	public static final String AFIRMA_CONNECTION_PORT = "afirma.connection.port";
	
	/**
	 * Constant that represents the static property that indicates the port that the server @Firma has available.
	 */
	public static final String AFIRMA_HTTPS_PORT = "afirma.https.port";

	/**
	 * Constant that represents the static property that indicates the URL path of the services.
	 */
	public static final String AFIRMA_CONNECTION_SERVICE_PATH = "afirma.connection.service.path";
	
	/**
	 * Constant that represents the static property that indicates the URL path of the ocsp service.
	 */
	public static final String AFIRMA_CONNECTION_OCSP_PATH = "afirma.connection.ocsp.path";
	
	/**
	 * Constant that represents the static property that indicates if the connection with @Firna should be in a secure way (HHTPS) o not (HTTP).
	 */
	public static final String TSA_CONNECTION_SECURE_MODE = "tsa.connection.secure.mode";
		
	/**
	 * Attribute that represents the static property that indicates the TS@ connection host. 
	 */
	public static final String TSA_CONNECTION_HOST = "tsa.connection.host";
	
	/**
	 * Attribute that represents the static property that indicates the TS@ connection port. 
	 */
	public static final String TSA_CONNECTION_PORT = "tsa.connection.port";
	
	/**
	 * Attribute that represents the static property that indicates the TS@ connection port. 
	 */
	public static final String TSA_HTTPS_PORT = "tsa.https.port";
	
	/**
	 * Constant that represents the static property that indicates the URL path of the services.
	 */
	public static final String TSA_CONNECTION_SERVICE_PATH = "tsa.connection.service.path";
	
	/**
	 * Attribute that represents the static property that indicates the TS@ RFC3161 connection context. 
	 */
	public static final String TSA_CONNECTION_RFC3161_CONTEXT = "tsa.connection.rfc3161.context";	
	
	/**
	 * Attribute that represents the static property that indicates the TS@ RFC3161 connection port. 
	 */
	public static final String TSA_CONNECTION_RFC3161_PORT = "tsa.connection.rfc3161.port";
	
	/**
	 *	Constant that represents the static property that indicates if the communication with platforms must be secured.
	 */
	public static final String SSL_ACTIVE = "ssl.active";
	
	/**
	 *	Constant that represents the static property that indicates the path to the SSL truststore.
	 */
	public static final String SSL_TRUSTTORE_PATH = "ssl.truststore.path";
	
	/**
	 * Constant that represents the static property that indicates the type of the SSL truststore.
	 */
	public static final String SSL_TRUSTSTORE_TYPE = "ssl.truststore.type";
	
	/**
	 *	Constant that represents the static property that indicates the password for the SSL truststore.
	 */
	public static final String SSL_TRUSTTORE_PASSWORD = "ssl.truststore.password";
	
	/**
	 * Constant that represents the static property that indicates if monitoriz@ uses alarms (true or false)
	 */
	public static final String ALARM_ACTIVE = "alarm.active";
	
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
	 * Constant that represents the static property that indicates if the server needs tls.
	 */		
	public static final String MAIL_ATTRIBUTE_TLS = "mail.attribute.tls";

	/**
	 * Constant that represents the static property that indicates the user of the authentication.
	 */			
	public static final String MAIL_ATTRIBUTE_USER = "mail.attribute.user";

	/**
	 * Constant that represents the static property that indicates the password of the authentication.
	 */		
	public static final String MAIL_ATTRIBUTE_PASSWORD = "mail.attribute.password";
	
	/**
	 * Constant that represents the static property that indicates if is necessary using client authentication
	 * for RFC3161 service use.
	 */
	public static final String RFC3161_HTTPS_USE_CLIENT_AUTHENTICATION = "rfc3161.https.use.client.auth";
	
	/**
	 * Constant that represents the static property that indicates the alias of the certificate used by client
	 * authentication for RFC3161 service.
	 */
	public static final String RFC3161_HTTPS_CERTIFICATE_ALIAS = "rfc3161.https.certificate.alias";
		
	/**
	 * Constant that represents the static property that indicates the password of the certificate used by client
	 * authentication for RFC3161 service.
	 */
	public static final String RFC3161_HTTPS_CERTIFICATE_PASSWORD = "rfc3161.https.certificate.password";
		
	/**
	 * Constant that represents the static property that indicates the path of the certificate used by client
	 * authentication for RFC3161 service.
	 */
	public static final String RFC3161_AUTH_KEYSTORE_PATH = "rfc3161.auth.keystore.path";
	
	/**
	 * Constant that represents the static property that indicates the type of the keystore used by client
	 * authentication for RFC3161 service.
	 */
	public static final String RFC3161_HTTPS_CERTIFICATE_TYPE = "rfc3161.https.certificate.type";
	
	/**
	 * Constants that represents the static property that indicates the size of the thread pool for launching requests. 
	 */
	public static final String REQUEST_THREAD_POOL_SIZE = "requestThreadPoolSize";
	
	/**
	 * Constant attribute that represents name for property <code>character.special</code>.
	 */
	public static final String LIST_CHARACTER_SPECIAL = "character.special";
	
	/**
	 * Attribute that represents the name for property <code>fixedRate.in.milliseconds</code>.
	 */
	public static final String FIXED_RATE_MILLISSECONDS = "fixedRate.in.milliseconds";
	
}
