package com.bc.wps.utilities;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import org.junit.*;

import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hans
 */
public class WpsLoggerTest {

    @Test
    public void canGetLogger() throws Exception {
        Logger logger = WpsLogger.getLogger();
        logger.log(Level.SEVERE, "Error message");

        assertThat(logger.getName(), equalTo("com.bc.wps"));
    }

    @Test
    public void canLogException() throws Exception {
        Logger logger = WpsLogger.getLogger();
        JAXBException exception = new JAXBException("Unable to unmarshal", "1");
        logger.log(Level.SEVERE, "Error message", exception);

        assertThat(logger.getName(), equalTo("com.bc.wps"));
    }
}