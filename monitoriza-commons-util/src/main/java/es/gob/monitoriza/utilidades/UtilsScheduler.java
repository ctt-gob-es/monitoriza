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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.UtilsScheduler.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>13/10/2020.</p>
 * @author Gobierno de España.
 * @version 1.0, 17/08/2021
 */
package es.gob.monitoriza.utilidades;

import java.io.InputStream;

import org.springframework.scheduling.support.CronSequenceGenerator;


/** 
 * <p>Class that provides methods to manage operations with scheduler.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 17/08/2021.
 */
public class UtilsScheduler {
	
	/**
	 * Constructor method for the class UtilsScheduler.java. 
	 */
	private UtilsScheduler() {
		super();
	}

	/**
	 * Method that handles the closing of a {@link InputStream} resource.
	 * @param expression Parameter that represents expression cron to validate.
	 * @return true if it is a valid expression
	 */
	public static Boolean validExpression(String expression) {
		boolean result = false;
		if (expression != null) {
			if(CronSequenceGenerator.isValidExpression(expression)){
				result = true;
			}
		}
		return result;
	}

}
