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

import java.util.List;

/**
 * Configuraci&oacute;n necesaria para el notificador, ya sea para su configuraci&oacute;n
 * general, para los datos de configuraci&oacute;n de env&iacute;o de alertas o para los
 * datos de configuraci&oacute;n de env&iacute;o de res&uacute;menes.
 * <b>Project:</b><p>Sistema de gesti&oacute;n de eventos.</p>
 * @version 1.0, 24/09/2021.
 */
public class NotifierConfig {

	/** Listado de par&aacute;metros de configuraci&oacute;n. */
	private List<NotifierConfigParam> params;

	/** Objeto para con los datos de configuraci&oacute;n. */
	private NotifierConfigDTO configDto;

	/**
	 * Obtiene el listado de par&aacute;metros para la configuraci&oacute;n.
	 * @return Listado de par&aacute;metros.
	 */
	public List<NotifierConfigParam> getParams() {
		return this.params;
	}

	/**
	 * Establece el listado de par&aacute;metros para la configuraci&oacute;n.
	 * @param params Listado de par&aacute;metros.
	 */
	public void setParams(final List<NotifierConfigParam> params) {
		this.params = params;
	}

	/**
	 * Obtiene una instancia del objeto con los valores de configuraci&oacute;n
	 * para su uso como clase DTO.
	 * @return Objeto DTO con los valores de configuraci&oacute;n.
	 */
	public NotifierConfigDTO getConfigDto() {
		return this.configDto;
	}

	/**
	 * Establece una instancia del objeto con los valores de configuraci&oacute;n
	 * para su uso como clase DTO.
	 * @param configDto Objeto DTO con los valores de configuraci&oacute;n.
	 */
	public void setConfig(final NotifierConfigDTO configDto) {
		this.configDto = configDto;
	}
}
