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
 * <b>File:</b><p>es.gob.monitoriza.exception.MonitorizaRestException.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>12/02/2019.</p>
 * @author Gobierno de España.
 * @version 1.2, 11/05/2022.
 */
package es.gob.monitoriza.exception;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.utilidades.UtilsXml;

/** 
 * <p>Class that manages exceptions produced by the rest service module.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.2, 11/05/2022.
 */
public class MonitorizaRestException extends Exception {

	/**
	 * Attribute that represents the serial version id. 
	 */
	private static final long serialVersionUID = -8731837393927855067L;
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(MonitorizaRestException.class);
	
	/**
	 * Attribute that represents a code associated to the error.
	 */
	private String errorCode;
	
	/**
	 * Attribute that represents a description associated to the error.
	 */
	private String errorDesc;

	/**
	 * Attribute that represents a java exception associated to the error. It is optional.
	 */
	private Exception exception;
	
	/**
	 * Constructor method for the class MonitorizaRestException.java.
	 */
	public MonitorizaRestException() {
		super();
	}

	/**
	 * Constructor method for the class MonitorizaRestException.java with the specified detail message.
	 * The cause is not initialized.
	 *
	 * @param message the detail message.
	 */
	public MonitorizaRestException(final String message) {
		super(message);
		errorDesc = message;
	}

	/**
	 * Constructor method for the class MonitorizaRestException.java.
	 * @param errorDescParam Description for the error.
	 * @param exceptionParam Exception that causes the error.
	 */
	public MonitorizaRestException(final String errorDescParam, final Exception exceptionParam) {
		super(errorDescParam);
		errorDesc = errorDescParam;
		exception = exceptionParam;
		LOGGER.warn(errorDescParam);
	}
	
	/**
	 * Constructor method for the class MonitorizaRestException.java.
	 * @param errorCodeParam Code of the error.
	 * @param errorDescParam Description of the error.
	 * @param exceptionParam Exception that causes the error.
	 */
	public MonitorizaRestException(final String errorCodeParam, final String errorDescParam, final Exception exceptionParam) {
		super(errorDescParam);
		errorCode = errorCodeParam;
		errorDesc = errorDescParam;
		exception = exceptionParam;
		LOGGER.warn(errorDescParam);
	}	
	
	/**
	 * Gets the value of the attribute {@link #errorCode}.
	 * @return the value of the attribute {@link #errorCode}.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the value of the attribute {@link #errorCode}.
	 * @param errorCodeParam The value for the attribute {@link #errorCode}.
	 */
	public void setErrorCode(String errorCodeParam) {
		this.errorCode = errorCodeParam;
	}

	/**
	 * Gets the value of the attribute {@link #errorDesc}.
	 * @return the value of the attribute {@link #errorDesc}.
	 */
	public String getErrorDescription() {
		return errorDesc;
	}

	/**
	 * Sets the value of the attribute {@link #errorDesc}.
	 * @param errorDescParam The value for the attribute {@link #errorDesc}.
	 */
	public void setErrorDescription(final String errorDescParam) {
		errorDesc = UtilsXml.escapeXml10(errorDescParam);
	}

	/**
	 * Gets the value of the attribute {@link #exception}.
	 * @return the value of the attribute {@link #exception}.
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * Sets the value of the attribute {@link #exception}.
	 * @param exceptionParam The value for the attribute {@link #exception}.
	 */
	public void setException(final Exception exceptionParam) {
		exception = exceptionParam;
	}	

	/**
	 * {@inheritDoc}
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {

		if (exception == null) {
			return "DESCRIPTION: [" + errorDesc + "]";
		} else {
			return "DESCRIPTION: [" + errorDesc + "]; CAUSE: " + exception.toString();
		}

	}



}
