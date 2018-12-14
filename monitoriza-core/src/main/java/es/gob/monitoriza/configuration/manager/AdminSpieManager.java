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
 * <b>File:</b><p>es.gob.monitoriza.configuration.manager.AdminSpieManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 22/10/2018.
 */
package es.gob.monitoriza.configuration.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.ConfSpieDTO;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfSpie;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SpieType;
import es.gob.monitoriza.persistence.configuration.model.repository.SpieTypeRepository;
import es.gob.monitoriza.service.IConfSpieService;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.service.IMethodValidationService;
import es.gob.monitoriza.service.INodeMonitorizaService;
import es.gob.monitoriza.service.ISpieScheduledService;

/** 
 * <p>Class that manages the configuration of the @firma/ts@ SPIE from database persistence
 *    for use in the status servlet.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 22/10/2018.
 */
@Service("adminSpieManager")
public class AdminSpieManager {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents the service object for accessing the service
	 * repository.
	 */
	@Autowired
	private IConfSpieService spieService;
	
	/**
	 * Attribute that represents the service object for accessing the service
	 * repository.
	 */
	@Autowired
	private ISpieScheduledService scheduledService;
	
	/**
	 * Attribute that represents the service object for accessing the service
	 * repository.
	 */
	@Autowired
	private INodeMonitorizaService nodeService;
	
	/**
	 * Attribute that represents the service object for accessing the keystore
	 * repository.
	 */
	@Autowired
	private IKeystoreService keystoreService;
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private IMethodValidationService methodValidationService; 
	
	/**
	 * Attribute that represents the service object for accessing the repository.
	 */
	@Autowired
	private SpieTypeRepository spieRepository;
		
	/**
	 * Get the general configuration for monitoring the SPIE.
	 * @return {@link ConfSpie}
	 */
	public ConfSpieDTO getSpieConfiguration() {
		
		ConfSpie confSpie = spieService.getAllConfSpie();
		
		return new ConfSpieDTO(confSpie.getIdConfSpie(), confSpie.getPercentAccept(), confSpie.getFrequencyAfirma(), confSpie.getFrequencyTsa(), null, null, methodValidationService.getAllMethodValidationString());
	}
	
	/**
	 * Deletes all scheduled timers in database.
	 */
	public void emptySpieScheduled() {
		
		scheduledService.emptyTimersScheduled();
	}
	
	/**
	 * Method that retrieves the keystore for SSL certificates from database.
	 * @return Keystore containing the certificates for SSL.
	 */
	public KeyStore loadSslTruststore() {
		
		final Keystore ks = keystoreService.getKeystoreByName(GeneralConstants.SSL_TRUST_STORE_NAME);
		
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
	 * Method that obtains all nodes of the specified platform type.
	 * @param idPlatform Platform identifier
	 * @return {@link List<NodeMonitoriza>} of nodes that belongs to the specified platform
	 */
	public List<NodeMonitoriza> getNodesByPlatform(final Long idPlatform) {
		
		return nodeService.getByPlatformType(idPlatform);
		
	}
	
	/**
	 * Method that gets a {@link #SpieType} from persistence.
	 * @param idSpieType SpieType identifier
	 * @return {@link #SpieType}
	 */
	public SpieType getSpieTypeById(final Long idSpieType) {
		
		return spieRepository.findByIdSpieType(idSpieType);
	}
		
}
