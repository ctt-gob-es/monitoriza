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
 * <b>File:</b><p>es.gob.monitoriza.spie.status.SpieStatus.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/10/2018.
 */
package es.gob.monitoriza.spie.status;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.StatusSpieDTO;
import es.gob.monitoriza.spie.response.ResponseJsonSpie;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30/10/2018.
 */
public class SpieStatus extends HttpServlet {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = -4507504345193367361L;
	
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
		
		try {
		
			os = response.getOutputStream();
					
			out = new DataOutputStream(new BufferedOutputStream(os));
					
			response.setStatus(HttpServletResponse.SC_OK);
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Server:", "Servidor SPIE Monitoriz@");
							
			responseJson = ResponseJsonSpie.getResponseStatus();
			response.setContentType("application/Json");
			responseBytes = responseJson.getBytes();
			response.setContentLength(responseBytes.length);
			
			
			out.write(responseBytes);
			out.flush();
			
		} catch(Exception e) {
			
			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				final String msgError = Language.getFormatResMonitoriza(IStatusLogMessages.ERRORSTATUS014, new Object[ ] { request.getRemoteAddr() });

				StatusSpieDTO dtStatusError = new StatusSpieDTO();
				dtStatusError.setError(msgError);
				ObjectMapper objectMapper = new ObjectMapper();
				responseJson = objectMapper.writeValueAsString(dtStatusError);
				responseBytes = responseJson.getBytes();

				out.write(responseBytes);
				out.flush();
				LOGGER.error(msgError, e);
			} catch (Exception e1) {
				// No se hace nada...
			}
			
		} finally {
			
			try {
				out.close();
			} catch (IOException e) {
				// No se hace nada...
				
			}
		}
	
	}

}
