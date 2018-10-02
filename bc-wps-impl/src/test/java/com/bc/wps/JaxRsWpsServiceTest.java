package com.bc.wps;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.bc.wps.exceptions.InvalidRequestException;
import com.bc.wps.utilities.XmlValidator;
import org.junit.*;
import org.junit.rules.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Locale;

/**
 * @author hans
 */
public class JaxRsWpsServiceTest {

    private JaxRsWpsService wpsService;
    private HttpServletRequest mockServletRequest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        mockServletRequest = mock(HttpServletRequest.class);
        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockName");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);
        wpsService = new JaxRsWpsService();
    }

    // XML validation can take a very long time because it has to call external URLs to get the schema
    @Ignore
    @Test
    public void canGetCapabilitiesFromMockInstanceWithValidRequest() {
        String response = wpsService.getWpsService("mock2", "WPS", "GetCapabilities", "", "", "", "", "",
                mockServletRequest);

        XmlValidator.validateString(response);
        assertThat(response, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:Capabilities service=\"WPS\" xml:lang=\"en\" version=\"1.0.0\" " +
                "xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsGetCapabilities_response.xsd\" " +
                "xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" " +
                "xmlns:ows=\"http://www.opengis.net/ows/1.1\" " +
                "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:ServiceIdentification>\n" +
                "        <ows:Title>A mock WPS server</ows:Title>\n" +
                "        <ows:Abstract>A mock WPS server to be used as a reference for any WPS implementations.</ows:Abstract>\n" +
                "        <ows:ServiceType>WPS</ows:ServiceType>\n" +
                "        <ows:ServiceTypeVersion>1.0.0</ows:ServiceTypeVersion>\n" +
                "        <ows:ServiceTypeVersion>2.0.0</ows:ServiceTypeVersion>\n" +
                "        <ows:Fees>gratis</ows:Fees>\n" +
                "    </ows:ServiceIdentification>\n" +
                "    <ows:ServiceProvider>\n" +
                "        <ows:ProviderName>Fantasy World</ows:ProviderName>\n" +
                "        <ows:ProviderSite xlink:href=\"http://fantasy-world.com\"/>\n" +
                "        <ows:ServiceContact>\n" +
                "            <ows:IndividualName>John Doe</ows:IndividualName>\n" +
                "            <ows:PositionName>System Administrator</ows:PositionName>\n" +
                "            <ows:ContactInfo>\n" +
                "                <ows:Phone>\n" +
                "                    <ows:Voice>+49 12345 6789</ows:Voice>\n" +
                "                    <ows:Facsimile>+49 98765 4321</ows:Facsimile>\n" +
                "                </ows:Phone>\n" +
                "                <ows:Address>\n" +
                "                    <ows:DeliveryPoint>Room 1, Building A, Fantasy Avenue</ows:DeliveryPoint>\n" +
                "                    <ows:City>Fantasyville</ows:City>\n" +
                "                    <ows:AdministrativeArea>FF</ows:AdministrativeArea>\n" +
                "                    <ows:PostalCode>1234</ows:PostalCode>\n" +
                "                    <ows:Country>Kingdom of Fantasy</ows:Country>\n" +
                "                    <ows:ElectronicMailAddress>admin@fantasy-world.com</ows:ElectronicMailAddress>\n" +
                "                </ows:Address>\n" +
                "            </ows:ContactInfo>\n" +
                "        </ows:ServiceContact>\n" +
                "    </ows:ServiceProvider>\n" +
                "    <ows:OperationsMetadata>\n" +
                "        <ows:Operation name=\"GetCapabilities\">\n" +
                "            <ows:DCP>\n" +
                "                <ows:HTTP>\n" +
                "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                "                </ows:HTTP>\n" +
                "            </ows:DCP>\n" +
                "        </ows:Operation>\n" +
                "        <ows:Operation name=\"DescribeProcess\">\n" +
                "            <ows:DCP>\n" +
                "                <ows:HTTP>\n" +
                "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                "                </ows:HTTP>\n" +
                "            </ows:DCP>\n" +
                "        </ows:Operation>\n" +
                "        <ows:Operation name=\"Execute\">\n" +
                "            <ows:DCP>\n" +
                "                <ows:HTTP>\n" +
                "                    <ows:Post xlink:href=\"http://companyUrl/serviceName\"/>\n" +
                "                </ows:HTTP>\n" +
                "            </ows:DCP>\n" +
                "        </ows:Operation>\n" +
                "        <ows:Operation name=\"GetStatus\">\n" +
                "            <ows:DCP>\n" +
                "                <ows:HTTP>\n" +
                "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                "                </ows:HTTP>\n" +
                "            </ows:DCP>\n" +
                "        </ows:Operation>\n" +
                "    </ows:OperationsMetadata>\n" +
                "    <wps:ProcessOfferings>\n" +
                "        <wps:Process wps:processVersion=\"0.1\">\n" +
                "            <ows:Identifier>process1</ows:Identifier>\n" +
                "            <ows:Title>Process 1</ows:Title>\n" +
                "            <ows:Abstract>This is a mock process from mock provider 2</ows:Abstract>\n" +
                "        </wps:Process>\n" +
                "    </wps:ProcessOfferings>\n" +
                "    <wps:Languages>\n" +
                "        <wps:Default>\n" +
                "            <ows:Language>EN</ows:Language>\n" +
                "        </wps:Default>\n" +
                "        <wps:Supported>\n" +
                "            <ows:Language>EN</ows:Language>\n" +
                "            <ows:Language>DE</ows:Language>\n" +
                "        </wps:Supported>\n" +
                "    </wps:Languages>\n" +
                "</wps:Capabilities>\n"));
    }

    @Test
    public void canGetCapabilitiesWithCookies() {
        Cookie mockCookie = new Cookie("queryString", "Service=WPS&Request=GetCapabilities");
        when(mockServletRequest.getCookies()).thenReturn(new Cookie[]{mockCookie});

        String response = wpsService.getWpsService("mock2", null, null, "", "", "", "", "",
                mockServletRequest);

        assertThat(response, containsString("<ows:ServiceIdentification>\n" +
                "        <ows:Title>A mock WPS server</ows:Title>\n" +
                "        <ows:Abstract>A mock WPS server to be used as a reference for any WPS implementations.</ows:Abstract>\n" +
                "        <ows:ServiceType>WPS</ows:ServiceType>\n" +
                "        <ows:ServiceTypeVersion>1.0.0</ows:ServiceTypeVersion>\n" +
                "        <ows:ServiceTypeVersion>2.0.0</ows:ServiceTypeVersion>\n" +
                "        <ows:Fees>gratis</ows:Fees>\n" +
                "    </ows:ServiceIdentification>"));
    }

    // XML validation can take a very long time because it has to call external URLs to get the schema
    @Ignore
    @Test
    public void canDescribeProcessWithValidRequest() {
        String response = wpsService.getWpsService("mock2", "WPS", "DescribeProcess", "1.0.0", "en", "all", "1.0.0", "",
                mockServletRequest);

        XmlValidator.validateString(response);
        assertThat(response, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ProcessDescriptions service=\"WPS\" version=\"1.0.0\" " +
                "xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsDescribeProcess_response.xsd\" " +
                "xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" " +
                "xmlns:ows=\"http://www.opengis.net/ows/1.1\" " +
                "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ProcessDescription wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>Process1</ows:Identifier>\n" +
                "        <ows:Title>Process1</ows:Title>\n" +
                "        <ows:Abstract>Description</ows:Abstract>\n" +
                "        <DataInputs>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>input1</ows:Identifier>\n" +
                "                <ows:Title>input title</ows:Title>\n" +
                "                <ows:Abstract>input description</ows:Abstract>\n" +
                "                <LiteralData>\n" +
                "                    <ows:DataType>String</ows:DataType>\n" +
                "                    <ows:AllowedValues>\n" +
                "                        <ows:Value>allowedValue</ows:Value>\n" +
                "                        <ows:Value>allowedValue</ows:Value>\n" +
                "                    </ows:AllowedValues>\n" +
                "                    <DefaultValue>default</DefaultValue>\n" +
                "                </LiteralData>\n" +
                "            </Input>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>input2</ows:Identifier>\n" +
                "                <ows:Title>input without allowed values</ows:Title>\n" +
                "                <ows:Abstract>input without allowed values description</ows:Abstract>\n" +
                "                <LiteralData>\n" +
                "                    <ows:DataType>String</ows:DataType>\n" +
                "                    <ows:AnyValue/>\n" +
                "                </LiteralData>\n" +
                "            </Input>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>complex.parameter</ows:Identifier>\n" +
                "                <ows:Title>An example of complex parameter</ows:Title>\n" +
                "                <ows:Abstract>Description for the parameter</ows:Abstract>\n" +
                "                <ComplexData>\n" +
                "                    <Default>\n" +
                "                        <Format>\n" +
                "                            <MimeType>application/xml</MimeType>\n" +
                "                            <Schema>schema.xsd</Schema>\n" +
                "                        </Format>\n" +
                "                    </Default>\n" +
                "                    <Supported>\n" +
                "                        <Format>\n" +
                "                            <MimeType>application/xml</MimeType>\n" +
                "                            <Schema>schema.xsd</Schema>\n" +
                "                        </Format>\n" +
                "                    </Supported>\n" +
                "                </ComplexData>\n" +
                "            </Input>\n" +
                "        </DataInputs>\n" +
                "        <ProcessOutputs>\n" +
                "            <Output>\n" +
                "                <ows:Identifier>output1</ows:Identifier>\n" +
                "                <ows:Title>Output 1</ows:Title>\n" +
                "                <ows:Abstract>This is output 1</ows:Abstract>\n" +
                "                <LiteralOutput>\n" +
                "                    <ows:DataType ows:reference=\"data type reference\">data type</ows:DataType>\n" +
                "                </LiteralOutput>\n" +
                "            </Output>\n" +
                "        </ProcessOutputs>\n" +
                "    </ProcessDescription>\n" +
                "    <ProcessDescription wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>Process2</ows:Identifier>\n" +
                "        <ows:Title>Process2</ows:Title>\n" +
                "        <ows:Abstract>Description</ows:Abstract>\n" +
                "        <DataInputs>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>input1</ows:Identifier>\n" +
                "                <ows:Title>input title</ows:Title>\n" +
                "                <ows:Abstract>input description</ows:Abstract>\n" +
                "                <LiteralData>\n" +
                "                    <ows:DataType>String</ows:DataType>\n" +
                "                    <ows:AllowedValues>\n" +
                "                        <ows:Value>allowedValue</ows:Value>\n" +
                "                        <ows:Value>allowedValue</ows:Value>\n" +
                "                    </ows:AllowedValues>\n" +
                "                    <DefaultValue>default</DefaultValue>\n" +
                "                </LiteralData>\n" +
                "            </Input>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>input2</ows:Identifier>\n" +
                "                <ows:Title>input without allowed values</ows:Title>\n" +
                "                <ows:Abstract>input without allowed values description</ows:Abstract>\n" +
                "                <LiteralData>\n" +
                "                    <ows:DataType>String</ows:DataType>\n" +
                "                    <ows:AnyValue/>\n" +
                "                </LiteralData>\n" +
                "            </Input>\n" +
                "            <Input minOccurs=\"0\" maxOccurs=\"1\">\n" +
                "                <ows:Identifier>complex.parameter</ows:Identifier>\n" +
                "                <ows:Title>An example of complex parameter</ows:Title>\n" +
                "                <ows:Abstract>Description for the parameter</ows:Abstract>\n" +
                "                <ComplexData>\n" +
                "                    <Default>\n" +
                "                        <Format>\n" +
                "                            <MimeType>application/xml</MimeType>\n" +
                "                            <Schema>schema.xsd</Schema>\n" +
                "                        </Format>\n" +
                "                    </Default>\n" +
                "                    <Supported>\n" +
                "                        <Format>\n" +
                "                            <MimeType>application/xml</MimeType>\n" +
                "                            <Schema>schema.xsd</Schema>\n" +
                "                        </Format>\n" +
                "                    </Supported>\n" +
                "                </ComplexData>\n" +
                "            </Input>\n" +
                "        </DataInputs>\n" +
                "        <ProcessOutputs>\n" +
                "            <Output>\n" +
                "                <ows:Identifier>output1</ows:Identifier>\n" +
                "                <ows:Title>Output 1</ows:Title>\n" +
                "                <ows:Abstract>This is output 1</ows:Abstract>\n" +
                "                <LiteralOutput>\n" +
                "                    <ows:DataType ows:reference=\"data type reference\">data type</ows:DataType>\n" +
                "                </LiteralOutput>\n" +
                "            </Output>\n" +
                "        </ProcessOutputs>\n" +
                "    </ProcessDescription>\n" +
                "</wps:ProcessDescriptions>\n"));

    }

    @Test
    public void canDescribeProcessWithCookies() {
        Cookie mockCookie = new Cookie("queryString", "Service=WPS&Request=DescribeProcess&Version=1.0.0&Identifier=all");
        when(mockServletRequest.getCookies()).thenReturn(new Cookie[]{mockCookie});

        String response = wpsService.getWpsService("mock2", null, null, "1.0.0", "en", null, null, "",
                mockServletRequest);

        assertThat(response, containsString("    <ProcessDescription wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>Process1</ows:Identifier>\n" +
                "        <ows:Title>Process1</ows:Title>\n" +
                "        <ows:Abstract>Description</ows:Abstract>\n"));
    }

    @Test
    public void canReturnExceptionGetCapabilitiesWithInvalidService() {
        String wpsResponse = wpsService.getWpsService("mock2", "invalidService", "GetCapabilities", "", "", "", "", "",
                mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"Service\">\n" +
                "        <ows:ExceptionText>Parameter 'Service' has an invalid value.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenDescribeProcessWithNoProcessId() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "DescribeProcess", "", "", "", "1.0.0", "",
                mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Identifier\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Identifier' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenDescribeProcessWithNoVersionNumber() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "DescribeProcess", "", "", "bundle~name~version",
                "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Version\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Version' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenGetStatusWithoutJobId() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "GetStatus", "", "", "", "", "",
                mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"JobId\">\n" +
                "        <ows:ExceptionText>The value of parameter 'JobId' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoServiceParameter() {
        String wpsResponse = wpsService.getWpsService("mock2", "", "", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Service\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Service' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoRequestTypeParameter() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Request\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Request' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canGetStatusFromMockWps() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "GetStatus", "", "", "", "", "dummyJobId",
                mockServletRequest);

        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ExecuteResponse serviceInstance=\"http://companyUrl/serviceName?\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">"));
        assertThat(wpsResponse,
                containsString("        <wps:ProcessStarted percentCompleted=\"65\">RUNNING</wps:ProcessStarted>\n" +
                        "    </wps:Status>\n" +
                        "</wps:ExecuteResponse>\n"));
    }

    @Test
    public void canReturnExceptionWhenRequestIsUnknown() {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "InvalidService", "", "", "", "", "",
                mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"Request\">\n" +
                "        <ows:ExceptionText>Parameter 'Request' has an invalid value.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    // XML validation can take a very long time because it has to call external URLs to get the schema
    @Ignore
    @Test
    public void canExecuteWithValidXmlRequestInMockWps() {
        configureMockUser();
        String wpsResponse = wpsService.postExecuteService("mock2", getValidExecuteRequest(), mockServletRequest);

        XmlValidator.validateString(wpsResponse);
        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ExecuteResponse serviceInstance=\"http://companyUrl/serviceName?\" statusLocation=\"null/mockUserName\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <wps:Process wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>process1</ows:Identifier>\n" +
                "        <ows:Title>Process 1</ows:Title>\n" +
                "        <ows:Abstract>Process 1 description</ows:Abstract>\n" +
                "    </wps:Process>"));
        assertThat(wpsResponse, containsString(
                "        <wps:ProcessAccepted>The request has been accepted. The job is being handled by processor 'beam-idepix~2.0.9~Idepix.Water'.</wps:ProcessAccepted>\n" +
                        "    </wps:Status>\n" +
                        "</wps:ExecuteResponse>\n"));
    }

    // not yet work. Storing in temp file must first be improved.
    @Ignore
    @Test
    public void canExecuteWithGetRequest() {
        configureMockUser();
        Cookie mockCookie = new Cookie("requestId", "aa-bb-cc");
        when(mockServletRequest.getCookies()).thenReturn(new Cookie[]{mockCookie});

        String wpsResponse = wpsService.getWpsService(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                mockServletRequest);
    }

    @Test
    public void canGetDefaultServiceAndVersionInRequestXml() {
        configureMockUser();
        String wpsResponse = wpsService.postExecuteService("mock2", getExecuteRequestWithoutServiceAndVersion(),
                mockServletRequest);

        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ExecuteResponse serviceInstance=\"http://companyUrl/serviceName?\" statusLocation=\"null/mockUserName\" service=\"WPS\" version=\"1.0.0\" " +
                "xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" " +
                "xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" " +
                "xmlns:ows=\"http://www.opengis.net/ows/1.1\" " +
                "xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <wps:Process wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>process1</ows:Identifier>\n" +
                "        <ows:Title>Process 1</ows:Title>\n" +
                "        <ows:Abstract>Process 1 description</ows:Abstract>\n" +
                "    </wps:Process>"));
        assertThat(wpsResponse, containsString(
                "        <wps:ProcessAccepted>The request has been accepted. The job is being handled by processor 'beam-idepix~2.0.9~Idepix.Water'.</wps:ProcessAccepted>\n" +
                        "    </wps:Status>\n" +
                        "</wps:ExecuteResponse>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoProcessorId() {
        String wpsResponse = wpsService.postExecuteService("mock2", getExecuteRequestWithoutIdentifier(),
                mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Identifier\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Identifier' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>\n"));
    }

    @Test
    public void canThrowExceptionWhenExecuteWithMalformedXmlRequest() {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Invalid Execute request. Content is not allowed in prolog.");

        wpsService.postExecuteService("mock2", "requestXml", mockServletRequest);
    }

    @Test
    public void canThrowExceptionWhenExecuteWithUnknownXmlRequest() {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage(
                "Invalid Execute request. unexpected element (uri:\"http://java.sun.com/xml/ns/j2ee\", local:\"web-app\"). " +
                        "Expected elements are <{http://www.opengis.net/wps/1.0.0}AllowedValues>," +
                        "<{http://www.opengis.net/wps/1.0.0}AnyValue>," +
                        "<{http://www.opengis.net/wps/1.0.0}Capabilities>,<{http://www.opengis.net/wps/1.0.0}DCP>," +
                        "<{http://www.opengis.net/wps/1.0.0}DescribeProcess>,<{http://www.opengis.net/ows/1.1}ExceptionReport>," +
                        "<{http://www.opengis.net/wps/1.0.0}Execute>,<{http://www.opengis.net/wps/1.0.0}ExecuteResponse>," +
                        "<{http://www.opengis.net/wps/1.0.0}GetCapabilities>,<{http://www.opengis.net/wps/1.0.0}HTTP>," +
                        "<{http://www.opengis.net/wps/1.0.0}Languages>,<{http://www.opengis.net/wps/1.0.0}NoValues>," +
                        "<{http://www.opengis.net/wps/1.0.0}Operation>,<{http://www.opengis.net/wps/1.0.0}OperationsMetadata>," +
                        "<{http://www.opengis.net/wps/1.0.0}ProcessDescriptions>,<{http://www.opengis.net/wps/1.0.0}ProcessOfferings>," +
                        "<{http://www.opengis.net/ows/1.1}ServiceIdentification>,<{http://www.opengis.net/wps/1.0.0}ServiceProvider>," +
                        "<{http://www.opengis.net/wps/1.0.0}ValuesReference>,<{http://www.opengis.net/wps/1.0.0}WSDL>," +
                        "<{http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd}parameters>");

        wpsService.postExecuteService("mock2", getUnknownXmlRequest(), mockServletRequest);
    }

    @Test
    public void canThrowExceptionWhenExecuteWithMissingElement() {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Invalid Execute request. unexpected element (uri:\"\", local:\"ProcessDescriptions\"). " +
                "Expected elements are <{http://www.opengis.net/wps/1.0.0}AllowedValues>," +
                "<{http://www.opengis.net/wps/1.0.0}AnyValue>," +
                "<{http://www.opengis.net/wps/1.0.0}Capabilities>," +
                "<{http://www.opengis.net/wps/1.0.0}DCP>," +
                "<{http://www.opengis.net/wps/1.0.0}DescribeProcess>," +
                "<{http://www.opengis.net/ows/1.1}ExceptionReport>," +
                "<{http://www.opengis.net/wps/1.0.0}Execute>," +
                "<{http://www.opengis.net/wps/1.0.0}ExecuteResponse>," +
                "<{http://www.opengis.net/wps/1.0.0}GetCapabilities>," +
                "<{http://www.opengis.net/wps/1.0.0}HTTP>," +
                "<{http://www.opengis.net/wps/1.0.0}Languages>," +
                "<{http://www.opengis.net/wps/1.0.0}NoValues>," +
                "<{http://www.opengis.net/wps/1.0.0}Operation>," +
                "<{http://www.opengis.net/wps/1.0.0}OperationsMetadata>," +
                "<{http://www.opengis.net/wps/1.0.0}ProcessDescriptions>," +
                "<{http://www.opengis.net/wps/1.0.0}ProcessOfferings>," +
                "<{http://www.opengis.net/ows/1.1}ServiceIdentification>," +
                "<{http://www.opengis.net/wps/1.0.0}ServiceProvider>," +
                "<{http://www.opengis.net/wps/1.0.0}ValuesReference>," +
                "<{http://www.opengis.net/wps/1.0.0}WSDL>," +
                "<{http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd}parameters>");

        wpsService.postExecuteService("mock2", getInvalidExecuteRequest(), mockServletRequest);
    }

    @Test
    public void testCleanRequest() {
        String xmlToCleanUp =
                "--------------------------e43de47a79e47e3d\n" +
                        "Content-Disposition: form-data; name=\"request\"; filename=\"test-request.xml\"\n" +
                        "Content-Type: application/xml\n" +
                        "\n" +
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?> <wps:Execute service=\"WPS\"\n" +
                        "             version=\"1.0.0\"\n" +
                        "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                        "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                        "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                        "  <ows:Identifier>fub-wew~6.1~FUB.Water</ows:Identifier>" +
                        "..................." +
                        "     </wps:Data>\n" +
                        "    </wps:Input>\n" +
                        "  </wps:DataInputs> </wps:Execute>\n" +
                        "\n" +
                        "--------------------------e43de47a79e47e3d--";

        String cleanXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?> <wps:Execute service=\"WPS\"\n" +
                        "             version=\"1.0.0\"\n" +
                        "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                        "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                        "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                        "  <ows:Identifier>fub-wew~6.1~FUB.Water</ows:Identifier>" +
                        "..................." +
                        "     </wps:Data>\n" +
                        "    </wps:Input>\n" +
                        "  </wps:DataInputs> </wps:Execute>";

        assertEquals(cleanXml, WpsFrontendConnector.cleanRequest(xmlToCleanUp));
    }

    private String getInvalidExecuteRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ProcessDescriptions service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "\t<ProcessDescription storeSupported=\"true\" statusSupported=\"true\" wps:processVersion=\"2.1.3-SNAPSHOT\">\n" +
                "\t\t<ows:Identifier>beam-idepix~2.1.3-SNAPSHOT~Idepix.Land</ows:Identifier>\n" +
                "\t\t<ows:Title>beam-idepix~2.1.3-SNAPSHOT~Idepix.Land</ows:Title>\n" +
                "\t\t<ows:Abstract>\n" +
                "\t\t</ows:Abstract>\n" +
                "\t\t<DataInputs>\n" +
                "\t\t\t<Input>\n" +
                "\t\t\t\t<ows:Identifier>productionName</ows:Identifier>\n" +
                "\t\t\t\t<ows:Title>Production name</ows:Title>\n" +
                "\t\t\t\t<LiteralData>\n" +
                "\t\t\t\t\t<ows:DataType>string</ows:DataType>\n" +
                "\t\t\t\t</LiteralData>\n" +
                "\t\t\t</Input>\n" +
                "\t\t</DataInputs>\n" +
                "\t\t<ProcessOutputs>\n" +
                "\t\t\t<Output>\n" +
                "\t\t\t\t<ows:Identifier>productionResults</ows:Identifier>\n" +
                "\t\t\t\t<ows:Title>URL to the production result(s)</ows:Title>\n" +
                "\t\t\t\t<ComplexOutput>\n" +
                "\t\t\t\t</ComplexOutput>\n" +
                "\t\t\t</Output>\n" +
                "\t\t</ProcessOutputs>\n" +
                "\t</ProcessDescription>\n" +
                "</ProcessDescriptions>";
    }

    private String getUnknownXmlRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<web-app version=\"2.4\" xmlns=\"http://java.sun.com/xml/ns/j2ee\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee\n" +
                "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\">\n" +
                "\n" +
                "    <display-name>Calvalus WPS</display-name>\n" +
                "    <description>Calvalus WPS</description>\n" +
                "</web-app>";
    }

    private String getExecuteRequestWithoutIdentifier() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                "\n" +
                "<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "</wps:Execute>";
    }

    private String getExecuteRequestWithoutServiceAndVersion() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                "\n" +
                "<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "\n" +
                "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
                "</wps:Execute>";
    }

    private String getValidExecuteRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
                "\n" +
                "<wps:Execute service=\"WPS\"\n" +
                "             version=\"1.0.0\"\n" +
                "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "\n" +
                "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
                "</wps:Execute>";
    }

    private void configureMockUser() {
        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockUserName");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);
    }

}