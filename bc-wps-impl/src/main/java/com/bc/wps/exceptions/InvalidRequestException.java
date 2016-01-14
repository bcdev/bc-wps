package com.bc.wps.exceptions;

/**
 * @author hans
 */
public class InvalidRequestException extends WpsRuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(Throwable cause) {
        super(cause);
    }

}
