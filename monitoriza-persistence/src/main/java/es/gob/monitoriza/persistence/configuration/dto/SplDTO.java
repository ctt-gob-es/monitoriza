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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AfirmaDTO.java.</p>
 * <b>Description:</b><p>Class that represents the backing form for adding/editing a user.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/03/2018.</p>
 * @author Gobierno de España.
 * @version 1.3, 30/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;
import es.gob.monitoriza.persistence.configuration.dto.validation.ThenCheckIt;
import es.gob.monitoriza.utilidades.UtilsStringChar;

/**
 * <p>Class that represents the transfer object and backing form for a SPL.</p>
 * <b>Project:</b><p>Application for management of SPLs.</p>
 * @version 1.0, 14/03/2019.
 */
public class SplDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idSpl;

	/**
	 * Attribute that represents the value of the input name of the SPL in the form.
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.log.spl.name.pattern}")
    @Size(min=NumberConstants.NUM1, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String name = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input description of the SPL in the form.
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.log.spl.description.pattern}")
    @Size(min=NumberConstants.NUM3, max=NumberConstants.NUM255, groups=ThenCheckIt.class)
    private String description = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input type of the SPL in the form.
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.log.spl.type.pattern}")
    @Size(min=NumberConstants.NUM3, max=NumberConstants.NUM30, groups=ThenCheckIt.class)
    private String type = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input URL of the SPL in the form.
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.log.spl.url.pattern}")
    @Size(min=NumberConstants.NUM3, max=NumberConstants.NUM255, groups=ThenCheckIt.class)
    private String url = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input key of the SPL in the form.
	 */
	@NotBlank(groups=CheckItFirst.class, message="{form.log.spl.key.pattern}")
    @Size(min=NumberConstants.NUM3, max=NumberConstants.NUM100, groups=ThenCheckIt.class)
    private String key = UtilsStringChar.EMPTY_STRING;

	/**
	 * Attribute that represents the value of the input isSecure of the SPL in the form.
	 */
	@NotNull(groups=CheckItFirst.class, message="{form.log.spl.secure.notnull}")
    private Boolean isSecure = Boolean.TRUE;

	/**
	 * Gets the value of the attribute {@link #idSpl}.
	 * @return the value of the attribute {@link #idSpl}.
	 */
	public Long getIdSpl() {
		return this.idSpl;
	}

	/**
	 * Sets the value of the attribute {@link #idSpl}.
	 * @param idSpl SPL identifier.
	 */
	public void setIdSpl(final Long idSpl) {
		this.idSpl = idSpl;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param name the value for the attribute {@link #name} to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 * @return the value of the attribute {@link #description}.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 * @param description the value for the attribute {@link #description} to set.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #type}.
	 * @return the value of the attribute {@link #type}.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 * @param type the value for the attribute {@link #type} to set.
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Gets the value of the attribute {@link #url}.
	 * @return the value of the attribute {@link #url}.
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the value of the attribute {@link #url}.
	 * @param url the value for the attribute {@link #url} to set.
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Gets the value of the attribute {@link #url}.
	 * @return the value of the attribute {@link #url}.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Sets the value of the attribute {@link #key}.
	 * @param key the value for the attribute {@link #key} to set.
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return this.isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecure the value for the attribute {@link #isSecure} to set.
	 */
	public void setIsSecure(final Boolean isSecure) {
		this.isSecure = isSecure;
	}
}
