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
 * <b>Date:</b><p>30/01/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.persistence.configuration.staticconfig;

import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.persistence.configuration.dto.ConnectionDTO;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;


/** 
 * <p>Class that manages the configuration of the @firma/ts@ connections from static properties file.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 25/01/2019.
 */
public class StaticConnectionManager implements ConnectionManager {
		
	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ConnectionManager#getAfirmaConnection()
	 */
	
	public ConnectionDTO getAfirmaConnection() {
		
		final ConnectionDTO aFirmaConnection = new ConnectionDTO();
		
		aFirmaConnection.setSecureMode(Boolean.valueOf(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_CONNECTION_SECURE_MODE)));
		aFirmaConnection.setHost(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_CONNECTION_HOST));
		aFirmaConnection.setPort(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_CONNECTION_PORT));
		aFirmaConnection.setSecurePort(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_HTTPS_PORT));
		aFirmaConnection.setServiceContext(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_CONNECTION_SERVICE_PATH));
		aFirmaConnection.setOcspContext(StaticMonitorizaConfig.getProperty(StaticConstants.AFIRMA_CONNECTION_OCSP_PATH));
		
		return aFirmaConnection;
	}

	/**
	 * {@inheritDoc}
	 * @see es.gob.monitoriza.configuration.ConnectionManager#getTsaConnection()
	 */
	
	public ConnectionDTO getTsaConnection() {

		final ConnectionDTO tsaConnection = new ConnectionDTO();
		
		tsaConnection.setSecureMode(Boolean.valueOf(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_SECURE_MODE)));
		tsaConnection.setHost(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_HOST));
		tsaConnection.setPort(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_PORT));
		tsaConnection.setSecurePort(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_HTTPS_PORT));
		tsaConnection.setRfc3161Port(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_RFC3161_PORT));
		tsaConnection.setServiceContext(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_SERVICE_PATH));
		tsaConnection.setRfc3161Context(StaticMonitorizaConfig.getProperty(StaticConstants.TSA_CONNECTION_RFC3161_CONTEXT));
		
		return tsaConnection;
	}

}
