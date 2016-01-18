package com.bc.wps.exceptionmapper;

import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class maps any NullPointerException to a proper WPS Exception response.
 *
 * @author hans
 */
@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {

    private static final Logger LOG = WpsLogger.getLogger();

    @Override
    public Response toResponse(NullPointerException exception) {
        LOG.log(Level.SEVERE, "A NullPointerException has been caught.", exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        String exceptionString = getExceptionString(exceptionResponse.
                    getGeneralExceptionWithCustomMessageResponse("A value is missing" +
                                                                 (StringUtils.isNotBlank(exception.getMessage()) ? " : " + exception.getMessage() : ""),
                                                                 exception.getCause()));
        return Response.serverError()
                    .entity(exceptionString)
                    .build();
    }

    private String getExceptionString(ExceptionReport exceptionReport) {
        try {
            return JaxbHelper.marshal(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS Exception.", exception);
            return getDefaultWpsJaxbExceptionResponse();
        }
    }

    private String getDefaultWpsJaxbExceptionResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<ExceptionReport version=\"version\" xml:lang=\"Lang\">\n" +
               "    <Exception exceptionCode=\"NoApplicableCode\">\n" +
               "        <ExceptionText>Unable to generate the exception XML : JAXB Exception.</ExceptionText>\n" +
               "    </Exception>\n" +
               "</ExceptionReport>\n";
    }
}
