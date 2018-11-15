//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.15 a las 11:03:34 AM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Configuración de la petición Saml
 * 			
 * 
 * <p>Clase Java para SamlRequestType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SamlRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assertionConsumerServiceURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SPApplication" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SPType" type="{urn:es:gob:monitoriza:invoker:http:conf:messages:1.0.0}SPRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SamlRequestType", propOrder = {
    "assertionConsumerServiceURL",
    "spApplication",
    "providerName",
    "spType"
})
public class SamlRequestType {

    @XmlElement(required = true)
    protected String assertionConsumerServiceURL;
    @XmlElement(name = "SPApplication", required = true)
    protected String spApplication;
    @XmlElement(required = true)
    protected String providerName;
    @XmlElement(name = "SPType", required = true)
    @XmlSchemaType(name = "string")
    protected SPRequestType spType;

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

}
