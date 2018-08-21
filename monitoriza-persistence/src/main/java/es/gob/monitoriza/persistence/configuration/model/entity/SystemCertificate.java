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
 * <b>File:</b><p>es.gob.afirma.persistence.configuration.model.POJO.SystemCertificatePOJO.java.</p>
 * <b>Description:</b><p>Class that represents the representation of the <i>SYSTEM_CERTIFICATE</i> database table as a Plain
 * Old Java Object.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>14/02/2012.</p>
 * @author Gobierno de España.
 * @version 1.0, 14/02/2012.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>Class that represents the representation of the <i>SYSTEM_CERTIFICATE</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.0, 14/02/2012.
 */
@Entity
@Table(name = "SYSTEM_CERTIFICATE")
public class SystemCertificate implements Serializable {

	/**
	 * Constant attribute that represents the string <i>"ID_SYSTEM_CERTIFICATE"</i>.
	 */
	private static final String CONS_ID_SYSTEM_CERTIFICATE = "ID_SYSTEM_CERTIFICATE";

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -8690329015595200542L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idSystemCertificate;

	/**
	 * Attribute that represents the keystore where this system certificate is stored.
	 */
	private Keystore keystore;

	/**
	 * Attribute that represents the alias of the certificate.
	 */
	private String alias;

	/**
	 * Attribute that indicates if the alias is about a key (true) or a certificate (false).
	 */
	private Boolean isKey;

	/**
	 * Attribute that represents the issuer of the certificate.
	 */
	private String issuer;

	/**
	 * Attribute that represents the subject of the certificate.
	 */
	private String subject;
	
	/**
	 * Attribute that represents the serial number of the certificate.
	 */
	private BigInteger serialNumber;
	
	/**
	 * Attribute that represents the vertificate status.
	 */
	private StatusCertificate statusCertificate;
	
	/**
	 * Attribute that represents the user monitoriza.
	 */
	private UserMonitoriza userMonitoriza;
	
	/**
	 * Gets the value of the attribute {@link #idSystemCertificate}.
	 * @return the value of the attribute {@link #idSystemCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = CONS_ID_SYSTEM_CERTIFICATE, unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_system_certificate")
	@GenericGenerator(
	                  name = "sq_system_certificate",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SYSTEM_CERTIFICATE"),
	                          @Parameter(name = "initial_value", value = "2"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdSystemCertificate() {
		// CHECKSTYLE:ON
		return idSystemCertificate;
	}

	/**
	 * Sets the value of the attribute {@link #idSystemCertificate}.
	 * @param idSystemCertificateParam The value for the attribute {@link #idSystemCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdSystemCertificate(Long idSystemCertificateParam) {
		// CHECKSTYLE:ON
		this.idSystemCertificate = idSystemCertificateParam;
	}

	/**
	 * Gets the value of the attribute {@link #alias}.
	 * @return the value of the attribute {@link #alias}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "ALIAS", nullable = false, length = NumberConstants.NUM4000)
	@JsonView(DataTablesOutput.View.class)
	public String getAlias() {
		// CHECKSTYLE:ON
		return alias;
	}

	/**
	 * Sets the value of the attribute {@link #alias}.
	 * @param aliasParam The value for the attribute {@link #alias}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAlias(String aliasParam) {
		// CHECKSTYLE:ON
		this.alias = aliasParam;
	}

	/**
	 * Gets the value of the attribute {@link #isKey}.
	 * @return the value of the attribute {@link #isKey}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_KEY", nullable = false, precision = 1)
	@Type(type = "yes_no")
	@JsonView(DataTablesOutput.View.class)
	public Boolean isKey() {
		// CHECKSTYLE:ON
		return isKey;
	}

	/**
	 * Sets the value of the attribute {@link #isKey}.
	 * @param isKey The value for the attribute {@link #isKey}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setKey(boolean isKey) {
		// CHECKSTYLE:ON
		this.isKey = isKey;
	}

	/**
	 * Gets the value of the attribute {@link #issuer}.
	 * @return the value of the attribute {@link #issuer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "ISSUER", length = NumberConstants.NUM4000, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getIssuer() {
		// CHECKSTYLE:ON
		return issuer;
	}

	/**
	 * Sets the value of the attribute {@link #issuer}.
	 * @param issuerParam The value for the attribute {@link #issuer}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIssuer(String issuerParam) {
		// CHECKSTYLE:ON
		this.issuer = issuerParam;
	}

	/**
	 * Gets the value of the attribute {@link #subject}.
	 * @return the value of the attribute {@link #subject}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SUBJECT", length = NumberConstants.NUM4000, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getSubject() {
		// CHECKSTYLE:ON
		return subject;
	}

	/**
	 * Sets the value of the attribute {@link #subject}.
	 * @param subjectParam The value for the attribute {@link #subject}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSubject(String subjectParam) {
		// CHECKSTYLE:ON
		this.subject = subjectParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #serialNumber}.
	 * @return the value of the attribute {@link #serialNumber}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SERIAL_NUMBER", length = NumberConstants.NUM100, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public BigInteger getSerialNumber() {
		// CHECKSTYLE:ON
		return serialNumber;
	}

	/**
	 * Sets the value of the attribute {@link #serialNumber}.
	 * @param serialNumberParam The value for the attribute {@link #serialNumber}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSerialNumber(BigInteger serialNumberParam) {
		// CHECKSTYLE:ON
		this.serialNumber = serialNumberParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #keystore}.
	 * @return the value of the attribute {@link #keystore}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_KEYSTORE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Keystore getKeystore() {
		// CHECKSTYLE:ON
		return keystore;
	}

	/**
	 * Sets the value of the attribute {@link #keystore}.
	 * @param keystoreParam The value for the attribute {@link #keystore}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setKeystore(Keystore keystoreParam) {
		// CHECKSTYLE:ON
		this.keystore = keystoreParam;
	}
	
	/**
	 * Gets the value of the attribute {@link #statusCertificate}.
	 * @return the value of the attribute {@link #statusCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_STATUS_CERTIFICATE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public StatusCertificate getStatusCertificate() {
		// CHECKSTYLE:ON
		return statusCertificate;
	}

	/**
	 * Sets the value of the attribute {@link #statusCertificate}.
	 * @param statusCertificateParam The value for the attribute {@link #statusCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setStatusCertificate(StatusCertificate statusCertificate) {
		// CHECKSTYLE:ON
		this.statusCertificate = statusCertificate;
	}
	
	/**
	 * Gets the value of the attribute {@link #userMonitoriza}.
	 * @return the value of the attribute {@link #userMonitoriza}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_USER_MONITORIZA", nullable = true)
	@JsonView(DataTablesOutput.View.class)
	public UserMonitoriza getUserMonitoriza() {
		// CHECKSTYLE:ON
		return userMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #userMonitoriza}.
	 * @param userMonitorizaParam The value for the attribute {@link #userMonitoriza}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setUserMonitoriza(UserMonitoriza userMonitoriza) {
		// CHECKSTYLE:ON
		this.userMonitoriza = userMonitoriza;
	}

}