package es.clave.monitoriza.tests;

import java.io.File;
import java.security.KeyStore;

import org.junit.Before;
import org.junit.Test;

import es.gob.monitoriza.exception.InvokerException;
import es.gob.monitoriza.invoker.http.AbstractHttpInvoker;
import es.gob.monitoriza.persistence.configuration.dto.ConfigServiceDTO;

public class UtilitiesTest extends AbstractHttpInvoker {

	KeyStore ssl;
	ConfigServiceDTO service;

	@Before
	public void initialize() {
		ssl = null;
		service = new ConfigServiceDTO("servicio");
		service.setBaseUrl("http://");
		service.setSoapUrl("http://localhost:8888/EidasNode");
		service.setWsdl("/ServiceProvider");
	}

	/**
	 * This test is to validate the correct generation of SAML request.
	 */
	@Test
	public void testSamlRequestGenerate() {
		//File file = new File(UtilitiesTest.class.getResource("/Test/monitorizaOriginal.xml").toString());
		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaOriginal.xml");
		try {
			HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);
		} catch (InvokerException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This test is to validate the generation of SAML request without the
	 * provider name.
	 * 
	 * @throws InvokerException
	 */
	@Test(expected = InvokerException.class)
	public void testSamlRequestNoProviderName() throws InvokerException {

		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaNoProviderName.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test is to validate the generation of SAML request without attributes.
	 * 
	 * @throws InvokerException
	 */
	@Test
	public void testSamlRequestNoAttributes() throws InvokerException {

		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaNoAttributes.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test is to validate the generation of SAML request with an invalid proxy port.
	 * 
	 * @throws InvokerException
	 */
	@Test(expected = InvokerException.class)
	public void testSamlRequestErrorProxyPort() throws InvokerException {

		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaErrorProxyPort.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test is to validate the generation of SAML request with an invalid sp type.
	 * 
	 * @throws InvokerException
	 */
	@Test
	public void testSamlRequestErrorSpType() throws InvokerException {

		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaErrorSpType.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test validates the loading of a file that is not a xml.
	 * 
	 * @throws InvokerException
	 */
	@Test(expected = InvokerException.class)
	public void testLoadDifferentFile() throws InvokerException {

		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\prueba.docx");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test validates the loading keystores.
	 * 
	 * @throws InvokerException
	 */
	@Test
	public void testHttpsKeystore() throws InvokerException {
		
		service.setBaseUrl("https://");
		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaOriginal.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
	
	/**
	 * This test validates the loading keystores with a different password.
	 * 
	 * @throws InvokerException
	 */
	@Test(expected = InvokerException.class)
	public void testIncorrectKeystorePassword() throws InvokerException {
		
		service.setBaseUrl("https://");
		File file = new File("C:\\Users\\samuel.zuluaga\\Desktop\\Test\\monitorizaDifferentPass.xml");
		HttpInvokerNoConnectionTest.sendRequest(file, service, ssl);

	}
}