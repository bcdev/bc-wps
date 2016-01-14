package com.bc.wps;


import com.bcs.wps.elements.Execute;

/**
 * @author hans
 */
public abstract class WpsServiceProvider {

    WpsMetadata wpsMetadata;

    public WpsServiceProvider(WpsMetadata wpsMetadata) {
        this.wpsMetadata = wpsMetadata;
    }

    public abstract String getCapabilities();

    public abstract String describeProcess(String processorId);

    public abstract String doExecute(Execute execute, String processorId);

    public abstract String getStatus(String jobId);
}
