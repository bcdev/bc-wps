package com.bc.wps.exceptionmapper;

import com.bc.wps.exceptions.WpsRuntimeException;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;
import com.sun.org.apache.xerces.internal.util.Status;

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
        String exceptionString;
        try {
            exceptionString = JaxbHelper.marshal(exceptionResponse.getExceptionResponse(exception));
        } catch (JAXBException jaxbException) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse jaxbExceptionResponse = new ExceptionResponse();
            exceptionString = jaxbExceptionResponse.getJaxbExceptionResponse();
        }
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(exceptionString)
                    .build();
    }
}
