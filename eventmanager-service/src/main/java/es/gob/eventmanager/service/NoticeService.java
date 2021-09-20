package es.gob.eventmanager.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.gob.eventmanager.message.Event;
import es.gob.eventmanager.message.EventResponse;
import es.gob.eventmanager.message.EventResponseType;

/**
 * Servicio para la notificaci&oacute;n de eventos.
 */
public class NoticeService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeService.class);

	private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeService() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		// Establecemos el juego de caracteres con el que se va a leer el contenido de la peticion
		request.setCharacterEncoding(CHARSET.displayName().toLowerCase());
		response.setCharacterEncoding(CHARSET.displayName().toLowerCase());

		final Event event;
		try {
			event = RequestValidator.validate(request);
		}
		catch (final SecurityException e) {
			LOGGER.warn("La peticion no cumple con los requisitos de seguridad", e);
			responseError(EventResponseType.ERROR_AUTHENTICATION, "La peticion no cumple con los requisitos de seguridad", response);
			return;
		}
		catch (final IOException e) {
			LOGGER.warn("El contenido de la peticion no es valido o no ha podido leerse", e);
			responseError(EventResponseType.ERROR_INVALID_REQUEST, "La peticion no cumple con los requisitos de seguridad", response);
			return;
		}
		catch (final Exception e) {
			LOGGER.warn("Error desconocido al validar la peticion", e);
			responseError(EventResponseType.UNKNOWN, "Error desconocido al validar la peticion", response);
			return;
		}

		// Procesamos el evento
		notifyEvent(event);

		// Registramos las estadisticas del evento
		registerStatistics(event);

		// Registramos el evento en los resumenes a los que pueda pertenecer
		registerOnResumes(event);

		// Enviamos la respuesta de exito
		responseOk(response);
	}

	/**
	 * Contesta al cliente devolviendo una respuesta de error.
	 * @param type Tipo de error.
	 * @param message Mensaje de error.
	 * @param httpResponse Respuesta HTTP sobre la que enviar el mensaje.
	 * @throws IOException Cuando ocurre un error durante la respuesta.
	 */
	private static void responseError(final EventResponseType type, final String message, final HttpServletResponse httpResponse) throws IOException {
		final EventResponse response = new EventResponse(type.getCode());
		response.setDescription(message);
		try (Writer writer = httpResponse.getWriter()) {
			writer.write(buildJsonMessage(response));
		}
	}

	/**
	 * Contesta al cliente devolviendo una respuesta de &eacute;xito.
	 * @param httpResponse Respuesta HTTP sobre la que enviar el mensaje.
	 * @throws IOException Cuando ocurre un error durante la respuesta
	 */
	public static void responseOk(final HttpServletResponse httpResponse) throws IOException {
		final EventResponse response = new EventResponse(EventResponseType.OK.getCode());
		try (Writer writer = httpResponse.getWriter()) {
			writer.write(buildJsonMessage(response));
		}
	}

	/**
	 * Construye un JSON que representa la respuesta de un evento.
	 * @param eventResponse Respuesta de un evento.
	 * @return Cadena JSON.
	 */
	private static String buildJsonMessage(final EventResponse eventResponse) {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		return gson.toJson(eventResponse);
	}

	/**
	 * Emite las notificaciones pertinentes a las alertas del evento recibido.
	 * @param event Evento en el que se notifican alertas.
	 */
	private void notifyEvent(final Event event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Registra con fines estad&iacute;sticos la informaci&oacute;n de las
	 * alertas recibidas.
	 * @param event Evento en el que se notifican alertas.
	 */
	private void registerStatistics(final Event event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Registra con fines estad&iacute;sticos la informaci&oacute;n de las
	 * alertas recibidas.
	 * @param event Evento en el que se notifican alertas.
	 */
	private void registerOnResumes(final Event event) {
		// TODO Auto-generated method stub

	}

}
