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
 * <b>File:</b><p>es.gob.afirma.wsServiceInvoker.ws.ResponseHandler.java.</p>
 * <b>Description:</b><p>Class that represents handler used to verify the signature response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * <b>Date:</b><p>26/09/2023.</p>
 * @version 1.0, 26/09/2023.
 */
package es.gob.monitoriza.handler;

/**
 * <p>Class that represents handler used to verify the signature response.</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 26/09/2023.
 */
public class ResponseHandler extends AbstractCommonHandler {

    
    /**
     * Constant attribute that represents the handler name. 
     */
    private static final String HANDLER_NAME = "responseHandlerIntegra";

    /**
     * Constructor method for the class CopyOfClientHandler.java.
     */
    public ResponseHandler() {
		this.handlerDesc.setName(HANDLER_NAME);
		this.handlerDesc.getRules().setPhaseLast(true);
    }

    /**
     * Constructor method for the class ResponseHandler.java.
     * @param keystorePath keystore path.
     * @param keystorePass keystore password.
     * @param keystoreType keystore type.
     * @param autUser alias of certificate stored in keystore.
     * @param autPassword password of certificate (private key).
     */
    public ResponseHandler(String keystorePath, String keystorePass, String keystoreType, String autUser, String autPassword) {
		this.handlerDesc.setName(HANDLER_NAME);
		this.handlerDesc.getRules().setPhaseLast(true);
		setUserKeystore(keystorePath);
		setUserKeystorePass(keystorePass);
		setUserKeystoreType(keystoreType);
		setUserAlias(autUser);
		setPassword(autPassword);
	}

}
