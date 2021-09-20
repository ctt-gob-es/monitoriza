package es.gob.eventmanager.client;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.gob.eventmanager.message.EventResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@SuppressWarnings("static-method")
public class EventClientTest extends TestCase{


	private static final TrustManager[] DUMMY_TRUST_MANAGER =
			new TrustManager[] {
                new X509TrustManager() {
                	@Override
                	public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                	@Override
                	public void checkClientTrusted(final X509Certificate[] certs, final String authType) { /* No hacemos nada */ }
                	@Override
                	public void checkServerTrusted(final X509Certificate[] certs, final String authType) {  /* No hacemos nada */  }
                }
	};

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EventClientTest( final String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EventClientTest.class );
    }

	public void testBuildAuthToken() {
		System.out.println(AuthTokenBuilder.buildAuthToken("AFIRMA", "password".toCharArray(), "message".getBytes()));
	}

	public void testSendAlert() throws Exception {

		disableSslChecks();

		final EventClient cliente = new EventClient("AFIRMA", "LOCAL", new URL("https://localhost:8443/eventmanager-service/notice"), "password");
		final EventResponse response = cliente.sendAlert("COD_ERR008", "Error en el acceso a la base de datos");

		System.out.println("Codigo resultado: " + response.getResult());
		System.out.println("Descripcion: " + response.getDescription());
	}

	/** Deshabilita las comprobaciones de certificados en conexiones SSL, acept&aacute;dose entonces
	 * cualquier certificado.
	 * @throws KeyManagementException Si hay problemas en la gesti&oacute;n de claves SSL.
	 * @throws NoSuchAlgorithmException Si el JRE no soporta alg&uacute;n algoritmo necesario.
	 * @throws KeyStoreException Si no se puede cargar el KeyStore SSL.
	 * @throws IOException Si hay errores en la carga del fichero KeyStore SSL.
	 * @throws CertificateException Si los certificados del KeyStore SSL son inv&aacute;lidos.
	 * @throws UnrecoverableKeyException Si una clave del KeyStore SSL es inv&aacute;lida. */
	public static void disableSslChecks() throws KeyManagementException,
	                                             NoSuchAlgorithmException,
	                                             KeyStoreException,
	                                             UnrecoverableKeyException,
	                                             CertificateException,
	                                             IOException {
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, DUMMY_TRUST_MANAGER, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(
			new HostnameVerifier() {
				@Override
				public boolean verify(final String hostname, final SSLSession session) {
					return true;
				}
			}
		);

		SSLContext.setDefault(sc);
	}
}
