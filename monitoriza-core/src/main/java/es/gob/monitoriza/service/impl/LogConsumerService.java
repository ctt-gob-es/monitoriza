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
 * <b>File:</b><p>es.gob.monitoriza.service.impl.ConfSpieService.java.</p>
 * <b>Description:</b><p>Class that implements the communication with the operations of the persistence layer for ConfSpieMonitoriza.</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>16/10/2018.</p>
 * @author Gobierno de España.
 * @version 1.2, 30/01/2019.
 */
package es.gob.monitoriza.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.gob.log.consumer.client.LogConsumerClient;
import es.gob.log.consumer.client.LogInfo;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.persistence.configuration.dto.LogFileInfoDTO;
import es.gob.monitoriza.persistence.configuration.dto.LogFilesDTO;
import es.gob.monitoriza.persistence.configuration.dto.RowLogFileErrorDTO;
import es.gob.monitoriza.service.ILogConsumerService;

/**
 * <p>
 * Class that implements the communication with the operations of the persistence layer
 * for LogConsumetClient.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
  * @version 1.0, 20/03/2019.
 */
@Service("logConsumerService")
public class LogConsumerService implements ILogConsumerService {

	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the injected interface that provides operation
	 * to check logs.
	 */
	@Autowired
	private LogConsumerClient logConsumerBean;

	@Override
	public void configure(final String url, final String key) throws IOException {
		this.logConsumerBean.init(url, key);
	}

	@Override
	public LogFilesDTO getLogFiles() {

		LogFilesDTO logFiles = new LogFilesDTO();

		final Type listType = new TypeToken<LogFilesDTO>() {}.getType();
		try {
			final byte[] logFilesJson = this.logConsumerBean.getLogFiles();
			logFiles = new Gson().fromJson(new String(logFilesJson), listType);
		} catch (final JsonSyntaxException e) {
			final String errorJson = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB025);
			final RowLogFileErrorDTO errorDTO = new RowLogFileErrorDTO();
			errorDTO.setCode(400);
			errorDTO.setMessage(errorJson);
			logFiles.setError(Collections.singletonList(errorDTO));
		}

		return logFiles;
	}

	@Override
	public LogFileInfoDTO openLogFile(final String logFileName) {

		final LogFileInfoDTO logFileInfo = new LogFileInfoDTO();

		final LogInfo logInfo = this.logConsumerBean.openFile(logFileName);

		if (logInfo.getError() != null) {
			final String errorMsg = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB026);
			logFileInfo.setError(errorMsg);
		}
		else {
			logFileInfo.setCharset(logInfo.getCharset());
			logFileInfo.setDate(logInfo.isDate());
			logFileInfo.setTime(logInfo.isTime());
			logFileInfo.setDateTimeFormat(logInfo.getDateTimeFormat());
			logFileInfo.setLevels(logInfo.getLevels());
		}
		return logFileInfo;
	}
}
