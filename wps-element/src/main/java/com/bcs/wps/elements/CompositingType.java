package com.bcs.wps.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author hans
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompositingType", propOrder = {
})
public enum CompositingType {
    BINNING,
    MOSAICKING;

    CompositingType() {
    }
}
