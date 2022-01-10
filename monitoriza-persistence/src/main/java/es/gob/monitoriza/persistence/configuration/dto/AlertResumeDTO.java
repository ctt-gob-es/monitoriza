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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.AlertResumeDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.gob.monitoriza.persistence.configuration.model.entity.ResumeMonitoriza;
import es.gob.monitoriza.utilidades.UtilsFecha;

/** 
 * <p>Class that represents a data structure for presenting the data related to alerts in a {@link ResumeMonitoriza} being sent.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
public class AlertResumeDTO implements Comparable<AlertResumeDTO> {
	
	/**
	 * Attribute that represents the format of the {@link #fecha} field. 
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UtilsFecha.FORMATO_FECHA_HORA);
	
	/**
	 * Attribute that represents timestamp in which the alert occurred. 
	 */
	private LocalDateTime fecha;
	
	/**
	 * Attribute that represents the severity of the alert. 
	 */
	private String severidad;
	
	/**
	 * Attribute that represents the type of the alert. 
	 */
	private String tipo;
	
	/**
	 * Attribute that represents the message text of the alert. 
	 */
	private String mensaje;
	
	
	/**
	 * Constructor method for the class AlertResumeDTO.java.
	 * @param fecha
	 * @param severidad
	 * @param tipo
	 * @param mensaje 
	 */
	public AlertResumeDTO(String fecha, String severidad, String tipo, String mensaje) {
		super();
		
		this.fecha = LocalDateTime.parse(fecha, formatter);
		this.severidad = severidad;
		this.tipo = tipo;
		this.mensaje = mensaje;
	}

	/**
	 * Gets the value of the attribute {@link fecha}.
	 * @return the value of the attribute {@link #fecha}.
	 */	
	public final LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Sets the value of the attribute {@link #fecha}.
	 * @param fecha {@link LocalDateTime} of the alert.
	 */
	public final void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * Gets the value of the attribute {@link #severidad}.
	 * @return the value of the attribute {@link #severidad}.
	 */	
	public final String getSeveridad() {
		return severidad;
	}

	/**
	 * Sets the value of the attribute {@link #severidad}.
	 * @param severidad severity of the alert
	 */
	public final void setSeveridad(String severidad) {
		this.severidad = severidad;
	}

	/**
	 * Gets the value of the attribute {@link #tipo}.
	 * @return the value of the attribute {@link #tipo}.
	 */	
	public final String getTipo() {
		return tipo;
	}

	/**
	 * Sets the value of the attribute {@link #tipo}.
	 * @param tipo type of the alert
	 */
	public final void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the value of the attribute {@link #mensaje}.
	 * @return the value of the attribute {@link #mensaje}.
	 */	
	public final String getMensaje() {
		return mensaje;
	}

	/**
	 * Sets the value of the attribute {@link #mensaje}.
	 * @param mensaje message text of the alert
	 */
	public final void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Gets the value of the attribute {@link #formatter}.
	 * @return the value of the attribute {@link #formatter}.
	 */		
	public final DateTimeFormatter getFormatter() {
		return formatter;
	}
	
	/**
	 * Sets the value of the attribute {@link #formatter}.
	 * @param formatter date-time format of the {@link #fecha} field.
	 */
	public final void setFormatter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AlertResumeDTO other) {
		
		return this.fecha.compareTo(other.getFecha());
	}
	
}
