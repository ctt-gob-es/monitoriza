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
 * <b>File:</b><p>es.gob.monitoriza.i18n.ITaskResumeMailText.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/01/2022.</p>
 * @author Gobierno de España.
 * @version 1.0, 10/01/2022.
 */
package es.gob.monitoriza.i18n;


/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 10/01/2022.
 */
public interface ITaskResumeMailText {
	
	/**
	 * Attribute that represents the resume mail subject. 
	 */
	public static String RESUME_SUBJECT = "task.resume.subject";
	
	/**
	 * Attribute that represents the resume mail header. 
	 */
	public static String RESUME_HEADER = "task.resume.header";
	
	/**
	 * Attribute that represents the resume mail header. 
	 */
	public static String RESUME_HEADER_APP = "task.resume.app.header";
	
	/**
	 * Attribute that represents the resume mail header. 
	 */
	public static String RESUME_ALERT_SEVERITY_COUNT = "task.resume.severity.count";
	
	/**
	 * Attribute that represents the resume mail header. 
	 */
	public static String RESUME_ALERT_BRAKDOWN_HEADER = "task.resume.alert.header";
	
	/**
	 * Attribute that represents . 
	 */
	public static String RESUME_ALERT_BREAKDOWN_DETAIL = "task.resume.alert.detail";

}
