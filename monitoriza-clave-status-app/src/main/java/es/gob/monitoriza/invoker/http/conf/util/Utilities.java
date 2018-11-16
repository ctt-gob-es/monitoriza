package es.gob.monitoriza.invoker.http.conf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;

public class Utilities {

	public static ClaveAgentConfType transformJabx(File file) throws JAXBException {

		//JAXBContext jc = JAXBContext.newInstance("es.gob.monitoriza.invoker.http.conf.messages");
		JAXBContext jc = JAXBContext.newInstance(ClaveAgentConfType.class);
		Unmarshaller u = jc.createUnmarshaller();
		ClaveAgentConfType res = (ClaveAgentConfType) u.unmarshal(file);

		return res;
	}

	public static KeyStore LoadKeystore(String path, String password, String type) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		// load the keystore containing the client certificate - keystore type
		// is probably jks or pkcs12
		final KeyStore keystore = KeyStore.getInstance(type);
		InputStream keystoreInput = new FileInputStream(new File(path));
		// get the keystore as an InputStream
		keystore.load(keystoreInput, password.toCharArray());
		return keystore;

	}

}