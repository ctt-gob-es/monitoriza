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
 * <b>File:</b><p>es.gob.monitoriza.spring.config.MultiFieldAuthenticationFilter.java.</p>
 * <b>Description:</b><p> .</p>
  * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30 jul. 2018.</p>
 * @author Gobierno de España.
 * @version 1.0, 30 jul. 2018.
 */
package es.gob.monitoriza.spring.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 30 jul. 2018.
 */
public class MultiFieldAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/**
	 * Attribute that represents the constant param of signature in base 64. 
	 */
	public static final String SPRING_SIGBASE64 = "signatureBase64";
	
	/**
	 * Attribute that represents the param of signature in base 64. . 
	 */
	private String sigBase64Param = SPRING_SIGBASE64;
    
    /**
     * {@inheritDoc}
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
			final HttpServletResponse response) throws AuthenticationException {

    	Authentication auth = null;
    	
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String signatureBase64 = obtainSignatureBase64(request);
		
		if (username == null) {
			username = "";
		}

		username = username.trim();
		
		if (password == null) {
			password = "";
		}
		
		if (signatureBase64 == null) {
			signatureBase64 = "";
		}

		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			MultiFieldLoginUserDetails customUser = new MultiFieldLoginUserDetails(username, signatureBase64);
			authRequest = new UsernamePasswordAuthenticationToken(customUser, password, AuthorityUtils.createAuthorityList("USER"));
			// Allow subclasses to set the "details" property
			setDetails(request, authRequest);
			auth = this.getAuthenticationManager().authenticate(authRequest);
		} catch (IllegalArgumentException iae) {
			auth = null;
		}
		return auth;
	}
    
    /**
     * Get sigBase64Param.
     * @return sigBase64Param
     */
    public String getSigBase64Param() {
    	return sigBase64Param;
    }
    
    /**
     * Set sigBase64Param.
     * @param sigBase64ParamP set sigBase64Param
     */
    public void setSigBase64Param(final String sigBase64ParamP) {
		this.sigBase64Param = sigBase64ParamP;
	}
    
    /**
     * Method that obtains de signature in base 64.
     * @param request HttpServletRequest
     * @return signature
     */
    private String obtainSignatureBase64(final HttpServletRequest request) {
		return request.getParameter(SPRING_SIGBASE64);
    }
}
