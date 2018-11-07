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
 * <b>File:</b><p>es.gob.afirma.persistence.configuration.model.POJO.KeystorePOJO.java.</p>
 * <b>Description:</b><p>Class that represents the representation of the <i>KEYSTORE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>13/02/2012.</p>
 * @author Gobierno de España.
 * @version 1.0, 13/02/2012.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>Class that represents the representation of the <i>KEYSTORE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.0, 13/02/2012.
 */
@Cacheable
@Entity
@Table(name = "KEYSTORE")
public class Keystore implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 1210946181096500994L;

	/**
	 * Attribute that represents the identifier for the truststore ssl keystore in database. 
	 */
	public static final long ID_TRUSTSTORE_SSL = 1L;

	/**
	 * Attribute that represents the identifier for the client authentication for RFC3161 service keystore in database. 
	 */
	public static final long ID_AUTHCLIENT_RFC3161 = 2L;

	/**
	 * Attribute that represents the identifier for the client authentication for user service keystore in database. 
	 */
	public static final long ID_USER_STORE = 3L;

	/**
	 * Attribute that represents the identifier for the client authentication for valid service keystore in database. 
	 */
	public static final long ID_VALID_SERVICE_STORE = 4L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idKeystore;

	/**
	 * Attribute that represents the name of the keystore.
	 */
	private String name;

	/**
	 * Attribute that represents the name of the token with the description stored in properties file for internationalization.
	 */
	private String tokenName;

	/**
	 * Attribute that represents the list of system certificates stored in the keystore.
	 */
	private List<SystemCertificate> listSystemCertificates;

	/**
	 * Attribute that represents the keystore.
	 */
	private byte[ ] keystore;

	/**
	 * Attribute that represents the password of the keystore.
	 */
	private String password;

	/**
	 * Attribute that represents the type of the keystore.
	 */
	private String keystoreType;

	/**
	 * Attribute that represents the object version.
	 */
	private Long version;

	/**
	* Gets the value of the attribute {@link #idKeystore}.
	* @return the value of the attribute {@link #idKeystore}.
	*/
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_KEYSTORE", unique = true, nullable = false, precision = NumberConstants.NUM19)
	public Long getIdKeystore() {
		// CHECKSTYLE:ON
		return idKeystore;
	}

	/**
	 * Sets the value of the attribute {@link #idKeystore}.
	 * @param idKeystoreParam The value for the attribute {@link #idKeystore}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdKeystore(Long idKeystoreParam) {
		// CHECKSTYLE:ON
		this.idKeystore = idKeystoreParam;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM150, unique = true)
	public String getName() {
		// CHECKSTYLE:ON
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param nameParam The value for the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setName(String nameParam) {
		// CHECKSTYLE:ON
		this.name = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #listSystemCertificates}.
	 * @return the value of the attribute {@link #listSystemCertificates}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@OneToMany(mappedBy = "keystore", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	public List<SystemCertificate> getListSystemCertificates() {
		// CHECKSTYLE:ON
		return listSystemCertificates;
	}

	/**
	 * Sets the value of the attribute {@link #listSystemCertificates}.
	 * @param listSystemCertificatesParam The value for the attribute {@link #listSystemCertificates}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setListSystemCertificates(List<SystemCertificate> listSystemCertificatesParam) {
		// CHECKSTYLE:ON
		this.listSystemCertificates = listSystemCertificatesParam;
	}

	/**
	 * Gets the value of the attribute {@link #keystore}.
	 * @return the value of the attribute {@link #keystore}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Lob()
	@Column(name = "KEYSTORE")
	public byte[ ] getKeystore() {
		// CHECKSTYLE:ON
		return keystore;
	}

	/**
	 * Sets the value of the attribute {@link #keystore}.
	 * @param keystoreParam The value for the attribute {@link #keystore}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setKeystore(byte[ ] keystoreParam) {
		// CHECKSTYLE:ON
		if (keystoreParam != null) {
			this.keystore = keystoreParam.clone();
		}

	}

	/**
	 * Gets the value of the attribute {@link #tokenName}.
	 * @return the value of the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "TOKEN_NAME", nullable = false, length = NumberConstants.NUM30)
	public String getTokenName() {
		// CHECKSTYLE:ON
		return tokenName;
	}

	/**
	 * Sets the value of the attribute {@link #tokenName}.
	 * @param tokenNameParam The value for the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTokenName(String tokenNameParam) {
		// CHECKSTYLE:ON
		this.tokenName = tokenNameParam;
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * @return the value of the attribute {@link #password}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PASSWORD", nullable = false, length = NumberConstants.NUM255)
	public String getPassword() {
		// CHECKSTYLE:ON
		return password;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * @param passwordParam The value for the attribute {@link #password}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setPassword(String passwordParam) {
		// CHECKSTYLE:ON
		this.password = passwordParam;
	}

	/**
	 * Gets the value of the attribute {@link #keystoreType}.
	 * @return the value of the attribute {@link #keystoreType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "KEYSTORE_TYPE", nullable = false, length = NumberConstants.NUM50)
	public String getKeystoreType() {
		// CHECKSTYLE:ON
		return keystoreType;
	}

	/**
	 * Sets the value of the attribute {@link #keystoreType}.
	 * @param keystoreTypeParam The value for the attribute {@link #keystoreType}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setKeystoreType(String keystoreTypeParam) {
		// CHECKSTYLE:ON
		this.keystoreType = keystoreTypeParam;
	}
	
	/**
	* Gets the value of the attribute {@link #version}.
	* @return the value of the attribute {@link #version}.
	*/
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "VERSION", nullable = false, precision = NumberConstants.NUM19)
	public Long getVersion() {
		// CHECKSTYLE:ON
		return version;
	}

	/**
	 * Sets the value of the attribute {@link #version}.
	 * @param versionParam The value for the attribute {@link #version}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setVersion(Long versionParam) {
		// CHECKSTYLE:ON
		this.version = versionParam;
	}

}