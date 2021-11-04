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
 * <b>File:</b><p>es.gob.eventmanager.notifier.thread.TimeLimitedThread.java.</p>
 * <b>Description:</b><p>Class that execute in a independent thread a operation in
 * a limit time.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.notifier.thread;

/**
 * <p>Class that execute in a independent thread a operation in
 * a limit time.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
public class TimeLimitedThread extends Thread {

	/**
	 * Attribute that represents the user class that contains the operation to realize.
	 */
	private ATimeLimitedOperation tlo = null;

	/**
	 * Constructor method for the class TimeLimitedThread.java.
	 */
	private TimeLimitedThread() {

	}

	/**
	 * Constructor method for the class TimeLimitedThread.java.
	 * @param tloParam User class that contains the operation to realize.
	 */
	public TimeLimitedThread(ATimeLimitedOperation tloParam) {

		this();
		this.tlo = tloParam;

	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		try {
			tlo.doOperationThread();
			tlo.setFinishedOperation();
		} catch (InterruptedException e) {

		} catch (Exception e) {
			tlo.setException(e);
			tlo.setFinishedOperation();
		}

	}

}
