package com.bc.wps.spi.mockproviders;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.WpsServiceException;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;

import java.util.List;

/**
 * @author hans
 */
public class MockInstanceOne implements WpsServiceInstance {

    @Override
    public Capabilities getCapabilities(WpsRequestContext context) throws WpsServiceException {
        return null;
    }

    @Override
    public List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processId) throws WpsServiceException {
        return null;
    }

    @Override
    public ExecuteResponse doExecute(WpsRequestContext context, Execute executeRequest) throws WpsServiceException {
        return null;
    }

    @Override
    public ExecuteResponse getStatus(WpsRequestContext context, String jobId) throws WpsServiceException {
        return null;
    }

    @Override
    public void dispose() {

    }

}
