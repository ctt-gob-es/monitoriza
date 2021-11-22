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
 * <b>File:</b><p>es.gob.eventmanager.spring.config.ApplicationConfiguration.java.</p>
 * <b>Description:</b><p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
 * <b>Project:</b><p>Event manager system.</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 22/11/2021.
 */
package es.gob.eventmanager.spring.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** 
 * <p>Spring configuration class that sets the configuration of Spring components, entities and repositories.</p>
  * <b>Project:</b><p>Event manager system.</p>
 * @version 1.1, 22/11/2021.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("es.gob.eventmanager")
@EntityScan("es.gob.eventmanager.persistence.model.entity")
@EnableJpaRepositories(basePackages = "es.gob.eventmanager.persistence.model.repository")
public class ApplicationConfig {
	
	/**
	 * Attribute that represents the logger of this class.
	 */
	private static Logger logger = LoggerFactory.getLogger("eventmanager-service");
	
	
	/**
	 * Method that initializes elements for this class and for the application boot.
	 */
	@PostConstruct
	public void init() {

		try {
			initializePlatform();
			logger.info("El servicio Event Manager ha iniciado con exito");
		} catch (Exception e) {
			logger.error("Ha ocurrido un error durante la inicializacion del servicio Event Manager", e);
		}

	}
	
	/**
	 * Method that initialize all the functions of the platform.
	 */
	private void initializePlatform() {
		
		logger.info("Inicializacion del servicio Event Manager");
				
	}
	
	
}
