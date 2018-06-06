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
 * <b>File:</b><p>es.gob.monitoriza.form.PickListElement.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>1 jun. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 1 jun. 2018.
 */
package es.gob.monitoriza.form;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

/** 
 * <p>Class that represents an element of the picklist view component.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 1 jun. 2018.
 */
public class PickListElement {
	
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
	public PickListElement() {
		
	}
				
	/**
	 * Constructor method for the class PickListElement.java.
	 * @param id
	 * @param text 
	 */
	public PickListElement(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	/**
	 * 
	 * @return
	 */
	@JsonView(DataTablesOutput.View.class)
	public String getId() {
		return id;
	}

	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	@JsonView(DataTablesOutput.View.class)
	public String getText() {
		return text;
	}

	
	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
