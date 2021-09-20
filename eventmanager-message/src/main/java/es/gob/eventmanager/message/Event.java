package es.gob.eventmanager.message;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

/**
 * Evento que se env&acute;a al sistema.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 06/09/2021.
 */
public class Event implements Serializable {

	/** Serial ID. */
	private static final long serialVersionUID = -6481402645923713695L;

	private static final SecureRandom RANDOM;

	/** Identificador del sistema. */
	private String systemId;

	/** Nodo que origina el evento. */
	private String node;

	/** Listado de alertas. */
	private List<Alert> alerts;

	/** Configuraci&oacute;n adicional del evento. */
	private Properties params;

	/** Aleatorio con el que garantizar que nunca habr&aacute; dos eventos iguales. */
	private String random;

	static {
		RANDOM = new SecureRandom();
	}

	/**
	 * Construye el evento vac&iacute;o. S&oacute;lo se deber&iacute;a usar para la
	 * recreaci&oacute;n autom&aacute;tica de un objeto.
	 */
	public Event() {
		this.systemId = null;
		this.node = null;
		this.alerts = null;
		this.params = null;
		this.random = null;
	}

	/**
	 * Construye un evento.
	 * @param systemId Identificador del sistema que lo origina.
	 * @param node Nombre del nodo que lo emite.
	 * @param alerts Listado de alarmas asociado.
	 */
	public Event(final String systemId, final String node, final List<Alert> alerts) {

		if (systemId == null) {
			throw new NullPointerException("El identificador de aplicacion no puede ser nulo");
		}
		if (node == null) {
			throw new NullPointerException("El nodo no puede ser nulo");
		}
		if (alerts == null || alerts.isEmpty()) {
			throw new NullPointerException("El listado de alertas no puede ser nulo ni vacio");
		}
		this.systemId = systemId;
		this.node = node;

		this.alerts = new ArrayList<>(alerts.size());
		for (final Alert alert : alerts) {
			this.alerts.add((Alert) alert.clone());
		}
		this.params = null;

		// Generamos bytes aleatorios con los que hacer unico el evento
		this.random = getRandomString();
	}

	/**
	 * Construye un evento.
	 * @param systemId Identificador del sistema que lo origina.
	 * @param node Nombre del nodo que lo emite.
	 * @param alert Alerta asociada.
	 */
	public Event(final String systemId, final String node, final Alert alert) {

		if (systemId == null) {
			throw new NullPointerException("El identificador de aplicacion no puede ser nulo");
		}
		if (node == null) {
			throw new NullPointerException("El nodo no puede ser nulo");
		}
		if (alert == null) {
			throw new NullPointerException("La alerta no puede ser nula");
		}

		this.systemId = systemId;
		this.node = node;
		this.alerts =  Arrays.asList(alert);
		this.params = null;

		// Generamos bytes aleatorios con los que hacer unico el evento
		this.random = getRandomString();
	}

	/**
	 * Genera una cadena aleatoria.
	 * @return Cadena aleatoria.
	 */
	private static String getRandomString() {
		final byte[] randomBytes = new byte[8];
		RANDOM.nextBytes(randomBytes);
		return Base64.getEncoder().encodeToString(randomBytes);
	}

	/**
	 * Establece los par&aacute;metros adicionales de configuraci&oacute;n.
	 * @param params Par&aacute;metros adicionales de configuraci&oacute;n.
	 */
	public void setParams(final Properties params) {
		this.params = params;
	}

	/**
	 * Recupera el identificador del sistema que emite el evento.
	 * @return Identificador del sistema que emite el evento.
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * Recupera el nodo desde el que se origina el evento.
	 * @return Nodo desde el que se origina el evento.
	 */
	public String getNode() {
		return this.node;
	}

	/**
	 * Recupera el listado de alertas emitidas.
	 * @return Listado de alertas emitidas.
	 */
	public List<Alert> getAlerts() {
		return this.alerts;
	}

	/**
	 * Recupera los par&aacute;metros de configuraci&oacute;n.
	 * @return Par&aacute;metros de configuraci&oacute;n.
	 */
	public Properties getParams() {
		return this.params;
	}

	/**
	 * Recupera una cadena aleatoria asociada al evento.
	 * @return Cadena aleatoria.
	 */
	public String getRandom() {
		return this.random;
	}

	/**
	 * Establece el identificador del sistema. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param systemId Identificador del sistema.
	 */
	public void setSystemId(final String systemId) {
		this.systemId = systemId;
	}

	/**
	 * Establece el nombre del nodo. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param node Nombre del nodo.
	 */
	public void setNode(final String node) {
		this.node = node;
	}

	/**
	 * Establece el listado de alarmas. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param alerts Listado de alarmas.
	 */
	public void setAlerts(final List<Alert> alerts) {
		this.alerts = alerts;
	}

	/**
	 * Establece la cadena aleatoria. S&oacute;lo para generacion autom&aacute;ca del objeto.
	 * @param random Cadena aleatoria.
	 */
	public void setRandom(final String random) {
		this.random = random;
	}
}
