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
 * <b>File:</b><p>es.gob.monitoriza.i18n.IWebViewMessages.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>05/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 04/01/2019.
 */
package es.gob.monitoriza.i18n;


/** 
 * <p>Interface that contains the keys to the messages for the web module that are set at server side.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 04/01/2019.
 */
public interface IWebViewMessages {
	
	/****************************************/
	/** Mensajes de error de semáforos VIP **/ 
	/****************************************/
	
	/**
	 * Constant attribute that represents the name of the property <code>error.vip.service.degraded</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_VIP_SERVICE_DEGRADED = "error.vip.service.degraded";
	
	/**
	 * Constant attribute that represents the name of the property <code>error.vip.service.down</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_VIP_SERVICE_DOWN = "error.vip.service.down";
	
	/****************************************/
	/** Mensajes de error de semáforos SPIE **/ 
	/****************************************/
	
	/**
	 * Constant attribute that represents the name of the property <code>error.spie.service</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_SPIE_SERVICE = "error.spie.service";
	
	/**
	 * Constant attribute that represents the name of the property <code>error.connection.service</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_CONNECTION_SPIE = "error.connection.service";
	
	/***********************/
	/** Mensajes de error **/ 
	/***********************/
		
	/**
	 * Constant attribute that represents the name of the property <code>error.auth.delete.used</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_AUTH_DELETE_USED = "error.auth.delete.used";
	
	/**
	 * Constant attribute that represents the name of the property <code>error.auth.delete.crypto</code> belonging to the file messages_xx_YY.properties.
	 */
	String ERROR_AUTH_DELETE_CRYPTO = "error.auth.delete.crypto";

}
