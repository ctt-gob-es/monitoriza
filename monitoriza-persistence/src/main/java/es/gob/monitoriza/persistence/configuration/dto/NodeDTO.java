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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.dto.NodeDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>15/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.persistence.configuration.dto;

import es.gob.monitoriza.constant.GeneralConstants;

/** 
 * <p>Class that represents the backing form for adding/editing a SPIE node.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 28/10/2018.
 */
public class NodeDTO {
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idNode;

	/**
	 * Attribute that represents the name of the node. 
	 */
	private String name;
	
	/**
	 * Attribute that represents the host string for connections to the node. 
	 */
	private String host;
	
	/**
	 * Attribute that represents the port string for connections to the node. 
	 */
	private String port;
	
	/**
	 * Attribute that represents the type of platform. 
	 */
	private Long type;
	
	/**
	 * Attribute that indicates if the SPIE for emergency database connection will be checked.
	 */
	private Boolean checkEmergencyDB;
	
	/**
	 * Attribute that indicates if the SPIE for TS@ connection will be checked.
	 */
	private Boolean checkTsa;
	
	/**
	 * Attribute that indicates if the SPIE for @Firma connection will be checked.
	 */
	private Boolean checkAfirma;
	
	/**
	 * Attribute that indicates if the SPIE for HSM connection will be checked.
	 */
	private Boolean checkHsm;
	
	/**
	 * Attribute that indicates if the SPIE for service response times will be checked.
	 */
	private Boolean checkServices;
	
	/**
	 * Attribute that indicates if the SPIE for validation methods connection will be checked.
	 */
	private Boolean checkValidMethod;
	
	/**
	 * Attribute that indicates whether the access to the node is through secured connection (https).
	 */
	private Boolean isSecure;
	
	/**
	 * Gets the value of the attribute {@link #idNode}.
	 * @return the value of the attribute {@link #idNode}.
	 */
	public Long getIdNode() {
		return idNode;
	}
	
	/**
	 * Sets the value of the attribute {@link #idNode}.
	 * @param idNodeParam The value for the attribute {@link #idNode}.
	 */
	public void setIdNode(final Long idNodeParam) {
		this.idNode = idNodeParam;
	}

	/**
	 * Gets the value of the attribute {@link #name}.
	 * @return the value of the attribute {@link #name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the attribute {@link #name}.
	 * @param nameParam The value for the attribute {@link #name}.
	 */
	public void setName(String nameParam) {
		this.name = nameParam;
	}

	/**
	 * Gets the value of the attribute {@link #host}.
	 * @return the value of the attribute {@link #host}.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the value of the attribute {@link #host}.
	 * @param hostParam The value for the attribute {@link #host}.
	 */
	public void setHost(String hostParam) {
		this.host = hostParam;
	}

	/**
	 * Gets the value of the attribute {@link #port}.
	 * @return the value of the attribute {@link #port}.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the value of the attribute {@link #port}.
	 * @param portParam The value for the attribute {@link #port}.
	 */
	public void setPort(String portParam) {
		this.port = portParam;
	}

	/**
	 * Gets the value of the attribute {@link #type}.
	 * @return the value of the attribute {@link #type}.
	 */
	public Long getType() {
		return type;
	}

	/**
	 * Sets the value of the attribute {@link #type}.
	 * @param typeParam The value for the attribute {@link #type}.
	 */
	public void setType(Long typeParam) {
		this.type = typeParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkEmergencyDB}.
	 * @return the value of the attribute {@link #checkEmergencyDB}.
	 */
	public Boolean getCheckEmergencyDB() {
		return checkEmergencyDB;
	}

	/**
	 * Sets the value of the attribute {@link #checkEmergencyDB}.
	 * @param checkEmergencyDBParam The value for the attribute {@link #checkEmergencyDB}.
	 */
	public void setCheckEmergencyDB(Boolean checkEmergencyDBParam) {
		this.checkEmergencyDB = checkEmergencyDBParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkTsa}.
	 * @return the value of the attribute {@link #checkTsa}.
	 */
	public Boolean getCheckTsa() {
		return checkTsa;
	}

	/**
	 * Sets the value of the attribute {@link #checkTsa}.
	 * @param checkTsaParam The value for the attribute {@link #checkTsa}.
	 */
	public void setCheckTsa(Boolean checkTsaParam) {
		this.checkTsa = checkTsaParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkAfirma}.
	 * @return the value of the attribute {@link #checkAfirma}.
	 */
	public Boolean getCheckAfirma() {
		return checkAfirma;
	}

	/**
	 * Sets the value of the attribute {@link #checkAfirma}.
	 * @param checkAfirmaParam The value for the attribute {@link #checkAfirma}.
	 */
	public void setCheckAfirma(Boolean checkAfirmaParam) {
		this.checkAfirma = checkAfirmaParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkHsm}.
	 * @return the value of the attribute {@link #checkHsm}.
	 */
	public Boolean getCheckHsm() {
		return checkHsm;
	}

	/**
	 * Sets the value of the attribute {@link #checkHsm}.
	 * @param checkHsmParam The value for the attribute {@link #checkHsm}.
	 */
	public void setCheckHsm(Boolean checkHsmParam) {
		this.checkHsm = checkHsmParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkServices}.
	 * @return the value of the attribute {@link #checkServices}.
	 */
	public Boolean getCheckServices() {
		return checkServices;
	}

	/**
	 * Sets the value of the attribute {@link #checkServices}.
	 * @param checkServicesParam The value for the attribute {@link #checkServices}.
	 */
	public void setCheckServices(Boolean checkServicesParam) {
		this.checkServices = checkServicesParam;
	}

	/**
	 * Gets the value of the attribute {@link #checkValidMethod}.
	 * @return the value of the attribute {@link #checkValidMethod}.
	 */
	public Boolean getCheckValidMethod() {
		return checkValidMethod;
	}

	/**
	 * Sets the value of the attribute {@link #checkValidMethod}.
	 * @param checkValidMethodParam The value for the attribute {@link #checkValidMethod}.
	 */
	public void setCheckValidMethod(Boolean checkValidMethodParam) {
		this.checkValidMethod = checkValidMethodParam;
	}

	/**
	 * Gets the value of the attribute {@link #isSecure}.
	 * @return the value of the attribute {@link #isSecure}.
	 */
	public Boolean getIsSecure() {
		return isSecure;
	}

	/**
	 * Sets the value of the attribute {@link #isSecure}.
	 * @param isSecureParam The value for the attribute {@link #isSecure}.
	 */
	public void setIsSecure(Boolean isSecureParam) {
		this.isSecure = isSecureParam;
	}

	/**
	 * Method that builds the String that represents the connection address to the node ([http/http]://host:port).
	 * @return String that represents the connection address to the node
	 */
	public String getAddress() {
		
		StringBuffer url = new StringBuffer();
				
		if (isSecure) {
			url.append(GeneralConstants.SECUREMODE_HTTPS);
		} else {
			url.append(GeneralConstants.SECUREMODE_HTTP);
		}
		
		url.append(host);
		
		if (port != null) {
			url.append(port);
		}
		
		return url.toString();
	}
	
	
}
