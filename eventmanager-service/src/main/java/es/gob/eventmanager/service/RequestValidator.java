/* ******************************************************************************
 * Copyright (C) 2021 MINHAFP, Gobierno de España
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
package es.gob.eventmanager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.gob.eventmanager.configuration.ManagerConfigurationServices;
import es.gob.eventmanager.exception.EventManagerException;
import es.gob.eventmanager.message.Alert;
import es.gob.eventmanager.message.Event;
import es.gob.eventmanager.persistence.model.entity.ApplicationMonitoriza;

/**
 * Servicio para la notificaci&oacute;n de eventos.
 * @version 1.1, 04/11/2021.
 */
@Component
public class RequestValidator {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private static final String HTTP_HEADER_AUTHENTICATION = "Authentication"; //$NON-NLS-1$

	private static final String HMAC_HEADER_PREFIX = "hmac ";

	/** Tama&ntilde;o m&iacute;nimo del salto para la derivaci&oacute;n de clave. */
	private static final int MIN_SALT_SIZE = 32;

	/** Diferencia de tiempo en milisegundos que puede haber entre la */
	private static final long MAX_TIME_DIFFERENCE = 300000; // 5 Minutos	
	

	/**
	 * Comprueba que la petici&oacute;n haya sido enviada por una aplicaci&oacute;n v&aacute;lida
	 * y que se hayan respetado todas las medidas de seguridad.
	 * @param request Petici&oacute;n con el evento.
	 * @return Evento cargado.
	 * @throws IOException Cuando no se pueda validar la petici&oacute;n o su formato sea incorrecto.
	 * @throws SecurityException Cuando falle la validaci&oacute;n de la petici&oacute;n.
	 */
	static Event validate(final HttpServletRequest request) throws IOException, SecurityException {

		// Leemos el nombre de la aplicacion de la cabecera
		final SecurityHeader securityHeader = checkSecurityHeader(request);

		// Comprobamos que la aplicacion exista y obtenemos de ella la contrasena base
		final char[] basePwd = checkApp(securityHeader.getAppName());

		// Leemos el cuerpo de la peticion
		final String body = loadRequestBody(request).toString();

		// Comprobamos la cabecera de seguridad
		checkIntegrity(securityHeader, basePwd, body.getBytes(CHARSET));

		// Cargamos el objeto evento de la peticion
		final Event event = loadEvent(body);

		// Comprueba que el evento haya sido emitido por la aplicacion declarada
		// y que el evento sea reciente
		checkEvent(securityHeader.getAppName(), event);

		return event;
	}

	/**
	 * Recupera el nombre de la aplicacion de la cabecera de la petici&oacute;n.
	 * @param request Petici&oacute;n de entrada.
	 * @return Informaci&oacute;n de la cabecera de seguridad.
	 */
	private static SecurityHeader checkSecurityHeader(final HttpServletRequest request) {

		final String authHeader = request.getHeader(HTTP_HEADER_AUTHENTICATION);
		if (authHeader == null) {
			throw new SecurityException("La peticion no contenia la cabecera de seguridad");
		}

		if (!authHeader.startsWith(HMAC_HEADER_PREFIX)) {
			throw new SecurityException("Cabecera de seguridad no soportada");
		}

		final String[] parts = authHeader.substring(HMAC_HEADER_PREFIX.length()).split(":");
		if (parts == null || parts.length != 3) {
			throw new SecurityException("La cabecera de seguridad no es valida");
		}

		final String appName = parts[0];
		if (appName == null || appName.trim().isEmpty()) {
			throw new SecurityException("No se ha indicado nombre de aplicacacion en la cabecera de seguridad");
		}

		final Decoder b64Decoder = Base64.getDecoder();
		final byte[] salt =  b64Decoder.decode(parts[1]);
		if (salt == null || salt.length < MIN_SALT_SIZE) {
			throw new SecurityException("Salto no valido");
		}

		final byte[] secureCode =  b64Decoder.decode(parts[2]);

		return new SecurityHeader(appName, salt, secureCode);
	}

	/**
	 * Comprueba que exista registrada en el sistema la aplicacion indicada y
	 * obtiene la contrase&ntilde;a base que tiene asignada.
	 * @param appName Nombre de la aplicaci&oacute;n.
	 * @return Contrase&ntilde;a base.
	 * @throws SecurityException Cuando la aplicaci&oacute;n no se encuentre
	 * dada de alta en el sistema.
	 */
	private static char[] checkApp(final String appName) throws SecurityException {

		// TODO: Acceso a la persistencia para comprobar que la aplicacion
		// existe y obtener su clave base
		ApplicationMonitoriza app = null;
		char[] password = new char[0];
		
		try {
			//app = RequestValidator.stEventManagerBO.getApplicationMonitorizaByName(appName);
			app = ManagerConfigurationServices.getInstance().getEventManagerBO().getApplicationMonitorizaByName(appName);
			
			if (app != null) {
				password = app.getCipherKey().toCharArray(); 
			}
		} catch (EventManagerException e) {
			
			throw new SecurityException(e.getMessage(), e.getCause());
		}
				
		return password;
	}

	/**
	 * Comprueba la integridad del mensaje.
	 * @param securityHeader Datos de la cabecera de seguridad.
	 * @param basePwd Contrase&ntilde;a a partir de la cual derivar la clave de autenticaci&oacute;n.
	 * @param body Cuerpo del mensaje que se ha enviado.
	 */
	private static void checkIntegrity(final SecurityHeader securityHeader, final char[] basePwd, final byte[] body) {

		AuthTokenChecker.checkAuthToken(securityHeader.getSecureCode(), basePwd, securityHeader.getSalt(), body);
	}

	/**
	 * Carga el cuerpo de la petici&oacute;n.
	 * @param request Petici&oacute;n realizada.
	 * @return Contenido de la petici&oacute;n.
	 * @throws IOException Cuando no se puede cargar la petici&oacute;n.
	 */
	private static StringBuilder loadRequestBody(final HttpServletRequest request) throws IOException {
					
		final StringBuilder buffer = new StringBuilder();
		try (final BufferedReader reader = request.getReader();) {
			
			reader.toString();
			int n = 0;
			final char[ ] block = new char[4096];

			while ((n = reader.read(block)) > 0) {
				if (n == block.length) {
					buffer.append(block);
				} else {
					buffer.append(Arrays.copyOfRange(block, 0, n));
				}
			}

		}
		return buffer;
	}

	/**
	 * Carga un objeto evento a partir de su codificaci&oacute;n JSON.
	 * @param eventJson Cadena JSON del evento.
	 * @return Evento.
	 * @throws IOException Cuando el contenido proporcionado no se corresponda
	 * con un JSON de evento.
	 */
	private static Event loadEvent(final String eventJson) throws IOException {
		try {
			final GsonBuilder builder = new GsonBuilder();
			final Gson gson = builder.create();
			return gson.fromJson(eventJson, Event.class);
		}
		catch (final Exception e) {
			throw new IOException("No se ha encontrado un JSON de evento");
		}
	}

	/**
	 * Comprueba que los datos del evento sean correctos.
	 * @param appName Nombre de la aplicaci&oacute;n que debe haber emitido el evento.
	 * @param event Evento recibido.
	 */
	private static void checkEvent(final String appName, final Event event) throws SecurityException {

		// Comprobamos que el nombre de aplicacion indicado en la cabecera de
		// seguridad sea igual al declarado en la peticion
		if (appName == null || !appName.equals(event.getSystemId())) {
			throw new SecurityException("La peticion no fue generada por la aplicacion indicada en la cabecera");
		}

		// Comprobamos que las alarmas del evento se generasen hace menos de
		// un tiempo limite. En caso contrario, alguien podria estar
		// reutilizando alarmas antiguas
		for (final Alert alert : event.getAlerts()) {
			if (Math.abs(new Date().getTime() - alert.getDate()) > MAX_TIME_DIFFERENCE) {
				throw new SecurityException("Una alerta se emitio fuera del rango de tiempo permitido");
			}
		}
	}

	/**
	 * Cabecera de seguridad con los datos de la petici&oacute;n que se debe comprobar.
	 * <b>Project:</b><p>Event manager system.</p>
	 * @version 1.0, 13/09/2021.
	 */
	private static class SecurityHeader {

		private final String appName;
		private final byte[] salt;
		private final byte[] secureCode;

		/**
		 * Contenido de la cabecera de seguridad
		 * Constructor method for the class RequestValidator.java.
		 * @param appName Nombre de aplicaci&oacute;n.
		 * @param salt Salto.
		 * @param secureCode C&oacute;digo de autorizaci&oacute;n del mensaje.
		 */
		public SecurityHeader(final String appName, final byte[] salt, final byte[] secureCode) {
			this.appName = appName;
			this.salt = salt;
			this.secureCode = secureCode;
		}

		/**
		 * Obtiene el nombre de aplicaci&oacute;n.
		 * @return Nombre de aplicaci&oacute;n.
		 */
		public String getAppName() {
			return this.appName;
		}

		/**
		 * Obtiene el salto de la clave.
		 * @return Salto de la clave.
		 */
		public byte[ ] getSalt() {
			return this.salt;
		}

		/**
		 * Obtiene el c&oacute;digo de autorizaci&oacute;n del mensaje.
		 * @return C&oacute;digo de autorizaci&oacute;n del mensaje.
		 */
		public byte[ ] getSecureCode() {
			return this.secureCode;
		}
	}
}
