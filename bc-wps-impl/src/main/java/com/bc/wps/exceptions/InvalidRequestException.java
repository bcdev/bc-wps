package com.bc.wps.exceptions;

import com.bc.wps.api.exceptions.WpsRuntimeException;

/**
 * @author hans
 */
public class InvalidRequestException extends WpsRuntimeException {

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
