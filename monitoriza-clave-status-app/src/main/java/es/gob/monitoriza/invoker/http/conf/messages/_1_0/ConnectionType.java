//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.13 a las 02:46:44 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Configuraci�n de la conexi�n de la petici�n. Opcional.
 * 			
 * 
 * <p>Clase Java para ConnectionType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ConnectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proxy" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}ProxyConfType" minOccurs="0"/>
 *         &lt;element name="authenticationMutual" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}AuthenticationMutualType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
