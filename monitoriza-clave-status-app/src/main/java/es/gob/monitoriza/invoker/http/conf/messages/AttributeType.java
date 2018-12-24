//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.24 a las 12:49:59 PM CET 
//


package es.gob.monitoriza.invoker.http.conf.messages;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para AttributeType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="AttributeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="familyName"/&gt;
 *     &lt;enumeration value="firstName"/&gt;
 *     &lt;enumeration value="dateOfBirth"/&gt;
 *     &lt;enumeration value="personIdentifier"/&gt;
 *     &lt;enumeration value="legalName"/&gt;
 *     &lt;enumeration value="legalPersonIdentifier"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
    @XmlEnumValue("dateOfBirth")
    DATE_OF_BIRTH("dateOfBirth"),
    @XmlEnumValue("personIdentifier")
    PERSON_IDENTIFIER("personIdentifier"),
    @XmlEnumValue("legalName")
    LEGAL_NAME("legalName"),
    @XmlEnumValue("legalPersonIdentifier")
    LEGAL_PERSON_IDENTIFIER("legalPersonIdentifier");
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
