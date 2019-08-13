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
 * <b>File:</b><p>es.gob.valet.rest.TslRestServiceApplication.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * <b>Date:</b><p>7/08/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 13/08/2019.
 */
package es.gob.monitoriza.rest.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import es.gob.monitoriza.rest.services.RegisterLogRestService;

/** 
 * <p>Class needed for restful ws server.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version1.1, 13/08/2019.
 */
@ApplicationPath("/rest")
public class RegisterConfigRestServiceApplication extends Application {

	/**
	 * Attribute that represents singletons objects. 
	 */
	private final Set<Object> singletons = new HashSet<Object>();

	/**
	 * Constructor method for the class TslRestServiceApplication.java. 
	 */
	public RegisterConfigRestServiceApplication() {
		singletons.add(new NodeRestService());
		singletons.add(new RegisterLogRestService());
	}

	/**
	 * {@inheritDoc}
	 * @see javax.ws.rs.core.Application#getSingletons()
	 */
	// CHECKSTYLE:OFF -- Checkstyle rule "Design for Extension" is not applied
	// because Restful needs not final access methods.
	@Override
	public Set<Object> getSingletons() {
		// CHECKSTYLE:ON
		return singletons;
	}

}
