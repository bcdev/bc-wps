package com.bcs.wps.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author hans
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggregatorConfig", propOrder = {
            "type",
            "varName"
})
public class AggregatorConfig {

    @XmlElement(name = "type")
    protected String type;
    @XmlElement(name = "varName")
    protected String varName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
