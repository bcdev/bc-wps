package com.bc.wps.utilities;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hans
 */
public class WpsServletContainer extends ServletContainer {

    private static List<ServletContextListener> listeners = new ArrayList<>();
    private static Logger logger = WpsLogger.getLogger();

    public static void addServletContextListener(ServletContextListener listener) {
        logger.log(Level.INFO, "adding " + listener.getClass() + " listener...");
        listeners.add(listener);
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "destroying " + listeners.size() + " listener(s)...");
        for (ServletContextListener listener : listeners) {
            listener.contextDestroyed(null);
        }
        super.destroy();
    }
}
