package com.bc.wps.api;

/**
 * The purpose of this interface to store the context information that is available from
 * the request and that is useful during the processing (eg. user name)
 *
 * @author hans
 */
public interface WpsRequestContext {

    String getUserName();

    WpsServerContext getServerContext();

    String getHeaderField(String key);

}
