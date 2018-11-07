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
 * <b>File:</b><p>es.gob.monitoriza.webservice.WSCryptoConfiguration.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>20 sept. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 20 sept. 2018.
 */
package es.gob.monitoriza.crypto.utils;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.security.auth.callback.CallbackHandler;

import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoType;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 20 sept. 2018.
 */
public class WSCryptoConfiguration implements Crypto{
	/**
     * Attribute that represents the certificate used for signing the request. 
     */
    private X509Certificate certificate = null;
    
    /**
     * Attribute that represents the key used for signing the request. 
     */
    private PrivateKey key = null;

    /**
     * Constructor method for the class WSCryptoConfiguration.java.
     * @param certificateParam  X.509 Certificate.
     * @param keyParam                               Private key.
     */
    public WSCryptoConfiguration(X509Certificate certificateParam, PrivateKey keyParam) {
                super();
                this.certificate = certificateParam;
                this.key = keyParam;
    }

    @Override
    public final byte[ ] getBytesFromCertificates(X509Certificate[ ] arg0) throws WSSecurityException {
                return null;
    }

    @Override
    public final CertificateFactory getCertificateFactory() throws WSSecurityException {
                return null;
    }

    @Override
    public final X509Certificate[ ] getCertificatesFromBytes(byte[ ] arg0) throws WSSecurityException {
                return null;
    }

    @Override
    public final String getCryptoProvider() {
                return null;
    }

    @Override
    public final String getDefaultX509Identifier() throws WSSecurityException {
                return null;
    }

    @Override
    public final PrivateKey getPrivateKey(X509Certificate arg0, CallbackHandler arg1) throws WSSecurityException {
                return null;
    }

    @Override
    public final PrivateKey getPrivateKey(String arg0, String arg1) throws WSSecurityException {
                return this.key;
    }

    @Override
    public final byte[ ] getSKIBytesFromCert(X509Certificate arg0) throws WSSecurityException {
                return null;
    }

    @Override
    public final X509Certificate[ ] getX509Certificates(CryptoType cryptoType) throws WSSecurityException {
                return new X509Certificate[] {certificate};
    }

    @Override
    public final String getX509Identifier(X509Certificate arg0) throws WSSecurityException {
                return null;
    }

    @Override
    public final X509Certificate loadCertificate(InputStream arg0) throws WSSecurityException {
                return null;
    }

    @Override
    public void setCertificateFactory(String arg0, CertificateFactory arg1) {
                
    }

    @Override
    public void setCryptoProvider(String arg0) {
                
    }

    @Override
    public void setDefaultX509Identifier(String arg0) {
                
    }

    @Override
    @Deprecated
    public final boolean verifyTrust(X509Certificate[ ] arg0) throws WSSecurityException {
                return false;
    }

    @Override
    public final boolean verifyTrust(PublicKey arg0) throws WSSecurityException {
                return false;
    }

    @Override
    public final boolean verifyTrust(X509Certificate[ ] arg0, boolean arg1) throws WSSecurityException {
                return false;
    }

}
