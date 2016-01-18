package com.bc.wps.api.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hans
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "variables", propOrder = {
            "variable"
})
public class Variables {

    @XmlElement(name = "variable", nillable = false, required = true)
    protected List<VariableConfig> variable;

    public List<VariableConfig> getVariable() {
        if (variable == null) {
            variable = new ArrayList<>();
        }
        return variable;
    }

}
