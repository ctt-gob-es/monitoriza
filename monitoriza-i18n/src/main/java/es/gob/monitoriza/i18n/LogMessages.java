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
 * <b>File:</b><p>es.gob.monitoriza.i18n.LogMessages.java.</p>
 * <b>Description:</b><p> Interface that contains the keys to the log monitoriza.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p> 09/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.5, 02/10/2018.
 */
package es.gob.monitoriza.i18n;

/** 
 * <p>Interface that contains the keys to the log monitoriza.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.4, 02/10/2018.
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
	String REQUEST_THREAD_WAIT = "requestThreadWait";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String CONFIRMATION_WAIT_INTERRUPTED = "waitInterrupted";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file. 
	 */	
	String CONFIRMATION_REQUIRED = "confirmationRequired";
	
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
	String ERROR_PROCESSING_SERVICE = "errorProcessingService";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String LOG_ENDPOINT = "logEndpoint";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String SUBJECT_MAIL_MONITORIZA = "subjectMailMonitoriza";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String BODY_MAIL_ALARM_DEGRADED = "bodyMailAlarmDegraded";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */	
	String BODY_MAIL_ALARM_LOST = "bodyMailAlarmLost" ;

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String SUMMARY_ALARM_SUBJECT_MAIL = "summaryAlarmSubjectMail";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String BODY_MAIL_SUMMARY_ROW_DEGRADED = "bodyMailSummaryRowDegraded";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String BODY_MAIL_SUMMARY_ROW_LOST = "bodyMailSummaryRowLost";	

	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String BODY_MAIL_SUMMARY = "bodyMailSummary";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String SUMMARY_ALARM_SENT = "summaryAlarmSent";
	
	/**
	 * Message key declared in the 'messages.properties' file. 
	 */
	String BLOCKED_ALARM_END = "blockedAlarmEnd";
	
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
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_MALFORMED_URL_RFC3161 = "errorMalformedUrlRfc3161";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_THROWING_ALARM = "errorThrowingAlarm";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_SERVLET_POST = "errorServletPost";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_SERVLET_REQUEST = "errorServletRequest";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_FORMAT_THREAD_POOL_SIZE = "errorFormatThreadPoolSize";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_NOT_BLANK_ALIAS = "errorNotBlankAlias";
	
	/**
	 * Message key declared in the 'monitoriza.properties' file.
	 */
	String ERROR_NOT_NULL_FILE_CERT = "errorNotNullFileCert";
	
	/**
	 * i18n Token to show message when a system certificate has been added succesfully.
	 */
	String ERROR_SPECIAL_CHAR_ALIAS = "errorSpecialCharAlias";
	
	/**
	 * i18n Token to show message when a system certificate has been added succesfully.
	 */
	String SYS_CERT_ADDED = "modKeyAddSysCert";
	
	/**
	 * i18n Token to show message when a key pair has been added succesfully.
	 */
	String KEY_PAIR_ADDED = "modKeyAddKeyPair";
	
	/**
	 * i18n Token to show error when exception occurs while adding given key pair.
	 */
	String ERROR_ADDING_KEY_PAIR = "errorAddingKeyPair";
	
	/**
	 * i18n Token to show error when exception occurs while reading given keystore.
	 */
	String ERROR_ADDING_SYS_CERTS = "errorAddingSysCert";
	
	/**
	 * i18n Token to show error when exception occurs while listing the aliases from a keystore.
	 */
	String ERROR_LISTING_ALIASES = "errorListingAliases";
	
	/**
	 * i18n Token to show error when exception occurs while listing the aliases from a keystore.
	 */
	String TIMER_UPDATED_CANCEL = "timerUpdatedCancel";
	
	/**
	 * i18n Token to show error when exception occurs while getting the bytes of the compressed request file.
	 */
	String ERROR_BYTES_ZIP_FILE = "errorBytesZipFile";
}

