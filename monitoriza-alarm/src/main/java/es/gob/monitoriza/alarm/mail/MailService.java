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
 * <b>File:</b><p>es.gob.monitoriza.alarm.mail.mailService.java.</p>
 * <b>Description:</b><p> Class that manages the mail service.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>23/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 17/10/2018.
 */
package es.gob.monitoriza.alarm.mail;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IAlarmLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.model.entity.ConfServerMail;
import es.gob.monitoriza.persistence.configuration.model.repository.ConfServerMailRepository;
import es.gob.monitoriza.utilidades.MailUtils;

/**
 * <p>
 * Class that manages the mail service.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 * 
 * @version 1.2, 17/10/2018.
 */
public class MailService {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Destination addressees of the email.
	 */
	private List<String> addressees;

	/**
	 * Issuer of the email.
	 */
	private String issuer;

	/**
	 * Destination host where sending the email.
	 */
	private String host;

	/**
	 * Port number where sending the email.
	 */
	private int port;

	/**
	 * It indicates if the email need authentication or not.
	 */
	private boolean authentication;

	/**
	 * Attribute that the communication with the mail server is ciphered
	 */
	private boolean tls;

	/**
	 * User for the authentication.
	 */
	private String user;

	/**
	 * Password for the authentication.
	 */
	private String password;

	/**
	 * Subject of the email.
	 */
	private String subject;

	/**
	 * Body of the email.
	 */
	private String bodyMsg;

	private ConfServerMailRepository confServerMailRepository;

	/**
	 * Gets the value of the attribute {@link #addressees}.
	 * 
	 * @return the value of the attribute {@link #addressees}.
	 */
	public List<String> getAddressees() {
		return addressees;
	}

	/**
	 * Sets the value of the attribute {@link #addressees}.
	 * 
	 * @param addresseesParam
	 *            The value for the attribute {@link #addressees}.
	 */
	public void setAddressees(List<String> addresseesParam) {
		this.addressees = addresseesParam;
	}

	/**
	 * Gets the value of the attribute {@link #issuer}.
	 * 
	 * @return the value of the attribute {@link #issuer}.
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * Sets the value of the attribute {@link #issuer}.
	 * 
	 * @param issuerParam
	 *            The value for the attribute {@link #issuer}.
	 */
	public void setIssuer(String issuerParam) {
		this.issuer = issuerParam;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * 
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * 
	 * @param hostParam
	 *            The value for the attribute {@link #host}.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * 
	 * @return the value of the attribute {@link #port}.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * 
	 * @param portParam
	 *            The value for the attribute {@link #port}.
	 */
	public void setPort(int portParam) {
		this.port = portParam;
	}

	/**
	 * Gets the value of the attribute {@link #authentication}.
	 * 
	 * @return the value of the attribute {@link #authentication}.
	 */
	public boolean isAuthentication() {
		return authentication;
	}

	/**
	 * Sets the value of the attribute {@link #authentication}.
	 * 
	 * @param authenticationParam
	 *            The value for the attribute {@link #authentication}.
	 */
	public void setAuthentication(boolean authenticationParam) {
		this.authentication = authenticationParam;
	}

	/**
	 * Gets the value of the attribute {@link #tls}.
	 * 
	 * @return the value of the attribute {@link #tls}.
	 */
	public boolean isTls() {
		return tls;
	}

	/**
	 * Sets the value of the attribute {@link #tls}.
	 * 
	 * @param authenticationParam
	 *            The value for the attribute {@link #tls}.
	 */
	public void setTls(boolean tls) {
		this.tls = tls;
	}

	/**
	 * Gets the value of the attribute {@link #user}.
	 * 
	 * @return the value of the attribute {@link #user}.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the value of the attribute {@link #user}.
	 * 
	 * @param userParam
	 *            The value for the attribute {@link #user}.
	 */
	public void setUser(String userParam) {
		this.user = userParam;
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * 
	 * @return the value of the attribute {@link #password}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * 
	 * @param passwordParam
	 *            The value for the attribute {@link #password}.
	 */
	public void setPassword(String passwordParam) {
		this.password = passwordParam;
	}

	/**
	 * Gets the value of the attribute {@link #subject}.
	 * 
	 * @return the value of the attribute {@link #subject}.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the value of the attribute {@link #subject}.
	 * 
	 * @param subjectParam
	 *            The value for the attribute {@link #subject}.
	 */
	public void setSubject(String subjectParam) {
		this.subject = subjectParam;
	}

	/**
	 * Gets the value of the attribute {@link #bodyMsg}.
	 * 
	 * @return the value of the attribute {@link #bodyMsg}.
	 */
	public String getBodyMsg() {
		return bodyMsg;
	}

	/**
	 * Sets the value of the attribute {@link #bodyMsg}.
	 * 
	 * @param bodyMsgParam
	 *            The value for the attribute {@link #bodyMsg}.
	 */
	public void setBodyMsg(String bodyMsgParam) {
		this.bodyMsg = bodyMsgParam;
	}

	/**
	 * Constructor method for the class MailService.java.
	 */
	public MailService() {
		// loadStaticParam();

		List<String> addresees = MailUtils.getListAddressees();

		List<ConfServerMail> listConfServerMail = confServerMailRepository.findAll();
		if (!listConfServerMail.isEmpty()) {
			ConfServerMail confServerMail = listConfServerMail.get(0);

			new MailService(addresees, confServerMail.getIssuerMail(), confServerMail.getHostMail(),
					Long.signum(confServerMail.getPortMail()), confServerMail.getAuthenticationMail().booleanValue(),
					confServerMail.getTslMail().booleanValue(), confServerMail.getUserMail(),
					confServerMail.getPasswordMail(), null, null);
		}
	}

	/**
	 * Constructor method for the class MailService.java.
	 * 
	 * @param addresseesParam
	 *            Destination addressees of the email
	 * @param issuerParam
	 *            Issuer of the email.
	 * @param hostParam
	 *            Destination host where sending the email.
	 * @param portParam
	 *            Port number where sending the email.
	 * @param authenticationParam
	 *            It indicates if the email need authentication or not.
	 * @param userParam
	 *            User for the authentication.
	 * @param passwordParam
	 *            Password for the authentication.
	 * @param subjectParam
	 *            Subject of the email.
	 * @param bodyMsgParam
	 *            Body of the email.
	 */
	public MailService(List<String> addresseesParam, String issuerParam, String hostParam, int portParam,
			boolean authenticationParam, boolean tlsParam, String userParam, String passwordParam, String subjectParam,
			String bodyMsgParam) {
		setAddressees(addresseesParam);
		setIssuer(issuerParam);
		setHost(hostParam);
		setPort(portParam);
		setAuthentication(authenticationParam);
		setTls(tlsParam);
		setUser(userParam);
		setPassword(passwordParam);
		setSubject(subjectParam);
		setBodyMsg(bodyMsgParam);
	}

	/**
	 * Method that load from the static properties file the properties necessaries
	 * for the mail service.
	 */
	// private void loadStaticParam() {
	//
	// String issuer =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_ISSUER);
	// String host =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_HOST);
	// String portString =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_PORT);
	// int port = Integer.valueOf(portString);
	// String authString =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_AUTHENTICATION);
	// String tlsString =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_TLS);
	// boolean authentication = Boolean.valueOf(authString);
	// boolean tls = Boolean.valueOf(tlsString);
	// String user =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_USER);
	// String password =
	// StaticMonitorizaProperties.getProperty(StaticConstants.MAIL_ATTRIBUTE_PASSWORD);
	// new MailService(addresees, issuer, host, port, authentication, tls, user,
	// password, null, null);
	// }

	/**
	 * Method that sends a mail with the parameters configured in the attributes of
	 * the class.
	 * 
	 * @return True if there was possible to send the mail and false if not.
	 */
	public boolean send() {
		Properties props = new Properties();
		props.put("mail.smtp.port", getPort());
		props.put("mail.smtp.starttls.enable", isTls());
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props);
		try {
			// Creamos el mensaje
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(getIssuer()));

			// Modificamos los destinatarios
			InternetAddress[] addrs = new InternetAddress[getAddressees().size()];
			List<String> listAddr = getAddressees();
			for (int i = 0; i < listAddr.size(); i++) {
				addrs[i] = new InternetAddress(listAddr.get(i));
			}
			msg.setRecipients(Message.RecipientType.TO, addrs);

			// Modificamos el asunto
			msg.setSubject(getSubject());

			// Actualizamos la fecha.
			msg.setSentDate(Calendar.getInstance().getTime());

			// Modificamos el cuerpo de mensaje.
			msg.setText(getBodyMsg());

			// Se intenta la conexión con el servidor de correo.
			Transport transport = session.getTransport(GeneralConstants.SMTP);
			transport.connect(getHost(), getUser(), getPassword());

			// Se efectúa el envío el mensaje
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			LOGGER.error(Language.getResAlarmMonitoriza(IAlarmLogMessages.ERRORALAMR003), e.getCause());
			return false;
		}

		return true;
	}

	/**
	 * Found the object confServerMailRepository
	 * @param confServerMailRepository 
	 */
	public void setConfMailRepository(ConfServerMailRepository confServerMailRepository) {
		this.confServerMailRepository = confServerMailRepository;
	}

}
