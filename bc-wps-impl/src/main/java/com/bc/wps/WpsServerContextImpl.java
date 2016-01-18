package com.bc.wps;

import com.bc.wps.api.WpsServerContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hans
 */
public class WpsServerContextImpl implements WpsServerContext {

    private final HttpServletRequest servletRequest;

    public WpsServerContextImpl(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String getRequestUrl() {
        return servletRequest.getRequestURL().toString();
    }

    @Override
    public String getHostAddress() {
        return servletRequest.getServerName();
    }

    @Override
    public int getPort() {
        return servletRequest.getServerPort();
    }
}
