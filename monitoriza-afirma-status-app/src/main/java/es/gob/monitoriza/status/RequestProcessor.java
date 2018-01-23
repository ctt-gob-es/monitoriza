/* 
* Este fichero forma parte de la plataforma de @firma. 
* La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
* y descargado desde http://forja-ctt.administracionelectronica.gob.es
*
* Copyright 2018 Gobierno de España
*/

/**
 * <b>File:</b>
 * <p>
 * es.gob.monitoriza.status.RequestProcessor.java.
 * </p>
 * <b>Description:</b>
 * <p>
 * Class that gets the average response times for @firma services.
 * </p>
 * <b>Project:</b>
 * <p>
 * Application for monitoring the services of @firma suite systems.
 * </p>
 * <b>Date:</b>
 * <p>
 * 21/12/2017.
 * </p>
 * 
 * @author Gobierno de España.
 * @version 1.0, 15/01/2018.
 */
package es.gob.monitoriza.status;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.dto.DTOService;
import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.i18.Language;
import es.gob.monitoriza.i18.LogMessages;
import es.gob.monitoriza.invoker.soap.HttpSoapInvoker;
import es.gob.monitoriza.util.StaticMonitorizaProperties;

/** 
 * <p>Class that gets the average response times for @firma services.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.0, 15/01/2018.
 */
public final class RequestProcessor {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);

	/**
	 * Attribute that represents a instance of this class. 
	 */
	private static RequestProcessor instance;

	/**
	 * Attribute that represents if the process is running. 
	 */
	private static boolean isRunning;

	/**
	 * Attribute that represents the path where the pairs are stored.
	 */
	private static String requestDirectory = StaticMonitorizaProperties.getProperty(GeneralConstants.ROOT_PATH_DIRECTORY);

	/**
	 * Private constructor method for the class RequestProcessor.java. 
	 */
	private RequestProcessor() {

	}

	/**
	 * Gets an instance of the class.
	 * @return	A {@link RequestProcessor} object.
	 */
	public static RequestProcessor getInstance() {

		if (instance == null) {
			instance = new RequestProcessor();
		}

		return instance;
	}

	/**
	 * Method that performs the invocation of service by service name.
	 * 
	 * @param rootPath initial path where is store every petition.
	 * @param responsesDir Path where the responses will be stored.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	public static void startInvoker(Map<String, String> statusHolder, List<DTOService> servicios) throws InvokerException {

		// Recorremos los subdirectorios que separan las peticiones por
		// servicio.
		for (DTOService s: servicios) {

			sendSetRequests(s, statusHolder);
		}
	}

	/**
	 * Method that search every request in the given directory, send it and store the response.
	 * 
	 * @param file Directory where search requests.
	 * @param responsesDir Directory where store the responses.
	 * @throws InvokerException if the path is not correct or the directories structure is not correct.
	 */
	private static void sendSetRequests(DTOService service, Map<String, String> statusHolder) throws InvokerException {

		LOGGER.info(Language.getFormatResMonitoriza(LogMessages.PATH_DIRECTORY_REQUESTS, new Object[ ] { requestDirectory }));
		File file = new File(requestDirectory);
		// Comprobamos que la ruta proporcioanda es correcta.
		if (!file.isDirectory()) {
			throw new InvokerException(Language.getFormatResMonitoriza(LogMessages.ERROR_BAD_DIRECTORIES_STRUCTURE, new Object[ ] { file.getAbsolutePath() }));
		}

		// Si el subdirectorio no es una carpeta, lanzamos un error ya que
		// la ruta proporcionada no es correcta.
		File serviceDir = new File(file.getPath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(service.getServiceId()));
		// Si existe un directorio con el nombre del servicio, continuo...
		if (serviceDir != null && serviceDir.exists() && serviceDir.isDirectory()) {

			LOGGER.info(Language.getFormatResMonitoriza(LogMessages.STARTING_TO_SEND_STORED_REQUESTS, new Object[ ] { serviceDir.toPath().toString() }));

			// Enviamos las peticiones del grupo principal
			File grupoPrincipal = new File(serviceDir.getAbsolutePath().concat(GeneralConstants.DOUBLE_PATH_SEPARATOR).concat(StaticMonitorizaProperties.getProperty(GeneralConstants.GRUPO_PRINCIPAL_PATH_DIRECTORY)));

			if (grupoPrincipal != null && grupoPrincipal.exists() && grupoPrincipal.listFiles() != null) {

				for (File request: grupoPrincipal.listFiles()) {
					// Vamos recorriendo los ficheros, y si es una petición,
					// la enviamos @Firma
					if (request != null) {

						LOGGER.info(Language.getFormatResMonitoriza(LogMessages.SENDING_REQUEST, new Object[ ] { request.getName() }));
						String response = HttpSoapInvoker.sendRequest(request, service, statusHolder);

					}
				}

			}

		}
	}

}
