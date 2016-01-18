package com.bc.wps.api;

/**
 * @author hans
 */
public class WpsServiceException extends Exception {

    public WpsServiceException(String message) {
        super(message);
    }

    public WpsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WpsServiceException(Throwable cause) {
        super(cause);
    }

}
