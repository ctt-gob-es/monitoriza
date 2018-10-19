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
 * @version 1.1, 10/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>Class that represents the representation of the <i>C_STATUS_CERTIFICATES</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.1, 10/10/2018.
 */
@Entity
@Table(name = "C_STATUS_CERTIFICATES")
public class StatusCertificate implements Serializable {

	/**
	 * Constant attribute that represents the string <i>"ID_SYSTEM_CERTIFICATE"</i>.
	 */
	private static final String CONS_ID_STATUS_CERTIFICATE = "ID_STATUS_CERTIFICATE";

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -8690329015595200542L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idStatusCertificate;

	/**
	 * Attribute that represents the description of the certificate status.
	 */
	private String tokenName;
	
	/**
	 * Gets the value of the attribute {@link #idStatusCertificate}.
	 * @return the value of the attribute {@link #idStatusCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = CONS_ID_STATUS_CERTIFICATE, unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_c_status_certificates")
	@GenericGenerator(
	                  name = "sq_c_status_certificates",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_C_STATUS_CERTIFICATES"),
	                          @Parameter(name = "initial_value", value = "7"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdStatusCertificate() {
		// CHECKSTYLE:ON
		return idStatusCertificate;
	}

	/**
	 * Sets the value of the attribute {@link #idStatusCertificate}.
	 * @param idStatusCertificate The value for the attribute {@link #idStatusCertificate}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdStatusCertificate(final Long idStatusCertificate) {
		// CHECKSTYLE:ON
		this.idStatusCertificate = idStatusCertificate;
	}

	/**
	 * Gets the value of the attribute {@link #tokenName}.
	 * @return the value of the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "TOKEN_NAME", nullable = false, length = NumberConstants.NUM30)
	@JsonView(DataTablesOutput.View.class)
	public String getTokenName() {
		// CHECKSTYLE:ON
		return tokenName;
	}

	/**
	 * Sets the value of the attribute {@link #tokenName}.
	 * @param tokenName The value for the attribute {@link #tokenName}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setTokenName(final String tokenName) {
		// CHECKSTYLE:ON
		this.tokenName = tokenName;
	}
}