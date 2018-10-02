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
import java.io.*;
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
public class JaxRsWpsService {

    final WpsFrontendConnector wpsFrontendConnector = new WpsFrontendConnector();

    @GET
    @Path("/{application}")
    @Produces(MediaType.APPLICATION_XML)
    public String getWpsService(@PathParam("application") String applicationName,
                                @QueryParam("Service") String service,
                                @QueryParam("Request") String requestType,
                                @QueryParam("AcceptVersions") String acceptedVersion,
                                @QueryParam("Language") String language,
                                @QueryParam("Identifier") String processIdentifier,
                                @QueryParam("Version") String version,
                                @QueryParam("JobId") String jobId,
                                @Context HttpServletRequest servletRequest) {
        return wpsFrontendConnector.getWpsService(applicationName, service, requestType, acceptedVersion, language, processIdentifier, version, jobId, servletRequest);
    }

    @POST
    @Path("/{application}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String postExecuteService(@PathParam("application") String applicationName,
                                     String request,
                                     @Context HttpServletRequest servletRequest) {
        return wpsFrontendConnector.postExecuteService(applicationName, request, servletRequest);
    }

}
