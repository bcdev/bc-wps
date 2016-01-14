package com.bc.wps;


import com.bc.wps.exceptions.WpsRuntimeException;

/**
 * @author hans
 */
public class WpsServiceFactory {

    private final ServletRequestWrapper servletRequestWrapper;

    public WpsServiceFactory(ServletRequestWrapper servletRequestWrapper) {
        this.servletRequestWrapper = servletRequestWrapper;
    }

    public WpsServiceProvider getWpsService(String applicationName) {
        if ("calvalus".equalsIgnoreCase(applicationName)) {
            WpsMetadata calvalusWpsMetadata = WpsMetadataBuilder.create()
                        .withServletRequestWrapper(servletRequestWrapper)
                        .build();
            return new TestWpsServiceProviderImpl(calvalusWpsMetadata);
        } else {
            throw new WpsRuntimeException("Unknown application name '" + applicationName + "'.");
        }
    }
}
