/*
 * Este fichero forma parte de la plataforma de @firma.
 * La plataforma de @firma es de libre distribución cuyo código fuente puede ser consultado
 * y descargado desde http://forja-ctt.administracionelectronica.gob.es
 *
 * Copyright 2017 Gobierno de España
 * Este fichero se distribuye bajo las licencias EUPL versión 1.1  y GPL versión 3, o superiores, según las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompaña.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aquí las condiciones expresadas allí.
 */

/**
 * <b>File:</b><p>es.gob.afirma.ws.keystores.KeystoreVersionFileManager.java.</p>
 * <b>Description:</b><p>Utility class for gets path for a keystore in a file system.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * <b>Date:</b><p>28/06/2018.</p>
 * @author Gobierno de España.
 * @version 1.7, 11/05/2022.
 */
package es.gob.monitoriza.keystore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.gob.monitoriza.utilidades.loggers.Logger;

import es.gob.monitoriza.constant.NumberConstants;
import es.gob.monitoriza.service.IKeystoreService;
import es.gob.monitoriza.utilidades.StaticMonitorizaConfig;

/**
 * <p>Utility class for gets path for a keystore in a file system.</p>
 * <b>Project:</b><p>Horizontal platform of validation services of multiPKI
 * certificates and electronic signature.</p>
 * @version 1.7, 11/05/2022.
 */
public final class KeystoreVersionFileManager {

	/**
	 * Constant attribute that represents the name of the directory inside of the JBoss configuration
	 * that contents the keystores extracted from the DDBB.
	 */
	private static final String KEYSTORE_DIR = "keystoresFromDDBB";

	/**
	 * Constant attribute that represents the absolute path to the keystore directory #{@link KeystoreVersionFileManager#KEYSTORE_DIR}.
	 */

	private static final String ABS_PATH_KEYSTORE_DIR = StaticMonitorizaConfig.createAbsolutePath(StaticMonitorizaConfig.getTomcatServerConfigDir(), KEYSTORE_DIR);

	/**
	 * Constant attribute that represents the string with the dot character.
	 */
	private static final String DOT = ".";

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(KeystoreVersionFileManager.class);

	/**
	 * Attribute that represents a hashtable with the relation between the identifier
	 * of a keystore and its path and version stored in the file system.
	 */
	private static Map<Long, KeystoreNameVersionStamp> keystores = new HashMap<Long, KeystoreNameVersionStamp>();

	/**
	 * Attribute that represents a flag that indicates if all temp files that reprensents the versioned keystore
	 * must be deleted to reload these.
	 */
	private static boolean flagToClearTemps = true;

	/**
	 * Attribute that represents the prefix to add in the filenames to assign for the keystores.
	 */
	private static String prefixFileName = String.valueOf(Calendar.getInstance().getTimeInMillis());

	/**
	 * Attribute that represents the service object for accessing the repository. 
	 */
	private static IKeystoreService keystoreService;

	/**
	 * Constructor method for the class KeystoreVersionFileManager.java.
	 */
	private KeystoreVersionFileManager() {
		super();
	}

	/**
	 * Method that returns the absolute path in the file system for the keystore
	 * indicated as parameter (identifier). If the keystore does not exist in the file system,
	 * or its version not is the same that the version in data base, then extrac it
	 * from the data base and store in the file system.
	 * @param keystoreId Keystore identifier at the DDBB. If it is <code>null</code> or a not recognized
	 * keystore identifier, then this method returns <code>null</code>.
	 * @return {@link String} with the absolute path to the keystore. In case of some
	 * error this method returns <code>null</code>.
	 */
	public static synchronized String getAbsolutePathForTheKeystore(Long keystoreId) {

		String result = null;

		// Comprobamos que el identificador es válido.
		if (checkIfExistsKeystore(keystoreId)) {
			// Lo primero es comprobar si hay que eliminar todos los almacenes
			// inicializados
			// hasta el momento, para forzar su recarga. Esto suele ocurrir al
			// recargar la caché.
			checkIfItIsNecessaryToClearTempsFiles();

			// Comprobamos si tenemos en memoria el path del keystore
			// solicitado, ya que
			// si no es así, hay que buscar en el sistema de ficheros.
			if (!keystores.containsKey(keystoreId)) {
				loadKeystoreFromFileSystem(keystoreId);
			}
			// Comprobamos si el que tenemos en memoria (una vez buscado en
			// ficheros) se corresponde en versión con el de memoria.
			checkIfKeystoreIsUpdatedInHashtable(keystoreId);

			// Una vez actualizado, lo recuperamos de memoria.
			KeystoreNameVersionStamp knvs = keystores.get(keystoreId);

			// Si no es nulo, obtenemos la ruta completa al fichero.
			if (knvs != null) {
				result = StaticMonitorizaConfig.createAbsolutePath(ABS_PATH_KEYSTORE_DIR, knvs.getPrefix() + KeystoreNameVersionFilter.HYPHEN + knvs.getKeystoreName() + DOT + knvs.getVersion());
			}
		}
		return result;
	}

	/**
	 * Method that checks that the keystore identifier not is null and that keystore
	 * exists in the DDBB.
	 * @param keystoreId Keystore identifier.
	 * @return <code>true</code> if the identifier not is null and exists in the DDBB,
	 * otherwise <code>false</code>.
	 */
	private static boolean checkIfExistsKeystore(Long keystoreId) {

		boolean result = false;

		if (keystoreId != null) {
			try {
				result = keystoreService.getKeystoreById(keystoreId) != null;
			} catch (Exception e) {
				LOGGER.error(keystoreId, e);
			}
		}
		return result;
	}

	/**
	 * Method that search the last version of a keystore in the file system and
	 * create its object representation and save it into the {@link #keystores}.
	 * In case of some error, this method do nothing.
	 * @param keystoreId Keystore identifier.
	 */
	private static void loadKeystoreFromFileSystem(Long keystoreId) {

		// Obtenemos el nombre del keystore de base de datos.
		String keystoreName = getNameOfKeystoreFromDDBB(keystoreId);

		// Obtenemos el listado de ficheros que comienzan por el nombre de
		// keystore obtenido.
		String[ ] keystoreFilesNames = loadListKeystoreFilesFromFileSystem(null, keystoreName);

		// Si se han encontrado nombres de ficheros, obtenemos el número de
		// versión mayor.
		if (keystoreFilesNames != null && keystoreFilesNames.length > 0) {
			// Obtenemos el prefijo que representa la marca de tiempo
			// más actual.
			String prefix = getMaxPrefix(keystoreFilesNames);
			// Obtenemos el número máximo de versión.
			Long version = getMaxVersionWithPrefix(keystoreFilesNames, prefix);
			// Creamos el objeto que representará esta información en memoria.
			KeystoreNameVersionStamp knvs = new KeystoreNameVersionStamp(prefix, keystoreName, version);
			// Lo añadimos al hashtable para almacenar la información en
			// memoria.
			keystores.put(keystoreId, knvs);
		}
	}

	/**
	 * Method that search the files with the keystore name indicated.
	 * The files names must be '[prefix]-keystoreName.version'.
	 * @param prefix Prefix to search. <code>null</code> for any.
	 * @param keystoreName Name of the keystore/file to search.
	 * @return Array of {@link String} with the name of the finded keystore files. Returns <code>null</code> or a
	 * empty array if there are not files with that starting name and prefix.
	 */
	private static String[ ] loadListKeystoreFilesFromFileSystem(String prefix, String keystoreName) {

		// Obtenemos la referencia al directorio que contiene los keystores en
		// el sistema de ficheros.
		File keystoreDir = new File(ABS_PATH_KEYSTORE_DIR);
		// Creamos el filtro de ficheros para el nombre recibido.
		KeystoreNameVersionFilter knvf = new KeystoreNameVersionFilter(prefix, keystoreName);
		// Obtenemos el listado aplicando el filtro.
		return keystoreDir.list(knvf);
	}

	/**
	 * Gets the prefix that represents the greatest timestamp.
	 * @param keystoreFilesNames Array of keystore file names. It can not be
	 * <code>null</code> or empty.
	 * @return The timestamp found in a {@link String}, or <code>null</code>
	 * if no one is found.
	 */
	private static String getMaxPrefix(String[ ] keystoreFilesNames) {

		String result = null;
		long actualResult = -1;

		for (String keystoreFileName: keystoreFilesNames) {
			String[ ] parts = keystoreFileName.split(KeystoreNameVersionFilter.HYPHEN);
			long parsedLong = Long.parseLong(parts[0]);
			if (parsedLong > actualResult) {
				actualResult = parsedLong;
				result = parts[0];
			}
		}
		return result;
	}

	/**
	 * Method that obtains the maximum version number from an array of
	 * keystore file names.
	 * @param keystoreFilesNames Array of keystore file names. It can not be
	 * <code>null</code> or empty.
	 * @param prefix Timestamp prefix to search.
	 * @return {@link Long} with the maximum number version.
	 */
	private static Long getMaxVersionWithPrefix(String[ ] keystoreFilesNames, String prefix) {

		long result = -1;

		for (String keystoreFileName: keystoreFilesNames) {

			String[ ] parts = keystoreFileName.split(KeystoreNameVersionFilter.HYPHEN);
			if (prefix.equals(parts[0])) {
				parts = parts[1].split(KeystoreNameVersionFilter.REGEX_DOT);
				result = Math.max(result, Long.parseLong(parts[1]));
			}
		}
		return Long.valueOf(result);
	}

	/**
	 * Method that checks if the specified keystore is stored in memory and updated
	 * in file system taking as reference the version of the DDBB.
	 * If the keystore not is updated in the file system then update it.
	 * @param keystoreId Keystore identifier.
	 */
	private static void checkIfKeystoreIsUpdatedInHashtable(Long keystoreId) {

		// Comprobamos si tenemos el keystore en el hashtable.
		if (keystores.containsKey(keystoreId)) {
			// Si lo tenemos, comparamos su versión con la de base de datos.
			KeystoreNameVersionStamp knvs = keystores.get(keystoreId);
			Long versionDDBB = getVersionOfKeystoreFromDDBB(keystoreId);
			if (!knvs.getVersion().equals(versionDDBB)) {
				// Si son distintos, significa que hay que actualizar el
				// keystore en el sistema de ficheros y memoria.
				updateKeystoreInFileSystemAndHashtable(knvs, keystoreId);
			}
		} else {
			// Si no lo tenemos, es porque se va a crear en el sistema de
			// ficheros por primera vez.
			updateKeystoreInFileSystemAndHashtable(null, keystoreId);
		}
	}

	/**
	 * Method that create in the file system a new version of the specified keystore.
	 * @param knvs Object that represents the last stored information about the keystore.
	 * @param keystoreId Keystore identifier.
	 */
	private static void updateKeystoreInFileSystemAndHashtable(KeystoreNameVersionStamp knvs, Long keystoreId) {

		// Obtenemos la versión y los datos del keystore de base de datos.
		Long versionDDBB = getVersionOfKeystoreFromDDBB(keystoreId);
		byte[ ] keystoreBytes = null;
		try {
			keystoreBytes = keystoreService.getKeystoreById(keystoreId).getKeystore();
		} catch (Exception e) {
			LOGGER.error(keystoreId, e);
		}

		// Si se han recuperado los datos del keystore...
		if (keystoreBytes != null) {
			// Obtenemos el nombre del keystore.
			String keystoreName = getNameOfKeystoreFromDDBB(keystoreId);
			// Creamos un nuevo objeto que represente al keystore.
			KeystoreNameVersionStamp newKnvs = new KeystoreNameVersionStamp(prefixFileName, keystoreName, versionDDBB);
			// Formamos la ruta del fichero a crear.
			File fileToWrite = new File(ABS_PATH_KEYSTORE_DIR, prefixFileName + KeystoreNameVersionFilter.HYPHEN + keystoreName + DOT + versionDDBB);
			// Si el fichero ya existe, no lo volvemos a escribir, tan solo
			// actualizamos la versión en memoria.
			if (!fileToWrite.exists()) {
				try {
					// Creamos el fichero y lo escribimos.
					if (fileToWrite.createNewFile()) {
						FileOutputStream fos = new FileOutputStream(fileToWrite);
						BufferedOutputStream bos = new BufferedOutputStream(fos);
						try {
							bos.write(keystoreBytes);
							LOGGER.debug(fileToWrite.getAbsolutePath());
						} catch (IOException e) {
							LOGGER.error(fileToWrite.getAbsolutePath(), e);
						} finally {
							safeCloseOutputStream(bos);
						}
					} else {
						LOGGER.error(fileToWrite.getAbsolutePath());
					}
				} catch (Exception e) {
					LOGGER.error(fileToWrite.getAbsolutePath(), e);
				}
			}
			// Almacenamos en memoria.
			keystores.put(keystoreId, newKnvs);
			// Eliminamos las versiones anteriores siempre y cuando hayan pasado
			// más de dos horas (precaución por cambio horario de
			// verano/invierno)
			// de su timestamp (si es que ya existía antes).
			if (knvs != null) {
				long actualDate = Calendar.getInstance().getTimeInMillis();
				if (actualDate - knvs.getTimestampInMillis() > NumberConstants.NUM7200000) {
					deleteOldVersions(knvs);
				}
			}
		}
	}

	/**
	 * This method delete the keystores files that has a version equal or less
	 * than the specified.
	 * @param knvs Keystore representation with prefix, name and version.
	 */
	private static void deleteOldVersions(KeystoreNameVersionStamp knvs) {

		// Obtenemos el listado de ficheros con el nombre del repositorio
		// indicado.
		String[ ] keystoreFilesList = loadListKeystoreFilesFromFileSystem(knvs.getPrefix(), knvs.getKeystoreName());

		// Recorremos los encontrados.
		if (keystoreFilesList != null && keystoreFilesList.length > 0) {
			for (String keystoreFile: keystoreFilesList) {
				// Obtenemos la versión.
				String[ ] parts = keystoreFile.split(KeystoreNameVersionFilter.REGEX_DOT);
				Long versionFile = Long.parseLong(parts[1]);
				// Si la versión del fichero es menor o igual a la del parámetro
				// de entrada, entonces lo borramos.
				if (versionFile.compareTo(knvs.getVersion()) <= 0) {
					File fileToDelete = new File(ABS_PATH_KEYSTORE_DIR, keystoreFile);
					if (fileToDelete.delete()) {
						LOGGER.debug(StaticMonitorizaConfig.createAbsolutePath(ABS_PATH_KEYSTORE_DIR, keystoreFile));
					} else {
						LOGGER.debug(StaticMonitorizaConfig.createAbsolutePath(ABS_PATH_KEYSTORE_DIR, keystoreFile));
					}
				}
			}
		}
	}

	/**
	 * Gets the name assigned to the specified keystore.
	 * @param keystoreId Keystore identifier.
	 * @return Name of the keystore. <code>null</code> incase of error or not exist the keystore.
	 */
	private static String getNameOfKeystoreFromDDBB(Long keystoreId) {

		String result = null;
		try {
			result = keystoreService.getKeystoreById(keystoreId).getName();
		} catch (Exception e) {
			LOGGER.error(keystoreId, e);
		}
		return result;
	}

	/**
	 * Gets the version of the specified keystore.
	 * @param keystoreId Keystore identifier.
	 * @return {@link Long} with the version of the keystore. <code>null</code> incase of some
	 * error or not exists the keystore.
	 */
	private static Long getVersionOfKeystoreFromDDBB(Long keystoreId) {

		Long result = null;
		try {
			result = keystoreService.getKeystoreById(keystoreId).getVersion();
		} catch (Exception e) {
			LOGGER.error(keystoreId, e);
		}
		return result;
	}

	/**
	 * This method set the flag to force clearing the temp files and the map with the keystores.
	 */
	public static void forceToClearTempsFilesAndReloadKeystores() {
		flagToClearTemps = true;
	}

	/**
	 * Checks if it is necessary to clean the temporary files used to represents the keystores.
	 */
	private static void checkIfItIsNecessaryToClearTempsFiles() {

		// Comprobamos si es necesario limpiar temporales...
		if (flagToClearTemps) {
			// Limpiamos los keystores almacenados.
			keystores.clear();
			// Creamos una nueva marca para los nombres de ficheros.
			prefixFileName = String.valueOf(Calendar.getInstance().getTimeInMillis());
			// Ahora eliminamos todos los ficheros contenidos en el
			// directorio temporal donde se almacenan los keystores.
			// Cargamos la ruta al directorio temporal.
			File keystoreDir = new File(ABS_PATH_KEYSTORE_DIR);
			// Comprobamos que existe y que se trata de un directorio.
			if (Files.isDirectory(keystoreDir.toPath())) {
				// Obtenemos el listado de ficheros del directorio.
				File[ ] filesList = keystoreDir.listFiles();
				// Los recorremos y vamos eliminando uno a uno.
				if (filesList != null) {
					for (File file: filesList) {
						try {
							Files.delete(file.toPath());
						} catch (IOException e) {
							LOGGER.error(file.toPath().toString(), e);
						}
					}
				}
			}
			// Finalmente cambiamos el valor de la bandera.
			flagToClearTemps = false;
		}
	}

	/**
	 * Method that handles the closing of a {@link OutputStream} resource.
	 * @param os Parameter that represents a {@link OutputStream} resource.
	 */
	private static void safeCloseOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				LOGGER.error(os.getClass().getName(), e);
			}
		}
	}

	/**
	 * Get keystoreService.
	 * @return keystoreService
	 */
	public IKeystoreService getKeystoreService() {
		return keystoreService;
	}

	/**
	 * Set keystoreService.
	 * @param keystoreService set keystoreService
	 */
	public static void setKeystoreService(IKeystoreService keystoreService) {
		KeystoreVersionFileManager.keystoreService = keystoreService;
	}

}
