package com.bc.wps.spi;

import com.bc.wps.api.IServiceProvider;

/**
 * @author hans
 */
public interface IBcWpsSpi {

    IServiceProvider create();

}
