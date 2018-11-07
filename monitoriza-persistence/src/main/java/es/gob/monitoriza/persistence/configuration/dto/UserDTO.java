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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.UserDTO.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8 mar. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import es.gob.monitoriza.persistence.configuration.dto.constraints.FieldMatch;
import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.NumberConstants;


/** 
 * <p>Class that represents the backing form for adding/editing a user.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 28/10/2018.
 */
@FieldMatch(first = "password", second = "confirmPassword", message = "{form.valid.user.password.confirm}")
public class UserDTO {
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idUserMonitoriza = null;
	
	/**
	 * Attribute that represents the value of the input name of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.name.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM15, groups=ThenCheckIt.class)
    private String name = "";

	/**
	 * Attribute that represents the value of the input surnames of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.surnames.notempty}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String surnames = "";
	
	/**
	 * Attribute that represents the value of the input username of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.login.notempty}")
    @Size(min=NumberConstants.NUM5, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String login = "";

	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.password.notempty}")
    @Size(min=NumberConstants.NUM7, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message="{form.valid.user.password.noPattern}", groups=ThenCheckIt.class)
    private String password = "";
	
	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.confirmPassword.notempty}")
    @Size(min=NumberConstants.NUM7, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String confirmPassword = "";
		
	/**
	 * Attribute that represents the value of the input email of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.email.notempty}")
    @Size(min=NumberConstants.NUM3, max=NumberConstants.NUM254, groups=ThenCheckIt.class)
    private String email = "";
			
	/**
	 * Gets the value of the attribute {@link #idUserMonitoriza}.
	 * @return the value of the attribute {@link #idUserMonitoriza}.
	 */
	public Long getIdUserMonitoriza() {
		return idUserMonitoriza;
	}

	/**
	 * Gets the value of the attribute {@link #idUserMonitoriza}.
	 * @param idUserMonitorizaParam UserMonitoriza identifier
	 */
	public void setIdUserMonitoriza(Long idUserMonitorizaParam) {
		this.idUserMonitoriza = idUserMonitorizaParam;
	}

	/**
	 * Gets the value of the attribute {@link #login}.
	 * @return the value of the attribute {@link #login}.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the value of the attribute {@link #login}.
	 * @param loginParam the value for the attribute {@link #login} to set.
	 */
	public void setLogin(String loginParam) {
		this.login = loginParam;
	}

	/**
	 * Gets the value of the attribute {@link #password}.
	 * @return the value of the attribute {@link #password}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the attribute {@link #password}.
	 * @param passwordParam the value for the attribute {@link #password} to set.
	 */
	public void setPassword(String passwordParam) {
		this.password = passwordParam;
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
	 * Gets the value of the attribute {@link #surnames}.
	 * @return the value of the attribute {@link #surnames}.
	 */
	public String getSurnames() {
		return surnames;
	}
	
	/**
	 * Sets the value of the attribute {@link #surnames}.
	 * @param surnamesParam the value for the attribute {@link #surnames} to set.
	 */
	public void setSurnames(String surnamesParam) {
		this.surnames = surnamesParam;
	}

	/**
	 * Gets the value of the attribute {@link #email}.
	 * @return the value of the attribute {@link #email}.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the value of the attribute {@link #email}.
	 * @param emailParam the value for the attribute {@link #email} to set.
	 */
	public void setEmail(String emailParam) {
		this.email = emailParam;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPasswordParam the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPasswordParam) {
		this.confirmPassword = confirmPasswordParam;
	}
		
	
}
