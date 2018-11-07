package es.clave.monitoriza.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.gob.monitoriza.constant.GeneralConstants;
import es.gob.monitoriza.invoker.http.saml.Constants;
import es.gob.monitoriza.invoker.http.saml.SpProtocolEngineFactory;
import eu.eidas.auth.engine.ProtocolEngineNoMetadataI;
import eu.eidas.auth.engine.configuration.SamlEngineConfigurationException;

public class HttpInvokerTest {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(GeneralConstants.LOGGER_NAME_MONITORIZA_LOG);
	
	public static void main(String[] args) {	
		try {
			sendRequest(new File("C:\\Users\\samuel.zuluaga\\Desktop\\grupoPrincipal\\MonitorizaSamlEngine.xml"));
		} catch (SamlEngineConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static Long sendRequest(final File file) throws IOException, SamlEngineConfigurationException {
		
		ProtocolEngineNoMetadataI protocolEngine = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);
		
		//ProtocolEngineNoMetadataI x = SpProtocolEngineFactory.getSpProtocolEngine(Constants.SP_CONF);
		
		Properties prop = new Properties();
		prop.loadFromXML(new FileInputStream(file));
		String providerName = prop.getProperty("provider.name");

		return 0L;
	}

}
