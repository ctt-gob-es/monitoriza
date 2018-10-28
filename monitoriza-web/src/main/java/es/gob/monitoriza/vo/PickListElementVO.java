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
 * <b>File:</b><p>es.gob.monitoriza.vo.PickListElementVO.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 jun. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 28/10/2018.
 */
package es.gob.monitoriza.vo;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents an element of the picklist view component.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 28/10/2018.
 */
public class PickListElementVO {
	
	/**
	 * Attribute that represents the picklist element identifier. 
	 */
	private String id;
	
	/**
	 * Attribute that represents the picklist element text. 
	 */
	private String text;
	
	/**
	 * Constructor method for the class PickListElement.java. 
	 */
	public PickListElementVO() {
		
	}
				
	/**
	 * Constructor method for the class PickListElement.java.
	 * @param idParam Identifier of the picklist element
	 * @param textParam textParam of the picklist element
	 */
	public PickListElementVO(String idParam, String textParam) {
		super();
		this.id = idParam;
		this.text = textParam;
	}

	/**
	 * Gets the value of the attribute {@link #id}.
	 * @return the value of the attribute {@link #id}.
	 */
	@JsonView(PickListVO.View.class)
	public String getId() {
		return id;
	}

	
	/**
	 * Sets the value of the attribute {@link #id}.
	 * @param idParam The value for the attribute {@link #id}.
	 */
	public void setId(String idParam) {
		this.id = idParam;
	}

	/**
	 * Gets the value of the attribute {@link #text}.
	 * @return the value of the attribute {@link #text}.
	 */
	@JsonView(PickListVO.View.class)
	public String getText() {
		return text;
	}

	
	/**
	 * Sets the value of the attribute {@link #text}.
	 * @param textParam The value for the attribute {@link #text}.
	 */
	public void setText(String textParam) {
		this.text = textParam;
	}
		
}
