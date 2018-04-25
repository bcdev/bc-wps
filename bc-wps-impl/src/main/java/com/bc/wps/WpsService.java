package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.exceptions.InvalidParameterValueException;
import com.bc.wps.api.exceptions.MissingParameterValueException;
import com.bc.wps.api.exceptions.WpsServiceException;
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
import com.bc.wps.utilities.UrlUtils;
import com.bc.wps.utilities.WpsLogger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static final String REQUEST_ID = "requestId";
    private static final String UTF_8 = "UTF-8";
    private static final String TEMP_DIRECTORY = "/tmp";
    private static final String REQUEST_FILE_PREFIX = "request-";

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

        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REQUEST_ID.equalsIgnoreCase(cookie.getName())) {
                    return postExecuteService(applicationName, requestType, servletRequest);
                }
                if ("queryString".equalsIgnoreCase(cookie.getName())) {
                    String value = cookie.getValue();
                    try {
                        if (service == null) {
                            service = UrlUtils.parseParameter(value, "Service");
                            System.out.println("Setting service to " + service);
                        }
                        if (requestType == null) {
                            requestType = UrlUtils.parseParameter(value, "Request");
                            System.out.println("Setting requestType to " + requestType);
                        }
                        if (version == null) {
                            version = UrlUtils.parseParameter(value, "Version");
                            System.out.println("Setting version to " + version);
                        }
                        if (processId == null) {
                            processId = UrlUtils.parseParameter(value, "Identifier");
                            System.out.println("Setting processId to " + processId);
                        }
                    } catch (UnsupportedEncodingException exception) {
                        LOG.log(Level.SEVERE, "Unable to process the WPS request", exception);
                        ExceptionResponse exceptionResponse = new ExceptionResponse();
                        ExceptionReport exceptionReport = exceptionResponse.getExceptionResponse(exception);
                        return getExceptionString(exceptionReport);
                    }
                }
            }
        }

        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServerContext serverContext = requestContext.getServerContext();
        WpsServiceInstance wpsServiceProvider = SpiLoader.getWpsServiceProvider(serverContext, applicationName);


        try {
            String exceptionXml = performUrlParameterValidation(service, requestType);
            if (StringUtils.isNotBlank(exceptionXml)) {
                return exceptionXml;
            }
            switch (requestType) {
            case "GetCapabilities":
                Capabilities capabilities = wpsServiceProvider.getCapabilities(requestContext);
                return JaxbHelper.marshalWithSchemaLocation(capabilities, "http://www.opengis.net/wps/1.0.0 " +
                                                                          "http://schemas.opengis.net/wps/1.0.0/wpsGetCapabilities_response.xsd");
            case "DescribeProcess":
                String describeProcessExceptionXml = performDescribeProcessParameterValidation(processId, version);
                if (StringUtils.isNotBlank(describeProcessExceptionXml)) {
                    return describeProcessExceptionXml;
                }
                List<ProcessDescriptionType> processDescriptionTypes = wpsServiceProvider.describeProcess(
                            requestContext, processId);
                ProcessDescriptions processDescriptions = constructProcessDescriptionXml(processDescriptionTypes);
                return JaxbHelper.marshalWithSchemaLocation(processDescriptions, "http://www.opengis.net/wps/1.0.0 " +
                                                                                 "http://schemas.opengis.net/wps/1.0.0/wpsDescribeProcess_response.xsd");
            case "GetStatus":
                if (StringUtils.isBlank(jobId)) {
                    throw new MissingParameterValueException("JobId");
                }
                ExecuteResponse executeResponse = wpsServiceProvider.getStatus(requestContext, jobId);
                return JaxbHelper.marshalWithSchemaLocation(executeResponse, "http://www.opengis.net/wps/1.0.0 " +
                                                                             "http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd");
            default:
                throw new InvalidParameterValueException("Request");
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
        Cookie[] cookies = servletRequest.getCookies();
        java.nio.file.Path tempFilePath = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REQUEST_ID.equalsIgnoreCase(cookie.getName())) {
                    String id = cookie.getValue();
                    String filename = TEMP_DIRECTORY + "/" + REQUEST_FILE_PREFIX + id;
                    System.out.println("Reading request from file " + filename);
                    byte[] encoded;
                    try {
                        tempFilePath = Paths.get(filename);
                        encoded = Files.readAllBytes(tempFilePath);
                        request = new String(encoded, UTF_8);
                        request = cleanRequest(request);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }

        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServerContext serverContext = requestContext.getServerContext();
        WpsServiceInstance wpsServiceProvider = SpiLoader.getWpsServiceProvider(serverContext, applicationName);
        Execute execute = getExecute(request);


        try {
            String exceptionXml = performXmlParameterValidation(execute);
            if (StringUtils.isNotBlank(exceptionXml)) {
                return exceptionXml;
            }
            ExecuteResponse executeResponse = wpsServiceProvider.doExecute(requestContext, execute);
            deleteTemporaryFile(tempFilePath);
            return JaxbHelper.marshalWithSchemaLocation(executeResponse, "http://www.opengis.net/wps/1.0.0 " +
                                                                         "http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd");
        } catch (WpsServiceException | IOException exception) {
            LOG.log(Level.SEVERE, "Unable to process the WPS request", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            ExceptionReport exceptionReport = exceptionResponse.getExceptionResponse(exception);
            try {
                deleteTemporaryFile(tempFilePath);
            } catch (IOException ignored) {
            }
            return getExceptionString(exceptionReport);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            try {
                deleteTemporaryFile(tempFilePath);
            } catch (IOException ignored) {
            }
            return exceptionResponse.getJaxbExceptionResponse();
        }
    }

    private void deleteTemporaryFile(java.nio.file.Path tempFilePath) throws IOException {
        if (tempFilePath != null && Files.exists(tempFilePath)) {
            LOG.log(Level.INFO, "deleting temporary file '" + tempFilePath + "'.");
            Files.delete(tempFilePath);
        }
    }

    static String cleanRequest(String request) {
        request = "<?xml" + request.split("<\\?xml")[1];
        request = request.split("wps:Execute>")[0] + "wps:Execute>";
        return request;
    }

    private String performDescribeProcessParameterValidation(String processorId, String version) throws
                                                                                                 MissingParameterValueException {
        if (StringUtils.isBlank(version)) {
            throw new MissingParameterValueException("Version");
        }
        if (StringUtils.isBlank(processorId)) {
            throw new MissingParameterValueException("Identifier");
        }
        return null;
    }

    private String performUrlParameterValidation(String service, String requestType) throws
                                                                                     MissingParameterValueException,
                                                                                     InvalidParameterValueException {
        if (StringUtils.isBlank(service)) {
            throw new MissingParameterValueException("Service");
        }
        if (StringUtils.isBlank(requestType)) {
            throw new MissingParameterValueException("Request");
        }
        if (!service.equals("WPS")) {
            throw new InvalidParameterValueException("Service");
        }
        return "";
    }

    private String performXmlParameterValidation(Execute execute) throws
                                                                  MissingParameterValueException,
                                                                  InvalidParameterValueException {
        String service = execute.getService();
        String version = execute.getVersion();
        CodeType identifier = execute.getIdentifier();

        if (StringUtils.isBlank(service)) {
            throw new MissingParameterValueException("Service");
        }
        if (!"WPS".equals(service)) {
            throw new InvalidParameterValueException("Service");
        }

        if (StringUtils.isBlank(version)) {
            throw new MissingParameterValueException("Version");
        }

        if (identifier == null || StringUtils.isBlank(identifier.getValue())) {
            throw new MissingParameterValueException("Identifier");
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

    private String getExceptionString(ExceptionReport exceptionReport) {
        try {
            return JaxbHelper.marshalWithSchemaLocation(exceptionReport, "http://www.opengis.net/ows/1.1 " +
                                                                         "http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd");
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
