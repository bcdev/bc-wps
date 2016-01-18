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

    /**
     * @param context WpsServerContext is used for creating service instance
     * @param applicationName To select which service instance to be returned.
     * @return the selected service instance. When not available, a default instance is returned.
     */
    public static WpsServiceInstance getWpsServiceProvider(WpsServerContext context, String applicationName) {
        ServiceLoader<WpsServiceProvider> availableProviders = ServiceLoader.load(WpsServiceProvider.class);
        for (WpsServiceProvider wpsProvider : availableProviders) {
            if (wpsProvider.getId().equalsIgnoreCase(applicationName)) {
                return wpsProvider.createServiceInstance(context);
            }
        }
        return new DefaultInstance();
    }
}
