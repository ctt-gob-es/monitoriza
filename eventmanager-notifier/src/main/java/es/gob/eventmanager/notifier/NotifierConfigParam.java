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
 * Par&aacute;metro de configuraci&oacute;n general del notificador, para el env&iacute;o de
 * mensajes o para el env&iacute;o de res&uacute;menes.
 * <b>Project:</b><p>Sistema de gesti&oacute;n de eventos.</p>
 * @version 1.0, 22/09/2021.
 */
public class NotifierConfigParam {

	/** Par&aacute;metro de tipo mensaje. Es un texto no configurable. */
	public static final int TYPE_MESSAGE = 1;
	/** Par&aacute;metro de tipo texto. Establece un texto con un textfield. */
	public static final int TYPE_TEXT = 2;
	/** Par&aacute;metro de tipo lista. Establece un listado de elementos con un textarea. */
	public static final int TYPE_LIST = 3;
	/** Par&aacute;metro de tipo lista de texto. Establece un elemento desde un listbox. */
	public static final int TYPE_TEXTLIST = 4;
	/** Par&aacute;metro de tipo listado de propiedades. Permite establecerlas con una tabla. */
	public static final int TYPE_PROPERTIES = 5;
	/** Par&aacute;metro de tipo booleano. Indica un estado con una casilla de verificaci&oacute;n. */
	public static final int TYPE_CHECK = 6;

	private final String name;

	private final int type;

	private String text;

	private String[] elements;

	private Object defaultValue;

	private Object value;

	/**
	 * Par&aacute;metro de configuraci&oacute;n del notificador.
	 * @param name Nombre del par&aacute;metro.
	 * @param type Tipo de par&aacute;metro.
	 */
	public NotifierConfigParam(final String name, final int type) {
		this.name = name;
		this.type = type;
		this.text = null;
	}

	/**
	 * Obtiene el nombre del par&aacute;metro.
	 * @return Nombre del par&aacute;metro.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Obtiene el tipo del par&aacute;metro.
	 * @return Tipo del par&aacute;metro.
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Establece el texto asociado al par&aacute;metro.
	 * @param text Texto asociado al par&aacute;metro.
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Obtiene el texto del par&aacute;metro.
	 * @return Texto del par&aacute;metro.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Establece el listado de elementos del par&aacute;metro.
	 * @param elements Listado de elementos requeridos por el par&aacute;metro.
	 */
	public void setElements(final String[] elements) {
		this.elements = elements;
	}

	/**
	 * Obtiene el listado de elementos del par&aacute;metro.
	 * @return Listado de elementos del par&aacute;metro cuando el tipo lo requiera o
	 * {@code null} si no lo requiere.
	 */
	public String[] getElements() {
		return this.elements;
	}

	/**
	 * Establece el valor por defecto asociado al par&aacute;metro.
	 * @param defaultValue Valor por defecto asociado al par&aacute;metro.
	 */
	public void setDefaultValue(final Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Obtiene el valor por defecto asociado al par&aacute;metro.
	 * @return Valor por defecto asociado al par&aacute;metro o {@code null} si no
	 * hay valor por defecto.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Establece el valor asociado al par&aacute;metro.
	 * @param value Valor asociado al par&aacute;metro.
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * Obtiene el valor asociado al par&aacute;metro.
	 * @return Valor asociado al par&aacute;metro o {@code null} si no
	 * est&aacute; establecido.
	 */
	public Object getValue() {
		return this.value;
	}
}
