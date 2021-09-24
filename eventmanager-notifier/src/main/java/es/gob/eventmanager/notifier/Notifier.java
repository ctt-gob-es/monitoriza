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

import javax.naming.OperationNotSupportedException;

import es.gob.eventmanager.message.Event;

/**
 * Interfaz para el env&iacute;o de notificaciones.
 * <b>Project:</b><p>Event manager system.</p>
 * @version 1.0, 21/09/2021.
 */
public interface Notifier {

	/**
	 * Notifica la recepci&oacute;n de un evento.
	 * @param event Evento que debe notificarse.
	 * @throws NotifierException Cuando ocurre un error durante la notificaci&oacute;n.
	 */
	void notifyEvent(Event event) throws NotifierException;

	/**
	 * Notifica el resumen de una serie de eventos.
	 * @param events Listado de eventos a partir de los que debe generarse el resumen.
	 * @throws NotifierException Cuando ocurre un error durante la notificaci&oacute;n.
	 * @throws OperationNotSupportedException Cuando el notificador no soporta el env&iacute;o
	 * de res&uacute;menes.
	 */
	void notifyResume(List<Event> events) throws NotifierException, OperationNotSupportedException;


	/**
	 * Obtiene el identificador de esta instancia del notificador.
	 * @return Identificador de la instancia del notificador.
	 */
	int getSystemId();

	/**
	 * Devuelve informacion sobre el sistema de notificaci&oacute;n.
	 * @return Informaci&oacute;n sobre el sistema de notificaci&oacute;n.
	 */
	NotifierInfo getInfo();

	/**
	 * Devuelve la informaci&oacute;n necesaria para establecer la configuraci&oacute;n del sistema
	 * de notificaci&oacute;n.
	 * @param systemId Identificador del sistema del que se desea obtener le
	 * @return Configuraci&oacute;n con los par&aacute;metros y recursos para el env&iacute;o.
	 */
	List<NotifierConfigParam> getSystemConfig(int systemId);

	/**
	 * Almacena la configuraci&oacute;n indicada, que debe ser la misma obtenida con el m&eacute;todo
	 * {@code #getConfig()}, a la que se le puede haber modificado el valor de cada uno de los
	 * par&aacute;metros.
	 * @param values Listado de par&aacute;metros con el valor asignado.
	 * @see {@link NotifierConfigParam#setValue(Object)}
	 */
	void saveSystemConfig(List<NotifierConfigParam> values);

	/**
	 * Devuelve la informaci&oacute;n necesaria para establecer la configuraci&oacute;n del env&iacute;o
	 * de un evento al sistema de notificaci&oacute;n.
	 * @param alertConfigId Identificador de la configuraci&oacute;n del evento.
	 * @return Configuraci&oacute;n con los par&aacute;metros y recursos para el env&iacute;o.
	 */
	List<NotifierConfigParam> getEventConfig(int alertConfigId);

	/**
	 * Almacena la configuraci&oacute;n indicada, que debe ser la misma obtenida con el m&eacute;todo
	 * {@code #getEventConfig()}, a la que se le puede haber modificado el valor de cada uno de los
	 * par&aacute;metros.
	 * @param values Listado de par&aacute;metros con el valor asignado.
	 */
	void saveEventConfig(int alertConfigId, List<NotifierConfigParam> values);

	/**
	 * Devuelve la informaci&oacute;n necesaria para establecer la configuraci&oacute;n del env&iacute;o
	 * de un resumen al sistema de notificaci&oacute;n.
	 * @param resumeConfigId Identificador de la configuraci&oacute;n del resumen.
	 * @return Configuraci&oacute;n con los par&aacute;metros y recursos para el env&iacute;o.
	 */
	List<NotifierConfigParam> getResumeConfig(int resumeConfigId);

	/**
	 * Almacena la configuraci&oacute;n indicada, que debe ser la misma obtenida con el m&eacute;todo
	 * {@code #getResumeConfig()}, a la que se le puede haber modificado el valor de cada uno de los
	 * par&aacute;metros.
	 * @param resumeConfigId Identificador de la configuraci&oacute;n del resumen.
	 * @param values informaci&oacute;n de configuraci&oacute;n con los par&aacute;metros asignados.
	 */
	void saveResumeConfig(int resumeConfigId, List<NotifierConfigParam> values);
}
