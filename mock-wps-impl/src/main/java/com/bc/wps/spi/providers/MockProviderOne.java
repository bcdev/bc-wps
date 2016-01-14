package com.bc.wps.spi.providers;

import com.bc.wps.api.IServiceProvider;

/**
 * @author hans
 */
public class MockProviderOne implements IServiceProvider {

    @Override
    public String getApplicationName() {
        return "provider1";
    }

    @Override
    public String getCapabilities() {
        return "getCapabilities from Provider1";
    }

    @Override
    public String describeProcess() {
        return "describeProcess from Provider1";
    }

    @Override
    public String doExecute() {
        return "execute from Provider1";
    }

    @Override
    public String getStatus() {
        return "getStatus from Provider1";
    }
}
