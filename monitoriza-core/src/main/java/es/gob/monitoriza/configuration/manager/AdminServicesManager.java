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
 * <b>Date:</b><p>19 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 19 ene. 2018.
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;
import es.gob.monitoriza.persistence.configuration.dto.ConnectionDTO;
import es.gob.monitoriza.persistence.configuration.dto.ServiceDTO;
import es.gob.monitoriza.persistence.configuration.dto.TimerDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.MailMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ServiceMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.TimerMonitoriza;
import es.gob.monitoriza.service.IAlarmMonitorizaService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IServiceMonitorizaService;
import es.gob.monitoriza.service.ITimerMonitorizaService;
import es.gob.monitoriza.utilidades.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ services from database persistence.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 30/08/2018.
 */
@Service("adminServicesManager")
public class AdminServicesManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	@Autowired
	IServiceMonitorizaService serviceService;
	
	@Autowired
	ITimerMonitorizaService timerService;
	
	@Autowired
	IKeystoreService keystoreService;
	
	@Autowired
	IAlarmMonitorizaService alarmService;
	
	/**
	 * Method that gets all timers from database
	 * @return List of TimerDTO
	 */
	public List<TimerDTO> getAllTimers() {
		
		final List<TimerMonitoriza> timers = StreamSupport.stream(timerService.getAllTimerMonitoriza().spliterator(), false).collect(Collectors.toList());
		final List<TimerDTO> timersDTO = new ArrayList<TimerDTO>();
		
		for (TimerMonitoriza timer : timers) {
			
			timersDTO.add(new TimerDTO(timer.getIdTimer(), timer.getName(), timer.getFrequency()));
		}
		
		return timersDTO;
	}
	

	/**
	 * Method that gets the services  from persistence (database or static properties file)
	 * @param timerId The Identifier of the timer configured in the service
	 * @return List with the service configuration which its timer matches with the parameter timerId
	 */
	public List<ServiceDTO> getServicesByTimer(final TimerDTO timerDTO) {

		final List<ServiceDTO> servicesTimer = new ArrayList<ServiceDTO>();
				
		final TimerMonitoriza timer = new TimerMonitoriza();
		timer.setIdTimer(timerDTO.getIdTimer());
		//timer.setName(timerDTO.getName());
		List<ServiceMonitoriza> servicesByTimer = StreamSupport.stream(serviceService.getAllByTimer(timer).spliterator(), false)
				.collect(Collectors.toList());
		ServiceDTO serviceDTO = null;
		
		for (ServiceMonitoriza service : servicesByTimer) {
									
			serviceDTO = new ServiceDTO(service.getIdService(), service.getName(), service.getTimer().getName(), service.getTimeout(), service.getNameWsdl(), service.getDegradedThreshold(), service.getLostThreshold().toString(), getDirectoryPath(service.getName()), isAfirmaPlatform(service.getPlatform().getPlatformType().getName()), service.getServiceType(), service.getPlatform().getIdPlatform());
					
			// Base URL de cada plataforma sin tener en cuenta ningún contexto. Servirá para construir la URL de invocación OCSP y RFC3161.
			serviceDTO.setBaseUrl(getBaseUrl(service.getPlatform()));
			// URL de los servicios SOAP hasta el contexto. En la clase HttpSoapInvoker se completará con el WSDL endpoint.
			serviceDTO.setSoapUrl(getBaseUrl(service.getPlatform()).concat(service.getPlatform().getServiceContext()));
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
			
			serviceDTO.setBlockTimeAlarm(service.getAlarm().getBlockedTime());
			service.getAlarm().getEmailsDegraded();
						
			serviceDTO.setListMailDegraded(getAddressesFromAlarm(service.getAlarm().getEmailsDegraded()));
			serviceDTO.setListMailDown(getAddressesFromAlarm(service.getAlarm().getEmailsDown()));
			
			servicesTimer.add(serviceDTO);
		}

		return servicesTimer;
	}

	/**
	 * Method that builds and returns the directory path for a service.
	 * @param serviceId The name identifier for the service.
	 * @return String that represents the directory path for the service passed as parameter.
	 */
	private String getDirectoryPath(final String serviceId) {
		
		String basePath = StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY);
		
		return basePath.concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(serviceId);
				
	}

	/**
	 * Method that determines if the service belongs to @Firma or TS@ platform.
	 * @return true if the service belongs to @Firma, false if the service belongs to TS@.
	 */
	private boolean isAfirmaPlatform(final String platformTypeName) {
		
		return GeneralConstants.PLATFORM_AFIRMA.equals(platformTypeName);
	}
	
	
	/**
	 * Method that returns the base URL connection with the platform, considering host, port, etc.
	 * @param service ServiceDTO with the configuration of the service.
	 * @return String that represents the base URL connection with the platform, considering host, port, etc.
	 */
	private String getBaseUrl(final PlatformMonitoriza platform) {
								
		ConnectionDTO connection = new ConnectionDTO(platform.getIsSecure(), platform.getHost(), platform.getPort(), platform.getHttpsPort(), platform.getRfc3161Port(), platform.getServiceContext(), platform.getOcspContext(), platform.getRfc3161Context());		
				
		String port = connection.getSecureMode()? connection.getSecurePort():connection.getPort();
		String protocol = connection.getSecureMode()? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP;
		
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
		
		Keystore ks = keystoreService.getKeystoreByName(GeneralConstants.SSL_TRUST_STORE_NAME);
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResMonitoriza(LogMessages.ERROR_ACCESS_CERTIFICATE_SSL);
			cer = KeyStore.getInstance(ks.getKeystoreType());

			//cer.load(readStream, StaticMonitorizaProperties.getProperty(StaticConstants.SSL_TRUSTTORE_PASSWORD).toCharArray());
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
		
		Keystore ks = keystoreService.getKeystoreByName(GeneralConstants.RFC3161_KEYSTORE_NAME);
		
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(ks);

		String msgError = null;
		KeyStore cer = null;

		try (InputStream readStream = new ByteArrayInputStream(ks.getKeystore());) {
			// Accedemos al almacén de confianza SSL
			msgError = Language.getResMonitoriza(LogMessages.ERROR_KEYSTORE_ACCESS_AUTH_CLIENT_RFC3161);
			cer = KeyStore.getInstance(ks.getKeystoreType());

			cer.load(readStream, keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()).toCharArray());

		} catch (IOException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | CryptographyException ex) {
			LOGGER.error(msgError, ex);
		}

		return cer;
	}
	
	/**
	 * Method that retrieves the addresses from a MailMonitoriza object
	 * @param mailSet
	 * @return
	 */
	private List<String> getAddressesFromAlarm(Set<MailMonitoriza> mailSet) {
		
		List<String> mailList = new ArrayList<String>();
		
		Iterator<MailMonitoriza> mailIterator = mailSet.iterator();
		
		while (mailIterator.hasNext()) {
			
			mailList.add(mailIterator.next().getEmailAddress());
		}
		
		return mailList;
	}
}
