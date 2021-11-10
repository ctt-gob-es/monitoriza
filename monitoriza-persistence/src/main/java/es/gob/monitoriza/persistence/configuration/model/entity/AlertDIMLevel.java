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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlarmMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALARM_MONITORIZA</i> database table as a Plain Old Java Object</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * <p>
 * Class that maps the <i>ALERT_DIM_LEVELS</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_DIM_LEVELS")
public class AlertDIMLevel implements Serializable {

	private static final long serialVersionUID = -9169481834787361583L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long levelID;

	/**
	 * Attribute that represents the level name.
	 */
	private String name;

	/**
	 * Attribute that represents the alert statistics.
	 */
	private Set<AlertStatistic> alertStatistics;

	/**
	 * Gets the value of the attribute {@link #levelID}.
	 *
	 * @return the value of the attribute {@link #levelID}.
	 */
	@Id
	@Column(name = "LEVEL_ID", unique = true, nullable = false)
	public Long getLevelID() {
		return this.levelID;
	}

	/**
	 * Sets the value of the attribute {@link #levelID}.
	 *
	 * @param levelID
	 *            The value for the attribute {@link #levelID}.
	 */
	public void setLevelID(final Long levelID) {
		this.levelID = levelID;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "LEVEL_NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 *
	 * @param name
	 *            The value for the attribute {@link #name}.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #alertStatistics}.
	 * @return the value of the attribute {@link #alertStatistics}.
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertDIMNode", cascade = CascadeType.ALL)
	public Set<AlertStatistic> getAlertConfigSystems() {
		return this.alertStatistics;
	}

	/**
	 * Sets the value of the attribute {@link #alertStatistics}.
	 * @param alertStatistics The value for the attribute {@link #alertStatistics}.
	 */
	public void setAlertConfigSystems(final Set<AlertStatistic> alertStatistics) {
		this.alertStatistics = alertStatistics;
	}
}
