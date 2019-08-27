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
 * <b>File:</b><p>es.gob.monitoriza.spie.response.ValMethodsConnDTO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>22/08/2019.</p>
 * @author Gobierno de España.
 * @version 1.1, 27/08/2019.
 */
package es.gob.monitoriza.persistence.configuration.dto;

/** 
 * <p>Class that store the results of the validation methods connection tests.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 27/08/2019.
 */
public class ValMethodsConnDTO {
	
	/**
	 * Attribute that represents the identifier of the validation method. 
	 */
	private String valMethodId;
	
	/**
	 * Attribute that represents the type of the validation method. 
	 */
	private String valMethodType;
	
	/**
	 * Attribute that represents the URL of the validation method. 
	 */
	private String valMethodUrl;
	
	/**
	 * Attribute that represents the result of the validation method connection test. 
	 */
	private String result;

	/**
	 * Gets the {@link #valMethodId}.
	 * @return {@link String}.
	 */
	public String getValMethodId() {
		return valMethodId;
	}

	/**
	 * Sets the {@link #valMethodId}.
	 * @param valMethodIdParam value for {@link #valMethodId} to set
	 */
	public void setValMethodId(final String valMethodIdParam) {
		this.valMethodId = valMethodIdParam;
	}

	/**
	 * Gets the {@link #valMethodType}.
	 * @return {@link String}.
	 */
	public final String getValMethodType() {
		return valMethodType;
	}

	/**
	 * Sets the {@link #valMethodType}.
	 * @param valMethodTypeParam value for {@link #valMethodType} to set
	 */
	public final void setValMethodType(String valMethodTypeParam) {
		this.valMethodType = valMethodTypeParam;
	}

	/**
	 * Gets the {@link #valMethodUrl}.
	 * @return {@link String}.
	 */
	public final String getValMethodUrl() {
		return valMethodUrl;
	}

	/**
	 * Sets the {@link #valMethodUrl}.
	 * @param valMethodUrlParam value for {@link #valMethodUrl} to set
	 */
	public final void setValMethodUrl(String valMethodUrlParam) {
		this.valMethodUrl = valMethodUrlParam;
	}

	/**
	 * Gets the {@link #result}.
	 * @return {@link String}.
	 */
	public final String getResult() {
		return result;
	}

	/**
	 * Sets the {@link #result}.
	 * @param resultParam value for {@link #result} to set
	 */
	public final void setResult(String resultParam) {
		this.result = resultParam;
	}
	
	
}
