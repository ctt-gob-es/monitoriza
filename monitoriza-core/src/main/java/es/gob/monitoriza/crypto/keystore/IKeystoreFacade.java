/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2009-2012 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.cryptography.keystore.IKeystoreFacade.java.</p>
 * <b>Description:</b><p>Interface that defines the methods to manage operations with keystores.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>06/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.0, 06/06/2012.
 */
package es.gob.monitoriza.crypto.keystore;

import java.security.Key;
import java.security.cert.Certificate;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;

/**
 * <p>Interface that defines the methods to manage operations with keystores.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 06/06/2012.
 */
public interface IKeystoreFacade {

	/**
	 * Method that stores a new entry into a keystore.
	 * @param alias Parameter that represents the alias of the entry.
	 * @param certificate Parameter that represents the certificate to store.
	 * @param key Parameter that represents the key to store.
	 * @return the updated keystore cache object representation.
	 * @throws CryptographyException If the method fails.
	 */
	Keystore storeCertificate(String alias, Certificate certificate, Key key) throws CryptographyException;
	
	/**
	 * Method that updates an entry into a keystore.
	 * @param oldEntryAlias Parameter that represents the old alias of the entry.
	 * @param newEntryAlias Parameter that represents the new alias of the entry.
	 * @return the updated keystore cache object representation.
	 * @throws CryptographyException If the method fails.
	 */
	Keystore updateCertificate(String oldEntryAlias, String newEntryAlias) throws CryptographyException;
	
	/**
	 * Method that deletes a certificate entry from a keystore
	 * @param alias Parameter that represents the alias of the entry.
	 * @return the updated keystore cache object representation.
	 * @throws CryptographyException
	 */
	Keystore deleteCertificate(String alias) throws CryptographyException;
	
	
}
