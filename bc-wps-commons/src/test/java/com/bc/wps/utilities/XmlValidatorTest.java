package com.bc.wps.utilities;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

/**
 * This is a test to the {@link XmlValidator}. All the tests are supposed to be run manually
 * because the validation process requires a connection to the schema location and may take a
 * while. Also, the test files are also supposed to be manually modified based on the latest response
 * of the actual WPS. Unit tests for a mock WPS implementation can be found in {@Link WpsServiceTest}.
 *
 * @author hans
 */
public class XmlValidatorTest {

    @Ignore
    @Test
    public void canValidateExecuteRequest() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-executeRequest.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void canValidateExecuteRequestL3() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-executeRequestL3.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void canValidateGetCapabilitiesResponse() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-getCapabilitiesResponse.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void canValidateDescribeProcessResponse() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-describeProcessResponse.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void canValidateExecuteAcceptedResponse() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-executeInProgressResponse.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void canValidateExecuteSuccessfulResponse() throws Exception {
        File xmlFile = new File("src/test/resources/bc-wps-executeSuccessfulResponse.xml");
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Ignore
    @Test
    public void validateString() throws Exception {
        String wpsResponseString = getExecuteAcceptedResponse();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        XmlValidator.validateString(wpsResponseString);

        assertThat(errStream.toString(), equalTo(""));
    }

    private String getExecuteAcceptedResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "<wps:ExecuteResponse serviceInstance=\"http://www.brockmann-consult.de/bc-wps/wps/calvalus?\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "\t<wps:Process wps:processVersion=\"1.0\">\n" +
               "\t\t<ows:Identifier>urbantep-subsetting~1.0~Subset</ows:Identifier>\n" +
               "\t\t<ows:Title>urbantep-subsetting~1.0~Subset</ows:Title>\n" +
               "\t</wps:Process>\n" +
               "\t<wps:Status creationTime=\"2016-04-04T17:26:14.911+02:00\">\n" +
               "\t\t<wps:ProcessStarted percentCompleted=\"0\">UNKNOWN</wps:ProcessStarted>\n" +
               "\t</wps:Status>\n" +
               "</wps:ExecuteResponse>\n";
    }
}