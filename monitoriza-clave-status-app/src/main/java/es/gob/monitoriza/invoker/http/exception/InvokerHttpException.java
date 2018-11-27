package es.gob.monitoriza.invoker.http.exception;

public class InvokerHttpException extends Exception {

	/**
	 * Número de serie de la clase.
	 */
	private static final long serialVersionUID = 295107306693038208L;

	/**
	 * Causa del error desconocida.
	 */
	public static final String CODE_999 = "COD_999";
	
	/**
	 * Código del error.
	 */
	protected String code = CODE_999;
	
	/**
	 * Constructor method for the class ScanDocsException.java.
	 */
	public InvokerHttpException() {
		super();
	}

	/**
	 * Constructor con argumentos.
	 *
	 * @param aCode
	 *            Código de error.
	 * @param aMessage
	 *            Mensaje del error.
	 */
	public InvokerHttpException(final String aCode, final String aMessage) {
		super(aMessage);
		this.code = aCode;
	}

	/**
	 * Constructor con argumentos.
	 *
	 * @param aMessage
	 *            Mensaje del error.
	 */
	public InvokerHttpException(final String aMessage) {
		super(aMessage);
		this.code = CODE_999;
	}

	/**
	 * Constructor con argumentos.
	 *
	 * @param aCause
	 *            Causa del error.
	 */
	public InvokerHttpException(final Throwable aCause) {
		super(aCause);
		this.code = CODE_999;
	}

	/**
	 * Constructor con argumentos.
	 *
	 * @param aMessage
	 *            Mensaje del error.
	 * @param aCause
	 *            Causa del error.
	 */
	public InvokerHttpException(final String aMessage, final Throwable aCause) {
		super(aMessage, aCause);
		this.code = CODE_999;
	}

	/**
	 * Constructor con argumentos.
	 *
	 * @param aCode
	 *            Código de error.
	 * @param aMessage
	 *            Mensaje del error.
	 * @param aCause
	 *            Causa del error.
	 */
	public InvokerHttpException(final String aCode, final String aMessage, final Throwable aCause) {
		super(aMessage, aCause);
		this.code = aCode;
	}

	/**
	 * Obtiene el código de error de la excepción.
	 *
	 * @return código de error de la excepción.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Establece el código de error de la excepción.
	 *
	 * @param aCode
	 *            código de error de la excepción.
	 */
	public void setCode(final String aCode) {
		this.code = aCode;
	}
	
}
