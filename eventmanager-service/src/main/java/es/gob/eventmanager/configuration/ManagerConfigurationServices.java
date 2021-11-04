/*
/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
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

/**
 * <b>File:</b><p>es.gob.eventmanager.configuration.ManagerPersistenceConfigurationServices.java.</p>
 * <b>Description:</b><p>Manager singleton instance for the use of the persistence services
 * of the configuration scheme.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gob.eventmanager.bo.IEventManagerBO;


/**
 * <p>Manager singleton instance for the use of the persistence services
 * of the configuration scheme.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ManagerConfigurationServices {

	/**
	 * Attribute that represents the unique singleton instance of the class.
	 */
	private static ManagerConfigurationServices instance = null;

	/**
	 * Gets the unique singleton instance of the class.
	 * @return the unique singleton instance of the class.
	 */
	public static ManagerConfigurationServices getInstance() {
		return instance;
	}

	/**
	 * Method that initializes the singleton unique instance.
	 */
	@PostConstruct
	public void init() {
		instance = this;
	}

	/**
	 * Method that destroy the singleton unique instance of this class.
	 */
	@PreDestroy
	public final void destroy() {
		instance = null;
	}

	/**
	 * Attribute that represents the services for the configuration persistence: Alarms.
	 */
	@Autowired
	private IEventManagerBO eventManagerBO;

	/**
	 * Gets the value of the attribute {@link #eventManagerBO}.
	 * @return the value of the attribute {@link #eventManagerBO}.
	 */
	public final IEventManagerBO getEventManagerBO() {
		return eventManagerBO;
	}
		
}
