package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServerContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hans
 */
public class WpsRequestContextImpl implements WpsRequestContext {

    private final HttpServletRequest servletRequest;

    public WpsRequestContextImpl(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    @Override
    public String getUserName() {
        return servletRequest.getUserPrincipal().getName();
    }

    @Override
    public WpsServerContext getServerContext() {
        return new WpsServerContextImpl(servletRequest);
    }
}
