/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2015 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.ws.engine.WSAuthSSLX509TrustManager.java.</p>
 * <b>Description:</b><p>Class that manages the trusted KeyStore for the SSL connections with mutual authentication.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>23/04/2009.</p>
 * @author Gobierno de España.
 * @version 1.3, 26/09/2023.
 */
package es.gob.monitoriza.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>Class that manages the trusted KeyStore for the SSL connections with mutual authentication.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.3, 26/09/2023.
 */
public class WSAuthSSLX509TrustManager implements X509TrustManager {

	/**
	 * Attribute that represents the default trust manager.
	 */
	private X509TrustManager defaultTrustManager = null;

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Log LOGGER = LogFactory.getLog(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Constructor method for the class WSAuthSSLX509TrustManager.java.
	 * @param defaultTrustManagerParam Parameter that represents the trust manager.
	 */
	public WSAuthSSLX509TrustManager(final X509TrustManager defaultTrustManagerParam) {
		super();
		if (defaultTrustManagerParam == null) {
			throw new IllegalArgumentException("Trust manager may not be null");
		}
		this.defaultTrustManager = defaultTrustManagerParam;
	}

	/**
	 * {@inheritDoc}
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkClientTrusted(X509Certificate[ ] certificates, String authType) throws CertificateException {
		if (LOGGER.isInfoEnabled() && certificates != null) {
			for (int c = 0; c < certificates.length; c++) {
				X509Certificate cert = certificates[c];
				LOGGER.info(" Client certificate " + (c + 1) + ":");
				LOGGER.info("  Subject DN: " + cert.getSubjectDN());
				LOGGER.info("  Signature Algorithm: " + cert.getSigAlgName());
				LOGGER.info("  Valid from: " + cert.getNotBefore());
				LOGGER.info("  Valid until: " + cert.getNotAfter());
				LOGGER.info("  Issuer: " + cert.getIssuerDN());
			}
		}
		defaultTrustManager.checkClientTrusted(certificates, authType);
	}

	/**
	 * {@inheritDoc}
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkServerTrusted(X509Certificate[ ] certificates, String authType) throws CertificateException {
		if (LOGGER.isInfoEnabled() && certificates != null) {
			for (int c = 0; c < certificates.length; c++) {
				X509Certificate cert = certificates[c];
				LOGGER.info(" Server certificate " + (c + 1) + ":");
				LOGGER.info("  Subject DN: " + cert.getSubjectDN());
				LOGGER.info("  Signature Algorithm: " + cert.getSigAlgName());
				LOGGER.info("  Valid from: " + cert.getNotBefore());
				LOGGER.info("  Valid until: " + cert.getNotAfter());
				LOGGER.info("  Issuer: " + cert.getIssuerDN());
				if (cert.getPublicKey() instanceof RSAPublicKey) {
					int modulusLength = ((RSAPublicKey) cert.getPublicKey()).getModulus().bitLength();
					if (checkRSAModulusCompatibilityWithDiffieHellmanAlgorithms(modulusLength)) {
						LOGGER.info("  RSA PublicKey Modulus Length: " + modulusLength + " bits.");
					} else {
						LOGGER.info("  RSA PublicKey Modulus Length: " + modulusLength + " bits. --> Not compatible with Diffie-Hellmans Algorithms in Java 1.6!!");
					}
				}
			}
		}
		defaultTrustManager.checkServerTrusted(certificates, authType);
	}

	/**
	 * Checks if the input number (RSA modulus length) is compatible with Diffie-Hellmans
	 * algorithms in Java 1.6. This checks that is a number between 512 and 1024, and its
	 * must be a number multiple of 64.
	 * @param modulusLength number to analyze.
	 * @return <code>true</code> if the number is between 512 and 1024, and its a multiple of 64.
	 */
	private boolean checkRSAModulusCompatibilityWithDiffieHellmanAlgorithms(int modulusLength) {
		return modulusLength >= NumberConstants.NUM512 && modulusLength <= NumberConstants.NUM1024 && modulusLength % NumberConstants.NUM64 == 0;
	}

	/**
	 * {@inheritDoc}
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	public X509Certificate[ ] getAcceptedIssuers() {
		return this.defaultTrustManager.getAcceptedIssuers();
	}
}
