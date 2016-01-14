package com.bcs.wps.elements;//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.11 at 03:37:15 PM CEST 
//


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Formats, encodings, and schemas supported by a process input or output. 
 * 
 * <p>Java class for SupportedComplexDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupportedComplexDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Default" type="{http://www.opengis.net/wps/1.0.0}ComplexDataCombinationType"/>
 *         &lt;element name="Supported" type="{http://www.opengis.net/wps/1.0.0}ComplexDataCombinationsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupportedComplexDataType", propOrder = {
    "_default",
    "supported"
})
@XmlSeeAlso({
    SupportedComplexDataInputType.class
})
public class SupportedComplexDataType {

    @XmlElement(name = "Default", namespace = "http://www.opengis.net/wps/1.0.0", required = true)
    protected ComplexDataCombinationType _default;
    @XmlElement(name = "Supported", namespace = "http://www.opengis.net/wps/1.0.0", required = true)
    protected ComplexDataCombinationsType supported;

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexDataCombinationType }
     *     
     */
    public ComplexDataCombinationType getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexDataCombinationType }
     *     
     */
    public void setDefault(ComplexDataCombinationType value) {
        this._default = value;
    }

    /**
     * Gets the value of the supported property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexDataCombinationsType }
     *     
     */
    public ComplexDataCombinationsType getSupported() {
        return supported;
    }

    /**
     * Sets the value of the supported property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexDataCombinationsType }
     *     
     */
    public void setSupported(ComplexDataCombinationsType value) {
        this.supported = value;
    }

}
