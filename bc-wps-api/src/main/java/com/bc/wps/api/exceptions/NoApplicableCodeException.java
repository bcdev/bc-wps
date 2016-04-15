package com.bc.wps.api.exceptions;

/**
 * Use this exception when no other exceptionCode specified by this
 * service and server applies to this exception (OGC 05-007r7 Table 62).
 *
 * @author hans
 */
public class NoApplicableCodeException extends WpsServiceException {

    public NoApplicableCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoApplicableCodeException(Throwable cause) {
        super(cause);
    }

}
