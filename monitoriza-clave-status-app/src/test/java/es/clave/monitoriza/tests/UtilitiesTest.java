package es.clave.monitoriza.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.gob.monitoriza.invoker.http.conf.messages.ClaveAgentConfType;

public class UtilitiesTest {

	public static void main(String[] args) {
		try {
			ClaveAgentConfType rcrt = transformJabx(new File("C:\\Trabajo\\monitoriza.xml"));
			System.out.println(rcrt);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ClaveAgentConfType transformJabx(File file) throws JAXBException, FileNotFoundException {

		InputStream is = new FileInputStream( file );
		JAXBContext jc = JAXBContext.newInstance(ClaveAgentConfType.class);
		Unmarshaller u = jc.createUnmarshaller();
		Object o = u.unmarshal(is);
		
		return (ClaveAgentConfType) o;
	}

}
