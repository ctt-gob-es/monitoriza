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
 * <b>File:</b><p>es.gob.monitoriza.webservice.ClientManager.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 26/09/2023.
 */
package es.gob.monitoriza.webservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.axis2.phaseresolver.PhaseException;
import org.springframework.stereotype.Service;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.crypto.exception.CryptographyException;
import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.enums.AuthenticationTypeEnum;
import es.gob.monitoriza.handler.ClientHandler;
import es.gob.monitoriza.handler.ResponseHandler;
import es.gob.monitoriza.keystore.KeystoreVersionFileManager;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.ValidService;
import es.gob.monitoriza.service.impl.KeystoreService;
import es.gob.monitoriza.service.utils.IServiceNameConstants;
import es.gob.monitoriza.spring.config.ApplicationContextProvider;
import es.gob.monitoriza.utilidades.loggers.Logger;

/** 
 * <p>Class ClientManager.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.6, 26/09/2023.
 */
@Service
public class ClientManager {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the localhost string.
	 */
	private static final String LOCALHOST = "localhost";
	
	/**
	 * Attribute that represents the name space uri.
	 */
	private static final String NAME_SPACE_URI = "http://soapinterop.org/";
	
	/**
	 * Attribute that represents the loval part.
	 */
	private static final String LOCAL_PART = "verify";
	
	/**
     * Attribute that represents the list of handlers added to the Axis engine. 
     */
    private static List<String> handlerAdded = new ArrayList<String>();
	
//	static {
		// for localhost testing only
//		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//
//			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//				if (hostname.equals(LOCALHOST)) {
//					return true;
//				}
//				return false;
//		
//			}
//		});
//	}

	/**
	 * Method that creates the service DSSCertificate.
	 * @param url URI
	 * @param validService Object with the information of the validation service.
	 * @param peticion Object array with the requests data.
	 * @return validation result
	 * @throws Exception If the method fails
	 */
	public String getDSSCertificateServiceClientResult(String url, ValidService validService, Object[] peticion) throws Exception {
		
		LOGGER.info("getDSSCertificateServiceClient init");
						
		ClientHandler requestHandler = null;
		ResponseHandler responseHandler = null;
		String res = null;		
		
		requestHandler = newRequestHandler(validService);	
		responseHandler = newResponseHandler(validService);
		
		// Creamos la factoria de objetos XML de AXIS2.
	    OMFactory fac = OMAbstractFactory.getOMFactory();
	    // Creamos el namespace de la petición.
	    OMNamespace ns = createNamespace(fac, "DSSAfirmaVerifyCertificate");
	    // Creamos el elemento XML raíz del SOAP body que indica la
	    // operación a realizar.
	    OMElement operationElem = fac.createOMElement(LOCAL_PART, ns);
	    // Creamos el elemento XML que contendrá la petición SOAP completa.
	    OMElement inputParamElem = fac.createOMElement("arg0", ns);
	    // Añadimos la petición al parámetro de entrada principal.
	    inputParamElem.setText((String) peticion[0]);
	    // Incluimos el parámetro a la operación para formar el body del
	    // SOAP
	    // completamente.
	    operationElem.addChild(inputParamElem);
		
		ServiceClient client = null;
		
		try {
			
			Options options = new Options();
		    LOGGER.info("Estableciendo el endpoint del Servicio de Validación a: " + url);
		    options.setTo(new EndpointReference(url));
		    // Desactivamos el chunked.
		    options.setProperty("__CHUNKED__", "false");			
		    client = new ServiceClient();
		    client.setOptions(options);
		    
			addHandlers(client, requestHandler, responseHandler);
			
			client.setOptions(options);
			OMElement result = client.sendReceive(operationElem);
			
			if (result != null && result.getFirstElement() != null && !result.getFirstElement().getText().isEmpty()) {
				res = result.getFirstElement().getText();
			} else {
				
				LOGGER.info("El Servicio de Validación ha obtenido una respuesta nula o vacía.");
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			
			removeHandlers(client);
		}
		LOGGER.info("getDSSCertificateServiceClient end");
		return res;
	}
	
	
/**
 * Method that creates a new instance of {@link ResponseHandler}.
 * @param validService Parameter that represents the mappend validation service.
 * @return the created instance of {@link ResponseHandler}.
*/
private ResponseHandler newResponseHandler(ValidService validService) {
	
	// Accedemos al Almacén de Autorización para el servicio de validación.
	KeystoreMonitoriza ksAuthValidServ = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);
	String keystorePath = KeystoreVersionFileManager.getAbsolutePathForTheKeystore(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);
	String keystorePass = ksAuthValidServ.getPassword();
	String keystoreType = ksAuthValidServ.getKeystoreType();
	ResponseHandler respHandler = new ResponseHandler(keystorePath, keystorePass, keystoreType, validService.getUser(), validService.getPass());
		
	return respHandler;
}

/**
 * Method that creates a new instance of {@link ClientHandler}.
 * @param validService Parameter that represents the mappend validation service.
 * @return the created instance of {@link ClientHandler}.
 * @throws CryptographyException If the method fails at decoding the keystore password.
*/
private ClientHandler newRequestHandler(ValidService validService) throws CryptographyException {
	
	ClientHandler reqHandler = null;
	
	if (validService.getAuthenticationType().getIdAuthenticationType().equals(AuthenticationTypeEnum.USERPASS.getId())) {
		reqHandler = new ClientHandler(AuthenticationTypeEnum.USERPASS.getName());
		reqHandler.setUserAlias(validService.getUser());
		reqHandler.setPassword(validService.getPass());
	} else if (validService.getAuthenticationType().getIdAuthenticationType().equals(AuthenticationTypeEnum.CERTIFICATE.getId())) {
		
		reqHandler = new ClientHandler(AuthenticationTypeEnum.CERTIFICATE.getName());
		// Propiedades para binarySecurityToken
		// Accedemos al Almacén de Autorización para el servicio de validación.
		KeystoreMonitoriza ksAuthValidServ = ApplicationContextProvider.getApplicationContext().getBean(IServiceNameConstants.KEYSTORE_SERVICE, KeystoreService.class).getKeystoreById(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);
		String keystorePath = KeystoreVersionFileManager.getAbsolutePathForTheKeystore(KeystoreMonitoriza.ID_VALID_SERVICE_STORE);
		String keystorePass = ksAuthValidServ.getPassword();
		String keystoreType = ksAuthValidServ.getKeystoreType();
		IKeystoreFacade keyStoreFacade = new KeystoreFacade(ksAuthValidServ);
		reqHandler.setUserAlias(validService.getValidServiceCertificate().getAlias());
		reqHandler.setPassword(keyStoreFacade.getKeystoreDecodedPasswordString(ksAuthValidServ.getPassword()));
		reqHandler.setUserKeystore(keystorePath);
		reqHandler.setUserKeystorePass(keystorePass);
		reqHandler.setUserKeystoreType(keystoreType);
		
	} else if (validService.getAuthenticationType().getIdAuthenticationType().equals(AuthenticationTypeEnum.NOAUTHENTICATION.getId())) {
		
		reqHandler = new ClientHandler(AuthenticationTypeEnum.NOAUTHENTICATION.getName());
	}
	
	return reqHandler;
}

	/**
     * Auxiliary method that adds the generated handlers to the 'phases' of Axis2.
     * @param client Service client.
     * @param requestHandler Request handler.
     * @param responseHandler Response handler.
     */
    private void addHandlers(ServiceClient client, ClientHandler requestHandler, ResponseHandler responseHandler) {

    	// Añadimos el handler de seguridad de salida.
    	AxisConfiguration config = client.getAxisConfiguration();
    	List<Phase> phasesOut = config.getOutFlowPhases();
    	for (Phase phase: phasesOut) {
    	    if ("Security".equals(phase.getPhaseName())) {
        		try {
        		    addHandler(phase, requestHandler, 1);
        		    break;
        		} catch (PhaseException e) {
        		    e.printStackTrace();
        		}
    	    }
    	}
    	
    	// Añadimos el handler de seguridad de entrada.
    	if (responseHandler != null) {
    	    List<Phase> phasesIn = config.getInFlowPhases();
    	    for (Phase phase: phasesIn) {
    		if ("Security".equals(phase.getPhaseName())) {
    		    try {
    			addHandler(phase, responseHandler, 2);
    			break;
    		    } catch (PhaseException e) {
    			e.printStackTrace();
    		    }
    		}
    	    }
    	}
    }

    /**
     * Method that removes the added handler from the axis engine.
     * @param client Axis service client.
     */
    private void removeHandlers(ServiceClient client) {
		if (client != null && !handlerAdded.isEmpty()) {
			AxisConfiguration config = client.getAxisConfiguration();

			// Recorremos las phases de salida.
			List<Phase> phasesOut = config.getOutFlowPhases();
			for (Phase phase: phasesOut) {
				removeHandler(phase);
			}

			// Recorremos las phases de entrada.
			List<Phase> phasesIn = config.getInFlowPhases();
			for (Phase phase: phasesIn) {
				removeHandler(phase);
			}

			// Reiniciamos la lista de handlers.
			handlerAdded = new ArrayList<String>();
		}

    }

    /**
     * Auxiliary method that removes the added handler from the given phase.
     * @param phase Axis phase where the handlers are.
     */
    private void removeHandler(Phase phase) {
		if (phase != null) {
			List<Handler> handlers = phase.getHandlers();
			for (Handler handler: handlers) {
				if (handlerAdded.contains(handler.getName())) {
					handler.getHandlerDesc().setHandler(handler);
					phase.removeHandler(handler.getHandlerDesc());
				}
			}
		}
    }

    /**
     * Auxiliary method that add a handler into an AXIS2 phase.
     * @param phase AXIS2 phase.
     * @param handler Handler to add.
     * @param position Indicates if the handler is added in the first place of the list (0), at the end (2) or is indifferent (1).
     * @throws PhaseException if it is not possible to add the handler to the phase.
     */
	private void addHandler(Phase phase, Handler handler, int position) throws PhaseException {
		if (position == 0 && !UtilsAxis.isHandlerInPhase(phase, handler)) {
			phase.setPhaseFirst(handler);
			handlerAdded.add(handler.getName());
			return;
		}
		if (position == 1 && !UtilsAxis.isHandlerInPhase(phase, handler)) {
			phase.addHandler(handler);
			handlerAdded.add(handler.getName());
			return;
		}
		if (position == 2 && !UtilsAxis.isHandlerInPhase(phase, handler)) {
			phase.setPhaseLast(handler);
			handlerAdded.add(handler.getName());
			return;
		}
	}
	
	/**
     * Auxiliary method that create the specific namespace for the specific service.
     * @param fac OM factory.
     * @param afirmaService service name.
     * @return the target namespace of the service.
     */
	private OMNamespace createNamespace(OMFactory fac, String afirmaService) {
		OMNamespace ns = fac.createOMNamespace("http://afirmaws/services/DSSAfirmaVerifyCertificate", "ns1");
		
		return ns;
	}

}
