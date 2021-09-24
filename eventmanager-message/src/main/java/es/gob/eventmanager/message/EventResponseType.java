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
package es.gob.eventmanager.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tipos de respuesta que puede devolver una llamada al servicio de gesti&oacute;n de eventos.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 13/09/2021.
 */
public enum EventResponseType {

	/** Respuesta exitosa. */
	OK("OK"),
	/** Error en la autenticaci&oacute;n de la petici&oacute;n. */
	ERROR_AUTHENTICATION("ERR_AUTH"),
	/** Petici&oacute;n mal formada. */
	ERROR_INVALID_REQUEST("ERR_REQUEST"),
	/** Error desconocido. */
	UNKNOWN("UNKNOWN");

	private static final Logger LOGGER = LoggerFactory.getLogger(EventResponseType.class);

	/** Tama&ntilde;o m&aacute;ximo de c&oacute;digo de respuesta. */
	private static final int MAX_SIZE_CODE = 20;

	private String code;

	private EventResponseType(final String code) {
		this.code = code;
	}

	/**
	 * Obtiene el c&oacute;digo de respuesta.
	 * @return C&oacute;digo de respuesta.
	 */
	public String getCode() {
		return this.code;
	}


	/**
	 * Parsea un c&oacute;digo de respuesta y devuelve a cual corresponde.
	 * @param code C&oacute;digo de respuesta.
	 * @return Tipo de respuesta.
	 */
	public static EventResponseType parse(final String code) {
		for (final EventResponseType response : values()) {
			if (response.getCode().equalsIgnoreCase(code)) {
				return response;
			}
		}
		LOGGER.info("No se conoce la respuesta del servicio de gestion de eventos con codigo: " + secureString(code, MAX_SIZE_CODE));
		return EventResponseType.UNKNOWN;
	}

	/**
	 * Construye una cadena segura que pueda imprimirse en el log.
	 * @param text Texto a imprimir.
	 * @param maxSize Tama&ntilde;o m&aacute;ximo permitido para el texto.
	 * @return Cadena segura.
	 */
	private static String secureString(final String text, final int maxSize) {
		if (text == null) {
			return null;
		}
		String secureText = text.trim();
		if (secureText.length() > maxSize) {
			secureText = secureText.substring(0, maxSize);
		}
		return secureText.replace("\n\r", " ").replace('\n', ' ').replace('\r', ' ');
	}
}
