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
 * <b>File:</b><p>es.gob.monitoriza.utilidades.FileUtils.java.</p>
 * <b>Description:</b><p> Utilities class for Files.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * <b>Date:</b><p>21/12/2017.</p>
 * @author Gobierno de España.
 * @version 1.4, 19/12/2018.
 */
package es.gob.monitoriza.utilidades;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.security.auth.login.Configuration;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.ICommonsUtilLogMessages;
import es.gob.monitoriza.i18n.Language;

/** 
 * <p>Utilities class for Files.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.4, 19/12/2018.
 */
public final class FileUtils {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	/**
	 * Attribute that represents BUFFER_SIZE.
	 */
	public static final int BUFFER_SIZE = 1024;
	
	/**
	 * Attribute that represents the rar file extension. 
	 */
	public static final String RAR_EXTENSION = "rar";
	
	/**
	 * Attribute that represents the zip file extension. 
	 */
	public static final String ZIP_EXTENSION = "zip";
	
	/**
	 * Attribute that represents the gzip file extension. 
	 */
	public static final String GZIP_EXTENSION = "gz";
	
	/**
	 * Attribute that represents the 'file signature' or 'magic number' to identify a rar file. 
	 */
	public static final String RAR_HEX_MAGIC_NUMBER = "526172211A0700";
	
	/**
	 * Attribute that represents the 'file signature' to identify a zip file.  
	 */
	public static final String ZIP_ISO8859_1_SIGNATURE = "PK"; 
	
	/**
	 * Attribute that represents the 'file signature' or 'magic number' to identify a gzip file. 
	 */
	public static final String GZIP_HEX_MAGIC_NUMBER = "1F8B";
	
	/**
	 * Attribute that represents . 
	 */
	public static final String NO_FILE_NAME = "nofile";
												   

	/**
	 * Constructor method for the class FileUtils.java.
	 */
	private FileUtils() {
	}

	/**
	 * method that read a file line by line fiven a file object.
	 * 
	 * @param file File to read.
	 * @return the content of the file as string object.
	 */
	public static String readFile(File file) {
		FileReader fileReader = null;
		BufferedReader bf = null;
		FileWriter result = null;
		StringBuilder res = new StringBuilder();
		try {
			// Creamos un fileReader para leer el log con las peticiones y
			// respuestas.
			fileReader = new FileReader(file.getAbsolutePath());
			// Creamos el buffer de escritura
			bf = new BufferedReader(fileReader);
			// Iniciamos la variable que irá leyendo linea por linea el fichero.
			String line = bf.readLine();
			if (line != null) {
				res.append(line);
				line = bf.readLine();
				while (line != null) {
					res.append(line);
					line = bf.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS001, new Object[ ] {file.getAbsolutePath()}), e);
		} catch (IOException e) {
			LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS002, new Object[ ] {file.getAbsolutePath()}), e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS003), e);
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS003), e);
				}
			}
		}
		return res.toString();
	}
	
	/**
	 * Method that reads the contents of a file and return the encoded byte array as string
	 * @param path Path to the file
	 * @param encoding Encoding to use
	 * @return String that represents the encoded contents of the file
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		
		byte[ ] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	/**
	 * Method that reads a file returning it as a byte array
	 * @param file The file to get its byte array
	 * @return byte[] representing the file
	 */
	public static byte[ ] fileToByteArray(File file) {

		byte[ ] raw;
		byte[ ] result;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			raw = Files.readAllBytes(file.toPath());
			
			for (byte b: raw) {
				if (b != 10 && b != 13) {
					baos.write(b);
				}
			}
			
		} catch (IOException e) {
			LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS002, new Object[ ] {file.getAbsolutePath()}), e);
		}

		result = baos.toByteArray();

		return result;
	}
	
		
	/**
	 * Write data into a file. If file doesn't exist, it is created.
	 * @param data information to include into file.
	 * @param filename name of file to record.
	 */
	public static void writeFile(byte[ ] data, String filename)  {
		
		if (data != null && filename != null && !"".equals(filename)) {
			
			try (FileOutputStream fos = new FileOutputStream(new File(filename));
					ByteArrayInputStream bais = new ByteArrayInputStream(data); ) {
								
				byte [] buffer = new byte [BUFFER_SIZE];
				int bytesReaded = 0;
				while ((bytesReaded = bais.read(buffer)) >= 0) {
					fos.write(buffer, 0, bytesReaded);
				}
			} catch (IOException e) {
				LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS004, new Object[ ] {filename}), e);
			} 
		} else {
			LOGGER.error(Language.getFormatResCommonsUtilsMonitoriza(ICommonsUtilLogMessages.ERRORUTILS005, new Object[ ] {filename}));
		}
	}
	
	/**
	 * Lists the names of the sub directories contained in the given folder
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	public static List<String> listSubDirectoriesForFolder(final File folder) throws IOException {
		
		List<String> listaCarpetasPeticiones = null;
		
		try (Stream<Path> paths = Files.walk(Paths.get(folder.getAbsolutePath()))) {

			listaCarpetasPeticiones = paths.filter(Files::isDirectory).map(Path::toString).collect(Collectors.toList());
		}
		
		return listaCarpetasPeticiones;
	}
	
	/**
	 * Method that unzips a file into a folder
	 * @param file Byte array representing the file to unzip
	 * @param targetFolder Destination folder including the file
	 * @throws IOException
	 */
	public static void unZipFile(final byte[] file, final String targetFolder) throws IOException {
		
		InputStream inputStream = new ByteArrayInputStream(file);

		byte[ ] buffer = new byte[1024];

		try (ZipInputStream zis = new ZipInputStream(inputStream);) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				File newFile = new File(targetFolder + "//" + fileName);
				FileOutputStream fos = new FileOutputStream(newFile);
					int len;
    				while ((len = zis.read(buffer)) > 0) {
    					fos.write(buffer, 0, len);
    				}
				fos.close();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		}	
		
	}
	
	/**
	 * Method that unzips a compressed file recursively
	 * @param fileBytes byte array representing the file
	 * @param fileName Name of the file
	 * @param targetFolder Folder to which unzip the file
	 * @throws IOException
	 */
	public static void unZipFileWithSubFolders(final byte[] fileBytes, final String fileName, final String targetFolder) throws IOException {
		try {
			
			String tempFilePath = new StringBuffer().append(StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY))
													.append(GeneralConstants.DOUBLE_PATH_SEPARATOR)
													.append(fileName).toString();
			
			// Se escribe temporalmente el fichero en el sistema de archivos
			writeFile(fileBytes, tempFilePath);
			
			
            // Se abre el fichero zip
            ZipFile zipFile = new ZipFile(tempFilePath);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = targetFolder.concat(zipEntry.getName());
                
                File file = new File(name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Se extrae el fichero
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();
                
            }
            zipFile.close();
            
            deleteFile(tempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

	
	
	/**
	 * Method that checks if the string argument matches the rar content types.
	 * @param contentType String that represents the content type of the file being checked
	 * @return true if the content type is supported, false otherwise
	 */
	public static boolean isRarType(final String contentType) {
		return contentType.equals("application/x-rar-compressed")
                || contentType.equals("application/octet-stream");
	}
	
	/**
	 * Method that checks if the string argument matches the zip content types.
	 * @param contentType String that represents the content type of the file being checked
	 * @return true if the content type is supported, false otherwise
	 */
	public static boolean isZipType(final String contentType) {
		return contentType.equals("application/zip")
                || contentType.equals("application/x-zip-compressed")
                || contentType.equals("multipart/x-zip")
				|| contentType.equals("application/octet-stream");
	}
	
	/**
	 * Method that checks if the string argument matches the gzip content types.
	 * @param contentType String that represents the content type of the file being checked
	 * @return true if the content type is supported, false otherwise
	 */
	public static boolean isGZipType(final String contentType) {
		return contentType.equals("application/gzip")
                || contentType.equals("application/x-gzip");
	}
	
	/**
	 * Method that gets a String representing the contents of the file in hexadecimal
	 * @param bytes byte array of the file
	 * @return String in hexadecimal representing the file content
	 */
	public static String bytesToHex(final byte[] bytes, final int length) {
	    return IntStream.range(0, length)
	        .collect(StringBuilder::new,
	                 (sb,i)->new Formatter(sb).format("%02x", bytes[i]&0xff),
	                 StringBuilder::append).toString();
	}
	
	/**
	 * Method that deletes a file
	 * @param filePath Path to the file
	 * @return true if the deletion was successful, false otherwise
	 */
	private static boolean deleteFile(final String filePath) {
		
		File file = new File(filePath);
		
		return file.delete();
	}
	
	/**
	 * Deletes a directory and its subdirectories.
	 * @param directoryToBeDeleted Object that represents the root directory to be deleted
	 * @return true if the directory were successfully deleted, false otherwise
	 */
	public static boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
	
	/**
	 * Loops over a path and deletes all directories inside beginning with a prefix
	 * @param pathRoot Root directory
	 * @param startName Prefix of the directories to be deleted
	 * @return true if all the files where deleted 
	 */
	public static boolean deleteAllDirectoriesBeginnigWith(final String pathRoot, final String startName) {
		
		File filePath = new File(pathRoot);
        File[] allFiles = filePath.listFiles();
        boolean deleted = false;

		for (File file: allFiles) {
			if (file.isDirectory()) {
				String fileName = file.getName();

				if (fileName.startsWith(startName)) {
					
					if (deleteDirectory(file)) {
						deleted = true;
					} else {
						deleted = false;
						break;
					}
				}
								
			}

		}
		
		return deleted;
	}
		
}
