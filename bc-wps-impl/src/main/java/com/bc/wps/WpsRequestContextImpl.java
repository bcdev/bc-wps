package com.bc.wps;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServerContext;
import org.apache.commons.lang.StringUtils;

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
        String userName = servletRequest.getUserPrincipal().getName();
        return StringUtils.isBlank(userName) ? "" : userName;
    }

    @Override
    public WpsServerContext getServerContext() {
        return new WpsServerContextImpl(servletRequest);
    }
}
