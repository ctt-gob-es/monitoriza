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
 * <b>File:</b><p>es.gob.monitoriza.exception.IMonitorizaException.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>13/08/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 13/08/2019.
 */
package es.gob.monitoriza.exception;


/** 
 * <p>Interface defining constants codes accepted for the exceptions on the platform.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 13/08/2019.
 */
public interface IMonitorizaException {
	
	/** Constant attribute that represents the error code: Unknown host. */
	String COD_193 = "COD_193";

	/** Constant attribute that represents the error code: Network connection timeout. */
	String COD_194 = "COD_194";

	/** Constant attribute that represents the error code: Connection refused. */
	String COD_195 = "COD_195";

	/** Constant attribute that represents the error code: Connection no available.	*/
	String COD_196 = "COD_196";

	/** Constant attribute that represents the error code: Not found. */
	String COD_197 = "COD_197";

	/** Constant attribute that represents the error code: Generic error. */
	String COD_198 = "COD_198";

}
