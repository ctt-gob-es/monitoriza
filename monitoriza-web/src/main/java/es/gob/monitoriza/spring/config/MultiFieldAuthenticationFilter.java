package es.gob.monitoriza.spring.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MultiFieldAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String SPRING_SECURITY_FORM_SIGNATURE_BASE_64_KEY = "signatureBase64";
	private String signatureBase64Parameter = SPRING_SECURITY_FORM_SIGNATURE_BASE_64_KEY;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

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
    
    public String getSignatureBase64Parameter() {
    	return signatureBase64Parameter;
    }
    
    public void setSignatureBase64Parameter(String signatureBase64Parameter) {
		this.signatureBase64Parameter = signatureBase64Parameter;
	}
    
    private String obtainSignatureBase64(HttpServletRequest request) {
		return request.getParameter(SPRING_SECURITY_FORM_SIGNATURE_BASE_64_KEY);
    }
}
