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
 * <b>File:</b><p>es.gob.monitoriza.task.VipStatisticsTask.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>10/12/2018.</p>
 * @author Gobierno de España.
 * @version 1.6, 25/01/2019.
 */
package es.gob.monitoriza.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.GrayLogErrorCodes;
import es.gob.monitoriza.i18n.IStatusLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.exception.DatabaseException;
import es.gob.monitoriza.service.IDailySpieMonitoringService;
import es.gob.monitoriza.utilidades.UtilsGrayLog;

/** 
 * <p>Class that define the tasks related to SPIE statistics.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.6, 25/01/2019.
 */
@Component
public class SpieStatisticsTask {
	
	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
		
	/**
	 * Attribute that represents the injected daily vip monitoring service. 
	 */
	@Autowired
	private IDailySpieMonitoringService dailyService;
	
	/**
	 * Group and dumps the data stored in the SPIE daily monitoring into the SPIE statistics table.
	 * Then deletes older monitoring data.
	 */
	public void dumpAndDeleteMonitoringData() {
		
		try {
			dailyService.dumpAndDeleteSpieMonitoringData();
		} catch (DatabaseException e) {
			String msg = Language.getResMonitoriza(IStatusLogMessages.ERRORSTATUS021);
			LOGGER.error(msg, e);
			UtilsGrayLog.writeMessageInGrayLog(UtilsGrayLog.LEVEL_ERROR, GrayLogErrorCodes.ERROR_STATISTICS_SPIE_DUMP, msg);
		}
	}

}
