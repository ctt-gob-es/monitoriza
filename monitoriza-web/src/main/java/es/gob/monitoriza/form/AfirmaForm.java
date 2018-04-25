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
 * <b>File:</b><p>es.gob.monitoriza.form.UserForm.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 8 mar. 2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class that represents the backing form for adding/editing a user.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 8 mar. 2018.
 */
public class AfirmaForm {
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idPlatformAfirma = null;
	
	/**
	 * Attribute that represents the value of the input name of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.afirma.name.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String name = "";

	/**
	 * Attribute that represents the value of the input surnames of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.afirma.host.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String host = "";
	
	/**
	 * Attribute that represents the value of the input username of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.afirma.port.notempty}")
    @Size(min=1, max=5, groups=ThenCheckIt.class)
    private String port = "";

	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.afirma.servicecontext.notempty}")
    @Size(min=1, max=50, groups=ThenCheckIt.class)
    private String serviceContext = "";
		
	/**
	 * Attribute that represents the value of the input email of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.afirma.ocspcontext.notempty}")
    @Size(min=1, max=50, groups=ThenCheckIt.class)
    private String ocspContext = "";
			
	/**
	 * Gets the value of the attribute {@link #idPlatformAfirma}.
	 * @return the value of the attribute {@link #idPlatformAfirma}.
	 */	
	public Long getIdPlatformAfirma() {
		return idPlatformAfirma;
	}

	/**
	 * Gets the value of the attribute {@link #idPlatformAfirma}.
	 * @return the value of the attribute {@link #idPlatformAfirma}.
	 */
	public void setIdPlatformAfirma(Long idPlatformAfirma) {
		this.idPlatformAfirma = idPlatformAfirma;
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
	 * @param login the value for the attribute {@link #host} to set.
	 */
	public void setHost(String host) {
		this.host = host;
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
	 * @param password the value for the attribute {@link #port} to set.
	 */
	public void setPort(String port) {
		this.port = port;
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
	 * @param name the value for the attribute {@link #name} to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param name the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Gets the value of the attribute {@link #ocspContext}.
	 * @return the value of the attribute {@link #ocspContext}.
	 */
	public String getOcspContext() {
		return ocspContext;
	}
	
	/**
	 * Sets the value of the attribute {@link #ocspContext}.
	 * @param name the value for the attribute {@link #ocspContext} to set.
	 */
	public void setOcspContext(String ocspContext) {
		this.ocspContext = ocspContext;
	}
		
}
