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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.AlertSystemType.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that maps the <i>ALERT_SYSTEMS_TYPES</i> database table as a Plain Old Java Object..</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
@Entity
@Table(name = "ALERT_SYSTEMS_TYPES")
public class AlertSystemType implements Serializable {

	/**
	 * Attribute that represents the serial version identifier. 
	 */
	private static final long serialVersionUID = -5402649135155255501L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idAlertSystemType;

	/**
	 * Attribute that represents the alert system type name.
	 */
	private String name;
	
	/**
	 * Attribute that indicates if the alert system type is enabled for sending 'resumes'. 
	 */
	private boolean isResumeEnabled;

	
	/**
	 * Gets the value of the attribute {@link #idAlertSystemType}.
	 *
	 * @return the value of the attribute {@link #idAlertSystemType}.
	 */
	@Id
	@Column(name = "SYSTEM_TYPE_ID", unique = true, nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getIdAlertSystemType() {
		return idAlertSystemType;
	}
	
	/**
	 * Sets the value of the attribute {@link #idAlertSystemType}.
	 * @param requestFile The value for the attribute {@link #idAlertSystemType}.
	 */
	public void setIdAlertSystemType(Long idAlertSystemType) {
		this.idAlertSystemType = idAlertSystemType;
	}
	
	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param requestFile The value for the attribute {@link #name}.
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #isResumeEnabled}.
	 *
	 * @return the value of the attribute {@link #isResumeEnabled}.
	 */
	@Column(name = "RESUME_ENABLED", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public boolean getIsResumeEnabled() {
		return isResumeEnabled;
	}

	/**
	 * Sets the value of the attribute {@link #isResumeEnabled}.
	 * @param requestFile The value for the attribute {@link #isResumeEnabled}.
	 */
	public void setIsResumeEnabled(boolean isResumeEnabled) {
		this.isResumeEnabled = isResumeEnabled;
	}
		
}
