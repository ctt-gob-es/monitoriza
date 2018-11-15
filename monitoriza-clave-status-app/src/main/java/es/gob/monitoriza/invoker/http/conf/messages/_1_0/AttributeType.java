//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.11.15 a las 12:33:07 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages._1_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para AttributeType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="AttributeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="familyName"/>
 *     &lt;enumeration value="firstName"/>
 *     &lt;enumeration value="birthDateOfBirth"/>
 *     &lt;enumeration value="personIdentifier"/>
 *     &lt;enumeration value="birthName"/>
 *     &lt;enumeration value="placeOfBirth"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttributeType")
@XmlEnum
public enum AttributeType {

    @XmlEnumValue("familyName")
    FAMILY_NAME("familyName"),
    @XmlEnumValue("firstName")
    FIRST_NAME("firstName"),
    @XmlEnumValue("birthDateOfBirth")
    BIRTH_DATE_OF_BIRTH("birthDateOfBirth"),
    @XmlEnumValue("personIdentifier")
    PERSON_IDENTIFIER("personIdentifier"),
    @XmlEnumValue("birthName")
    BIRTH_NAME("birthName"),
    @XmlEnumValue("placeOfBirth")
    PLACE_OF_BIRTH("placeOfBirth");
    private final String value;

    AttributeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttributeType fromValue(String v) {
        for (AttributeType c: AttributeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
