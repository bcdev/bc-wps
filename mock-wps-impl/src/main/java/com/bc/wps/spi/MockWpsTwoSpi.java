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
    public WpsServiceInstance createServiceInstance(WpsServerContext ctx) {
        return new MockInstanceTwo();
    }
}
