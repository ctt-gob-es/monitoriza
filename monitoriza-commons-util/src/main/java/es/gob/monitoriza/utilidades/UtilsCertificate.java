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
 * <b>File:</b><p>es.gob.afirma.utilidades.UtilsCertificate.java.</p>
 * <b>Description:</b><p>Class that provides methods for managing certificates.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>10/05/2012.</p>
 * @author Gobierno de España.
 * @version 1.5, 29/11/2017.
 */
package es.gob.monitoriza.utilidades;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * <p>Class that provides methods for managing certificates.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.5, 29/11/2017.
 */
public final class UtilsCertificate {

	private static Logger LOGGER = Logger.getLogger(UtilsCertificate.class);

	/**
	 * Attribute that represents the UTF-8 codification chacarter.
	 */
	public static final String CODIFICACION_UTF_8 = "UTF-8";
	
	/**
	 * Attribute that represents the protocol http.
	 */
	public static final String PROTOCOL_HTTP = "http";
	
	/**
	 * Attribute that represents the protocol https.
	 */
	public static final String PROTOCOL_HTTPS = "https";
	
	
	public static final String VALID_SERVICE_ENDPOINT = "/afirmaws/services/DSSAfirmaVerifyCertificate";
	
	/**
	 * Attribute that represents the path sign validation report.
	 */
	public static final String PATH_CERT_VALIDATION_REPORT = "/WEB-INF/classes/static/xml/validCertificate.xml";

	/**
	 * Attribute that represents the document builder. 
	 */
	public static DocumentBuilder db = null;

	static {
		// Obtención de un parseador de XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(Boolean.TRUE);
		dbf.setIgnoringComments(Boolean.FALSE);
		try {
			db = dbf.newDocumentBuilder();
		} catch (Exception e) {
			LOGGER.error("Error en inicialización");
		}
	}

	/**
	 * Constant that represents a coma separator.
	 */
	private static final String COMA_SEPARATOR = ",";

	/**
	 * Constant that represents a equals character.
	 */
	private static final String EQUALS_CHAR = "=";

	/**
	 * Constant that represents a "x509" Certificate type.
	 */
	private static final String CERTIFICATE_TYPE = "x509";

	/**
	 * Constant that defines a default hash algorithm (SHA-256) used to calculate certificate hash.
	 */
	public static final String DEFAULT_HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Attribute that represents the header to download certificate. 
	 */
	public static final String HEADER_DOWNLOAD_CERTIFICATE = "attachment; filename=certificate.cer";

	/**
	 * Attribute that represents the content disposition to download certificate. 
	 */
	public static final String CONTENT_DISPOSITION_DOWNLOAD_CERTIFICATE = "Content-disposition";
	
	/**
	 * Attribute that represents the content disposition to download certificate. 
	 */
	public static final String CONTENT_TYPE_DOWNLOAD_CERTIFICATE = "application/x-x509-ca-cert";

	/**
	 * Constructor method for the class UtilsCertificate.java.
	 */
	private UtilsCertificate() {
		super();
	}

	/**
	 * Creates a X509Certificate given its content.
	 * 
	 * @param certificate Certificate content.
	 * @return X509Certificate jce X509Certificate.
	 * @throws CommonUtilsException Exception thrown if there is any problem creating the certificate.
	 */
	public static X509Certificate getCertificate(byte[ ] certificate) throws CertificateException {
		InputStream is = new ByteArrayInputStream(certificate);

		return (X509Certificate) CertificateFactory.getInstance(CERTIFICATE_TYPE).generateCertificate(is);

	}

	/**
	 * Creates a certificate alias used in PSC or Certificate Type Keystores. Certificate alias will be the certificate hash in hex String(in this case uses {@link #DEFAULT_HASH_ALGORITHM} to get it).
	 * The hex string must be formed as defined in {@link UtilsStringChar#convertByteArrayToHex(byte[])}.
	 * 
	 * @param cert Certificate to calculate its alias.
	 * @param hashAlgorithm Algorithm uses to calculate certificate hash. If it´s null, method uses {@link #DEFAULT_HASH_ALGORITHM} algorithm.
	 * @return The certificate Alias.
	 * @throws CommonUtilsException Exception thrown if there is any problem calculating its alias.
	 */
	public static String createCertificateAlias(X509Certificate cert, String hashAlgorithm) throws NoSuchAlgorithmException {
		if (cert == null) {
			return null;
		}
		String ha = hashAlgorithm == null ? DEFAULT_HASH_ALGORITHM : hashAlgorithm;
		byte[ ] hash = null;

		byte[ ] issuerBytes = cert.getIssuerX500Principal().getEncoded();
		byte[ ] serialNumberBytes = cert.getSerialNumber().toByteArray();
		byte[ ] issuerAndSerialNumberBytes = new byte[issuerBytes.length + serialNumberBytes.length];
		System.arraycopy(issuerBytes, 0, issuerAndSerialNumberBytes, 0, issuerBytes.length);
		System.arraycopy(serialNumberBytes, 0, issuerAndSerialNumberBytes, issuerBytes.length, serialNumberBytes.length);
		hash = MessageDigest.getInstance(ha).digest(issuerAndSerialNumberBytes);
		return UtilsStringChar.convertByteArrayToHex(hash, true);

	}

	/**
	 * Method that canonicalizes the identifier of a certificate.
	 * @param idCertificado Parameter that represents the identifier of a certificate.
	 * @return the canonicalized identifier of the certificate.
	 */
	public static String canonicalizarIdCertificado(String idCertificado) {
		if (idCertificado.indexOf(EQUALS_CHAR) != -1) {
			String[ ] campos = idCertificado.split(COMA_SEPARATOR);
			Set<String> ordenados = new TreeSet<String>();
			StringBuffer sb = new StringBuffer();
			String[ ] pair;
			int i = 0;
			while (i < campos.length) {
				/*Puede darse el caso de que haya campos que incluyan comas, ejemplo:
				 *[OU=Class 3 Public Primary Certification Authority, O=VeriSign\\,  Inc., C=US]
				 */
				int currentIndex = i;
				// Lo primero es ver si estamos en el campo final y si el
				// siguiente campo no posee el símbolo igual, lo
				// concatenamos al actual
				while (i < campos.length - 1 && !campos[i + 1].contains(EQUALS_CHAR)) {
					campos[currentIndex] += COMA_SEPARATOR + campos[i + 1];
					i++;
				}
				sb = new StringBuffer();
				pair = campos[currentIndex].trim().split(EQUALS_CHAR);
				sb.append(pair[0].toLowerCase());
				sb.append(EQUALS_CHAR);
				if (pair.length == 2) {
					sb.append(pair[1]);
				}
				ordenados.add(sb.toString());
				i++;
			}
			Iterator<String> it = ordenados.iterator();
			sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(it.next());
				sb.append(COMA_SEPARATOR);
			}
			return sb.substring(0, sb.length() - 1);
		} else {
			// No es un identificador de certificado, no se canonicaliza.
			return idCertificado;
		}
	}

	/**
	 * Gets certificate´s identifier (canonicalized subject).
	 * 
	 * @param cert Certificate to get the identifier.
	 * @return Certificate identifier.
	 * @throws CommonUtilsException Exception thrown if there is any problem creating the certificate.
	 */
	public static String getCertificateId(X509Certificate cert) throws Exception {
		if (cert == null) {
			return null;
		}
		String id = ASN1Utilities.toString(cert.getSubjectX500Principal());
		return canonicalizarIdCertificado(id);
	}

	/**
	 * Method that obtains the canonicalized identifier of the issuer of a certificate.
	 * @param cert Parameter that represents the certificate.
	 * @return the canonicalized identifier of the issuer of the certificate.
	 * @throws CommonUtilsException If the method fails.
	 */
	public static String getCertificateIssuerId(X509Certificate cert) throws Exception {
		if (cert == null) {
			return null;
		}
		return canonicalizarIdCertificado(ASN1Utilities.toString(cert.getIssuerX500Principal()));
	}

	/**
	 * Method that obtains the serial number of a certificate.
	 * @param cert Parameter that represents the certificate.
	 * @return the serial number of the certificate.
	 * @throws CommonUtilsException If the method fails.
	 */
	public static BigInteger getCertificateSerialNumber(X509Certificate cert) throws Exception {
		if (cert == null) {
			return null;
		}
		return cert.getSerialNumber();
	}

	/**
	 * Method that indicates whether some other certificate is "equal to" this one (true) or not (false).
	 * @param cert1 Parameter that represents the first certificate to compare.
	 * @param cert2 Parameter that represents the second certificate to compare.
	 * @return a boolean that indicates whether some other certificate is "equal to" this one (true) or not (false).
	 * @throws CommonUtilsException If the method fails.
	 */
	public static boolean equals(X509Certificate cert1, X509Certificate cert2) throws Exception {
		boolean res = false;

		if (cert1 != null && cert2 != null) {
			if (cert1.getPublicKey().equals(cert2.getPublicKey())) {
				String idEmisor1 = getCertificateIssuerId(cert1);
				String idEmisor2 = getCertificateIssuerId(cert2);
				if (idEmisor1 != null && idEmisor2 != null && idEmisor1.equalsIgnoreCase(idEmisor2)) {
					if (cert1.getSerialNumber() != null && cert2.getSerialNumber() != null && cert1.getSerialNumber().compareTo(cert2.getSerialNumber()) == 0) {
						res = true;
					} else {
						res = false;
					}
				}
			} else {
				res = false;
			}
		}
		return res;
	}

	/**
	 * Method that procress the response of the plataform.
	 *
	 * @param response
	 *            Respuesta XML de la plataforma
	 * @return long statusCertificate
	 */
	public static Long processStatusCertificate(String response) throws Exception {

		Document responseDoc = null;
		Long statusCertificateId = StatusCertificateEnum.NOTVALID.getId();

		try {
			responseDoc = db.parse(new ByteArrayInputStream(response.getBytes(UtilsCertificate.CODIFICACION_UTF_8)));
		} catch (Exception e) {
			LOGGER.error("Se ha producido un error obteniendo el estado de la respuesta");
			throw (e);
		}
		NodeList resultMajorNode = null;
		resultMajorNode = responseDoc.getElementsByTagName("dss:ResultMajor");
		String resultMajor = resultMajorNode.item(0).getFirstChild().getNodeValue();

		NodeList resultMinorNode = null;
		resultMinorNode = responseDoc.getElementsByTagName("dss:ResultMinor");
		String resultMinor = resultMinorNode.item(0).getFirstChild().getNodeValue();

		if (resultMajor.contains("Success")) {
			if (resultMinor.contains("Definitive") || resultMinor.contains("Temporal")) {
				statusCertificateId = StatusCertificateEnum.VALID.getId();
			}
			if (resultMinor.contains("Expired")) {
				statusCertificateId = StatusCertificateEnum.CADUCATE.getId();
			}
			if (resultMinor.contains("Revoked") || resultMinor.contains("OnHold")) {
				statusCertificateId = StatusCertificateEnum.REVOCATE.getId();
			}
			if (resultMinor.contains("PathValidationFails")) {
				statusCertificateId = StatusCertificateEnum.UNKNOWN.getId();
			}
			if (resultMinor.contains("NotYetValid")) {
				statusCertificateId = StatusCertificateEnum.NOTVALIDYET.getId();
			}
		}

		return statusCertificateId;
	}

}
