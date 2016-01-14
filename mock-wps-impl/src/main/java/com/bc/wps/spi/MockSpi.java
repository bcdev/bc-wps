package com.bc.wps.spi;

import com.bc.wps.api.IServiceProvider;
import com.bc.wps.spi.providers.MockProviderOne;

/**
 * @author hans
 */
public class MockSpi implements IBcWpsSpi {

    @Override
    public IServiceProvider create() {
        return new MockProviderOne();
    }
}
