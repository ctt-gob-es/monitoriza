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
 * <b>File:</b><p>es.gob.eventmanager.notifier.graylog.GraylogConfigDTO.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * <b>Date:</b><p>04/11/2021.</p>
 * @author Gobierno de España.
 * @version 1.0, 04/11/2021.
 */
package es.gob.eventmanager.notifier.graylog;

import java.util.Map;

import es.gob.eventmanager.notifier.NotifierConfigDTO;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Servicio para la notificaci&oacute;n de eventos</p>
 * @version 1.0, 04/11/2021.
 */
public class GrayLogConfigDTO implements NotifierConfigDTO {
	
	/**
	 * Attribute that represents Graylog host. 
	 */
	private String host;
	
	/**
	 * Attribute that represents Graylog port. 
	 */
	private int port;
	
	/**
	 * Attribute that represents additional configuration parameters (key/value). 
	 */
	private Map<String, String> grayLogDeclaredFields;

	
	public final String getHost() {
		return host;
	}

	
	public final void setHost(String host) {
		this.host = host;
	}

	
	public final int getPort() {
		return port;
	}

	
	public final void setPort(int port) {
		this.port = port;
	}


	
	public final Map<String, String> getGrayLogDeclaredFields() {
		return grayLogDeclaredFields;
	}


	
	public final void setGrayLogDeclaredFields(Map<String, String> grayLogDeclaredFields) {
		this.grayLogDeclaredFields = grayLogDeclaredFields;
	}

}
