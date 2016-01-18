package com.bc.wps.api;

import com.bc.wps.api.WpsServiceInstance;

/**
 * @author hans
 */
public interface WpsServiceProvider {

    /**
     * @return the ID of this service which will be used in the URI path.
     */
    String getId();

    /**
     * @return the human-readable name of this service.
     */
    //String getName();

    /**
     * @return the human-readable description of this service.
     */
    //String getDescription();

    WpsServiceInstance createServiceInstance(WpsServerContext ctx);
}
