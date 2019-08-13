package es.gob.monitoriza.spring.config;

import org.springframework.context.annotation.Configuration;

/**
 * <p>Class that store the information of the connected log consumer service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 17/05/2019.
 */
@Configuration
public class LogConsumerConnectionInfo {

	/**
	 * Id of the current log consumer service.
	 */
	private Long serviceId;

	/**
	 * Name of the current log consumer service.
	 */
	private String serviceName;

	/**
	 * Type of the current log consumer service.
	 */
	private String serviceType;

	/**
	 * Description of the current log consumer service.
	 */
	private String serviceDesc;

	/**
	 * Name of the current openned log file.
	 */
	private String filename;

	public void setServerInfo(final Long id, final String name, final String type, final String desc) {
		this.serviceId = id;
		this.serviceName = name;
		this.serviceType = type;
		this.serviceDesc = desc;
	}

	public Long getServiceId() {
		return this.serviceId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public String getServiceDesc() {
		return this.serviceDesc;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}
}
