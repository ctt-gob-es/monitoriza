/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://administracionelectronica.gob.es
 *
 * Copyright 2005-2019 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.eventmanager.notifier.email.EMailTimeLimitedOperation.java.</p>
 * <b>Description:</b><p>Class that represents an e-mail sending-operation. In this one all the information
 * is specified to define the e-mail and the necessary functionality is contributed to realize the sending
 * as an independent thread via SMTP server. This thread will be time limited.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.notifier.email;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gob.eventmanager.constant.NumberConstants;
import es.gob.eventmanager.exception.CipherException;
import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.notifier.thread.ATimeLimitedOperation;
import es.gob.eventmanager.persistence.model.entity.ConfServerMail;
import es.gob.eventmanager.utils.AESCipher;
import es.gob.eventmanager.utils.UtilsStringChar;
import es.gob.eventmanager.utils.UtilsValidation;


/**
 * <p>Class that represents an e-mail sending-operation. In this one all the information
 * is specified to define the e-mail and the necessary functionality is contributed to realize the sending
 * as an independent thread via SMTP server. This thread will be time limited.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
public class EMailTimeLimitedOperation extends ATimeLimitedOperation {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger("EVENTMANAGER-SERVICE");

	/**
	 * Constant attribute that represents the email transport 'smtp'.
	 */
	private static final String TRANSPORT_SMTP = "smtp";

	/**
	 * Constant attribute that represents a string separator.
	 */
	private static final String SEPARATOR = "------";
	
	/**
	 * Attribute that represents a divider of line.
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * Attribute that represents mail servers pojo list.
	 */
	private ConfServerMail confServerMail;

	/**
	 * Attribute that represents the list of addressees to the e-mail.
	 */
	private List<String> listAddresses = new ArrayList<String>();

	/**
	 * Attribute that represents the subject of the e-mail.
	 */
	private String subject = null;

	/**
	 * Attribute that represents the message of the e-mail.
	 */
	private StringBuilder messageBuilder = null;

	/**
	 * This constructor method sets an email to be sended through the ministry messaging platform.
	 * @param listAddresseesParam Parameter that represents the list of addressees to the e-mail.
	 * @param subjectParam Parameter that represents the subject of the e-mail.
	 * @param message Parameter that represents the message of the e-mail.
	 * @throws AlarmsException If some of the input parameters is null.
	 */
	public EMailTimeLimitedOperation(List<String> listAddresseesParam, String subjectParam, String message) throws EventManagerException {
		super();
		// Comprobamos que haya destinatarios a los que enviar el e-mail
		if (!UtilsValidation.isValid(listAddresseesParam)) {
			throw new EventManagerException("Error creando el correo electrónico: No se ha indicado ningún destinatario");
		}
		// Comprobamos que se haya indicado un asunto
		if (subjectParam == null) {
			throw new EventManagerException("Error creando el correo electrónico: No se ha indicado el asunto");
		}
		// Comprobamos que se haya indicado un mensaje
		if (message == null) {
			throw new EventManagerException("Error creando el correo electrónico: No se ha indicado el cuerpo del mensaje");
		}
		listAddresses = listAddresseesParam;
		subject = subjectParam;
		messageBuilder = new StringBuilder(message);
		setMaxTimeForRunningThread(NumberConstants.NUM10000);
	}


	/**
	 * This constructor method sets an email to be sended through internal mail server.
	 * @param listSmtpServerPOJO list of parameters that represents the data of the SMTP server.
	 * @param listAddresseesParam Parameter that represents the list of addressees to the e-mail.
	 * @param subjectParam Parameter that represents the subject of the e-mail.
	 * @param message Parameter that represents the message of the e-mail.
	 * @throws AlarmsException If some of the input parameters is null.
	 */
	public EMailTimeLimitedOperation(ConfServerMail confServerMail, List<String> listAddresseesParam, String subjectParam, String message) throws EventManagerException {
		this(listAddresseesParam, subjectParam, message);
		// Comprobamos que los datos del servidor SMTP esté completos
		if (confServerMail == null) {
			throw new EventManagerException("Error creando el correo electrónico: No se ha indicado correctamente el servidor SMTP");

		}
		this.confServerMail = confServerMail;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.afirma.utilidades.threads.ATimeLimitedOperation#doOperationThread()
	 */
	@Override
	protected final void doOperationThread() throws Exception {
			
		useInternalConfServerMail();	

	}

	/**
	 * Private method that implements the logic to send an email through the internal mail server.
	 */
	private void useInternalConfServerMail() {

		if (confServerMail != null) {
							
				// Se inicializan las propiedades junto con una sesión por
				// defecto
				Properties props = new Properties();
				props.put("mail.smtp.host", confServerMail.getHostMail());
				props.put("mail.smtp.port", confServerMail.getPortMail());
				props.put("mail.smtp.auth", confServerMail.getAuthenticationMail());
				props.put("mail.smtp.starttls.enable", confServerMail.getTslMail());

				Session session = Session.getInstance(props);

				try {
					// Se crea el mensaje
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(confServerMail.getIssuerMail()));

					// Se construye el array de addressees del mensaje
					List<String> listEmail = new ArrayList<String>();
					for (int i = 0; i < listAddresses.size(); i++) {
						if (listAddresses.get(i) != null) {
							listEmail.add(listAddresses.get(i));
						}
					}

					InternetAddress[ ] address = new InternetAddress[listEmail.size()];
					for (int i = 0; i < listEmail.size(); i++) {
						address[i] = new InternetAddress(listEmail.get(i));
					}

					if (address.length > 0) {
						msg.setRecipients(Message.RecipientType.TO, address);
						msg.setSubject(subject);

						msg.setSentDate(Calendar.getInstance().getTime());
						msg.setText(messageBuilder.toString());

						try {
							
							String user = null;
							String decPassword = null;
							// Si se requiere autenticación, establecemos el usuario y password
							// según los datos del servidor.
							if (confServerMail.getAuthenticationMail()) {
								user = confServerMail.getUserMail();
								if (UtilsStringChar.isNullOrEmpty(confServerMail.getPasswordMail())) {
									decPassword = UtilsStringChar.EMPTY_STRING;
								} else {
									decPassword = new String(AESCipher.getInstance().decryptMessage(confServerMail.getPasswordMail()));
								}
							}
							
							// Se intenta la conexión con el servidor de correo
							Transport transport = session.getTransport(TRANSPORT_SMTP);
							transport.connect(confServerMail.getHostMail(), user, decPassword);

							// Se efectúa el envío el mensaje
							transport.sendMessage(msg, msg.getAllRecipients());
							transport.close();
					
						} catch (CipherException e) {
							LOGGER.error("Error creando el correo electrónico: No se ha indicado correctamente el servidor SMTP");
						}
					}
				} catch (MessagingException e) {
					sendException(e);
				}
			
		}

	}

	/**
	 * Method that manages the send of the produced exception.
	 * @param mex Parameter that represents the exception to send.
	 */
	private void sendException(MessagingException mex) {

		LOGGER.error("Error enviando el correo electrónico");
		LOGGER.error(stack2string(mex));

		Exception ex = mex;
		do {
			if (ex instanceof SendFailedException) {
				SendFailedException sfex = (SendFailedException) ex;
				Address[ ] invalid = sfex.getInvalidAddresses();
				sendExceptionAux("\\    ** Invalid Email", invalid);

				Address[ ] validUnsent = sfex.getValidUnsentAddresses();
				sendExceptionAux("\\   ** ValidUnsent Email", validUnsent);

				Address[ ] validSent = sfex.getValidSentAddresses();
				sendExceptionAux("\\    ** ValidSent Email", validSent);
			}

			if (ex instanceof MessagingException) {
				ex = ((MessagingException) ex).getNextException();
			} else {
				ex = null;
			}
		}
		while (ex != null);

	}

	/**
	 * Method that shows in the log the result of the e-mail send for each addressee.
	 * @param errorMsg Parameter that represents the message for the error.
	 * @param arrayAddress Parameter that represents the list of addressees.
	 */
	private void sendExceptionAux(String errorMsg, Address[ ] arrayAddress) {
		LOGGER.error(errorMsg);
		if (arrayAddress != null) {
			for (int i = 0; i < arrayAddress.length; i++) {
				LOGGER.error("\\         {}", arrayAddress[i].toString());
			}
		}
	}

	/**
	 * Method that obtains the text from the stack trace of an exception.
	 * @param e Parameter that represents the exception to process.
	 * @return the text from the stack trace of the exception.
	 */
	private String stack2string(Exception e) {

		try (StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw); ) {
			e.printStackTrace(pw);
			return SEPARATOR + NEW_LINE + sw.toString() + SEPARATOR + NEW_LINE;
		} catch (Exception e2) {
			return "bad stack2string";
		} 
	}

	/**
	 * Method that appends a text at the end of the body of the e-mail message.
	 * @param text Parameter that represents the text to append.
	 */
	public final void appendToBodyMessage(String text) {
		messageBuilder.append(text);
	}

	/**
	 * Method that appends a text and a new line at the end of the body of the e-mail message.
	 * @param text String with text to append.
	 */
	public final void appendToBodyMessageWithNewLine(String text) {
		appendToBodyMessage(text);
		messageBuilder.append(UtilsStringChar.SPECIAL_LINE_BREAK_STRING);
	}

}
