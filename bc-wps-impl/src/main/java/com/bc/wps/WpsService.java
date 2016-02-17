package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceException;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ObjectFactory;
import com.bc.wps.api.schema.ProcessDescriptionType;
import com.bc.wps.api.schema.ProcessDescriptions;
import com.bc.wps.exceptions.InvalidRequestException;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.serviceloader.SpiLoader;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.WpsLogger;
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
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the entry point to the WPS server. Several actions are performed here:
 * <ul>
 * <li>Mapping of service instance</li>
 * <li>Parameter validity check</li>
 * <li>Marshalling and Unmarshalling</li>
 * <li>Exception handling</li>
 * </ul>
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

        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServerContext serverContext = requestContext.getServerContext();
        WpsServiceInstance wpsServiceProvider = SpiLoader.getWpsServiceProvider(serverContext, applicationName);

        String exceptionXml = performUrlParameterValidation(service, requestType);
        if (StringUtils.isNotBlank(exceptionXml)) {
            return exceptionXml;
        }

        try {
            switch (requestType) {
            case "GetCapabilities":
                Capabilities capabilities = wpsServiceProvider.getCapabilities(requestContext);
                return JaxbHelper.marshal(capabilities);
            case "DescribeProcess":
                String describeProcessExceptionXml = performDescribeProcessParameterValidation(processId, version);
                if (StringUtils.isNotBlank(describeProcessExceptionXml)) {
                    return describeProcessExceptionXml;
                }
                List<ProcessDescriptionType> processDescriptionTypes = wpsServiceProvider.describeProcess(requestContext, processId);
                ProcessDescriptions processDescriptions = constructProcessDescriptionXml(processDescriptionTypes);
                return JaxbHelper.marshal(processDescriptions);
            case "GetStatus":
                if (StringUtils.isBlank(jobId)) {
                    return getMissingParameterXmlWriter("JobId");
                }
                ExecuteResponse executeResponse = wpsServiceProvider.getStatus(requestContext, jobId);
                return JaxbHelper.marshal(executeResponse);
            default:
                return getInvalidParameterXmlWriter("Request");
            }
        } catch (WpsServiceException exception) {
            LOG.log(Level.SEVERE, "Unable to process the WPS request", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            ExceptionReport exceptionReport = exceptionResponse.getExceptionResponse(exception);
            return getExceptionString(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            return exceptionResponse.getJaxbExceptionResponse();
        }
    }

    @POST
    @Path("/{application}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String postExecuteService(@PathParam("application") String applicationName,
                                     String request,
                                     @Context HttpServletRequest servletRequest) {
        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServerContext serverContext = requestContext.getServerContext();
        WpsServiceInstance wpsServiceProvider = SpiLoader.getWpsServiceProvider(serverContext, applicationName);
        Execute execute = getExecute(request);

        String exceptionXml = performXmlParameterValidation(execute);
        if (StringUtils.isNotBlank(exceptionXml)) {
            return exceptionXml;
        }

        try {
            ExecuteResponse executeResponse = wpsServiceProvider.doExecute(requestContext, execute);
            return JaxbHelper.marshal(executeResponse);
        } catch (WpsServiceException exception) {
            LOG.log(Level.SEVERE, "Unable to process the WPS request", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            ExceptionReport exceptionReport = exceptionResponse.getExceptionResponse(exception);
            return getExceptionString(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            return exceptionResponse.getJaxbExceptionResponse();
        }
    }

    private String performDescribeProcessParameterValidation(String processorId, String version) {
        if (StringUtils.isBlank(version)) {
            return getMissingParameterXmlWriter("Version");
        }
        if (StringUtils.isBlank(processorId)) {
            return getMissingParameterXmlWriter("Identifier");
        }
        return null;
    }

    private String performUrlParameterValidation(String service, String requestType) {
        if (StringUtils.isBlank(service)) {
            return getMissingParameterXmlWriter("Service");
        }
        if (StringUtils.isBlank(requestType)) {
            return getMissingParameterXmlWriter("Request");
        }
        if (!service.equals("WPS")) {
            return getInvalidParameterXmlWriter("Service");
        }
        return "";
    }

    private String performXmlParameterValidation(Execute execute) {
        String service = execute.getService();
        String version = execute.getVersion();
        CodeType identifier = execute.getIdentifier();

        if (StringUtils.isBlank(service)) {
            return getMissingParameterXmlWriter("Service");
        }
        if (!"WPS".equals(service)) {
            return getInvalidParameterXmlWriter(service);
        }

        if (StringUtils.isBlank(version)) {
            return getMissingParameterXmlWriter("Version");
        }

        if (identifier == null || StringUtils.isBlank(identifier.getValue())) {
            return getMissingParameterXmlWriter("Identifier");
        }
        return "";
    }

    private Execute getExecute(String request) {
        InputStream requestInputStream = new ByteArrayInputStream(request.getBytes());
        try {
            return (Execute) JaxbHelper.unmarshal(requestInputStream, new ObjectFactory());
        } catch (ClassCastException exception) {
            throw new InvalidRequestException("Invalid Execute request. Please see the WPS 1.0.0 guideline " +
                                              "for the right Execute request structure.",
                                              exception);
        } catch (JAXBException exception) {
            throw new InvalidRequestException(
                        "Invalid Execute request. "
                        + (exception.getMessage() != null ? exception.getMessage() : exception.getCause().getMessage()),
                        exception);
        }
    }

    private String getMissingParameterXmlWriter(String missingParameter) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ExceptionReport exceptionReport = exceptionResponse.getMissingParameterExceptionResponse(
                    "Missing parameter value", missingParameter);
        return getExceptionString(exceptionReport);
    }

    private String getInvalidParameterXmlWriter(String invalidParameter) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ExceptionReport exceptionReport = exceptionResponse.getInvalidParameterExceptionResponse(
                    "Invalid value of parameter '" + invalidParameter + "'", invalidParameter);
        return getExceptionString(exceptionReport);
    }

    private String getExceptionString(ExceptionReport exceptionReport) {
        try {
            return JaxbHelper.marshal(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS exception.", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            return exceptionResponse.getJaxbExceptionResponse();
        }
    }

    private ProcessDescriptions constructProcessDescriptionXml(List<ProcessDescriptionType> processes) {
        ProcessDescriptions processDescriptions = createBasicProcessDescriptions();
        for (ProcessDescriptionType process : processes) {
            processDescriptions.getProcessDescription().add(process);
        }
        return processDescriptions;
    }

    private ProcessDescriptions createBasicProcessDescriptions() {
        ProcessDescriptions processDescriptions = new ProcessDescriptions();
        processDescriptions.setService("WPS");
        processDescriptions.setVersion("1.0.0");
        processDescriptions.setLang("en");
        return processDescriptions;
    }
}
