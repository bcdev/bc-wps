package com.bc.wps.spi;

import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.WpsServiceProvider;
import com.bc.wps.spi.mockproviders.MockInstanceTwo;

/**
 * @author hans
 */
public class MockWpsTwoSpi implements WpsServiceProvider {

    @Override
    public String getId() {
        return "mock2";
    }

    @Override
    public String getName() {
        return "Mock WPS provider implementation";
    }

    @Override
    public String getDescription() {
        return "This is a mock WPS provider implementation. ";
    }

    @Override
    public WpsServiceInstance createServiceInstance(WpsServerContext context) {
        return new MockInstanceTwo();
    }
}
