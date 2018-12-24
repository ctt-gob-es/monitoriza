//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.24 a las 12:49:59 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Configuración de la conexión de la petición. Opcional.
 * 			
 * 
 * <p>Clase Java para ConnectionType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ConnectionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="proxy" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}ProxyConfType" minOccurs="0"/&gt;
 *         &lt;element name="authenticationMutual" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}AuthenticationMutualType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionType", propOrder = {
    "proxy",
    "authenticationMutual"
})
public class ConnectionType {

    protected ProxyConfType proxy;
    protected AuthenticationMutualType authenticationMutual;

    /**
     * Obtiene el valor de la propiedad proxy.
     * 
     * @return
     *     possible object is
     *     {@link ProxyConfType }
     *     
     */
    public ProxyConfType getProxy() {
        return proxy;
    }

    /**
     * Define el valor de la propiedad proxy.
     * 
     * @param value
     *     allowed object is
     *     {@link ProxyConfType }
     *     
     */
    public void setProxy(ProxyConfType value) {
        this.proxy = value;
    }

    /**
     * Obtiene el valor de la propiedad authenticationMutual.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationMutualType }
     *     
     */
    public AuthenticationMutualType getAuthenticationMutual() {
        return authenticationMutual;
    }

    /**
     * Define el valor de la propiedad authenticationMutual.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationMutualType }
     *     
     */
    public void setAuthenticationMutual(AuthenticationMutualType value) {
        this.authenticationMutual = value;
    }

}
