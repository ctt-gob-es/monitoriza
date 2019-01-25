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
 * <b>File:</b><p>es.gob.monitoriza.constant.GrayLogErrorCodes.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>23/01/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 23/01/2019.
 */
package es.gob.monitoriza.constant;


/** 
 * <p>Interface for Graylog's error codes.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 23/01/2019.
 */
public interface GrayLogErrorCodes {
	
	/**
	 * Constant attribute that represents the GrayLog's error code for "servicio caído" alarms.
	 */
	public static final String ALARM_SERVICE_LOST = "0001";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for "servicio degradado" alarms.
	 */
	public static final String ALARM_SERVICE_DEGRADED = "0002";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for SPIE errors.
	 */
	public static final String ALARM_SPIE_ERROR = "0003";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for VIP status persistence.
	 */
	public static final String ERROR_STATUS_VIP_SAVE = "0004";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for SPIE status persistence.
	 */
	public static final String ERROR_STATUS_SPIE_SAVE = "0005";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for dumping data to VIP statistics.
	 */
	public static final String ERROR_STATISTICS_VIP_DUMP = "0006";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for dumping data to SPIE statistics.
	 */
	public static final String ERROR_STATISTICS_SPIE_DUMP = "0007";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for .
	 */
	public static final String ERROR_VALIDATION_SERVICE_TASK = "1001";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for .
	 */
	public static final String ERROR_VALIDATION_SERVICE_CONFIG = "1002";
	
	/**
	 * Constant attribute that represents the GrayLog's error code for .
	 */
	public static final String ERROR_MAIL_SERVER_CONFIG = "1003";
	

}
