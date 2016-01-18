package com.bc.wps.serviceloader;

import com.bc.wps.DefaultInstance;
import com.bc.wps.api.WpsServerContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.WpsServiceProvider;

import java.util.ServiceLoader;

/**
 * @author hans
 */
public class SpiLoader {

    public static WpsServiceInstance getWpsServiceProvider(WpsServerContext ctx, String applicationName) {
        ServiceLoader<WpsServiceProvider> availableProviders = ServiceLoader.load(WpsServiceProvider.class);
        for (WpsServiceProvider wpsProvider : availableProviders) {
            if (wpsProvider.getId().equalsIgnoreCase(applicationName)) {
                return wpsProvider.createServiceInstance(ctx);
            }
        }
        return new DefaultInstance();
    }
}
