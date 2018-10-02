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
 * @version 1.2, 02/10/2018.
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.constant.StaticConstants;
import es.gob.monitoriza.i18n.Language;
import es.gob.monitoriza.i18n.LogMessages;

/** 
 * <p>Utilities class for Files.</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems.</p>
 * @version 1.1, 02/10/2018.
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
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_NO_FILE_EXISTS), e);
		} catch (IOException e) {
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_READING_FILE), e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_INPUT_OUTPUT_FILE), e);
				}
			}
		}
		return res.toString();
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
		
		byte[ ] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

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
			LOGGER.error(Language.getResMonitoriza(LogMessages.ERROR_READING_FILE), e);
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
				LOGGER.error(e);
			} 
		} else {
			LOGGER.error("No se puede crear archivo: los datos de entrada son incorrectos");
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
	
	public static void unZipFileWithSubFolders(final byte[] fileBytes, final String fileName, final String targetFolder) throws IOException {
		try {
			
			String tempFilePath = new StringBuffer().append(StaticMonitorizaProperties.getProperty(StaticConstants.ROOT_PATH_DIRECTORY))
													.append(GeneralConstants.DOUBLE_PATH_SEPARATOR)
													.append(fileName).toString();
			
			// Se escribe temporalmente el fichero en el sistema de archivos
			writeFile(fileBytes, tempFilePath);
			
			
            // Open the zip file
            ZipFile zipFile = new ZipFile(tempFilePath);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = targetFolder.concat(zipEntry.getName());
                               
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();
                //LOGGER.info("name: " + name + "| size: " + size + "| compressed size: " + compressedSize + "\n");

                // Do we need to create a directory ?
                File file = new File(name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
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
                || contentType.equals("multipart/x-zip");
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
	 * Method that 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(final byte[] bytes, final int length) {
	    return IntStream.range(0, length)
	        .collect(StringBuilder::new,
	                 (sb,i)->new Formatter(sb).format("%02x", bytes[i]&0xff),
	                 StringBuilder::append).toString();
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
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
		
}
