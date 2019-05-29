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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.PlatformMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>PLATFORM_MONITORIZA</i> database table as a Plain Old Java Object.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 05/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import es.gob.monitoriza.constant.NumberConstants;

/**
 * <p>Class that maps the <i>SPL_MONITORIZA</i> database table as a Plain Old Java Object.</p>
 * <b>Project:</b><p>Application for monitoring services of SPLs.</p>
 * @version 1.0, 14/03/2019.
 */
@Entity
@Table(name = "SPL_MONITORIZA")
public class SplMonitoriza implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = -4397315342661952388L;

	/**
	 * Constant attribute that represents the string <i>"yes_no"</i>.
	 */
	private static final String CONS_YES_NO = "yes_no";

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idSpl;

	/**
	 * Attribute that represents the name of the SPL.
	 */
	private String name;

	/**
	 * Attribute that represents the SPL's description.
	 */
	private String description;

	/**
	 * Attribute that represents the SPL's type.
	 */
	private String type;

	/**
	 * Attribute that represents the URL to connect to the SPL.
	 */
	private String url;

	/**
	 * Attribute that represents the AES key base 64 encoded to connect to the SPL.
	 */
	private String key;

	/**
	 * Gets the value of the attribute {@link #idSpl}.
	 * @return the value of the attribute {@link #idSpl}.
	 */
	@Id
	@Column(name = "ID_SPL", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_spl")
	@GenericGenerator(
	                  name = "sq_spl",
	                  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
	                  parameters = {
	                          @Parameter(name = "sequence_name", value = "SQ_SPL_MONITORIZA"),
	                          @Parameter(name = "initial_value", value = "1"),
	                          @Parameter(name = "increment_size", value = "1")
	                  }
	          )
	@JsonView(DataTablesOutput.View.class)
	public Long getIdSpl() {
		return this.idSpl;
	}

	/**
	 * Sets the value of the attribute {@link #idSpl}.
	 * @param idSpl The value for the attribute {@link #idSpl}.
	 */
	public void setIdSpl(final Long idSpl) {
		this.idSpl = idSpl;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false, length = NumberConstants.NUM30, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;
	}


	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param name The value for the attribute {@link #name}.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #description}.
	 * @return the value of the attribute {@link #description}.
	 */
	@Column(name = "DESCRIPTION", nullable = false, length = NumberConstants.NUM255, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getDescription() {
		return this.description;
	}


	/**
	 * Sets the value of the attribute {@link #description}.
	 * @param description The value for the attribute {@link #description}.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #serviceContext}.
	 * @return the value of the attribute {@link #serviceContext}.
	 */
	@Column(name = "TYPE", nullable = false, length = NumberConstants.NUM30, unique = true)
	@JsonView(DataTablesOutput.View.class)
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 * @param type The value for the attribute {@link #type}.
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Gets the value of the attribute {@link #url}.
	 * @return the value of the attribute {@link #url}.
	 */
	@Column(name = "URL", nullable = false, length = NumberConstants.NUM255)
	@JsonView(DataTablesOutput.View.class)
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the value of the attribute {@link #url}.
	 * @param url The value for the attribute {@link #url}.
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Gets the value of the attribute {@link #key}.
	 * @return the value of the attribute {@link #key}.
	 */
	@Column(name = "KEY", nullable = false, length = NumberConstants.NUM150)
	@JsonView(DataTablesOutput.View.class)
	public String getKey() {
		return this.key;
	}

	/**
	 * Sets the value of the attribute {@link #key}.
	 * @param key The value for the attribute {@link #key}.
	 */
	public void setKey(final String key) {
		this.key = key;
	}
}
