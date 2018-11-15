package com.bc.wps.api.exceptions;

/**
 * Use this exception when operation request contains an invalid parameter value (OGC 05-007r7 Table 62).
 *
 * @author hans
 */
public class InvalidParameterValueException extends WpsServiceException {

    /**
     * @param message          The new exception message
     * @param cause            The exception
     * @param invalidParameter The name of parameter with invalid value
     */
    public InvalidParameterValueException(String message, Throwable cause, String invalidParameter) {
        super(message + " : parameter '" + invalidParameter + "' has an invalid value.", invalidParameter ,cause );
    }

    /**
     * @param cause            The exception
     * @param invalidParameter The name of parameter with invalid value
     */
    public InvalidParameterValueException(Throwable cause, String invalidParameter) {
        super("Parameter '" + invalidParameter + "' has an invalid value.", invalidParameter,cause);
    }

    /**
     * @param invalidParameter The name of parameter with invalid value
     */
    public InvalidParameterValueException(String invalidParameter) {
        super("Parameter '" + invalidParameter + "' has an invalid value.", invalidParameter);
    }

    public String getInvalidParameter() {
        return super.getLocator();
    }

}
