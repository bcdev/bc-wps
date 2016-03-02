package com.bc.wps.exceptions;

import com.bc.wps.api.WpsRuntimeException;

/**
 * @author hans
 */
public class InvalidRequestException extends WpsRuntimeException {

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
