package com.bc.wps.responses;


import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.api.schema.ExceptionType;

/**
 * @author hans
 */
public class ExceptionResponse {

    public ExceptionReport getGeneralExceptionWithExceptionCauseMessage(String errorMessage, Throwable cause) {
        return getGeneralExceptionReport(errorMessage, cause);
    }

    public ExceptionReport getExceptionResponse(Exception exception) {
        return getGeneralExceptionReport(exception.getMessage(), exception.getCause());
    }

    public ExceptionReport getMissingParameterExceptionResponse(String exceptionMessage, String missingParameter) {
        ExceptionReport exceptionReport = new ExceptionReport();
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(exceptionMessage);
        exceptionResponse.setExceptionCode("MissingParameterValue");
        exceptionResponse.setLocator(missingParameter);

        exceptionReport.getException().add(exceptionResponse);
        exceptionReport.setLang("Lang");
        exceptionReport.setVersion("version");

        return exceptionReport;
    }

    public ExceptionReport getInvalidParameterExceptionResponse(String exceptionMessage, String invalidParameter) {
        ExceptionReport exceptionReport = new ExceptionReport();
        ExceptionType exceptionResponse = new ExceptionType();
        exceptionResponse.getExceptionText().add(exceptionMessage);
        exceptionResponse.setExceptionCode("InvalidParameterValue");
        exceptionResponse.setLocator(invalidParameter);

        exceptionReport.getException().add(exceptionResponse);
        exceptionReport.setLang("Lang");
        exceptionReport.setVersion("version");

        return exceptionReport;
    }

    public String getJaxbExceptionResponse(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<ExceptionReport version=\"version\" xml:lang=\"Lang\">\n" +
               "    <Exception exceptionCode=\"NoApplicableCode\">\n" +
               "        <ExceptionText>Unable to generate the exception XML : JAXB Exception.</ExceptionText>\n" +
               "    </Exception>\n" +
               "</ExceptionReport>\n";
    }

    private ExceptionReport getGeneralExceptionReport(String exceptionMessage, Throwable cause) {
        ExceptionReport exceptionReport = new ExceptionReport();
        ExceptionType exceptionResponse = new ExceptionType();
        if (cause != null) {
            exceptionResponse.getExceptionText().add(exceptionMessage + " : " + cause.getMessage());
        } else {
            exceptionResponse.getExceptionText().add(exceptionMessage);
        }
        exceptionResponse.setExceptionCode("NoApplicableCode");

        exceptionReport.getException().add(exceptionResponse);
        exceptionReport.setLang("Lang");
        exceptionReport.setVersion("version");
        return exceptionReport;
    }

}
