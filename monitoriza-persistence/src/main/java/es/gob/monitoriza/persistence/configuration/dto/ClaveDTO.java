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
 * <b>Date:</b><p>25/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 30/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>
 * Class that represents the backing form for adding/editing a cl@ve platform.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of cl@ve suite systems.
 * </p>
 * 
 * @version 1.1, 30/01/2019.
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
	@Size(min = 1, max = NumberConstants.NUM30, groups = ThenCheckIt.class)
	private String name = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input surnames of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.host.notempty}")
	@Size(min = 1, max = NumberConstants.NUM30, groups = ThenCheckIt.class)
	private String host = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input port of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.port.notempty}")
	@Size(min = 1, max = NumberConstants.NUM5, groups = ThenCheckIt.class)
	private String port = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input isSecure of the user in the
	 * form.
	 */
	private Boolean isSecure = false;

	/**
	 * Attribute that represents the value of the input password of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.platform.httpcontext.notempty}")
	@Size(min = 1, max = NumberConstants.NUM50, groups = ThenCheckIt.class)
	private String serviceContext = UtilsStringChar.EMPTY_STRING;

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
	 * @param idPlatformParam Platform identifier
	 */
	public void setIdPlatform(Long idPlatformParam) {
		this.idPlatform = idPlatformParam;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param hostParam the value for the attribute {@link #host} to set.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param portParam the value for the attribute {@link #port} to set.
	 */
	public void setPort(String portParam) {
		this.port = portParam;
	}

	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecureParam the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(Boolean isSecureParam) {
		this.isSecure = isSecureParam;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param nameParam the value for the attribute {@link #name} to set.
	 */
	public void setName(String nameParam) {
		this.name = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #serviceContex}.
	 * @return the value of the attribute {@link #serviceContex}.
	 */
	public String getServiceContext() {
		return serviceContext;
	}

	/**
	 * Sets the value of the attribute {@link #serviceContext}.
	 * @param serviceContextParam the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContextParam) {
		this.serviceContext = serviceContextParam;
	}

}
