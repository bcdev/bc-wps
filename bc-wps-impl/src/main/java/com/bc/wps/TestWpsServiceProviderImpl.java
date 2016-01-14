package com.bc.wps;

import com.bcs.wps.elements.Execute;

/**
 * @author hans
 */
public class TestWpsServiceProviderImpl extends WpsServiceProvider {

    public TestWpsServiceProviderImpl(WpsMetadata wpsMetadata) {
        super(wpsMetadata);
    }

    @Override
    public String getCapabilities() {
        return null;
    }

    @Override
    public String describeProcess(String processorId) {
        return null;
    }

    @Override
    public String doExecute(Execute execute, String processorId) {
        return null;
    }

    @Override
    public String getStatus(String jobId) {
        return null;
    }
}
