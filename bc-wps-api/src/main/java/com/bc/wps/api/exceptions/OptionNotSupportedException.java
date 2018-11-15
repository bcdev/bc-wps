package com.bc.wps.api.exceptions;

/**
 * Use this exception when an execute request declares a not supported option.
 * See subclause 10.3.3 [OGC 05-007r7].
 *
 * @author Sabine
 */
public class OptionNotSupportedException extends WpsServiceException {

    public OptionNotSupportedException(String message, String identifierOfOptionNotSupported) {
        super(message, identifierOfOptionNotSupported);
    }

    public String getIdentifierOfOptionNotSupported() {
        return getLocator();
    }
}
