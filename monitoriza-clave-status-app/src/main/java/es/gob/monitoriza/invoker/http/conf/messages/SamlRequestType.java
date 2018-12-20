//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantacion de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderan si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.18 a las 09:12:39 AM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Configuracion de la peticion Saml
 * 			
 * 
 * <p>Clase Java para SamlRequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SamlRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="assertionConsumerServiceURL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SPApplication" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SPType" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}SPRequestType" minOccurs="0"/&gt;
 *         &lt;element name="attributes" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}AttributesType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SamlRequestType", propOrder = {
    "assertionConsumerServiceURL",
    "spApplication",
    "providerName",
    "spType",
    "attributes"
})
public class SamlRequestType {

    @XmlElement(required = true)
    protected String assertionConsumerServiceURL;
    @XmlElement(name = "SPApplication", required = true)
    protected String spApplication;
    @XmlElement(required = true)
    protected String providerName;
    @XmlElement(name = "SPType")
    @XmlSchemaType(name = "string")
    protected SPRequestType spType;
    protected AttributesType attributes;

    /**
     * Obtiene el valor de la propiedad assertionConsumerServiceURL.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssertionConsumerServiceURL() {
        return assertionConsumerServiceURL;
    }

    /**
     * Define el valor de la propiedad assertionConsumerServiceURL.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssertionConsumerServiceURL(String value) {
        this.assertionConsumerServiceURL = value;
    }

    /**
     * Obtiene el valor de la propiedad spApplication.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPApplication() {
        return spApplication;
    }

    /**
     * Define el valor de la propiedad spApplication.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPApplication(String value) {
        this.spApplication = value;
    }

    /**
     * Obtiene el valor de la propiedad providerName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Define el valor de la propiedad providerName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Obtiene el valor de la propiedad spType.
     * 
     * @return
     *     possible object is
     *     {@link SPRequestType }
     *     
     */
    public SPRequestType getSPType() {
        return spType;
    }

    /**
     * Define el valor de la propiedad spType.
     * 
     * @param value
     *     allowed object is
     *     {@link SPRequestType }
     *     
     */
    public void setSPType(SPRequestType value) {
        this.spType = value;
    }

    /**
     * Obtiene el valor de la propiedad attributes.
     * 
     * @return
     *     possible object is
     *     {@link AttributesType }
     *     
     */
    public AttributesType getAttributes() {
        return attributes;
    }

    /**
     * Define el valor de la propiedad attributes.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributesType }
     *     
     */
    public void setAttributes(AttributesType value) {
        this.attributes = value;
    }

}
