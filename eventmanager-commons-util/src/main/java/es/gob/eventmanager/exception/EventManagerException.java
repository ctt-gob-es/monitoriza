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
 * <b>File:</b><p>es.gob.eventmanager.exception.EventManagerException.java.</p>
 * <b>Description:</b><p> Class that defines the exception object of the Event Manager classes.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.exception;


/** 
 * <p>Class that defines the exception object of the Event Manager classes.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
public class EventManagerException extends Exception {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = 3899883933884042065L;

	/**
	 * Constructor method for the class InvokerException.java. 
	 */
	public EventManagerException() {
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message 
	 */
	public EventManagerException(String message) {
		super(message);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param cause 
	 */
	public EventManagerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 */
	public EventManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor method for the class InvokerException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 * @param enableSuppression indicates if it should enable the suppression.
	 * @param writableStackTrace  indicates if it should write the stack trace.
	 */
	public EventManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
