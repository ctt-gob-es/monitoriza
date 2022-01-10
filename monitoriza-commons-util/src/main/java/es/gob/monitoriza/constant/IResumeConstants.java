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
 * <b>File:</b><p>es.gob.monitoriza.constant.IResumeConstants.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.constant;


/** 
 * <p>Interface .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
public interface IResumeConstants {

	/**
	 * Attribute that represents the number of hours of the hourly periodicity . 
	 */
	public static Integer RESUME_PERIODICITY_HOURLY = 1;
	
	/**
	 * Attribute that represents the number of hours of the daily periodicity . 
	 */
	public static Integer RESUME_PERIODICITY_DAILY = 24;
	
	/**
	 * Attribute that represents the number of hours of the weekly periodicity . 
	 */
	public static Integer RESUME_PERIODICITY_WEEKLY = 168;
	
	/**
	 * Attribute that represents the number of hours of the monthly periodicity . 
	 */
	public static Integer RESUME_PERIODICITY_MONTHLY = 720;
	
	/**
	 * Attribute that represents the String that indicates that the resume has been never sent. 
	 */
	public static String RESUME_NEVER_SENT = "Nunca";
	
}
