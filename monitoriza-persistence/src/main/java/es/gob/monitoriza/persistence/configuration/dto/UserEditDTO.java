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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.UserEditDTO.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.dto.constraints.FieldMatch;
import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>
 * Class that represents the backing form for adding/editing a user.
 * </p><b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
  * @version 1.2, 30/01/2019.
 */
@FieldMatch(first = "password", second = "confirmPassword", message = "{form.valid.user.password.confirm}")
public class UserEditDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private Long idUserMonitorizaEdit = null;

	/**
	 * Attribute that represents the value of the input name of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.user.name.notempty}")
	@Size(min = NumberConstants.NUM1, max = NumberConstants.NUM15, groups = ThenCheckIt.class)
	private String nameEdit = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input surnames of the user in
	 * the form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.user.surnames.notempty}")
	@Size(min = NumberConstants.NUM1, max = NumberConstants.NUM30, groups = ThenCheckIt.class)
	private String surnamesEdit = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input username of the user in
	 * the form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.user.login.notempty}")
	@Size(min = NumberConstants.NUM5, max = NumberConstants.NUM30, groups = ThenCheckIt.class)
	private String loginEdit = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input email of the user in the
	 * form.
	 */
	@NotBlank(groups = CheckItFirst.class, message = "{form.valid.user.email.notempty}")
	@Size(min = NumberConstants.NUM3, max = NumberConstants.NUM254, groups = ThenCheckIt.class)
	private String emailEdit = UtilsStringChar.EMPTY_STRING;

	/**
	 * Gets the value of the attribute {@link #idUserMonitoriza}.
	 * 
	 * @return the value of the attribute {@link #idUserMonitoriza}.
	 */
	public Long getIdUserMonitorizaEdit() {
		return idUserMonitorizaEdit;
	}

	/**
	 * Gets the value of the attribute {@link #idUserMonitorizaEdit}.
	 * @param idUserMonitorizaEditParam the idUserMonitorizaEdit to set
	 */
	public void setIdUserMonitorizaEdit(Long idUserMonitorizaEditParam) {
		this.idUserMonitorizaEdit = idUserMonitorizaEditParam;
	}

	/**
	 * @return the nameEdit
	 */
	public String getNameEdit() {
		return nameEdit;
	}

	/**
	 * @param nameEditParam the nameEdit to set
	 */
	public void setNameEdit(String nameEditParam) {
		this.nameEdit = nameEditParam;
	}

	/**
	 * @return the surnamesEdit
	 */
	public String getSurnamesEdit() {
		return surnamesEdit;
	}

	/**
	 * @param surnamesEditParam the surnamesEdit to set
	 */
	public void setSurnamesEdit(String surnamesEditParam) {
		this.surnamesEdit = surnamesEditParam;
	}

	/**
	 * @return the loginEdit
	 */
	public String getLoginEdit() {
		return loginEdit;
	}

	/**
	 * @param loginEditParam the loginEdit to set
	 */
	public void setLoginEdit(String loginEditParam) {
		this.loginEdit = loginEditParam;
	}

	/**
	 * @return the emailEdit
	 */
	public String getEmailEdit() {
		return emailEdit;
	}

	/**
	 * @param emailEditParam the emailEdit to set
	 */
	public void setEmailEdit(String emailEditParam) {
		this.emailEdit = emailEditParam;
	}

	

}
