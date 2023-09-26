/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2017 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.cryptography.keystore.StandardKeystore2.java.</p>
 * <b>Description:</b><p>Class that manages all the operations related with JCE, JCEKS and PKCS#12 keystores.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI certificates and electronic signature.</p>
 * <b>Date:</b><p>03/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.8, 26/09/2023.
 */
package es.gob.monitoriza.crypto.keystore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import es.gob.monitoriza.utilidades.loggers.Logger;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.utils.CryptographyValidationUtils;
import es.gob.monitoriza.exception.CipherException;
import es.gob.monitoriza.i18n.ICoreLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.utilidades.AESCipher;

/**
 * <p>Class that manages all the operations related with JCE, JCEKS and PKCS#12 keystores.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.8, 26/09/2023.
 */
public class KeystoreFacade implements IKeystoreFacade {

	/**
	 * Attribute that represents a p12 key store file extension.
	 */
	public static final String P12_KEYSTORE_EXT = "p12";

	/**
	 * Attribute that represents a pfx key store file extension.
	 */
	public static final String PFX_KEYSTORE_EXT = "pfx";

	/**
	 * Attribute that represents the PKCS#12 keystore type.
	 */
	public static final String PKCS12 = "PKCS12";
	
	/**
	 * Attribute that represents the information about the keystore from the cache system.
	 */
	private KeystoreMonitoriza keystore;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constructor method for the class KeystoreFacade.
	 * @param keystoreParam Parameter that represents the information about the keystore from the persistence.
	 */
	public KeystoreFacade(final KeystoreMonitoriza keystoreParam) {
		keystore = keystoreParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.cryptography.keystore.IKeystoreFacade#storeCertificate(java.lang.String, java.security.cert.Certificate, java.security.Key)
	 */
	@Override
	public final KeystoreMonitoriza storeCertificate(final String alias, final Certificate certificate, final Key key) throws CryptographyException {
		LOGGER.info(Language.getResCoreMonitoriza(ICoreLogMessages.CORE001));
		try {
			// Comprobamos que el certificado no sea nulo
			CryptographyValidationUtils.checkIsNotNull(certificate, Language.getResCoreMonitoriza(ICoreLogMessages.CORE003));

			// Comprobamos que el alias no sea nulo
			CryptographyValidationUtils.checkIsNotNull(alias, Language.getResCoreMonitoriza(ICoreLogMessages.CORE004));

			// Tratamos de convertir el objeto Certificate a X509Certificate
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(certificate.getEncoded()));

			// Realizamos validación básica del certificado a añadir
			try {
				// Comprobamos el periodo de validez del certificado
				cert.checkValidity(Calendar.getInstance().getTime());

			} catch (CertificateExpiredException e) {
				// Certificado caducado
				LOGGER.warn(Language.getResCoreMonitoriza(ICoreLogMessages.CORE005));
			} catch (CertificateNotYetValidException e) {
				// Certificado no válido aún
				LOGGER.warn(Language.getResCoreMonitoriza(ICoreLogMessages.CORE006));
			}

			// Actualizamos el almacén de claves físicamente. Si la clave es
			// nula, sólo se insertará el certificado.
			LOGGER.debug(Language.getFormatResCoreMonitoriza(ICoreLogMessages.CORE007, new Object[ ] { alias, keystore.getName() }));
			addEntryToKeystore(alias, certificate, key);
		} catch (CertificateException e) {
			String errorMsg = Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE001);
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} catch (KeyStoreException e) {
			String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE002, new Object[ ] { alias, keystore.getName() });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} finally {
			LOGGER.info(Language.getResCoreMonitoriza(ICoreLogMessages.CORE002));
		}
		// Devolvemos los datos en caché actualizados del almacén de claves
		return keystore;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.crypto.keystore.IKeystoreFacade#updateCertificate(java.lang.String, java.lang.String, java.security.cert.Certificate, java.security.Key)
	 */
	@Override
	public KeystoreMonitoriza updateCertificateAlias(final String oldEntryAlias, final String newEntryAlias) throws CryptographyException {
		LOGGER.info(Language.getResCoreMonitoriza(ICoreLogMessages.CORE001));
		try {

			// Comprobamos que el alias no sea nulo
			CryptographyValidationUtils.checkIsNotNull(newEntryAlias, Language.getResCoreMonitoriza(ICoreLogMessages.CORE004));

			// Actualizamos el almacén de claves físicamente. Si la clave es
			// nula, sólo se insertará el certificado.
			LOGGER.debug(Language.getFormatResCoreMonitoriza(ICoreLogMessages.CORE007, new Object[ ] { newEntryAlias, keystore.getTokenName() }));
			updateEntryToKeystore(oldEntryAlias, newEntryAlias);
		} catch (KeyStoreException e) {
			String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE002, new Object[ ] { newEntryAlias, Language.getResCoreMonitoriza(keystore.getTokenName()) });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException e) {
			String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE004, new Object[ ] { oldEntryAlias, newEntryAlias, Language.getResCoreMonitoriza(keystore.getTokenName()) });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} finally {
			LOGGER.info(Language.getResCoreMonitoriza(ICoreLogMessages.CORE002));
		}
		// Devolvemos los datos en caché actualizados del almacén de claves
		return keystore;
	}

	/**
	 * Method that inserts an entry inside of a keystore.
	 * @param alias Parameter that represents the alias of the entry to store.
	 * @param cert Parameter that represents the certificate associated to the new entry.
	 * @param key Parameter that represents the private key associated to the new entry.
	 * @throws KeyStoreException If there is some error inserting the entry into the keystore.
	 * @throws CryptographyException If there is some error decrypting the password of the keystore.
	 */
	private void addEntryToKeystore(final String alias, final Certificate cert, final Key key) throws KeyStoreException, CryptographyException {

		// Cargamos el keystore desde la persistencia
		KeyStore kstore = KeyStore.getInstance(keystore.getKeystoreType());
		char[ ] ksPass = new String(getKeystoreDecodedPassword(null)).toCharArray();

		try (ByteArrayInputStream bais = new ByteArrayInputStream(keystore.getKeystore());) {
			kstore.load(bais, ksPass);

		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE010), e);
		}

		if (key == null) {
			kstore.setCertificateEntry(alias, cert);
		} else {
			kstore.setKeyEntry(alias, key, ksPass, new Certificate[ ] { cert });
		}

		// Establecemos el nuevo valor del almacén
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			kstore.store(baos, ksPass);

			keystore.setKeystore(baos.toByteArray());
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE011), e);
		}

	}

	/**
	 * Method that updates an alias entry inside of a keystore.
	 * @param oldEntryAlias Parameter that represents the alias of the entry to update.
	 * @param newEntryAlias Parameter that represents the new value for the alias.
	 * @throws UnrecoverableKeyException If the key cannot be recovered (e.g., the given password is wrong).
	 * @throws KeyStoreException If the keystore has not been initialized (loaded).
	 * @throws NoSuchAlgorithmException If the algorithm for recovering the key cannot be found.
	 * @throws CryptographyException If there is some error decrypting the password of the keystore.
	 * @return an object that represents the keystore.
	 */
	private KeystoreMonitoriza updateEntryToKeystore(final String oldEntryAlias, final String newEntryAlias) throws CryptographyException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
		char[ ] ksPass = new String(getKeystoreDecodedPassword(null)).toCharArray();
		// Cargamos el keystore SSL desde la persistencia
		KeyStore kstore = KeyStore.getInstance(keystore.getKeystoreType());

		try (ByteArrayInputStream bais = new ByteArrayInputStream(keystore.getKeystore());) {
			kstore.load(bais, ksPass);
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE010), e);
		}
		
		if (kstore.containsAlias(oldEntryAlias)) {
			if (kstore.isCertificateEntry(oldEntryAlias)) {
				Certificate cert = kstore.getCertificate(oldEntryAlias);
				kstore.deleteEntry(oldEntryAlias);
				kstore.setCertificateEntry(newEntryAlias, cert);
			} else if (kstore.isKeyEntry(oldEntryAlias)) {
				Key key = kstore.getKey(oldEntryAlias, ksPass);
				Certificate[ ] certChain = kstore.getCertificateChain(oldEntryAlias);
				kstore.deleteEntry(oldEntryAlias);
				kstore.setKeyEntry(newEntryAlias, key, ksPass, certChain);
			}
		}

		// Establecemos el nuevo valor del almacén SSL
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			kstore.store(baos, ksPass);
			keystore.setKeystore(baos.toByteArray());
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException | KeyStoreException e) {
			LOGGER.error(Language.getResCoreMonitoriza(ICoreLogMessages.ERRORCORE011), e);
		}

		return keystore;
	}

	/**
	 * Method that obtains the decoded password of the keystore represented by {@link #keystore}.
	 * @param password to decode.
	 * @return the decoded password of the keystore represented by {@link #keystore}.
	 * @throws CryptographyException If the method fails.
	 */
	private byte[ ] getKeystoreDecodedPassword(final String password) throws CryptographyException {
		try {
	
			return AESCipher.getInstance().decryptMessage(password == null ? keystore.getPassword() : password);
		} catch (CipherException e) {
			String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE003, new Object[ ] { keystore.getTokenName() });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		}
	}

	/**
	 * Method that obtains the decoded password of the keystore.
	 * @param password to decode.
	 * @return the decoded password of the keystore represented.
	 * @throws CryptographyException If the method fails.
	 */
	@Override
	public String getKeystoreDecodedPasswordString(final String password) throws CryptographyException {
		String passwordDecode = null;
		passwordDecode = new String(getKeystoreDecodedPassword(password));
		return passwordDecode;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.crypto.keystore.IKeystoreFacade#deleteCertificate(java.lang.String)
	 */
	@Override
	public KeystoreMonitoriza deleteCertificate(final String alias) throws CryptographyException {

		char[ ] ksPass = new String(getKeystoreDecodedPassword(null)).toCharArray();

		// Cargamos el keystore SSL desde la persistencia
		try (ByteArrayInputStream bais = new ByteArrayInputStream(keystore.getKeystore());) {
			KeyStore kstore = KeyStore.getInstance(keystore.getKeystoreType());
			kstore.load(bais, ksPass);

			if (kstore.containsAlias(alias)) {				// Si existe la entrada, la elimino
				kstore.deleteEntry(alias);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			kstore.store(baos, ksPass);
			keystore.setKeystore(baos.toByteArray());
		} catch (NoSuchAlgorithmException | CertificateException | IOException
				| KeyStoreException e) {
			String errorMsg = Language.getFormatResCoreMonitoriza(ICoreLogMessages.ERRORCORE002, new Object[ ] { alias, Language.getResCoreMonitoriza(keystore.getTokenName()) });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} finally {
			LOGGER.info(Language.getResCoreMonitoriza(ICoreLogMessages.CORE002));
		}

		return keystore;
	}

	/**
	 * Method that obtains a {@link KeyStore}.
	 * @param keyStoreData Parameter that represents the input stream from which the keystore is loaded.
	 * @param keyStoreType Parameter that represents the keystore type.
	 * @param keyStorePass Parameter that represents the keystore password.
	 * @return a {@link KeyStore}.
	 * @throws KeyStoreException If the metod fails.
	 * @throws NoSuchAlgorithmException If the metod fails.
	 * @throws CertificateException If the metod fails.
	 * @throws IOException If the metod fails.
	 */
	public static KeyStore getKeystore(final byte[ ] keyStoreData, final String keyStoreType, final String keyStorePass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore kstore = KeyStore.getInstance(keyStoreType);

		try (ByteArrayInputStream bais = new ByteArrayInputStream(keyStoreData);) {
			kstore.load(bais, keyStorePass.toCharArray());
			return kstore;
		}
	}

	/**
	 * Calculates keystore type.
	 * @param nameFile Name of the keystore file.
	 * @return String with keystore type.
	 */
	public String getKeystoreType(final String nameFile) {
		String keyStoreType = null;

		if (nameFile.endsWith(P12_KEYSTORE_EXT) || nameFile.endsWith(PFX_KEYSTORE_EXT)) {
			keyStoreType = PKCS12;
		}
		return keyStoreType;
	}

	/**
	 * Lists all certificates alias includes in this keystore.
	 *
	 * @param keyStore KeyStore from which extract the aliases.
	 * @return all certificates alias includes in this keystore. If the keystore is <code>null</code>,
	 * then returns <code>null</code>;
	 * @throws KeyStoreException In case of some error extracting the aliases from the keystore.
	 */
	public List<String> listAllAliases(final KeyStore keyStore) throws KeyStoreException {
		List<String> result = null;
		if (keyStore != null) {
			result = Collections.list(keyStore.aliases());
		}
		return result;
	}

	/**
	 * Get keystore.
	 * @return keystore
	 */
	public KeystoreMonitoriza getKeystore() {
		return keystore;
	}

	/**
	 * Set keystore.
	 * @param keystoreParam set keystore
	 */
	public void setKeystore(final KeystoreMonitoriza keystoreParam) {
		this.keystore = keystoreParam;
	}
		
}
