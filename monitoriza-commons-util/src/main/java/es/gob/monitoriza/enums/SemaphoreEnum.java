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
 * <b>File:</b><p>es.gob.monitoriza.enums.SemaphoreEnum.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>08/11/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 04/01/2019.
 */
package es.gob.monitoriza.enums;


/** 
 * <p>Enum that represents the semaphore values .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 04/01/2019.
 */
public enum SemaphoreEnum {
	
	GREEN(0, "OK"), AMBER(1, "WARNING"), RED(2, "ERROR");

	/**
	 * Attribute that represents the enum id. 
	 */
	private final Integer id;

	/**
	 * Attribute that represents the enum name. 
	 */
	private final String name;

	/**
	 * Constructor method for the class SemaphoreEnum.java.
	 * @param id enum id
	 * @param name enum name
	 */
	private SemaphoreEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Get id.
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Get name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
}
