/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.status.AfirmaServicesStatus.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that gets the average response times for @firma services.
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
 * @version 1.0, 21 dic. 2017.
 */
package es.gob.monitoriza.status;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.response.ResponseErrorConnection;
import es.gob.monitoriza.response.ResponseMonitoriza;


/** 
 * <p>Class that gets the average response times for @firma services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 05 feb. 2018.
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
			LOGGER.error(Language.getFormatResMonitoriza("", new Object[ ] { request.getRemoteAddr() }), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			
		byte[ ] responseBytes = null;
		OutputStream os = null;
		DataOutputStream out = null;
		
		try {
			
			// Procesamos la petición, obteniendo el posible parámetro
			// para filtrar la plataforma
			final String platformFilter = request.getParameter(GeneralConstants.OPERATION_CODE_PLATFORM_FILTER);
			
			os = response.getOutputStream();
			out = new DataOutputStream(new BufferedOutputStream(os));
			
			response.setContentType("text/html");
			response.addHeader("Server:", "Servidor Monitoriz@");
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html");
			
			responseBytes = ResponseMonitoriza.render(platformFilter).getBytes();
			response.setContentLength(responseBytes.length);
			
			out.write(responseBytes);
			out.flush();
			
		} catch(Exception e) {
			
			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseBytes = ResponseErrorConnection.render().getBytes();
				response.setContentLength(responseBytes.length);
				out.write(responseBytes);
				out.flush();
				LOGGER.error(Language.getFormatResMonitoriza("", new Object[ ] { request.getRemoteAddr() }), e);
			} catch (Exception e1) {
				// No se hace nada...
			}
			
		} finally {
			
		}
	
	}
	
}
