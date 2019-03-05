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
 * <b>File:</b><p>es.gob.monitoriza.rest.client.MonitorizaClient.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 05/03/2019.
 */
package es.gob.monitoriza.rest.client;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import es.gob.monitoriza.exception.MonitorizaRestException;
import es.gob.monitoriza.rest.elements.NodeRestStatusResponse;
import es.gob.monitoriza.rest.services.INodeRestService;

/** 
 * <p>Class that implements a client for Monitoriz@ rest services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 05/03/2019.
 */
public class MonitorizaClient {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(MonitorizaClient.class);

	/**
	 * Attribute that represents the object that manages the communication with Monitoriza rest services.
	 */
	private INodeRestService restService;
	
	/**
	 * Constructor method for the class MonitorizaClient.java.
	 * @param urlMonitorizatRest Endpoint for Monitoriz@ rest services.
	 * @param timeout Limit in seconds the network timeout for connect to Monitoriza rest services.
	 */
	public MonitorizaClient(final String urlMonitorizatRest, final Integer timeout) {
		// TODO IMPORTANTE: Hay que establecer una creación del cliente en
		// función de si
		// el destino es HTTP o HTTPS, así como dar la posibilidad de usar
		// proxy.
		// Incluso se podría configurar almacenes de confianza e identificación
		// para conexiones segura.
		// También es posible especificar listado de SNI válidos.
		// Del mismo modo se debería permitir configurar los algoritmos y
		// protocolos restringidos
		// para el cifrado de la conexión segura.
		ResteasyClient client = new ResteasyClientBuilder().readTimeout(timeout, TimeUnit.SECONDS).disableTrustManager().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlMonitorizatRest));
		restService = target.proxy(INodeRestService.class);
	}
	
	/**
	 * <br>Method that register a node in Monitoriz@. Registering a node could mean one of two things:</br>
	 * <br>1.- If the node not exists, a new {@link es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza} is persisted in repository.</br>
	 * <br>2.- If the node exists, sets the value of the attribute active of the node to true</br>
	 * @param nodeName Name of the node.
	 * @param nodeHost Host of the node.
	 * @param nodePort Port of the node.
	 * @param nodeType Platform type of the node.
	 * @param nodeSecure The connection to the node will be securized (true) or not (false).
	 * @param spieCodes {@link Set} of SPIEs to check.
	 * @return {@link NodeRestStatusResponse} with the result
	 * @throws MonitorizaRestException if the method fails
	 */
	public NodeRestStatusResponse registerNode(String nodeName, String nodeHost, String nodePort, String nodeType, Boolean nodeSecure, final Set<String> spieSelected) throws MonitorizaRestException {
		
		LOGGER.info("Starting call to \'registerNode\' method at Monitoriz@ rest service.");

		NodeRestStatusResponse response = null;
		
		if (restService != null) {
			
			try {
				response = restService.registerNode(nodeName, nodeHost, nodePort, nodeType, nodeSecure, spieSelected);
			} catch (ProcessingException e) {
				if (e.getCause().getClass().equals(UnknownHostException.class)) {
					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Unknown host. The address of the host could not be determined.");
				} else if (e.getCause().getClass().equals(SocketTimeoutException.class)) {
					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Network connection timeout. The service didn't response after seconds configured as 'timeout'.");
				} else if (e.getCause().getClass().equals(ConnectException.class)) {
					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection refused. Error occurred while attempting to connect a socket to a remote address and port.");
				} else {
					// If child exception of ProcessingException is unknown
					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection no available. There are internal processing errors on the server.");
				}
			} catch (NotFoundException e) {
				throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Not found. The resource requested by client was not found on the server.");
			} catch (Exception e) {
				throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection no available.", e);
			}
			
		}
		
		return response;
	}
	
	/**
	 * Method that un-register a node in Monitoriz@. This always means a de-activation setting the attribute active to false in {@link es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza}.
	 * @param nodeName Name of the node.
	 * @return {@link NodeRestStatusResponse} with the result
	 * @throws MonitorizaRestException if the method fails
	 */
//	public NodeRestStatusResponse unRegisterNode(String nodeName) throws MonitorizaRestException {
//		
//		LOGGER.info("Starting call to \'unRegisterNode\' method at Monitoriz@ rest service.");
//
//		NodeRestStatusResponse response = null;
//		
//		if (restService != null) {
//			
//			try {
//				response = restService.unRegisterNode(nodeName);
//			} catch (ProcessingException e) {
//				if (e.getCause().getClass().equals(UnknownHostException.class)) {
//					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Unknown host. The address of the host could not be determined.");
//				} else if (e.getCause().getClass().equals(SocketTimeoutException.class)) {
//					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Network connection timeout. The service didn't response after seconds configured as 'timeout'.");
//				} else if (e.getCause().getClass().equals(ConnectException.class)) {
//					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection refused. Error occurred while attempting to connect a socket to a remote address and port.");
//				} else {
//					// If child exception of ProcessingException is unknown
//					throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection no available. There are internal processing errors on the server.");
//				}
//			} catch (NotFoundException e) {
//				throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Not found. The resource requested by client was not found on the server.");
//			} catch (Exception e) {
//				throw new MonitorizaRestException("Error trying to connect to Monitoriza rest services. Connection no available.", e);
//			}
//			
//		}
//		
//		return response;
//	}
	
	

}
