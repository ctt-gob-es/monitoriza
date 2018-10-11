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
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.status.AfirmaServicesStatus.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that gets the status for the @firma/ts@ services through servlet call.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 21/12/2017.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.1, 10/10/2018.
 */
package es.gob.monitoriza.status;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.response.DatatableStatus;
import es.gob.monitoriza.response.ResponseErrorConnection;
import es.gob.monitoriza.response.ResponseJsonMonitoriza;
import es.gob.monitoriza.response.ResponseMonitoriza;


/** 
 * <p>Class that gets the status for the @firma/ts@ services through servlet call</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.1, 10/10/2018.
 */
public class AfirmaServicesStatus extends HttpServlet {

	/**
	 * Attribute that represents the generated version id. 
	 */
	private static final long serialVersionUID = -6583932223401086851L;
		
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
			
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "");
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error(Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS013, new Object[ ] { request.getRemoteAddr() }), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			
		byte[ ] responseBytes = null;
		String responseJson = null;
		OutputStream os = null;
		DataOutputStream out = null;
		
		
		// Procesamos la petición, obteniendo el posible parámetro
		// para filtrar la plataforma
		final String platformFilter = request.getParameter(GeneralConstants.OPERATION_CODE_PLATFORM_FILTER);
		final String adminFilter = request.getParameter(GeneralConstants.OPERATION_CODE_ADMIN_FILTER);
		
		try {
		
			os = response.getOutputStream();
			out = new DataOutputStream(new BufferedOutputStream(os));
					
			response.setStatus(HttpServletResponse.SC_OK);
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Server:", "Servidor Monitoriz@");
				
			if (adminFilter == null) {
    			responseBytes = ResponseMonitoriza.render(platformFilter, adminFilter).getBytes();
    			response.setContentLength(responseBytes.length);
    			response.setContentType("text/html");
    			
			} else {
				responseJson = ResponseJsonMonitoriza.getResponseStatus();
				response.setContentLength(responseJson.length());
				response.setContentType("application/Json");
		        		        
				responseBytes = responseJson.getBytes();
			}
			
			out.write(responseBytes);
			out.flush();
			
		} catch(Exception e) {
			
			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				final String msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS014, new Object[ ] { request.getRemoteAddr() });
				
				if (adminFilter == null) {
    				responseBytes = ResponseErrorConnection.render().getBytes();
    				response.setContentLength(responseBytes.length);
    				
				} else {
					DatatableStatus dtStatusError = new DatatableStatus();
					dtStatusError.setError(msgError);
					ObjectMapper objectMapper = new ObjectMapper();
					responseJson = objectMapper.writeValueAsString(dtStatusError);
					responseBytes = responseJson.getBytes();
				}
				
				out.write(responseBytes);
				out.flush();
				LOGGER.error(msgError, e);
			} catch (Exception e1) {
				// No se hace nada...
			}
			
		} finally {
			
		}
	
	}
	
}
