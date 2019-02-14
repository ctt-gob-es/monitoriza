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
 * <b>File:</b><p>es.gob.monitoriza.invoker.http.saml.SPUtil.java.</p>
 * <b>Description:</b><p>Class </p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>9/04/2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30/01/2019.
 */
package es.gob.monitoriza.invoker.http.saml;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import es.gob.monitoriza.utilidades.UtilsStringChar;
import eu.eidas.auth.commons.xml.DocumentBuilderFactoryUtil;

/**
 * <p>Class .</p>
 * <b>Project:</b>
 * <p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30/01/2019.
 */
public class SPUtil {

    SPUtil() {};

    private static final Logger LOG = LoggerFactory.getLogger(SPUtil.class);

    private static final String NO_ASSERTION = "no assertion found";

    private static final String ASSERTION_XPATH = "//*[local-name()='Assertion']";

    public static String getConfigFilePath() {
        String envLocation = System.getenv().get(Constants.MONITORIZA_CONFIG_REPOSITORY_VRBLE);
        String configLocation = System.getProperty(Constants.MONITORIZA_CONFIG_REPOSITORY_VRBLE, envLocation);
        return configLocation;
    }

    private static Properties loadConfigs(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(SPUtil.getConfigFilePath()+fileName));
        return properties;
    }

    public static Properties loadSPConfigs() {
        try {
            return SPUtil.loadConfigs(Constants.SP_PROPERTIES);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            LOG.error(UtilsStringChar.EMPTY_STRING, e);
            throw new IllegalArgumentException("Could not load configuration file" + e.getMessage());
        }
    }

    /**
     * Returns true when the input contains an encrypted SAML Response
     *
     * @param tokenSaml
     * @return
     * @throws EIDASSAMLEngineException
     */
    

    /**
     * @param samlMsg the saml response as a string
     * @return a string representing the Assertion
     */
    public static String extractAssertionAsString(String samlMsg) {
        String assertion = NO_ASSERTION;
        try {
            Document doc = DocumentBuilderFactoryUtil.parse(samlMsg);

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(ASSERTION_XPATH, doc, XPathConstants.NODE);
            if (node != null) {
                assertion = DocumentBuilderFactoryUtil.toString(node);
            }
        } catch (ParserConfigurationException pce) {
            LOG.error("cannot parse response {}", pce);
        } catch (SAXException saxe) {
            LOG.error("cannot parse response {}", saxe);
        } catch (IOException ioe) {
            LOG.error("cannot parse response {}", ioe);
        } catch (XPathExpressionException xpathe) {
            LOG.error("cannot find the assertion {}", xpathe);
        } catch (TransformerException trfe) {
            LOG.error("cannot output the assertion {}", trfe);
        }

        return assertion;
    }
}
