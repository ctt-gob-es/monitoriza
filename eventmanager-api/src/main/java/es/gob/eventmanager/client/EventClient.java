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
package es.gob.eventmanager.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.gob.eventmanager.message.Alert;
import es.gob.eventmanager.message.Event;
import es.gob.eventmanager.message.EventResponse;

/**
 * Cliente para el env&iacute;o de alertas.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 07/09/2021.
 */
public class EventClient {

	private static final String HTTP_HEADER_AUTHENTICATION = "Authentication"; //$NON-NLS-1$

	private static final String HMAC_HEADER_PREFIX = "hmac ";

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private final String systemId;

	private final String node;

	private final URL url;

	private final char[] pwdBase;

	/**
	 * Construye el cliente para el env&iacute;o de alertas.
	 * @param systemId Identificador del sistema que desea notificar las alertas.
	 * @param node Nombre del nodo desde donde se emiten las alertas.
	 * @param systemKey Clave para a autenticaci&oacute;n del sistema.
	 * @throws NullPointerException Cuando alguno de los par&aacute;metros es nulo.
	 */
	public EventClient(final String systemId, final String node, final URL url, final String systemKey) {

		if (systemId == null) {
			throw new NullPointerException("El identificador del sistema no puede ser nulo"); //$NON-NLS-1$
		}
		if (node == null) {
			throw new NullPointerException("El nodo de origen no puede ser nulo"); //$NON-NLS-1$
		}
		if (url == null) {
			throw new NullPointerException("La URL del servicio no puede ser nula"); //$NON-NLS-1$
		}
		if (systemKey == null) {
			throw new NullPointerException("El identificador del sistema no puede ser nulo"); //$NON-NLS-1$
		}

		this.systemId = systemId;
		this.node = node;
		this.url = url;
		this.pwdBase = systemKey.toCharArray();
	}

	/**
	 * Env&iacute;a una alerta.
	 * @param code C&oacute;digo de la alerta.
	 * @param message Mensaje descriptivo de la alerta.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 * @throws NullPointerException Cuando alguno de los par&aacute;metros es nulo.
	 */
	public EventResponse sendAlert(final String code, final String message) throws IOException {
		final Event event = new Event(this.systemId, this.node, new Alert(code, message));
		return send(event);
	}

	/**
	 * Env&iacute;a una alerta.
	 * @param code C&oacute;digo de la alerta.
	 * @param message Mensaje descriptivo de la alerta.
	 * @param config Configuraci&oacute;n adicional del evento generado.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 * @throws NullPointerException Cuando el c&oacute;digo o el mensaje son nulos.
	 */
	public EventResponse sendAlert(final String code, final String message, final Properties config) throws IOException {
		final Event event = new Event(this.systemId, this.node, new Alert(code, message));
		event.setParams(config);
		return send(event);
	}

	/**
	 * Env&iacute;a una alerta.
	 * @param alert Alerta.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 * @throws NullPointerException Cuando la alarma es nula.
	 */
	public EventResponse send(final Alert alert) throws IOException {
		final Event event = new Event(this.systemId, this.node, alert);
		return send(event);
	}

	/**
	 * Env&iacute;a una alerta.
	 * @param alert Alerta.
	 * @param config Configuraci&oacute;n adicional del evento generado.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 * @throws NullPointerException Cuando la alarma es nula.
	 *
	 */
	public EventResponse send(final Alert alert, final Properties config) throws IOException {
		final Event event = new Event(this.systemId, this.node, alert);
		event.setParams(config);
		return send(event);
	}

	/**
	 * Env&iacute;a un listado de alertas.
	 * @param alerts Listado de alertas.
	 * @param config Configuraci&oacute;n adicional del evento generado.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 * @throws NullPointerException Cuando el listado de alarmas es nulo.
	 */
	public EventResponse send(final List<Alert> alerts, final Properties config) throws IOException {
		final Event event = new Event(this.systemId, this.node, alerts);
		event.setParams(config);
		return send(event);
	}

	/**
	 * Env&iacute;a un evento.
	 * @param event Evento.
	 * @return Respuesta del sistema.
	 * @throws IOException Cuando no se puede enviar la notificaci&oacute;n al servicio.
	 */
	private EventResponse send(final Event event) throws IOException {

		// Iniciamos la conexion
		final HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
		conn.setDoOutput(true);

		// Construimos el cuerpo del mensaje
		final byte[] message = buildJsonMessage(event);

		// Establecemos las cabeceras de seguridad
		try {
			setSecurityHeader(conn, event.getSystemId(), message);
		}
		catch (final Exception e) {
			throw new IOException("No se han podido establecer las cabeceras de seguridad de la peticion", e); //$NON-NLS-1$
		}

		// Enviamos la peticion
		try (OutputStream os = conn.getOutputStream()) {
			os.write(message);
		}

		// Realizamos la peticion y obtenemos la respuesta
		EventResponse response;
		try (InputStream is = conn.getInputStream()) {
			response = buildResponse(is);
		}
		catch (final Exception e) {
			throw new IOException("Error al procesar la respuesta del servicio", e); //$NON-NLS-1$
		}

		return response;
	}

	/**
	 * Construye un JSON que representa a un evento.
	 * @param event Evento.
	 * @return JSON codificado.
	 */
	private static byte[] buildJsonMessage(final Event event) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		return gson.toJson(event).getBytes(CHARSET);
	}

	/**
	 * Agrega la cabecera HTTP con los datos de seguridad para la autorizaci&oacute;n de
	 * la petici&oacute;n.
	 * @param conn Conexi&oacute;n HTTP a la que agregar la cabecera.
	 * @param message Mensaje que se desea enviar y en base al que se generar&aacute; la cabecera.
	 */
	private void setSecurityHeader(final HttpURLConnection conn, final String systemId, final byte[] message) {
		final String authToken = AuthTokenBuilder.buildAuthToken(systemId, this.pwdBase, message);
		conn.setRequestProperty(HTTP_HEADER_AUTHENTICATION, HMAC_HEADER_PREFIX + authToken);
	}

	/**
	 * Obtenemos el objeto con la respuesta a la petici&oacute;n a partir del mensaje recibido.
	 * @param is Flujo de datos con el mensaje de respuesta.
	 * @return Objeto con la respuesta.
	 * @throws IOException Cuando la respuesta no puede leerse o no tiene un formato v&aacute;lido.
	 */
	private static EventResponse buildResponse(final InputStream is) throws IOException {

		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		EventResponse response;
		try (InputStreamReader reader = new InputStreamReader(is)) {
			response = gson.fromJson(reader, EventResponse.class);
		}
		return response;
	}
}
