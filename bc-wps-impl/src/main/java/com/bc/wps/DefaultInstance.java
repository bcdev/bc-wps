package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hans
 */
public class DefaultInstance implements WpsServiceInstance {

    @Override
    public Capabilities getCapabilities(WpsRequestContext context) {
        return new Capabilities();
    }

    @Override
    public List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processorId) {
        return new ArrayList<>();
    }

    @Override
    public ExecuteResponse doExecute(WpsRequestContext context, Execute executeRequest) {
        return new ExecuteResponse();
    }

    @Override
    public ExecuteResponse getStatus(WpsRequestContext context, String jobid) {
        return new ExecuteResponse();
    }

    @Override
    public void dispose() {

    }

}
