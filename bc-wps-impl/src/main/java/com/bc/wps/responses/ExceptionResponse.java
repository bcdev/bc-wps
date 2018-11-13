package com.bc.wps.responses;

import com.bc.wps.api.exceptions.InvalidParameterValueException;
import com.bc.wps.api.exceptions.MissingParameterValueException;
import com.bc.wps.api.exceptions.NoApplicableCodeException;
import com.bc.wps.api.exceptions.NotEnoughStorageException;
import com.bc.wps.api.exceptions.OptionNotSupportedException;
import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.api.schema.ExceptionType;
import com.bc.wps.api.schema.ProcessFailedType;

/**
 * @author hans
 */
public class ExceptionResponse {

    private ExceptionReport exceptionReport;

    public ExceptionResponse() {
        ProcessFailedType processFailedType = new ProcessFailedType();
        exceptionReport = new ExceptionReport();
        exceptionReport.setLang("en");
        exceptionReport.setVersion("1.0.0");
    }

//    public ExceptionReport getGeneralExceptionWithExceptionCauseMessage(String errorMessage, Throwable cause) {
//        return getGeneralExceptionReport(errorMessage, cause);
//    }

    public ExceptionReport getExceptionResponse(Exception exception) {
        if (exception instanceof InvalidParameterValueException) {
            return getInvalidParameterExceptionResponse((InvalidParameterValueException) exception);
        } else if (exception instanceof MissingParameterValueException) {
            return getMissingParameterExceptionResponse((MissingParameterValueException) exception);
        } else if (exception instanceof NotEnoughStorageException) {
            return getNotEnoughStorageExceptionResponse((NotEnoughStorageException) exception);
        } else if (exception instanceof NoApplicableCodeException) {
            return getNoApplicableCodeExceptionResponse((NoApplicableCodeException) exception);
        } else if (exception instanceof OptionNotSupportedException) {
            return getOptionNotSupportedExceptionResponse((OptionNotSupportedException) exception);
        } else {
            return getGeneralExceptionReport(exception);
        }
    }

    public String getJaxbExceptionResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<ExceptionReport version=\"version\" xml:lang=\"Lang\">\n" +
               "    <Exception exceptionCode=\"NoApplicableCode\">\n" +
               "        <ExceptionText>Unable to generate the exception XML : JAXB Exception.</ExceptionText>\n" +
               "    </Exception>\n" +
               "</ExceptionReport>\n";
    }

    private ExceptionReport getInvalidParameterExceptionResponse(InvalidParameterValueException invalidParameterException) {
        final String message = invalidParameterException.getMessage();
        final String locator = invalidParameterException.getInvalidParameter();
        return createReport(message, "InvalidParameterValue", locator);
    }

    private ExceptionReport getMissingParameterExceptionResponse(MissingParameterValueException missingParameterValueException) {
        final String message = missingParameterValueException.getMessage();
        final String locator = missingParameterValueException.getMissingParameter();
        return createReport(message, "MissingParameterValue", locator);
    }

    private ExceptionReport getNoApplicableCodeExceptionResponse(NoApplicableCodeException noApplicableCodeException) {
        final String message = noApplicableCodeException.getMessage();
        return createReport(message, "NoApplicableCode", null);
    }

    private ExceptionReport getNotEnoughStorageExceptionResponse(NotEnoughStorageException notEnoughStorageException) {
        final String message = notEnoughStorageException.getMessage();
        return createReport(message, "NotEnoughStorage", null);
    }

    private ExceptionReport getOptionNotSupportedExceptionResponse(OptionNotSupportedException optionNotSupportedException) {
        final String message = optionNotSupportedException.getMessage();
        return createReport(message, "OptionNotSupported", null);
    }

    private ExceptionReport getGeneralExceptionReport(Exception exception) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause != null) {
            message = message + " : " + cause.getMessage();
        } else {
            message = exception.getClass() + " : " + message;
        }
        return createReport(message, "NoApplicableCode", null);
    }

    private ExceptionReport createReport(String message, String exceptionCode, String locator) {
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(message);
        exceptionResponse.setExceptionCode(exceptionCode);
        if (locator != null) {
            exceptionResponse.setLocator(locator);
        }
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }
}
