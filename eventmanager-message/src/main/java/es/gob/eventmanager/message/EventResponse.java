package es.gob.eventmanager.message;

import java.io.Serializable;

/**
 *
 * Respuesta obtenida del servicio de gesti&oacute;n de eventos.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 07/09/2021.
 */
public class EventResponse implements Serializable {

	/** Serial ID. */
	private static final long serialVersionUID = -4183796374683570896L;

	private final String result;

	private String description;


	public EventResponse(final String result) {
		this.result = result;
		this.description = null;
	}


	public void setDescription(final String description) {
		this.description = description;
	}


	public String getResult() {
		return this.result;
	}


	public String getDescription() {
		return this.description;
	}
}
