//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.15 a las 01:46:28 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Configuración de la conexión de la petición. Opcional.
 * 			
 * 
 * <p>Clase Java para RequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="httpRequest" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}HttpRequestType" minOccurs="0"/>
 *         &lt;element name="samlRequest" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}SamlRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "httpRequest",
    "samlRequest"
})
public class RequestType {

    protected HttpRequestType httpRequest;
    @XmlElement(required = true)
    protected SamlRequestType samlRequest;

    /**
     * Obtiene el valor de la propiedad httpRequest.
     * 
     * @return
     *     possible object is
     *     {@link HttpRequestType }
     *     
     */
    public HttpRequestType getHttpRequest() {
        return httpRequest;
    }

    /**
     * Define el valor de la propiedad httpRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link HttpRequestType }
     *     
     */
    public void setHttpRequest(HttpRequestType value) {
        this.httpRequest = value;
    }

    /**
     * Obtiene el valor de la propiedad samlRequest.
     * 
     * @return
     *     possible object is
     *     {@link SamlRequestType }
     *     
     */
    public SamlRequestType getSamlRequest() {
        return samlRequest;
    }

    /**
     * Define el valor de la propiedad samlRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link SamlRequestType }
     *     
     */
    public void setSamlRequest(SamlRequestType value) {
        this.samlRequest = value;
    }

}
