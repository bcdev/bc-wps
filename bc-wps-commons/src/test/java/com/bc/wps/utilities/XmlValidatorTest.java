package com.bc.wps.utilities;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;

/**
 * This is a test to the {@link XmlValidator}.
 * Because the validation process requires a connection to the schema location and may take a
 * while, these test use @{@link RunWith} with {@link ProfileTestRunner} to prevent
 * unintended test execution. These time consuming tests can be executed if the VM-Parameter
 * given by the annotation "RunIfProfileIsActivated" is set to true.
 * E.g. -Dxml.validator.tests=true
 * The test files are supposed to be manually modified based on the latest response
 * of the actual WPS. Unit tests for a mock WPS implementation can be found in JaxRsWpsServiceTest
 * in module bc-wps-impl.
 *
 * @author hans
 */
@RunWith(ProfileTestRunner.class)
@ProfileTestRunner.RunIfProfileIsActivated("xml.validator.test")
public class XmlValidatorTest {

    private PrintStream err;
    ByteArrayOutputStream errStream;

    @Before
    public void setUp() throws Exception {
        err = System.err;
        errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
    }

    @After
    public void tearDown() throws Exception {
        System.setErr(err);
    }

    @Test
    public void canValidateExecuteRequest() throws Exception {
        final File xmlFile = getXmlResourceFile("bc-wps-executeRequest.xml");
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateExecuteRequestL3() throws Exception {
        final File xmlFile = getXmlResourceFile("bc-wps-executeRequestL3.xml");
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateGetCapabilitiesResponse() throws Exception {
        final String name = "bc-wps-getCapabilitiesResponse.xml";
        final File xmlFile = getXmlResourceFile(name);
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateDescribeProcessResponse() throws Exception {
        final String name = "bc-wps-describeProcessResponse.xml";
        final File xmlFile = getXmlResourceFile(name);
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateExecuteAcceptedResponse() throws Exception {
        final String name = "bc-wps-executeInProgressResponse.xml";
        final File xmlFile = getXmlResourceFile(name);
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateExecuteSuccessfulResponse() throws Exception {
        final String name = "bc-wps-executeSuccessfulResponse.xml";
        final File xmlFile = getXmlResourceFile(name);
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void canValidateExecuteFailedResponse() throws Exception {
        final String name = "bc-wps-executeFailedResponse.xml";
        final File xmlFile = getXmlResourceFile(name);
        XmlValidator.validate(xmlFile);

        assertThat(errStream.toString(), equalTo(""));
    }

    @Test
    public void validateString() throws Exception {
        String wpsResponseString = getExecuteAcceptedResponse();
        XmlValidator.validateString(wpsResponseString);

        assertThat(errStream.toString(), equalTo(""));
    }

    private String getExecuteAcceptedResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "<wps:ExecuteResponse serviceInstance=\"http://www.brockmann-consult.de/bc-wps/wps/calvalus?\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "\t<wps:Process wps:processVersion=\"1.0\">\n" +
               "\t\t<ows:Identifier>urbantep-subsetting~1.0~Subset</ows:Identifier>\n" +
               "\t\t<ows:Title>urbantep-subsetting~1.0~Subset</ows:Title>\n" +
               "\t</wps:Process>\n" +
               "\t<wps:Status creationTime=\"2016-04-04T17:26:14.911+02:00\">\n" +
               "\t\t<wps:ProcessStarted percentCompleted=\"0\">UNKNOWN</wps:ProcessStarted>\n" +
               "\t</wps:Status>\n" +
               "</wps:ExecuteResponse>\n";
    }

    private File getXmlResourceFile(String name) {
        final URL resource = this.getClass().getResource("/" + name);
        return new File(resource.getFile());
    }
}