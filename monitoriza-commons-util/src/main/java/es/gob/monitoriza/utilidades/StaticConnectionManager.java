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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.StaticConnectionManager.java.</p>
 * <b>Description:</b>
 * <p>Class that manages the configuration of the @firma/ts@ connections from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems</p>
 * <b>Date:</b><p>30 ene. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 ene. 2018.
 */
package es.gob.monitoriza.utilidades;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.persistence.configuration.dto.DTOConnection;


/** 
 * <p>Class that manages the configuration of the @firma/ts@ connections from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 ene. 2018.
 */
public class StaticConnectionManager {
		
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ConnectionManager#getAfirmaConnection()
	 */
	
	public DTOConnection getAfirmaConnection() {
		
		final DTOConnection aFirmaConnection = new DTOConnection();
		
		aFirmaConnection.setSecureMode(Boolean.valueOf(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP);
		aFirmaConnection.setHost(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_HOST));
		aFirmaConnection.setPort(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_PORT));
		aFirmaConnection.setServiceContext(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_SERVICE_PATH));
		aFirmaConnection.setOcspContext(StaticMonitorizaProperties.getProperty(StaticConstants.AFIRMA_CONNECTION_OCSP_PATH));
		
		return aFirmaConnection;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ConnectionManager#getTsaConnection()
	 */
	
	public DTOConnection getTsaConnection() {

		final DTOConnection tsaConnection = new DTOConnection();
		
		tsaConnection.setSecureMode(Boolean.valueOf(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_SECURE_MODE)) ? GeneralConstants.SECUREMODE_HTTPS : GeneralConstants.SECUREMODE_HTTP);
		tsaConnection.setHost(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_HOST));
		tsaConnection.setPort(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_PORT));
		tsaConnection.setRfc3161Port(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_RFC3161_PORT));
		tsaConnection.setServiceContext(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_SERVICE_PATH));
		tsaConnection.setRfc3161Context(StaticMonitorizaProperties.getProperty(StaticConstants.TSA_CONNECTION_RFC3161_CONTEXT));
		
		return tsaConnection;
	}

}
