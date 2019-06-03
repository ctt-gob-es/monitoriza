package es.gob.monitoriza.rest.services;

/**
 * <p>Enum with the posible results of the register a log node by rest service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 03/06/2019.
 */
public enum LogRestServiceStatus {


	/**
	 * Constant attribute that represents the value for the result status service
	 * when the node registration is successful and the log node is created.
	 */
 	STATUS_NODE_REGISTER_CREATED(0),

	/**
	 * Constant attribute that represents the value for the result status service
	 * when the lo node already was registered.
	 */
 	STATUS_NODE_WAS_REGISTERED(1),

	/**
	 * Constant attribute that represents the value for the general result status service
	 * when there is an error checking the input parameters.
	 */
	STATUS_ERROR_INPUT_PARAMETERS(2),

	/**
	 * Constant attribute that represents the value for the general result status service
	 * when there is an error executing the service.
	 */
	STATUS_ERROR_EXECUTING_SERVICE(3);

	/** Status Id. */
	private final int id;

	/**
	 * Constructor method for the enum LogRestServiceStatus.
	 * @param id Status id.
	 */
	private LogRestServiceStatus(final int id) {
		this.id = id;
	}

	/**
	 * Get the status Id.
	 * @return Status Id.
	 */
	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return Integer.toString(this.id);
	}
}
