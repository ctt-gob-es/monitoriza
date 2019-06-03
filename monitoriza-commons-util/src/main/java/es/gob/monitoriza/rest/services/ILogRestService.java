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

import es.gob.monitoriza.exception.MonitorizaRestException;
import es.gob.monitoriza.rest.elements.LogRestRegisterResponse;

/**
 * <p>Interface that represents the registration restful service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 06/02/2019.
 */
public interface ILogRestService {

	/**
	 * Constant attribute that represents the token parameter 'logName'.
	 */
	String PARAM_LOG_NAME = "logName";

	/**
	 * Constant attribute that represents the token parameter 'logDescription'.
	 */
	String PARAM_LOG_DESCRIPTION = "logDescription";

	/**
	 * Constant attribute that represents the token parameter 'logType'.
	 */
	String PARAM_LOG_TYPE = "logType";

	/**
	 * Constant attribute that represents the token parameter 'logUrl'.
	 */
	String PARAM_LOG_URL = "logUrl";

	/**
	 * Constant attribute that represents the token parameter 'logKey'.
	 */
	String PARAM_LOG_KEY = "logKey";

	/**
	 * Constant attribute that represents the token parameter 'registerLog'.
	 */
	String SERVICENAME_REGISTER_LOG = "registerLog";

	/**
	 * Service to register a remote logs service. If the service exist (same URL), ignore the request.
	 * @param nodeName Name of the remote node.
	 * @param nodeDescription Description of the remote node.
	 * @param nodeType Type of the remote node
	 * @param nodeUrl Url from the remote node.
	 * @param nodeKey AES key encoded with Base64.
	 * @return Operation result.
	 * @throws MonitorizaRestException If the method fails
	 */
	LogRestRegisterResponse registerLog(String nodeName, String nodeDescription, String nodeType, String nodeUrl, String nodeKey) throws MonitorizaRestException;
}
