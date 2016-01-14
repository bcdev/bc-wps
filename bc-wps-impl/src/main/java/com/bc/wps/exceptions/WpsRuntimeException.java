package com.bc.wps.exceptions;

/**
 * @author hans
 */
public class WpsRuntimeException extends RuntimeException {

    public WpsRuntimeException(String message) {
        super(message);
    }

    public WpsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WpsRuntimeException(Throwable cause) {
        super(cause);
    }

}
