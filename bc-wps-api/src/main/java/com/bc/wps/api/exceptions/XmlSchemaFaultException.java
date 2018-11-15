/*
 * $Id$
 *
 * Copyright (C) 2010 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bc.wps.api.exceptions;

public class XmlSchemaFaultException extends WpsServiceException {

    public XmlSchemaFaultException(String missedElement, String parentElemet) {
        super(createMessage(missedElement, parentElemet), parentElemet);
    }

    public String getParentElement() {
        return getLocator();
    }

    private static String createMessage(String missedElement, String parentElemet) {
        return "XML schema fault: Element '" + missedElement + "' missed in element '" + parentElemet + "'.";
    }
}
