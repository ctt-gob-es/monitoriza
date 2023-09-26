/* 
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
 * <b>File:</b><p>es.gob.monitoriza.cron.ValidCertificatesJob.java.</p>
 * <b>Description:Class that manages the scheduled job for certificate validation</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>17/08/2021.</p>
 * @author Gobierno de España.
 * @version 1.2, 26/09/2023.
 */
package es.gob.monitoriza.cron;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.ServletContext;

import es.gob.monitoriza.utilidades.loggers.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.constant.INotificationOriginIds;
import es.gob.monitoriza.constant.INotificationPriority;
import es.gob.monitoriza.constant.INotificationTypeIds;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemNotification;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IStatusCertificateService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.ISystemNotificationService;
import es.gob.monitoriza.service.IValidServiceService;
import es.gob.monitoriza.utilidades.UtilsCertificate;
import es.gob.monitoriza.utilidades.UtilsGrayLog;
import es.gob.monitoriza.utilidades.UtilsStringChar;
import es.gob.monitoriza.utilidades.UtilsXml;
import es.gob.monitoriza.webservice.ClientManager;

/** 
 * <p>Class that manages the scheduled job for certificate validation.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 26/09/2023.
 */
@Service
public class ValidCertificatesJob implements SchedulerObjectInterface {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the service object for accessing the
	 * valid service repository.
	 */
	@Autowired
	private IValidServiceService validServiceService;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private ISystemCertificateService sysCertService;

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IKeystoreService keystoreService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IStatusCertificateService statusCertService;
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ISystemNotificationService sysNotificationService;

	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private ClientManager clientManager;

	/**
	 * Attribute that represents the context object.
	 */
	@Autowired
	private ServletContext context;
	
	/**
	 * Constructor method for the class ValidCertificatesJob.java.
	 */
	public ValidCertificatesJob() {
		super();
	}
    
    /**
     * Attribute that represents the task execution result. 
     */
    @SuppressWarnings("rawtypes")
	private ScheduledFuture future;


    /**
     * {@inheritDoc}
     * @see es.gob.monitoriza.cron.SchedulerObjectInterface#start()
     */
    @Override
    public void start() {
        future = new ConcurrentTaskScheduler().schedule(new Runnable() {
            @Override
            public void run() {
            	try {
            		validCertificatesJobScheduled();
				} catch (Exception e) {
					LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE007), e.getCause());
				}
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
            	String cronExpression = UtilsStringChar.EMPTY_STRING;
            	Date nextExec = null;
            	List<ValidService> validServices = null;
            	ValidService validService = null;
            	//ISystemNotificationService sysNotificationService = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.SYSTEM_NOTIFICATION_SERVICE, SystemNotificationService.class);
            	try {
            		validServices = validServiceService.getAllValidServices();
            		if (!validServices.isEmpty()) {
                		validService = validServices.get(0);
                		cronExpression = validService.getCronExpression();
                	}
                    CronTrigger trigger = new CronTrigger(cronExpression);
                    nextExec = trigger.nextExecutionTime(triggerContext);
                    
                    // Si la ejecución es correcta, se comprueba si hay que eliminar
                    // el aviso correspondiente.
                    SystemNotification sysNot = sysNotificationService.getSystemNotificationByOrigin(INotificationOriginIds.ID_CONFIG_VALIDATION_SERVICE_ORIGIN);
                    if (sysNot != null)
                    {
                    	sysNotificationService.deleteSystemNotification(sysNot);
            		}
            	} catch (Exception e) {
            		String msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE007);
            		LOGGER.error(msgError, e);
            		UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_VALIDATION_SERVICE_CONFIG, msgError);
            		 
            		// Se comprueba si es necesario registrar el aviso.
					if (sysNotificationService.getSystemNotificationByOrigin(INotificationOriginIds.ID_CONFIG_VALIDATION_SERVICE_ORIGIN) == null) {

						// Se registra la notificación asociada al registro del
						// nodo.
						sysNotificationService.registerSystemNotification(INotificationTypeIds.ID_CONFIG_NOTIFICATION_TYPE, INotificationOriginIds.ID_CONFIG_VALIDATION_SERVICE_ORIGIN, INotificationPriority.ID_NOTIFICATION_PRIORITY_IMPORTANT, msgError);
					}
            	}
                return nextExec;
            }
        });

    }

    /**
     * {@inheritDoc}
     * @see es.gob.monitoriza.cron.SchedulerObjectInterface#stop()
     */
    @Override
    public void stop() {
        future.cancel(false);
    }

	/**
	 * Method that starts the CRLs download.
	 * @throws Exception exception
	 */
	private void validCertificatesJobScheduled() {
		LOGGER.info("Init validCertificatesJobScheduled");
		String aliasCertificate = UtilsStringChar.EMPTY_STRING;
		try {
			List<ValidService> validServices = validServiceService.getAllValidServices();
			ValidService validService = null;
			if (!validServices.isEmpty()) {
				validService = validServices.get(0);
				if (validService.getIsEnableValidationJob() != null && validService.getIsEnableValidationJob()) {
					List<SystemCertificate> systemCertificates = StreamSupport.stream(sysCertService.getAllSystemCertificate().spliterator(), false).collect(Collectors.toList());

					for (SystemCertificate systemCertificate: systemCertificates) {
						String certificateBase64 = null;
						IKeystoreFacade keyStoreFacade = new KeystoreFacade(systemCertificate.getKeystore());
						KeystoreMonitoriza ks = keystoreService.getKeystoreById(systemCertificate.getKeystore().getIdKeystore());
						KeyStore ksCetificate = KeystoreFacade.getKeystore(ks.getKeystore(), ks.getKeystoreType(), keyStoreFacade.getKeystoreDecodedPasswordString(ks.getPassword()));
						aliasCertificate = systemCertificate.getAlias();
						if (aliasCertificate != null) {
							Certificate cert;
							cert = ksCetificate.getCertificate(aliasCertificate);
							if (cert != null) {
								String protocol = validService.getIsSecure() ? UtilsCertificate.PROTOCOL_HTTPS : UtilsCertificate.PROTOCOL_HTTP;
								String host = validService.getHost();
								String port = validService.getPort();
								String endpoint = protocol + "://" + host + ":" + port + UtilsCertificate.VALID_SERVICE_ENDPOINT;
								certificateBase64 = Base64.getEncoder().encodeToString(cert.getEncoded());
								Object[ ] peticion = UtilsXml.getXmlValidation(context.getRealPath(UtilsCertificate.PATH_CERT_VALIDATION_REPORT), validService.getApplication(), certificateBase64);
								String result = clientManager.getDSSCertificateServiceClientResult(endpoint, validService, peticion);
								Long statusCertificateId = UtilsCertificate.processStatusCertificate(result);
								systemCertificate.setStatusCertificate(statusCertService.getStatusCertificateById(statusCertificateId));
								sysCertService.saveSystemCertificate(systemCertificate);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			
			String msgError = null;
			if (aliasCertificate.isEmpty()) {
				msgError = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE008);
				LOGGER.error(msgError, e);
				
			} else {
				msgError = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE008, new Object[ ] { aliasCertificate });
				LOGGER.error(msgError, e);
			}
			
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_VALIDATION_SERVICE_TASK, msgError);
			
		}
		LOGGER.info("End validCertificatesJobScheduled");
	}

	/**
	 * Get validServiceService.
	 * @return validServiceService
	 */
	public IValidServiceService getValidServiceService() {
		return validServiceService;
	}

	/**
	 * Set validServiceService.
	 * @param validServiceServiceParam set {#validServiceService}
	 */
	public void setValidServiceService(IValidServiceService validServiceServiceParam) {
		this.validServiceService = validServiceServiceParam;
	}

	/**
	 * Get sysCertService.
	 * @return sysCertService
	 */
	public ISystemCertificateService getSysCertService() {
		return sysCertService;
	}

	/**
	 * Set sysCertService.
	 * @param sysCertServiceParam set {#sysCertService}
	 */
	public void setSysCertService(ISystemCertificateService sysCertServiceParam) {
		this.sysCertService = sysCertServiceParam;
	}

	/**
	 * Get keystoreService.
	 * @return keystoreService
	 */
	public IKeystoreService getKeystoreService() {
		return keystoreService;
	}

	/**
	 * Set keystoreService.
	 * @param keystoreServiceParam set {#keystoreService}
	 */
	public void setKeystoreService(IKeystoreService keystoreServiceParam) {
		this.keystoreService = keystoreServiceParam;
	}

	/**
	 * Get statusCertService.
	 * @return statusCertService
	 */
	public IStatusCertificateService getStatusCertService() {
		return statusCertService;
	}

	/**
	 * Set statusCertService.
	 * @param statusCertServiceParam set {#statusCertService}
	 */
	public void setStatusCertService(IStatusCertificateService statusCertServiceParam) {
		this.statusCertService = statusCertServiceParam;
	}

	/**
	 * Get clientManager.
	 * @return clientManager
	 */
	public ClientManager getClientManager() {
		return clientManager;
	}

	/**
	 * Set clientManager.
	 * @param clientManagerP set clientManager
	 */
	public void setClientManager(ClientManager clientManagerP) {
		this.clientManager = clientManagerP;
	}

	/**
	 * Get context.
	 * @return context
	 */
	public ServletContext getContext() {
		return context;
	}

	/**
	 * Set context.
	 * @param contextParam set {#context}
	 */
	public void setContext(ServletContext contextParam) {
		this.context = contextParam;
	}
	
}
