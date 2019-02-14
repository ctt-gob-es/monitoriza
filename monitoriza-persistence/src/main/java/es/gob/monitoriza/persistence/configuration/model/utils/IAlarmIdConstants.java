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
 * <b>File:</b><p>es.gob.valet.persistence.configuration.model.utils.IAlarmIdConstants.java.</p>
 * <b>Description:</b><p>Interface that contains all the IDs of the alarms.</p>
 * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * <b>Date:</b><p>30/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.utils;

/**
 * <p>Interface that contains all the IDs of the alarms.</p>
 * <b>Project:</b><p>Platform for detection and validation of certificates recognized in European TSL.</p>
 * @version 1.0, 30/01/2019.
 */
public interface IAlarmIdConstants {

	/**
	 * Constant attribute that represents the ID for the Alarm 001: Nodo {0} de @firma sin conexión con TS@.
	 */
	String ALM001_AFIRMA_NO_TSA_CONNECTION = "ALM_001";

	/**
	 * Constant attribute that represents the ID for the Alarm 002: Nodo {0} de @firma sin conexión con HSM.
	 */
	String ALM002_AFIRMA_NO_HSM_CONNECTION = "ALM_002";

	/**
	 * Constant attribute that represents the ID for the Alarm 003: Nodo {0} de @firma con método de validación en estado no correcto.
	 */
	String ALM003_ERROR_AFIRMA_VALIDATION_SERVICE = "ALM_003";

	/**
	 * Constant attribute that represents the ID for the Alarm 004: Nodo {0} de @firma con número de transacciones con tiempos de respuesta por encima del máximo que superan el umbral del {1}.
	 */
	String ALM004_AFIRMA_TRANS_ABOVE_MAX = "ALM_004";

	/**
	 * Constant attribute that represents the ID for the Alarm 005: Nodo {0} de @firma se encuentra en modo de emergencia.
	 */
	String ALM005_AFIRMA_EMERGENCY_MODE = "ALM_005";
	
	/**
	 * Constant attribute that represents the ID for the Alarm 006: Nodo {0} de TS@ sin conexión con HSM.
	 */
	String ALM006_TSA_NO_HSM_CONNECTION = "ALM_006";
	
	/**
	 * Constant attribute that represents the ID for the Alarm 007: Nodo {0} de TS@ sin conexión con @firma.
	 */
	String ALM007TSA_NO_AFIRMA_CONNECTION = "ALM_007";
	
	/**
	 * Constant attribute that represents the ID for the Alarm 008: Nodo {0} de TS@ en modo de emergencia.
	 */
	String ALM008_TSA_EMERGENCY_MODE = "ALM_008";
	
	/**
	 * Constant attribute that represents the ID for the Alarm 009: Sin acceso al nodo SPIE {0} , con direcci\u00f3n: {1}.
	 */
	String ALM009_ERROR_NODE_CONNECTION = "ALM_009";

}
