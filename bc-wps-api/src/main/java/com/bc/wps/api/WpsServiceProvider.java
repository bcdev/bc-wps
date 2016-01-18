package com.bc.wps.api;

/**
 * This is the interface for the WPS server. Implement this for a new WPS server.
 *
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
    String getName();

    /**
     * @return the human-readable description of this service.
     */
    String getDescription();

    /**
     * @param context Server context
     *
     * @return WpsServiceInstance
     */
    WpsServiceInstance createServiceInstance(WpsServerContext context);
}
