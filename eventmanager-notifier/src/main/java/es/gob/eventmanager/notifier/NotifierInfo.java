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
package es.gob.eventmanager.notifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Informaci&oacute;n sobre le notificador.
 * <b>Project:</b><p>Sistema de gesti&oacute;n de eventos.</p>
 * @version 1.0, 23/09/2021.
 */
public class NotifierInfo {

	/** Propiedad para la configuracion del nombre el notificador. */
	private static final String PROP_NAME = "name";

	/** Propiedad con la que indicar si el notificador soporta el env&oacute;o de resumenes. */
	private static final String PROP_RESUME_SUPPORTED = "resumeSupported";

	/** Propiedad con la que indicar si el notificador soporta el env&oacute;o de resumenes. */
	private static final String PROP_MULTIPLE_INSTANCES_ALLOWED = "multipleIntancesAllowed";

	private final String name;

	private boolean resumeSupported = false;

	private boolean multipleIntancesAllowed = true;

	/**
	 * Construye la informacion del notificador.
	 * Constructor method for the class NotifierInfo.java.
	 * @param name
	 */
	public NotifierInfo(final String name) {
		this.name = name;
	}

	/**
	 * Obtiene el nombre del notificador, lo que identifica su tipo (mail, graylog, etcetera).
	 * @return Nombre del identificador.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Indica si el sistema de notificaci&oacute;n soporta el env&iacute;o de res&uacute;menes.
	 * @return {@code true} si se soporta el env&iacute;o de res&uacute;menes o {@code false} en
	 * caso contrario.
	 */
	public boolean isResumeSuported() {
		return this.resumeSupported;
	}

	/**
	 * Establece si el sistema de notificaci&oacute;n soporta el env&iacute;o de res&uacute;menes.
	 * @param resumeSupported {@code true} si se soporta el env&iacute;o de res&uacute;menes o
	 * {@code false} en caso contrario.
	 */
	void setResumeSupported(final boolean resumeSupported) {
		this.resumeSupported = resumeSupported;
	}

	/**
	 * Indica si se puede haber m&acute;s de una instancia del notificador dado de alta en el
	 * sistema. Por ejemplo, s&oacute;lo puede haber una instancia del notificador por correo
	 * electr&oacute;nico, ya que no tendr&acute;a sentido que unos correos se enviasen desde
	 * un servidor y otros desde otro, o que se notificasen las alertas varias veces a cada destino
	 * desde varios servidores de correo distintos. S&iacute; que puede haber m&aacute;s de un
	 * notificador a GrayLog, ya que se podr&iacute;an notificar las alarmas a distintos GrayLog.
	 * @return {@code true} si puede haber mas de una instancia, {@code false} en caso contrario.
	 */
	public boolean isMultipleIntancesAllowed() {
		return this.multipleIntancesAllowed;
	}

	/**
	 * Establece si el sistema de notificaci&oacute;n soporta el env&iacute;o de res&uacute;menes.
	 * @param resumeSupported {@code true} si se soporta el env&iacute;o de res&uacute;menes o
	 * {@code false} en caso contrario.
	 */
	public void setMultipleIntancesAllowed(final boolean multipleIntancesAllowed) {
		this.multipleIntancesAllowed = multipleIntancesAllowed;
	}

	/**
	 * Carga la informaci&oacute;n de un notificador a partir de un lector de datos.
	 * La informaci&oacute;n debe estar construida en forma de {@code properties} con las
	 * propiedades requeridas.
	 * @param reader Lector para la carga de las propiedades.
	 * @return Informaci&oacute;n del notificador.
	 * @throws IOException Cuando ocurre un error durante la lectura de los datos.
	 * @throws NotifierException Cuando faltan datos o no se obtener la informaci&oacute;n
	 * del notificador.
	 */
	public static NotifierInfo parse(final InputStreamReader reader) throws IOException, NotifierException {

		final Properties config = new Properties();
		config.load(reader);

		final String name = config.getProperty(PROP_NAME);
		if (name == null || name.trim().isEmpty()) {
			throw new NotifierException(String.format(
					"No se ha encontrado un parametro %s con el nombre del notificador",
					PROP_NAME));
		}

		final NotifierInfo info = new NotifierInfo(name);

		if (config.containsKey(PROP_RESUME_SUPPORTED)) {
			final boolean supported = Boolean.parseBoolean(config.getProperty(PROP_RESUME_SUPPORTED));
			info.setResumeSupported(supported);
		}

		if (config.containsKey(PROP_MULTIPLE_INSTANCES_ALLOWED)) {
			final boolean allowed = Boolean.parseBoolean(config.getProperty(PROP_MULTIPLE_INSTANCES_ALLOWED));
			info.setMultipleIntancesAllowed(allowed);
		}

		return info;
	}
}
