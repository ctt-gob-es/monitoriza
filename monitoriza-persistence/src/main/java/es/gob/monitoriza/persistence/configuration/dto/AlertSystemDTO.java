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
 * @version 1.2, 10/01/2022.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.io.Serializable;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that represents the backing form for adding/editing an alert system.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 */
public class AlertSystemDTO implements Serializable{

	private static final long serialVersionUID = -3349913988947911986L;

	/**
	 * Attribute that represents the value of the primary key as a hidden input in the form.
	 */
	private Long idAlertSystemMonitoriza;

	/**
	 * Attribute that represents the name of the alert system
	 */
	private String name;

	/**
	 * Attribute that represents the type of the alert system
	 */
	private Long type;

	/**
	 * Attribute that represents the host of the alert system for graylog systems
	 */
	private String host;

	/**
	 * Attribute that represents the port of the alert system for graylog systems
	 */
	private Long port;

	/**
	 * Attribute that represents the emails configurated for the resumes
	 */
	private String resumeEmailAddresses;

	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertSystemMonitoriza() {
		return this.idAlertSystemMonitoriza;
	}

	public void setIdAlertSystemMonitoriza(final Long idAlertSystemMonitoriza) {
		this.idAlertSystemMonitoriza = idAlertSystemMonitoriza;
	}

	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(final Long type) {
		this.type = type;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public Long getPort() {
		return this.port;
	}

	public void setPort(final Long port) {
		this.port = port;
	}

	@JsonView(DataTablesOutput.View.class)
	public String getResumeEmailAddresses() {
		return this.resumeEmailAddresses;
	}

	public void setResumeEmailAddresses(final String emailAdresses) {
		this.resumeEmailAddresses = emailAdresses;
	}

}
