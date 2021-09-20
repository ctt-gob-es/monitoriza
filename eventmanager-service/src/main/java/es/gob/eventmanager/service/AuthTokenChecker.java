package es.gob.eventmanager.service;

import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para la comprobaci&oacute;n de tokens de autenticaci&oacute;n.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 07/09/2021.
 */
public class AuthTokenChecker {

	private static final int ITERACTIONS = 65536;
	private static final int KEY_LENGTH = 128;

	/** Algoritmo HMAC a utilizar. */
	private static final String HMAC_ALGORITHM = "HmacSHA256"; //$NON-NLS-1$

	/** Algoritmo de deribaci&oacute;n de clave a partir de contrase&ntilde;a que se utiliza. */
	private static final String PASSWORD_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256"; //$NON-NLS-1$

	/** Algoritmo que define el tipo de clave a utilizar. */
	private static final String KEY_ALGORITHM = "AES"; //$NON-NLS-1$

	/**
	 * Comprueba si un c&oacute;digo de validaci&oacute;n se gener&oacute; en base a una clave, un salto y un mensaje concreto.
	 * @param secureCode C&oacute;digo de validaci&oacute;n que deseamos comprobar.
	 * @param basePwd Contrase&ntilde;a a partir de la cual se genera el c&oacute;digo de validaci&oacute;n.
	 * @param salt Salto sobre la contrase&ntilde;a.
	 * @param message Mensaje sobre el que se gener&oacute; el c&oacute;digo.
	 * @throws SecurityException Cuando el c&oacute;digo no es v&aacute;lido o no se pudo comprobar.
	 */
	static void checkAuthToken(final byte[] secureCode, final char[] basePwd, final byte[] salt, final byte[] message) throws SecurityException {

		// Generamos la clave
		final SecretKey key = generateKey(basePwd, salt);

		// Obtenemos el token
		byte[] hmacSha256 = null;
		try {
			final Mac mac = Mac.getInstance(HMAC_ALGORITHM);
			mac.init(key);
			hmacSha256 = mac.doFinal(message);
		} catch (final Exception e) {
			throw new SecurityException("No se ha podido calcular el token de autenticacion", e); //$NON-NLS-1$
		}

		if (!Arrays.equals(secureCode, hmacSha256)) {
			throw new SecurityException("Token de autorizacion incorrecto");
		}
	}

	/**
	 * Genera la clave de autorizaci&oacute;.
	 * @param basePwd Contrase&ntilde;a en base a la cual se genera.
	 * @param salt Salto para la generaci&oacute;n de la clave.
	 * @return Clave de autorizaci&oacute;n.
	 * @throws SecurityException Cuando no se puede generar la clave.
	 */
	private static SecretKey generateKey(final char[] basePwd, final byte[] salt) throws SecurityException {
		final byte[] keyEncoded;
		try {
			// Aplicamos el salto a la clave indicado junto con una serie de iteraciones
			final SecretKeyFactory factory = SecretKeyFactory.getInstance(PASSWORD_DERIVATION_ALGORITHM);
			final KeySpec spec = new PBEKeySpec(basePwd, salt, ITERACTIONS, KEY_LENGTH);
			final SecretKey tmp = factory.generateSecret(spec);
			keyEncoded = tmp.getEncoded();
		}
		catch (final Exception e) {
			throw new SecurityException("No se ha podido generar de cifrado del token", e); //$NON-NLS-1$
		}
        return new SecretKeySpec(keyEncoded, KEY_ALGORITHM);
	}
}
