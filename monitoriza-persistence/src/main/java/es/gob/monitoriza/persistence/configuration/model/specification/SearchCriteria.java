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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.specification.SearchCriteria.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>24 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 24 abr. 2018.
 */
package es.gob.monitoriza.persistence.configuration.model.specification;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 24 abr. 2018.
 */
public class SearchCriteria {
	
    /**
     * Attribute that represents . 
     */
    private String key;
    
    /**
     * Attribute that represents . 
     */
    private String operation;
    
    /**
     * Attribute that represents . 
     */
    private Object value;
    	
	/**
	 * Constructor method for the class SearchCriteria.java.
	 * @param key
	 * @param operation
	 * @param value 
	 */
	public SearchCriteria(String key, String operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}


	public String getKey() {
		return key;
	}

	
	public void setKey(String key) {
		this.key = key;
	}

	
	public String getOperation() {
		return operation;
	}

	
	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public Object getValue() {
		return value;
	}

	
	public void setValue(Object value) {
		this.value = value;
	}
    
}
