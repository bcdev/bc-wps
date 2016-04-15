package com.bc.wps.spi.mockproviders;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.exceptions.WpsServiceException;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.ProcessDescriptionType;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * @author hans
 */
public class MockInstanceOne implements WpsServiceInstance {

    @Override
    public Capabilities getCapabilities(WpsRequestContext context) throws WpsServiceException {
        throw new WpsServiceException("Unable to perform GetCapabilities operation successfully", new IOException("dummy IOException"));
    }

    @Override
    public List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processId) throws WpsServiceException {
        throw new WpsServiceException("Unable to perform DescribeProcess operation successfully",
                                      new IOException("process ID '" + processId + "' is not available"));
    }

    @Override
    public ExecuteResponse doExecute(WpsRequestContext context, Execute executeRequest) throws WpsServiceException {
        throw new WpsServiceException("Unable to perform Execute operation successfully", new JAXBException("dummy JaxbException"));
    }

    @Override
    public ExecuteResponse getStatus(WpsRequestContext context, String jobId) throws WpsServiceException {
        throw new WpsServiceException("Unable to perform GetStatus operation successfully", new IOException("dummy IOException"));
    }

    @Override
    public void dispose() {

    }

}
