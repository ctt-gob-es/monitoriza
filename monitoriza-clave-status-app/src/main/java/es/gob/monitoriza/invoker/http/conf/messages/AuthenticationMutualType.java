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
import javax.xml.bind.annotation.XmlType;


/**
 * Configuraci�n de la autenticacion mutua
 * 			
 * 
 * <p>Clase Java para AuthenticationMutualType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AuthenticationMutualType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="base64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="passwordKeyStore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="typeKeyStore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
