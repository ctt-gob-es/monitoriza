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
 * <b>Description:</b><p>Class that handles the securization of SOAP messages for @Firma requests.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/09/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 26/09/2023.
 */
package es.gob.monitoriza.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axiom.soap.SOAPBody;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.saaj.SOAPHeaderElementImpl;
import org.apache.axis2.saaj.util.SAAJUtil;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.gob.monitoriza.utilidades.UtilsStringChar;
import es.gob.monitoriza.webservice.UtilsAxis;

/** 
 * <p>Class that handles the securization of SOAP messages for @Firma requests.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.3, 26/09/2023.
 */
public class ClientHandler extends AbstractCommonHandler {
	
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
	public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		SOAPMessage secMsg;
		
		Document doc = null;

		secMsg = null;

		try {
			// Obtención del documento XML que representa la petición SOAP
			doc = SAAJUtil.getDocumentFromSOAPEnvelope(msgContext.getEnvelope());
			// Securización de la petición SOAP según la opcion de seguridad
			// configurada
			if (this.securityOption.equals(USERNAMEOPTION)) {
				secMsg = this.createUserNameToken(doc);
			} else if (this.securityOption.equals(CERTIFICATEOPTION)) {
				secMsg = this.createBinarySecurityToken(doc);
			}

			// Modificación de la petición SOAP
			if (!this.securityOption.equals(NONEOPTION) && secMsg != null) {
				
				// Eliminamos el contenido del body e insertamos el nuevo body
				// generado.
				msgContext.getEnvelope().getBody().removeChildren();
				SOAPBody body = msgContext.getEnvelope().getBody();
				UtilsAxis.updateSoapBody(body, secMsg.getSOAPBody());
				
				// Añadimos las cabeceras generadas.
				Iterator<?> headers = secMsg.getSOAPHeader().getChildElements();
				while (headers.hasNext()) {
				    msgContext.getEnvelope().getHeader().addChild(UtilsAxis.fromSOAPHeaderToOMElement((SOAPHeaderElementImpl) headers.next()));
				}
			}
		} catch (Exception e) {
			throw AxisFault.makeFault(e);
		}
		
		return InvocationResponse.CONTINUE;
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

		// Eliminamos el provider ApacheXMLDSig de la lista de provider para que
		// no haya conflictos con el nuestro.
		Provider apacheXMLDSigProvider = Security.getProvider("ApacheXMLDSig");
		Security.removeProvider("ApacheXMLDSig");

		try {
		    // Inserción del tag wsse:Security y userNameToken
		    wsSecHeader = new WSSecHeader(null, false);
		    wsSecUsernameToken = new WSSecUsernameToken();
		    wsSecUsernameToken.setPasswordType(getPasswordType());
		    wsSecUsernameToken.setUserInfo(getUserAlias(), getPassword());
		    wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
		    wsSecUsernameToken.prepare(soapEnvelopeRequest);
		    // Añadimos una marca de tiempo inidicando la fecha de creación del
		    // tag
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
		    MessageFactory mf = new org.apache.axis2.saaj.MessageFactoryImpl();
		    res = mf.createMessage(null, new ByteArrayInputStream(secSOAPReq.getBytes()));

		} finally {
			// Eliminamos de nuevo el provider por si se ha añadido otra
		    // versión durante la generación de la petición.
		    Security.removeProvider("ApacheXMLDSig");

		    // Restauramos el provider ApacheXMLDSig eliminado inicialmente.
		    if (apacheXMLDSigProvider != null) {
			// Añadimos el provider.
			Security.insertProviderAt(apacheXMLDSigProvider, 1);
		    }
		}

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

		// Eliminamos el provider ApacheXMLDSig de la lista de provider para que
		// no haya conflictos con el nuestro.
		//Provider apacheXMLDSigProvider = Security.getProvider("ApacheXMLDSig");
		//Security.removeProvider("ApacheXMLDSig");

		try {
		    // Inserción del tag wsse:Security y X509CertificateToken
		    wsSecHeader = new WSSecHeader(null, false);
		    wsSecHeader.setMustUnderstand(true);
		    wsSecSignature = new WSSecSignature();
		    crypto = initializateCryptoProperties();
		    // Indicación para que inserte el tag X509CertificateToken
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
		    MessageFactory mf = new org.apache.axis2.saaj.MessageFactoryImpl();
		    res = mf.createMessage(null, new ByteArrayInputStream(secSOAPReq.getBytes()));

		} finally {
		    // Restauramos el provider ApacheXMLDSig eliminado inicialmente.
		    //if (apacheXMLDSigProvider != null) {
    			// Eliminamos de nuevo el provider por si se ha añadido otra
    			// versión durante la generación de la petición.
    			//Security.removeProvider("ApacheXMLDSig");
    			// Añadimos el provider.
    			//Security.insertProviderAt(apacheXMLDSigProvider, 1);
		    //}
		}
		return res;
	}
}
