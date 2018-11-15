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
 * Configuración de la autenticación mutua
 * 			
 * 
 * <p>Clase Java para AuthenticationMutualType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AuthenticationMutualType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="base64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passwordKeyStore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="typeKeyStore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticationMutualType", propOrder = {
    "path",
    "base64",
    "passwordKeyStore",
    "typeKeyStore"
})
public class AuthenticationMutualType {

    protected String path;
    protected String base64;
    @XmlElement(required = true)
    protected String passwordKeyStore;
    @XmlElement(required = true)
    protected String typeKeyStore;

    /**
     * Obtiene el valor de la propiedad path.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Define el valor de la propiedad path.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Obtiene el valor de la propiedad base64.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase64() {
        return base64;
    }

    /**
     * Define el valor de la propiedad base64.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase64(String value) {
        this.base64 = value;
    }

    /**
     * Obtiene el valor de la propiedad passwordKeyStore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasswordKeyStore() {
        return passwordKeyStore;
    }

    /**
     * Define el valor de la propiedad passwordKeyStore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasswordKeyStore(String value) {
        this.passwordKeyStore = value;
    }

    /**
     * Obtiene el valor de la propiedad typeKeyStore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeKeyStore() {
        return typeKeyStore;
    }

    /**
     * Define el valor de la propiedad typeKeyStore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeKeyStore(String value) {
        this.typeKeyStore = value;
    }

}
