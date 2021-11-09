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
 * <b>File:</b><p>es.gob.eventmanager.persistence.model.entity.TemplateMonitoriza.java.</p>
 * <b>Description:</b><p>Class that maps the <i>ALERT_APP_TEMPLATES</i> database table as a Plain Old Java Object</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.1, 09/11/2021.
 */
package es.gob.eventmanager.persistence.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.gob.eventmanager.constant.NumberConstants;

/**
 * <p>
 * Class that maps the <i>ALERT_APP_TEMPLATES</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.1, 09/11/2021.
 */
@Entity
@Table(name = "ALERT_APP_TEMPLATES")
public class TemplateMonitoriza implements Serializable {

	private static final long serialVersionUID = -7313327436554167369L;

	/**
	 * Attribute that represents the object ID.
	 */
	private Long idTemplateMonitoriza;

	/**
	 * Attribute that represents the template's name.
	 */
	private String name;
	
	 /**
     * Attribute that represents . 
     */
    private List<AlertTypeMonitoriza> listAlertTypes;

	/**
	 * Gets the value of the attribute {@link #idTemplateMonitoriza}.
	 *
	 * @return the value of the attribute {@link #idTemplateMonitoriza}.
	 */
	@Id
	@Column(name = "TEMPLATE_ID", unique = true, nullable = false, precision = NumberConstants.NUM19)
	@GeneratedValue(generator = "sq_template_monitoriza")
	@GenericGenerator(name = "sq_template_monitoriza", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = { @Parameter(name = "sequence_name", value = "SQ_ALERT_APP_TEMPLATES"), @Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	public Long getIdTemplateMonitoriza() {
		return this.idTemplateMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #templateId}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #templateId}.
	 */
	public void setIdTemplateMonitoriza(final Long templateId) {
		this.idTemplateMonitoriza = templateId;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false, precision = NumberConstants.NUM19)
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
	 * Gets the value of the attribute {@link #listAlertTypes}.
	 *
	 * @return the value of the attribute {@link #listAlertTypes}.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ALERT_TEMPLATE_TYPES", joinColumns = { @JoinColumn(name = "TEMPLATE_ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "TYPE_ID", nullable = false) })
	public List<AlertTypeMonitoriza> getListAlertTypes() {
		return listAlertTypes;
	}

	/**
	 * Sets the value of the attribute {@link #listAlertTypes}.
	 *
	 * @param listAlertTypes
	 *            The value for the attribute {@link #listAlertTypes}.
	 */
	public void setListAlertTypes(List<AlertTypeMonitoriza> listAlertTypes) {
		this.listAlertTypes = listAlertTypes;
	}
}
