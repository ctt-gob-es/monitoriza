package es.gob.monitoriza.spring.config;

public class MultiFieldLoginUserDetails {
    
    private final String username;
    
    private final String signatureBase64;
    
    public MultiFieldLoginUserDetails(String username, String signatureBase64) {
        this.username = username;
    	this.signatureBase64 = signatureBase64;
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getSignatureBase64() {
        return this.signatureBase64;
    }
}
