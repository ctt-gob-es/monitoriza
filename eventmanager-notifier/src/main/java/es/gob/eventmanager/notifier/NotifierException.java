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

/**
 * Excepci&oacute;n en la carga de un notificador o el env&oacute;o de una notificaci&oacute;n.
 * <b>Project:</b><p>Sistema de gesti&oacute;n de eventos.</p>
 * @version 1.0, 22/09/2021.
 */
public class NotifierException extends Exception {

	/** Serial Id. */
	private static final long serialVersionUID = 4046822960742408804L;

	/**
	 * Construye la excepci&oacute;n con un mensaje descriptivo.
	 * @param message Descripci&oacute;n del error.
	 */
	public NotifierException(final String message) {
		super(message);
	}

	/**
	 * Construye la excepci&oacute;n con un mensaje descriptivo y su causa.
	 * @param message Descripci&oacute;n del error.
	 * @param cause Causa del error.
	 */
	public NotifierException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
