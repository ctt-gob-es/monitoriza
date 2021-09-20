package es.gob.eventmanager.message;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

/**
 * Alerta que se notifica.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 06/09/2021.
 */
public class Alert implements Serializable {

	/** Serial ID. */
	private static final long serialVersionUID = -1468772927837487752L;

	private String code;

	private String message;

	private String resource;

	private Properties config;

	private long date;

	/**
	 * Construye la alerta vac&iacute;a. S&oacute;lo se deber&iacute;a usar para la
	 * recreaci&oacute;n autom&aacute;tica de un objeto.
	 */
	public Alert() {
		this.code = null;
		this.message = null;
		this.date = 0;
		this.resource = null;
		this.config = null;
	}

	/**
	 * Construye la alerta con los datos obligatorios.
	 * @param code C&oacute;digo de alerta (debe estar dado de alta en el
	 * sistema de gesti&oacute;n de eventos).
	 * @param message Texto descriptivo de la alerta.
	 */
	public Alert(final String code, final String message) {
		this.code = code;
		this.message = message;
		this.date = new Date().getTime();
		this.resource = null;
		this.config = null;
	}

	/**
	 * Construye la alerta con los datos obligatorios.
	 * @param code C&oacute;digo de alerta (debe estar dado de alta en el
	 * sistema de gesti&oacute;n de eventos).
	 * @param message Texto descriptivo de la alerta.
	 * @param date Fecha de emisi&oacute;n de la alerta.
	 */
	public Alert(final String code, final String message, final Date date) {
		this.code = code;
		this.message = message;
		this.date = date.getTime();
		this.resource = null;
		this.config = null;
	}

	/**
	 * Establece el recurso por el que se produce la alerta.
	 * @param resource Nombre del recurso.
	 */
	public void setResource(final String resource) {
		this.resource = resource;
	}

	/**
	 * Establece la configuraci&oacute;n adicional para la notificaci&oacute;n
	 * de la alerta.
	 * @param config Configuraci&oacute;n.
	 */
	public void setConfig(final Properties config) {
		this.config = config != null ? (Properties) config.clone() : null;
	}

	/**
	 * Recupera el c&oacute;digo de error.
	 * @return C&oacute;digo de error.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Recupera el mensaje de error.
	 * @return Mensaje de error.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Recupera la fecha de emisi&oacute;n de la alerta en milisegundos.
	 * @return Fecha de emisi&oacute;n de la alerta en milisegundos.
	 */
	public long getDate() {
		return this.date;
	}

	/**
	 * Recupera el recurso por el que se origina el error.
	 * @return Recurso por el que se origina el error.
	 */
	public String getResource() {
		return this.resource;
	}

	/**
	 * Recupera la configuraci&oacute;n espec&iacute;fica para el env&iacute;o
	 * del error.
	 * @return Configuraci&oacute;n espec&iacute;fica para el env&iacute;o
	 * del error.
	 */
	public Properties getConfig() {
		return this.config != null ? (Properties) this.config.clone() : null;
	}

	@Override
	protected Object clone() {
		final Alert alertClone = new Alert(this.code, this.message);
		alertClone.setResource(this.resource);
		alertClone.setConfig(this.config != null ? (Properties) this.config.clone() : null);
		return alertClone;
	}

	/**
	 * Establece el c&oacute;digo de la alerta. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param code C&oacute;digo de la alerta.
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Establece el mensaje de la alerta. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param message Mensaje de la alerta.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Establece la fecha de la alerta. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param date Fecha de la alerta.
	 */
	public void setDate(final long date) {
		this.date = date;
	}
}
