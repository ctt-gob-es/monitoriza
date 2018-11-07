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
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>27 sept. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/10/2018.
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IStatusCertificateService;
import es.gob.monitoriza.service.ISystemCertificateService;
import es.gob.monitoriza.service.IValidServiceService;
import es.gob.monitoriza.utilidades.UtilsCertificate;
import es.gob.monitoriza.utilidades.UtilsXml;
import es.gob.monitoriza.webservice.ClientManager;

/** 
 * <p>Class that manages the scheduled job for certificate validation.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/10/2018.
 */
@Service
public class ValidCertificatesJob implements SchedulerObjectInterface {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(ValidCertificatesJob.class);

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
    
    @SuppressWarnings("rawtypes")
	private ScheduledFuture future;


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
            	String cronExpression = "";
            	Date nextExec = null;
            	List<ValidService> validServices = null;
            	ValidService validService = null;
            	try {
            		validServices = validServiceService.getAllValidServices();
            		if (!validServices.isEmpty()) {
                		validService = validServices.get(0);
                		cronExpression = validService.getCronExpression();
                	}
                    CronTrigger trigger = new CronTrigger(cronExpression);
                    nextExec = trigger.nextExecutionTime(triggerContext);
            	} catch (Exception e) {
            		LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE007), e.getCause());
            	}
                return nextExec;
            }
        });

    }

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
		String aliasCertificate = "";
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
						Keystore ks = keystoreService.getKeystoreById(systemCertificate.getKeystore().getIdKeystore());
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
							}
						}
					}
				}
			}
		} catch (Exception e) {
			if (aliasCertificate.isEmpty()) {
				LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE008), e.getCause());
			} else {
				LOGGER.error(Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE008, new Object[ ] { aliasCertificate }), e.getCause());
			}
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
	 * @param validServiceService set validServiceService
	 */
	public void setValidServiceService(IValidServiceService validServiceService) {
		this.validServiceService = validServiceService;
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
	 * @param sysCertService set sysCertService
	 */
	public void setSysCertService(ISystemCertificateService sysCertService) {
		this.sysCertService = sysCertService;
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
	 * @param keystoreService set keystoreService
	 */
	public void setKeystoreService(IKeystoreService keystoreService) {
		this.keystoreService = keystoreService;
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
	 * @param statusCertService set statusCertService
	 */
	public void setStatusCertService(IStatusCertificateService statusCertService) {
		this.statusCertService = statusCertService;
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
	 * @param context set context
	 */
	public void setContext(ServletContext context) {
		this.context = context;
	}
	
}
