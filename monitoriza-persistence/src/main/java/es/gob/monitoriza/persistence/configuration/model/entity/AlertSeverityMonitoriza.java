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
 * Class that maps the <i>ALERT_SEVERITY</i> database table as a Plain Old Java Object.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring services of @firma suite systems.
 * </p>
 */
@Entity
@Table(name = "ALERT_SEVERITY")
public class AlertSeverityMonitoriza implements Serializable {

	private static final long serialVersionUID = -8267084543031731102L;

	/**
	 * Attribute that represents the id
	 */
	private Long severityTypeId;

	/**
	 * Attribute that represents if the name
	 */
	private String name;

	/**
	 * Attribute that represents if the alerts configuration for this severity
	 */
	private Set<AlertConfigMonitoriza> alertsConfigMonitoriza;

	/**
	 * Gets the value of the attribute {@link #severityTypeId}.
	 *
	 * @return the value of the attribute {@link #severityTypeId}.
	 */
	@Id
	@Column(name = "ID_SEVERITY_TYPE", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public Long getSeverityTypeId() {
		return this.severityTypeId;
	}

	/**
	 * Sets the value of the attribute {@link #severityTypeId}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #severityTypeId}.
	 */
	public void setSeverityTypeId(final Long severityTypeId) {
		this.severityTypeId = severityTypeId;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 *
	 * @return the value of the attribute {@link #name}.
	 */
	@Column(name = "NAME", nullable = false)
	@JsonView(DataTablesOutput.View.class)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 *
	 * @param templateId
	 *            The value for the attribute {@link #name}.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the value of the attribute {@link #alertsConfigMonitoriza}.
	 *
	 * @return the value of the attribute {@link #alertsConfigMonitoriza}.
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alertSeverityMonitoriza", cascade = CascadeType.ALL)
	public Set<AlertConfigMonitoriza> getAlertsConfigMonitoriza() {
		return this.alertsConfigMonitoriza;
	}

	/**
	 * Sets the value of the attribute {@link #alertsConfigMonitoriza}.
	 *
	 * @param alertsConfigMonitoriza
	 *            The value for the attribute {@link #alertsConfigMonitoriza}.
	 */
	public void setAlertsConfigMonitoriza(final Set<AlertConfigMonitoriza> alertsConfigMonitoriza) {
		this.alertsConfigMonitoriza = alertsConfigMonitoriza;
	}


}
