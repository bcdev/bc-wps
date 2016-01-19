package com.bc.wps.exceptionmapper;

import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.exceptions.WpsRuntimeException;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class maps any unhandled WpsRuntimeException to a proper WPS Exception response.
 *
 * @author hans
 */
@Provider
public class WpsRuntimeExceptionMapper implements ExceptionMapper<WpsRuntimeException> {

    private static final Logger LOG = WpsLogger.getLogger();

    @Override
    public Response toResponse(WpsRuntimeException exception) {
        LOG.log(Level.SEVERE, "A WpsRuntimeException has been caught.", exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        String exceptionString = getExceptionString(exceptionResponse.getExceptionResponse(exception));
        return Response.serverError()
                    .entity(exceptionString)
                    .build();
    }

    private String getExceptionString(ExceptionReport exceptionReport) {
        try {
            return JaxbHelper.marshal(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS Exception.", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            return exceptionResponse.getJaxbExceptionResponse();
        }
    }
}
