package es.gob.monitoriza.log.register;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Gestor de la configuraci&oacute;n del servicio.
 */
class ConfigManager {

	private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());

	private static final String PROP_AUTHORIZATION_KEY = "auth.key"; //$NON-NLS-1$

	private static final String PROP_SERVICE_NAME = "logs.register.service.name";

	private static final String PROP_SERVICE_DESCRIPTION = "logs.register.service.description";

	private static final String PROP_SERVICE_TYPE = "logs.register.service.type";

	private static final String PROP_SERVICE_URL = "logs.register.service.url";

	private final Properties config;

	public ConfigManager(final Properties config) {
		this.config = config;
	}

	/**
	 * Obtiene la clave de cifrado configurada.
	 * @return Clave de cifrado.
	 */
	public byte[] getCipherKey() {

		final String encodedKey = this.config.getProperty(PROP_AUTHORIZATION_KEY);
		try {
			return Base64.decode(encodedKey);
		} catch (final IOException e) {
			LOGGER.severe("La clave de autorizacion no esta correctamente codificada: " + e); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * Recupera el nombre del servicio o host del que se pueden obtener los logs.
	 * @return Nombre del servicio o host al que pertenecen los logs que se pueden consultar.
	 */
	public String getLogServiceName() {
		return this.config.getProperty(PROP_SERVICE_NAME);
	}

	/**
	 * Recupera la descripci&oacute;n del servicio del que se pueden obtener los logs.
	 * @return Descripci&oacute;n del servicio al que pertenecen los logs que se pueden consultar.
	 */
	public String getLogServiceDescription() {
		return this.config.getProperty(PROP_SERVICE_DESCRIPTION);
	}

	/**
	 * Recupera el tipo del servicio del que se pueden obtener los logs.
	 * @return Tipo de servicio al que pertenecen los logs que se pueden consultar.
	 */
	public String getLogServiceType() {
		return this.config.getProperty(PROP_SERVICE_TYPE);
	}

	/**
	 * Recupera la URL para el acceso al servicio de consulta de logs.
	 * @return URL de acceso al servicio de consulta de logs.
	 */
	public String getLogServiceUrl() {
		return this.config.getProperty(PROP_SERVICE_URL);
	}

	/**
	 * Comprueba que se hayan configurado todas las propiedades necesarias para el registro del servicio en Monitoriza.
	 *
	 * @throws IOException Cuando falta alguna propiedad obligatoria para el funcionamiento del servicio.
	 */
	public void check() throws IOException {
		final StringBuilder buffer = new StringBuilder();
		if (!this.config.containsKey(PROP_SERVICE_NAME)) {
			buffer.append(PROP_SERVICE_NAME).append(",");
		}
		if (!this.config.containsKey(PROP_SERVICE_DESCRIPTION)) {
			buffer.append(PROP_SERVICE_DESCRIPTION).append(",");
		}
		if (!this.config.containsKey(PROP_SERVICE_TYPE)) {
			buffer.append(PROP_SERVICE_TYPE).append(",");
		}
		if (!this.config.containsKey(PROP_SERVICE_URL)) {
			buffer.append(PROP_SERVICE_URL).append(",");
		}
		if (!this.config.containsKey(PROP_AUTHORIZATION_KEY)) {
			buffer.append(PROP_AUTHORIZATION_KEY).append(",");
		}
		if (buffer.length() > 0) {
			throw new IOException("No se han proporcionado todas las propiedades de configuracion necesarias para registrar el servicio: " + buffer.toString());
		}

	}
}
