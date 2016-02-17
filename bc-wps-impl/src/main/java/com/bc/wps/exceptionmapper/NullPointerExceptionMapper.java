package com.bc.wps.exceptionmapper;

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
        String exceptionString;
        try {
            exceptionString = JaxbHelper.marshal(exceptionResponse.
                        getGeneralExceptionWithExceptionCauseMessage("A value is missing" +
                                                                     (StringUtils.isNotBlank(exception.getMessage()) ? " : " + exception.getMessage() : ""),
                                                                     exception.getCause()));
        } catch (JAXBException jaxbException) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse jaxbExceptionResponse = new ExceptionResponse();
            exceptionString = jaxbExceptionResponse.getJaxbExceptionResponse();
        }
        return Response.serverError()
                    .entity(exceptionString)
                    .build();
    }
}
