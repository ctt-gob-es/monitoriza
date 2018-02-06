/* 
/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/** 
 * <b>File:</b><p>es.gob.monitoriza.mailService.mailService.java.</p>
 * <b>Description:</b><p> Class that manages the mail service.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>23/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 23/01/2018.
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
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.util.MailUtils;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that manages the mail service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 23/01/2018.
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
	private  String subject;

	/**
	 * Body of the email.
	 */
	private String bodyMsg;

	/**
	 * Gets the value of the attribute {@link #addressees}.
	 * @return the value of the attribute {@link #addressees}.
	 */
	public List<String> getAddressees() {
		return addressees;
	}

	/**
	 * Sets the value of the attribute {@link #addressees}.
	 * @param addresseesParam The value for the attribute {@link #addressees}.
	 */
	public void setAddressees(List<String> addresseesParam) {
		this.addressees = addresseesParam;
	}

	/**
	 * Gets the value of the attribute {@link #issuer}.
	 * @return the value of the attribute {@link #issuer}.
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * Sets the value of the attribute {@link #issuer}.
	 * @param issuerParam The value for the attribute {@link #issuer}.
	 */
	public void setIssuer(String issuerParam) {
		this.issuer = issuerParam;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param hostParam The value for the attribute {@link #host}.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param portParam The value for the attribute {@link #port}.
	 */
	public void setPort(int portParam) {
		this.port = portParam;
	}

	/**
	 * Gets the value of the attribute {@link #authentication}.
	 * @return the value of the attribute {@link #authentication}.
	 */
	public boolean isAuthentication() {
		return authentication;
	}

	/**
	 * Sets the value of the attribute {@link #authentication}.
	 * @param authenticationParam The value for the attribute {@link #authentication}.
	 */
	public void setAuthentication(boolean authenticationParam) {
		this.authentication = authenticationParam;
	}

	/**
	 * Gets the value of the attribute {@link #user}.
	 * @return the value of the attribute {@link #user}.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the value of the attribute {@link #user}.
	 * @param userParam The value for the attribute {@link #user}.
	 */
	public void setUser(String userParam) {
		this.user = userParam;
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * @return the value of the attribute {@link #password}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * @param passwordParam The value for the attribute {@link #password}.
	 */
	public void setPassword(String passwordParam) {
		this.password = passwordParam;
	}

	/**
	 * Gets the value of the attribute {@link #subject}.
	 * @return the value of the attribute {@link #subject}.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the value of the attribute {@link #subject}.
	 * @param subjectParam The value for the attribute {@link #subject}.
	 */
	public void setSubject(String subjectParam) {
		this.subject = subjectParam;
	}

	/**
	 * Gets the value of the attribute {@link #bodyMsg}.
	 * @return the value of the attribute {@link #bodyMsg}.
	 */
	public String getBodyMsg() {
		return bodyMsg;
	}

	/**
	 * Sets the value of the attribute {@link #bodyMsg}.
	 * @param bodyMsgParam The value for the attribute {@link #bodyMsg}.
	 */
	public void setBodyMsg(String bodyMsgParam) {
		this.bodyMsg = bodyMsgParam;
	}

	/**
	 * Constructor method for the class MailService.java. 
	 */
	public MailService() {
		loadStaticParam();
	}

	/**
	 * Constructor method for the class MailService.java.
	 * @param addresseesParam Destination addressees of the email
	 * @param issuerParam Issuer of the email.
	 * @param hostParam Destination host where sending the email.
	 * @param portParam Port number where sending the email.
	 * @param authenticationParam It indicates if the email need authentication or not.
	 * @param userParam User for the authentication.
	 * @param passwordParam Password for the authentication.
	 * @param subjectParam Subject of the email.
	 * @param bodyMsgParam Body of the email.
	 */
	public MailService(List<String> addresseesParam, String issuerParam, String hostParam, int portParam, boolean authenticationParam, String userParam, String passwordParam, String subjectParam, String bodyMsgParam) {
		setAddressees(addresseesParam);
		setIssuer(issuerParam);
		setHost(hostParam);
		setPort(portParam);
		setAuthentication(authenticationParam);
		setUser(userParam);
		setPassword(passwordParam);
		setSubject(subjectParam);
		setBodyMsg(bodyMsgParam);
	}

	/**
	 * Method that load from the static properties file the properties necessaries for the mail service.
	 */
	private void loadStaticParam() {
		List<String> addresees = MailUtils.getListAddressees();
		String issuer = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_ISSUER);
		String host = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_HOST);
		String portString = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_PORT);
		int port = Integer.valueOf(portString);
		String authString = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_AUTHENTICATION);
		boolean authentication = Boolean.valueOf(authString);
		String user = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_USER);
		String password = StaticMonitorizaProperties.getProperty(GeneralConstants.MAIL_ATTRIBUTE_PASSWORD);
		new MailService(addresees, issuer, host, port, authentication, user, password, null, null);
	}

	/**
	 * Method that sends a mail with the parameters configured in the attributes of the class.
	 * @return True if there was possible to send the mail and false if not.
	 */
	public boolean send() {
		Properties props = new Properties();
		props.put(GeneralConstants.MAIL_ATTRIBUTE_HOST, getHost());
		props.put(GeneralConstants.MAIL_ATTRIBUTE_PORT, getPort());
		props.put(GeneralConstants.MAIL_ATTRIBUTE_AUTHENTICATION, isAuthentication());
		Session session = Session.getInstance(props);
		try {
			// Creamos el mensaje
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(getIssuer()));

			// Modificamos los destinatarios
			InternetAddress[ ] addrs = new InternetAddress[getAddressees().size()];
			List<String> listAddr = getAddressees();
			for(int i=0; i< listAddr.size(); i++){
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
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_SENDING_MAIL),  e.getCause());
			System.out.println("Correo enviando - Fail");
			return false;
		}
		System.out.println("Correo enviando - OK");
		return true;
	}

}
