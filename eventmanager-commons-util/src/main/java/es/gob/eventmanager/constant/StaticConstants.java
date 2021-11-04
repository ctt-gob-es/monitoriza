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
 * <b>File:</b><p>es.gob.monitoriza.constant.StaticConstants.java.</p>
 * <b>Description:</b>
 * <p>Interface that contains the static constants for the static configuration.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 04/01/2019.
 */
package es.gob.eventmanager.constant;

/** 
 * <p>Interface that contains the static constants for the static configuration.</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.6, 04/01/2019.
 */
public interface StaticConstants {
	
		
	/**
	 * Attribute that represents the Padding algorithm for the AES cipher.
	 */
	public static final String AES_PADDING_ALG = "aes.padding.alg";

	/**
	 * Attribute that represents the AES algorithm name.
	 */
	public static final String AES_ALGORITHM = "aes.algorithm";

	/**
	 * Attribute that represents the password for the system keystores.
	 */
	public static final String AES_PASSWORD = "aes.password";
	
	/**
	 * Constant attribute that represents the property key jboss.server.config.dir. 
	 */
	public static final String PROP_SERVER_CONFIG_DIR = "server.config.path";
	
	
}
