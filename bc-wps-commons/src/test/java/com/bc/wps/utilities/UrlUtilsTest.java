package com.bc.wps.utilities;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

/**
 * @author hans
 */
public class UrlUtilsTest {

    @Test
    public void canParseExistingParameters() throws Exception {
        String queryString = "Service=WPS&Request=DescribeProcess&Version=1.0.0&Identifier=all";

        assertThat(UrlUtils.parseParameter(queryString, "Service"), equalTo("WPS"));
        assertThat(UrlUtils.parseParameter(queryString, "Request"), equalTo("DescribeProcess"));
        assertThat(UrlUtils.parseParameter(queryString, "Version"), equalTo("1.0.0"));
        assertThat(UrlUtils.parseParameter(queryString, "Identifier"), equalTo("all"));
    }

    @Test
    public void canReturnEmptyStringWhenParsingNonExistentParameters() throws Exception {
        String queryString = "Service=WPS&Request=GetCapabilities";

        assertThat(UrlUtils.parseParameter(queryString, "service"), equalTo(""));
        assertThat(UrlUtils.parseParameter(queryString, "Version"), equalTo(""));
    }

    @Test
    public void canParseEncodedQueryString() throws Exception {
        String queryString = "%3FService%3DWPS%26Request%3DDescribeProcess%26Version%3D1.0.0%26Identifier%3Dall";

        assertThat(UrlUtils.parseParameter(queryString, "Service"), equalTo("WPS"));
        assertThat(UrlUtils.parseParameter(queryString, "Request"), equalTo("DescribeProcess"));
        assertThat(UrlUtils.parseParameter(queryString, "Version"), equalTo("1.0.0"));
        assertThat(UrlUtils.parseParameter(queryString, "Identifier"), equalTo("all"));
    }

    @Test
    public void canReturnEmptyStringWhenInvalidQueryString() throws Exception {
        String queryString = "blablabla";

        assertThat(UrlUtils.parseParameter(queryString, "Service"), equalTo(""));
        assertThat(UrlUtils.parseParameter(queryString, "Request"), equalTo(""));
        assertThat(UrlUtils.parseParameter(queryString, "Version"), equalTo(""));
        assertThat(UrlUtils.parseParameter(queryString, "Identifier"), equalTo(""));
    }
}