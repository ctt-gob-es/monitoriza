package es.gob.monitoriza.invoker.http.conf.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.gob.monitoriza.invoker.http.conf.messages.RegisterClaveRequestType;

public class Utilities {

	public static RegisterClaveRequestType transformJabx(File file) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance(RegisterClaveRequestType.class);
		Unmarshaller u = jc.createUnmarshaller();
		Object o = u.unmarshal(file);
		
		return (RegisterClaveRequestType) o;
	}

}
