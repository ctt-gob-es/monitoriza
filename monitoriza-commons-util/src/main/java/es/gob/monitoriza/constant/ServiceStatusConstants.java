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
 * <b>File:</b><p>es.gob.monitoriza.ServiceStatus.java.</p>
 * <b>Description:</b><p> Interface with the possible statuses for the services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>22/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.0, 12/01/2018.
 */
package es.gob.monitoriza.constant;

/** 
 * <p>Interface with the possible statuses for the services.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 16/01/2018.
 */
public interface ServiceStatusConstants {
	
	/**
	 * Attribute that represents the value for the CORRECTO status. 
	 */
	String CORRECTO = "Correcto";
	
	/**
	 * Attribute that represents the value for the DEGRADADO status. 
	 */
	String DEGRADADO = "Degradado";
	
	/**
	 * Attribute that represents the value for the CAIDO status. 
	 */
	String CAIDO = "Caído";

}
