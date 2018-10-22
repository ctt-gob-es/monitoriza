/* 
/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the  terms
 * of the European Public License (EUPL), either version 1.1 or (at your option)
 * any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * more details.
 * You should have received a copy of the EUPL1.1 license
 * along with this program; if not, you may find it at
 * http:joinup.ec.europa.eu/software/page/eupl/licence-eupl
 ******************************************************************************/

/** 
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail.java.</p>
 * <b>Description:</b><p>Class that maps the <i>CONF_SERVER_MAIL</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old
 * Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16/10/2018.
 */
@Entity
@Table(name = "CONF_SERVER_MAIL")
public class ConfServerMail {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 9090589742672624291L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idConfServerMail;

	/**
	 * Attribute that represents the issuer.
	 */
	private String issuerMail;

	/**
	 * Attribute that represents the host.
	 */
	private String hostMail;

	/**
	 * Attribute that represents the port.
	 */
	private Long portMail;

	/**
	 * Attribute that represents the tsl.
	 */
	private Boolean tslMail;

	/**
	 * Attribute that represents the authentication.
	 */
	private Boolean authenticationMail;

	/**
	 * Attribute that represents the user.
	 */
	private String userMail;

	/**
	 * Attribute that represents the password.
	 */
	private String passwordMail;

	/**
	 * Gets the value of the attribute {@link #idConfServerMail}.
	 * 
	 * @return the value of the attribute {@link #idConfServerMail}.
	 */
	@Id
	@Column(name = "ID_CONF_SERVER_MAIL", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_conf_server_mail")
	@GenericGenerator(name = "sq_conf_server_mail", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQ_CONF_SERVER_MAIL"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdConfServerMail() {
		return idConfServerMail;
	}

	/**
	 * Sets the value of the attribute {@link #idConfServerMail}.
	 * 
	 * @param idConfServerMail
	 *            The value for the attribute {@link #idConfServerMail}.
	 */
	public void setIdConfServerMail(Long idConfServerMail) {
		this.idConfServerMail = idConfServerMail;
	}

	/**
	 * Gets the value of the attribute {@link #issuer}.
	 * 
	 * @return the value of the attribute {@link #issuer}.
	 */
	@Column(name = "ISSUER_MAIL", nullable = false, precision = NumberConstants.NUM200)
	public String getIssuerMail() {
		return issuerMail;
	}

	/**
	 * Sets the value of the attribute {@link #issuer}.
	 * 
	 * @param issuer
	 *            The value for the attribute {@link #issuer}.
	 */
	public void setIssuerMail(String issuerMail) {
		this.issuerMail = issuerMail;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * 
	 * @return the value of the attribute {@link #host}.
	 */
	@Column(name = "HOST_MAIL", nullable = false, precision = NumberConstants.NUM200)
	public String getHostMail() {
		return hostMail;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * 
	 * @param host
	 *            The value for the attribute {@link #host}.
	 */
	public void setHostMail(String hostMail) {
		this.hostMail = hostMail;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * 
	 * @return the value of the attribute {@link #port}.
	 */
	@Column(name = "PORT_MAIL", nullable = false, precision = NumberConstants.NUM10)
	public Long getPortMail() {
		return portMail;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * 
	 * @param port
	 *            The value for the attribute {@link #port}.
	 */
	public void setPortMail(Long portMail) {
		this.portMail = portMail;
	}

	/**
	 * Gets the value of the attribute {@link #user}.
	 * 
	 * @return the value of the attribute {@link #user}.
	 */
	@Column(name = "USER_MAIL", nullable = true, precision = NumberConstants.NUM200)
	public String getUserMail() {
		return userMail;
	}

	/**
	 * Sets the value of the attribute {@link #user}.
	 * 
	 * @param user
	 *            The value for the attribute {@link #user}.
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * 
	 * @return the value of the attribute {@link #password}.
	 */
	@Column(name = "PASSWORD_MAIL", nullable = true, precision = NumberConstants.NUM200)
	public String getPasswordMail() {
		return passwordMail;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * 
	 * @param password
	 *            The value for the attribute {@link #password}.
	 */
	public void setPasswordMail(String passwordMail) {
		this.passwordMail = passwordMail;
	}

	/**
	 * @return the tsl
	 */
	@Column(name = "TSL", nullable = false, precision = NumberConstants.NUM1)
	public Boolean getTslMail() {
		return tslMail;
	}

	/**
	 * @param tsl
	 *            the tsl to set
	 */
	public void setTslMail(Boolean tslMail) {
		this.tslMail = tslMail;
	}

	/**
	 * @return the authentication
	 */
	@Column(name = "AUTHENTICATION", nullable = false, precision = NumberConstants.NUM1)
	public Boolean getAuthenticationMail() {
		return authenticationMail;
	}

	/**
	 * @param authentication
	 *            the authentication to set
	 */
	public void setAuthenticationMail(Boolean authenticationMail) {
		this.authenticationMail = authenticationMail;
	}

}
