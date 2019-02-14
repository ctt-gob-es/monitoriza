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
 * <b>File:</b><p>es.gob.afirma.cryptography.keystore.AKeystoreFacade.java.</p>
 * <b>Description:</b><p>Class that manages the generation of the class which manages the keystores in the system.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>06/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.crypto.keystore;

import org.springframework.beans.factory.annotation.Autowired;

import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.utils.CryptographyValidationUtils;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.service.IKeystoreService;

/**
 * <p>Class that manages the generation of the class which manages the keystores in the system.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 25/01/2019.
 */
public final class KeystoreFactory {

	/**
	 * Attribute that represents an instance of the class which manages all the operations related with keystores in the system.
	 */
	private IKeystoreFacade keystoreInstance = null;
	
	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	@Autowired
	private IKeystoreService keystoreService; 

	/**
	 * Constructor method for the class KeystoreFactory.java.
	 * @param idKeystoreParam Parameter that represents the ID of the keystore in the database.
	 * @throws CryptographyException If the method fails.
	 */
	private KeystoreFactory(Long idKeystoreParam) throws CryptographyException {
		// Comprobamos que el ID del almacén de claves no es nulo
		if (idKeystoreParam == null) {
			throw new CryptographyException(Language.getResCoreMonitoriza("logKF001"));
		}

		// Obtenemos el almacén de claves
		KeystoreMonitoriza keystore = keystoreService.getKeystoreById(idKeystoreParam);
		// Comprobamos que el almacén de claves no es nulo
		CryptographyValidationUtils.checkIsNotNull(keystore, Language.getFormatResCoreMonitoriza("logKF003", new Object[ ] { idKeystoreParam }));

		keystoreInstance = new KeystoreFacade(keystore);
	
	}

	/**
	 * Method that obtains an instance of the class.
	 * @param idKeystoreParam Parameter that represents the ID of the keystore in the database.
	 * @return an instance of the class.
	 * @throws CryptographyException If the method fails.
	 */
	public static synchronized KeystoreFactory getInstance(Long idKeystoreParam) throws CryptographyException {
		return new KeystoreFactory(idKeystoreParam);
	}

	/**
	 * Method that obtains the concrete instance of {@link #keystoreInstance}.
	 * @return the concrete instance of {@link #keystoreInstance}.
	 */
	public IKeystoreFacade getKeystoreInstance() {
		return keystoreInstance;
	}


}
