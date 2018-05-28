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
 * <b>Date:</b><p>03/03/2015.</p>
 * @author Gobierno de España.
 * @version 1.2, 23/02/2017.
 */
package es.gob.monitoriza.crypto.keystore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.utils.CryptographyValidationUtils;
import es.gob.monitoriza.i18n.LanguageWeb;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.service.IKeystoreService;

/**
 * <p>Class that manages all the operations related with JCE, JCEKS and PKCS#12 keystores.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 23/02/2017.
 */
public class KeystoreFacade implements IKeystoreFacade {

	/**
	 * Attribute that represents the Padding algorithm for the AES cipher. 
	 */
	private static final String AES_PADDING_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IKeystoreService keystoreService;
	
	/**
	 * Attribute that represents the AES algorithm name. 
	 */
	private static final String AES_ALGORITHM = "AES";
	
	/**
	 * Attribute that represents . 
	 */
	private static final String AES_PASSWORD = "ABCDEFGHIJKLMNOP";
	
	/**
	 * Constant attribute that represents the name of the property <code>logSK014</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK014 = "logSK014";

	/**
	 * Constant attribute that represents the name of the property <code>logSK009</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK009 = "logSK009";

	/**
	 * Constant attribute that represents the name of the property <code>logSK008</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK008 = "logSK008";

	/**
	 * Constant attribute that represents the name of the property <code>logSK007</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK007 = "logSK007";

	/**
	 * Constant attribute that represents the name of the property <code>logSK006</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK006 = "logSK006";

	/**
	 * Constant attribute that represents the name of the property <code>logSK005</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK005 = "logSK005";

	/**
	 * Constant attribute that represents the name of the property <code>logSK004</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK004 = "logSK004";

	/**
	 * Constant attribute that represents the name of the property <code>logSK003</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK003 = "logSK003";

	/**
	 * Constant attribute that represents the name of the property <code>logSK002</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK002 = "logSK002";

	/**
	 * Constant attribute that represents the name of the property <code>logSK001</code> belonging to the file core/general_xx_YY.properties.
	 */
	private static final String LOG_SK001 = "logSK001";

	/**
	 * Attribute that represents the information about the keystore from the cache system.
	 */
	private Keystore keystore = null;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(KeystoreFacade.class);

	/**
	 * Constructor method for the class StandardKeystore2.java.
	 * @param keystoreCacheObjectParam Parameter that represents the information about the keystore from the cache system.
	 */
	public KeystoreFacade(Keystore keystoreParam) {
		keystore = keystoreParam;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.cryptography.keystore.IKeystoreFacade#storeAlias(java.lang.String, java.security.cert.Certificate, java.security.Key)
	 */
	@Override
	public final Keystore storeAlias(String alias, Certificate certificate, Key key) throws CryptographyException {
		LOGGER.info(LanguageWeb.getResWebMonitoriza(LOG_SK001));
		try {
			// Comprobamos que el certificado no sea nulo
			CryptographyValidationUtils.checkIsNotNull(certificate, LanguageWeb.getResWebMonitoriza(LOG_SK003));

			// Comprobamos que el alias no sea nulo
			CryptographyValidationUtils.checkIsNotNull(alias, LanguageWeb.getResWebMonitoriza(LOG_SK004));
			
			// Tratamos de convertir el objeto Certificate a X509Certificate
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(certificate.getEncoded()));
			
			// Realizamos validación básica del certificado a añadir
			try {
				// Comprobamos el periodo de validez del certificado
				cert.checkValidity(Calendar.getInstance().getTime());
				
			} catch (CertificateExpiredException e) {
				// Certificado caducado
				LOGGER.warn(LanguageWeb.getResWebMonitoriza(LOG_SK005));
			} catch (CertificateNotYetValidException e) {
				// Certificado no válido aún
				LOGGER.warn(LanguageWeb.getResWebMonitoriza(LOG_SK006));
			}
						
			// Actualizamos el almacén de claves físicamente. Si la clave es
			// nula, sólo se insertará el certificado.
			LOGGER.debug(LanguageWeb.getFormatResWebMonitoriza(LOG_SK007, new Object[ ] { alias, keystore.getTokenName() }));
			addEntryToKeystore(alias, certificate, key);
		} catch (CertificateException e) {
			String errorMsg = LanguageWeb.getResWebMonitoriza(LOG_SK008);
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} catch (KeyStoreException e) {
			String errorMsg = LanguageWeb.getFormatResWebMonitoriza(LOG_SK009, new Object[ ] { alias, LanguageWeb.getResWebMonitoriza(keystore.getTokenName()) });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		} finally {
			LOGGER.info(LanguageWeb.getResWebMonitoriza(LOG_SK002));
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
	private void addEntryToKeystore(String alias, Certificate cert, Key key) throws KeyStoreException, CryptographyException {
		
		// Cargamos el keystore SSL desde la persistencia
		KeyStore ks = KeyStore.getInstance(keystore.getKeystoreType());
		ByteArrayInputStream bais = new ByteArrayInputStream(keystore.getKeystore());
		try {
			ks.load(bais, new String(getKeystoreDecodedPassword()).toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error("Error cargando el keystore", e);
		}
						
		if (key == null) {
			ks.setCertificateEntry(alias, cert);
		} else {
			ks.setKeyEntry(alias, key, AES_PASSWORD.toCharArray(), new Certificate[ ] { cert });
		}
		
		// Establecemos el nuevo valor del almacén SSL
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ks.store(baos, new String(getKeystoreDecodedPassword()).toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error("Error modificando el keystore", e);
		}
		
		keystore.setKeystore(baos.toByteArray());		
	}
		
	/**
	 * Method that obtains the decoded password of the keystore represented by {@link #keystore}.
	 * @return the decoded password of the keystore represented by {@link #keystore}.
	 * @throws CryptographyException If the method fails.
	 */
	private byte[] getKeystoreDecodedPassword() throws CryptographyException {
		try {
			SecretKeySpec key = new SecretKeySpec(AES_PASSWORD.getBytes(), AES_ALGORITHM);
			Cipher cipher = Cipher.getInstance(AES_PADDING_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			return cipher.doFinal(Base64.decodeBase64(keystore.getPassword()));
		} catch (Exception e) {
			String errorMsg = LanguageWeb.getFormatResWebMonitoriza(LOG_SK014, new Object[ ] { keystore.getTokenName() });
			LOGGER.error(errorMsg, e);
			throw new CryptographyException(errorMsg, e);
		}
	}
	
}
