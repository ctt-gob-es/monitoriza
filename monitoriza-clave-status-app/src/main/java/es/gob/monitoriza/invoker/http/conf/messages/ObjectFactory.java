//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.24 a las 12:49:59 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.gob.monitoriza.invoker.http.conf.messages package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.gob.monitoriza.invoker.http.conf.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClaveAgentConfType }
     * 
     */
    public ClaveAgentConfType createClaveAgentConfType() {
        return new ClaveAgentConfType();
    }

    /**
     * Create an instance of {@link ProxyConfType }
     * 
     */
    public ProxyConfType createProxyConfType() {
        return new ProxyConfType();
    }

    /**
     * Create an instance of {@link AuthenticationMutualType }
     * 
     */
    public AuthenticationMutualType createAuthenticationMutualType() {
        return new AuthenticationMutualType();
    }

    /**
     * Create an instance of {@link ConnectionType }
     * 
     */
    public ConnectionType createConnectionType() {
        return new ConnectionType();
    }

    /**
     * Create an instance of {@link ParamType }
     * 
     */
    public ParamType createParamType() {
        return new ParamType();
    }

    /**
     * Create an instance of {@link ParamsType }
     * 
     */
    public ParamsType createParamsType() {
        return new ParamsType();
    }

    /**
     * Create an instance of {@link AttributesType }
     * 
     */
    public AttributesType createAttributesType() {
        return new AttributesType();
    }

    /**
     * Create an instance of {@link SamlRequestType }
     * 
     */
    public SamlRequestType createSamlRequestType() {
        return new SamlRequestType();
    }

    /**
     * Create an instance of {@link HttpRequestType }
     * 
     */
    public HttpRequestType createHttpRequestType() {
        return new HttpRequestType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClaveAgentConfType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ClaveAgentConfType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0", name = "claveAgentConf")
    public JAXBElement<ClaveAgentConfType> createClaveAgentConf(ClaveAgentConfType value) {
        return new JAXBElement<ClaveAgentConfType>(_ClaveAgentConf_QNAME, ClaveAgentConfType.class, null, value);
    }

}
