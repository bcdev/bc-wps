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
    public String getName() {
        return "Unimplemented mock WPS";
    }

    @Override
    public String getDescription() {
        return "This is still work in progress";
    }

    @Override
    public WpsServiceInstance createServiceInstance(WpsServerContext context) {
        return new MockInstanceOne();
    }
}
