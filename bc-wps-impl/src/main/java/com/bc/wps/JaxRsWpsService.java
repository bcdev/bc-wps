package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.serviceloader.SpiLoader;

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

        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServiceInstance wpsServiceProvider = getServiceProvider(applicationName, requestContext);
        return wpsFrontendConnector.getWpsService(service, requestType, acceptedVersion, language, processIdentifier,
                                                  version, jobId, servletRequest, wpsServiceProvider);
    }

    @POST
    @Path("/{application}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String postExecuteService(@PathParam("application") String applicationName,
                                     String request,
                                     @Context HttpServletRequest servletRequest) {

        WpsRequestContext requestContext = new WpsRequestContextImpl(servletRequest);
        WpsServiceInstance wpsServiceProvider = getServiceProvider(applicationName, requestContext);
        return wpsFrontendConnector.postExecuteService(request, servletRequest, wpsServiceProvider);
    }

    private WpsServiceInstance getServiceProvider(String applicationName, WpsRequestContext requestContext) {
        WpsServerContext serverContext = requestContext.getServerContext();
        return SpiLoader.getWpsServiceProvider(serverContext, applicationName);
    }

}
