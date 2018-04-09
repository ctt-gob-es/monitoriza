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
 * <b>File:</b><p>es.gob.monitoriza.persistence.configuration.model.entity.PlatformAfirma.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9 abr. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 9 abr. 2018.
 */
package es.gob.monitoriza.persistence.configuration.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 9 abr. 2018.
 */
@Entity
@Table(name = "PLATFORM_AFIRMA")
public class PlatformAfirma implements Serializable {

	/**
	 * Attribute that represents . 
	 */
	private static final long serialVersionUID = -5686817672445961068L;
	
	/**
	 * Attribute that represents the object ID.
	 */
	private Long idPlatformAfirma;
		
	/**
	 * Attribute that represents . 
	 */
	private String name;
	
	/**
	 * Attribute that represents . 
	 */
	private String host;
	
	/**
	 * Attribute that represents . 
	 */
	private String port;
	
	/**
	 * Attribute that represents . 
	 */
	private String serviceContext;
	
	/**
	 * Attribute that represents . 
	 */
	private String ocspContext;
	
	
}
