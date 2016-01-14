package com.bc.wps.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * In this case, any actions (if any) to happen when the servlet context is
 * initialized or destroyed are defined.
 *
 * @author hans
 */
public class ContainerInitAndDestroy implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("*****************************************");
        System.out.println("****** Starting calwps application ******");
        System.out.println("*****************************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("********************************************");
        System.out.println("******Stopping StatusObserver thread *******");
        System.out.println("********************************************");
    }
}
