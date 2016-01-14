package com.bc.wps.serviceloader;

import com.bc.wps.DefaultProvider;
import com.bc.wps.api.IServiceProvider;
import com.bc.wps.exceptions.WpsRuntimeException;
import com.bc.wps.spi.IBcWpsSpi;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author hans
 */
public class SpiLoader {

    public static IServiceProvider getWpsServiceProvider(String applicationName) {
        List<IServiceProvider> serviceProviderList = SpiLoader.loadServices();
        for (IServiceProvider serviceProvider : serviceProviderList) {
            if (serviceProvider.getApplicationName().equalsIgnoreCase(applicationName)) {
                return serviceProvider;
            }
        }
        return new DefaultProvider();
    }

    private static List<IServiceProvider> loadServices() {
        ServiceLoader<IBcWpsSpi> availableProviders = ServiceLoader.load(IBcWpsSpi.class);
        ArrayList<IServiceProvider> wpsProviderList = new ArrayList<>();
        for (IBcWpsSpi wpsProvider : availableProviders) {
            wpsProviderList.add(wpsProvider.create());
        }
        return wpsProviderList;
    }
}
