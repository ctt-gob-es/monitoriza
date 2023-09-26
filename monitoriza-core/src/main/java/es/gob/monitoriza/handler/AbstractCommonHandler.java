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
 * <b>File:</b><p>es.gob.monitoriza.handler.AbstractCommonHandler.java.</p>
 * <b>Description:</b><p> Class that represents handlers used in the service invoker.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.2 26/09/2023.
 */
package es.gob.monitoriza.handler;

import java.util.List;
import java.util.Properties;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.keystore.KeystoreVersionFileManager;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.service.impl.KeystoreService;
import es.gob.monitoriza.service.impl.ValidServiceService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class that represents handlers used in the service invoker.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 26/09/2023.
 */
public class AbstractCommonHandler extends AbstractHandler {
	
	/**
	 * Attribute that represents the user name to authenticate the request with UserNameToken, or the alias of the private key defined to to authenticate the
	 * request with BinarySecurityToken.
	 */
	private String userAlias = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the user password to authenticate the request with UserNameToken, or the password of the private key defined to authenticate
	 * the request with BinarySecurityToken.
	 */
	private String password = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents type of password.
	 */
	private String passwordType = WSConstants.PASSWORD_TEXT;
	
	/**
	 * Constant that represents db identifier of the Validation Service Keystore.
	 */
	static private Long ID_VALID_SERVICE_KEYSTORE = 4L;

	/**
	 * Attribute that represents user Keystore.
	 */
	private String userKeystore;

	/**
	 * Attribute that represents user Keystore Pass.
	 */
	private String userKeystorePass;

	/**
	 * Attribute that represents user Keystore Type.
	 */
	private String userKeystoreType;

	/**
	 * {@inheritDoc}
	 * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
	 */
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		return InvocationResponse.CONTINUE;
	}

	/**
	 * Method that configures the properties related to WSS4J cryptographic manager.
	 * @return the configured properties related to WSS4J cryptographic manager.
	 * @throws WSSecurityException If there is an error in loading the cryptographic properties.
	 */
	final Crypto getCryptoInstance() throws WSSecurityException {

		Properties properties = new Properties();
		properties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
		properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", this.userKeystoreType);
		properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", this.userKeystorePass);
		properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", this.userAlias);
		properties.setProperty("org.apache.ws.security.crypto.merlin.alias.password", this.password);
		properties.setProperty("org.apache.ws.security.crypto.merlin.file", this.userKeystore);
		return CryptoFactory.getInstance(properties);
	}
	
	/**
	 * Method that sets the properties for initialize the cryptographic manager of the WSS$J library.
	 * @return the properties for initialize the cryptographic manager of the WSS$J library.
	 * @throws AfirmaException If the method fails.
	 */
	final Crypto initializateCryptoProperties() throws WSSecurityException {
		Properties properties = new Properties();
		try {
			// Accedemos al Almacén de Autorización para el servicio de validación.
			KeystoreMonitoriza ksAuthValidServ = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).getKeystoreById(ID_VALID_SERVICE_KEYSTORE);
			List<ValidService> validServices = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.VALID_SERVICE_SERVICE, ValidServiceService.class).getAllValidServices();
			IKeystoreFacade keyStoreFacade = new KeystoreFacade(ksAuthValidServ);
			properties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
			properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", ksAuthValidServ.getKeystoreType());
			properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", keyStoreFacade.getKeystoreDecodedPasswordString(ksAuthValidServ.getPassword()));
			properties.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", validServices.get(NumberConstants.NUM0).getValidServiceCertificate().getAlias());
			properties.setProperty("org.apache.ws.security.crypto.merlin.alias.password", keyStoreFacade.getKeystoreDecodedPasswordString(ksAuthValidServ.getPassword()));
			properties.setProperty("org.apache.ws.security.crypto.merlin.file", KeystoreVersionFileManager.getAbsolutePathForTheKeystore(ID_VALID_SERVICE_KEYSTORE));
		} catch (Exception e) {
			throw new WSSecurityException("Error al obtener las propiedades crypto del keystore a autenticacion del servicio de validacion", e.getCause());
		}
		return CryptoFactory.getInstance(properties);
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * @return the value of the attribute {@link #password}.
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * @param passParam The value for the attribute {@link #password}.
	 */
	public final void setPassword(String passParam) {
		this.password = passParam;
	}

	/**
	 * Gets the value of the attribute {@link #passwordType}.
	 * @return the value of the attribute {@link #passwordType}.
	 */
	public final String getPasswordType() {
		return passwordType;
	}

	/**
	 * Sets the value of the attribute {@link #passwordType}.
	 * @param passTypeParam The value for the attribute {@link #passwordType}.
	 */
	public final void setPasswordType(String passTypeParam) {
		if ("digest".equalsIgnoreCase(passTypeParam)) {
			this.passwordType = WSConstants.PASSWORD_DIGEST;
		} else if ("clear".equalsIgnoreCase(passTypeParam)) {
			this.passwordType = WSConstants.PASSWORD_TEXT;
		}
	}

	/**
	 * Gets the value of the attribute {@link #userKeystore}.
	 * @return the value of the attribute {@link #userKeystore}.
	 */
	public final String getUserKeystore() {
		return userKeystore;
	}

	/**
	 * Sets the value of the attribute {@link #userKeystore}.
	 * @param userKeystoreParam The value for the attribute {@link #userKeystore}.
	 */
	public final void setUserKeystore(String userKeystoreParam) {
		this.userKeystore = userKeystoreParam;
	}

	/**
	 * Gets the value of the attribute {@link #userKeystorePass}.
	 * @return the value of the attribute {@link #userKeystorePass}.
	 */
	public final String getUserKeystorePass() {
		return userKeystorePass;
	}

	/**
	 * Sets the value of the attribute {@link #userKeystorePass}.
	 * @param userKeyPassParam The value for the attribute {@link #userKeystorePass}.
	 */
	public final void setUserKeystorePass(String userKeyPassParam) {
		this.userKeystorePass = userKeyPassParam;
	}

	/**
	 * Gets the value of the attribute {@link #userKeystoreType}.
	 * @return the value of the attribute {@link #userKeystoreType}.
	 */
	public final String getUserKeystoreType() {
		return userKeystoreType;
	}

	/**
	 * Sets the value of the attribute {@link #userKeystoreType}.
	 * @param userKeyType The value for the attribute {@link #userKeystoreType}.
	 */
	public final void setUserKeystoreType(String userKeyType) {
		this.userKeystoreType = userKeyType;
	}

	/**
	 * Gets the value of the attribute {@link #userAlias}.
	 * @return the value of the attribute {@link #userAlias}.
	 */
	public final String getUserAlias() {
		return userAlias;
	}

	/**
	 * Sets the value of the attribute {@link #userAlias}.
	 * @param userAliasParam The value for the attribute {@link #userAlias}.
	 */
	public final void setUserAlias(String userAliasParam) {
		this.userAlias = userAliasParam;
	}
}
