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

/**
 * <p>
 * Class that represents the backing form for adding/editing an alert system configuration.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 *
 */
public class GrayLogConfigDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private Long idAlertSystem;

	/**
	 * Attribute that represents the keys for GrayLog
	 */
	private String[] keysList;

	/**
	 * Attribute that represents the values for GrayLog
	 */
	private String[] valuesList;

	public Long getIdAlertSystem() {
		return this.idAlertSystem;
	}

	public void setIdAlertSystem(final Long idAlertSystem) {
		this.idAlertSystem = idAlertSystem;
	}

	public String[] getKeysList() {
		return this.keysList;
	}

	public void setKeysList(final String[] keysList) {
		this.keysList = keysList;
	}

	public String[] getValuesList() {
		return this.valuesList;
	}

	public void setValuesList(final String[] valuesList) {
		this.valuesList = valuesList;
	}
}
