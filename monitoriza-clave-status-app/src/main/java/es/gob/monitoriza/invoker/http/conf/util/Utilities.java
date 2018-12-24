package es.gob.monitoriza.invoker.http.conf.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;

public class Utilities {

	/**
	 * Method to transform xml file to Java class.
	 * 
	 * @param file XML file uploaded
	 * @return ClaveAgentConfType Java Class
	 * @throws JAXBException
	 */
	public static ClaveAgentConfType transformJabx(File file) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance(ClaveAgentConfType.class);
		Unmarshaller u = jc.createUnmarshaller();
		ClaveAgentConfType res = (ClaveAgentConfType) u.unmarshal(file);

		return res;
	}

	/**
	 * Method to load a keystore with a path
	 * 
	 * @param path of keystore
	 * @param password of keystore
	 * @param type file extension (jks or pkcs12)
	 * @return KeyStore
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyStoreException
	 */
	public static KeyStore loadKeystorePath(String path, String password, String type) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		// load the keystore containing the client certificate - keystore type
		// is probably jks or pkcs12
		final KeyStore keystore = KeyStore.getInstance(type);
		InputStream keystoreInput = new FileInputStream(new File(path));
		// get the keystore as an InputStream
		keystore.load(keystoreInput, password.toCharArray());
		return keystore;
	}
	
	/**
	 * Method to load a keystore with a base64
	 * 
	 * @param base64 of keystore
	 * @param password of keystore
	 * @param type file extension (jks or pkcs12)
	 * @return KeyStore
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyStoreException
	 */
	public static KeyStore loadKeystoreBase64(String base64, String password, String type) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		final KeyStore keystore = KeyStore.getInstance(type);
		byte encodedCert[] = Base64.getDecoder().decode(base64);
		
		ByteArrayInputStream keystoreInput  =  new ByteArrayInputStream(encodedCert);
		keystore.load(keystoreInput, password.toCharArray());
		
		return keystore;
	}

}