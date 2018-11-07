/* 
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
 * <b>File:</b><p>es.gob.monitoriza.webservice.AxisSSLSocketFactory.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9 oct. 2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 28/10/2018.
 */
package es.gob.monitoriza.webservice;

import java.io.BufferedWriter;
/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 28/10/2018.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Hashtable;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import org.apache.axis.components.net.BooleanHolder;
import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.SecureSocketFactory;
import org.apache.axis.components.net.TransportClientProperties;
import org.apache.axis.components.net.TransportClientPropertiesFactory;
import org.apache.axis.utils.Messages;
import org.apache.axis.utils.StringUtils;
import org.apache.axis.utils.XMLUtils;
import org.apache.log4j.Logger;

import es.gob.monitoriza.utilidades.NumberConstants;

/**
* @author elblogdeselo
*
*/

public class AxisSSLSocketFactory extends JSSESocketFactory implements SecureSocketFactory {

	/**
	 * Attribute that represents the object that manages the log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(AxisSSLSocketFactory.class);

	/**
	 * Attribute that represents the SSL keyStore. 
	 */
	private static KeyStore keyStore;

	/**
	 * Attribute that represents the pass of SSL keyStore. 
	 */
	private static String keystorePass;

	/**
	 * Constructor method for the class AxisSSLSocketFactory.java.
	 * @param attributes 
	 */
	public AxisSSLSocketFactory(@SuppressWarnings("rawtypes") Hashtable attributes) {
		super(attributes);
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.axis.components.net.JSSESocketFactory#initFactory()
	 */
	@Override
	protected void initFactory() throws IOException {
		try {
			SSLContext context = getContext();
			sslFactory = context.getSocketFactory();
		} catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			}
			throw new IOException(e.getMessage());
		}
	}
	
	/**
     * creates a secure socket.
     *
     * @param host
     * @param port
     * @param otherHeaders
     * @param useFullURL
     *
     * @return Socket
     * @throws Exception
     */
	@SuppressWarnings("resource")
	@Override
    public Socket create(String host, int port, StringBuffer otherHeaders, BooleanHolder useFullURL)
            throws Exception {
        initFactory();
        if (port == -1) {
            port = NumberConstants.NUM443;
        }

        TransportClientProperties tcp = TransportClientPropertiesFactory.create("https");

        boolean hostInNonProxyList = isHostInNonProxyList(host, tcp.getNonProxyHosts());

        Socket sslSocket = null;
        if (tcp.getProxyHost().length() == 0 || hostInNonProxyList) {
            // direct SSL connection
            sslSocket = sslFactory.createSocket(host, port);
        } else {

            // Default proxy port is 80, even for https
            int tunnelPort = (tcp.getProxyPort().length() != 0)
                             ? Integer.parseInt(tcp.getProxyPort())
                             : NumberConstants.NUM80;
            if (tunnelPort < 0)
                tunnelPort = NumberConstants.NUM80;

            // Create the regular socket connection to the proxy
            Socket tunnel = new Socket(tcp.getProxyHost(), tunnelPort);

            // The tunnel handshake method (condensed and made reflexive)
            OutputStream tunnelOutputStream = tunnel.getOutputStream();
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(tunnelOutputStream)));
            out.print("CONNECT " + host + ":" + port + " HTTP/1.0\r\n"
                    + "User-Agent: AxisClient");
            if (tcp.getProxyUser().length() != 0 &&
                tcp.getProxyPassword().length() != 0) {

                // add basic authentication header for the proxy
                String encodedPassword = XMLUtils.base64encode((tcp.getProxyUser()
                        + ":"
                        + tcp.getProxyPassword()).getBytes());

                out.print("\nProxy-Authorization: Basic " + encodedPassword);
            }
            out.print("\nContent-Length: 0");
            out.print("\nPragma: no-cache");
            out.print("\r\n\r\n");
            out.flush();
            InputStream tunnelInputStream = tunnel.getInputStream();

            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("isNull00", "tunnelInputStream",
                        "" + (tunnelInputStream
                        == null)));
            }
            String replyStr = "";

            // Make sure to read all the response from the proxy to prevent SSL negotiation failure
            // Response message terminated by two sequential newlines
            int newlinesSeen = 0;
            boolean headerDone = false;    /* Done on first newline */

            while (newlinesSeen < 2) {
                int i = tunnelInputStream.read();

                if (i < 0) {
                    throw new IOException("Unexpected EOF from proxy");
                }
                if (i == '\n') {
                    headerDone = true;
                    ++newlinesSeen;
                } else if (i != '\r') {
                    newlinesSeen = 0;
                    if (!headerDone) {
                        replyStr += String.valueOf((char) i);
                    }
                }
            }
            if (StringUtils.startsWithIgnoreWhitespaces("HTTP/1.0 200", replyStr) &&
                    StringUtils.startsWithIgnoreWhitespaces("HTTP/1.1 200", replyStr)) {
                throw new IOException(Messages.getMessage("cantTunnel00",
                        new String[]{
                            tcp.getProxyHost(),
                            "" + tunnelPort,
                            replyStr}));
            }

            // End of condensed reflective tunnel handshake method
            sslSocket = sslFactory.createSocket(tunnel, host, port, true);
            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("setupTunnel00",
                          tcp.getProxyHost(),
                        "" + tunnelPort));
            }
        }

        ((SSLSocket) sslSocket).startHandshake();
        if (log.isDebugEnabled()) {
            log.debug(Messages.getMessage("createdSSL00"));
        }
        return sslSocket;
    }

	/**
	 * Method that gets a custom SSL Context. This is the main working of this class.
	 * @return Configured SSL Context
	 */
	protected SSLContext getContext() {
		try {
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keyStore, keystorePass.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);
			// congifure a local SSLContext to use created keystores
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
			return sslContext;
		} catch (Exception e) {
			LOGGER.error("Error creating context for SSLSocket!");
		}
		return null;
	}

	/**
	 * Get keyStore.
	 * @return keyStore
	 */
	public KeyStore getKeyStore() {
		return keyStore;
	}

	/**
	 * Set keyStore.
	 * @param keyStore set keyStore.
	 */
	public static void setKeyStore(KeyStore keyStore) {
		AxisSSLSocketFactory.keyStore = keyStore;
	}

	/**
	 * Get keystorePass.
	 * @return keystorePass
	 */
	public String getKeystorePass() {
		return keystorePass;
	}

	/**
	 * Set keystorePass.
	 * @param keystorePass set keystorePass
	 */
	public static void setKeystorePass(String keystorePass) {
		AxisSSLSocketFactory.keystorePass = keystorePass;
	}
}
