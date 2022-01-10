/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://administracionelectronica.gob.es
 *
 * Copyright 2005-2019 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.eventmanager.notifier.thread.ATimeLimitedOperation.java.</p>
 * <b>Description:</b><p>Abstract class that defines a operation to execute in other
 * thread in limited time.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.utilidades.thread;

/**
 * <p>Abstract class that defines a operation to execute in other thread in limited time.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 10/01/2022.
 */
public abstract class ATimeLimitedOperation {

	/**
	 * Attribute that represents the thread where run the operation.
	 */
	private TimeLimitedThread tlt = null;

	/**
	 * Attribute that represents an exception captured while running the operation.
	 */
	private Exception excep = null;

	/**
	 * Attribute that represents if the operation has been finished.
	 */
	private boolean operationFinished = false;

	/**
	 * Attribute that represents if the execution thread must be interrupted.
	 */
	private boolean interruptThread = false;

	/**
	 * Attribute that represents the number of millisecond how the max time for running
	 * the operation.
	 */
	private int millisecondsTime = 0;

	/**
	 * Method called by the thread to set the operation how finished.
	 */
	protected final void setFinishedOperation() {
		this.operationFinished = true;
	}

	/**
	 * Method to test if the operation has been finished.
	 * @return <i>true</i> if the operation has been finished, otherwise <i>false</i>.
	 */
	public final boolean isOperationFinished() {
		return operationFinished;
	}

	/**
	 * Method that obtain the max time (in milliseconds) before stop the thread.
	 * @return Max time (milliseconds).
	 */
	public final int getMaxTimeForRunningThread() {
		return millisecondsTime;
	}

	/**
	 * Method to set the max time (in milliseconds) before stop the thread.
	 * If it is less or equal to zero, then there isn't wait time.
	 * @param maxTimeForRunningThread Number of milliseconds.
	 */
	public final void setMaxTimeForRunningThread(int maxTimeForRunningThread) {
		if (maxTimeForRunningThread > 0) {
			millisecondsTime = maxTimeForRunningThread;
		} else {
			millisecondsTime = 0;
		}
	}

	/**
	 * Method to obatin the exception captured while running the operation.
	 * @return The captured exception.
	 */
	public final Exception getException() {
		return excep;
	}

	/**
	 * Method used by the thread to set the exception captured.
	 * @param exc Exception.
	 */
	protected final void setException(Exception exc) {
		this.excep = exc;
	}

	/**
	 * Method that test if the operation finished with a exception.
	 * @return <i>true</i> if the operation finished with a exception, otherwise <i>false</i>.
	 */
	public final boolean isOperationFinishedWithException() {

		boolean result = false;

		if (operationFinished && excep != null) {
			result = true;
		}

		return result;

	}

	/**
	 * Method that start the operation in other thread, except if the
	 * time is less or equal to 0. In that case, the operation will be executed
	 * in this thread.
	 */
	public final void startOperation() {

		init();

		if (millisecondsTime > 0) {

			tlt = new TimeLimitedThread(this);
			tlt.start();
			try {
				tlt.join(millisecondsTime);
			} catch (InterruptedException e) {
				interruptThread = true;
			}
			if (interruptThread || !operationFinished) {
				stopTlt();
			}

		} else {

			try {
				doOperationThread();
			} catch (Exception e) {
				setException(e);
			} finally {
				setFinishedOperation();
			}

		}

	}

	/**
	 * Method that initializes the attributes for running clean operations.
	 */
	private void init() {

		tlt = null;
		excep = null;
		operationFinished = false;
		interruptThread = false;

	}

	/**
	 * Method that stop the running thread.
	 */
	@SuppressWarnings("deprecation")
	private void stopTlt() {

		if (tlt != null && tlt.isAlive()) {

			interruptThread = true;
			tlt.interrupt();
			
			if (tlt != null && tlt.isAlive()) {
				tlt.stop();	
			}

		}

	}

	/**
	 * Method that checks whether the thread should continue running.
	 * This method must be called how test in all loops inside the 'doOperationThread' method.
	 * @return <i>true</i> if the operation should continue running, otherwise <i>false</i>.
	 */
	protected final boolean continueRunning() {

		return !interruptThread;

	}

	/**
	 * Abstract method where must be defined the operation to realize.
	 * In the code, in all the loops, is necessary to call the method 'continueRunning'
	 * to test if it is necessary to stop the execution.
	 * @throws Exception Any exception that occurs during the execution
	 * of the operation.
	 */
	protected abstract void doOperationThread() throws Exception;

}
