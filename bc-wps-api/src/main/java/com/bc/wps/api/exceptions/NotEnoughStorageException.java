package com.bc.wps.api.exceptions;

/**
 * Use this exception when the server does not have enough space available to store
 * the inputs and outputs associated with the request (OGC 05-007r7 Table 62).
 *
 * @author hans
 */
public class NotEnoughStorageException extends WpsServiceException {

    public NotEnoughStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStorageException(Throwable cause) {
        super(cause);
    }

}
