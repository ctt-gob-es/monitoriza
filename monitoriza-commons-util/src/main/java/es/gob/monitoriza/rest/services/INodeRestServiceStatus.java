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
 * <b>File:</b><p>es.gob.monitoriza.rest.services.INodeRestServiceStatus.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>11/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 05/03/2019.
 */
package es.gob.monitoriza.rest.services;


/** 
 * <p>Interface that defines the constants for the result status of the services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 05/03/2019.
 */
public interface INodeRestServiceStatus {

	/**
	 * Constant attribute that represents the value for the general result status service
	 * when there is an error checking the input parameters.
	 */
	Integer STATUS_ERROR_INPUT_PARAMETERS = Integer.valueOf(0);
	
	/**
	 * Constant attribute that represents the value for the general result status service
	 * when there is an error checking the node type parameter.
	 */
	Integer STATUS_ERROR_NODETYPE_PARAMETER = Integer.valueOf(1);

	/**
	 * Constant attribute that represents the value for the general result status service
	 * when there is an error executing the service.
	 */
	Integer STATUS_ERROR_EXECUTING_SERVICE = Integer.valueOf(2);
	
	/**
	 * Constant attribute that represents the value for the result status service
	 * when the node registration is successful and the node is modified.
	 */
	Integer STATUS_NODE_REGISTER_MODIFIED = Integer.valueOf(3);
	
	/**
	 * Constant attribute that represents the value for the result status service
	 * when the node registration is successful and the node is created.
	 */
	Integer STATUS_NODE_REGISTER_CREATED = Integer.valueOf(4);
	
	/**
	 * Constant attribute that represents the value for the general result status service
	 * when the indicated node doesn't exist during un-register process.  
	 */
	Integer STATUS_ERROR_NODE_NOT_FOUND = Integer.valueOf(5);
	
	/**
	 * Constant attribute that represents the value for the result status service
	 * when the node registration is successful and the node is created.
	 */
	Integer STATUS_NODE_UNREGISTER = Integer.valueOf(6);
	
	/**
	 * Constant attribute that represents the value for the result status service
	 * when the node already exists and the service does nothing.
	 */
	Integer STATUS_ALREADY_REGISTERED = Integer.valueOf(7);
}
