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
 * <b>File:</b><p>es.gob.monitoriza.exception.RepositoryDeleteException.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 05/11/2018.
 */
package es.gob.monitoriza.exception;


/** 
 * <p>Class that defines the exception object for deleting a configuration element from persistence</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 05/11/2018.
 */
public class RepositoryDeleteException extends Exception {

	/**
	 * Attribute that represents the class version identifier. 
	 */
	private static final long serialVersionUID = -4533930946820460636L;
	
	/**
	 * Constructor method for the class RequestFileNotFoundException.java. 
	 */
	public RepositoryDeleteException() {
	}

	/**
	 * Constructor method for the class RequestFileNotFoundException.java.
	 * @param message 
	 */
	public RepositoryDeleteException(String message) {
		super(message);
	}

	/**
	 * Constructor method for the class RequestFileNotFoundException.java.
	 * @param cause 
	 */
	public RepositoryDeleteException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor method for the class RequestFileNotFoundException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 */
	public RepositoryDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor method for the class RequestFileNotFoundException.java.
	 * @param message String to store in the exception object.
	 * @param cause Cause of the exception.
	 * @param enableSuppression indicates if it should enable the suppression.
	 * @param writableStackTrace  indicates if it should write the stack trace.
	 */
	public RepositoryDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
