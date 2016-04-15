package com.bc.wps.responses;


import com.bc.wps.api.exceptions.InvalidParameterValueException;
import com.bc.wps.api.exceptions.MissingParameterValueException;
import com.bc.wps.api.exceptions.NoApplicableCodeException;
import com.bc.wps.api.exceptions.NotEnoughStorageException;
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
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(invalidParameterException.getMessage());
        exceptionResponse.setExceptionCode("InvalidParameterValue");
        exceptionResponse.setLocator(invalidParameterException.getInvalidParameter());
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }

    private ExceptionReport getMissingParameterExceptionResponse(MissingParameterValueException missingParameterValueException) {
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(missingParameterValueException.getMessage());
        exceptionResponse.setExceptionCode("MissingParameterValue");
        exceptionResponse.setLocator(missingParameterValueException.getMissingParameter());
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }

    private ExceptionReport getNoApplicableCodeExceptionResponse(NoApplicableCodeException noApplicableCodeException) {
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(noApplicableCodeException.getMessage());
        exceptionResponse.setExceptionCode("NoApplicableCode");
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }

    private ExceptionReport getNotEnoughStorageExceptionResponse(NotEnoughStorageException notEnoughStorageException) {
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(notEnoughStorageException.getMessage());
        exceptionResponse.setExceptionCode("NotEnoughStorage");
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }

    private ExceptionReport getGeneralExceptionReport(Exception exception) {
        ExceptionType exceptionResponse = new ExceptionType();
        Throwable cause = exception.getCause();
        String exceptionMessage = exception.getMessage();
        if (cause != null) {
            exceptionResponse.getExceptionText().add(exceptionMessage + " : " + cause.getMessage());
        } else {
            exceptionResponse.getExceptionText().add(exception.getClass() + " : " + exceptionMessage);
        }
        exceptionResponse.setExceptionCode("NoApplicableCode");
        exceptionReport.getException().add(exceptionResponse);
        return exceptionReport;
    }

}
