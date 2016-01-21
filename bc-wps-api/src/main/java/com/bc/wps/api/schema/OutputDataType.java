package com.bc.wps.api.schema;//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.11 at 03:37:15 PM CEST 
//


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Value of one output from a process. 
 * 
 * In this use, the DescriptionType shall describe this process output. 
 * 
 * <p>Java class for OutputDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/wps/1.0.0}DescriptionType">
 *       &lt;group ref="{http://www.opengis.net/wps/1.0.0}OutputDataFormChoice"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputDataType", propOrder = {
    "reference",
    "data"
})
public class OutputDataType
    extends DescriptionType
{

    @XmlElement(name = "Reference")
    protected OutputReferenceType reference;
    @XmlElement(name = "Data")
    protected DataType data;

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link OutputReferenceType }
     *     
     */
    public OutputReferenceType getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputReferenceType }
     *     
     */
    public void setReference(OutputReferenceType value) {
        this.reference = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setData(DataType value) {
        this.data = value;
    }

}