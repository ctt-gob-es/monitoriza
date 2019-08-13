package es.gob.monitoriza.log;

/**
 * <p>Enum to define the errors what you can get to search logs.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/05/2019.
 */
public enum LogErrors {

	/** Error desconocido o no catalogado. */
	UNKNOWN_ERROR(500, "log.search.error.unknown"),
	/** Cuando la consulta no proporciona m&aacute;s l&iacute;neas. */
	NO_MORE_LINES(204, "log.search.error.noMoreLines");

	/** Codigo de error. */
	private int code;

	/** Clave del mensaje de error. */
	private String message;


	LogErrors(final int code, final String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * Get the LogErrors with a code.
	 * @param code Code of the error.
	 * @return Error info.
	 */
	public static LogErrors parse(final int code) {
		for (final LogErrors err : values()) {
			if (err.getCode() == code) {
				return err;
			}
		}
		return UNKNOWN_ERROR;
	}
}
