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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.utils.IStatusAdapter.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>14/03/2019.</p>
 * @author Gobierno de España.
 * @version 1.0, 14/03/2019.
 */
package es.gob.monitoriza.persistence.configuration.model.utils;

import es.gob.monitoriza.constant.ServiceStatusConstants;
import es.gob.monitoriza.enums.SemaphoreEnum;

/** 
 * <p>Interface with utility methods for adapt between status String values and Sepmahore Integer values.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 14/03/2019.
 */
public interface IStatusAdapter {
	
	
	/**
	 * Method that convert VIP String status to Integer semaphore status. 
	 * @param vipStatus String that represents the VIP status:
	 * 		- Correcto
	 * 		- Caído
	 * 		- Degradado
	 * @return Integer that represents the semaphore status value:
	 * 		- 0 (GREEN)
	 * 		- 1 (AMBER)
	 * 		- 2 (RED)
	 */
	static Integer vipToSemaphoreStatus(String vipStatus) {
		Integer semaphore = null;
		switch (vipStatus) {
    		case ServiceStatusConstants.CORRECTO:
    			semaphore = SemaphoreEnum.GREEN.getId();
    			break;
    		case ServiceStatusConstants.CAIDO:
    			semaphore = SemaphoreEnum.RED.getId();
    			break;
    		case ServiceStatusConstants.DEGRADADO:
    			semaphore = SemaphoreEnum.AMBER.getId();
    			break;
    		default:
    			semaphore = SemaphoreEnum.RED.getId();
		}
		return semaphore;
	}

}
