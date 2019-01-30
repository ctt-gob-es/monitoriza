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
 * <b>File:</b><p>es.gob.monitoriza.handler.ClientHandler.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 30/01/2019.
 */
package es.gob.monitoriza.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.gob.monitoriza.crypto.utils.WSCryptoConfiguration;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 30/01/2019.
 */
public class ClientHandler extends AbstractCommonHandler {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -4719511031384163945L;

	/**
	 * Constant attribute that identifies UserNameToken authorization method.
	 */
	private static final String USERNAMEOPTION = WSConstants.USERNAME_TOKEN_LN;

	/**
	 * Constant attribute that identifies BinarySecurityToken authorization method.
	 */
	private static final String CERTIFICATEOPTION = WSConstants.BINARY_TOKEN_LN;

	/**
	 * Constant attribute that identifies none authorization method.
	 */
	private static final String NONEOPTION = "none";

	/**
	 * Attribute that indicates the current authorization method.
	 */
	private String securityOption = UtilsStringChar.EMPTY_STRING;
	
	/**
     * Attribute that represents the certificate that will be used for signing the SOAP request. 
     */
    private X509Certificate certificate = null;

    /**
     * Attribute that represents the key that will be used for signing the SOAP request. 
     */
    private PrivateKey key = null;

    /**
     * Gets the value of the attribute {@link #certificate}.
     * @return the value of the attribute {@link #certificate}.
     */
    public final X509Certificate getCertificate() {
	return certificate;
    }

    /**
     * Sets the value of the attribute {@link #certificate}.
     * @param certificateParam The value for the attribute {@link #certificate}.
     */
    public final void setCertificate(X509Certificate certificateParam) {
	this.certificate = certificateParam;
    }

    /**
     * Gets the value of the attribute {@link #key}.
     * @return the value of the attribute {@link #key}.
     */
    public final PrivateKey getKey() {
	return key;
    }

    /**
     * Sets the value of the attribute {@link #key}.
     * @param keyParam The value for the attribute {@link #key}.
     */
    public final void setKey(PrivateKey keyParam) {
	this.key = keyParam;
    }
    
	/**
     * Constructor method for the class ClientHandler.java.
     * @param securityOpt Parameter that represents the authorization method.
     * @throws WSServiceInvokerException If the method fails.
     */
    public ClientHandler(String securityOpt)  {
	

	if (securityOpt.equals(USERNAMEOPTION)) {
	    this.securityOption = USERNAMEOPTION;
	} else if (securityOpt.equals(CERTIFICATEOPTION)) {
	    this.securityOption = CERTIFICATEOPTION;
	} else if (securityOpt.equals(NONEOPTION)) {
	    this.securityOption = NONEOPTION;
	} 

    }

	/**
	 * {@inheritDoc}
	 * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
	 */
	public void invoke(MessageContext msgContext) throws AxisFault {
		SOAPMessage msg, secMsg;
		Document doc = null;

		secMsg = null;

		try {
			// Obtención del documento XML que representa la petición SOAP
			msg = msgContext.getCurrentMessage();
			doc = ((org.apache.axis.message.SOAPEnvelope) msg.getSOAPPart().getEnvelope()).getAsDocument();
			// Securización de la petición SOAP según la opcion de seguridad
			// configurada
			if (this.securityOption.equals(USERNAMEOPTION)) {
				secMsg = this.createUserNameToken(doc);
			} else if (this.securityOption.equals(CERTIFICATEOPTION)) {
				secMsg = this.createBinarySecurityToken(doc);
			}

			if (!this.securityOption.equals(NONEOPTION)) {
				// Modificación de la petición SOAP
				((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(secMsg.getSOAPPart().getEnvelope(), SOAPPart.FORM_SOAPENVELOPE);
			}
		} catch (Exception e) {
			throw AxisFault.makeFault(e);
		}
	}

	/**
	 * Method that creates a request secured by UserNameToken.
	 * @param soapEnvelopeRequest Parameter that represents the unsecured request.
	 * @return the secured request.
	 * @throws TransformerException If an unrecoverable error occurs during the course of the transformation.
	 * @throws IOException If there is a problem in reading data from the input stream.
	 * @throws SOAPException If the message is invalid.
	 * @throws WSSecurityException If the method fails.
	 */
	private SOAPMessage createUserNameToken(Document soapEnvelopeRequest) throws TransformerException, IOException, SOAPException, WSSecurityException {
		ByteArrayOutputStream baos;
		Document secSOAPReqDoc;
		DOMSource source;
		Element element;
		SOAPMessage res;
		StreamResult streamResult;
		String secSOAPReq;
		WSSecUsernameToken wsSecUsernameToken;
		WSSecHeader wsSecHeader;

		// Inserción del tag wsse:Security y userNameToken
		wsSecHeader = new WSSecHeader(null, false);
		wsSecUsernameToken = new WSSecUsernameToken();
		wsSecUsernameToken.setPasswordType(getPasswordType());
		wsSecUsernameToken.setUserInfo(getUserAlias(), getPassword());
		wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
		wsSecUsernameToken.prepare(soapEnvelopeRequest);
		// Añadimos una marca de tiempo inidicando la fecha de creación del tag
		wsSecUsernameToken.addCreated();
		wsSecUsernameToken.addNonce();
		// Modificación de la petición
		secSOAPReqDoc = wsSecUsernameToken.build(soapEnvelopeRequest, wsSecHeader);
		element = secSOAPReqDoc.getDocumentElement();

		// Transformación del elemento DOM a String
		source = new DOMSource(element);
		baos = new ByteArrayOutputStream();
		streamResult = new StreamResult(baos);
		TransformerFactory.newInstance().newTransformer().transform(source, streamResult);
		secSOAPReq = new String(baos.toByteArray());

		// Creación de un nuevo mensaje SOAP a partir del mensaje SOAP
		// securizado formado
		MessageFactory mf = new org.apache.axis.soap.MessageFactoryImpl();
		res = mf.createMessage(null, new ByteArrayInputStream(secSOAPReq.getBytes()));

		return res;
	}

	/**
	 * Method that creates a request secured by BinarySecurityToken.
	 * @param soapEnvelopeRequest Parameter that represents the unsecured request.
	 * @return the secured request.
	 * @throws TransformerException If an unrecoverable error occurs during the course of the transformation.
	 * @throws IOException If there is a problem in reading data from the input stream.
	 * @throws SOAPException May be thrown if the message is invalid.
	 * @throws WSSecurityException If the method fails.
	 */
	private SOAPMessage createBinarySecurityToken(Document soapEnvelopeRequest) throws TransformerException, IOException, SOAPException, WSSecurityException {
		ByteArrayOutputStream baos;
		Crypto crypto = null;
		Document secSOAPReqDoc;
		DOMSource source;
		Element element;
		StreamResult streamResult;
		String secSOAPReq;
		SOAPMessage res;
		WSSecSignature wsSecSignature = null;
		WSSecHeader wsSecHeader = null;

		
		// Inserción del tag wsse:Security y BinarySecurityToken
		wsSecHeader = new WSSecHeader(null, false);
		wsSecSignature = new WSSecSignature();
		crypto = new WSCryptoConfiguration(this.certificate, this.key);
		// Indicación para que inserte el tag BinarySecurityToken
		wsSecSignature.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
		wsSecSignature.setUserInfo(getUserAlias(), getPassword());
		wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
		wsSecSignature.prepare(soapEnvelopeRequest, crypto, wsSecHeader);

		// Modificación y firma de la petición
		secSOAPReqDoc = wsSecSignature.build(soapEnvelopeRequest, crypto, wsSecHeader);
		element = secSOAPReqDoc.getDocumentElement();
		// Transformación del elemento DOM a String
		source = new DOMSource(element);
		baos = new ByteArrayOutputStream();
		streamResult = new StreamResult(baos);
		TransformerFactory.newInstance().newTransformer().transform(source, streamResult);
		secSOAPReq = new String(baos.toByteArray());

		// Creación de un nuevo mensaje SOAP a partir del mensaje SOAP
		// securizado formado
		MessageFactory mf = new org.apache.axis.soap.MessageFactoryImpl();
		res = mf.createMessage(null, new ByteArrayInputStream(secSOAPReq.getBytes()));

		return res;
	}
}
