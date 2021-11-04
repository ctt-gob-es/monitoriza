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
 * <b>File:</b><p>es.gob.eventmanager.utils.AESCipher.java.</p>
 * <b>Description:</b><p>Class to decode and encode password using AES algorithm.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.utils;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import es.gob.eventmanager.constant.StaticConstants;
import es.gob.eventmanager.exception.CipherException;

/**
 * <p>Class to decode and encode password using AES algorithm.</p>
* <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
public final class AESCipher implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 1094502415533707370L;

	/**
	 * Attribute that represents the key for decode the passwords.
	 */
	private static Key key;

	/**
	 * Attribute that represents an instance of the class.
	 */
	private static AESCipher instance = null;
	
	/**
	 * Method that inits the class.
	 * @throws CipherException If the method fails.
	 */
	private AESCipher() throws CipherException {
			
		key = new SecretKeySpec(StaticEventManagerConfig.getProperty(StaticConstants.AES_PASSWORD).getBytes(), StaticEventManagerConfig.getProperty(StaticConstants.AES_ALGORITHM));		
	}

	/**
	 * Method that encrypt a message.
	 * @param msg The message to encrypt.
	 * @return the message encrypted.
	 * @throws CipherException If the method fails.
	 */
	public byte[ ] encryptMessage(String msg) throws CipherException {
		
		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance(StaticEventManagerConfig.getProperty(StaticConstants.AES_PADDING_ALG));
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.encodeBase64(cipher.doFinal(msg.getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CipherException("Error codificando mensaje");
		} 
	}
	
	/**
	 * Method that decrypt a message.
	 * @param msg The message to decrypt.
	 * @return the message decrypted.
	 * @throws CipherException If the method fails.
	 */
	public byte[ ] decryptMessage(String msg) throws CipherException {
		
		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance(StaticEventManagerConfig.getProperty(StaticConstants.AES_PADDING_ALG));
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(Base64.decodeBase64(msg));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new CipherException("Error decodificando mensaje");
		} 
	}
	
	/**
	 * Method that obtains an instance of the class.
	 * @return an instance of the class.
	 * @throws CipherException If the method fails.
	 */
	public static synchronized AESCipher getInstance() throws CipherException {
		if (instance == null) {
			instance = new AESCipher();
		}
		return instance;
	}
	
}
