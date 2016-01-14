package com.bc.wps.api;


/**
 * @author hans
 */
public interface IServiceProvider {

    String getApplicationName();

    String getCapabilities();

    String describeProcess();

    String doExecute();

    String getStatus();

}
