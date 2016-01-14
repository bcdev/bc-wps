package com.bc.wps;

import com.bc.wps.api.IServiceProvider;
import com.bc.wps.exceptions.InvalidRequestException;
import com.bc.wps.exceptions.WpsInvalidParameterValueException;
import com.bc.wps.exceptions.WpsMissingParameterValueException;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.serviceloader.SpiLoader;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;
import com.bcs.wps.elements.CodeType;
import com.bcs.wps.elements.ExceptionReport;
import com.bcs.wps.elements.Execute;
import com.bcs.wps.elements.ObjectFactory;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the entry point of all the WPS Requests. WPS Parameters check are also performed here.
 *
 * @author hans
 */
@Path("/")
public class WpsService {

    private static final Logger LOG = WpsLogger.getLogger();

    @GET
    @Path("/{application}")
    @Produces(MediaType.APPLICATION_XML)
    public String getWpsService(@PathParam("application") String applicationName,
                                @QueryParam("Service") String service,
                                @QueryParam("Request") String requestType,
                                @QueryParam("AcceptVersions") String acceptedVersion,
                                @QueryParam("Language") String language,
                                @QueryParam("Identifier") String processId,
                                @QueryParam("Version") String version,
                                @QueryParam("JobId") String jobId,
                                @Context HttpServletRequest servletRequest) {

        IServiceProvider wpsServiceProvider = SpiLoader.getWpsServiceProvider(applicationName);

        String exceptionXml = performUrlParameterValidation(service, requestType);
        if (StringUtils.isNotBlank(exceptionXml)) {
            return exceptionXml;
        }

        switch (requestType) {
        case "GetCapabilities":
//            return wpsServiceProvider.getCapabilities();
            return wpsServiceProvider.getCapabilities();
        case "DescribeProcess":
//            String describeProcessExceptionXml = performDescribeProcessParameterValidation(processId, version);
//            if (StringUtils.isNotBlank(describeProcessExceptionXml)) {
//                return describeProcessExceptionXml;
//            }
//            return wpsServiceProvider.describeProcess(processId);
            return wpsServiceProvider.describeProcess();
        case "GetStatus":
//            if (StringUtils.isBlank(jobId)) {
//                StringWriter stringWriter = getMissingParameterXmlWriter("JobId");
//                return stringWriter.toString();
//            }
//            return wpsServiceProvider.getStatus(jobId);
            return wpsServiceProvider.getStatus();
        default:
//            StringWriter stringWriter = getInvalidParameterXmlWriter("Request");
//            return stringWriter.toString();
            return "invalid request";
        }
    }

    @POST
    @Path("/{application}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String postExecuteService(@PathParam("application") String applicationName,
                                     String request,
                                     @Context HttpServletRequest servletRequest) {
        IServiceProvider wpsServiceProvider = SpiLoader.getWpsServiceProvider(applicationName);
//        Execute execute = getExecute(request);
//
//        String exceptionXml = performXmlParameterValidation(execute);
//        if (StringUtils.isNotBlank(exceptionXml)) {
//            return exceptionXml;
//        }
//
//        String processorId = execute.getIdentifier().getValue();
//
//        return wpsServiceProvider.doExecute(execute, processorId);
        return wpsServiceProvider.doExecute();
    }



    private String performDescribeProcessParameterValidation(String processorId, String version) {
        if (StringUtils.isBlank(version)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Version");
            return stringWriter.toString();
        }
        if (StringUtils.isBlank(processorId)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Identifier");
            return stringWriter.toString();
        }
        return null;
    }

    private String performUrlParameterValidation(String service, String requestType) {
        if (StringUtils.isBlank(service)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Service");
            return stringWriter.toString();
        }
        if (StringUtils.isBlank(requestType)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Request");
            return stringWriter.toString();
        }
        if (!service.equals("WPS")) {
            StringWriter stringWriter = getInvalidParameterXmlWriter(service);
            return stringWriter.toString();
        }
        return "";
    }

    private String performXmlParameterValidation(Execute execute) {
        String service = execute.getService();
        String version = execute.getVersion();
        CodeType identifier = execute.getIdentifier();

        if (StringUtils.isBlank(service)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Service");
            return stringWriter.toString();
        }
        if (!"WPS".equals(service)) {
            StringWriter stringWriter = getInvalidParameterXmlWriter(service);
            return stringWriter.toString();
        }

        if (StringUtils.isBlank(version)) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Version");
            return stringWriter.toString();
        }

        if (identifier == null || StringUtils.isBlank(identifier.getValue())) {
            StringWriter stringWriter = getMissingParameterXmlWriter("Identifier");
            return stringWriter.toString();
        }
        return "";
    }

    private Execute getExecute(String request) {
        InputStream requestInputStream = new ByteArrayInputStream(request.getBytes());
        JaxbHelper jaxbHelper = new JaxbHelper();
        try {
            return (Execute) jaxbHelper.unmarshal(requestInputStream, new ObjectFactory());
        } catch (ClassCastException exception) {
            throw new InvalidRequestException("Invalid Execute request. Please see the WPS 1.0.0 guideline for the right Execute request structure.",
                                              exception);
        } catch (JAXBException exception) {
            throw new InvalidRequestException("Invalid Execute request. "
                                              + (exception.getMessage() != null ? exception.getMessage() : exception.getCause().getMessage()),
                                              exception);
        }
    }

    private StringWriter getMissingParameterXmlWriter(String missingParameter) {
        WpsMissingParameterValueException exception = new WpsMissingParameterValueException(missingParameter);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ExceptionReport exceptionReport = exceptionResponse.getMissingParameterExceptionResponse(exception, missingParameter);
        return getExceptionStringWriter(exceptionReport);
    }

    private StringWriter getInvalidParameterXmlWriter(String invalidParameter) {
        WpsInvalidParameterValueException exception = new WpsInvalidParameterValueException(invalidParameter);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ExceptionReport exceptionReport = exceptionResponse.getInvalidParameterExceptionResponse(exception, invalidParameter);
        return getExceptionStringWriter(exceptionReport);
    }

    private StringWriter getExceptionStringWriter(ExceptionReport exceptionReport) {
        JaxbHelper jaxbHelper = new JaxbHelper();
        StringWriter stringWriter = new StringWriter();
        try {
            jaxbHelper.marshal(exceptionReport, stringWriter);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS exception.", exception);
        }
        return stringWriter;
    }
}
