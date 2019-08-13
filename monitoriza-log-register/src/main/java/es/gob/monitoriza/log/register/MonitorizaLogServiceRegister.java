package es.gob.monitoriza.log.register;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.gob.log.register.LogServiceRegister;
import es.gob.log.register.RegistrationException;

/**
 * Conector para el registro del servicio de consulta de logs en Monitoriza.
 * @version 1.0, 18/06/2019.
 */
public class MonitorizaLogServiceRegister implements LogServiceRegister {

	/** Nombre del atributo para el registro del nombre del log. */
	private static final String PARAM_LOG_NAME = "logName";

	/** Descripci&oacute;n del atributo para el registro del nombre del log. */
	private static final String PARAM_LOG_DESCRIPTION = "logDescription";

	/** Tipo de sistema que se registra. */
	private static final String PARAM_LOG_TYPE = "logType";

	/** URL del servicio de consulta de logs. */
	private static final String PARAM_LOG_URL = "logUrl";

	/** Clave para el acceso al servicio de consulta de logs. */
	private static final String PARAM_LOG_KEY = "logKey";

	private static final Logger LOGGER = Logger.getLogger(MonitorizaLogServiceRegister.class.getName());

	private static final String SSL_CONTEXT = "SSL";//$NON-NLS-1$

	private static final HostnameVerifier HOSTNAME_VERIFIER;

	private static final TrustManager[] DUMMY_TRUST_MANAGER;

	static {
		HOSTNAME_VERIFIER = new HostnameVerifier() {
			@Override
			public boolean verify(final String hostname, final SSLSession session) {
				return true;
			}
		};

		DUMMY_TRUST_MANAGER = new TrustManager[] {
		    new X509TrustManager() {
		    	@Override
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		    		return null;
		        }
		        @Override
		        public void checkClientTrusted(final X509Certificate[] certs, final String authType) { /* No hacemos nada */ }
		        @Override
		        public void checkServerTrusted(final X509Certificate[] certs, final String authType) {  /* No hacemos nada */  }
		    }
		};
	}

	private String serviceUrl = null;

	private ConfigManager config = null;

	@Override
	public void setServiceUrl(final String url) {
		this.serviceUrl = url;
	}

	@Override
	public void setAuthenticationInfo(final Object info) {
		// TODO Ahora no hay autenticacion
	}

	@Override
	public void setConfig(final Properties config) {
		this.config = new ConfigManager(config);
	}

	@Override
	public void registry() throws IOException, RegistrationException {

		final HttpURLConnection conn = (HttpURLConnection) new URL(this.serviceUrl).openConnection();

		if (conn instanceof HttpsURLConnection) {
			try {
				disableSslChecks((HttpsURLConnection) conn);
			} catch (final Exception e) {
				LOGGER.log(Level.FINE, "No se han podido desactivar las comprobaciones de seguridad de SSL", e);
			}
		}

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		try {
			this.config.check();
		}
		catch (final Exception e) {
			throw new IOException("No se proporcionaron todas las propiedades de configuracion necesarias para el registro", e);
		}

		final StringBuilder urlParameters = new StringBuilder();
		urlParameters.append(PARAM_LOG_NAME).append("=").append(this.config.getLogServiceName()).append("&")
		.append(PARAM_LOG_DESCRIPTION).append("=").append(this.config.getLogServiceDescription()).append("&")
		.append(PARAM_LOG_TYPE).append("=").append(this.config.getLogServiceType()).append("&")
		.append(PARAM_LOG_URL).append("=").append(this.config.getLogServiceUrl()).append("&")
		.append(PARAM_LOG_KEY).append("=").append(Base64.encode(this.config.getCipherKey()));

		conn.setDoOutput(true);

		// Preparamos el envio de los parametros
		try (final OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
			writer.write(urlParameters.toString());
		}

		// Conectamos
		conn.connect();

		// Comprobamos que no se produjese un error durante la operacion
		final int resCode = conn.getResponseCode();
		final String statusCode = Integer.toString(resCode);
		if (statusCode.startsWith("4") || statusCode.startsWith("5")) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new RegistrationException(
			    String.format("Error al registrar el servicio de log. StatusCode %1s. Mensaje: %2s",
			                  statusCode, conn.getResponseMessage()));
		}
	}


	/** Deshabilita las comprobaciones de certificados en conexiones SSL, acept&aacute;dose entonces
	 * cualquier certificado.
	 * @throws KeyManagementException Si hay problemas en la gesti&oacute;n de claves SSL.
	 * @throws NoSuchAlgorithmException Si el JRE no soporta alg&uacute;n algoritmo necesario. */
	private static void disableSslChecks(final HttpsURLConnection conn) throws NoSuchAlgorithmException, KeyManagementException  {
		final SSLContext sc = SSLContext.getInstance(SSL_CONTEXT);
		sc.init(
			null,
			DUMMY_TRUST_MANAGER,
			new java.security.SecureRandom()
		);
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(HOSTNAME_VERIFIER);
	}
}
