package com.bc.wps.api;

/**
 * The purpose of this interface to store the context information that is available from
 * the server and that is useful during the processing (eg. host name, port number)
 *
 * @author hans
 */
public interface WpsServerContext {

    String getRequestUrl();

    String getHostAddress();

    int getPort();

}
