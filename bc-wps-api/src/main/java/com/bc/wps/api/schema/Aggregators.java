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
@XmlType(name = "aggregators", propOrder = {
            "aggregator"
})
public class Aggregators {

    @XmlElement(name = "aggregator", namespace = "http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd", nillable = false, required = true)
    protected List<AggregatorConfig> aggregator;

    public List<AggregatorConfig> getAggregator() {
        if (aggregator == null) {
            aggregator = new ArrayList<>();
        }
        return aggregator;
    }
}
