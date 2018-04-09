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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>USER_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p>06/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 06/03/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>Class that maps the <i>USER_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 06/03/2018.
 */
@Entity
@Table(name = "USER_MONITORIZA")
public class UserMonitoriza implements Serializable {

	/**
	 * Constant attribute that represents the string <i>"yes_no"</i>.
	 */
	private static final String CONS_YES_NO = "yes_no";

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -60419018366799736L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idUserMonitoriza;

	/**
	 * Attribute that indicates whether the user has audit role (true) or not (false).
	 */
	private Boolean isBlocked;

	/**
	 * Attribute that represents the name for access to the platform.
	 */
	private String login;

	/**
	 * Attribute that represents the email of the user.
	 */
	private String email;

	/**
	 * Attribute that represents the user name.
	 */
	private String name;

	/**
	 * Attribute that represents the hash of the user password.
	 */
	private String password;

	/**
	 * Attribute that represents the surnames of the user.
	 */
	private String surnames;
	
	/**
	 * Attribute that represents the accumulated number of failed attempts accessing the platform since the last time which the
	 * user acceded correctly.
	 */
	private Integer attemptsNumber;

	/**
	 * Attribute that represents the date with the last correctly access to the platform by the user.
	 */
	private Date lastAccess;

	/**
	 * Attribute that represents the IP address used by the user to access to the platform.
	 */
	private String lastIpAccess;

	/**
	 * Gets the value of the attribute {@link #idUserMonitoriza}.
	 * @return the value of the attribute {@link #idUserMonitoriza}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Id
	@Column(name = "ID_USER_MONITORIZA", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator="sq_user_monitoriza")
	@SequenceGenerator(name="sq_user_monitoriza",sequenceName="SQ_USER_MONITORIZA", allocationSize=1)
	@JsonView(DataTablesOutput.View.class)
	public Long getIdUserMonitoriza() {
		// CHECKSTYLE:ON
		return idUserMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #idUserMonitoriza}.
	 * @param idUserMonitorizaParam The value for the attribute {@link #idUserMonitoriza}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIdUserMonitoriza(Long idUserMonitorizaParam) {
		// CHECKSTYLE:ON
		this.idUserMonitoriza = idUserMonitorizaParam;
	}

	/**
	 * Gets the value of the attribute {@link #isBlocked}.
	 * @return the value of the attribute {@link #isBlocked}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IS_BLOCKED", nullable = false, precision = 1)
	@Type(type = CONS_YES_NO)
	public Boolean getIsBlocked() {
		// CHECKSTYLE:ON
		return isBlocked;
	}

	/**
	 * Sets the value of the attribute {@link #isBlocked}.
	 * @param isBlockedParam The value for the attribute {@link #isBlocked}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setIsBlocked(Boolean isBlockedParam) {
		// CHECKSTYLE:ON
		this.isBlocked = isBlockedParam;
	}	

	/**
	 * Gets the value of the attribute {@link #login}.
	 * @return the value of the attribute {@link #login}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "LOGIN", nullable = false, length = NumberConstants.NUM100, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the value of the attribute {@link #login}.
	 * @param loginParam The value for the attribute {@link #login}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLogin(String loginParam) {
		this.login = loginParam;
	}

	/**
	 * Gets the value of the attribute {@link #email}.
	 * @return the value of the attribute {@link #email}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "EMAIL", nullable = false, length = NumberConstants.NUM150)
	@JsonView(DataTablesOutput.View.class)
	public String getEmail() {
		// CHECKSTYLE:ON
		return email;
	}

	/**
	 * Sets the value of the attribute {@link #mail}.
	 * @param mailParam The value for the attribute {@link #mail}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setEmail(String mailParam) {
		// CHECKSTYLE:ON
		this.email = mailParam;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM100)
	@JsonView(DataTablesOutput.View.class)
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
	 * Gets the value of the attribute {@link #password}.
	 * @return the value of the attribute {@link #password}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "PASSWORD", nullable = false, length = NumberConstants.NUM512)
	@JsonView(DataTablesOutput.View.class)
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
	 * Gets the value of the attribute {@link #surnames}.
	 * @return the value of the attribute {@link #surnames}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "SURNAMES", nullable = false, length = NumberConstants.NUM150)
	@JsonView(DataTablesOutput.View.class)
	public String getSurnames() {
		// CHECKSTYLE:ON
		return surnames;
	}

	/**
	 * Sets the value of the attribute {@link #surnames}.
	 * @param surnamesParam The value for the attribute {@link #surnames}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setSurnames(String surnamesParam) {
		// CHECKSTYLE:ON
		this.surnames = surnamesParam;
	}

	/**
	 * Gets the value of the attribute {@link #attemptsNumber}.
	 * @return the value of the attribute {@link #attemptsNumber}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "ATTEMPTS_NUMBER", nullable = false)
	public Integer getAttemptsNumber() {
		// CHECKSTYLE:ON
		return attemptsNumber;
	}

	/**
	 * Sets the value of the attribute {@link #attemptsNumber}.
	 * @param attemptsNumber The value for the attribute {@link #attemptsNumber}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setAttemptsNumber(Integer attemptsNumber) {
		// CHECKSTYLE:ON
		this.attemptsNumber = attemptsNumber;
	}

	/**
	 * Gets the value of the attribute {@link #lastAccess}.
	 * @return the value of the attribute {@link #lastAccess}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "LAST_ACCESS")
	public Date getLastAccess() {
		// CHECKSTYLE:ON
		return lastAccess;
	}

	/**
	 * Sets the value of the attribute {@link #lastAccess}.
	 * @param lastAccess The value for the attribute {@link #lastAccess}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLastAccess(Date lastAccess) {
		// CHECKSTYLE:ON
		this.lastAccess = lastAccess;
	}

	/**
	 * Gets the value of the attribute {@link #lastIpAccess}.
	 * @return the value of the attribute {@link #lastIpAccess}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	@Column(name = "IP_LAST_ACCESS", length = NumberConstants.NUM15)
	public String getLastIpAccess() {
		// CHECKSTYLE:ON
		return lastIpAccess;
	}

	/**
	 * Sets the value of the attribute {@link #lastIpAccess}.
	 * @param lastIpAccess The value for the attribute {@link #lastIpAccess}.
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Hibernate JPA needs not final access methods.
	public void setLastIpAccess(String lastIpAccess) {
		// CHECKSTYLE:ON
		this.lastIpAccess = lastIpAccess;
	}

}