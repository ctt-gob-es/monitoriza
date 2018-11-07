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
 * <b>File:</b><p>es.gob.monitoriza.form.ClaveForm.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a clave platform.</p>
  * <b>Project:</b><p>Application for monitoring the services of cl@ve suite systems</p>
 * <b>Date:</b><p>25 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 25/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;

/**
 * <p>
 * Class that represents the backing form for adding/editing a cl@ve platform.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of cl@ve suite systems.
 * </p>
 * 
 * @version 1.0, 25/10/2018.
 */
public class ClaveDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in
	 * the form.
	 */
	private Long idPlatform;

	/**
	 * Attribute that represents the value of the input name of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.name.notempty}")
	@Size(min = 1, max = 30, groups = ThenCheckIt.class)
	private String name = "";

	/**
	 * Attribute that represents the value of the input surnames of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.host.notempty}")
	@Size(min = 1, max = 30, groups = ThenCheckIt.class)
	private String host = "";

	/**
	 * Attribute that represents the value of the input port of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.port.notempty}")
	@Size(min = 1, max = 5, groups = ThenCheckIt.class)
	private String port = "";

	/**
	 * Attribute that represents the value of the input isSecure of the user in the
	 * form.
	 */
	private Boolean isSecure = false;

	/**
	 * Attribute that represents the value of the input password of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.servicecontext.notempty}")
	@Size(min = 1, max = 50, groups = ThenCheckIt.class)
	private String serviceContext = "";

	/**
	 * Gets the value of the attribute {@link #idPlatform}.
	 * 
	 * @return the value of the attribute {@link #idPlatform}.
	 */
	public Long getIdPlatform() {
		return idPlatform;
	}

	/**
	 * Sets the value of the attribute {@link #idPlatform}.
	 * 
	 * @return the value of the attribute {@link #idPlatform}.
	 */
	public void setIdPlatform(Long idPlatform) {
		this.idPlatform = idPlatform;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * 
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * 
	 * @param login
	 *            the value for the attribute {@link #host} to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * 
	 * @return the value of the attribute {@link #port}.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * 
	 * @param port
	 *            the value for the attribute {@link #port} to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * 
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * 
	 * @param isSecure
	 *            the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * 
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * 
	 * @param name
	 *            the value for the attribute {@link #name} to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #serviceContex}.
	 * 
	 * @return the value of the attribute {@link #serviceContex}.
	 */
	public String getServiceContext() {
		return serviceContext;
	}

	/**
	 * Sets the value of the attribute {@link #serviceContext}.
	 * 
	 * @param serviceContext
	 *            the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

}
