package com.bc.wps.api;


import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;

import java.util.List;

/**
 * @author hans
 */
public interface WpsServiceInstance {

    Capabilities getCapabilities(WpsRequestContext ctx) throws WpsServiceException;

    List<ProcessDescriptionType> describeProcess(WpsRequestContext ctx, String processorId) throws WpsServiceException;

    ExecuteResponse doExecute(WpsRequestContext ctx, Execute executeRequest) throws WpsServiceException;

    ExecuteResponse getStatus(WpsRequestContext ctx, String jobId) throws WpsServiceException;

    /**
     * Called when the service is no longer used. This may occur for different reasons:
     * <ol>
     *     <li>the service provider is about to be uninstalled or updated;</li>
     *     <li>the service hasn't been used for a period of time, so it will be deactivated;</li>
     *     <li>the WPS is about to be shut down.</li>
     * </ol>
     */
    void dispose();
}
