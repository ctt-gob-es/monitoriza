//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.15 a las 01:46:28 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SPRequestType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="SPRequestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="public"/>
 *     &lt;enumeration value="private"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SPRequestType")
@XmlEnum
public enum SPRequestType {

    @XmlEnumValue("public")
    PUBLIC("public"),
    @XmlEnumValue("private")
    PRIVATE("private");
    private final String value;

    SPRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SPRequestType fromValue(String v) {
        for (SPRequestType c: SPRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
