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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.security.KeyStore;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.gob.log.consumer.client.DownloadedLogFile;
import es.gob.log.consumer.client.LogConsumerClient;
import es.gob.log.consumer.client.LogData;
import es.gob.log.consumer.client.LogError;
import es.gob.log.consumer.client.LogInfo;
import es.gob.log.consumer.client.LogResult;
import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.i18n.IWebLogMessages;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.log.LogErrors;
import es.gob.monitoriza.persistence.configuration.dto.DownloadedLogFileDTO;
import es.gob.monitoriza.persistence.configuration.dto.LogDataDTO;
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

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents the injected interface that provides operation
	 * to check logs.
	 */
	@Autowired
	private LogConsumerClient logConsumerBean;


	@Override
	public void setSslTrustStore(final KeyStore trustStore) {
		this.logConsumerBean.setTrustStore(trustStore);
	}

	@Override
	public void connect(final String url, final String key) throws IOException {
		this.logConsumerBean.init(url, key);
	}

	@Override
	public void closeConnection() {
		this.logConsumerBean.closeConnection();
	}

	@Override
	public boolean echo(final String urlTex) {

		final String result = this.logConsumerBean.echo(urlTex);

		final String ok = "\"Code\":200";
		if (result.indexOf(ok) == -1) {
			LOGGER.warn("No se pudo conectar con el servicio: " + result);
			return false;
		}

		return true;
	}

	@Override
	public LogFilesDTO getLogFiles() {

		LogFilesDTO logFiles = new LogFilesDTO();

		final Type listType = new TypeToken<LogFilesDTO>() { /* EmptyBlock */ }.getType();
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
	public LogFileInfoDTO openLogFile(final String logFilename) {

		final LogFileInfoDTO logFileInfo = new LogFileInfoDTO();

		final LogInfo logInfo = this.logConsumerBean.openFile(logFilename);

		if (logInfo.getError() != null) {
			final String errorMsg = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB026);
			logFileInfo.setError(errorMsg);
		}
		else {
			logFileInfo.setFilename(logFilename);
			logFileInfo.setCharset(logInfo.getCharset());
			logFileInfo.setDate(logInfo.isDate());
			logFileInfo.setTime(logInfo.isTime());
			logFileInfo.setDateTimeFormat(logInfo.getDateTimeFormat());
			logFileInfo.setLevels(logInfo.getLevels());
		}
		return logFileInfo;
	}

	@Override
	public void closeLogFile() {
		// No hacemos nada con el resultado. Si  recogemos el resultado, ya que
		final LogResult result = this.logConsumerBean.closeFile();
		if (result.getError() != null) {
			LOGGER.warn("No se ha podido cerrar el fichero de log abierto: " + result.getError());
		}
	}

	@Override
	public DownloadedLogFileDTO downloadLogFile(final String logFilename) {

		final DownloadedLogFileDTO logFileInfo = new DownloadedLogFileDTO();

		final String tempDir = System.getProperty("java.io.tmpdir");
		final DownloadedLogFile downloadedFile = this.logConsumerBean.download(logFilename, tempDir);

		if (downloadedFile.getError() != null) {
			final String errorMsg = Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB027);
			logFileInfo.setError(errorMsg);
		}
		else {
			final File logFile = new File(downloadedFile.getPath());
			try {
				logFileInfo.setData(Files.readAllBytes(logFile.toPath()));
				logFileInfo.setFilename(logFile.getName());
				logFileInfo.setContentType("application/zip");
			} catch (final IOException e) {
				LOGGER.error("No se pudo leer el fichero de log almacenado en el directorio temporal", e);
				logFileInfo.setError(Language.getResWebMonitoriza(IWebLogMessages.ERRORWEB028));
			}
		}

		return logFileInfo;
	}

	@Override
	public LogDataDTO lastLines(final String logName, final int numLines) {

		final LogData logData = this.logConsumerBean.getLogTail(numLines, logName);

		final LogDataDTO log = new LogDataDTO();
		if (logData.getError() != null) {
			if (LogError.EC_NO_MORE_LINES.equals(logData.getError().getCode())) {
				log.setErrorCode(LogErrors.NO_MORE_LINES.getCode());
				log.setErrorMessage(LogErrors.NO_MORE_LINES.getMessage());
			}
			else {
				LOGGER.warn("Error desconocido al solicitar las ultimas lineas: " + logData.getError().getMessage());
				log.setErrorMessage(LogErrors.UNKNOWN_ERROR.getMessage());
			}
		}
		else {
			log.setLog(logData.getLog());
			log.setCharset(logData.getCharset());
		}

		return log;
	}

	@Override
	public LogDataDTO filterLines(final int numLines, final long startDate, final long endDate,
			final String level, final boolean more) {

		final LogData logData = this.logConsumerBean.getLogFiltered(numLines, startDate, endDate, level, !more);

		final LogDataDTO log = new LogDataDTO();
		if (logData.getError() != null) {
			if (LogError.EC_NO_MORE_LINES.equals(logData.getError().getCode())) {
				log.setErrorCode(LogErrors.NO_MORE_LINES.getCode());
				log.setErrorMessage(LogErrors.NO_MORE_LINES.getMessage());
			}
			else {
				LOGGER.warn("Error desconocido al filtar lineas: " + logData.getError().getMessage());
				log.setErrorCode(LogErrors.UNKNOWN_ERROR.getCode());
				log.setErrorMessage(LogErrors.UNKNOWN_ERROR.getMessage());
			}
		}
		else {
			log.setLog(logData.getLog());
			log.setCharset(logData.getCharset());
		}

		return log;
	}

	@Override
	public LogDataDTO searchText(final int numLines, final String text, final long startDate, final boolean more) {

		final LogData logData = this.logConsumerBean.searchText(numLines, text, startDate, !more);

		final LogDataDTO log = new LogDataDTO();
		if (logData.getError() != null) {
			if (LogError.EC_NO_MORE_LINES.equals(logData.getError().getCode())) {
				log.setErrorCode(LogErrors.NO_MORE_LINES.getCode());
				log.setErrorMessage(LogErrors.NO_MORE_LINES.getMessage());
			}
			else {
				LOGGER.warn("Error desconocido al buscar un texto: " + logData.getError().getMessage());
				log.setErrorCode(LogErrors.UNKNOWN_ERROR.getCode());
				log.setErrorMessage(LogErrors.UNKNOWN_ERROR.getMessage());
			}
		}
		else {
			log.setLog(logData.getLog());
			log.setCharset(logData.getCharset());
		}
		return log;
	}

	@Override
	public LogDataDTO getMore(final int numLines) {

		final LogData logData = this.logConsumerBean.getMoreLog(numLines);

		final LogDataDTO log = new LogDataDTO();
		if (logData.getError() != null) {
			if (LogError.EC_NO_MORE_LINES.equals(logData.getError().getCode())) {
				log.setErrorCode(LogErrors.NO_MORE_LINES.getCode());
				log.setErrorMessage(LogErrors.NO_MORE_LINES.getMessage());
			}
			else {
				LOGGER.warn("Error desconocido al solicitar mas lineas: " + logData.getError().getMessage());
				log.setErrorMessage(LogErrors.UNKNOWN_ERROR.getMessage());
			}
		}
		else {
			log.setLog(logData.getLog());
			log.setCharset(logData.getCharset());
		}

		return log;
	}


}
