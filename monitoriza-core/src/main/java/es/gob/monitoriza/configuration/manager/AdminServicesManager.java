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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.StaticServicesManage.java.</p>
 * <b>Description:</b>
 * <p>Class that manages the configuration of the @firma/ts@ services from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>19/01/2018.</p>
 * @author Gobierno de España.
 * @version 2.3 25/01/2019.
 */
package es.gob.monitoriza.configuration.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.exception.RequestFileNotFoundException;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConfigTimerDTO;
import es.gob.monitoriza.persistence.configuration.dto.ConnectionDTO;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.persistence.configuration.model.entity.DailySpieMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.DailyVipMonitorig;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.RequestServiceFile;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerScheduled;
import es.gob.monitoriza.service.IDailySpieMonitoringService;
import es.gob.monitoriza.service.IDailyVipMonitoringService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IRequestServiceFileService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;
import es.gob.monitoriza.service.ITimerScheduledService;
import es.gob.monitoriza.utilidades.FileUtils;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;
import es.gob.monitoriza.utilidades.UtilsGrayLog;

/** 
 * <p>Class that manages the configuration of the services from database persistence for use in the status servlet.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 *  @version 2.3, 25/01/2019.
 */
@Service("adminServicesManager")
public class AdminServicesManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the object that maps TimerMonitoriza by its identifier to the corresponding programmable timer. 
	 */
	private static Map<Long, Timer> scheduledTimers = new HashMap<Long, Timer>();
	
	/**
	 * Attribute that represents the service object for accessing the service
	 * repository.
	 */
	@Autowired
	private IServiceMonitorizaService serviceService;
	
	/**
	 * Attribute that represents the service object for accessing the timer
	 * repository.
	 */
	@Autowired
	private ITimerMonitorizaService timerService;
	
	/**
	 * Attribute that represents the service object for accessing the scheduled timer
	 * repository.
	 */
	@Autowired
	private ITimerScheduledService scheduledService;
		
	/**
	 * Attribute that represents the service object for accessing the keystore
	 * repository.
	 */
	@Autowired
	private IKeystoreService keystoreService;
	
	/**
	 * Attribute that represents the service object for accessing the request file
	 * repository.
	 */
	@Autowired
	private IRequestServiceFileService fileService;
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IDailyVipMonitoringService dailyVipService;
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IDailySpieMonitoringService dailySpieService;
	
	/**
	 * Method that gets all timers from database.
	 * @return List of TimerDTO
	 */
	public List<ConfigTimerDTO> getAllTimers() {
		
		final List<TimerMonitoriza> timers = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false).collect(Collectors.toList());
		final List<ConfigTimerDTO> timersDTO = new ArrayList<ConfigTimerDTO>();
		
		for (TimerMonitoriza timer : timers) {
			
			timersDTO.add(new ConfigTimerDTO(timer.getIdTimer(), timer.getName(), timer.getFrequency()));
		}
		
		return timersDTO;
	}
	
	/**
	 * Method that gets timers by ids from database.
	 * @param idTimers List that contains timer identifiers.
	 * @return List of TimerDTO
	 */
	public List<ConfigTimerDTO> getAllTimersById(List<Long> idTimers) {
		
		final List<TimerMonitoriza> timers = StreamSupport.stream(timerService.getAllTimerMonitorizaById(idTimers).spliterator(), false).collect(Collectors.toList());
		final List<ConfigTimerDTO> timersDTO = new ArrayList<ConfigTimerDTO>();
		
		for (TimerMonitoriza timer : timers) {
			
			timersDTO.add(new ConfigTimerDTO(timer.getIdTimer(), timer.getName(), timer.getFrequency()));
		}
		
		return timersDTO;
	}
	

	/**
	 * Method that gets the services  from persistence (database or static properties file).
	 * @param timerDTO The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	public List<ConfigServiceDTO> getServicesByTimer(final ConfigTimerDTO timerDTO) {

		final List<ConfigServiceDTO> servicesTimer = new ArrayList<ConfigServiceDTO>();
				
		final TimerMonitoriza timer = new TimerMonitoriza();
		timer.setIdTimer(timerDTO.getIdTimer());
		//timer.setName(timerDTO.getName());
		final List<ServiceMonitoriza> servicesByTimer = StreamSupport.stream(serviceService.getAllByTimer(timer).spliterator(), false)
				.collect(Collectors.toList());
		ConfigServiceDTO serviceDTO = null;
		
		for (ServiceMonitoriza service : servicesByTimer) {
									
			serviceDTO = new ConfigServiceDTO(service.getIdService(), service.getName(), service.getTimer().getName(), service.getTimeout(), service.getNameWsdl(), service.getDegradedThreshold(), service.getLostThreshold().toString(), getDirectoryPath(service.getIdService(), service.getName()), service.getPlatform().getPlatformType().getName(), service.getServiceType(), service.getPlatform().getIdPlatform());
					
			// Base URL de cada plataforma sin tener en cuenta ningún contexto. Servirá para construir la URL de invocación OCSP y RFC3161.
			serviceDTO.setBaseUrl(getBaseUrl(service.getPlatform(), service.getServiceType()));
			// URL de los servicios SOAP hasta el contexto. En la clase HttpSoapInvoker se completará con el WSDL endpoint.
			serviceDTO.setSoapUrl(getBaseUrl(service.getPlatform(), service.getServiceType()).concat(service.getPlatform().getServiceContext()));
			// Contexto OCSP que se concatenará a la baseUrl para generar la URL completa OCSP. Esta se usará en OcspInvoker.
			serviceDTO.setOcspContext(service.getPlatform().getOcspContext());
			// Contexto RFC3161 que se concatenará a la baseUrl para generar la URL completa RFC3161. Esta se usará en Rfc3161Invoker.
			serviceDTO.setRfc3161Context(service.getPlatform().getRfc3161Context());
			// Indicador de uso de autenticación para el servicio RFC3161 de TS@.
			serviceDTO.setUseRfc3161Auth(service.getPlatform().getUseRfc3161Auth());
			if (serviceDTO.getUseRfc3161Auth() != null && serviceDTO.getUseRfc3161Auth() && service.getPlatform().getRfc3161Certificate() != null) {
				// Alias del certificado para la autenticación del servicio RFC3161 que usaremos en Rfc3161Invoker.
				serviceDTO.setRfc3161Cert(service.getPlatform().getRfc3161Certificate().getAlias());
			}
			
			try {
				serviceDTO.setRfc3161Password(getRfc3161KeystorePassword());
			} catch (CryptographyException e) {
				String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE003, new Object[ ] { keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161).getTokenName() });
				LOGGER.error(errorMsg, e);
			}			
			
			serviceDTO.setIdAlarm(service.getAlarm().getIdAlarm());
			
//			serviceDTO.setBlockTimeAlarm(service.getAlarm().getBlockedTime());
//									
//			serviceDTO.setListMailDegraded(getAddressesFromAlarm(service.getAlarm().getEmailsDegraded()));
//			serviceDTO.setListMailDown(getAddressesFromAlarm(service.getAlarm().getEmailsDown()));
			
			servicesTimer.add(serviceDTO);
						
			// Se actualiza la carpeta de peticiones
			if (service.getRequestFile() != null) {
    			RequestServiceFile file = new RequestServiceFile();
    			try {
    				file = fileService.getRequestFileById(service.getRequestFile().getIdRequestServiceFile());
    
    				StringBuffer targetFolder = new StringBuffer();
    				targetFolder.append(StaticMonitorizaConfig.getProperty(StaticConstants.ROOT_PATH_DIRECTORY)).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(serviceDTO.getServiceId()).append(GeneralConstants.SEPARATOR).append(serviceDTO.getServiceName()).append(GeneralConstants.DOUBLE_PATH_SEPARATOR);
    				
    				// El primer paso es eliminar la existente para este servicio
    				// File directoryToBeDeleted = new java.io.File(targetFolder.toString());
    				// if (directoryToBeDeleted.exists()) {
    				//	FileUtils.deleteDirectory(directoryToBeDeleted);
    				// }
    				
    				// Es necesario borrar todos las carpetas que comiencen por el identificador del servicio.
    				// Esto es debido a que si se modifica el nombre del servicio, ya no se conoce el nombre
    				// original y sería imposible eliminar la carpeta "id_nombre_original".
    				FileUtils.deleteAllDirectoriesBeginnigWith(StaticMonitorizaConfig.getProperty(StaticConstants.ROOT_PATH_DIRECTORY), String.valueOf(serviceDTO.getServiceId()).concat(GeneralConstants.SEPARATOR));    				
    				
    				// Se descomprime el ZIP extraído de base de datos en el destino configurado
    				FileUtils.unZipFileWithSubFolders(file.getFiledata(), file.getFilename(), targetFolder.toString());
    			} catch (RequestFileNotFoundException e) {
    				LOGGER.error(Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE012, new Object[]{service.getRequestFile().getIdRequestServiceFile()}), e);
    			} catch (IOException e) {
    				LOGGER.error(Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE013, new Object[]{file.getFilename()}), e);
    			}
			}
		}

		return servicesTimer;
	}
	
	/**
	 * Method that gets a timer by its identifier.
	 * @param idTimer Timer database identifier
	 * @return Data transfer object with timer data.
	 */
	public ConfigTimerDTO getTimerById(final Long idTimer) {
		
		final TimerMonitoriza timer = timerService.getTimerMonitorizaById(idTimer);
		
		return new ConfigTimerDTO(timer.getIdTimer(), timer.getName(), timer.getFrequency());
	}

	/**
	 * Method that builds and returns the directory path for a service: "service-identifier_service-name".
	 * @param serviceId The name identifier for the service.
	 * @param serviceName String that represents the service name. It will be used to determine the service request directory path.
	 * @return String that represents the directory path for the service passed as parameter.
	 */
	private String getDirectoryPath(final Long serviceId, final String serviceName) {
		
		String basePath = StaticMonitorizaConfig.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);
		
		return basePath.concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(serviceId.toString()).concat(GeneralConstants.SEPARATOR).concat(serviceName);
				
	}
	
	/**
	 * Method that returns the base URL connection with the platform, considering host, port, etc.
	 * @param platform Platform configuration.
	 * @param serviceType Type of the service.
	 * @return String that represents the base URL connection with the platform, considering host, port, etc.
	 */
	private String getBaseUrl(final PlatformMonitoriza platform, final String serviceType) {
								
		final ConnectionDTO connection = new ConnectionDTO(platform.getIsSecure(), platform.getHost(), platform.getPort(), platform.getRfc3161Port(), platform.getServiceContext(), platform.getOcspContext(), platform.getRfc3161Context());

		String port = connection.getPort();
		
		String protocol = null;
		if (serviceType.equalsIgnoreCase(GeneralConstants.RFC3161_SERVICE)) {
			// Si el servicio es RFC3161, la conexión siempre se hará mediante HTTPS
			protocol = GeneralConstants.SECUREMODE_HTTPS;
		} else {
			protocol = connection.getSecureMode()? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
		}
		
		
		StringBuilder url = new StringBuilder();
							
		if (port != null && !"".equals(port)) {
			url.append(protocol).append(GeneralConstants.COLON).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(connection.getHost()).append(GeneralConstants.COLON).append(port);
		} else {
			url.append(protocol).append(GeneralConstants.COLON).append(GeneralConstants.DOUBLE_PATH_SEPARATOR).append(connection.getHost());
		}
		
		return url.toString();
	}
	
	/**
	 * Method that retrieves the keystore for SSL certificates from database.
	 * @return Keystore containing the certificates for SSL.
	 */
	public KeyStore loadSslTruststore() {
		
		final KeystoreMonitoriza ks = keystoreService.getKeystoreByName(GeneralConstants.SSL_TRUST_STORE_NAME);
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE005);
			cer = KeyStore.getInstance(ks.getKeystoreType());
			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
	/**
	 * Method that retrieves the keystore for RFC3161 authentication key pairs from database.
	 * @return Keystore cointaining the key pairs for RFC3161 authentication.
	 */
	public KeyStore loadRfc3161Keystore() {
		
		final KeystoreMonitoriza ks = keystoreService.getKeystoreByName(GeneralConstants.RFC3161_KEYSTORE_NAME);
		
		final IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén RFC3161
			msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE006);
			cer = KeyStore.getInstance(ks.getKeystoreType());

			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
	/**
	 * Method that retrieves the keystore for valid service key pairs from database.
	 * @return Keystore cointaining the key pairs for valid service.
	 */
	public KeyStore loadValidServiceKeystore() {

		final KeystoreMonitoriza ks = keystoreService.getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);

		final IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResCoreMonitoriza(IStatusLogMessages.STATUS012);
			cer = KeyStore.getInstance(ks.getKeystoreType());

			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
			
	/**
	 * Saves a scheduled timer in database.
	 * @param scheduled The scheduled timer to save
	 * @return TimerScheduled object saved in database
	 */
	public TimerScheduled saveTimerScheduled(final TimerScheduled scheduled) {
		
		return scheduledService.saveTimerScheduled(scheduled);
	}
	
	/**
	 * Deletes a scheduled timer in database.
	 * @param scheduledId The scheduled timer identifier
	 */
	public void deleteTimerScheduled(final Long scheduledId) {
		scheduledService.deleteTimerScheduled(scheduledId);
	}
	
	/**
	 * Deletes all scheduled timers in database.
	 */
	public void emptyTimersScheduled() {
		scheduledService.emptyTimersScheduled();
	}
	
	
	/**
	 * Method that retrieves the password for the RFC3161 Authentication Keystore.
	 * @return String that represents the decodified password
	 * @throws CryptographyException If the method fails
	 */
	private String getRfc3161KeystorePassword() throws CryptographyException {
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161));
		final String encodedPassword = keystoreService.getKeystoreById(KeystoreMonitoriza.ID_AUTHCLIENT_RFC3161).getPassword();
		
		return keyStoreFacade.getKeystoreDecodedPasswordString(encodedPassword);		
		
	}

	/**
	 * Gets the value of the attribute {@link #scheduledTimers}.
	 * @return the value of the attribute {@link #scheduledTimers}.
	 */
	public static Map<Long, Timer> getScheduledTimers() {
		return scheduledTimers;
	}

	/**
	 * Sets the value of the attribute {@link #scheduledTimers}.
	 * @param scheduledTimersParam the value for the attribute {@link #scheduledTimers} to set.
	 */
	public static void setScheduledTimers(Map<Long, Timer> scheduledTimersParam) {
		AdminServicesManager.scheduledTimers = scheduledTimersParam;
	}
	
	/**
	 * Method that stores a {@link #DailyVipMonitorig} in persistence.
	 * @param daily {@link #DailyVipMonitorig} to save
	 */
	public void saveDailyVip(DailyVipMonitorig daily) {
	
		try {
			dailyVipService.saveDailyVipMonitoring(daily);
		} catch (DatabaseException e) {
			String msg = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS018);
			LOGGER.error(msg, e);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_STATUS_VIP_SAVE, msg);
		}
	}
	
	/**
	 * Method that stores a {@link #DailySpieMonitorig} in persistence.
	 * @param daily {@link #DailySpieMonitorig} to save
	 */
	public void saveDailySpie(DailySpieMonitorig daily) {
	
		try {
			dailySpieService.saveDailySpieMonitoring(daily);
		} catch (DatabaseException e) {
			String msg = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS019);
			LOGGER.error(msg, e);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_STATUS_SPIE_SAVE, msg);
		}
	}
		
}
