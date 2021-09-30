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

import java.util.List;

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
public class SummaryDTO {

	/**
	 * Attribute that represents the value of the primary key as a hidden input
	 * in the form.
	 */
	private String name;

	private boolean active;

	private String periodicity;

	private List notificationsSystemList;

	private List alarmsList;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public String getPeriodicity() {
		return this.periodicity;
	}

	public void setPeriodicity(final String periodicity) {
		this.periodicity = periodicity;
	}

	public List getNotificationsSystemList() {
		return this.notificationsSystemList;
	}

	public void setNotificationsSystemList(final List notificationsSystemList) {
		this.notificationsSystemList = notificationsSystemList;
	}

	public List getAlarmsList() {
		return this.alarmsList;
	}

	public void setAlarmsList(final List alarmsList) {
		this.alarmsList = alarmsList;
	}

}
