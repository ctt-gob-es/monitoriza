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
 * @version 1.1, 10/10/2018.
 */
package es.gob.monitoriza.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

/** 
 * <p>Class that represents the backing form for adding/editing a ts@ platform.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 10/10/2018.
 */
public class TsaForm {
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idPlatform;
	
	/**
	 * Attribute that represents the value of the input name of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.name.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String name = "";

	/**
	 * Attribute that represents the value of the input surnames of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.host.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String host = "";
	
	/**
	 * Attribute that represents the value of the input username of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.port.notempty}")
    @Size(min=1, max=5, groups=ThenCheckIt.class)
    private String port = "";
	
	/**
	 * Attribute that represents the value of the input isSecure of the user in the form. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.platform.secure.notnull}")
    private Boolean isSecure = false;
	
	/**
	 * Attribute that represents the value of the input httpsPort of the user in the form. 
	 */
	@Size(min=0, max=5, groups=ThenCheckIt.class)
    private String httpsPort = "";

	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.servicecontext.notempty}")
    @Size(min=1, max=50, groups=ThenCheckIt.class)
    private String serviceContext = "";
	
	/**
	 * Attribute that represents the value of the input email of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.rfc3161context.notempty}")
    @Size(min=1, max=50, groups=ThenCheckIt.class)
    private String rfc3161Context = "";
	
	/**
	 * Attribute that represents the value of the input email of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.platform.portrfc3161.notempty}")
    @Size(min=1, max=5, groups=ThenCheckIt.class)
    private String rfc3161Port = "";
	
	/**
	 * Attribute that represents the value of the input isSecure of the user in the form. 
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.valid.platform.useauth.notnull}")
    private Boolean useRfc3161Auth = false;
	
	/**
	 * Attribute that represents the identifier of the certificate for RFC3161 authentication. 
	 */
	@NotNull(message="{form.platform.authcert.notnull}")
	private Long rfc3161Certificate;
		
				
	/**
	 * Gets the value of the attribute {@link #idPlatform}.
	 * @return the value of the attribute {@link #idPlatform}.
	 */	
	public Long getIdPlatform() {
		return idPlatform;
	}

	/**
	 * Sets the value of the attribute {@link #idPlatform}.
	 * @param the value of the attribute {@link #idPlatform} to set.
	 */
	public void setIdPlatform(Long idPlatform) {
		this.idPlatform = idPlatform;
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
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecure the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}
	
	/**
	 * Gets the value of the attribute {@link #httpsPort}.
	 * @return the value of the attribute {@link #httpsPort}.
	 */	
	public String getHttpsPort() {
		return httpsPort;
	}

	/**
	 * Sets the value of the attribute {@link #httpsPort}.
	 * @param httpsPort the value for the attribute {@link #httpsPort} to set.
	 */
	public void setHttpsPort(String httpsPort) {
		this.httpsPort = httpsPort;
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
	 * @param serviceContext the value for the attribute {@link #serviceContext} to set.
	 */
	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Context}.
	 * @return the value of the attribute {@link #rfc3161Context}.
	 */
	public String getRfc3161Context() {
		return rfc3161Context;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Context}.
	 * @param rfc3161Context the value for the attribute {@link #rfc3161Context} to set.
	 */
	public void setRfc3161Context(String rfc3161Context) {
		this.rfc3161Context = rfc3161Context;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Port}.
	 * @return the value of the attribute {@link #rfc3161Port}.
	 */
	public String getRfc3161Port() {
		return rfc3161Port;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Port}.
	 * @param rfc3161Port the value for the attribute {@link #rfc3161Port} to set.
	 */
	public void setRfc3161Port(String rfc3161Port) {
		this.rfc3161Port = rfc3161Port;
	}
			
	/**
	 * Gets the value of the attribute {@link #useRfc3161Auth}.
	 * @return the value of the attribute {@link #useRfc3161Auth}.
	 */
	public Boolean getUseRfc3161Auth() {
		return useRfc3161Auth;
	}
	
	/**
	 * Sets the value of the attribute {@link #useRfc3161Auth}.
	 * @param useRfc3161Auth the value for the attribute {@link #useRfc3161Auth} to set.
	 */
	public void setUseRfc3161Auth(Boolean useRfc3161Auth) {
		this.useRfc3161Auth = useRfc3161Auth;
	}

	/**
	 * Gets the value of the attribute {@link #rfc3161Certificate}.
	 * @return the value of the attribute {@link #rfc3161Certificate}.
	 */
	public Long getRfc3161Certificate() {
		return rfc3161Certificate;
	}

	/**
	 * Sets the value of the attribute {@link #rfc3161Certificate}.
	 * @param rfc3161Certificate the value for the attribute {@link #rfc3161Certificate} to set.
	 */
	public void setRfc3161Certificate(Long rfc3161Certificate) {
		this.rfc3161Certificate = rfc3161Certificate;
	}	
				
}
