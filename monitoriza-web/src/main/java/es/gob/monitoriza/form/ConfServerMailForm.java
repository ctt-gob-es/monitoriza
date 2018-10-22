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
 * <b>File:</b><p>es.gob.monitoriza.form.ConfServerMailForm.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 16 oct. 2018.
 */
package es.gob.monitoriza.form;

/**
 * <p>
 * Class that represents the backing form for adding/editing a @firma platform.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.0, 16 oct. 2018.
 */
public class ConfServerMailForm {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in
	 * the form.
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
	 * @return the tslMail
	 */
	public Boolean getTslMail() {
		return tslMail;
	}

	/**
	 * @param tslMail
	 *            the tslMail to set
	 */
	public void setTslMail(Boolean tslMail) {
		this.tslMail = tslMail;
	}

	/**
	 * @return the authenticationMail
	 */
	public Boolean getAuthenticationMail() {
		return authenticationMail;
	}

	/**
	 * @param authenticationMail
	 *            the authenticationMail to set
	 */
	public void setAuthenticationMail(Boolean authenticationMail) {
		this.authenticationMail = authenticationMail;
	}

}
