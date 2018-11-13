//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.13 a las 02:46:44 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.gob.monitoriza.invoker.http.conf.messages._1_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ClaveAgentConf_QNAME = new QName("urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0", "claveAgentConf");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.gob.monitoriza.invoker.http.conf.messages._1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegisterClaveRequestType }
     * 
     */
    public RegisterClaveRequestType createRegisterClaveRequestType() {
        return new RegisterClaveRequestType();
    }

    /**
     * Create an instance of {@link AuthenticationMutualType }
     * 
     */
    public AuthenticationMutualType createAuthenticationMutualType() {
        return new AuthenticationMutualType();
    }

    /**
     * Create an instance of {@link ParamsType }
     * 
     */
    public ParamsType createParamsType() {
        return new ParamsType();
    }

    /**
     * Create an instance of {@link HttpRequestType }
     * 
     */
    public HttpRequestType createHttpRequestType() {
        return new HttpRequestType();
    }

    /**
     * Create an instance of {@link SamlRequestType }
     * 
     */
    public SamlRequestType createSamlRequestType() {
        return new SamlRequestType();
    }

    /**
     * Create an instance of {@link ConnectionType }
     * 
     */
    public ConnectionType createConnectionType() {
        return new ConnectionType();
    }

    /**
     * Create an instance of {@link ProxyConfType }
     * 
     */
    public ProxyConfType createProxyConfType() {
        return new ProxyConfType();
    }

    /**
     * Create an instance of {@link ParamType }
     * 
     */
    public ParamType createParamType() {
        return new ParamType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterClaveRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0", name = "claveAgentConf")
    public JAXBElement<RegisterClaveRequestType> createClaveAgentConf(RegisterClaveRequestType value) {
        return new JAXBElement<RegisterClaveRequestType>(_ClaveAgentConf_QNAME, RegisterClaveRequestType.class, null, value);
    }

}
