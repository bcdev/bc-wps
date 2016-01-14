package com.bc.wps;

import com.bc.wps.api.IServiceProvider;

/**
 * @author hans
 */
public class DefaultProvider implements IServiceProvider {

    @Override
    public String getApplicationName() {
        return "calvalus";
    }

    @Override
    public String getCapabilities() {
        return "Default getCapabilities response";
    }

    @Override
    public String describeProcess() {
        return "default describeProcess response";
    }

    @Override
    public String doExecute() {
        return "default execute response";
    }

    @Override
    public String getStatus() {
        return "default getStatus response";
    }
}
