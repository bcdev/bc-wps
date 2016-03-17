package com.bc.wps.utilities;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import javax.servlet.ServletContextListener;

/**
 * @author hans
 */
public class WpsServletContainerTest {

    @Test
    public void canAddListeners() throws Exception {
        ServletContextListener mockListener1 = mock(ServletContextListener.class);
        ServletContextListener mockListener2 = mock(ServletContextListener.class);
        ServletContextListener mockListener3 = mock(ServletContextListener.class);

        WpsServletContainer.addServletContextListener(mockListener1);
        WpsServletContainer.addServletContextListener(mockListener2);
        WpsServletContainer.addServletContextListener(mockListener3);

        assertThat(WpsServletContainer.getListeners().size(), equalTo(3));
    }
}