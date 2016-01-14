package com.bc.wps.exceptionmapper;

import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;
import com.bcs.wps.elements.ExceptionReport;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
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
        StringWriter stringWriter = getExceptionStringWriter(exceptionResponse.
                    getGeneralExceptionWithCustomMessageResponse("A value is missing" +
                                                                 (StringUtils.isNotBlank(exception.getMessage()) ? " : " + exception.getMessage() : ""),
                                                                 exception.getCause()));
        return Response.serverError()
                    .entity(stringWriter.toString())
                    .build();
    }

    private StringWriter getExceptionStringWriter(ExceptionReport exceptionReport) {
        JaxbHelper jaxbHelper = new JaxbHelper();
        StringWriter stringWriter = new StringWriter();
        try {
            jaxbHelper.marshal(exceptionReport, stringWriter);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS Exception.", exception);
        }
        return stringWriter;
    }
}