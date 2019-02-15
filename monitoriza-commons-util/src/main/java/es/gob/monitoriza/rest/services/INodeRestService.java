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
 * <b>File:</b><p>es.gob.monitoriza.rest.services.IRegisterConfigRestService.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>06/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 06/02/2019.
 */
package es.gob.monitoriza.rest.services;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.gob.monitoriza.exception.MonitorizaRestException;
import es.gob.monitoriza.rest.elements.NodeRestStatusResponse;

/** 
 * <p>Interface that represents the registration restful service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 06/02/2019.
 */
public interface INodeRestService {
	
	/**
	 * Constant attribute that represents the token parameter 'nodeName'.
	 */
	String PARAM_NODE_NAME = "nodeName";
	
	/**
	 * Constant attribute that represents the token parameter 'nodeHost'.
	 */
	String PARAM_NODE_HOST = "nodeHost";
	
	/**
	 * Constant attribute that represents the token parameter 'nodePort'.
	 */
	String PARAM_NODE_PORT = "nodePort";
	
	/**
	 * Constant attribute that represents the token parameter 'nodeSecure'.
	 */
	String PARAM_NODE_SECURE = "nodeSecure";
	
	/**
	 * Constant attribute that represents the token parameter 'nodeType'.
	 */
	String PARAM_NODE_TYPE = "nodeType";
	
	/**
	 * Constant attribute that represents the token parameter 'spieSelected'.
	 */
	String PARAM_SPIE_SELECTED = "spieSelected";
	
	/**
	 * Constant attribute that represents the token parameter 'registerNode'.
	 */
	String SERVICENAME_REGISTER_NODE = "registerNode";
		
	/**
	 * Constant attribute that represents the token parameter 'unRegisterNode'.
	 */
	String SERVICENAME_UNREGISTER_NODE = "unRegisterNode";
		
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
	@POST
	@Path("/registerNode")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	NodeRestStatusResponse registerNode(@FormParam(PARAM_NODE_NAME) String nodeName, @FormParam(PARAM_NODE_HOST) String nodeHost, @FormParam(PARAM_NODE_PORT) String nodePort, @FormParam(PARAM_NODE_TYPE) String nodeType, @FormParam(PARAM_NODE_SECURE) Boolean nodeSecure, @FormParam(PARAM_SPIE_SELECTED) Set<String> spieSelected) throws MonitorizaRestException;
	
	/**
	 * Method that un-register a node in Monitoriz@. This always means a de-activation setting the attribute active to false in {@link es.gob.monitoriza.persistence.configuration.model.entity.NodeMonitoriza}.
	 * @param nodeName Name of the node.
	 * @return {@link NodeRestStatusResponse} with the result
	 * @throws MonitorizaRestException if the method fails
	 */
	NodeRestStatusResponse unRegisterNode(@FormParam(PARAM_NODE_NAME) String nodeName) throws MonitorizaRestException;

}
