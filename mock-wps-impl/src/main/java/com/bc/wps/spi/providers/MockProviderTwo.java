package com.bc.wps.spi.providers;

import com.bc.wps.api.IServiceProvider;

/**
 * @author hans
 */
public class MockProviderTwo implements IServiceProvider {

    @Override
    public String getApplicationName() {
        return "provider2";
    }

    @Override
    public String getCapabilities() {
        return "getCapabilities from Provider2";
    }

    @Override
    public String describeProcess() {
        return "describeProcess from Provider2";
    }

    @Override
    public String doExecute() {
        return "execute from Provider2";
    }

    @Override
    public String getStatus() {
        return "getStatus from Provider2";
    }
}
