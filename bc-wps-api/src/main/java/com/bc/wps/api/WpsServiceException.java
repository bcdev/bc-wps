package com.bc.wps.api;

/**
 * Use this exception for any WPS-operation-related errors.
 *
 * @author hans
 */
public class WpsServiceException extends Exception {

    public WpsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WpsServiceException(Throwable cause) {
        super(cause);
    }

}
