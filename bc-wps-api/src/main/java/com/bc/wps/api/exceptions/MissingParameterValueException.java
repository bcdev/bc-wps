package com.bc.wps.api.exceptions;

/**
 * Use this exception when operation request does not include a parameter value
 * and this server did not declare a default value for that parameter (OGC 05-007r7 Table 62).
 *
 * @author hans
 */
public class MissingParameterValueException extends WpsServiceException {

    private final String missingParameter;

    /**
     * @param message          Exception message
     * @param cause            The exception
     * @param missingParameter The parameter name of the missing value
     */
    public MissingParameterValueException(String message, Throwable cause, String missingParameter) {
        super("The value of parameter '" + missingParameter + "' is missing.");
        this.missingParameter = missingParameter;
    }

    /**
     * @param cause            The exception
     * @param missingParameter The parameter name of the missing value
     */
    public MissingParameterValueException(Throwable cause, String missingParameter) {
        super("The value of parameter '" + missingParameter + "' is missing.");
        this.missingParameter = missingParameter;
    }

    /**
     * @param missingParameter The parameter name of the missing value
     */
    public MissingParameterValueException(String missingParameter) {
        super("The value of parameter '" + missingParameter + "' is missing.");
        this.missingParameter = missingParameter;
    }

    public String getMissingParameter() {
        return missingParameter;
    }

}
