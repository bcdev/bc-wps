/*
 * Copyright (C) 2018 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bc.wps;

import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.exceptions.InvalidParameterValueException;
import com.bc.wps.api.exceptions.MissingParameterValueException;
import com.bc.wps.api.exceptions.NoApplicableCodeException;
import com.bc.wps.api.exceptions.WpsServiceException;
import com.bc.wps.api.exceptions.XmlSchemaFaultException;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;
import com.bc.wps.api.schema.ProcessDescriptions;
import com.bc.wps.exceptions.InvalidRequestException;
import com.bc.wps.responses.ExceptionResponse;
import com.bc.wps.utilities.JaxbHelper;
import com.bc.wps.utilities.UrlUtils;
import com.bc.wps.utilities.WpsLogger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// @todo discuss naming with Norman and Cosmin
public class WpsFrontendConnector {

    private static final Logger LOG = WpsLogger.getLogger();
    private static final String REQUEST_ID = "requestId";
    private static final String UTF_8 = "UTF-8";
    private static final String TEMP_DIRECTORY = "tmp";
    private static final String REQUEST_FILE_PREFIX = "request-";

    // @todo discuss naming with Norman and Cosmin
    public String getWpsService(String service,
                                String requestType,
                                String acceptedVersion,
                                String language,
                                String processIdentifier,
                                String version,
                                String jobId,
                                HttpServletRequest servletRequest,
                                WpsServiceInstance wpsServiceProvider) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REQUEST_ID.equalsIgnoreCase(cookie.getName())) {
                    return postExecuteService(requestType, servletRequest, wpsServiceProvider);
                }
                if ("queryString".equalsIgnoreCase(cookie.getName())) {
                    String value = cookie.getValue();
                    try {
                        if (service == null) {
                            String parsed = UrlUtils.parseParameter(value, "Service");
                            if (StringUtils.isNotBlank(parsed)) {
                                service = parsed;
                                LOG.info("Setting service to " + service);
                            }
                        }
                        if (requestType == null) {
                            String parsed = UrlUtils.parseParameter(value, "Request");
                            if (StringUtils.isNotBlank(parsed)) {
                                requestType = parsed;
                                LOG.info("Setting requestType to " + requestType);
                            }
                        }
                        if (version == null) {
                            String parsed = UrlUtils.parseParameter(value, "Version");
                            if (StringUtils.isNotBlank(parsed)) {
                                version = parsed;
                                LOG.info("Setting version to " + version);
                            }
                        }
                        if (processIdentifier == null) {
                            String parsed = UrlUtils.parseParameter(value, "Identifier");
                            if (StringUtils.isNotBlank(parsed)) {
                                processIdentifier = parsed;
                                LOG.info("Setting processId to " + processIdentifier);
                            }
                        }
                        if (jobId == null) {
                            String parsed = UrlUtils.parseParameter(value, "JobId");
                            if (StringUtils.isNotBlank(parsed)) {
                                jobId = parsed;
                                LOG.info("Setting jobId to " + jobId);
                            }
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

        try {
            performUrlParameterValidation(service, requestType);
            final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(servletRequest);
            final Object wpsObject;
            final String schemaLocation;
            switch (requestType) {
                case "GetCapabilities":
                    wpsObject = wpsServiceProvider.getCapabilities(requestContext);
                    schemaLocation = "http://www.opengis.net/wps/1.0.0 " +
                                     "http://schemas.opengis.net/wps/1.0.0/wpsGetCapabilities_response.xsd";

                    break;
                case "DescribeProcess":
                    performDescribeProcessParameterValidation(processIdentifier, version);
                    wpsObject = createProcessDescription(processIdentifier, wpsServiceProvider, requestContext);
                    schemaLocation = "http://www.opengis.net/wps/1.0.0 " +
                                     "http://schemas.opengis.net/wps/1.0.0/wpsDescribeProcess_response.xsd";

                    break;
                case "GetStatus":
                    if (StringUtils.isBlank(jobId)) {
                        throw new MissingParameterValueException("JobId");
                    }
                    wpsObject = wpsServiceProvider.getStatus(requestContext, jobId);
                    if (wpsObject == null) {
                        throw new NoApplicableCodeException("Unknown jobId: '" + jobId + "'", null);
                    }
                    schemaLocation = "http://www.opengis.net/wps/1.0.0 " +
                                     "http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd";

                    break;
                default:
                    throw new InvalidParameterValueException("Request");
            }
            return toString(wpsObject, schemaLocation);
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshall the WPS response", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            return exceptionResponse.getJaxbExceptionResponse();
        } catch (Exception exception) {
            LOG.log(Level.SEVERE, "Unable to process the WPS request", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            ExceptionReport exceptionReport = exceptionResponse.getExceptionResponse(exception);
            return getExceptionString(exceptionReport);
        }
    }

    public String postExecuteService(String request,
                                     HttpServletRequest servletRequest,
                                     WpsServiceInstance wpsServiceProvider) {
        Cookie[] cookies = servletRequest.getCookies();
        java.nio.file.Path tempFilePath = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REQUEST_ID.equalsIgnoreCase(cookie.getName())) {
                    String id = cookie.getValue();
                    String filename = File.separator + TEMP_DIRECTORY + File.separator + REQUEST_FILE_PREFIX + id;
                    System.out.println("Reading request from file " + filename);
                    byte[] encoded;
                    try {
                        tempFilePath = Paths.get(filename);
                        LOG.info(tempFilePath.toAbsolutePath().toString());
                        encoded = Files.readAllBytes(tempFilePath);
                        request = new String(encoded, UTF_8);
                        request = cleanRequest(request);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }

        Execute execute = getExecute(request);

        try {
            performXmlParameterValidation(execute);
            final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(servletRequest);
            ExecuteResponse executeResponse = wpsServiceProvider.doExecute(requestContext, execute);
            deleteTemporaryFile(tempFilePath);
            return JaxbHelper.marshalWithSchemaLocation(
                    executeResponse, "http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd");
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

    static String cleanRequest(String request) {
        request = "<?xml" + request.split("<\\?xml")[1];
        request = request.split("wps:Execute>")[0] + "wps:Execute>";
        return request;
    }

    private ProcessDescriptions createProcessDescription(String processIdentifier, WpsServiceInstance wpsServiceProvider, WpsRequestContextImpl requestContext) throws WpsServiceException {
        List<ProcessDescriptionType> processDescriptionTypes = wpsServiceProvider.describeProcess(
                requestContext, processIdentifier);
        ProcessDescriptions processDescriptions = new ProcessDescriptions();
        processDescriptions.setService("WPS");
        processDescriptions.setVersion("1.0.0");
        processDescriptions.setLang("en");

        for (ProcessDescriptionType process : processDescriptionTypes) {
            processDescriptions.getProcessDescription().add(process);
        }
        return processDescriptions;
    }

    private String toString(Object capabilities, String schemaLocation) throws JAXBException {
        return JaxbHelper.marshalWithSchemaLocation(capabilities, schemaLocation);
    }

    private void deleteTemporaryFile(java.nio.file.Path tempFilePath) throws IOException {
        if (tempFilePath != null && Files.exists(tempFilePath)) {
            LOG.log(Level.INFO, "deleting temporary file '" + tempFilePath + "'.");
            Files.delete(tempFilePath);
        }
    }

    private String performDescribeProcessParameterValidation(String processorId, String version) throws MissingParameterValueException {
        if (StringUtils.isBlank(version)) {
            throw new MissingParameterValueException("Version");
        }
        if (StringUtils.isBlank(processorId)) {
            throw new MissingParameterValueException("Identifier");
        }
        return null;
    }

    private void performUrlParameterValidation(String service, String requestType)
            throws MissingParameterValueException, InvalidParameterValueException {
        if (StringUtils.isBlank(service)) {
            throw new MissingParameterValueException("Service");
        }
        if (StringUtils.isBlank(requestType)) {
            throw new MissingParameterValueException("Request");
        }
        if (!service.equals("WPS")) {
            throw new InvalidParameterValueException("Service");
        }
    }

    private void performXmlParameterValidation(Execute execute)
            throws WpsServiceException {
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
        if (!"1.0.0".equals(version)) {
            throw new InvalidParameterValueException("Version");
        }

        if (identifier == null) {
            throw new XmlSchemaFaultException("Identifier", "Execute");
        }
        if (StringUtils.isBlank(identifier.getValue())) {
            throw new MissingParameterValueException("Identifier");
        }
    }

    private Execute getExecute(String request) {
        InputStream requestInputStream = new ByteArrayInputStream(request.getBytes());
        try {
            return (Execute) JaxbHelper.unmarshal(requestInputStream, new Execute());
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
        String retVal;
        try {
            retVal = JaxbHelper.marshalWithSchemaLocation(exceptionReport, "http://www.opengis.net/ows/1.1 " +
                                                                           "http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd");
        } catch (JAXBException exception) {
            LOG.log(Level.SEVERE, "Unable to marshal the WPS exception.", exception);
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            retVal = exceptionResponse.getJaxbExceptionResponse();
        }
        return retVal;
    }

}
