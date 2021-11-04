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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.AlertTypeMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_TYPES</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.eventmanager.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_TYPES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
@Entity
@Table(name = "ALERT_TYPES")
public class AlertTypeMonitoriza implements Serializable {

	private static final long serialVersionUID = 1955681895543358686L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTypeMonitoriza;

	/**
	 * Attribute that represents the alert type name.
	 */
	private String name;

	/**
	 * Attribute that represents the alert type description.
	 */
	private String description;

	/**
	 * Attribute that represents the relations with applications and resumes.
	 */
    private Set<AlertResumeType> alertResumeTypes;
    
    /**
     * Attribute that represents . 
     */
    private List<TemplateMonitoriza> listTemplates;
   
	/**
	 * Gets the value of the attribute {@link #idTypeMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idTypeMonitoriza}.
	 */
	@Id
	@Column(name = "TYPE_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_alert_type_monitoriza")
	@GenericGenerator(name = "sq_alert_type_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_TYPES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdTypeMonitoriza() {
		return this.idTypeMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #idTypeMonitoriza}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #idTypeMonitoriza}.
	 */
	public void setIdTypeMonitoriza(final Long typeId) {
		this.idTypeMonitoriza = typeId;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false)
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
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the value of the attribute {@link #description}.
	 *
	 * @param name
	 *            The value for the attribute {@link #description}.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertTypeMonitoriza", cascade = CascadeType.ALL)
	public Set<AlertResumeType> getAlertResumeTypes() {
		return this.alertResumeTypes;
	}

	public void setAlertResumeTypes(final Set<AlertResumeType> alertResumeTypes) {
		this.alertResumeTypes = alertResumeTypes;
	}

	/**
	 * Gets the value of the attribute {@link #listTemplates}.
	 *
	 * @return the value of the attribute {@link #listTemplates}.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ALERT_TEMPLATE_TYPES", joinColumns = { @JoinColumn(name = "TYPE_ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "TEMPLATE_ID", nullable = false) })
	public final List<TemplateMonitoriza> getListTemplates() {
		return listTemplates;
	}

	/**
	 * Sets the value of the attribute {@link #listTemplates}.
	 *
	 * @param listTemplates
	 *            The value for the attribute {@link #listTemplates}.
	 */
	public final void setListTemplates(List<TemplateMonitoriza> listTemplates) {
		this.listTemplates = listTemplates;
	}	

}
