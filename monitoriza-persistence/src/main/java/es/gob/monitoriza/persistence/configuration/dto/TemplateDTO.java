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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AlarmDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>8/05/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import es.gob.monitoriza.persistence.configuration.dto.validation.CheckItFirst;

/**
 * <p>
 * Class that represents the backing form for adding/editing an system notification.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 * @version 1.1, 25/01/2019.
 */
public class TemplateDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idTemplateMonitoriza;

	/**
	 * Attribute that represents the name of the template
	 */
	private String template;

	/**
	 * Attribute that represents the description of the template
	 */
	private String description;

	/**
	 * Attribute that represents the concatenated mail addresses for down alarms.
	 */

	@NotNull(groups=CheckItFirst.class, message="{form.valid.keystore.file.notnull}")
	private MultipartFile file;


	

	/**
	 * @return the idTemplateMonitoriza
	 */
	public Long getIdTemplateMonitoriza() {
		return idTemplateMonitoriza;
	}

	/**
	 * @param idTemplateMonitoriza the idTemplateMonitoriza to set
	 */
	public void setIdTemplateMonitoriza(Long idTemplateMonitoriza) {
		this.idTemplateMonitoriza = idTemplateMonitoriza;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the name
	 */
	public String getTemplate() {
		return this.template;
	}

	/**
	 * @param nameParam the name to set
	 */
	public void setTemplate(final String template) {
		this.template = template;
	}

	/**
	 * @param the type
	 */
	public void getDescription(final String description) {
		this.description = description;
	}

	/**
	 * @param type the name to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the value of the attribute {@link #file}.
	 * @return the value of the attribute {@link #file}.
	 */	
	public MultipartFile getFile() {
		return file;
	}

	
	/**
	 * Sets the value of the attribute {@link #file}.
	 * @param sslCertificateParam the value for the attribute {@link #file} to set.
	 */
	public void setFile(final MultipartFile sslCertificateParam) {
		this.file = sslCertificateParam;
	}

}
