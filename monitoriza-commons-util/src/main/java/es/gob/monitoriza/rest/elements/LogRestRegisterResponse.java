package es.gob.monitoriza.rest.elements;

import java.time.LocalDateTime;

import es.gob.monitoriza.rest.services.LogRestServiceStatus;

/**
 * <p>Class with the log node registration restful service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 03/06/2019.
 */
public class LogRestRegisterResponse {

	/**
	 * Operations result.
	 */
	private LogRestServiceStatus status;

	/**
	 * Legible result operation.
	 */
	private String description;

	/**
	 * Time of the generated response.
	 */
	private LocalDateTime responseTime;

	public LogRestServiceStatus getStatus() {
		return this.status;
	}

	public void setStatus(final LogRestServiceStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDateTime getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(final LocalDateTime responseTime) {
		this.responseTime = responseTime;
	}
}
