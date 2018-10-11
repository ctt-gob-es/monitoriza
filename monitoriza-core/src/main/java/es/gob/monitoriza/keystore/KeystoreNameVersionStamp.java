/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2016 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.ws.keystores.KeystoreNameVersionStamp.java.</p>
 * <b>Description:</b><p>Class that represents the information that define
 * the name of a keystore, the version of this keystore and the timestamp when
 * is created or modify this object for the last time.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>28/06/2012.</p>
 * @author Gobierno de España.
 * @version 1.1, 15/09/2016.
 */
package es.gob.monitoriza.keystore;

import java.util.Calendar;

/**
 * <p>Class that represents the information that define
 * the name of a keystore, the version of this keystore and the timestamp when
 * is created or modify this object for the last time.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.1, 15/09/2016.
 */
public class KeystoreNameVersionStamp {

	/**
	 * Attribute that represents the timestamp prefix.
	 */
	private String prefix = null;

	/**
	 * Attribute that represents the name of a keystore.
	 */
	private String keystoreName = null;

	/**
	 * Attribute that represents the actual keystore version.
	 */
	private Long version = null;

	/**
	 * Attribute that represents the time when this object has been
	 * created, or the last time that was modified.
	 */
	private long timestampInMillis = Calendar.getInstance().getTimeInMillis();

	/**
	 * Constructor method for the class KeystoreNameVersion.java.
	 */
	private KeystoreNameVersionStamp() {
		super();
		timestampInMillis = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * Constructor method for the class KeystoreNameVersion.java.
	 * @param prefixTimestamp Prefix added to the keystore name.
	 * @param name Name to assign to the keystore.
	 * @param vers Version for the keystore.
	 */
	public KeystoreNameVersionStamp(String prefixTimestamp, String name, Long vers) {
		this();
		this.prefix = prefixTimestamp;
		this.keystoreName = name;
		this.version = vers;
	}

	/**
	 * Gets the value of the attribute {@link #prefix}.
	 * @return the value of the attribute {@link #prefix}.
	 */
	public final String getPrefix() {
		return prefix;
	}

	/**
	 * Gets the value of the attribute {@link #keystoreName}.
	 * @return the value of the attribute {@link #keystoreName}.
	 */
	public final String getKeystoreName() {
		return keystoreName;
	}

	/**
	 * Gets the value of the attribute {@link #version}.
	 * @return the value of the attribute {@link #version}.
	 */
	public final Long getVersion() {
		return version;
	}

	/**
	 * Gets the value of the attribute {@link #timestampInMillis}.
	 * @return the value of the attribute {@link #timestampInMillis}.
	 */
	public final long getTimestampInMillis() {
		return timestampInMillis;
	}

}
