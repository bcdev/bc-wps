package com.bc.wps.spi;

import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.WpsServiceProvider;
import com.bc.wps.spi.mockproviders.MockInstanceOne;

/**
 * @author hans
 */
public class MockWpsOneSpi implements WpsServiceProvider {

    @Override
    public String getId() {
        return "mock1";
    }

    @Override
    public WpsServiceInstance createServiceInstance(WpsServerContext ctx) {
        return new MockInstanceOne();
    }
}
