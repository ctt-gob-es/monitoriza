package es.gob.eventmanager.client;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para la construcci&oacute;n de tokens de autenticaci&oacute;n.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 07/09/2021.
 */
public class AuthTokenBuilder {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int ITERACTIONS = 65536;
	private static final int KEY_LENGTH = 128;
	private static final int SALT_LENGTH = 32;

	/** Algoritmo HMAC a utilizar. */
	private static final String HMAC_ALGORITHM = "HmacSHA256"; //$NON-NLS-1$

	/** Algoritmo de deribaci&oacute;n de clave a partir de contrase&ntilde;a que se utiliza. */
	private static final String PASSWORD_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256"; //$NON-NLS-1$

	/** Algoritmo que define el tipo de clave a utilizar. */
	private static final String KEY_ALGORITHM = "AES"; //$NON-NLS-1$

	/**
	 * Construye un token de autenticaci&oacute;n para el env&iacute;o de un mensaje concreto.
	 * @param systemId Identificador de aplicaci&oacute;n que emite el mensaje.
	 * @param basePwd Contrase&ntilde;a a partir de la cual se genera la clave HMAC a utilizar.
	 * @param message Mensaje que se desea enviar.
	 * @return Dupla base64(salto):base64(hash).
	 * @throws SecurityException Cuando no es posible generar el token.
	 */
	static String buildAuthToken(final String systemId, final char[] basePwd, final byte[] message) throws SecurityException {

		// Generamos el salto
		final byte[] salt = getNextSalt();

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

		// Formateamos la salida:   appId:base64(salto):base64(hash)
		final Encoder encoder = Base64.getEncoder();
	    return systemId + ":" + encoder.encodeToString(salt) + ":" + encoder.encodeToString(hmacSha256); //$NON-NLS-1$
	}

	/**
	 * Obtiene un salto aleatorio para la generacion de la clave.
	 * @return Salto.
	 */
	private static byte[] getNextSalt() {
		final byte[] salt = new byte[SALT_LENGTH];
		RANDOM.nextBytes(salt);
		return salt;
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
