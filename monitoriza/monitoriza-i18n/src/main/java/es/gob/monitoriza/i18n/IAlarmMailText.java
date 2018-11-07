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
 * <b>File:</b><p>es.gob.monitoriza.i18n.IAlarmMailText.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>08/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 08/10/2018.
 */
package es.gob.monitoriza.i18n;


/** 
 * <p>Interface that contains the keys to the mail texts for alarm service.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 08/10/2018.
 */
public interface IAlarmMailText {
	
	/**
	 * Constant attribute that represents the name of the property <code>subjectMailMonitoriza</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String SUBJECT_MAIL_MONITORIZA = "subjectMailMonitoriza";
	
	/**
	 * Constant attribute that represents the name of the property <code>bodyMailAlarmDegraded</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String BODY_MAIL_ALARM_DEGRADED = "bodyMailAlarmDegraded";
	
	/**
	 * Constant attribute that represents the name of the property <code>bodyMailAlarmLost</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String BODY_MAIL_ALARM_LOST = "bodyMailAlarmLost" ;

	/**
	 * Constant attribute that represents the name of the property <code>summaryAlarmSubjectMail</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String SUMMARY_ALARM_SUBJECT_MAIL = "summaryAlarmSubjectMail";
	
	/**
	 * Constant attribute that represents the name of the property <code>bodyMailSummaryRowDegraded</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String BODY_MAIL_SUMMARY_ROW_DEGRADED = "bodyMailSummaryRowDegraded";
	
	/**
	 * Constant attribute that represents the name of the property <code>bodyMailSummaryRowLost</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String BODY_MAIL_SUMMARY_ROW_LOST = "bodyMailSummaryRowLost";	

	/**
	 * Constant attribute that represents the name of the property <code>bodyMailSummary</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String BODY_MAIL_SUMMARY = "bodyMailSummary";
	
	/**
	 * Constant attribute that represents the name of the property <code>summaryAlarmSent</code> belonging to the file status/monitoriza_xx_YY.properties.
	 */
	String SUMMARY_ALARM_SENT = "summaryAlarmSent";

}
