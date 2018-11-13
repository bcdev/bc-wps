package com.bc.wps.api.exceptions;

/**
 * Use this exception when operation request does not include a parameter value
 * and this server did not declare a default value for that parameter (OGC 05-007r7 Table 62).
 *
 * @author Sabine
 */
public class OptionNotSupportedException extends WpsServiceException {

    public OptionNotSupportedException(String message) {
        super(message);
    }
}
