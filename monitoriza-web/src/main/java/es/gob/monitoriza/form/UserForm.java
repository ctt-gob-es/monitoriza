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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import es.gob.monitoriza.constraints.FieldMatch;
import es.gob.monitoriza.rest.exception.CheckItFirst;
import es.gob.monitoriza.rest.exception.ThenCheckIt;

@FieldMatch(first = "password", second = "confirmPassword", message = "{form.valid.user.password.confirm}")
/** 
 * <p>Class that represents the backing form for adding/editing a user.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 8 mar. 2018.
 */
public class UserForm {
	
	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form. 
	 */
	private Long idUserMonitoriza = null;
	
	/**
	 * Attribute that represents the value of the input name of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.name.notempty}")
    @Size(min=1, max=15, groups=ThenCheckIt.class)
    private String name = "";

	/**
	 * Attribute that represents the value of the input surnames of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.surnames.notempty}")
    @Size(min=1, max=30, groups=ThenCheckIt.class)
    private String surnames = "";
	
	/**
	 * Attribute that represents the value of the input username of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.login.notempty}")
    @Size(min=5, max=30, groups=ThenCheckIt.class)
    private String login = "";

	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.password.notempty}")
    @Size(min=7, max=30, groups=ThenCheckIt.class)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message="{form.valid.user.password.noPattern}", groups=ThenCheckIt.class)
    private String password = "";
	
	/**
	 * Attribute that represents the value of the input password of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.confirmPassword.notempty}")
    @Size(min=7, max=30, groups=ThenCheckIt.class)
    private String confirmPassword = "";
		
	/**
	 * Attribute that represents the value of the input email of the user in the form. 
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.valid.user.email.notempty}")
    @Size(min=3, max=254, groups=ThenCheckIt.class)
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
	 * @return the value of the attribute {@link #idUserMonitoriza}.
	 */
	public void setIdUserMonitoriza(Long idUserMonitoriza) {
		this.idUserMonitoriza = idUserMonitoriza;
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
	 * @param login the value for the attribute {@link #login} to set.
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @param password the value for the attribute {@link #password} to set.
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Gets the value of the attribute {@link #surnames}.
	 * @return the value of the attribute {@link #surnames}.
	 */
	public String getSurnames() {
		return surnames;
	}
	
	/**
	 * Sets the value of the attribute {@link #surnames}.
	 * @param name the value for the attribute {@link #surnames} to set.
	 */
	public void setSurnames(String surnames) {
		this.surnames = surnames;
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
	 * @param name the value for the attribute {@link #email} to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
		
	
}
